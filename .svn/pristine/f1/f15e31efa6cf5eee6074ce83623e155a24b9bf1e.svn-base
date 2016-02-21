package de.moaiosbeer.tests;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import de.moaiosbeer.helper.HttpConstants;


public class RestApi_HttpPutUser_V1_01_JearsyClient {
	public static HttpConstants HTTP_CONSTANTS = new HttpConstants();
	 public static void main(String[] args) {
		

			try {
				// Jersy CLient anlegen ( com.sun.jersey.api.client.Client; )
				Client client = Client.create();
				// Web Resurce anlegen (com.sun.jersey.api.client.WebResource;) und URL zur REST Resurce angeben 
				WebResource webResource = client.resource("http://localhost:8080/MoaIosBeer/rest/v1.01/users");
				// In unserem Fall ein User json (de.Das_Portal.beans.User_V1_01.java)
				String input = "{\"username\":\"User 6\",\"password\":\"12345\"}";
				// Eine Client Antwort anlegen / response vom Server zum Client (com.sun.jersey.api.client.ClientResponse;)
				ClientResponse response = webResource.type("application/json").put(ClientResponse.class, input);
				//ClientResponse response = webResource.type("text/plain").put(ClientResponse.class, "foo:bar");
				
				// ist der Response kein http created(201) dann schreibe fehlercode
				if (response.getStatus() != 201 ) {
					throw new RuntimeException("Failed:+" + response.getStatus());
				}

				System.out.println("Output from Server .... \n");
				String output = response.getEntity(String.class);
				System.out.println(output);

			  } catch (Exception e) {

				e.printStackTrace();

			  }

			}
      
}
