package de.moaiosbeer.rest_api;



import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

public class MoaIosBeer extends ResourceConfig{
	
	public MoaIosBeer() {
		
			
			super(ApiUser_V1_01.class);					// <-- Konstrucktor der Resource 
		 	register(RolesAllowedDynamicFeature.class); // <--- Rollen Management
		 	register(JacksonFeature.class);    			// <----- Jackson JSON MApper Support
		 	packages("de.moaiosbeer.rest_api"); 		// <-- Zu Mappende API Resourcen
		 	packages("de.moaiosbeer.db.models");
   	}
	
}
