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
public class User_V1_01_DaO {

// Private inztanz des Verbindungshelfers	
private Hib_DB_Conn_V1_01 Con = new Hib_DB_Conn_V1_01();

protected Long user_id;

/**
 * Diese User-Dao Methode giebt anhand eines Auth-Tokens eine UserId zurück.
 * @param token base64 Encoding aus username:password
 * @return Long userID
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

/*
 * Diese Funktion gibt anhand der Userid das Userobjekt zurück*/
public User_V1_01 getUser(long userid) throws Exception
{
	/**
	 * Die Methode getUser gibt auf eier Benutzer ID den kompletten User zurück.
	 */
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
 * Giebt alle User_V1_01 beans aus der DB als Liste zurück
 * @return Lise von Benutzern in der DB
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
 * Die Methode newUser erstellt einen neuen Benutzer  in der Datenbank.
 * @param user de.moaiosbeer.db.models.User_V1_01.java
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

/*
 * Diese Funktion ändert die gewünschten Einträge des Users in der Datenbank
 * */
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

/*
 * Diese funktion löscht den angegebenen Nutzer aus der Datenbank
 * Da dies von uns nicht gewünscht ist, wurde diese Funktion nicht getestet
 * */
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
 * */
public Long isUser(String user, String passw)
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
			if(userList.getUsername().equals(user))
			{
				if(userList.getPassword().equals(passw))
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
