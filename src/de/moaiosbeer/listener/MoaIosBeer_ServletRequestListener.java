package de.moaiosbeer.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
//import javax.servlet.annotation.WebListener; nur benötigt wenn der Listener per annotation eingebunden wird
 
//@WebListener
/**
 * Dieser Listener horcht auf Anfragen die erstellt bzw zerstört werden.
 * Hier wird er für das Implementieren eines Request logs genutzt.
 * @author Stephan
 */
public class MoaIosBeer_ServletRequestListener implements ServletRequestListener {
 
	/**
	 * Dieser Code wird Ausgeführt wenn ein request beendet wird
	 */
	public void requestDestroyed(ServletRequestEvent event) {
		// TODO System.out.println(); durch Request Log Austauschen
		System.out.println("request being sent to "
				+ event.getServletRequest().getRemoteAddr() );
	
	}
	/**
	 * Dieser Code wird ausgeführt wenn ein request eingeht
	 */
	public void requestInitialized(ServletRequestEvent event) {
		// TODO System.out.println(); durch Request Log Austauschen
		System.out.println("now initializing request"
				+ event.getServletRequest().getRemoteAddr());
 
	}
 
}