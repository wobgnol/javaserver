/*
*************************************************************************
* 21.12.2015 															*
* Copyright � 2015-2016 Tim Langenbrink, Stephan Wissing, 				*
* 						Jonas Klein-Hitpa� 								*
*************************************************************************
* 																		*
* Autor 	   : Jonas Klein-Hitpa� 									*
* Company 	   : / 														*
* File-Name    : Game_V1_01_DaO.java 									*
* Beschreibung : Diese Klasse wird verwendet, um die gew�nschten		*
* 				 Abfragen, bzgl der Spiele gegen die Datenbank			*
* 				 auszuf�hren											*
* 																		*
*************************************************************************
*/
package de.moaiosbeer.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;

import de.moaiosbeer.db.models.Game_V1_01;
import de.moaiosbeer.db.models.Playsheet_V1_01;
import de.moaiosbeer.db.models.User_V1_01;
import de.moaiosbeer.helper.MoaHttpClient;
import de.moaiosbeer.hibernate.Hib_DB_Conn_V1_01;

/**
 * Diese Klasse ist ein DaO => Data Acces Object f�r das Hibernate-OrM Mapping,
 * durch Sie werden geregelte CRUD (Create Read Update Delete) operationen f�r 
 * die gemappte Tabelle Gleichen Namens bereitstellt.
 * 
 * Sie gew�hrleistet somit einen geregelten Datenbank zugriff durch die bereitgestellten Methoden.
 * 
 * Alle Datenbankzugriffe in dieser ServerImplementierung werden nur durch Daos geregelt.
 * 
 * @author Stephan
 */
public class Game_V1_01_DaO {
	
	private Hib_DB_Conn_V1_01 Con = new Hib_DB_Conn_V1_01();

	private long game_id;
	private String gamerole;

	/**
	 * Diese Methode schreibt das �bergebene Spiel in die Datenbank.
	 * Zwischentabellen werden ebenfalls ber�cksichtigt
	 * @param game Game_V1_01 game
	 */
	public void newGame(Game_V1_01 game)
	{
		try{
			Con.Transaction_Start();
			Con.getTransaction().setTimeout(10);	

			Con.getSession().save(game);
					
			Con.getTransaction().commit();
			System.out.println(new Date()+" || Hibernate: Transaction comited");
		}catch(RuntimeException e){
			try{
				Con.getTransaction().rollback();
				System.out.println("Rolback");
			}catch(RuntimeException rbe){
				//TODO Log f�r Transaktions rollback anlegen : log.error("Couldn�t roll back transaction", rbe);
			}
			throw e;
		}finally{
			if(Con.getSession()!=null){
				Con.getSession().close();
				System.out.println(new Date()+ " || Hibernate: Session Closed");
				//TODO Log f�r Hibernate Sessions anlegen
			}
		}
	}


	/**
	 * Diese Methode updatet alle ben�tigten Eintr�ge eines Spiels, welche mit dem
	 * Hinzuf�gen eines Spielers verbunden sind
	 * @param game Game_V1_01 game
	 * @param userid long userid
	 */
	public void updateGame(Game_V1_01 game, long userid)
	{
		try{
			
			long pid = -1;
			for(Playsheet_V1_01 p : game.getPlaysheets())
			{
				if(p.getOwner_id() == userid)
				{
					Playsheet_V1_01_DaO pd = new Playsheet_V1_01_DaO();
					pd.newSheet(p);
					pid = p.getId();
				}
			}
			
			Con.Transaction_Start();
			Con.getTransaction().setTimeout(10);

			Con.getSession().createSQLQuery(
			          "INSERT INTO game_v1_01_user_v1_01 "
			          + "VALUES ("+ game.getId() + ","+ userid+ ")").executeUpdate();
			//Con.getTransaction().commit();
			
			Con.getSession().createSQLQuery(
			          "INSERT INTO game_v1_01_playsheet_v1_01 "
			          + "VALUES ("+ game.getId() + ","+ pid+ ",1)").executeUpdate();
			//Con.getSession().update(game);
			Con.getTransaction().commit();
			System.out.println(new Date()+" || Hibernate: Transaction comited");
		}catch(RuntimeException e){
			try{
				Con.getTransaction().rollback();
				System.out.println("Rolback");
			}catch(RuntimeException rbe){
				//TODO Log f�r Transaktions rollback anlegen : log.error("Couldn�t roll back transaction", rbe);
			}
			throw e;
		}finally{
			if(Con.getSession()!=null){
				Con.getSession().close();
				System.out.println(new Date()+ " || Hibernate: Session Closed");
				//TODO Log f�r Hibernate Sessions anlegen
			}
		}
	}
	
