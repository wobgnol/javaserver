/*
*************************************************************************
* 21.12.2015 															*
* Copyright © 2015-2016 Tim Langenbrink, Stephan Wissing, 				*
* 						Jonas Klein-Hitpaß 								*
*************************************************************************
* 																		*
* Autor 	   : Tim Langenbrink 										*
* Company 	   : / 														*
* File-Name    : Game_V1_01.java 										*
* Beschreibung : Diese Klasse bildet das Spiel mithilfe von Hibernate	*
* 				 auf die Datenbank ab und stellt alle benötigten		*
* 				 Funktionen für das Spiel zur Verfügung					*
* 																		*
*************************************************************************
*/
package de.moaiosbeer.db.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import de.moaiosbeer.dao.Game_V1_01_DaO;
import de.moaiosbeer.dao.Playsheet_V1_01_DaO;
import de.moaiosbeer.helper.GameClosedException;

@Entity
@Table (name="Game_V1_01")
public class Game_V1_01 {
	
	@Id
	@TableGenerator(name="Game_V1_01_ID", table="Game_V1_01_PK" , pkColumnName="Game_V1_01_ID" ,pkColumnValue="Game_V1_01_Value" , allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE , generator="Game_V1_01_ID")
	@Column(name="game_id")
	private long id;
	
	@Column(name="currentround")
	private int currentround;
	
	@Column(name="maxrounds")
	private int maxrounds;	

	@Column(name="gamename")
	private String gamename;
	
	@Transient
	private int activePlayer = -1;
	@Transient
	private String[] role = {"Wholesaler","Distributor","Retailer","Factory"};
	
