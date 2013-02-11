package org.greatlogic.itunes.server.model.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.greatlogic.itunes.server.ITunesServerEnums.UserCol;
import com.greatlogic.glbase.gldb.GLDBException;
import com.greatlogic.glbase.gldb.GLSQL;

@Entity
public class User {
//--------------------------------------------------------------------------------------------------
@Column(name = "Id", nullable = false, unique = true)
@Id
private Integer _id;

@Column(length = 200, name = "PasswordHash", nullable = false)
private String  _passwordHash;

@Column(length = 50, name = "UserId", nullable = false)
private String  _userId;

@Column(name = "Version", nullable = false)
private Integer _version;
//--------------------------------------------------------------------------------------------------
public User() {
  // used by the GWT request factory
} // User()
//--------------------------------------------------------------------------------------------------
public User(final GLSQL userSQL) throws GLDBException {
  this(userSQL.asInt(UserCol.Id), userSQL.asString(UserCol.PasswordHash),
       userSQL.asString(UserCol.UserId), userSQL.asInt(UserCol.Version));
} // User()
//--------------------------------------------------------------------------------------------------
public User(final int id, final String passwordHash, final String userId, final int version) {
  setId(id);
  setPasswordHash(passwordHash);
  setUserId(userId);
  setVersion(version);
} // User()
//--------------------------------------------------------------------------------------------------
public int getId() {
  return _id;
} // getId()
//--------------------------------------------------------------------------------------------------
public String getPasswordHash() {
  return _passwordHash;
} // getPasswordHash()
//--------------------------------------------------------------------------------------------------
public String getUserId() {
  return _userId;
} // getUserId()
//--------------------------------------------------------------------------------------------------
public int getVersion() {
  return _version;
} // getVersion()
//--------------------------------------------------------------------------------------------------
public void setId(final Integer id) {
  _id = id;
} // setId()
//--------------------------------------------------------------------------------------------------
public void setPasswordHash(final String passwordHash) {
  _passwordHash = passwordHash;
} // setPasswordHash()
//--------------------------------------------------------------------------------------------------
public void setUserId(final String userId) {
  _userId = userId;
} // setUserId()
//--------------------------------------------------------------------------------------------------
public void setVersion(final Integer version) {
  _version = version;
} // setVersion()
//--------------------------------------------------------------------------------------------------
@Override
public String toString() {
  return "Id:" + _id + " Version:" + _version + " UserId:" + _userId;
} // toString()
//--------------------------------------------------------------------------------------------------
}