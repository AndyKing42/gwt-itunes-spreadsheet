package org.greatlogic.itunes.client.widget;

import org.greatlogic.itunes.client.IClientFactory;
import org.greatlogic.itunes.shared.IRemoteServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class LoginWidget extends PopupPanel {
//--------------------------------------------------------------------------------------------------
@UiField
Button                 okButton;
@UiField
PasswordTextBox        passwordTextBox;
@UiField
TextBox                userIdTextBox;

private IClientFactory _clientFactory;
//==================================================================================================
interface ILoginWidgetBinder extends UiBinder<Widget, LoginWidget> { //
} // interface ILoginWidgetBinder
//==================================================================================================
public LoginWidget(final IClientFactory clientFactory) {
  _clientFactory = clientFactory;
  ILoginWidgetBinder binder = GWT.create(ILoginWidgetBinder.class);
  setWidget(binder.createAndBindUi(this));
} // LoginWidget()
//--------------------------------------------------------------------------------------------------
public void login() {
  setGlassEnabled(true);
  show();
  center();
} // login()
//--------------------------------------------------------------------------------------------------
@SuppressWarnings("unused")
@UiHandler("okButton")
public void onOKButtonClick(final ClickEvent clickEvent) {
  IRemoteServiceAsync remoteService = _clientFactory.getRemoteServiceAsync();
  remoteService.login( //
  userIdTextBox.getText(), passwordTextBox.getText(), new AsyncCallback<Integer>() {
    @Override
    public void onFailure(final Throwable caught) {} // onFailure()
    @Override
    public void onSuccess(final Integer result) {
      if (result > 0) {
        hide();
        _clientFactory.getRequestFactoryResender().resend();
      }
    } // onSuccess()
  });
} // onOKButtonClick()
//--------------------------------------------------------------------------------------------------
}