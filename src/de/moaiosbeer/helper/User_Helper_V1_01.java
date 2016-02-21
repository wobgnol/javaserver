package de.moaiosbeer.helper;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import de.moaiosbeer.db.models.User_V1_01;


public class User_Helper_V1_01 {

	public static void main(String[] args) {
		
		// User instanziieren
		User_V1_01 user = new User_V1_01();
		User_V1_01 user2 = new User_V1_01();
		User_V1_01 user3 = new User_V1_01();	
		
		// Session anlegen und Transaktion öffnen
		SessionFactory Factory = new Configuration().configure().buildSessionFactory();
		Session Session = Factory.openSession();
					
		//alle Beans lesen und ausgeben
		List<User_V1_01> list = Session.createCriteria(User_V1_01.class).list();
		user=list.get(0);
		user2=list.get(1);
		user3=list.get(2);
					
		// Konvertiert JavaObjekte zu JSON
		ObjectMapper mapper = new ObjectMapper();
					
		System.out.println( pojo(user));
		System.out.println( pojo(user2));
		System.out.println( pojo(user3));
		
		//Pojo in einer anderesn Klasse auslagern
					
		// Session und Factory schließen
		Session.close();
		Factory.close();
	}
	
	
	/** 
	 * POJO to JSON 
	 * @param m
	 * @return 
	 */
	public static <T> String pojo( T m){
	
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			return mapper.writeValueAsString(m).toString();
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block (MSG: JsonGenerationException)
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block (MSG: JsonMappingException)
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block (MSG: IOException)
			e.printStackTrace();
		}
		return " ";
		}
}


