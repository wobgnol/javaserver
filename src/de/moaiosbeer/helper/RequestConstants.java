package de.moaiosbeer.helper;

public class RequestConstants {
	
	/* Dieses Attribut stellt die Hilfsklasse de.moaiosbeer.helpers.SessionOwner.java zur Verfügung,
	 * welche für die Nutzerzugeordneten Aktion genutzt wird.
	 * Aus der Session holen => SessionOwner owner = (SessionOwner) session.getAttribute("sessionOwner"); */
	public static final String ATTR_SESSION_OWNER = "sessionOwner";
	//Hier wird die GameID des Spiels angegeben werden, auf den der Mode angewandt werden soll
	public static final String ATTR_GAMEID = "gameid";
	//Hier wird angegeben, was der user machen möchte
	public static final String ATTR_REQUESTMODE = "mode";
	//Hier wird die userid des anfragenden Spielers gesichert(MODE_LOGIN/-LOGOUT des eingeloggten Users)
	public static final String ATTR_USERID = "userid";
	//Hier wird die neue Order des Spielers angegeben
	public static final String ATTR_ORDER = "order";
	
	//Gibt an, dass...
	//...ein Spieler einen Spielzug machen möchte
	public static final String MODE_TURN = "turn";
	//...ein Spieler ein Spiel erstellen möchte
	public static final String MODE_CREATE = "create";
	//...ein Spieler einem bestehenden offenen Spiel beitreten möchte
	public static final String MODE_JOIN = "join";
	//...die Teilnehmerliste eines offenen Spiels zurückgegeben werden soll
	public static final String MODE_SHOWGAME = "showgame";
	//...das Playsheet zurückgegeben werden soll
	public static final String MODE_SHOWPLAYSHEET = "showsheet";
	//...Polling, um zu wissen ob der pollende Spieler am Zug ist
	public static final String MODE_POLLING = "poll";
	//...das sich ein neuer Spieler eingeloggt hat
	public static final String MODE_LOGIN = "login";
	//...das sich ein Spieler ausgeloggt hat
	public static final String MODE_LOGOUT = "logout";
}
