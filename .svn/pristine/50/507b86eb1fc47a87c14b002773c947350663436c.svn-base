package de.moaiosbeer.tests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import de.moaiosbeer.db.models.Game_V1_01;
import de.moaiosbeer.db.models.Playsheet_V1_01;
import de.moaiosbeer.db.models.TomcatUserRoles_V1_01;
import de.moaiosbeer.db.models.User_V1_01;
import de.moaiosbeer.hibernate.Hib_DB_Conn_V1_01;
 
public class DB_Push_User_V1_01_with_Playsheet_V1_01 {
 

	
   
    public static void main(String[] args) {
    	
    	Playsheet_V1_01.setNextPlaysheetID();
    	// Private inztanz des Verbindungshelfers	
    	Hib_DB_Conn_V1_01 Con = new Hib_DB_Conn_V1_01();
      
    	//Transaktion1 =====================================================Start
        try{
    		// Session und Transaktion starten
    		Con.Transaction_Start();
    		// Timeout der Transaktion setzen
    		Con.getTransaction().setTimeout(20);
    		
    		
            //Schritt 1/3----Zwei User erzeugen und mit Daten füllen--------Start
            User_V1_01 User1 = new User_V1_01();
            User_V1_01 User2 = new User_V1_01();
            
            // username und password setzen
            User1.setUsername("Ich"); 		
            User1.setPassword("1234");		
            User2.setUsername("Kevin"); 
            User2.setPassword("1234");
            
            // User Rollen erstellen (für den Login)
			TomcatUserRoles_V1_01 Role = new TomcatUserRoles_V1_01();
			TomcatUserRoles_V1_01 Role2 = new TomcatUserRoles_V1_01();
			
			// Zwei neue Rollen erstellen
			Role.setRolename("manager-gui");
			Role2.setRolename("admin");
			
			// Den Usern die eben erstellten Rollen zuweisen
			User1.getRoles().add(Role);
			User2.getRoles().add(Role2);
			
			/*wichtig jede Inztanz nach Erstellung 
			 * wie Folgt speichern:*/
			Con.getSession().save(User1);
			Con.getSession().save(User2);
			/*Sonst wird die UserID nicht Autoincrementiert
			 * Was Dazu führt das der Gamekonstrucktor 
			 * für das Feld ownerID im Playsheet 0 schreibt statt der ID */
			
			//Schritt 1/3----Zwei User erzeugen und mit Daten füllen---------Ende
            
			
			
			//Schritt 2/3----Aus den Usern eine UserList erzeugen------------Start
            List<User_V1_01> plist = new ArrayList<User_V1_01>(); 
            plist.add(User1);
            plist.add(User2);
            //Schritt 2/3----Aus den Usern eine UserList erzeugen------------Ende
            
            
           
            //Schritt 3/3------Ein Spiel mit der UserListe erzeugen----------Start
            Game_V1_01 Game = new Game_V1_01(plist);
            // Dem Game einen Namen verpassen
            Game.setGamename("Ein neues Game222");
            // Spiel Speichern
            Con.getSession().save(Game);
            //Schritt 3/3------Ein Spiel mit der UserListe erzeugen----------Ende
           
       
    		// Transaktion abschließen
    		Con.getTransaction().commit();
    		System.out.println(new Date()+" || Hibernate: Transaction comited");
    	
    	// im Fehlerfall Transaktionsrollback fahren	
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
    	}//Transaktion1 =====================================================Ende
        
       
        
        //Transaktion2 =====================================================Start
        try{
    		// Session und Transaktion starten
    		Con.Transaction_Start();
    		// Timeout der Transaktion setzen
    		Con.getTransaction().setTimeout(20);
    		
    		
            //Schritt 1/3----Zwei User erzeugen und mit Daten füllen--------Start
            User_V1_01 User1 = new User_V1_01();
            User_V1_01 User2 = new User_V1_01();
            User_V1_01 User3 = new User_V1_01();
            User_V1_01 User4 = new User_V1_01();
            
            // username und password setzen
            User1.setUsername("Ingo2"); 		
            User1.setPassword("1234");		
            User2.setUsername("Dieter2"); 
            User2.setPassword("1234");
            User3.setUsername("Tim22"); 		
            User3.setPassword("1234");		
            User4.setUsername("JonFire22"); 
            User4.setPassword("1234");
            
            
            // User Rollen erstellen (für den Login)
			TomcatUserRoles_V1_01 Role = new TomcatUserRoles_V1_01();
			TomcatUserRoles_V1_01 Role2 = new TomcatUserRoles_V1_01();
			
			// Zwei neue Rollen erstellen
			Role.setRolename("player");
			Role2.setRolename("admin");
			
			// Den Usern die eben erstellten Rollen zuweisen
			User1.getRoles().add(Role);
			User2.getRoles().add(Role2);
			User3.getRoles().add(Role);
			User4.getRoles().add(Role2);
			
			
			/*wichtig jede Inztanz nach Erstellung 
			 * wie Folgt speichern:*/
			Con.getSession().save(User1);
			Con.getSession().save(User2);
			Con.getSession().save(User3);
			Con.getSession().save(User4);
			/*Sonst wird die UserID nicht Autoincrementiert
			 * Was Dazu führt das der Gamekonstrucktor 
			 * für das Feld ownerID im Playsheet 0 schreibt statt der ID */
			
			//Schritt 1/3----Zwei User erzeugen und mit Daten füllen---------Ende
			
			
			//Schritt 2/3----Aus den Usern eine UserList erzeugen------------Start
            List<User_V1_01> plist = new ArrayList<User_V1_01>(); 
            plist.add(User1);
            plist.add(User2);
            plist.add(User3);
            plist.add(User4);
            //Schritt 2/3----Aus den Usern eine UserList erzeugen------------Ende
            
            
           
            //Schritt 3/3------Ein Spiel mit der UserListe erzeugen----------Start
            Game_V1_01 Game = new Game_V1_01(plist);
            // Dem Game einen Namen verpassen
            Game.setGamename("BeerGame332222");
            // Spiel Speichern
            Con.getSession().save(Game);
            //Schritt 3/3------Ein Spiel mit der UserListe erzeugen----------Ende
            
            long User1ID = User1.getId();
            	for(Playsheet_V1_01 ps: Game.getPlaysheets()){
            		if(User1ID == ps.getOwner_id())
            		{
            			System.out.println(ps.getId() +" "+ps.getOwner_id());
            			
            		}
            	}
           
            
    		// Transaktion abschließen
    		Con.getTransaction().commit();
    		System.out.println(new Date()+" || Hibernate: Transaction comited");
    	
    	// im Fehlerfall Transaktionsrollback fahren	
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
    	}//Transaktion2 =====================================================Ende
        
        
    
        
     
    }
}
