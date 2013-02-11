package org.greatlogic.itunes.server.model.dao;

import java.util.List;
import org.greatlogic.itunes.server.ITunesServerEnums.BookClubCol;
import org.greatlogic.itunes.server.ITunesServerEnums.EItunesTable;
import org.greatlogic.itunes.server.model.dto.BookClub;
import com.google.common.collect.Lists;
import com.greatlogic.glbase.gldb.GLDBEnums.EGLDBOp;
import com.greatlogic.glbase.gldb.GLDBException;
import com.greatlogic.glbase.gldb.GLSQL;
import com.greatlogic.glbase.gllib.GLLibEnums.EGLLogLevel;
import com.greatlogic.glbase.gllib.GLLog;
import com.greatlogic.glbase.gllib.GLUtil;

public class BookClubDAO implements IBookClubDAO {
//--------------------------------------------------------------------------------------------------
private static BookClubDAO _bookClubDAO;
//--------------------------------------------------------------------------------------------------
static BookClubDAO getInstance() {
  if (_bookClubDAO == null) {
    _bookClubDAO = new BookClubDAO();
  }
  return _bookClubDAO;
} // getInstance()
//--------------------------------------------------------------------------------------------------
private BookClubDAO() {
  if (bookClubTableIsEmpty()) {
    createTestData();
  }
} // BookClubDAO()
//--------------------------------------------------------------------------------------------------
private boolean bookClubTableIsEmpty() {
  boolean result = false;
  try {
    GLSQL bookClubSQL = GLSQL.select();
    bookClubSQL.from(EItunesTable.BookClub);
    bookClubSQL.open();
    try {
      result = !bookClubSQL.next();
    }
    finally {
      bookClubSQL.close();
    }
  }
  catch (GLDBException dbe) {
    GLLog.major("Error attempting to select from the BookClub table", dbe);
  }
  return result;
} // bookClubTableIsEmpty()
//--------------------------------------------------------------------------------------------------
private void createTestData() {
  try {
    TestBookClubDAO testBookClubDAO = TestBookClubDAO.getInstance();
    for (BookClub bookClub : testBookClubDAO.selectAllBookClubs()) {
      GLSQL bookClubSQL = GLSQL.insert(EItunesTable.BookClub, false);
      bookClubSQL.setValue(BookClubCol.Id, bookClub.getId());
      bookClubSQL.setValue(BookClubCol.BookClubDesc, bookClub.getBookClubDesc());
      bookClubSQL.setValue(BookClubCol.BookClubName, bookClub.getBookClubName());
      bookClubSQL.setValue(BookClubCol.ModifiedDateTime, bookClub.getModifiedDateTime());
      bookClubSQL.setValue(BookClubCol.ModifiedUserId, bookClub.getModifiedUserId());
      bookClubSQL.setValue(BookClubCol.Version, bookClub.getVersion());
      bookClubSQL.execute();
    }
  }
  catch (GLDBException dbe) {
    GLLog.major("Error attempting to insert book club sample data", dbe);
  }
} // createTestData()
//--------------------------------------------------------------------------------------------------
@Override
public BookClub findById(final Integer id) {
  BookClub result = null;
  try {
    GLSQL bookClubSQL = GLSQL.select();
    bookClubSQL.from(EItunesTable.BookClub);
    bookClubSQL.whereAnd(BookClubCol.Id, EGLDBOp.Equals, id);
    bookClubSQL.open();
    try {
      if (bookClubSQL.next()) {
        result = new BookClub(bookClubSQL);
      }
    }
    finally {
      bookClubSQL.close();
    }
  }
  catch (GLDBException dbe) {
    GLLog.major("Error attempting to get book club id:" + id, dbe);
  }
  return result;
} // findById()
//--------------------------------------------------------------------------------------------------
@Override
public void save(final BookClub bookClub) {
  try {
    GLSQL bookClubSQL = GLSQL.update(EItunesTable.BookClub);
    bookClubSQL.setValue(BookClubCol.BookClubDesc, bookClub.getBookClubDesc());
    bookClubSQL.setValue(BookClubCol.BookClubName, bookClub.getBookClubName());
    bookClubSQL.setValue(BookClubCol.ModifiedDateTime, GLUtil.currentTimeYYYYMMDDHHMMSS());
    bookClubSQL.setValue(BookClubCol.ModifiedUserId, bookClub.getModifiedUserId());
    bookClubSQL.setValue(BookClubCol.Version, bookClub.getVersion());
    bookClubSQL.whereAnd(BookClubCol.Id, EGLDBOp.Equals, bookClub.getId());
    bookClubSQL.execute();
  }
  catch (GLDBException dbe) {
    GLLog.major("Update failed for BookClub:" + bookClub, dbe);
  }
} // save()
//--------------------------------------------------------------------------------------------------
@Override
public List<BookClub> selectAllBookClubs() {
  List<BookClub> result = Lists.newArrayListWithExpectedSize(100);
  EGLLogLevel saveLogLevel = GLLog.setThreadLogLevel(EGLLogLevel.Minor);
  try {
    GLSQL bookClubSQL = GLSQL.select();
    bookClubSQL.from(EItunesTable.BookClub);
    bookClubSQL.orderBy(EItunesTable.BookClub, BookClubCol.BookClubName, true);
    bookClubSQL.open();
    try {
      while (bookClubSQL.next()) {
        result.add(new BookClub(bookClubSQL));
      }
    }
    finally {
      bookClubSQL.close();
    }
  }
  catch (GLDBException dbe) {
    GLLog.major("Error attempting to get book clubs", dbe);
  }
  finally {
    GLLog.setThreadLogLevel(saveLogLevel);
  }
  return result;
} // selectAllBookClubs()
//--------------------------------------------------------------------------------------------------
}