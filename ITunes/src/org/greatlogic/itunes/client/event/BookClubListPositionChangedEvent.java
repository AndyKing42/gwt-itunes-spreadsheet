package org.greatlogic.itunes.client.event;

import org.greatlogic.itunes.client.event.BookClubListPositionChangedEvent.IBookClubListPositionChangedEventHandler;
import org.greatlogic.itunes.client.model.IBookClubProxy;
import com.google.gwt.event.shared.EventHandler;
import com.google.web.bindery.event.shared.Event;

public class BookClubListPositionChangedEvent extends Event<IBookClubListPositionChangedEventHandler> {
//--------------------------------------------------------------------------------------------------
public static final Type<IBookClubListPositionChangedEventHandler> BookClubListPositionChangedEventType = new Type<IBookClubListPositionChangedEventHandler>();

private IBookClubProxy                                             _bookClub;
private int                                                        _insertIndex;
private int                                                        _removeIndex;
//==================================================================================================
public interface IBookClubListPositionChangedEventHandler extends EventHandler {
public void onBookClubListPositionChangedEvent(final BookClubListPositionChangedEvent bookClubListPositionChangedEvent);
} // interface IBookClubListPositionChangedEventHandler
//==================================================================================================
public BookClubListPositionChangedEvent(final IBookClubProxy bookClub, final int removeIndex,
                                        final int insertIndex) {
  _bookClub = bookClub;
  _removeIndex = removeIndex;
  _insertIndex = insertIndex;
} // BookClubListPositionChangedEvent()
//--------------------------------------------------------------------------------------------------
@Override
protected void dispatch(final IBookClubListPositionChangedEventHandler handler) {
  handler.onBookClubListPositionChangedEvent(this);
} // dispatch()
//--------------------------------------------------------------------------------------------------
@Override
public Type<IBookClubListPositionChangedEventHandler> getAssociatedType() {
  return BookClubListPositionChangedEventType;
} // getAssociatedType()
//--------------------------------------------------------------------------------------------------
public IBookClubProxy getBookClub() {
  return _bookClub;
} // getBookClub()
//--------------------------------------------------------------------------------------------------
public int getInsertIndex() {
  return _insertIndex;
} // getInsertIndex()
//--------------------------------------------------------------------------------------------------
public int getRemoveIndex() {
  return _removeIndex;
} // getRemoveIndex()
//--------------------------------------------------------------------------------------------------
}