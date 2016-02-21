package de.moaiosbeer.tests;

import java.util.Date;
import java.util.List;

import de.moaiosbeer.db.models.Playsheet_V1_01;
import de.moaiosbeer.db.models.User_V1_01;
import de.moaiosbeer.hibernate.Hib_DB_Conn_V1_01;

public class DB_Pull_AallPlaysheets_V1_01 {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Hib_DB_Conn_V1_01 Con = new Hib_DB_Conn_V1_01();
			
				// Session und Transaktion starten
				Con.Transaction_Start();
				// Timeout der Transaktion setzen
				Con.getTransaction().setTimeout(10);
				
				//DB Abfrage mit Hibernate starten alle Tabellen eintr‰ge von User
				List<Playsheet_V1_01> list = Con.getSession().createCriteria(Playsheet_V1_01.class,"u").list();
				
				// Playsheet Liste Ausgeben
				for (Playsheet_V1_01 p : list){
					System.out.println(	"OwnerID: \t"+p.getOwner_id()+ 
										"\nGameRole: \t"+p.getGamerole()+
										"\nTotalCosts: \t"+p.getTotalcosts()+
										"\n\n"
									  );
				}
				// Transaktion abschlieﬂen
				Con.getTransaction().commit();
				System.out.println(new Date()+" || Hibernate: Transaction comited");
				

	}
}
