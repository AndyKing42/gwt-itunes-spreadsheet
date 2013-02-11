package org.greatlogic.itunes.client.event;

import org.greatlogic.itunes.client.event.SaveBookClubEvent.ISaveBookClubEventHandler;
import org.greatlogic.itunes.client.model.IBookClubProxy;
import com.google.gwt.event.shared.EventHandler;
import com.google.web.bindery.event.shared.Event;

public class SaveBookClubEvent extends Event<ISaveBookClubEventHandler> {
//--------------------------------------------------------------------------------------------------
public static final Type<ISaveBookClubEventHandler> SaveBookClubEventType = new Type<ISaveBookClubEventHandler>();

private IBookClubProxy                              _bookClub;
//==================================================================================================
public interface ISaveBookClubEventHandler extends EventHandler {
public void onSaveBookClubEvent(final SaveBookClubEvent saveBookClubEvent);
} // interface ISaveBookClubEventHandler
//==================================================================================================
public SaveBookClubEvent(final IBookClubProxy bookClub) {
  _bookClub = bookClub;
} // SaveBookClubEvent()
//--------------------------------------------------------------------------------------------------
@Override
protected void dispatch(final ISaveBookClubEventHandler handler) {
  handler.onSaveBookClubEvent(this);
} // dispatch()
//--------------------------------------------------------------------------------------------------
@Override
public Type<ISaveBookClubEventHandler> getAssociatedType() {
  return SaveBookClubEventType;
} // getAssociatedType()
//--------------------------------------------------------------------------------------------------
public IBookClubProxy getBookClub() {
  return _bookClub;
} // getBookClub()
//--------------------------------------------------------------------------------------------------
}