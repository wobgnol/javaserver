package de.moaiosbeer.rest_api;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

/**
 * Diese Klasse Dient dem Jersey Framework Jaxrs.ws für Rest,
 * als Globale Resourcen Configuration
 * 
 * Hier wird eingestellt Welche Klassen zum Frontend in Json gemapt werden können,
 * ob ein Nutzer Rollen check verwendet werden soll uvm siehe Jersey Dokumentation
 * @author Stephan
 */
public class MoaIosBeer extends ResourceConfig{
	
	public MoaIosBeer() {
		
			
			super(ApiUser_V1_01.class);					// <-- Konstrucktor der Resource 
		 	register(RolesAllowedDynamicFeature.class); // <--- Rollen Management
		 	register(JacksonFeature.class);    			// <----- Jackson JSON MApper Support
		 	packages("de.moaiosbeer.rest_api"); 		// <-- Zu Mappende API Resourcen
		 	packages("de.moaiosbeer.db.models");
   	}
	
}
