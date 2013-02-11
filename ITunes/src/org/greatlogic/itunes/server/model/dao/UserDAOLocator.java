package org.greatlogic.itunes.server.model.dao;

import com.google.web.bindery.requestfactory.shared.ServiceLocator;

public class UserDAOLocator implements ServiceLocator {
//--------------------------------------------------------------------------------------------------
public static IUserDAO getUserDAO() {
  return UserDAO.getInstance();
} // getUserDAO()
//--------------------------------------------------------------------------------------------------
@Override
public IUserDAO getInstance(final Class<?> clazz) {
  return getUserDAO();
} // getInstance()
//--------------------------------------------------------------------------------------------------
}