package org.greatlogic.itunes.client;

import org.greatlogic.itunes.shared.IBookClubRequestContext;
import org.greatlogic.itunes.shared.IUserRequestContext;
import com.google.web.bindery.requestfactory.shared.RequestFactory;

public interface IRequestFactory extends RequestFactory {
//--------------------------------------------------------------------------------------------------
IBookClubRequestContext newBookClubRequestContext();
IUserRequestContext newUserRequestContext();
//--------------------------------------------------------------------------------------------------
}