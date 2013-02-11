package org.greatlogic.itunes.client.event;

import org.greatlogic.itunes.client.event.BookClubNameChangeEvent.IBookClubNameChangeEventHandler;
import org.greatlogic.itunes.client.model.IBookClubProxy;
import com.google.gwt.event.shared.EventHandler;
import com.google.web.bindery.event.shared.Event;

public class BookClubNameChangeEvent extends Event<IBookClubNameChangeEventHandler> {
//--------------------------------------------------------------------------------------------------
public static final Type<IBookClubNameChangeEventHandler> BookClubNameChangeEventType = new Type<IBookClubNameChangeEventHandler>();

private IBookClubProxy                                    _bookClub;
private String                                            _newName;
//==================================================================================================
public interface IBookClubNameChangeEventHandler extends EventHandler {
public void onBookClubNameChangeEvent(final BookClubNameChangeEvent bookClubNameChangeEvent);
} // interface IBookClubNameChangeEventHandler
//==================================================================================================
public BookClubNameChangeEvent(final IBookClubProxy bookClub, final String newName) {
  _bookClub = bookClub;
  _newName = newName;
} // BookClubNameChangeEvent()
//--------------------------------------------------------------------------------------------------
@Override
protected void dispatch(final IBookClubNameChangeEventHandler handler) {
  handler.onBookClubNameChangeEvent(this);
} // dispatch()
//--------------------------------------------------------------------------------------------------
@Override
public Type<IBookClubNameChangeEventHandler> getAssociatedType() {
  return BookClubNameChangeEventType;
} // getAssociatedType()
//--------------------------------------------------------------------------------------------------
public IBookClubProxy getBookClub() {
  return _bookClub;
} // getBookClub()
//--------------------------------------------------------------------------------------------------
public String getNewName() {
  return _newName;
} // getNewName()
//--------------------------------------------------------------------------------------------------
}