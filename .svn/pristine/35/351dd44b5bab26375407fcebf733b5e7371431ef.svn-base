package de.moaiosbeer.tests;

import javax.naming.AuthenticationException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.Base64;



public class RestApi_HttpGet_RolesAllowed_manager_gui {
	
	
	/**
	 * Ermöglicht einen HttpGET Request mit BASC Autentifizierung ( Hash base64 von username:password )
	 * @param url http://host:Port/resourceUrl
	 * @param auth String auth = new String(Base64.encode(unsername+":"+password));
	 * @return Enttity(string.class)
	 * @throws AuthenticationException Wenn Http Status code != 200 ist (Http ok)
	 * @throws ClientHandlerException	
	 */
	private static String invokeGet(String url, String auth ) throws AuthenticationException, ClientHandlerException {
		//String test = new String(Base64.decode("SWNoOjEyMzQ="));
		// Test Ausgaeben 
		//System.out.println(test);
		//System.out.println(auth);
	    Client client = Client.create();
	    WebResource webResource = client.resource(url);
	    ClientResponse response = webResource.header("Authorization", "Basic " + auth).accept("application/json").accept("text/plain").get(ClientResponse.class);
	    System.out.println(response);
	    int statusCode = response.getStatus();
	    
	    // Ist der Response kein Http 20 = ok dann Werfe => AuthenticationException
	    if (statusCode != 200) {
	        throw new AuthenticationException("Invalid Username or Password");
	    }
	    return response.getEntity(String.class);
	}
	
	
	/**
	 * ermöglicht einen HttpGET Request mit BASC Autentifizierung ( Hash base64 von username:password )
	 * @param url http://host:Port/resourceUrl
	 * @param username Username String
	 * @param password Pssword String
	 * @return Enttity(string.class)
	 * @throws AuthenticationException Wenn Http Status code != 200 ist (Http ok)
	 * @throws ClientHandlerException	
	 */
	private static String invokeGet(String url,String username,String password ) throws AuthenticationException, ClientHandlerException {
		
		// Basic Autentifizierungs String Hashen
		String auth = new String(Base64.encode(username+":"+password));
		
		//Testausgaben
		//System.out.println(auth);
		//  "SWNoOjEyMzQ=" <=> String auth = new String(Base64.encode("Ich:1234")); (64er Hash)
		// String test = new String(Base64.decode("SWNoOjEyMzQ=")); 
		//System.out.println(test);
		
		// Http Clienten erstellen  (import com.sun.jersey.api.client.Client;)
	    Client client = Client.create();
	    // Web Resource aus dem response fischen (import com.sun.jersey.api.client.ClientResponse; import com.sun.jersey.api.client.WebResource;)
	   //  Accept: text/plain, text/html , application/json
	    WebResource webResource = client.resource(url);
	    // anfrage zusammenbauen
	    ClientResponse response = webResource.header("Authorization", "Basic " + auth).accept("application/json").accept("text/plain").get(ClientResponse.class);
	    System.out.println(response);
	    int statusCode = response.getStatus();
	   
	    // Ist der Response kein Http 20 = ok dann Werfe => AuthenticationException
	    if (statusCode != 200) {
	        throw new AuthenticationException("Invalid Username or Password");
	    }
	    return response.getEntity(String.class);
	}
	
	
	
	
	
	
	/**
	 * ermöglicht einen HttpPOST Request mit BASC Autentifizierung ( Hash base64 von username:password )
	 * @param url http://host:Port/resourceUrl
	 * @param username Username String
	 * @param password Pssword String
	 * @return Enttity(string.class)
	 * @throws AuthenticationException Wenn Http Status code != 200 ist (Http ok)
	 * @throws ClientHandlerException	
	 */
	private static String invokePost(String url,String username,String password ) throws AuthenticationException, ClientHandlerException {
		
		// Basic Autentifizierungs String Hashen
		String auth = new String(Base64.encode(username+":"+password));
		// Jersy Http Clienten anlegen 
	    Client client = Client.create();
	    //Webrecource mit URL anlegen 
	    WebResource webResource = client.resource(url);
	    // einen String fürs Versenden via Post anlegen
	    String payload = "Ich bin der Payload";
	    // Http Post mit dem Payload absenden
	    ClientResponse response = webResource.header("Authorization", "Basic " +auth).type("text/plain").accept("text/plain").post(ClientResponse.class,payload);
	    // Response ausgeben
	    System.out.println(response);
	    
	    // Statuscode des Responses ablegen
	    int statusCode = response.getStatus();
	    // Ist der Response Status kein Http 20 = ok dann Werfe => AuthenticationException
	    if (statusCode != 200) {
	        throw new AuthenticationException("Invalid Username or Password");
	    }
	    return response.getEntity(String.class);
	}
	
	
	/**
	 * ermöglicht einen HttpPOST Request mit BASC Autentifizierung ( Hash base64 von username:password )
	 * @param  url http://host:Port/resourceUrl
	 * @param  auth String auth = new String(Base64.encode(unsername+":"+password));
	 * @return Enttity(string.class)
	 * @throws AuthenticationException Wenn Http Status code != 200 ist (Http ok)
	 * @throws ClientHandlerException	
	 */
	private static String invokePost(String url, String auth ) throws AuthenticationException, ClientHandlerException {
		
		// Jersy Http Clienten anlegen 
	    Client client = Client.create();
	    //Webrecource mit URL anlegen 
	    WebResource webResource = client.resource(url);
	    // einen String fürs Versenden via Post anlegen
	    String payload = "Ich bin der Payload";
	    // Http Post mit dem Payload absenden
	    ClientResponse response = webResource.header("Authorization", "Basic " +auth).type("text/plain").accept("text/plain").post(ClientResponse.class,payload);
	    // Response ausgeben
	    System.out.println(response);
	    
	    // Statuscode des Responses ablegen
	    int statusCode = response.getStatus();
	    // Ist der Response Status kein Http 20 = ok dann Werfe => AuthenticationException
	    if (statusCode != 200) {
	        throw new AuthenticationException("Invalid Username or Password");
	    }
	    return response.getEntity(String.class);
	}
	

