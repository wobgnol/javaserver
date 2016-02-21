package de.moaiosbeer.tests;

import java.util.HashMap;

import javax.naming.AuthenticationException;
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import de.moaiosbeer.helper.MoaHttpClient;

public class Helper_MoaHttpClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MoaHttpClient client = new MoaHttpClient();
		
		try {
			System.out.println("Start");
			ClientResponse res ;
			client.setUSERNAME("Tim");
			client.setPASSWORD("1234");
			client.setACCEPTHEADER(client.getHTTPCONST().MIMETYPE_TEXT_PLAIN);
			client.setPAYLOADTYPE(client.getHTTPCONST().MIMETYPE_TEXT_PLAIN);
			String usersURL = "http://localhost:8080/MoaIosBeer/rest/v1.01/login" ;
			
			
			//A) Get ohne Request-Parameter absenden
			res = client.invokeGet(usersURL);
			System.out.println("\nA) Get ohne Request-Parameter absenden\n Statuscode: "+res.getStatus()+" Inhalt: "+res.getEntity(String.class) +"Request: "+res );
			System.out.println(client.getEncodeedAuthString(client.getUSERNAME(),client.getPASSWORD()));
		
			// B) Post ohne Request-Parameter absenden
			String payload = "{\"username\":\"User 7\",\"password\":\"12345\"}";
			res = client.invokePost(usersURL, payload);
			System.out.println("\nB) Post ohne Request-Parameter absenden\n Statuscode: "+res.getStatus()+" Inhalt: "+res.getEntity(String.class) +"Request: "+res );
			
			
			// Liste von Request-Parametern anlegen
			MultivaluedMap<String, String> requestParameters = new MultivaluedMapImpl();
			requestParameters.add("gameid", "1");
			client.setQUERRYPARMS(requestParameters);
			
			//C) Get mit Request-Parameter absenden
			res = client.invokeGet(usersURL);
			System.out.println("\nC) Get mit Request-Parameter absenden\n Statuscode: "+res.getStatus()+" Inhalt: "+res.getEntity(String.class) +"Request: "+res );
			
			//D) Post mit Request-Parameter absenden
			res = client.invokePost(usersURL, payload);
			System.out.println("\nD) Post mit Request-Parameter absenden\n Statuscode: "+res.getStatus()+" Inhalt: "+res.getEntity(String.class) +"Request: "+res );
			
			//resetet die Request-Parameter
			client.resetQuerryParms();
				
			//E) Get ohne Request-Parameter absenden
			res = client.invokeGet(usersURL);
			System.out.println("\nE) Get mit Request-Parameter resttet absenden\n Statuscode: "+res.getStatus()+" Inhalt: "+res.getEntity(String.class) +"Request: "+res );
			
			//F) Post ohne Request-Parameter absenden
			res = client.invokePost(usersURL, payload);
			System.out.println("\nF) Post mit Request-Parameter resttet absenden\n Statuscode: "+res.getStatus()+" Inhalt: "+res.getEntity(String.class) +"Request: "+res );
			
			
			client.setACCEPTHEADER(client.getHTTPCONST().MIMETYPE_TEXT_JSON);
			client.setPAYLOADTYPE(client.getHTTPCONST().MIMETYPE_TEXT_JSON);
			
			//G) Post ohne Request-Parameter absenden
			res = client.invokeGet("http://localhost:8080/MoaIosBeer/rest/v1.01/games/open");
			System.out.println("\nG) Get mit Request-Parameter resttet absenden\n Statuscode: "+res.getStatus()+" Inhalt: "+res.getEntity(String.class) +"Request: "+res );
			
			//G) Post ohne Request-Parameter absenden
			res = client.invokeGet("http://localhost:8080/MoaIosBeer/rest/v1.01/users/id/SWNoOjEyMzQ=");
			System.out.println("\nG) Get mit Request-Parameter resttet absenden\n Statuscode: "+res.getStatus()+" Inhalt: "+res.getEntity(String.class) +"Request: "+res );
			
			
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientHandlerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
