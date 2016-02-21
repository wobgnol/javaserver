package de.moaiosbeer.helper;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.AuthenticationException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;

/***
 * Diese Klasse enth�lt wichtige Sitzungsdaten f�r den Gamecontroller.
 * @author Stephan
 */
public class SessionOwner {
	// Felder-----------------------------------------------------------------------------------Start	
	/** 
	 * Dieses Feld ist f�r die Aufnahme einer User ID gedacht.
	 * und zwar einer ID der klasse:
	 * de.moaiosbeer.db.models.User_V1_01.java*/
	private long ownerID =  0;
	/** 
	 * Dieses Feld ist f�r die Aufnahme einer Spiel ID gedacht.
	 * und zwar einer ID der klasse:
	 * de.moaiosbeer.db.models.Game_V1_01.java*/
	private long gameID = 0;
	/** 
	 * Dieses Feld ist f�r die Aufnahme einer Java HttpSession session gedacht.
	 * request.getSession().getId();*/
	private String sessionID = null;
	/** 
	 * Dieses Feld ist f�r die Aufnahme eines Nutzernamen gedacht, 
	 * welcher vom login erhalten wird*/
	private String name = null ;
	/** 
	 * Dieses Feld ist f�r die Aufnahme des HTTP Basic Authentification Strings gedacht.
	 * Er muss folgendem encoding entsprechen. 
	 * Zu erreichen:
	 * 1. mittels jersy Framwork => (import com.sun.jersey.core.util.Base64;)
	 * String auth = new String(Base64.encode(USERNAME+":"+PASSWORD));
	 * 2. mittels MoaHttpClient.java (de.moaiosbeer.helper.MoaHttpClient.java)
	 *  String auth = client.getEncodeedAuthString("username","password");*/
	private String auth = null;
	/** 
	 * Dieses Feld dient Controllern,die Inztanzen dieser Klasse nutzen,
	 * als Speicher f�r Relative Anwendungspfade.
	 * Welche f�r redirecting oder dispatching ben�tigt werden.
	 * (ein Client wird immer nur zu einer Folgeseite weitergeleitet)*/
	private String nextTargetURL = null;
	/** 
	 * Dieses Feld dient Controllern zur Speicherung der URL der zuvor angezeigte Seite,
	 * als relativer Pfad zur Anwendungswurzel.
	 * */
	private String lastTargetURL = null;
	/** 
	 * Dieses Feld dient Controllern,die Inztanzen dieser Klasse nutzen,
	 * als Speicher f�r die Aufforderungsflags.
	 * Welche f�r die Art und Weise der Requestverarbeitung dieses Clienten ben�tigt werden.
	 * (z.B. userAktion ="joinGame"; userAktion ="rejoinGame"; )*/
	private String userAktion = null;	
	/**Diese Feld dient dazu Antworten vom Server anzunehmen.*/
	private ClientResponse serverResponse = null;
	
	/**Dieses Feld dient dem jeweiligen Controller
	 * um zu erkennen ob eine Initialisierte Inztanz ein nutzer im System ist*/
	private boolean isValidUser = false;
	
	// Felder-----------------------------------------------------------------------------------ENDE	
	
	
	// Methoden --------------------------------------------------------------------------------Start	
	
	

	@Override
	public String toString(){
		String owner =""
				+"isValidUser:\t"+this.isValidUser+"\n"
				+"ownerID:\t"+this.ownerID+"\n"
				+"name:\t\t"+this.name+"\n"
				+"gameID:\t\t"+this.gameID+"\n"
				+"sessionID:\t"+this.sessionID+"\n"
				+"userAktion:\t"+this.userAktion+"\n"
				+"auth:\t\t"+this.auth+"\n"
				+"nextTarget:\t"+this.nextTargetURL+"\n"
				+"lastTarget:\t"+this.lastTargetURL+"\n"
				+"serverResponse:\t"+this.serverResponse+"\n";
		
		return owner;		
	}
	

	
	
