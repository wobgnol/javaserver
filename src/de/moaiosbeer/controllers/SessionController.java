package de.moaiosbeer.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.sun.jersey.api.client.ClientResponse;

import de.moaiosbeer.helper.RequestConstants;
import de.moaiosbeer.helper.SessionOwner;


/**
 * Servlet implementation class SessionController
 */
@WebServlet("/SessionController")
public class SessionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SessionController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	 // Konstanten werte
	RequestConstants requestConstants = new RequestConstants();
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		PrintWriter out = response.getWriter(); 	// Zum Debugging einkommentieren
		// login parameter aus dem Request holen 
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		/*!!!!!! WICHTIG AUS DER API BESCHREIBUNG ENTNOMMEN !!!!!!!!!!!
		 * A) getSession(true) will check whether a session already exists for the user. If yes, 
		 * 	 it will return that session object else it will create a new session object and return it.
		 * 
		 *B) getSession(false) will check existence of session. If session exists, 
		 *   then it returns the reference of that session object, if not, this methods will return null.
		 * */
		// �berschreibt das neue Session objekt entweder mit null oder einer vorhanden Session
		HttpSession session = request.getSession(false); // giebt null zur�ck oder die Session
		SessionOwner owner = null; // Gloabel sitzungsmember anlegen
		
		if(session == null){// User ohne Session------------------------------------------------------start
			owner  = new SessionOwner(); // neuen Sitzungsbesitzer anlegen und zuweisen
			
			/* Die Session ist null der User hatte keine aktuelle Sitzung 
			 * => neu angelegte weiter verwenden und �berschreiben*/
			
			/*validateFactory(username,password),
			 *validiert ob die username/password Kompi im System vorhanden ist.
			 *Desweiteren giebt sie immer eine initialisierte Inztans eines SessionOwners zur�ck.
			 * 
			 *Folgende Felder werden �beschrieben:
			 *A)Valider Nutzer: 
			 *	=> nextTargetUrl = "/gamelist.jsp",name="username",serverResponse="Statuscode" 
			 *
			 *B)nicht Valider Nutzer: 	
			 *	=> nextTargetUrl= "/login.jsp",name="username"serverResponse="Statuscode" 
			 *
			 *die restlichenFelder werden auf null bzw 0 gesetzt */
			owner = owner.validateFactory(username, password);
			
			//--------------Valider User im System ohne Session---------------------------------------------------------------Start
			/* wenn es ein User im System ist, 
			 * und dieser keine Session besitzt dann:*/
			if(owner.getIsValidUser() == true){ 
				
					//weise ihm eine neue session zu
					session = request.getSession(true);	// Giebt alte session zur�ck oder erstellt neue
					//Session id �bergeben
					owner.setSessionID(session.getId());
					/* Holt sich die User ID von der REST-API und schreibt diese in die Inztanz.
					 * Au�erdem �berschreibt die Methode das Feld ServerResponse = neue Antwort der anfrage*/
					owner.serverGiveMyID();
					/* Holt sich die Game ID von der REST-API und schreibt diese in die Inztanz.
					 * Hat der Nutzer kein Spiel,
					 * Dann wird owner.setOwnerID(o); <= hatte kein laufendes Spiel
					 * ansonsten wird owner.setOwnerID(ErhalteneUserIdVomServer); <= hatte laufendes Spiel
					 * Au�erdem �berschreibt die Methode das Feld ServerResponse = neue Antwort der anfrage
					 * */
					owner.serverGiveMyGameID();
					// der session die owner Instanz hinzuf�gen ( damit arbeiten die Contoller ) 
					session.setAttribute("sessionOwner", owner);	
					/* Dieses Feld enth�lt immer die Relative Url zum n�chsten Ziel
					 * Die Methode: String checkAndOverwriteNextTarget(), 
					 * �berpr�ft wie das n�chste Ziel sein muss:
					 * =>	Valider Spieler ohne Spiel Ziel=> /gamelist.jsp 
					 * => 	Valider Spieler mit Spiel Ziel=> /game = GameController	
					 * => 	nicht Valider Nutzer /login.jsp 
					 * Au�erdem wird owner.setUserAktion(userAktion)gesetzt:
					 * =>	Valider Spieler ohne Spiel  owner.setUserAktion(null);
					 * => 	Valider Spieler mit Spiel 	owner.setUserAktion(RequestConstants.MODE_SHOWPLAYSHEET);
					 * => 	nicht Valider Nutzer 		owner.setUserAktion(null);
					 * */
					String nextTarget = owner.checkAndOverwriteNextTargetAndUserAktion();
					if(nextTarget.equals("/game")){
						request.setAttribute("mode", RequestConstants.MODE_SHOWGAME);
						request.setAttribute("gameid", owner.getGameID());
					}
					// Dispatcher Instanzieren
					RequestDispatcher rd = getServletContext().getRequestDispatcher(nextTarget);
					// Request forwarding ( weiterleitung )
					rd.forward(request, response);
					
					// Ja ! Valider Nutzer der zuvor keine Sitzung hatte	
					// out.println("Ich hatte zuvor keine Sitzung aber habe jetzt eine bekommen"+owner.toString()); // Debug Zeile
					return;
			}//--------------Valider User im System ohne Session---------------------------------------Ende
			//--------------!NICHT! Valider User im System ohne Sessionn-------------------------------Start
			
			/* damit der User seine Fehleingabe 
			 * korrigieren kann wird diese zur�ck gesendet zum login*/
			request.setAttribute("username", username);
			request.setAttribute("password", password);
			response.setStatus(owner.getServerResponse().getStatus());
			// out.println(owner.getNextTargetURL()); // Debug Zeile f�r owner attribute
			String nextTarget = owner.getNextTargetURL();
			/*Dispatch to => /login.jsp*/
			RequestDispatcher rd = getServletContext().getRequestDispatcher(nextTarget);
			// Request forwarding ( weiterleitung )
			rd.forward(request, response);
			// Nein ! Nicht Valider Nutzer der zuvor keine Sitzung hatte	
			// out.println("Ich bin kein Valider Nutzer"); // Debug Zeile
			return;
			
			//--------------!NICHT! Valider User im System ohne Sessionn-------------------------------Ende
			
			
		}//User ohne Session-------------------------------------------------------------------------------------------------Ende
		else if (session != null ){//User Mit Session-----------------------------------------------------------------------Start
			
				// testen ob ein Logout erw�nscht ist
				String logoutflag = (String)request.getParameter("logoutflag");
				//Testen ob logout erw�nscht ist
				if ("true".equals(logoutflag) == false){ // Nein ! kein logou erw�nscht--------------------------------Start
					
						/* TODO Ablauf:
						 * a)alte Session behalten || 
						 * b)testen ob Spiel vorhanden ?
						 * 	=>		  Vorhanden: Redirect zum alten Spiel
						 *  =>	Nicht vorhanden: Redirect gamelist*/
						
						// b) 
						/*Eine Inztanz der Helperklasse SessionOwner anlegen.
						 *Paket: de.moiosbeer.helpers */	
						owner = (SessionOwner) session.getAttribute("sessionOwner");
						// 	out.println("Ich habe Sitzung und kein laufendes Spiel"+owner); // Debug Zeile
						// Wie oben NextTargetURL setzen und mode einstellen
						String nextTarget = owner.checkAndOverwriteNextTargetAndUserAktion();
						if(nextTarget.equals("/game")){
							request.setAttribute("mode", RequestConstants.MODE_SHOWGAME);
							request.setAttribute("gameid", owner.getGameID());
						}
						 // Dispatch zum Login
						 RequestDispatcher rd = getServletContext().getRequestDispatcher(nextTarget); 	//  Ziel: /gamelist.jsp 
						 rd.forward(request, response);
						return;	
				}//logout nicht erw�nscht----------------------------------------------------------------------------Ende
				else{//logout erw�nscht ------------------------------------------------------------------------------Start
					/*ist das logoutflag gesetzt dispatche zum game Controller,
					 * mit dem request Parameter mode = RequestConstants.MODE_LOGOUT
					 * Der GameController antwortet mit einem �berschriebenen Sessionowner.
					 * 
					 * Nur wenn request Parameter mode und owner.userAktion == RequestConstants.MODE_LOGOUT der Antwort gillt,
					 * dann wird die Sitzung beendet !
					 * 
					 * das wird folgend �berpr�ft */
					// owner aus der Session fischen
					owner = (SessionOwner) session.getAttribute("sessionOwner");
					// logoutflag auf false setzen erm�glicht neuen login bei gleichersitzung
					request.setAttribute("logoutflag", "false");
					 // Schreibe den usernamen in den Response Header f�r Verabschiedungssatz
					 response.addHeader("username", (String)request.getSession().getAttribute("username"));
					 // Server Antwort f�r JSP erstellen
					 response.setStatus(1010101); 
					 response.setHeader("logouttext",  "Aufwedersehen " +owner.getName()
					  +"<br>Sie haben sich erfolgreich Ausgelogt "); 
					 // Leeren owner anlegen alle Felder null oder 0
					 SessionOwner newOwner = new SessionOwner();
					 // den leeren owner an die Session h�ngen 
					 session.setAttribute("sessionOwner", newOwner);
					 //Dispatch zum Login.jsp
					 RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
					 rd.forward(request, response);
					 // die Session beenden
					 session.invalidate();
					 return;
				}
		
			
				
			
				//logout erw�nscht ------------------------------------------------------------------------------Ende
				
				
		}//User Mit Session------------------------------------------------------------------------------------------------Ende
		
		
		 //Testen auf Logout; Session behalten und Dispatchen  Ende*/
	
		// else session killen dispatch => login
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

}
