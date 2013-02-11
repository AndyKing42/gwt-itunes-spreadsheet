package org.greatlogic.itunes.server;

import com.greatlogic.glbase.gllib.GLConfig;
import com.greatlogic.glbase.gllib.GLEmailer;
import com.greatlogic.glbase.gllib.GLUtil;
import com.greatlogic.glbase.gllib.IGLProgram;
import com.greatlogic.glbase.glxml.GLXMLElement;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.greatlogic.itunes.server.ITunesServerEnums.EItunesConfigAttribute;
import org.greatlogic.itunes.server.model.dao.BookClubDAOLocator;

public class ITunesServletContextListener implements ServletContextListener {
//==================================================================================================
private static class ITunesProgram implements IGLProgram {
@Override
public boolean displayCommandLineHelp() {
  return false;
} // displayCommandLineHelp()
} // class ITunesProgram
//==================================================================================================
@Override
public void contextDestroyed(final ServletContextEvent event) {
  //
} // contextDestroyed()
//--------------------------------------------------------------------------------------------------
@Override
public void contextInitialized(final ServletContextEvent event) {
  String configFilename = event.getServletContext().getInitParameter("ConfigFilename");
  GLUtil.initializeProgram(new ITunesProgram(), null, null, //
                           "<args ConfigFilename='" + configFilename + "'/>");
  GLEmailer emailer = new GLEmailer(GLConfig.getTopConfigElement());
  initializeExternalIPChecker(emailer);
  //  initializeDAOs();
} // contextInitialized()
//--------------------------------------------------------------------------------------------------
private void initializeDAOs() {
  BookClubDAOLocator.getBookClubDAO();
} // initializeDAOs()
//--------------------------------------------------------------------------------------------------
private void initializeExternalIPChecker(final GLEmailer emailer) {
  GLXMLElement configElement = GLConfig.getTopConfigElement();
  int intervalSeconds = configElement.attributeAsInt(EItunesConfigAttribute.ExternalIPCheckerIntervalSeconds);
  String emailFrom = configElement.attributeAsString(EItunesConfigAttribute.ExternalIPCheckerEmailFrom);
  String emailTo = configElement.attributeAsString(EItunesConfigAttribute.ExternalIPCheckerEmailTo);
  String saveToFilename = configElement.attributeAsString(EItunesConfigAttribute.ExternalIPCheckerSaveToFilename);
  if (intervalSeconds > 0 && !emailTo.isEmpty() && !saveToFilename.isEmpty()) {
    new ExternalIPChecker(intervalSeconds, emailer, emailFrom, emailTo, saveToFilename);
  }
} // initializeExternalIPChecker()
//--------------------------------------------------------------------------------------------------
}