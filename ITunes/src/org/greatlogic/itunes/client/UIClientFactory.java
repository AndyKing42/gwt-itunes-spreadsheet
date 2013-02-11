package org.greatlogic.itunes.client;

import com.google.web.bindery.event.shared.EventBus;
import org.greatlogic.itunes.client.cache.BookClubCache;
import org.greatlogic.itunes.client.view.BookClubMaintenanceView;
import org.greatlogic.itunes.client.widget.LoginWidget;
import org.greatlogic.itunes.shared.IRemoteServiceAsync;

public class UIClientFactory implements IClientFactory {
//--------------------------------------------------------------------------------------------------
private BookClubCache           _bookClubCache;
private BookClubMaintenanceView _bookClubMaintenanceView;
private EventBus                _eventBus;
private LoginWidget             _loginWidget;
private IRemoteServiceAsync     _remoteServiceAsync;
private IRequestFactory         _requestFactory;
private RequestFactoryResender  _requestFactoryResender;
//--------------------------------------------------------------------------------------------------
@Override
public BookClubCache getBookClubCache() {
  return _bookClubCache;
} // getBookClubCache()
//--------------------------------------------------------------------------------------------------
@Override
public BookClubMaintenanceView getBookClubMaintenanceView() {
  if (_bookClubMaintenanceView == null) {
    _bookClubMaintenanceView = new BookClubMaintenanceView();
    _bookClubMaintenanceView.initialize(this);
  }
  return _bookClubMaintenanceView;
} // getBookClubMaintenanceView()
//--------------------------------------------------------------------------------------------------
@Override
public EventBus getEventBus() {
  return _eventBus;
} // getEventBus()
//--------------------------------------------------------------------------------------------------
@Override
public IRemoteServiceAsync getRemoteServiceAsync() {
  return _remoteServiceAsync;
} // getRemoteServiceAsync()
//--------------------------------------------------------------------------------------------------
@Override
public IRequestFactory getRequestFactory() {
  return _requestFactory;
} // getRequestFactory()
//--------------------------------------------------------------------------------------------------
@Override
public RequestFactoryResender getRequestFactoryResender() {
  return _requestFactoryResender;
} // getRequestFactoryResender()
//--------------------------------------------------------------------------------------------------
@Override
public void initialize(final EventBus eventBus, final IRemoteServiceAsync remoteServiceAsync,
                       final IRequestFactory requestFactory, final BookClubCache bookClubCache) {
  _eventBus = eventBus;
  _remoteServiceAsync = remoteServiceAsync;
  _requestFactory = requestFactory;
  ((ITunesRequestTransport)_requestFactory.getRequestTransport()).initialize(this);
  _bookClubCache = bookClubCache;
} // initialize()
//--------------------------------------------------------------------------------------------------
@Override
public void login() {
  if (_loginWidget == null) {
    _loginWidget = new LoginWidget(this);
  }
  _loginWidget.login();
} // login()
//--------------------------------------------------------------------------------------------------
@Override
public void setRequestFactoryResender(final RequestFactoryResender requestFactoryResender) {
  _requestFactoryResender = requestFactoryResender;
} // setRequestFactoryResender()
//--------------------------------------------------------------------------------------------------
}