package de.moaiosbeer.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.AuthenticationException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;

import de.moaiosbeer.helper.HttpConstants;
import de.moaiosbeer.helper.JersyClientConfigHelper;
import de.moaiosbeer.helper.MoaHttpClient;
import de.moaiosbeer.helper.MoaHttpHeaderHelper;
import de.moaiosbeer.wrapers.MutableHttpServletRequest;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public LoginController() {super();
    }

    //  <!-- Login servlet erstellt muss noch verdrahtet werden -->
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 PrintWriter out = response.getWriter(  ); 
		 
//		 /* hat der nutzer Bereits eine Sitzung 
//			 * Dann Teste ob er ein aktuelles Spiel hat
//			 * hat er eins: redirect => playsheet.jsp (mode = "back" gameid= x)
//			 * hat er keins: redirect => gamelist.jsp */
//			if(null != request.getSession(false))	{ 
//				
//				/* If(hasGame == true){
//				 * redirect HisPlaysheet.jsp
//				 * }
//				 * else if ( ist das logoutflag "true"){ 
//				 * ausloggen 
//				 * }
//				 * else redirect gamelist */
//			}
//			else {}
	
		 
	// LOGOUT TEST-----------------------------------------------------------------------------START	 
		 /* Ist das logoutflag gesetzt logge den User aus*/
		 String logoutflag = (String)request.getParameter("logoutflag"); //Prüfwert für logout
		 // Ist das logout flagg gleich dem String "true" und  eine Session vorhanden
		 if("true".equals(logoutflag)){
			 
			 // Schreibe den usernamen in den Response Header für Verabschiedungssatz
			 response.addHeader("username", (String)request.getSession().getAttribute("username"));
			 // Browserchaching unterbinden
			 response.setHeader("Cache-Control","no-cache"); //HTTP 1.1 
			 response.setHeader("Pragma","no-cache"); //HTTP 1.0 
			 response.setDateHeader ("Expires", 0); //prevents caching at the proxy server 
			 response.sendRedirect("./gamelist.jsp"); //logged-in page 
			 /* wichtig nach Dispatching / redirecting immer return
			  * ansonsten kann code weiter unten ausgeführt werden */
			 
		
			 // Session beenden
			 request.getSession().invalidate();
			 
			 // Dispatch zur gamelist mit 200 ok 
			 RequestDispatcher rd = getServletContext().getRequestDispatcher("/gamelist.jsp");
			 response.setStatus(200); // http code vom response der Rest API weiter geben.
			 rd.forward(request, response);
			 
			 return;
		 }
		 
	
	// LOGOUT TEST-----------------------------------------------------------------------------Ende		
		 
	// Login Bereich-----------------------------------------------------------------------------Start		 
	try {
		
		/* Einen Neuen Http Clienten anlegen
		 * für den Usertest an der RestAPI erzeugen*/
		MoaHttpClient client = new MoaHttpClient();
		// den Clienten initialisieren
		client.setUSERNAME((String)request.getParameter("username")); 	// // Empfangener Username
		client.setPASSWORD((String)request.getParameter("password")); // 	// Empfangenes Passwort
		// Empfang/Sende-Typ einstellen
		client.setACCEPTHEADER(client.getHTTPCONST().MIMETYPE_TEXT_PLAIN); 
		client.setPAYLOADTYPE(client.getHTTPCONST().MIMETYPE_TEXT_PLAIN); 
		// login Url erstellen
		String loginURL = "http://localhost:8080/MoaIosBeer/rest/v1.01/login" ;
		// login mit den Userdaten und Antwort speichern
		ClientResponse res;
		// Die antwort des RestCalls speichern
		res = client.invokePost(loginURL,"{}");
		
		// Debug Ausgaben
		out.println("<h1> response:"+res+"</h1>");
		out.println("<h1> responsecode:"+res.getStatus()+"</h1>");
	
		
			if(res.getStatus()== 200){ // Valider Nutzer http 200 ok
				out.println("<h1> im IF </h1>");
				out.println("<h1>Username:"+request.getParameter("username")+"</h1>");
				/*A) getSession(true) will check whether a session already exists for the user. If yes, 
				 * 	 it will return that session object else it will create a new session object and return it.
				 * 
				 *B) getSession(false) will check existence of session. If session exists, 
				 *   then it returns the reference of that session object, if not, this methods will return null.
				 * */
				 HttpSession session = request.getSession(true); // A) aktiv
				 // Der Session das Attribut logedin verpassen
				 session.setAttribute("logedin", "true");
				 // Der Session das Attribut username verpassen ( z.B. für wilkommens Floskel auf der folgeseite )
				 String username = request.getParameter("username");
				 session.setAttribute("username", username );
				 out.println("<h1>UsernameAttribute:"+(String)session.getAttribute("username")+"</h1>");
				 session.setAttribute("Authorization",  (String)client.getEncodeedAuthString(client.getUSERNAME(), client.getPASSWORD()) );
				
				 out.println("<h1>AttributAuthorizationHeader:"+(String)session.getAttribute("Authorization")+"</h1>");
				 // Wieter zu nächsten seite
				
				 RequestDispatcher rd = getServletContext().getRequestDispatcher("/gamelist.jsp");
				 response.setStatus((int)res.getStatus()); // http code vom response der Rest API weiter geben.
				 rd.forward(request, response);
//				 response.sendRedirect("./gamelist.jsp"); //logged-in page 
				}
			else{
				// Invalider Nutzer bzw login fehler z.b. http 401 unauthorized
				/* die landingpage erwartet folgende Attribute 
				 * für die Fehlereingabe verarbeitung*/
				//String username = (String)request.getAttribute("username"); 
				//String username = (String)request.getAttribute("password"); 
				request.setAttribute("username", (String)request.getParameter("username") );
				request.setAttribute("password", (String)request.getParameter("password")  );
				 // Zurück zum Login
	//			 RequestDispatcher rd = request.getRequestDispatcher("./login.jsp");
	//			 response.setStatus(res.getStatus()); // http code vom response der Rest API weiter geben.
	//			 rd.forward(request, response);
				
			}// else ende
			
	} catch (AuthenticationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ClientHandlerException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	
	
	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