	/**
	 * Diese Methode setzt die aktuelle und maximale runde des angegebenen games neu*/
	public void updateGame(long gameid, int currentround, int maxrounds)
	{
		/**
		 updateGame bekommt ein Game �bergeben und f�hrt ein update �ber dieses Game aus.
		 */
		try{
			Con.Transaction_Start();
			Con.getTransaction().setTimeout(10);

			Con.getSession().createSQLQuery(
			          "UPDATE game_v1_01 "
			          + "SET currentround=" + currentround+ " AND maxrounds = " + maxrounds + " WHERE game_v1_01.game_id = " + gameid).executeUpdate();
			//Con.getSession().update(game);
			
			Con.getTransaction().commit();
			System.out.println(new Date()+" || Hibernate: Transaction comited");
		}catch(RuntimeException e){
			try{
				Con.getTransaction().rollback();
				System.out.println("Rolback");
			}catch(RuntimeException rbe){
				//TODO Log f�r Transaktions rollback anlegen : log.error("Couldn�t roll back transaction", rbe);
			}
			throw e;
		}finally{
			if(Con.getSession()!=null){
				Con.getSession().close();
				System.out.println(new Date()+ " || Hibernate: Session Closed");
				//TODO Log f�r Hibernate Sessions anlegen
			}
		}
	}


	/**
	 * Diese Methode l�scht das angegebene Spiel aus der Datenbank.
	 * Da dies von unserer Seite nicht erw�nscht ist, wurde diese Funktion nicht getestet!*/
	public void deleteGame(Game_V1_01 game)
	{
		
		try{
			Con.Transaction_Start();
			Con.getTransaction().setTimeout(10);	

			Con.getSession().delete(game);

			Con.getTransaction().commit();
			System.out.println(new Date()+" || Hibernate: Transaction comited");
		}catch(RuntimeException e){
			try{
				Con.getTransaction().rollback();
				System.out.println("Rolback");
			}catch(RuntimeException rbe){
				//TODO Log f�r Transaktions rollback anlegen : log.error("Couldn�t roll back transaction", rbe);
			}
			throw e;
		}finally{
			if(Con.getSession()!=null){
				Con.getSession().close();
				System.out.println(new Date()+ " || Hibernate: Session Closed");
				//TODO Log f�r Hibernate Sessions anlegen
			}
		}
	}
	
	
	