	/**
	 * Diese Methode Autentifiziert die im Login eingegeben Nutzerdaten ( username und password),
	 * mittels RESTcall gegen die url /rest/loginCheck.
	 * 
	 * @param username String Parameter der Nutzernamen aufnimmt
	 * @param password String Parameter der Nutzerpaswort aufnimmt
	 * @return boolean True bei erfolgreichem login, false bei misserfolg.
	 * 
	 * Desweiteren wird die Klassenvariabele loginResponse dieser Inztanz mit (This.loginResponse) 
	 * durch die erhaltene Serverantwort des Client request �berschrieben.
	 * ClientResponse (com.sun.jersey.api.client.ClientResponse) mit der Server Antwort:
	 * a) http Statuscode 200 Ok =>  Valider Nutzer => return true.
	 * b) http Statuscode 401 Unauthorized => Unvalider Nutzer => return false.  
	 * c) http Statuscode 500 Internal Server Error => Methoden Exeption => return false
	 * ( AuthenticationException e  oder   ClientHandlerException e )
	 * 
	 * Der Statuscode des letzten requests, kann mit der Methodencascade:
	 * getLoginResponse().getStatus(); erhalten werden.
	 * 
	 * War der Nutzer Valide also giebt die Methode true zur�ck:
	 * So wird die Klassenvariabele auth dieser Inztanz (this.auth)�berschrieben:
	 * this.auth = client.getEncodeedAuthString(username, password);
	 * 
	 * War der Nutzer Invalid also giebt die Methode false zur�ck:
	 * So wird die Klassenvariabele auth dieser Inztanz (this.auth)�berschrieben:
	 * this.auth = client.getEncodeedAuthString(username, password);
	 */
	public boolean isValidUser (String username, String password){
	
		/* Klassenvariabelen bei jedem logincheck zur�cksetzen */
		// Leere Serverantwort erzeugen
		ClientResponse res = null;
		 //diese nimmt sp�ter die oben erzeugte serverantwort auf
		this.serverResponse = null;
		//diese nimmt sp�ter den Autorisierungstext auf
		this.auth = null;
		
		try {
			/* Einen Neuen Http Clienten anlegen
			 * f�r den Usertest an der RestAPI erzeugen*/
			MoaHttpClient client = new MoaHttpClient();
			// den Clienten initialisieren
			client.setUSERNAME(username); 	// Empfangener Username
			client.setPASSWORD(password);  	// Empfangenes Passwort
			// Empfang/Sende-Typ einstellen
			client.setACCEPTHEADER(client.getHTTPCONST().MIMETYPE_TEXT_PLAIN); 
			client.setPAYLOADTYPE(client.getHTTPCONST().MIMETYPE_TEXT_PLAIN); 
			// login Url erstellen
			String loginURL = "http://localhost:8080/MoaIosBeer/rest/v1.01/login" ;
			// Die antwort des RestCalls speichern
			res = client.invokePost(loginURL,"{}");
			
			// Die server Antwort in die Inztanz schreiben
			this.serverResponse = res;
			
			 /*Ist der Nutzer ein Valider Nutzer ? */
			if (res.getStatus() == 200){ 
				/* JA ! Valide
				 * Http ok Hier f�r User im System vorhanden
				 * siehe: de.moaiosbeer.rest_api.Login_Check_V1_1.java*/
				
				// Autorisierungstext zusammensetzten
				this.auth = client.getEncodeedAuthString(username, password);
				// Valider nutzer
				this.isValidUser = true;
				return true; 
			}
			else{ return false; } // Nein ! nicht Valide
			
		} catch (AuthenticationException e) {
			e.printStackTrace();
			// Autorisierungstext auf null setzen
			this.auth = null;
			// kein valider nutzer
			this.isValidUser = false;
			// loginresponse Statuscode �berschreiben
			this.serverResponse = res;
			this.serverResponse.setStatus(500); // Http 500 	Internal Server Error  ;
			// login fehlgeschlagen false r�ckgeben
			return false;
		} catch (ClientHandlerException e) {
			e.printStackTrace();
			// Autorisierungstext auf null setzen
			this.auth = null;
			// kein valider nutzer
			this.isValidUser = false;
			// loginresponse Statuscode �berschreiben
			this.serverResponse = res;
			this.serverResponse.setStatus(500); // Http 500 	Internal Server Error  ;
			// login fehlgeschlagen false r�ckgeben
			return false;
		}
	}
	
	
	
