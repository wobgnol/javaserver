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
 * An dieser URL [ ./rest/v1.01/login ] kann mittels Get oder Post verifiziert werden,
 * ob ein Nutzer ein Valider Nutzer im System ist.
 * Dieser API Pfad dient dem Login_Servlet / bzw der Swift app  als Nutzer Validierung.
 */
@Path("v1.01/login")
public class Login_Check_V1_1 {

	
	  /**
	  * An dieser URL [ ./rest/v1.01/login ] kann mittels Get verifiziert werden,
	  * ob ein Nutzer ein Valider Nutzer im System ist.
	  * Hierzu muss der Client einen Basic Auth Http Header an diese Resource senden.
	  * @return Valider Nutzer: 200 ok	|	Nicht Valider: 401 	Unauthorized
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
		  * @return Valider Nutzer: 200 ok	|	Nicht Valider: 401 	Unauthorized
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