	//===========================GetMyGamesByUserToken========================================================START
		/**
		 * Diese Methode giebt alle Spiele eines Spielers anhand seines Tokens zur�ck
		 * @param token Auth Token
		 * @return  List<Game_V1_01>
		 */
			public List<Game_V1_01> GetGamesByUserToken(String token){
					// R�ckgabeliste f�r myGames
					List<Game_V1_01> listToReturn = new ArrayList<Game_V1_01>();
					try{
						/* Userdao anlegen um die userid eines nutzers anhand des AuthTokens zu finden*/
						User_V1_01_DaO Userdao = new User_V1_01_DaO();
						// Userid anhand des Tokens ermitteln
						Long userID = Userdao.getUserIdByAuthToken(token);
						

						Con.Transaction_Start();
						Con.getTransaction().setTimeout(10);	
						
						/*Die Lieste aller Spiele nach dieser UserID durchsuchen-----------------------Start*/
						List<Game_V1_01> list = Con.getSession().createCriteria(Game_V1_01.class,"u").list();
							// F�r jedes Spiel aus der Liste Aller:
							for(Game_V1_01 game : list){
								// Hole die die Teilnehmerliste dieses Spiels
								List<User_V1_01> userlist = game.getUserlist();
								//Durchsuche die Teilnehmerliste nach der UserID
								for(User_V1_01 user : userlist)
								{	
									// ist der User Teilnehmer dann:
									if(user.getId() == userID){
										// Der r�ckgabe liste das Spiel anh�ngen
										listToReturn.add(game);	
									}	
								}
							}
					/*Die Lieste aller Spiele nach dieser UserID durchsuchen-----------------------Ende*/
					Con.getTransaction().commit();
					System.out.println(new Date()+" || Hibernate: Transaction comited");
					// Liste der Spiele dieses Spielers zur�ckgeben
					return listToReturn;
					
					}catch(RuntimeException e){
						try{
							Con.getTransaction().rollback();
							System.out.println("Rolback");
						}catch(RuntimeException rbe){
							//TODO Log f�r Transaktions rollback anlegen : log.error("Couldn�t roll back transaction", rbe);
						}
						throw e;
					}finally{
						if(Con.getSession()!=null){
							Con.getSession().close();
							System.out.println(new Date()+ " || Hibernate: Session Closed");
							//TODO Log f�r Hibernate Sessions anlegen
						}
						
					}
					
				}
	//===========================GetMyGamesByUserToken========================================================ENDE
			/**
			 * Diese Methode Gibt alles Offenen Spiele eine Spielers anhand seines Auth Tokens zur�ck
			 * @param token Auth Token
			 * @return  List<Game_V1_01>
			 */
			public List<Game_V1_01> getOpenGamesByToken(String token){
				// R�ckgabeliste f�r openGames
				List<Game_V1_01> listToReturn = new ArrayList<Game_V1_01>();
				try{
					/* Userdao anlegen um die userid eines nutzers anhand des AuthTokens zu finden*/
					User_V1_01_DaO Userdao = new User_V1_01_DaO();
					// Userid anhand des Tokens ermitteln
					Long userID = Userdao.getUserIdByAuthToken(token);
					

					Con.Transaction_Start();
					Con.getTransaction().setTimeout(10);	
					
					/*Die Lieste aller Spiele nach dieser UserID durchsuchen-----------------------Start*/
					List<Game_V1_01> list = Con.getSession().createCriteria(Game_V1_01.class,"u").list();
						// F�r jedes Spiel aus der Liste Aller:
						for(Game_V1_01 game : list){
							Boolean isPartof = false;
							// Hole die die Teilnehmerliste dieses Spiels
							List<User_V1_01> userlist = game.getUserlist();
							//Durchsuche die Teilnehmerliste nach der UserID
							for(User_V1_01 user : userlist)
							{	
								// ist der User Teilnehmer dann:
								if(user.getId() == userID){
									isPartof=true;
									break;
								}	
							}
							if(!isPartof)
							{
								listToReturn.add(game);	
							}
						}
				/*Die Lieste aller Spiele nach dieser UserID durchsuchen-----------------------Ende*/
				Con.getTransaction().commit();
				System.out.println(new Date()+" || Hibernate: Transaction comited");
				// Liste der Spiele dieses Spielers zur�ckgeben
				return listToReturn;
				
				}catch(RuntimeException e){
					try{
						Con.getTransaction().rollback();
						System.out.println("Rolback");
					}catch(RuntimeException rbe){
						//TODO Log f�r Transaktions rollback anlegen : log.error("Couldn�t roll back transaction", rbe);
					}
					throw e;
				}finally{
					if(Con.getSession()!=null){
						Con.getSession().close();
						System.out.println(new Date()+ " || Hibernate: Session Closed");
						//TODO Log f�r Hibernate Sessions anlegen
					}
					
				}
				
			}
	
			
	//===========================GetGameIdByUserId========================================================	GetGameIdByUserId	
		/* Erg�nzt am 12.01.2016 19:28 
		 * wird f�r den Session Controller gen�tigt,
		 * um neuen Sessions anhand der Spieler ID 
		 * zum jeweiligen Ziel dispatchen zu k�nnen*/
		/**
		 * Diese Methode ermittelt ob Ein User an einem laufenden Spiel Teilnimmt,
		 * tut er dies giebt Sie die Spiel ID zur�ck,
		 * ansonsten 0 ;
		 * @param id UserID 
		 * @return giebt einen Long wert zur�ck 
		 */
		public Long GetGameIdByUserId(Long userID){
				try{
					Con.Transaction_Start();
					Con.getTransaction().setTimeout(10);	
					/*Ein Spieler kann immer nur an einem Spiel Teilnehmen.
					 * => Durchsuche die Liste aller laufenden spiele nach meiner userID*/
					List<Game_V1_01> list = Con.getSession().createCriteria(Game_V1_01.class,"u").list();
					// Initialiesieren auf 0
					Long gameID = (long)0;
					for(Game_V1_01 game : list){	// alle laufenden Spiele
						if(game.getCurrentround() <= game.getMaxrounds())
						{	//Liste der Teilnehmer dieses Spiels
							List<User_V1_01> userlist = game.getUserlist();
							for(User_V1_01 user : userlist)
							{					
								if(user.getId() == userID)
								{	// nimmt ein User an einem Spiel teil, return spielID
									gameID = game.getId();
									return  gameID;
								}
							}
						}
					}

					Con.getTransaction().commit();
					System.out.println(new Date()+" || Hibernate: Transaction comited");
				}catch(RuntimeException e){
					try{
						Con.getTransaction().rollback();
						System.out.println("Rolback");
					}catch(RuntimeException rbe){
						//TODO Log f�r Transaktions rollback anlegen : log.error("Couldn�t roll back transaction", rbe);
					}
					throw e;
				}finally{
					if(Con.getSession()!=null){
						Con.getSession().close();
						System.out.println(new Date()+ " || Hibernate: Session Closed");
						//TODO Log f�r Hibernate Sessions anlegen
					}
				}
				return (long)0;
			}
		
		

