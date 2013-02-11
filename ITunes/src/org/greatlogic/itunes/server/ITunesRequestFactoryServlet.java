package org.greatlogic.itunes.server;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.greatlogic.itunes.server.model.dto.User;
import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;

@SuppressWarnings("serial")
public class ITunesRequestFactoryServlet extends RequestFactoryServlet {
//--------------------------------------------------------------------------------------------------
@Override
protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
  throws IOException, ServletException {
  if (!userIsLoggedIn(request)) {
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
  }
  else {
    super.doPost(request, response);
  }
} // doPost()
//--------------------------------------------------------------------------------------------------
private boolean userIsLoggedIn(final HttpServletRequest request) {
  boolean result = false;
  HttpSession session = request.getSession();
  if (session != null) {
    User user = (User)session.getAttribute("User");
    result = user != null;
  }
  return result;
} // userIsLoggedIn()
//--------------------------------------------------------------------------------------------------
}