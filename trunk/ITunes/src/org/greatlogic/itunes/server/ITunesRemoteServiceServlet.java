package org.greatlogic.itunes.server;

import javax.servlet.http.HttpSession;
import org.greatlogic.itunes.server.model.dao.UserDAOLocator;
import org.greatlogic.itunes.server.model.dto.User;
import org.greatlogic.itunes.shared.IRemoteService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.greatlogic.glbase.gllib.GLLog;

@SuppressWarnings("serial")
public class ITunesRemoteServiceServlet extends RemoteServiceServlet implements IRemoteService {
//--------------------------------------------------------------------------------------------------
/**
 * Attempts to log in using the supplied user id and password.
 * @param userId The user id that will be used for the login attempt.
 * @param password The password that will be used for the login attempt (this is the plain text
 * password, not the encrypted hash value).
 * @return The id of the User row, or zero if the login request fails.
 */
@Override
public Integer login(final String userId, final String password) {
  Integer result;
  User user = UserDAOLocator.getUserDAO().findByUserIdAndPassword(userId, password);
  if (user == null) {
    GLLog.infoSummary("Login failed for userId:" + userId);
    result = 0;
  }
  else {
    result = user.getId();
    GLLog.infoSummary("Login succeeded for userId:" + userId);
    HttpSession session = getThreadLocalRequest().getSession();
    session.setAttribute("User", user);
  }
  return result;
} // login()
//--------------------------------------------------------------------------------------------------
}