		/**
		 * Diese Methode ermittelt das Spiel, welches zu der angegebenen gameId geh�rt
		 * @return Game_V1_01
		 * */
		public Game_V1_01 GetGameById(Long gameID){
			/**
			 * GetGameById bekommt eine gameID �bergeben und gibt das dazu passende Game zur�ck. 
			 */
			try{
				Con.Transaction_Start();
				Con.getTransaction().setTimeout(10);	

				List<Game_V1_01> list = Con.getSession().createCriteria(Game_V1_01.class).list();
				
				for(Game_V1_01 game : list){	// alle laufenden Spiele
					if(game.getId() == gameID)
					{	
						return game;
					}
				}

				Con.getTransaction().commit();
				System.out.println(new Date()+" || Hibernate: Transaction comited");
			}catch(RuntimeException e){
				try{
					Con.getTransaction().rollback();
					System.out.println("Rolback");
				}catch(RuntimeException rbe){
					//TODO Log f�r Transaktions rollback anlegen : log.error("Couldn�t roll back transaction", rbe);
				}
				throw e;
			}finally{
				if(Con.getSession()!=null){
					Con.getSession().close();
					System.out.println(new Date()+ " || Hibernate: Session Closed");
					//TODO Log f�r Hibernate Sessions anlegen
				}
			}
			return null;
		}
	/**
	 * Diese Funktion ermittelt, ob der User, welcher durch die �bergebene UserID
	 * repr�sentiert wird, teil eines OFFENEN/AKTIVEN Spiels ist.
	 * Offen ist ein Spiel, wenn noch Spieler beitreten k�nnen.
	 * Aktiv, wenn das Spiel voll ist und noch nicht beendet
	 * 
	 * @return true wenn er Teil des Spiels ist sonst false*/
	public boolean partofGame(Long id)
	{
		
		try{
			Con.Transaction_Start();
			Con.getTransaction().setTimeout(10);	

			List<Game_V1_01> list = Con.getSession().createCriteria(Game_V1_01.class,"u").list();
			for(Game_V1_01 game : list)
			{
				if(game.getCurrentround() <= game.getMaxrounds())
				{
					List<User_V1_01> userlist = game.getUserlist();
					for(User_V1_01 ulist : userlist)
					{					
						if(ulist.getId() == id)
						{
							return true;
						}
					}
				}
			}

			Con.getTransaction().commit();
			System.out.println(new Date()+" || Hibernate: Transaction comited");
		}catch(RuntimeException e){
			try{
				Con.getTransaction().rollback();
				System.out.println("Rolback");
			}catch(RuntimeException rbe){
				//TODO Log f�r Transaktions rollback anlegen : log.error("Couldn�t roll back transaction", rbe);
			}
			throw e;
		}finally{
			if(Con.getSession()!=null){
				Con.getSession().close();
				System.out.println(new Date()+ " || Hibernate: Session Closed");
				//TODO Log f�r Hibernate Sessions anlegen
			}
		}
		return false;
	}
	
		/**
		 * Diese Methode gibt alle Spiele zur�ck, bei denen noch Pl�tze f�r Spieler frei sind
		 * @return List<Game_V1_01>*/
		public List<Game_V1_01> openGames()
		{
			
			try{
				Con.Transaction_Start();
				Con.getTransaction().setTimeout(10);	
				
				List<Game_V1_01> gameList = new ArrayList<Game_V1_01>();
				List<Game_V1_01> list = Con.getSession().createCriteria(Game_V1_01.class,"u").list();
				for(Game_V1_01 game : list)
				{
					if(!game.isFull())
					{
						gameList.add(game);
					}
				}
				
				
				Con.getTransaction().commit();
				System.out.println(new Date()+" || Hibernate: Transaction comited");
				return  gameList;
			}catch(RuntimeException e){
				try{
					Con.getTransaction().rollback();
					System.out.println("Rolback");
				}catch(RuntimeException rbe){
					//TODO Log f�r Transaktions rollback anlegen : log.error("Couldn�t roll back transaction", rbe);
				}
				throw e;
			}finally{
				if(Con.getSession()!=null){
					Con.getSession().close();
					System.out.println(new Date()+ " || Hibernate: Session Closed");
					//TODO Log f�r Hibernate Sessions anlegen
				}
			}
		}
		
