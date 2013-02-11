package org.greatlogic.itunes.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import org.apache.commons.mail.EmailException;
import com.greatlogic.glbase.glevent.GLEvent;
import com.greatlogic.glbase.glevent.GLEventThread;
import com.greatlogic.glbase.glevent.GLEventType;
import com.greatlogic.glbase.glevent.IGLEventReceiver;
import com.greatlogic.glbase.gllib.GLEmailer;
import com.greatlogic.glbase.gllib.GLLog;
import com.greatlogic.glbase.gllib.GLUtil;

class ExternalIPChecker implements IGLEventReceiver {
//--------------------------------------------------------------------------------------------------
private static final GLEventType ExternalIPCheckerET = GLEventType.create("ExternalIPChecker");

private GLEmailer                _emailer;
private String                   _emailFrom;
private String                   _emailTo;
private GLEventThread            _eventThread;
private String                   _previousIP;
private String                   _saveToFilename;
//--------------------------------------------------------------------------------------------------
ExternalIPChecker(final int intervalSeconds, final GLEmailer emailer, final String emailFrom,
                  final String emailTo, final String saveToFilename) {
  _emailer = emailer;
  _emailFrom = emailFrom;
  _emailTo = emailTo;
  _saveToFilename = saveToFilename;
  _eventThread = GLEventThread.create("ExternalIPChecker", this, 10, 10);
  loadPreviousIP();
  _eventThread.addRepeatEventEvery(ExternalIPCheckerET, null, null, null, null, 0,
                                   intervalSeconds * 1000);
} // ExternalIPChecker()
//--------------------------------------------------------------------------------------------------
private void loadPreviousIP() {
  try {
    BufferedReader input = new BufferedReader(new FileReader(_saveToFilename));
    try {
      String line = null;
      line = input.readLine();
      if (line != null) {
        _previousIP = line;
      }
    }
    finally {
      input.close();
    }
  }
  catch (IOException ioe) {
    if (ioe.getMessage().contains("cannot find the file")) {
      _previousIP = GLUtil.getExternalIP();
      storeIP(_previousIP);
    }
    else {
      GLLog.major("Failed to load the IP address", ioe);
    }
  }
  _previousIP = _previousIP == null ? GLUtil.getExternalIP() : _previousIP;
} // loadPreviousIP()
//--------------------------------------------------------------------------------------------------
@Override
public boolean processEvent(GLEvent event) {
  if (event.getEventType() == ExternalIPCheckerET) {
    String ip = GLUtil.getExternalIP();
    if (!ip.equals(_previousIP)) {
      String message = "IP address changed from:" + _previousIP + " To:" + ip;
      GLLog.infoSummary(message);
      storeIP(ip);
      _previousIP = ip;
      boolean retry = false;
      do {
        try {
          _emailer.sendEmail(retry, _emailFrom, _emailTo, message, message, null);
        }
        catch (EmailException ee) {
          if (!retry) {
            GLLog.major("Email attempt failed ... retrying with logging turned on ...", ee);
            retry = true;
          }
        }
      } while (retry);
    }
  }
  return true;
} // processEvent()
//--------------------------------------------------------------------------------------------------
private void storeIP(final String ip) {
  try {
    Writer output = new BufferedWriter(new FileWriter(_saveToFilename));
    try {
      output.write(ip);
    }
    finally {
      output.close();
    }
  }
  catch (IOException ioe) {
    GLLog.major("Failed to store the IP address", ioe);
  }
} // storeIP()
//--------------------------------------------------------------------------------------------------
}