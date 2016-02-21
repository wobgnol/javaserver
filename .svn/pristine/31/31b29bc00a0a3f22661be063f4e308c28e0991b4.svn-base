package de.moaiosbeer.alte.tests;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import de.moaiosbeer.db.models.Game_V1_01;
import de.moaiosbeer.db.models.TomcatUserRoles_V1_01;
import de.moaiosbeer.db.models.User_V1_01;


public class DB_Push_User_V1_01 {

	public static void main(String[] args) {
		// User instanziieren
		User_V1_01 u = new User_V1_01();
		User_V1_01 u2 = new User_V1_01();
		User_V1_01 u3 = new User_V1_01();
	
		// --------------------------------------------
		
		//  Drei User mit hilfe der Klasse User anlegen
		//User 1
		u.setUsername("Ich");
		u.setPassword("1234");
		
		// Game anlegen in Table(Game_V1_01) und dem user zuweisen
		Game_V1_01 game = new Game_V1_01();
		//game.setGamerole("peter");
		//(u.getGames()).add(game);
		
		// Rolle anlegen in Table(TomcatUserRoles_V1_01) und dem user zuweisen
		TomcatUserRoles_V1_01 Role = new TomcatUserRoles_V1_01();
		Role.setRolename("admin-gui");
		(u.getRoles()).add(Role);
		
		// Rolle anlegen in Table(TomcatUserRoles_V1_01) und dem user zuweisen
		TomcatUserRoles_V1_01 Role2 = new TomcatUserRoles_V1_01();
		Role2.setRolename("manager-gui");
		(u.getRoles()).add(Role2);
		

		
		// Hibernate Session instanziieren 
		SessionFactory Factory = new Configuration().configure().buildSessionFactory();
		Session Session = Factory.openSession();
		Session.beginTransaction();
		
		// Instanziierte User speichern
		Session.save(u);
	
		
		
		
		// Session abfeuern (commit) und beenden
		Session.getTransaction().commit();
		Session.close();
		Factory.close();
	}

}
