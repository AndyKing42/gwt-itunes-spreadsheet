package org.greatlogic.itunes.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ResizeLayoutPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import org.greatlogic.itunes.client.IClientFactory;
import org.greatlogic.itunes.client.event.BookClubListPositionChangedEvent;
import org.greatlogic.itunes.client.event.BookClubNameChangeEvent;
import org.greatlogic.itunes.client.event.BookClubSelectedEvent;
import org.greatlogic.itunes.client.event.BookClubsLoadedEvent;
import org.greatlogic.itunes.client.event.BookClubListPositionChangedEvent.IBookClubListPositionChangedEventHandler;
import org.greatlogic.itunes.client.event.BookClubNameChangeEvent.IBookClubNameChangeEventHandler;
import org.greatlogic.itunes.client.event.BookClubSelectedEvent.IBookClubSelectedEventHandler;
import org.greatlogic.itunes.client.event.BookClubsLoadedEvent.IBookClubsLoadedEventHandler;
import org.greatlogic.itunes.client.model.IBookClubProxy;
import org.greatlogic.itunes.client.widget.BookClubMaintenanceWidget;

public class BookClubMaintenanceView extends Composite implements
                                                      IBookClubListPositionChangedEventHandler,
                                                      IBookClubNameChangeEventHandler,
                                                      IBookClubSelectedEventHandler,
                                                      IBookClubsLoadedEventHandler {
//--------------------------------------------------------------------------------------------------
@UiField
ListBox                   bookClubListBox;
@UiField
SplitLayoutPanel          bookClubMaintenancePanel;
@UiField(provided = true)
BookClubMaintenanceWidget bookClubMaintenanceWidget;
@UiField
ResizeLayoutPanel         topLevelPanel;

private IClientFactory    _clientFactory;
//==================================================================================================
public interface IBookClubMaintenanceViewUiBinder extends UiBinder<Widget, BookClubMaintenanceView> { //
} // interface IBookClubMaintenanceViewUiBinder
//==================================================================================================
public ResizeLayoutPanel getTopLevelPanel() {
  return topLevelPanel;
} // getTopLevelPanel()
//--------------------------------------------------------------------------------------------------
public void initialize(final IClientFactory clientFactory) {
  _clientFactory = clientFactory;
  bookClubMaintenanceWidget = new BookClubMaintenanceWidget(_clientFactory);
  IBookClubMaintenanceViewUiBinder binder = GWT.create(IBookClubMaintenanceViewUiBinder.class);
  binder.createAndBindUi(this);
  bookClubMaintenancePanel.setWidgetToggleDisplayAllowed(bookClubListBox, true);
  _clientFactory.getRequestFactory().getEventBus().addHandler(BookClubListPositionChangedEvent.BookClubListPositionChangedEventType,
                                                              this);
  _clientFactory.getRequestFactory().getEventBus().addHandler(BookClubNameChangeEvent.BookClubNameChangeEventType,
                                                              this);
  _clientFactory.getRequestFactory().getEventBus().addHandler(BookClubSelectedEvent.BookClubSelectedEventType,
                                                              this);
  _clientFactory.getRequestFactory().getEventBus().addHandler(BookClubsLoadedEvent.BookClubsLoadedEventType,
                                                              this);
  _clientFactory.getBookClubCache().loadAllBookClubs();
} // initialize()
//--------------------------------------------------------------------------------------------------
@SuppressWarnings("unused")
@UiHandler("newButton")
public void onNewButtonClick(final ClickEvent clickEvent) {
  requestConfirmationToSaveCurrentChanges();
  bookClubMaintenanceWidget.editBookClub(null);
  //  _handlerRegistration.removeHandler();
} // onNewButtonClick()
//--------------------------------------------------------------------------------------------------
@Override
public void onBookClubListPositionChangedEvent(final BookClubListPositionChangedEvent bookClubListPositionChangedEvent) {
  IBookClubProxy bookClub = bookClubListPositionChangedEvent.getBookClub();
  if (bookClubListPositionChangedEvent.getRemoveIndex() >= 0) {
    bookClubListBox.removeItem(bookClubListPositionChangedEvent.getRemoveIndex());
  }
  if (bookClubListPositionChangedEvent.getInsertIndex() >= 0) {
    bookClubListBox.insertItem(bookClub.getBookClubName(), Integer.toString(bookClub.getId()),
                               bookClubListPositionChangedEvent.getInsertIndex());
  }
} // onBookClubListPositionChangedEvent()
//--------------------------------------------------------------------------------------------------
@SuppressWarnings("unused")
@UiHandler("bookClubListBox")
public void onBookClubListBoxChange(final ChangeEvent event) {
  int selectedIndex = bookClubListBox.getSelectedIndex();
  if (selectedIndex >= 0) {
    int bookClubId = Integer.parseInt(bookClubListBox.getValue(selectedIndex));
    _clientFactory.getEventBus().fireEvent(new BookClubSelectedEvent(bookClubId));
  }
} // onBookClubListBoxChange()
//--------------------------------------------------------------------------------------------------
@Override
public void onBookClubNameChangeEvent(final BookClubNameChangeEvent bookClubNameChangeEvent) {
  for (int listBoxIndex = 0; listBoxIndex < bookClubListBox.getItemCount(); ++listBoxIndex) {
    if (Integer.valueOf(bookClubListBox.getValue(listBoxIndex)) == bookClubNameChangeEvent.getBookClub().getId()) {
      bookClubListBox.setItemText(listBoxIndex, bookClubNameChangeEvent.getNewName());
      break;
    }
  }
} // onBookClubNameChangeEvent()
//--------------------------------------------------------------------------------------------------
/**
 * Loads the selected BookClub into the editor.
 */
@Override
public void onBookClubSelectedEvent(final BookClubSelectedEvent bookClubSelectedEvent) {
  requestConfirmationToSaveCurrentChanges();
  IBookClubProxy bookClub = _clientFactory.getBookClubCache().getBookClub(bookClubSelectedEvent.getBookClubId());
  bookClubMaintenanceWidget.editBookClub(bookClub);
} // onBookClubSelectedEvent()
//--------------------------------------------------------------------------------------------------
@Override
public void onBookClubsLoadedEvent(final BookClubsLoadedEvent bookClubsLoadedEvent) {
  bookClubListBox.clear();
  for (IBookClubProxy bookClub : _clientFactory.getBookClubCache().getBookClubList()) {
    bookClubListBox.addItem(bookClub.getBookClubName(), Integer.toString(bookClub.getId()));
  }
} // onBookClubsLoadedEvent()
//--------------------------------------------------------------------------------------------------
@SuppressWarnings("unused")
@UiHandler("saveButton")
public void onSaveButtonClick(final ClickEvent clickEvent) {
  if (bookClubMaintenanceWidget.getBookClub() != null) {
    bookClubMaintenanceWidget.saveCurrentChanges();
  }
} // onSaveButtonClick()
//--------------------------------------------------------------------------------------------------
@SuppressWarnings("unused")
@UiHandler("undoButton")
public void onUndoButtonClick(final ClickEvent clickEvent) {
  bookClubMaintenanceWidget.undoCurrentChanges();
} // onUndoButtonClick()
//--------------------------------------------------------------------------------------------------
private void requestConfirmationToSaveCurrentChanges() {
  if (bookClubMaintenanceWidget.isChanged()) {
    if (Window.confirm("Do you want to save the current changes?")) {
      bookClubMaintenanceWidget.saveCurrentChanges();
    }
    else {
      bookClubMaintenanceWidget.undoCurrentChanges();
    }
  }
} // requestConfirmationToSaveCurrentChanges()
//--------------------------------------------------------------------------------------------------
}