package org.greatlogic.itunes.client;

import org.greatlogic.itunes.shared.IUserRequestContext;
import com.google.web.bindery.requestfactory.shared.RequestTransport.TransportReceiver;
/**
 * This class is used to store information about the last request that was sent using request
 * factory. If there is a need to resend the request (for example, if the request failed because of
 * an issue on the server) then the "resend()" method can be used. There will (probably) be just one
 * instance of this class.
 */
public class RequestFactoryResender {
//--------------------------------------------------------------------------------------------------
private IClientFactory    _clientFactory;
private String            _payload;
private TransportReceiver _receiver;
private boolean           _resend;
//--------------------------------------------------------------------------------------------------
public void clearLastRequest() {
  _resend = false;
} // clearLastRequest()
//--------------------------------------------------------------------------------------------------
public String getPayload(final String payload) {
  String result;
  if (_resend && _payload != null) {
    result = _payload;
  }
  else {
    result = payload;
  }
  _payload = payload;
  return result;
} // getPayload()
//--------------------------------------------------------------------------------------------------
public TransportReceiver getReceiver(final TransportReceiver receiver) {
  TransportReceiver result;
  if (_resend && _receiver != null) {
    result = _receiver;
  }
  else {
    result = receiver;
  }
  _receiver = receiver;
  return result;
} // getReceiver()
//--------------------------------------------------------------------------------------------------
RequestFactoryResender(final IClientFactory clientFactory) {
  _clientFactory = clientFactory;
} // RequestFactoryResender()
//--------------------------------------------------------------------------------------------------
/**
 * Resends the most recent request factory request. This is achieved by firing any request ... it
 * doesn't matter which request is actually fired here because when the RequestTransport "send()"
 * is executed the payload and receiver will be replaced with the payload and receiver that were
 * used in the last request.
 */
public void resend() {
  _resend = true;
  IUserRequestContext _userRequestContext; // this could have been any request context
  _userRequestContext = _clientFactory.getRequestFactory().newUserRequestContext();
  _userRequestContext.save(null).fire(); // this could have been any request
} // resend()
//--------------------------------------------------------------------------------------------------
}