/*
*************************************************************************
* 21.12.2015 															*
* Copyright � 2015-2016 Tim Langenbrink, Stephan Wissing, 				*
* 						Jonas Klein-Hitpa� 								*
*************************************************************************
* 																		*
* Autor 	   : / 														*
* Company 	   : / 														*
* File-Name    : Playsheet_V1_01.java 									*
* Beschreibung : Diese Klasse bildet das Playsheet mithilfe von 		*
* 				 Hibernate auf die Datenbank ab und stellt alle 		*
* 				 ben�tigten Funktionen f�r die Playsheet Verwaltung		*
* 				 zur Verf�gung											*
* 																		*
*************************************************************************
*/

package de.moaiosbeer.db.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import de.moaiosbeer.dao.Playsheet_V1_01_DaO;

@Entity
@Table (name="Playsheet_V1_01")
public class Playsheet_V1_01 {

//	@Id
//	@TableGenerator(name="Playsheet_V1_01_ID", table="Playsheet_V1_01_PK" , pkColumnName="Playsheet_V1_01_ID" ,pkColumnValue="Playsheet_V1_01_Value" , allocationSize=1)
//	@GeneratedValue(strategy=GenerationType.TABLE , generator="Playsheet_V1_01_ID")
//	@Column(name="playsheetid")
//	private long id;
	
	/*Ein zusammengesetzter Prim�rschl�ssel, welcher durch eine Klasse
	 * repr�sentiert wird*/
	@EmbeddedId
	@LazyCollection(LazyCollectionOption.FALSE)
	private PlaysheetPK pk;
	
	@Column(name="owner_id")
	private long owner_id;
	
	@Column(name="gamerole")
	private String gamerole;    // Rolle im Spiel
	
	@Column(name="incoming")
	private int incoming;
	
//	@Column(name="round")
//	private int round;
	
	@Column(name="totalcosts")
	private double totalcosts;
	
	@Column(name="inventory")
	private int inventory;
	
	@Column(name="backorder")
	private int backorder;
	
	@Column(name="neworder")
	private int neworder;
	
	@Column(name="ourorder")
	private int ourorder;
	
	@Column(name="delivery")
	private int delivery;
	
	@Transient
	static int nextID = -1;
	//@ManyToMany(cascade = CascadeType.ALL)
	//@LazyCollection(LazyCollectionOption.FALSE) 
	//private List<Game_V1_01> gamelist = new ArrayList<Game_V1_01>(0);
	
//	public List<Game_V1_01> getGamelist() {
//		return gamelist;
//	}
//
//	public void setGamelist(List<Game_V1_01> gamelist) {
//		this.gamelist = gamelist;
//	}
	/*
	 * Diese Funktion fragt gegen die Datenbank, welches die n�chste Freie Playsheet id ist.
	 * Diese wird durch die Menge der vorhandenen Playsheets festgelegt.
	 * Die Abfrage wird nur ausgef�hrt, falls die ID nocht nicht bekannt ist*/
	public static void setNextPlaysheetID()
	{
		if(nextID<0)
		{
			nextID = new Playsheet_V1_01_DaO().get_next_playsheetid();
		}
	}

	public long getId() {
	 	 return pk.getId(); 
	}
	
	public String getGamerole() {
	 	 return gamerole; 
	}
	
	public void setGamerole(String gamerole) { 
		 this.gamerole = gamerole; 
	}
	
	public int getIncoming() {
	 	 return incoming; 
	}
	
	public void setDelivery(int delivery) { 
		 this.delivery = delivery; 
	}
	
	public int getDelivery() {
	 	 return delivery; 
	}
	
	public void setIncoming(int incoming) { 
		 this.incoming = incoming; 
	}
	
	public int getRound() {
	 	 return pk.getRound(); 
	}
	
	public void setRound(int round) { 
		 pk.setRound(round); 
	}
	
	public long getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(long owner_id) {
		this.owner_id = owner_id;
	}

	public double getTotalcosts() {
	 	 return totalcosts; 
	}
	
	public void setTotalcosts(double totalcosts) { 
		 this.totalcosts = totalcosts; 
	}
	
	public int getInventory() {
	 	 return inventory; 
	}
	
	public void setInventory(int inventory) { 
		 this.inventory = inventory; 
	}
	
	public int getBackorder() {
	 	 return backorder; 
	}
	
	public void setBackorder(int backorder) { 
		 this.backorder = (backorder<0?0:backorder); 
	}
	
	public int getNeworder() {
	 	 return neworder; 
	}
	
	public void setNeworder(int neworder) { 
		 this.neworder = neworder; 
	}
	
	public int getOurorder() {
	 	 return ourorder; 
	}
	
	public void setOurorder(int ourorder) { 
		 this.ourorder = ourorder; 
	} 

	/*
	 * Diese Funktion setzt alle Werte, welche "gesetzt" werden k�nnen, sobald
	 * eine Order eines anderen Spieler bei dem entsprechenden Spieler eingeht*/
	public void calc(int neworder)
	{
		//setRound(pk.getRound()+1);
	    setNeworder(neworder);
	    setDelivery(((this.inventory-neworder)<0?this.inventory:neworder));
		setInventory(this.incoming  + this.inventory - this.backorder - this.neworder);
		setBackorder(this.neworder-this.inventory);
		setTotalcosts(this.inventory*0.5+backorder);
	}
	
	// Konstrucktoren----------------------------------------Start
			public Playsheet_V1_01() {
				// Standart Konstruktor
			}
			
			/*
			 * Eine art Copy_Konstruktor*/
			public Playsheet_V1_01(Playsheet_V1_01 ps)
			{
				this.pk = new PlaysheetPK();
				this.pk.setId(ps.pk.getId());
				this.setGamerole(ps.getGamerole());
				this.setOwner_id(ps.getOwner_id());
			}
			
			public Playsheet_V1_01(User_V1_01 owner, String role) {
				System.out.println("Playsheet_V1_01_Constructor initialize a Playsheet");
				// Den playsheet einen Besitzer und ein Spiel zuordnen
				this.owner_id = owner.getId();
				this.gamerole = role;
				// restliche Attribute Initialisieren
				this.inventory = 15; 	//Start Wert im Inventar
				this.incoming = 0; 		//demn�chst Eintreffende Lieferung
				pk = new PlaysheetPK(nextID++, 1);
				this.backorder = 0;		/* usw Initialisierung der Startwerte*/
				this.ourorder = 0;		
				this.neworder = 0;
				this.totalcosts =0;
			}		
			// Konstrucktoren----------------------------------------Ende
	
}
