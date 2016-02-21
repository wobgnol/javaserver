package de.moaiosbeer.listener;

import java.util.Date;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.annotation.WebListener; //nur benötigt wenn der Listener per annotation eingebunden wird

/*  If a new session is created , e.g “request.getSession();” , the listener’s sessionCreated() will be executed.
– If a session is destroyed, e.g session’s timeout or “session.invalidate()”, the listener’s sessionDestroyed() will b*/
@WebListener
/**
 * SessionCounterListener dient dazu Die Anzahl der Http Sessions zu zählen. 
 * Darüber hinaus soll er das erstellen / löschen von Sessions in ein log ablegen
 * @author Stephan
 */
public class MoaIosBeer_SessionCounterListener implements HttpSessionListener {
	// Http Session Counter
	private static int totalActiveSessions;
	
	  public static int getTotalActiveSession(){  
		return totalActiveSessions;
	  }
		
	  @Override
	  public void sessionCreated(HttpSessionEvent arg0) {
		  
		  // TODO  an log file binden ( Session erstellt )
		totalActiveSessions++;
		System.out.println(new Date()+ " || Tomcat: sessionCreated || add one session into counter");
	  }

	  @Override
	  public void sessionDestroyed(HttpSessionEvent arg0) {
		  // TODO  an log file binden ( Session gelöscht )
		totalActiveSessions--;
		System.out.println(new Date()+ " || Tomcat: sessionDestroyed || deduct one session from counter");
	  }	

}