	/**Diese Methode giebt eine initialisierte SessionOwner Inztanz zur�ck. 
	 * Eine solche Inztanz dient den folgenden Controllern als hilfe f�r Seiten Redirecting
	 * bzw. Seiten Dispatching.
	 * 
	 * @param username username	(z.B. String aus Form request)
	 * @param password passwort (z.B. String aus Form request)
	 * @return SessionOwner Inztanz mit Attributen: namen,auth,ServerResponse,nextTargetUrl 
	 */
	public SessionOwner validateFactory(String username, String password){
		// leeren SessionOwner anlegen 
		SessionOwner internalOwner = new SessionOwner();
		
		/*Ruft die Inztansmethode =>
		 *isValidUser (username, password);
		 *Diese Testet ob der Nutzer ein Valider Nutzer im System ist.
		 *Ist das der Fall, so:
		 * 	  giebt die Methode true zur�ck
		 *und: 
		 * 	  �berschreibt die Inztanzvariabele auth => durch den login Token
		 *Sowie: 
		 *	  die Inztansvariabele serverResponse => durch die Serverantwort
		 * 
		 *  */
		if(true == internalOwner.isValidUser (username, password)){ 
			// JA ! dann:
			// username setzen
			internalOwner.setName(username);
			//relativer Pfad zur n�chsten Seite => auf gamlist.jsp setzen
			internalOwner.setNextTargetURL("/gamelist.jsp");
			
			//TODO this.ownerID = long getUserIDfromServer() SERVERABFRAGE ()  ;
			//TODO this.GameID = long hasGame(); giebt GameID dieses owners SERVERABFRAGE ()
			return internalOwner;
		}
		else 
			// NEIN ! dann:
			internalOwner.setName(username);
			//relativer Pfad zur n�chsten Seite => auf /login.jsp setzen
			internalOwner.setNextTargetURL("/login.jsp");
			return internalOwner;	
	}
	
	
	
	public String checkAndOverwriteNextTargetAndUserAktion(){
		String url = null;
		long nulll = 0;
		// Valider Spieler ohne Spiel Ziel=> /gamelist.jsp 
		if (this.isValidUser == true && this.gameID == nulll){
			 url = "/gamelist.jsp";
			this.nextTargetURL = url;
			return url;
		}
		// Valider Spieler mit Spiel Ziel=> /game = GameController
		if (this.isValidUser == true && this.gameID != nulll){
			 url = "/game";
			//mode = Playsheet anzeigen 
			this.userAktion = RequestConstants.MODE_SHOWPLAYSHEET;
			this.nextTargetURL = url;
			return url;
		}
		else 
			// nicht Valider Nutzer /login.jsp 
			url = "/login";
			return url;
	}
	
	
	
