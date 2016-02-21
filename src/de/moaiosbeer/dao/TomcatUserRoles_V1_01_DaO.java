/*
*************************************************************************
* 21.12.2015 															*
* Copyright © 2015-2016 Tim Langenbrink, Stephan Wissing, 				*
* 						Jonas Klein-Hitpaß 								*
*************************************************************************
* 																		*
* Autor 	   : Jonas Klein-Hitpaß 									*
* Company 	   : / 														*
* File-Name    : TomcatUserRoles_V1_01_DaO.java 						*
* Beschreibung : Diese Klasse wird verwendet, um die gewünschten		*
* 				 Abfragen, bzgl des Tomcat-Rollen gegen die Datenbank 	*
* 				 auszuführen											*
* 																		*
*************************************************************************
*/
package de.moaiosbeer.dao;

import java.util.Date;
import de.moaiosbeer.db.models.TomcatUserRoles_V1_01;
import de.moaiosbeer.hibernate.Hib_DB_Conn_V1_01;

public class TomcatUserRoles_V1_01_DaO {
	
	private Hib_DB_Conn_V1_01 Con = new Hib_DB_Conn_V1_01();
	
	private long role_id;
	private String rolename;
	
	/*
	 * Diese Funktion legt eine neue Tomcat-Role in der Datenbank an
	 * */
	public void newTomcatUserRole(TomcatUserRoles_V1_01 tomcatUserRoles)
	{
		/**
		 * Die Methode newTomcatUserRole erstellt eine neue Tomcat Benutzerrolle
		 */
		try{
			Con.Transaction_Start();
			Con.getTransaction().setTimeout(10);	
			
			Con.getSession().createQuery(
			          "INSERT INTO TomcatUserRoles_V1_01 "
			          + "VALUES ('"+ role_id + "','"+ rolename+ "');").iterate();

			Con.getTransaction().commit();
			System.out.println(new Date()+" || Hibernate: Transaction comited");
		}catch(RuntimeException e){
			try{
				Con.getTransaction().rollback();
				System.out.println("Rolback");
			}catch(RuntimeException rbe){
				//TODO Log für Transaktions rollback anlegen : log.error("Couldn’t roll back transaction", rbe);
			}
			throw e;
		}finally{
			if(Con.getSession()!=null){
				Con.getSession().close();
				System.out.println(new Date()+ " || Hibernate: Session Closed");
				//TODO Log für Hibernate Sessions anlegen
			}
		}
	}
	
	/*
	 * Diese Funktion, updatet eine bestehende Tomcat-Role in der Datenbank*/
	public void updateTomcatUserRole(TomcatUserRoles_V1_01 tomcatUserRoles)
	{
		/**
		 * Die Methode updateTomcatUserRole aktualisiert die Benutzerrolle
		 */
		try{
			Con.Transaction_Start();
			Con.getTransaction().setTimeout(10);	
			
			Con.getSession().createQuery(
			          "UPDATE TomcatUserRoles_V1_01 "
			          + "SET  role_id = '"+ role_id + "', rolename = '"+ rolename + "');").iterate();

			Con.getTransaction().commit();
			System.out.println(new Date()+" || Hibernate: Transaction comited");
		}catch(RuntimeException e){
			try{
				Con.getTransaction().rollback();
				System.out.println("Rolback");
			}catch(RuntimeException rbe){
				//TODO Log für Transaktions rollback anlegen : log.error("Couldn’t roll back transaction", rbe);
			}
			throw e;
		}finally{
			if(Con.getSession()!=null){
				Con.getSession().close();
				System.out.println(new Date()+ " || Hibernate: Session Closed");
				//TODO Log für Hibernate Sessions anlegen
			}
		}
	}
	
	/*
	 * Diese Funktion löscht die angegebene Tomcat-Rolle aus der Datenbank
	 * Da dies von uns nicht gewünscht ist, wurde diese Funktion nicht getestet
	 * */
	public void deleteTomcatUserRole(TomcatUserRoles_V1_01 tomcatUserRoles)
	{
		/**
		 * Die Methode deleteTomcatUserRole löscht die Benutzerrolle
		 */
		try{
			Con.Transaction_Start();
			Con.getTransaction().setTimeout(10);	
			
			Con.getSession().createQuery(
			          "DELETE FROM TomcatUserRoles_V1_01 "
			          + "WHERE role_id = '"+ role_id + "');").iterate();

			Con.getTransaction().commit();
			System.out.println(new Date()+" || Hibernate: Transaction comited");
		}catch(RuntimeException e){
			try{
				Con.getTransaction().rollback();
				System.out.println("Rolback");
			}catch(RuntimeException rbe){
				//TODO Log für Transaktions rollback anlegen : log.error("Couldn’t roll back transaction", rbe);
			}
			throw e;
		}finally{
			if(Con.getSession()!=null){
				Con.getSession().close();
				System.out.println(new Date()+ " || Hibernate: Session Closed");
				//TODO Log für Hibernate Sessions anlegen
			}
		}
	}

}