	// wichtig ! FetchType.EAGER sonst kann Jakson nicht die games eines Student ausgeben:
	@ManyToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE) 
	private List<Playsheet_V1_01> playsheets = new ArrayList<Playsheet_V1_01>(0);

	
	// wichtig ! FetchType.EAGER sonst kann Jakson nicht die games eines Student ausgeben:
	@ManyToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE) 
	private List<User_V1_01> userlist = new ArrayList<User_V1_01>(0);
	
	/*
	 * Diese Funktion setzt die initial Werte eines neuen Spiels welches gestartet wird, sollte dies noch nicht geschehen sein
	 * */
	public void startGame()
	{
		if(activePlayer<0)
		{
			if(isFull())
			{
				activePlayer=0;
				currentround=1;
				maxrounds = 40;
				Playsheet_V1_01_DaO p = new Playsheet_V1_01_DaO();
				//Setze das Incoming der 2. Runde auf 0
				for(Playsheet_V1_01 ps : playsheets)
				{
					p.incommingIn2(ps.getId(), 0, 0);
					p.updateSheet(ps);
				}
				Game_V1_01_DaO g = new Game_V1_01_DaO();
				g.updateGame(this.getId(),currentround, maxrounds);
			}
		}
	}
	
	/*
	 * Diese Funktion überprüft anhand der userliste, ob das Spiel voll ist oder nicht
	 * */
	public Boolean isFull()
	{
		return (userlist.size()>=4);
	}

	/*
	 * Diese Funktion bekommt die aktuelle Order des aktiven Spielers übergeben
	 * und setzt im Playsheet die entsprechenden Daten
	 * */
	private void doOrder(int order) throws Exception
	{
		if(activePlayer>=0)
		{
			//TODO eventuell DaO aufrufe gegen REST aufrufe austauschen
			Playsheet_V1_01_DaO p = new Playsheet_V1_01_DaO();
			int nextRole = (activePlayer+1)%role.length;
//			int incoming = -1;
			for(Playsheet_V1_01 ps : playsheets){
				if(ps.getGamerole().equals(role[activePlayer]))
				{
					ps.setOurorder(order);
					for(Playsheet_V1_01 psn : playsheets)
					{
						if(psn.getGamerole().equals(role[nextRole]))
						{
							psn.calc(order);
							p.incommingIn2(ps.getId(), this.currentround, psn.getDelivery());
							p.updateSheet(psn);
							break;
						}
					}
					ps.setIncoming(p.get_incoming(ps.getId(), this.currentround));
					p.updateSheet(ps);
					ps.setRound(ps.getRound()+1);
					break;
				}
			}
//			if(incoming<0)
//			{
//				throw new Exception("Corrupt Game!");
//			}
		}
	}
	
	/*
	 * Diese Funktion gibt anhand der Rolle die Userid zurück, von dem User welcher momentan am Zug ist
	 * Sollte das Spiel voll sein, die Variable "activePlayer" aber noch nicht gesetzt sein, berechnet diese
	 * Funktion den initial Wert
	 * */
	public long getActiveUserid() throws Exception
	{
		if(activePlayer<0)
		{
			if(isFull())
			{
				if(currentround==0)
				{
					startGame();
				}
				else
				{
					activePlayer = 0;
					int z = -1;
					Playsheet_V1_01_DaO p = new Playsheet_V1_01_DaO();
					for(Playsheet_V1_01 ps : playsheets)
					{
						List<Playsheet_V1_01> list = p.get_whole_playsheet(ps.getId());
						if(z<0)
						{
							z=list.size();
						}
						else if(list.size()<z)
						{
							break;
						}
						else
						{
							activePlayer++;
						}
					}
					activePlayer++;
					activePlayer = activePlayer%4;
					//activePlayer = ((currentround%4)-1);
				}
			}
		}
		if(activePlayer>=0)
		{
			for(Playsheet_V1_01 ps : playsheets){
				if(ps.getGamerole().equals(role[activePlayer]))
				{
					return ps.getOwner_id();
				}
			}
			throw new Exception("Corrupt Game!");
		}
		return -1;
	}
	
	/*
	 * Diese Funktion überprüft ob das Spiel zuende ist
	 * Zuende ist ein Spiel, wenn die aktuelle Runde größer als die Maximale runde ist
	 * z.B.: Maxround = 40; currentround = 41
	 * */
	public Boolean isFinished()
	{
		return (currentround > maxrounds);
	}
	
	/*
	 * Diese Funktion führt die Entsrepchenden Funktionen aus, um einen Spielzug zu simulieren
	 * und überprüft ob der gewünschte Spieler am Zug ist und ob das Spiel noch offen ist*/
	public void performTurn(long userID, int newOrder) throws Exception
	{
		if(isFinished())
		{
			throw new GameClosedException("Game is already closed");
		}
		if(userID == getActiveUserid())
		{
			doOrder(newOrder);
			activePlayer++;
			if(activePlayer%4==0)
			{
				activePlayer=0;
				currentround++;
				Game_V1_01_DaO g = new Game_V1_01_DaO();
				g.updateGame(this.getId(),this.getCurrentround(), this.getMaxrounds());
			}
			return;
		}
		throw new Exception("It's not the player's turn");
	}
	
	/*
	 * Diese Funktion überprüft ob das Spiel den angegebenen User bereits enthält*/
	public Boolean containsPlayer(User_V1_01 user)
	{
		for(int i=0;i<userlist.size();i++)
		{
			if(userlist.get(i).getId() == user.getId())
			{
				return true;
			}
		}
		return false;
	}
	
	public Boolean containsrole(int roleId)
	{
		for(Playsheet_V1_01 ps : playsheets)
		{
			if(ps.getGamerole() == role[roleId])
			{
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Diese Funktion fügt den angegeben Nutzer zum Spiel hinzu, sofern dies noch nicht voll ist und
	 * der Spieler noch nicht teil des Spiels ist*/
	public void addPlayer(User_V1_01 user, int roleId) throws Exception
	{
		Playsheet_V1_01.setNextPlaysheetID();
		if(!isFull())
		{
			if(!containsPlayer(user))
			{
				if(!containsrole(roleId))
				{
					userlist.add(user);
					playsheets.add(new Playsheet_V1_01(user,role[roleId]));
					return;
				}
				throw new Exception("Role already in use");
			}
			throw new Exception("Player already in Game");
		}
		throw new Exception("Max amount of Players reached");
	}
	
	
	

	// Getter und Setter ------------------------------------Start
	
	public List<User_V1_01> getUserlist() {
		return userlist;
	}
	
	public void setUserlist(List<User_V1_01> userlist) {
		this.userlist = userlist;
	}
	
	public int getCurrentround() {
		return currentround;
	}

	public void setCurrentround(int currentround) {
		this.currentround = currentround;
	}

	public int getMaxrounds() {
		return maxrounds;
	}

	public void setMaxrounds(int maxrounds) {
		this.maxrounds = maxrounds;
	}


	public List<Playsheet_V1_01> getPlaysheets() {
		return playsheets;
	}

	public void setPlaysheets(List<Playsheet_V1_01> playsheets) {
		this.playsheets = playsheets;
	}

	public long getId() {
		return id;
	}
	
	public String getGamename() {
		return gamename;
	}

	public void setGamename(String gamename) {
		this.gamename = gamename;
	}
	// Getter und Setter ------------------------------------Ende
	
	
	// Konstrucktoren----------------------------------------Start
	public Game_V1_01() {
		// Standart Konstruktor
		
	}
	
// Timis AdUser Methode	
//	public void adusertogame(User_V1_01 user){
//		this.getUserlist().add(user);
//		this.getPlaysheets().add(new )
//	}
	
	public Game_V1_01(List<User_V1_01> Userlist) {
		
		// ist die Playerlist gefüllt ?
		if ( false == Userlist.isEmpty()){
			this.userlist = Userlist;
			System.out.println("\nGame_V1_01_Constructor adds Field => userlist.set(\""+Userlist+"\")");
			// Ja erstelle Liste von Playsheets 
			int i=0;
			for(User_V1_01 user: Userlist ){
				System.out.println("Game_V1_01_Constructor => called Playsheet_V1_01_Constructor to add FieldEntrys in => this.userlist.add(\"UserID: "+user.getId()+" | Username: "+user.getUsername()+"\")");
				this.playsheets.add(new Playsheet_V1_01(user,role[i++]));
			}	
			System.out.println("\n");
		}
		else {
			// Nein: keine Liste von Playsheets erzeugen
		}
		
	}
	// Konstrucktoren----------------------------------------Ende
	
}
