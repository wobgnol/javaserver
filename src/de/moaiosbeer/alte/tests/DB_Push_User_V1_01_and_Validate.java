package de.moaiosbeer.alte.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import de.moaiosbeer.db.models.TomcatUserRoles_V1_01;
import de.moaiosbeer.db.models.User_V1_01;


public class DB_Push_User_V1_01_and_Validate {

	public static void main(String[] args) {
		// User_V1_01 Inztanz anlegen
		User_V1_01 u = new User_V1_01();
		
		
		// User anlegen
		// aus User_V1_01.java geht vor das User ein Passwort haben müssen (@NotNull)
		// TODO Username peer Req X regeln leerstring etc soll nicht erlaubt werdne
		u.setUsername("User_4"); // Testet Username regx konvention
		// TODO Passwort peer Reg X regeln
		u.setPassword("400");
		
		// Rolle anlegen in Table(TomcatUserRoles_V1_01) und dem user zuweisen
		TomcatUserRoles_V1_01 Role = new TomcatUserRoles_V1_01();
		Role.setRolename("User");
		(u.getRoles()).add(Role);
		
		
		// ValidatorFactory erzeugen
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		// mit der Factory einen Validator besorgen
		Validator validator = factory.getValidator();
				
		
		// Validierung starten
		// try catch block für validierungsfehler
		 try {
			 
			 //set mit Validierungsergebnissen füllen (set leer = keine Fehler)
			 Set<ConstraintViolation<User_V1_01>> failures = validator.validate(u);
			 
			 	// Code der Ausgeführt wird wenn das Error set nicht leer ist
				if(!failures.isEmpty()){ 
					
					// Constraint violations: Errors in the Set
					Map<String,Object> errors = new HashMap<String,Object>();
					List<String> errorMessages = new ArrayList<String>();
					
					// für jeden Fehler in failures mache dies:
					for(ConstraintViolation<?> failure : failures ){
						// fehler Nachricht an Liste hängen
						errorMessages.add(failure.getMessage());
						// giebt classen feld aus 
						//System.out.println(failure.getPropertyPath());
					}
					
					errors.put("errors",errorMessages );
					
					for(String m : errorMessages ){
						System.out.println(m);
					}
					
				} //ENDE => Code der Ausgeführt wird wenn das error set nicht leer ist
				
				
				// TODO  Transaktions rollback einfügen
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
				
			  }//ENDE => Code der Ausgeführt wird wenn das error set leer ist
		 
			  catch (final ConstraintViolationException ex) {
			   System.out.println("Hibernate Class Validation Error. look ErrorList:");
			   
			  }
		
		
		
		
	
	
		// --------------------------------------------
		
		
	}

}
