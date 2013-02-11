package org.greatlogic.itunes.client;

import org.greatlogic.itunes.client.cache.BookClubCache;
import org.greatlogic.itunes.client.view.BookClubMaintenanceView;
import org.greatlogic.itunes.shared.IRemoteService;
import org.greatlogic.itunes.shared.IRemoteServiceAsync;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class ITunes implements EntryPoint {
//--------------------------------------------------------------------------------------------------
@Override
public void onModuleLoad() {
  SimpleEventBus eventBus = new SimpleEventBus();
  IRemoteServiceAsync remoteServiceAsync = (IRemoteServiceAsync)GWT.create(IRemoteService.class);
  IRequestFactory requestFactory = GWT.create(IRequestFactory.class);
  requestFactory.initialize(eventBus, new ITunesRequestTransport());
  BookClubCache bookClubCache = new BookClubCache();
  IClientFactory clientFactory = GWT.create(UIClientFactory.class);
  clientFactory.initialize(eventBus, remoteServiceAsync, requestFactory, bookClubCache);
  bookClubCache.initialize(clientFactory);
  BookClubMaintenanceView bookClubMaintenanceView = new BookClubMaintenanceView();
  bookClubMaintenanceView.initialize(clientFactory);
  RootLayoutPanel.get().add(bookClubMaintenanceView.getTopLevelPanel());
} // onModuleLoad()
//--------------------------------------------------------------------------------------------------
}