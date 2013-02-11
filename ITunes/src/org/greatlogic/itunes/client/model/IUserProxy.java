package org.greatlogic.itunes.client.model;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import org.greatlogic.itunes.server.model.dto.User;
import org.greatlogic.itunes.server.model.dto.UserLocator;

@ProxyFor(value = User.class, locator = UserLocator.class)
public interface IUserProxy extends EntityProxy {
//--------------------------------------------------------------------------------------------------
int getId();
String getUserId();
int getVersion();
//--------------------------------------------------------------------------------------------------
}