		/**
		 * Diese Methode gibt alle Spiele zur�ck, welche voll sind, aber noch nicht beendet
		 * @return List<Game_V1_01>*/
		public List<Game_V1_01> activeGames()
		{
			
			try{
				Con.Transaction_Start();
				Con.getTransaction().setTimeout(10);	

				List<Game_V1_01> gameList = new ArrayList<Game_V1_01>();
				List<Game_V1_01> list = Con.getSession().createCriteria(Game_V1_01.class,"u").list();
				for(Game_V1_01 game : list)
				{
					if(game.isFull())
					{
						gameList.add(game);
					}
				}	
				
				Con.getTransaction().commit();
				System.out.println(new Date()+" || Hibernate: Transaction comited");
				return  gameList;
			}catch(RuntimeException e){
				try{
					Con.getTransaction().rollback();
					System.out.println("Rolback");
				}catch(RuntimeException rbe){
					//TODO Log f�r Transaktions rollback anlegen : log.error("Couldn�t roll back transaction", rbe);
				}
				throw e;
			}finally{
				if(Con.getSession()!=null){
					Con.getSession().close();
					System.out.println(new Date()+ " || Hibernate: Session Closed");
					//TODO Log f�r Hibernate Sessions anlegen
				}
			}
		}
		
		/**
		 * Diese Funktion ermittelt anhand der gameID, welcher Spieler in dem Spiel welche
		 * Spielrolle �bernimmt(Wholesaler, Retailer, etc)
		 * @return List<HashMap>*/
		public List<HashMap> listRoleUser(long gameID)
		{
			
			try{
				Con.Transaction_Start();
				Con.getTransaction().setTimeout(10);	

				List<Game_V1_01> gamelist = Con.getSession().createCriteria(Game_V1_01.class,"u").list();
				List<Playsheet_V1_01> sheetlist = Con.getSession().createCriteria(Playsheet_V1_01.class).list();		 
				
				HashMap hMap;
				List<HashMap> hList = new ArrayList<HashMap>();

				for(Game_V1_01 game : gamelist)
				{					
					if(game.getId()== gameID)
					{						
						for(User_V1_01 user : game.getUserlist())
						{
							for(Playsheet_V1_01 sl : sheetlist)
							{
								if(user.getId()==sl.getOwner_id())	
								{
									hMap = new HashMap();
									hMap.put("role", sl.getGamerole());
									hMap.put("name", user.getUsername());
									hList.add(hMap);
								}
							}
						}
					}
				}	
						
				Con.getTransaction().commit();
				System.out.println(new Date()+" || Hibernate: Transaction comited");
				
				return  hList;
				
			}catch(RuntimeException e){
				try{
					Con.getTransaction().rollback();
					System.out.println("Rolback");
				}catch(RuntimeException rbe){
					//TODO Log f�r Transaktions rollback anlegen : log.error("Couldn�t roll back transaction", rbe);
				}
				throw e;
			}finally{
				if(Con.getSession()!=null){
					Con.getSession().close();
					System.out.println(new Date()+ " || Hibernate: Session Closed");
					//TODO Log f�r Hibernate Sessions anlegen
				}
			}
		}
	
		/**
		 * Diese Methode Pr�ft ob ein Game mit diesem Namen Bereits existiert
		 * activeGames gibt ein Liste mit allen laufenden Games zur�ck. 
		 * @param gamename String des Spiel Namens
		 * @return true =>  Name bereits vergeben | false Name nicht Vergeben
		 */
		public boolean existGame(String gamename)
		{
			try{
				Con.Transaction_Start();
				Con.getTransaction().setTimeout(10);	

				//List<Game_V1_01> gameList = new ArrayList<Game_V1_01>();
				List<Game_V1_01> list = Con.getSession().createCriteria(Game_V1_01.class,"u").list();
				for(Game_V1_01 game : list)
				{
					if(game.getGamename().equals(gamename))
					{
						return true;
					}
				}	
				
				Con.getTransaction().commit();
				System.out.println(new Date()+" || Hibernate: Transaction comited");
				return  false;
			}catch(RuntimeException e){
				try{
					Con.getTransaction().rollback();
					System.out.println("Rolback");
				}catch(RuntimeException rbe){
					//TODO Log f�r Transaktions rollback anlegen : log.error("Couldn�t roll back transaction", rbe);
				}
				throw e;
			}finally{
				if(Con.getSession()!=null){
					Con.getSession().close();
					System.out.println(new Date()+ " || Hibernate: Session Closed");
					//TODO Log f�r Hibernate Sessions anlegen
				}
			}
		}
}
