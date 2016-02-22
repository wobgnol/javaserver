package de.moaiosbeer.rest_api;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * Dieser Api Pfad [ ./rest/v1.01/login] Ist der Hauptaufsatzpunkt der Login_Check_V1_1
 * Dieser Api Pfad dient allein der Nutzer Authorisierung und Authentifizierung.
 * 
 * Http Methoden mit Methoden Auffruf an dieser URL: GET oder POST
 * Weitere Methoden Erlaubt aber geben keinen Content zurück
 * 
 * Mögliche Fälle beim rufen dieser URL:
 * Kein user im System: 
 * response => Http 403 Forbidden
 * 
 * User Im System aber Falsche Rolle für den Aufruf: 
 * response => Http 401 Unauthorized
 * 
 * User Im System mit gültiger Rolle => 
 * response Http 200 Ok
 * 
 * 
 * Server bezogene Fehler
 * Server Fehler: 
 * response => Http 500 Server Error
 * 
 * Request Fehler: 
 * response => Http 400 Bad Request
 * @author Stephan
 * 
 */
@Path("v1.01/login")
public class Login_Check_V1_1 {

	
	  /**
	  * An dieser URL [ ./rest/v1.01/login ] kann mittels Get verifiziert werden,
	  * ob ein Nutzer ein Valider Nutzer im System ist.
	  * Hierzu muss der Client einen Basic Auth Http Header an diese Resource senden.
	  * @return Valider Nutzer: 200 ok	|	Nicht Valider: 401 	Unauthorized | Clientzugriff ohne Auth  403 Forbidden
	  */
	  @GET
	  @RolesAllowed({"admin","player","manager-gui"})
	  @Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML,MediaType.TEXT_PLAIN})
	  @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML,MediaType.TEXT_PLAIN})
	  public Response get_Autorisation() {
		// Mit Http statuscode 200 = ok antworten  
		return Response.status(200).entity("Login: success").build();
	  }
	  
	  /**
		  * An dieser URL [ ./rest/v1.01/login ] kann mittels Post verifiziert werden,
		  * ob ein Nutzer ein Valider Nutzer im System ist.
		  * Hierzu muss der Client einen Basic Auth Http Header an diese Resource senden.
		  * @return Valider Nutzer: 200 ok	|	Nicht Valider: 401 	Unauthorized |  Clientzugriff ohne Auth  403 Forbidden
		  */
	  @POST
	  @RolesAllowed({"admin","player","manager-gui"})
	  @Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML,MediaType.TEXT_PLAIN})
	  @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML,MediaType.TEXT_PLAIN})
	  public Response post_Autorisation() {
		  // Mit Http statuscode 200 = ok antworten 
		  return Response.status(200).entity("Login: success").build();
	  }	
}