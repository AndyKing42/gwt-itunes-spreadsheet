package org.greatlogic.itunes.client.cache;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.greatlogic.itunes.client.IClientFactory;
import org.greatlogic.itunes.client.event.BookClubListPositionChangedEvent;
import org.greatlogic.itunes.client.event.BookClubsLoadedEvent;
import org.greatlogic.itunes.client.event.SaveBookClubEvent;
import org.greatlogic.itunes.client.event.SaveBookClubEvent.ISaveBookClubEventHandler;
import org.greatlogic.itunes.client.model.IBookClubProxy;
import org.greatlogic.itunes.shared.IBookClubRequestContext;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

public class BookClubCache implements ISaveBookClubEventHandler {
//--------------------------------------------------------------------------------------------------
protected List<IBookClubProxy>         _bookClubList;
protected Map<Integer, IBookClubProxy> _bookClubMap;  // id -> IBookClubProxy
protected IClientFactory               _clientFactory;
//--------------------------------------------------------------------------------------------------
public enum EUpdateType {
Delete,
Insert,
Update
} // enum EUpdateType
//--------------------------------------------------------------------------------------------------
IBookClubProxy createBookClub(final IBookClubRequestContext bookClubRequestContext) {
  IBookClubProxy result = bookClubRequestContext.create(IBookClubProxy.class);
  int id = Random.nextInt(10000);
  result.setId(id);
  result.setVersion(0);
  return result;
} // createBookClub()
//--------------------------------------------------------------------------------------------------
public IBookClubProxy getBookClub(final int bookClubId) {
  return _bookClubMap.get(bookClubId);
} // getBookClub()
//--------------------------------------------------------------------------------------------------
public List<IBookClubProxy> getBookClubList() {
  return _bookClubList;
} // getBookClubs()
//--------------------------------------------------------------------------------------------------
public void initialize(final IClientFactory clientFactory) {
  _clientFactory = clientFactory;
  _clientFactory.getEventBus().addHandler(SaveBookClubEvent.SaveBookClubEventType, this);
} // initialize()
//--------------------------------------------------------------------------------------------------
public void loadAllBookClubs() {
  _clientFactory.getRequestFactory().newBookClubRequestContext().selectAllBookClubs().fire( //
  new Receiver<List<IBookClubProxy>>() {
    @Override
    public void onFailure(final ServerFailure error) {
      Window.alert("Request failed:" + error.getMessage());
      super.onFailure(error);
    } // onFailure()
    @Override
    public void onSuccess(final List<IBookClubProxy> response) {
      loadBookClubsIntoCache(response);
    } // onSuccess()
  });
} // loadAllBookClubs()
//--------------------------------------------------------------------------------------------------
/**
 * Loads the BookClub entities into the cache. This will be invoked after the BookClub objects have
 * been retrieved, for example, after receiving them from the server if the BookClubCache is getting
 * data from the server, or after receiving them from locally generated test data if the program is
 * running in a test mode.
 */
protected void loadBookClubsIntoCache(final List<IBookClubProxy> bookClubList) {
  _bookClubList = bookClubList;
  _bookClubMap = new TreeMap<Integer, IBookClubProxy>();
  for (IBookClubProxy bookClub : _bookClubList) {
    _bookClubMap.put(bookClub.getId(), bookClub);
  }
  _clientFactory.getEventBus().fireEvent(new BookClubsLoadedEvent());
} // loadBookClubsIntoCache()
//--------------------------------------------------------------------------------------------------
public final IBookClubProxy newBookClub(final IBookClubRequestContext bookClubRequestContext) {
  return createBookClub(bookClubRequestContext);
} // newBookClub()
//--------------------------------------------------------------------------------------------------
@Override
public void onSaveBookClubEvent(final SaveBookClubEvent saveBookClubEvent) {
  IBookClubProxy bookClub = saveBookClubEvent.getBookClub();
  if (bookClub != null) {
    // update the cache
    // send update to server
    // if this is a new bookClub then fire the bookClub created event? (to update the list, for example)
  }
} // onSaveBookClubEvent()
//--------------------------------------------------------------------------------------------------
/**
 * Saves changes to a BookClub; the changes could be either updates to an existing BookClub, or
 * creation of a new BookClub.
 */
public final void saveBookClub(final IBookClubRequestContext requestContext,
                               final IBookClubProxy bookClub, final EUpdateType updateType) {
  int removeIndex = -1;
  if (updateType != EUpdateType.Insert) {
    for (int bookClubIndex = 0; bookClubIndex < _bookClubList.size() && removeIndex < 0; ++bookClubIndex) {
      if (_bookClubList.get(bookClubIndex).getId() == bookClub.getId()) {
        removeIndex = bookClubIndex;
      }
    }
    if (removeIndex >= 0) {
      _bookClubList.remove(removeIndex);
    }
  }
  int insertIndex = -1;
  if (updateType != EUpdateType.Delete) {
    for (insertIndex = 0; insertIndex < _bookClubList.size() &&
                          bookClub.getBookClubName().compareToIgnoreCase(_bookClubList.get(insertIndex).getBookClubName()) >= 0; ++insertIndex) {
      //
    }
    _bookClubList.add(insertIndex, bookClub);
    _bookClubMap.put(bookClub.getId(), bookClub);
  }
  if (insertIndex != removeIndex) {
    _clientFactory.getEventBus().fireEvent(new BookClubListPositionChangedEvent(bookClub,
                                                                                removeIndex,
                                                                                insertIndex));
  }
  bookClub.setVersion(bookClub.getVersion() + 1);
  requestContext.save(bookClub).fire();
} // saveBookClub()
//--------------------------------------------------------------------------------------------------
}