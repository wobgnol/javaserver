package de.moaiosbeer.tests;



//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.core.HttpHeaders;
//import javax.ws.rs.core.Response;
//import java.nio.charset.Charset;
//import javax.ws.rs.core.HttpHeaders;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;






public class RestApi_HttpGetUser_V1_01_JearsyClient {

	public static void main(String[] args) {

			

			try {
				
				// jersy Client 2.6 ( javax.rs.client.api ) umrüsten auf diesen
//				 HttpAuthenticationFeature feature = HttpAuthenticationFeature.universalBuilder()
//					      .credentialsForBasic("user", "123456")
//					      .credentials("adminuser", "hello")
//					      .build();
//				 Client client =  ClientBuilder.newClient();
//				 final Response response = client.target("http://localhost:8080/MoaIosBeer/rest/v1.01/users")
//						 				.request()
//						 				.property(feature.HTTP_AUTHENTICATION_BASIC_USERNAME, "Ich")
//						 				.property(feature.HTTP_AUTHENTICATION_BASIC_PASSWORD, "1234").get();
//				
				
				// Jersy CLient anlegen ( com.sun.jersey.api.client.Client; )
				Client client = Client.create();
				// Web Resurce anlegen (com.sun.jersey.api.client.WebResource;) und URL zur REST Resurce angeben 
				WebResource webResource = client.resource("http://localhost:8080/MoaIosBeer/rest/v1.01/users");
				
				//String auth = new String(Base64.encode("username:password"));
				 // String auth = new String(Base64.encode("Ich:1234"));
				  
				  /* Eine Client Antwort anlegen / response vom Server zum Client 
				 * dieser wird als antwort auf das Get vom Server erzeugt
				 * (com.sun.jersey.api.client.ClientResponse;)*/
				ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
				//ClientResponse response = webResource.header(HttpHeaders.WWW_AUTHENTICATE, value)
				//ClientResponse response = webResource.type("text/plain").put(ClientResponse.class, "foo:bar");
				  //ClientResponse response = webResource.header(HttpHeaders.AUTHORIZATION, "Basic" + auth ).accept("application/json").get(ClientResponse.class);
				  //ClientResponse response = webResource.header("Authorization", "Basic" + auth).accept("application/json").get(ClientResponse.class);
				// ist der Response kein http ok(200) dann schreibe fehlercode
				
				
				if (response.getStatus() != 200 ) {
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
