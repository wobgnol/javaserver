package de.moaiosbeer.alte.tests;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import de.moaiosbeer.db.models.User_V1_01;


public class DB_Pull_User_V1_01 {

		public static void main(String[] args) {
			// Session anlegen und Transaktion öffnen
			SessionFactory Factory = new Configuration().configure().buildSessionFactory();
			Session Session = Factory.openSession();
			
			//alle Beans lesen und ausgeben
			List<User_V1_01> list = Session.createCriteria(User_V1_01.class).list();
			for (User_V1_01 m : list){
				System.out.println("Username: "+ m.getUsername() +"|| Password: " +m.getPassword());
			}
			
			// Session und Factory schließen
			Session.close();
			Factory.close();
		
		}


	}