	/**
	 * Diese Methode ermittelt an einer SessionOwner Instanz,
	 * deren ownerID mittels Rest abfrage.
	 * Und schreibt die erhaltene Userid in den Owner.
	 * @return ClientResponse ( Serverantwort )
	 */
	public ClientResponse serverGiveMyID(){
		
		// Leeren Client response alnegen
		ClientResponse res = null;
		try {
			/* Einen Neuen Http Clienten anlegen*/
			MoaHttpClient client = new MoaHttpClient();
			// HasMap f�r username Password decoding 
			HashMap<String,String>  username_password = new HashMap<String,String>();
			/*String(username:password) am : Splitten und in Hashmap ablegen */
			username_password = client.getDecodedAuthString(auth);
			// den Clienten initialisieren
			client.setUSERNAME(username_password.get("username")); 	// username decoing aus Auth String
			client.setPASSWORD(username_password.get("password"));  // passwort decoing aus Auth String
			// Empfang/Sende-Typ einstellen
			client.setACCEPTHEADER(client.getHTTPCONST().MIMETYPE_TEXT_JSON); 
			client.setPAYLOADTYPE(client.getHTTPCONST().MIMETYPE_TEXT_JSON); 
			// login Url erstellen
			String UserIdByAuthTokenURL = "http://localhost:8080/MoaIosBeer/rest/v1.01/users/id/"+auth ;
			// Die antwort des RestCalls speichern
			res = client.invokeGet(UserIdByAuthTokenURL);
			
			// Die server Antwort in die Inztanz schreiben
			this.serverResponse = res;
			
			 /*Ist der Nutzer ein Valider Nutzer ? */
			if (res.getStatus() == 200){ 
				// Ja ! Valider Nutzer 
				
				try { // Nutzer ID aus der ServerAntwort Fishen und in diese Inztanz Schreiben
					
					/* einen Jackson object Mapper erzeugen 
					 * erm�glicht bidirektionales konvertieren von Json zu java Objekten*/ 
					ObjectMapper mapper = new ObjectMapper();
					
					/*Serverantwort im Json Format: 
					 * mittels 
					 * {id= EINE ID ALS Ziffer}
					 * z.B. 
					 * {id=1}*/
					String jsonInString = res.getEntity(String.class);
					
					/*Eine Map zur aufname der User id  erstellen.
					 * genereisch gehalten Typ wird bei Konvertirung von JSON => Map angegeben.*/
					Map<String, Object> map = new HashMap<String, Object>();

					/*Den Erhaltenen JSON String aus der ServerAntwort (z.B. "{id=1}" )
					 *in eine Java Map<String, Long> convertieren*/
					map = mapper.readValue(jsonInString, new TypeReference<Map<String, Long>>(){});
					this.ownerID  = (long)map.get("id");
						
					} catch (JsonGenerationException e) {
						e.printStackTrace();
						//Statuscode �berschreiben
						this.serverResponse = res;
						this.serverResponse.setStatus(500); // Http 500 	Internal Server Error  ;
						// login fehlgeschlagen false r�ckgeben
						return serverResponse;
					} catch (JsonMappingException e) {
						e.printStackTrace();
						//Statuscode �berschreiben
						this.serverResponse = res;
						this.serverResponse.setStatus(500); // Http 500 	Internal Server Error  ;
						// login fehlgeschlagen false r�ckgeben
						return serverResponse;
					} catch (IOException e) {
						e.printStackTrace();
						//Statuscode �berschreiben
						this.serverResponse = res;
						this.serverResponse.setStatus(500); // Http 500 	Internal Server Error  ;
						// login fehlgeschlagen false r�ckgeben
						return serverResponse;
					}
				return res;  // Http 200 OK => response zur�ck geben 
			}
			else{ return res;} // Nein ! nicht Valide 401 	Unauthorized => response zur�ck geben 
			
		} catch (AuthenticationException e) {
			e.printStackTrace();
			//Statuscode �berschreiben
			this.serverResponse = res;
			this.serverResponse.setStatus(500); // Http 500 	Internal Server Error  ;
			// login fehlgeschlagen false r�ckgeben
			return serverResponse;
		} catch (ClientHandlerException e) {
			e.printStackTrace();
			// Statuscode �berschreiben
			this.serverResponse = res;
			this.serverResponse.setStatus(500); // Http 500 	Internal Server Error  ;
			// login fehlgeschlagen false r�ckgeben
			return serverResponse;
		}
		
	}
	
	
	
	
/**
* Diese Methode ermittelt an einer SessionOwner Instanz,
* deren gameID mittels Rest abfrage.
* Und schreibt die erhaltene Gameid in den Owner.
* @return ClientResponse ( Serverantwort )
*/
public ClientResponse serverGiveMyGameID(){
		
		// Leeren Client response alnegen
		ClientResponse res = null;
		try {
			/* Einen Neuen Http Clienten anlegen*/
			MoaHttpClient client = new MoaHttpClient();
			// HasMap f�r username Password decoding 
			HashMap<String,String>  username_password = new HashMap<String,String>();
			/*String(username:password) am : Splitten und in Hashmap ablegen */
			username_password = client.getDecodedAuthString(auth);
			// den Clienten initialisieren
			client.setUSERNAME(username_password.get("username")); 	// username decoing aus Auth String
			client.setPASSWORD(username_password.get("password"));  // passwort decoing aus Auth String
			// Empfang/Sende-Typ einstellen
			client.setACCEPTHEADER(client.getHTTPCONST().MIMETYPE_TEXT_JSON); 
			client.setPAYLOADTYPE(client.getHTTPCONST().MIMETYPE_TEXT_JSON); 
			// login Url erstellen
			String GameIdByUserIDURL = "http://localhost:8080/MoaIosBeer/rest/v1.01/users/getgameidbyuserid/"+this.ownerID ;
			// Die antwort des RestCalls speichern
			res = client.invokeGet(GameIdByUserIDURL);
			
			// Die server Antwort in die Inztanz schreiben
			this.serverResponse = res;
			
			 /*Ist der Nutzer ein Valider Nutzer ? */
			if (res.getStatus() == 200){ 
				// Ja ! Valider Nutzer 
				
				try { // Nutzer ID aus der ServerAntwort Fishen und in diese Inztanz Schreiben
					
					/* einen Jackson object Mapper erzeugen 
					 * erm�glicht bidirektionales konvertieren von Json zu java Objekten*/ 
					ObjectMapper mapper = new ObjectMapper();
					
					/*Serverantwort im Json Format: 
					 * mittels 
					 * {id= EINE ID ALS Ziffer}
					 * z.B. 
					 * {id=1}*/
					String jsonInString = res.getEntity(String.class);
					
					/*Eine Map zur aufname der User id  erstellen.
					 * genereisch gehalten Typ wird bei Konvertirung von JSON => Map angegeben.*/
					Map<String, Object> map = new HashMap<String, Object>();

					/*Den Erhaltenen JSON String aus der ServerAntwort (z.B. "{id=1}" )
					 *in eine Java Map<String, Long> convertieren*/
					map = mapper.readValue(jsonInString, new TypeReference<Map<String, Long>>(){});
					this.gameID  = (long)map.get("id");
						
					} catch (JsonGenerationException e) {
						e.printStackTrace();
						//Statuscode �berschreiben
						this.serverResponse = res;
						this.serverResponse.setStatus(500); // Http 500 	Internal Server Error  ;
						// login fehlgeschlagen false r�ckgeben
						return serverResponse;
					} catch (JsonMappingException e) {
						e.printStackTrace();
						//Statuscode �berschreiben
						this.serverResponse = res;
						this.serverResponse.setStatus(500); // Http 500 	Internal Server Error  ;
						// login fehlgeschlagen false r�ckgeben
						return serverResponse;
					} catch (IOException e) {
						e.printStackTrace();
						//Statuscode �berschreiben
						this.serverResponse = res;
						this.serverResponse.setStatus(500); // Http 500 	Internal Server Error  ;
						// login fehlgeschlagen false r�ckgeben
						return serverResponse;
					}
				return res;  // Http 200 OK => response zur�ck geben 
			}
			else{ return res;} // Nein ! nicht Valide 401 	Unauthorized => response zur�ck geben 
			
		} catch (AuthenticationException e) {
			e.printStackTrace();
			//Statuscode �berschreiben
			this.serverResponse = res;
			this.serverResponse.setStatus(500); // Http 500 	Internal Server Error  ;
			// login fehlgeschlagen false r�ckgeben
			return serverResponse;
		} catch (ClientHandlerException e) {
			e.printStackTrace();
			// Statuscode �berschreiben
			this.serverResponse = res;
			this.serverResponse.setStatus(500); // Http 500 	Internal Server Error  ;
			// login fehlgeschlagen false r�ckgeben
			return serverResponse;
		}
		
	}
	

public String getAuthString(String username,String password){
	MoaHttpClient client = new MoaHttpClient();
	return client.getEncodeedAuthString(username, password);
}



public boolean registerMeAtServer(){
	
	// Leeren Client response alnegen
	ClientResponse res = null;
	try {
		/* Einen Neuen Http Clienten anlegen*/
		MoaHttpClient client = new MoaHttpClient();
		// HasMap f�r username Password decoding 
		HashMap<String,String>  username_password = new HashMap<String,String>();
		/*String(username:password) am : Splitten und in Hashmap ablegen */
		//username_password = client.getDecodedAuthString(auth);
		// den Clienten initialisieren
		client.setUSERNAME("admin"); 	// username decoing aus Auth String
		client.setPASSWORD("admin");  // passwort decoing aus Auth String
		// Empfang/Sende-Typ einstellen
		client.setACCEPTHEADER(client.getHTTPCONST().MIMETYPE_TEXT_JSON); 
		client.setPAYLOADTYPE(client.getHTTPCONST().MIMETYPE_TEXT_JSON); 
		// login Url erstellen
		String UserIdByAuthTokenURL = "http://localhost:8080/MoaIosBeer/rest/v1.01/users/newuser_v1_01/"+this.auth ;
		
		// Die antwort des RestCalls speichern
		res = client.invokeGet(UserIdByAuthTokenURL);
		
		// Die server Antwort in die Inztanz schreiben
		this.serverResponse = res;
		
		 /*Ist der Nutzer ein Valider Nutzer ? */
		if (res.getStatus() == 201){ 
			// Ja ! Valider Nutzer 		
			// Registrierung ging durch 
			return true;  // Http 200 OK => response zur�ck geben 
		}
		else{ return false;} // Nein ! nicht Valide 401 	Unauthorized => response zur�ck geben 
		
	} catch (AuthenticationException e) {
		e.printStackTrace();
		//Statuscode �berschreiben
		this.serverResponse = res;
		this.serverResponse.setStatus(500); // Http 500 	Internal Server Error  ;
		// login fehlgeschlagen false r�ckgeben
		return false;
	} catch (ClientHandlerException e) {
		e.printStackTrace();
		// Statuscode �berschreiben
		this.serverResponse = res;
		this.serverResponse.setStatus(500); // Http 500 	Internal Server Error  ;
		// login fehlgeschlagen false r�ckgeben
		return false;
	}
	
}
	
	
	
