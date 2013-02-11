package org.greatlogic.itunes.server.model.dto;

import com.greatlogic.glbase.gldb.GLDBException;
import com.greatlogic.glbase.gldb.GLSQL;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.greatlogic.itunes.server.ITunesServerEnums.BookClubCol;

@Entity
public class BookClub implements Comparable<BookClub> {
//--------------------------------------------------------------------------------------------------
@Column(length = 200, name = "BookClubDesc", nullable = false)
private String  _bookClubDesc;

@Column(length = 50, name = "BookClubName", nullable = false)
private String  _bookClubName;

@Column(name = "Id", nullable = false, unique = true)
@Id
private Integer _id;

@Column(length = 14, name = "ModifiedDateTime", nullable = false)
private String  _modifiedDateTime;

@Column(length = 50, name = "ModifiedUserId", nullable = false)
private String  _modifiedUserId;

@Column(name = "Version", nullable = false)
private Integer _version;
//--------------------------------------------------------------------------------------------------
public BookClub() {
  // used by the GWT request factory
} // BookClub()
//--------------------------------------------------------------------------------------------------
public BookClub(final GLSQL bookClubSQL) throws GLDBException {
  this(bookClubSQL.asString(BookClubCol.BookClubDesc),
       bookClubSQL.asString(BookClubCol.BookClubName), bookClubSQL.asInt(BookClubCol.Id),
       bookClubSQL.asString(BookClubCol.ModifiedDateTime),
       bookClubSQL.asString(BookClubCol.ModifiedUserId), bookClubSQL.asInt(BookClubCol.Version));
} // BookClub()
//--------------------------------------------------------------------------------------------------
public BookClub(final String bookClubDesc, final String bookClubName, final int id,
                final String modifiedDateTime, final String modifiedUserId, final int version) {
  setBookClubDesc(bookClubDesc);
  setBookClubName(bookClubName);
  _id = id;
  _modifiedDateTime = modifiedDateTime;
  _modifiedUserId = modifiedUserId;
  _version = version;
} // BookClub()
//--------------------------------------------------------------------------------------------------
@Override
public int compareTo(final BookClub org) {
  return _bookClubName.compareToIgnoreCase(org._bookClubName);
} // compareTo()
//--------------------------------------------------------------------------------------------------
public String getBookClubDesc() {
  return _bookClubDesc;
} // getBookClubDesc()
//--------------------------------------------------------------------------------------------------
public String getBookClubName() {
  return _bookClubName;
} // getBookClubName()
//--------------------------------------------------------------------------------------------------
public int getId() {
  return _id;
} // getId()
//--------------------------------------------------------------------------------------------------
public String getModifiedDateTime() {
  return _modifiedDateTime;
} // getModifiedDateTime()
//--------------------------------------------------------------------------------------------------
public String getModifiedUserId() {
  return _modifiedUserId;
} // getModifiedUserId()
//--------------------------------------------------------------------------------------------------
public int getVersion() {
  return _version == null ? 0 : _version;
} // getVersion()
//--------------------------------------------------------------------------------------------------
public void setBookClubDesc(final String bookClubDesc) {
  _bookClubDesc = bookClubDesc;
} // setBookClubDesc()
//--------------------------------------------------------------------------------------------------
public void setBookClubName(final String bookClubName) {
  _bookClubName = bookClubName.length() > 50 ? bookClubName.substring(0, 50) : bookClubName;
} // setBookClubName
//--------------------------------------------------------------------------------------------------
public void setId(final Integer id) {
  _id = id;
} // setId()
//--------------------------------------------------------------------------------------------------
public void setModifiedDateTime(final String modifiedDateTime) {
  _modifiedDateTime = modifiedDateTime;
} // setModifiedDateTime()
//--------------------------------------------------------------------------------------------------
public void setModifiedUserId(final String modifiedUserId) {
  _modifiedUserId = modifiedUserId;
} // setModifiedUserId()
//--------------------------------------------------------------------------------------------------
public void setVersion(final Integer version) {
  _version = version;
} // setVersion()
//--------------------------------------------------------------------------------------------------
@Override
public String toString() {
  return "Id:" + _id + " Version:" + _version + " Name:" + _bookClubName;
} // toString()
//--------------------------------------------------------------------------------------------------
}