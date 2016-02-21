package de.moaiosbeer.tests;

import java.util.Date;
import java.util.List;

import de.moaiosbeer.db.models.Game_V1_01;
import de.moaiosbeer.db.models.Playsheet_V1_01;
import de.moaiosbeer.db.models.User_V1_01;
import de.moaiosbeer.hibernate.Hib_DB_Conn_V1_01;

public class DB_Pull_AllGames_V1_01 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Hib_DB_Conn_V1_01 Con = new Hib_DB_Conn_V1_01();
			
				// Session und Transaktion starten
				Con.Transaction_Start();
				// Timeout der Transaktion setzen
				Con.getTransaction().setTimeout(10);
				
				//DB Abfrage mit Hibernate starten alle Tabellen einträge von User
				List<Game_V1_01> list = Con.getSession().createCriteria(Game_V1_01.class,"u").list();
				List<User_V1_01> userList = null; 
				List<Playsheet_V1_01> psheetList = null;
				// Playsheet Liste Ausgeben
				for (Game_V1_01 g : list){
					System.out.println(	"GameID: \t"+g.getId()+ 
										"\nCurrentRound: \t"+g.getCurrentround()+
										"\nMaxrounds: \t"+g.getMaxrounds()
										);
					
					// User / Playsheet Listen Prüfen 
					userList = g.getUserlist();
					psheetList = g.getPlaysheets();	
					}
					System.out.print("UserList = \t[ ");
					for(User_V1_01 u: userList){
						System.out.print(	"{ "+
											" UserID: "+u.getId()+
										 	" UserName: "+u.getUsername()+
											" PW: "+u.getUsername()+
											"} "
										   );	
						System.out.print("]");		
					}
					System.out.print("\nPlaysheetList = [ ");	
					for (Playsheet_V1_01 p : psheetList){
						System.out.print(	"{ "+
											" OwnerID: "+p.getOwner_id()+ 
											" GameRole: "+p.getGamerole()+
											" TotalCosts: "+p.getTotalcosts()+
											" } "
										  );
					}
					System.out.print(" ]");	
					
				// Transaktion abschließen
				Con.getTransaction().commit();
				System.out.println(new Date()+" || Hibernate: Transaction comited");
				

	}

}
