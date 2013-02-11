package org.greatlogic.itunes.client;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.web.bindery.requestfactory.gwt.client.DefaultRequestTransport;
/**
 * Every request factory transmission will pass through the single instance of this class. This can
 * be used to ensure that when a response is received any global conditions (e.g., the user is no
 * longer logged in) can be handled in a consistent manner.
 */
public class ITunesRequestTransport extends DefaultRequestTransport {
//--------------------------------------------------------------------------------------------------
private IClientFactory         _clientFactory;
private RequestFactoryResender _requestFactoryResender;
//==================================================================================================
private final class ITunesRequestCallback implements RequestCallback {
private RequestCallback _requestCallback;
private ITunesRequestCallback(final RequestCallback requestCallback) {
  _requestCallback = requestCallback;
} // ITunesRequestCallback()
@Override
public void onError(final Request request, final Throwable exception) {
  _requestCallback.onError(request, exception);
} // onError()
@Override
public void onResponseReceived(final Request request, final Response response) {
  if (response.getStatusCode() == Response.SC_UNAUTHORIZED) {
    _clientFactory.login();
  }
  else {
    _requestCallback.onResponseReceived(request, response);
  }
} // onResponseReceived()
} // class ITunesRequestCallback
//==================================================================================================
@Override
protected void configureRequestBuilder(final RequestBuilder builder) {
  super.configureRequestBuilder(builder);
} // configureRequestBuilder()
//--------------------------------------------------------------------------------------------------
@Override
protected RequestCallback createRequestCallback(final TransportReceiver receiver) {
  return new ITunesRequestCallback(super.createRequestCallback(receiver));
} // createRequestCallback()
//--------------------------------------------------------------------------------------------------
void initialize(final IClientFactory clientFactory) {
  _clientFactory = clientFactory;
  _requestFactoryResender = new RequestFactoryResender(_clientFactory);
  _clientFactory.setRequestFactoryResender(_requestFactoryResender);
} // initialize()
//--------------------------------------------------------------------------------------------------
/**
 * Before sending the request to the actual RequestTransport potentially replace the payload and
 * receiver with the payload and receiver from the previous request.
 */
@Override
public void send(final String payload, final TransportReceiver receiver) {
  String actualPayload = _requestFactoryResender.getPayload(payload);
  TransportReceiver actualReceiver = _requestFactoryResender.getReceiver(receiver);
  super.send(actualPayload, actualReceiver);
  _requestFactoryResender.clearLastRequest();
} // send()
//--------------------------------------------------------------------------------------------------
}