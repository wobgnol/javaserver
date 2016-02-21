package de.moaiosbeer.alte.tests;

import de.moaiosbeer.db.models.Game_V1_01;
import de.moaiosbeer.db.models.TomcatUserRoles_V1_01;
import de.moaiosbeer.db.models.User_V1_01;
import de.moaiosbeer.hibernate.Hib_DB_Conn_V1_01;

public class DB_Push_UserHasGame {
	
		public static void main(String[] args) {
			//Spiel Tabelle mit einem Spiel füllen
			Game_V1_01 Game = new Game_V1_01();
			//Game.setGamerole("factory");
			
			// Rollen Tabelle mit einer Rolle Bestücken
			TomcatUserRoles_V1_01 Role = new TomcatUserRoles_V1_01();
			Role.setRolename("manager-gui");
			
			// Spieler erzeugen
			User_V1_01 u = new User_V1_01();
			
			// Spieler Felder setzen
			u.setUsername("Hans");
			u.setPassword("1234");
			
			// Rollen Liste eine Rolle hinzufügen (oben angelegte aus der Roletable)
			(u.getRoles()).add(Role);
			
			// Spielliste ein Spiel hinzufügen(oben angelegtes aus der Game Table)
			//(u.getGames()).add(Game);
	
			// dem spieler eine Liste von Spielen zuordnen
			
			
//			User_V1_01 User = new User_V1_01();
//			// Spieler Felder setzen
//			User.setUsername("Harald");
//			User.setPassword("1234");
//			User.setRole("User");
//			User.setGames(games);

			Hib_DB_Conn_V1_01 Con = new Hib_DB_Conn_V1_01();
			Con.Transaction_Start();
			// Timeout der Transaktion setzen
			Con.getTransaction().setTimeout(5);

			// please note I am not saving studentCertification object but still it will
			// be saved in a database that's the magic of Many to Many mapping

			Con.getSession().save(u);
	
			
			Con.getSession().getTransaction().commit();
			Con.getSession().close();
		

		}

	


}
