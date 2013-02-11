package org.greatlogic.itunes.client.event;

import org.greatlogic.itunes.client.event.BookClubSelectedEvent.IBookClubSelectedEventHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.web.bindery.event.shared.Event;

public class BookClubSelectedEvent extends Event<IBookClubSelectedEventHandler> {
//--------------------------------------------------------------------------------------------------
public static final Type<IBookClubSelectedEventHandler> BookClubSelectedEventType = new Type<IBookClubSelectedEventHandler>();

private int                                             _bookClubId;
//==================================================================================================
public interface IBookClubSelectedEventHandler extends EventHandler {
public void onBookClubSelectedEvent(final BookClubSelectedEvent bookClubSelectedEvent);
} // interface IBookClubSelectedEventHandler
//==================================================================================================
public BookClubSelectedEvent(final int bookClubId) {
  _bookClubId = bookClubId;
} // BookClubSelectedEvent()
//--------------------------------------------------------------------------------------------------
@Override
protected void dispatch(final IBookClubSelectedEventHandler handler) {
  handler.onBookClubSelectedEvent(this);
} // dispatch()
//--------------------------------------------------------------------------------------------------
@Override
public Type<IBookClubSelectedEventHandler> getAssociatedType() {
  return BookClubSelectedEventType;
} // getAssociatedType()
//--------------------------------------------------------------------------------------------------
public int getBookClubId() {
  return _bookClubId;
} // getBookClubId()
//--------------------------------------------------------------------------------------------------
@Override
public String toString() {
  return "BookClubSelectedEvent - BookClubId:" + _bookClubId;
} // toString()
//--------------------------------------------------------------------------------------------------
}