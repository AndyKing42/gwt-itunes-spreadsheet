package org.greatlogic.itunes.server.model.dao;

import com.google.common.collect.Lists;
import com.greatlogic.glbase.gldb.GLDBEnums.EGLDBOp;
import com.greatlogic.glbase.gldb.GLDBException;
import com.greatlogic.glbase.gldb.GLSQL;
import com.greatlogic.glbase.gllib.BCrypt;
import com.greatlogic.glbase.gllib.GLLibEnums.EGLLogLevel;
import com.greatlogic.glbase.gllib.GLLog;
import java.util.List;
import org.greatlogic.itunes.server.ITunesServerEnums.EItunesDBSequence;
import org.greatlogic.itunes.server.ITunesServerEnums.EItunesTable;
import org.greatlogic.itunes.server.ITunesServerEnums.UserCol;
import org.greatlogic.itunes.server.model.dto.User;

public class UserDAO implements IUserDAO {
//--------------------------------------------------------------------------------------------------
private static UserDAO _userDAO;
//--------------------------------------------------------------------------------------------------
static UserDAO getInstance() {
  if (_userDAO == null) {
    _userDAO = new UserDAO();
  }
  return _userDAO;
} // getInstance()
//--------------------------------------------------------------------------------------------------
@Override
public User findById(final Integer id) {
  User result = null;
  try {
    GLSQL userSQL = GLSQL.select();
    userSQL.from(EItunesTable.User);
    userSQL.whereAnd(UserCol.Id, EGLDBOp.Equals, id);
    userSQL.open();
    try {
      if (userSQL.next()) {
        result = new User(userSQL);
      }
    }
    finally {
      userSQL.close();
    }
  }
  catch (GLDBException dbe) {
    GLLog.major("Error attempting to get user id:" + id, dbe);
  }
  return result;
} // findById()
//--------------------------------------------------------------------------------------------------
@Override
public User findByUserIdAndPassword(final String userId, final String password) {
  User result = null;
  if (!password.contains("\\") && !password.contains("'")) {
    try {
      GLSQL userSQL = GLSQL.select();
      userSQL.from(EItunesTable.User);
      userSQL.whereAnd(UserCol.UserId, EGLDBOp.Equals, userId);
      userSQL.open();
      try {
        if (userSQL.next()) {
          String passwordHash = userSQL.asString(UserCol.PasswordHash);
          if ((password.isEmpty() && passwordHash.isEmpty()) ||
              (!password.isEmpty() && !passwordHash.isEmpty() && //
              BCrypt.checkpw(password, passwordHash))) {
            result = new User(userSQL);
          }
        }
      }
      finally {
        userSQL.close();
      }
    }
    catch (Exception e) {
      GLLog.major("Error attempting to get user for userId:" + userId, e);
    }
  }
  return result;
} // findByUserIdAndPassword()
//--------------------------------------------------------------------------------------------------
/**
 * Saves a user.
 * @return For a new user this will be a new id; for an existing user this will be the same as the
 * userId that was in the supplied User object.
 */
@Override
public Integer save(final User user) {
  int result = user.getId();
  try {
    GLSQL userSQL;
    if (user.getId() == 0) {
      result = EItunesDBSequence.UserId.getNextValue(1);
      userSQL = GLSQL.insert(EItunesTable.User, false);
      userSQL.setValue(UserCol.Id, result);
      userSQL.setValue(UserCol.PasswordHash, BCrypt.hashpw(user.getPasswordHash(), BCrypt.gensalt()));
    }
    else {
      userSQL = GLSQL.update(EItunesTable.User);
      userSQL.whereAnd(UserCol.Id, EGLDBOp.Equals, result);
    }
    userSQL.setValue(UserCol.UserId, user.getUserId());
    userSQL.setValue(UserCol.Version, user.getVersion());
    userSQL.execute();
  }
  catch (GLDBException dbe) {
    GLLog.major("Update failed for user:" + user, dbe);
  }
  return result;
} // save()
//--------------------------------------------------------------------------------------------------
@Override
public List<User> selectAllUsers() {
  List<User> result = Lists.newArrayListWithExpectedSize(100);
  EGLLogLevel saveLogLevel = GLLog.setThreadLogLevel(EGLLogLevel.Minor);
  try {
    GLSQL userSQL = GLSQL.select();
    userSQL.from(EItunesTable.User);
    userSQL.open();
    try {
      while (userSQL.next()) {
        result.add(new User(userSQL));
      }
    }
    finally {
      userSQL.close();
    }
  }
  catch (GLDBException dbe) {
    GLLog.major("Error attempting to get users", dbe);
  }
  finally {
    GLLog.setThreadLogLevel(saveLogLevel);
  }
  return result;
} // selectAllUsers()
//--------------------------------------------------------------------------------------------------
}