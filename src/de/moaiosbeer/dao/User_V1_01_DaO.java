/*
*************************************************************************
* 21.12.2015 															*
* Copyright © 2015-2016 Tim Langenbrink, Stephan Wissing, 				*
* 						Jonas Klein-Hitpaß 								*
*************************************************************************
* 																		*
* Autor 	   : Jonas Klein-Hitpaß 									*
* Company 	   : / 														*
* File-Name    : User_V1_01_DaO.java 									*
* Beschreibung : Diese Klasse wird verwendet, um die gewünschten		*
* 				 Abfragen, bzgl des Users gegen die Datenbank 			*
* 				 auszuführen											*
* 																		*
*************************************************************************
*/
package de.moaiosbeer.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import de.moaiosbeer.db.models.TomcatUserRoles_V1_01;
import de.moaiosbeer.db.models.User_V1_01;
import de.moaiosbeer.helper.MoaHttpClient;
import de.moaiosbeer.hibernate.Hib_DB_Conn_V1_01;

/**
 * Diese Klasse ist ein DaO => Data Acces Object für das Hibernate-OrM Mapping,
 * durch Sie werden geregelte CRUD (Create Read Update Delete) operationen für 
 * die gemappte Tabelle Gleichen Namens bereitstellt.
 * 
 * Sie gewährleistet somit einen geregelten Datenbank zugriff durch die bereitgestellten Methoden.
 * 
 * Alle Datenbankzugriffe in dieser ServerImplementierung werden nur durch Daos geregelt.
 * 
 * @author Stephan
 */
public class User_V1_01_DaO {

// Private inztanz des Verbindungshelfers	
private Hib_DB_Conn_V1_01 Con = new Hib_DB_Conn_V1_01();

protected Long user_id;

/**
 * Diese Methode gibt anhand eines Auth-Tokens eine User Id zurück.
 * @param token base64 Encoding aus username:password
 * @return Long User_V1_01 ID
 */
public Long getUserIdByAuthToken ( String token){
	/* MoaHttpClinet Instanzieren =>  um RestCall mit Antwort zu erhalten
	 * und um die Comfort-Methoden des Clienten nutzen zu können */
	MoaHttpClient client = new MoaHttpClient();	
	//Hashmap<String K,String V> für => username und password anlegen
	HashMap<String,String>  username_password = new HashMap<String,String>();
	/*username und password aus dem Auth-Token decodieren, String(username:password), 
	 *und am : Splitten. 
	 *Dann in HashMap ablegen */
	username_password = client.getDecodedAuthString(token);
	// User Id mittels User_dao ermitteln ( Abfrage zur DB )
	return  this.isUser(username_password.get("username") ,username_password.get("password"));	 
}


/**
 * Diese Funktion gibt anhand der Userid das Userobjekt zurück
 * @param userid Long User_V1_01 ID
 * @return User_V1_01 Objekt
 * @throws Exception  RuntimeException Bei Hibernate Rolback
 */
public User_V1_01 getUser(long userid) throws Exception
{

	try{
		// Session und Transaktion starten
		Con.Transaction_Start();
		// Timeout der Transaktion setzen
		Con.getTransaction().setTimeout(10);
		
		//DB Abfrage mit Hibernate starten alle Tabellen einträge von User
		List<User_V1_01> list = Con.getSession().createCriteria(User_V1_01.class).add(Restrictions.eq("id", userid)).list();
		
		// Transaktion abschließen
		Con.getTransaction().commit();
		System.out.println(new Date()+" || Hibernate: Transaction comited");
		
		if(list.size()==1)
		{
			return list.get(0);
		}
		throw new Exception("Kein Benutzer mit der angegeben ID vorhanden");
		
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

/**
 * Giebt alle User_V1_01 aus der DB als Liste zurück
 * @return List <User_V1_01> 
 * @throws Exception  RuntimeException Bei Hibernate Rolback
 */
public List<User_V1_01> getAllUsers(){
	
	try{
		// Session und Transaktion starten
		Con.Transaction_Start();
		// Timeout der Transaktion setzen
		Con.getTransaction().setTimeout(10);
		
		//DB Abfrage mit Hibernate starten alle Tabellen einträge von User
		List<User_V1_01> list = Con.getSession().createCriteria(User_V1_01.class,"u").list();
		
		
		// Transaktion abschließen
		Con.getTransaction().commit();
		System.out.println(new Date()+" || Hibernate: Transaction comited");
		//req.getSession().invalidate();
		
		return list;
		
	// return user_chain ;
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
 

/**
 * Die Methode newUser erstellt einen neuen Benutzer in der Datenbank.
 * @param user de.moaiosbeer.db.models.User_V1_01.java
 * @throws Exception  RuntimeException Bei Hibernate Rolback
 */
public void newUser(User_V1_01 user)
{
	
	try{
		Con.Transaction_Start();
		Con.getTransaction().setTimeout(10);	
		
		TomcatUserRoles_V1_01 roles = new TomcatUserRoles_V1_01();
		List<TomcatUserRoles_V1_01> list = new ArrayList<TomcatUserRoles_V1_01>();
		
		//roles.setRolename("manager-gui");
		//list.add(roles);
		//user.setRoles(list);
	
		Con.getSession().save(user);
		Con.getTransaction().commit();
		
		Con.Transaction_Start();
		Con.getTransaction().setTimeout(10);
		Con.getSession().createSQLQuery("INSERT INTO user_v1_01_tomcatuserroles_v1_01 VALUES(" + user.getId() + ",3)").executeUpdate();
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


/**
 * Diese Funktion ändert die gewünschten Einträge des Users in der Datenbank
 * @param user Instanz: User_V1_01 user
 */
public void updateUser(User_V1_01 user)
{
	/**
	 * Die Methode updateUser aktualisiert.
	 */
	try{
		Con.Transaction_Start();
		Con.getTransaction().setTimeout(10);	

		Con.getSession().update(user);
		
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


/**
 * Diese Metzhode löscht den angegebenen Nutzer aus der Datenbank
 * Da dies von uns nicht gewünscht ist, wurde diese Funktion nicht getestet
 * @param user
 */
public void deleteUser(User_V1_01 user)
{
	/**
	 * Die Methode deleteUser löscht den Benutzer.
	 */
	try{
		Con.Transaction_Start();
		Con.getTransaction().setTimeout(10);	

		Con.getSession().delete(user);
		
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


/**
 * Diese Methode ermittelt anhand des übergebenen Usernames und Passworts, 
 * ob dies ein registrierter Benutzer ist
 * @param username String: User_V1_01 username
 * @param password String: User_V1_01 passwort
 * @return Long  true Wenn User_V1_01 ein Valider User ist | sonst false
 */
public Long isUser(String username, String password)
{
	/**
	 * Die Methode isUser prüft den Benutzer auf eine ID. 
	 */
	try{
		Con.Transaction_Start();
		Con.getTransaction().setTimeout(10);	
		
		List<User_V1_01> list = Con.getSession().createCriteria(User_V1_01.class,"u").list();
		for (User_V1_01 userList : list)
		{
			if(userList.getUsername().equals(username))
			{
				if(userList.getPassword().equals(password))
				{
					return userList.getId();
				}
			}
		}

		Con.getTransaction().commit();
		System.out.println(new Date()+" || Hibernate: Transaction comited");
		return null;
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
