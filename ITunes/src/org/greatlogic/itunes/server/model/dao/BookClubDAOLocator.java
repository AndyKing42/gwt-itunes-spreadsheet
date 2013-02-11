package org.greatlogic.itunes.server.model.dao;

import com.google.web.bindery.requestfactory.shared.ServiceLocator;

public class BookClubDAOLocator implements ServiceLocator {
//--------------------------------------------------------------------------------------------------
public static IBookClubDAO getBookClubDAO() {
  return BookClubDAO.getInstance();
} // getBookClubDAO()
//--------------------------------------------------------------------------------------------------
@Override
public IBookClubDAO getInstance(final Class<?> clazz) {
  return getBookClubDAO();
} // getInstance()
//--------------------------------------------------------------------------------------------------
}