	// Methoden --------------------------------------------------------------------------------Ende	
	
	
	// Getter / Setter -------------------------------------------------------------------------Start	
	public long getOwnerID() {
		return ownerID;
	}
	public void setOwnerID(long ownerID) {
		this.ownerID = ownerID;
	}
	public long getGameID() {
		return gameID;
	}
	public void setGameID(long gameID) {
		this.gameID = gameID;
	}
	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getNextTargetURL() {
		return nextTargetURL;
	}
	public void setNextTargetURL(String nextTargetURL) {
		/* last Target muss immer die vorherige Seites ein 
		 * war dies keins so ist es nun null */
		this.lastTargetURL = this.nextTargetURL;
		this.nextTargetURL = nextTargetURL;
	}
	public String getLastTargetURL() {
		return lastTargetURL;
	}
	public void setLastTargetURL(String lastTargetURL) {
		this.lastTargetURL = lastTargetURL;
	}
	public String getUserAktion() {
		return userAktion;
	}
	public void setUserAktion(String userAktion) {
		this.userAktion = userAktion;
	}
	/* Nur Getter der response darf nur von der Methode
	 * isValidUser gesetzt werden*/
	public ClientResponse getServerResponse() {
		return serverResponse;
	}
	public boolean getIsValidUser() {
		return isValidUser;
	}

	public void setIsValidUser(boolean loginflag) {
		this.isValidUser = loginflag;
	}
	// Getter / Setter -------------------------------------------------------------------------Start
	
	//TODO Server Loging f�r Exeption Handling einbauen
}
