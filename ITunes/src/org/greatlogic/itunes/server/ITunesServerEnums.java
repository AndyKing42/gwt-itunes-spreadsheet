package org.greatlogic.itunes.server;

import com.greatlogic.glbase.gldb.GLDBException;
import com.greatlogic.glbase.gldb.GLDBUtil;
import com.greatlogic.glbase.gldb.IGLColumn;
import com.greatlogic.glbase.gldb.IGLTable;
import com.greatlogic.glbase.glxml.IGLXMLAttributeEnum;

public class ITunesServerEnums {
//--------------------------------------------------------------------------------------------------
public enum EItunesConfigAttribute implements IGLXMLAttributeEnum {
ExternalIPCheckerEmailFrom,
ExternalIPCheckerEmailTo,
ExternalIPCheckerIntervalSeconds,
ExternalIPCheckerSaveToFilename
} // enum EItunesConfigAttribute
//--------------------------------------------------------------------------------------------------
public enum EItunesDBSequence {
UserId(1);
private int _id;
private EItunesDBSequence(final int id) {
  _id = id;
} // enum EItunesDBSequence
public int getId() {
  return _id;
} // getId()
public int getNextValue(final int numberOfValues) throws GLDBException {
  return (int)GLDBUtil.getNextSequenceValue(name(), EItunesTable.DBSequence,
                                            DBSequenceCol.NextValue, DBSequenceCol.Id + "=" + _id,
                                            numberOfValues);
} // getNextValue()
} // enum EItunesDBSequence
//--------------------------------------------------------------------------------------------------
public enum EItunesTable implements IGLTable {
BookClub(BookClubCol.class),
DBSequence(DBSequenceCol.class),
User(UserCol.class);
private Class<? extends Enum<?>> _columnEnumClass;
private EItunesTable(final Class<? extends Enum<?>> columnEnumClass) {
  _columnEnumClass = columnEnumClass;
  GLDBUtil.registerTable(this);
} // ECirrusTable()
@Override
public String getAbbrev() {
  return _columnEnumClass.getSimpleName();
} // getAbbrev()
@Override
public Class<? extends Enum<?>> getColumnEnumClass() {
  return _columnEnumClass;
} // getColumnEnumClass()
@Override
public String getDataSourceName() {
  return null;
} // getDataSourceName()
} // enum EItunesTable
//==================================================================================================
public enum BookClubCol implements IGLColumn {
BookClubDesc,
BookClubName,
Id,
ModifiedDateTime,
ModifiedUserId,
Version
} // enum BookClubCol
//--------------------------------------------------------------------------------------------------
public enum DBSequenceCol implements IGLColumn {
Id,
NextValue
} // enum DBSequenceCol
//--------------------------------------------------------------------------------------------------
public enum UserCol implements IGLColumn {
Id,
PasswordHash,
UserId,
Version
} // enum UserCol
//--------------------------------------------------------------------------------------------------
}