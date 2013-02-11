package org.greatlogic.itunes.client.event;

import org.greatlogic.itunes.client.event.BookClubsLoadedEvent.IBookClubsLoadedEventHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.web.bindery.event.shared.Event;

public class BookClubsLoadedEvent extends Event<IBookClubsLoadedEventHandler> {
//--------------------------------------------------------------------------------------------------
public static final Type<IBookClubsLoadedEventHandler> BookClubsLoadedEventType = new Type<IBookClubsLoadedEventHandler>();
//==================================================================================================
public interface IBookClubsLoadedEventHandler extends EventHandler {
public void onBookClubsLoadedEvent(final BookClubsLoadedEvent bookClubsLoadedEvent);
} // interface IBookClubsLoadedEventHandler
//==================================================================================================
@Override
protected void dispatch(final IBookClubsLoadedEventHandler handler) {
  handler.onBookClubsLoadedEvent(this);
} // dispatch()
//--------------------------------------------------------------------------------------------------
@Override
public Type<IBookClubsLoadedEventHandler> getAssociatedType() {
  return BookClubsLoadedEventType;
} // getAssociatedType()
//--------------------------------------------------------------------------------------------------
}