package de.moaiosbeer.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
//import javax.servlet.annotation.WebListener;

import javax.servlet.annotation.WebListener;

import de.moaiosbeer.hibernate.HibernateUtil;

/*Annotation to aktivate Contextlistener on Application startup. 
 *Supported for Servlet container 3.x or higher (or with Listener mapping in web.xml)*/

@WebListener
/**
 * ServletContextListener dient dazu gewisse dinge vorm starten / herunterfahren der Webanwendung auszuführen
 * (Initialisierender bzw Aufräumender Code )
 * @author Stephan
 */
public class MoaIosBeer_ContextListener 
implements  ServletContextListener{
	
	
	@Override
	/** 
	 * Dieser Code wird vorm starten der Webanwendung ausgeführ 
	 * (initialisieren, Datenbank verbindungen etc)
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		
		System.out.println("\n\nInitialize WebApplication: MoaIosBeer  => ServletContextListener started\n");
		System.out.println("Stepp 1 of 1: Start => Initialize Hibernate");
		/**
		 * Hibernate Session Factory Global Registrieren
		 */
		HibernateUtil.getSessionFactory(); // Initialize the Database connection for Hibernate 
		System.out.println("Stepp 1 of 1: Ready => Hibernate Initialized\n");
		System.out.println("WebApplication: MoaIosBeer now Initialized next => Start Web Application\n\n");
	}
	
	
	@Override
	/** 
	 * Dieser Code wird vor dem Herunterfahren der Webanwendung ausgeführ 
	 * (Aufräumen, finales Persitieren etc)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		
		HibernateUtil.getSessionFactory().close(); // Free all Hibernate resources 
		System.out.println("ServletContextListener destroyed");
	}

}
