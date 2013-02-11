package org.greatlogic.itunes.server.model.dto;

import org.greatlogic.itunes.server.model.dao.UserDAOLocator;
import com.google.web.bindery.requestfactory.shared.Locator;

public class UserLocator extends Locator<User, Integer> {
//--------------------------------------------------------------------------------------------------
@Override
public User create(final Class<? extends User> clazz) {
  return new User();
} // create()
//--------------------------------------------------------------------------------------------------
@Override
public User find(final Class<? extends User> clazz, final Integer id) {
  return UserDAOLocator.getUserDAO().findById(id);
} // find()
//--------------------------------------------------------------------------------------------------
@Override
public Class<User> getDomainType() {
  return User.class;
} // getDomainType()
//--------------------------------------------------------------------------------------------------
@Override
public Integer getId(final User domainObject) {
  return domainObject.getId();
} // getId()
//--------------------------------------------------------------------------------------------------
@Override
public Class<Integer> getIdType() {
  return Integer.class;
} // getIdType()
//--------------------------------------------------------------------------------------------------
@Override
public Object getVersion(final User domainObject) {
  return domainObject.getVersion();
} // getVersion()
//--------------------------------------------------------------------------------------------------
@Override
public boolean isLive(final User domainObject) {
  return true;
} // isLive()
//--------------------------------------------------------------------------------------------------
}