	public static void main(String[] args) {

		try {
			
			//TODO Client java generisch machen
			
			// passwort und username
			String pw = "1234" ;
			String un = "Ich"  ;
			
			// Base 64 encoding für den aufruf von invokeGet(url,auth);
			String auth = new String(Base64.encode(un+":"+pw));
			
			// URL Host:Port/rest/v1.01/login 
			String usersURL = "http://localhost:8080/MoaIosBeer/rest/v1.01/users" ;
			// URL Host:Port/rest/v1.01/users 
			String loginURL = "http://localhost:8080/MoaIosBeer/rest/v1.01/login" ;
			
			
			// Test A) HttpGet => http://Host:Portr/est/v1.01/login --------------------------------Start
			System.out.println("Test A) 1. Login => HttpGET http://Host:Port/rest/v1.01/login");
			System.out.println("Response: "+invokeGet(loginURL,auth));
			
			System.out.println("\nTest A) 2. Login=>  HttpGET http://Host:Port/rest/v1.01/login");
			System.out.println("Response: "+invokeGet(loginURL,un,pw));
			// Test A) HttpGet => http://Host:Port/rest/v1.01/login --------------------------------Ende
			
			
			// Test B) HttpPOST => http://Host:Port/rest/v1.01/login -------------------------------Start
			System.out.println("\n\nTest B)	1. Login=>  HttpPOST => http://Host:Port/rest/v1.01/login");
			System.out.println("Response: "+invokePost(loginURL,auth));
			

			System.out.println("\nTest B) 2. Login=>  HttpPOST http://Host:Port/rest/v1.01/login");
			System.out.println("Response: "+invokePost(loginURL,un,pw));
			// Test B) HttpPOST => http://Host:Port/rest/v1.01/login -------------------------------Ende
			
			
			
			// Test C) HttpGet => http://Host:Port/rest/v1.01/users --------------------------------Start
			System.out.println("\n\nTest C) 1. GetAllUsers => HttpGET http://Host:Port/rest/v1.01/users");
			System.out.println("Response: "+invokeGet(usersURL,auth));
			
			System.out.println("\nTest C) 2. GetAllUsers => HttpGET http://Host:Port/rest/v1.01/users");
			System.out.println("Response: "+invokeGet(usersURL,un,pw));
			// Test C) HttpGet => http://Host:Port/rest/v1.01/login/v1.01/users --------------------Ende
			
		  } catch (Exception e) {

			e.printStackTrace();

		  }

		}	

	}


