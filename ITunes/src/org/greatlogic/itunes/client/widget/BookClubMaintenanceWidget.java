package org.greatlogic.itunes.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import org.greatlogic.itunes.client.IClientFactory;
import org.greatlogic.itunes.client.cache.BookClubCache.EUpdateType;
import org.greatlogic.itunes.client.event.BookClubNameChangeEvent;
import org.greatlogic.itunes.client.model.IBookClubProxy;
import org.greatlogic.itunes.shared.IBookClubRequestContext;

public class BookClubMaintenanceWidget extends Composite implements Editor<IBookClubProxy> {
//--------------------------------------------------------------------------------------------------
@UiField
TextBox                         bookClubDesc;
@UiField
TextBox                         bookClubName;

private IBookClubProxy          _bookClub;              // the current BookClub that is being edited
private IBookClubEditorDriver   _bookClubEditorDriver;
private IBookClubRequestContext _bookClubRequestContext;
private IClientFactory          _clientFactory;
private IBookClubProxy          _originalBookClub;      // used if there is a request to undo changes
private EUpdateType             _updateType;
//==================================================================================================
interface IBookClubEditorDriver extends RequestFactoryEditorDriver<IBookClubProxy, //
                               BookClubMaintenanceWidget> { //
} // interface IBookClubEditorDriver
//==================================================================================================
interface IBookClubMaintenanceWidgetBinder extends UiBinder<Widget, BookClubMaintenanceWidget> { //
} // interface IBookClubMaintenanceWidgetBinder
//==================================================================================================
public BookClubMaintenanceWidget(final IClientFactory clientFactory) {
  _clientFactory = clientFactory;
  IBookClubMaintenanceWidgetBinder binder = GWT.create(IBookClubMaintenanceWidgetBinder.class);
  initWidget(binder.createAndBindUi(this));
  _bookClubEditorDriver = GWT.create(IBookClubEditorDriver.class);
  _bookClubEditorDriver.initialize(_clientFactory.getRequestFactory().getEventBus(),
                                   _clientFactory.getRequestFactory(), this);
  editBookClub(null);
} // BookClubMaintenanceWidget()
//--------------------------------------------------------------------------------------------------
public void editBookClub(final IBookClubProxy bookClub) {
  _originalBookClub = bookClub;
  _updateType = bookClub == null ? EUpdateType.Insert : EUpdateType.Update;
  _bookClubRequestContext = _clientFactory.getRequestFactory().newBookClubRequestContext();
  if (_updateType == EUpdateType.Insert) {
    _bookClub = _clientFactory.getBookClubCache().newBookClub(_bookClubRequestContext);
    _bookClubEditorDriver.edit(_bookClub, _bookClubRequestContext);
    bookClubName.setFocus(true);
  }
  else {
    _bookClub = _bookClubRequestContext.edit(bookClub);
    _bookClubEditorDriver.edit(_bookClub, _bookClubRequestContext);
  }
} // editBookClub()
//--------------------------------------------------------------------------------------------------
public IBookClubProxy getBookClub() {
  return _bookClub;
} // getBookClub()
//--------------------------------------------------------------------------------------------------
public boolean isChanged() {
  return _bookClub != null && _bookClubRequestContext != null &&
         (_bookClubEditorDriver.isDirty() || _bookClubRequestContext.isChanged()) &&
         _bookClub.getBookClubName() != null && _bookClub.getBookClubName().trim().length() > 0;
} // isChanged()
//--------------------------------------------------------------------------------------------------
@SuppressWarnings("unused")
@UiHandler("bookClubName")
public void onNameKeyUp(final KeyUpEvent keyUpEvent) {
  if (_updateType == EUpdateType.Update) {
    _clientFactory.getEventBus().fireEvent(new BookClubNameChangeEvent(_bookClub,
                                                                       bookClubName.getText()));
  }
} // onNameKeyUp()
//--------------------------------------------------------------------------------------------------
public void saveCurrentChanges() {
  if (isChanged()) {
    _bookClubEditorDriver.flush();
    _clientFactory.getBookClubCache().saveBookClub(_bookClubRequestContext, _bookClub, _updateType);
    editBookClub(_bookClub);
  }
} // saveCurrentChanges()
//--------------------------------------------------------------------------------------------------
public void undoCurrentChanges() {
  if (isChanged()) {
    editBookClub(_originalBookClub);
    if (_updateType == EUpdateType.Update) {
      _clientFactory.getEventBus().fireEvent(new BookClubNameChangeEvent(_bookClub,
                                                                         bookClubName.getText()));
    }
  }
} // undoCurrentChanges()
//--------------------------------------------------------------------------------------------------
}