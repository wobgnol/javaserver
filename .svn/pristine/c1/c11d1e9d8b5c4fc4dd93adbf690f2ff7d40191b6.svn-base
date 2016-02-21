package de.moaiosbeer.helper;

import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.AuthenticationException;
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.Base64;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * Die Klasse MoaHttpClient ist ein generischer HttpClient auf Basis der Klasse com.sun.jersey.api.client.Client.
 * Dieser bietet diemöglichkeit die gängigen Http Methoden(Get/Post/Put/Delete) abzusetzen.
 * An der Client Inztanz können die gängigen header beschrieben werden: 
 * siehe wiki => https://en.wikipedia.org/wiki/List_of_HTTP_header_fields#Response_fields
 * Die ClientInztanz giebt beim ausführen einer jeden Methode einen Response vom Typ com.sun.jersey.api.client.ClientResponse zurück.
 * @author Stephan 16.12.2015 17:07
 */
public class MoaHttpClient {
	
	//static private JersyClientConfigHelper ClientHelper = new JersyClientConfigHelper();
	
	// Statische felder für den Http Clienten
	private String USERNAME = null;
	private String PASSWORD = null;
	private HttpConstants HTTPCONST = new HttpConstants();
	
	// Was Akzeptiere ich als Response Datentyp
	private String ACCEPTHEADER = HTTPCONST.MIMETYPE_TEXT_HTML;
	// Was sende ich Als Datentyp
	private String PAYLOADTYPE 	= HTTPCONST.MIMETYPE_TEXT_HTML;
	//Wurzelpfad der anwendung
	private String baseUrl = null;

	


	// Liste zum erzeugen von Request Parametern <Key,Value> => <"name",""Wert>
	static private MultivaluedMap<String, String> QUERRYPARMS = new MultivaluedMapImpl();
	
	//--------------------Username und Password Methoden---------------------------start
	public void resetUsernameAndPassword(){
		PASSWORD = null;
		USERNAME = null;
	}
	
	public void overwriteUsernameAndPassword(String username, String password){
		PASSWORD = password;
		USERNAME = username;
	}
	//--------------------Username und Password Methoden---------------------------end
	
	//--------------------Username:Password Encoding------------------------ ------start
	public String getEncodeedAuthString(String username, String password){
		return new String(Base64.encode(username+":"+password));
	}
	
	// macht aus http Basic Auth Strings eine Hasmap von username und password
	public HashMap<String,String> getDecodedAuthString(String auth){		
	String udecodedString=	 Base64.base64Decode(auth);
	String[] splittedString = udecodedString.split(":");
	HashMap<String,String>  username_password = new HashMap<String,String>();
	username_password.put("username",splittedString[0]);
	username_password.put("password",splittedString[1]);
	return username_password;
	}
	//--------------------Username:Password Encoding------------------------ ------Ende
	//--------------------ResetAll-------------------------------------------------start
	public void resetAll (){
		USERNAME = null;
		PASSWORD = null;
		ACCEPTHEADER = HTTPCONST.MIMETYPE_TEXT_HTML;
		PAYLOADTYPE = HTTPCONST.MIMETYPE_TEXT_HTML;
		QUERRYPARMS = new MultivaluedMapImpl();
		baseUrl = null;
		
	}
	//--------------------ResetAll-------------------------------------------------end
	
	//set MimeTypes--------------(input Type = output Type)------------------------start
	public void setJsonOnlyHeaders(){
		ACCEPTHEADER = HTTPCONST.MIMETYPE_TEXT_JSON;
		PAYLOADTYPE =  HTTPCONST.MIMETYPE_TEXT_JSON;
	}
	
	public void setHtmlOnlyHeaders(){
		ACCEPTHEADER = HTTPCONST.MIMETYPE_TEXT_HTML;
		PAYLOADTYPE = HTTPCONST.MIMETYPE_TEXT_HTML;
	}
	
	public void setTextPlainOnlyHeaders(){
		ACCEPTHEADER = HTTPCONST.MIMETYPE_TEXT_PLAIN;
		PAYLOADTYPE = HTTPCONST.MIMETYPE_TEXT_PLAIN;
		
	}
	
	public void setXmlOnlyHeaders(){
		ACCEPTHEADER = HTTPCONST.MIMETYPE_TEXT_XML;
		PAYLOADTYPE = HTTPCONST.MIMETYPE_TEXT_XML;
		
	}
	//set MimeTypes--------------(input Type = output Type)------------------------end
	
	//------------------------------Reset Querry Parameters------------------------start
	public void resetQuerryParms() {
		QUERRYPARMS = new MultivaluedMapImpl();
	}
	//------------------------------Reset Querry Parameters------------------------end
	
	//----------------------------Getter / Setter----------------------------------start
	public  MultivaluedMap<String, String> getQUERRYPARMS() {
		return QUERRYPARMS;
	}

	public  void setQUERRYPARMS(MultivaluedMap<String, String> querryParms) {
		QUERRYPARMS = querryParms;
	}

	public  String getUSERNAME() {
		return USERNAME;
	}

	public  void setUSERNAME(String username) {
		USERNAME = username;
	}

	public  String getPASSWORD() {
		return PASSWORD;
	}

	public  void setPASSWORD(String password) {
		PASSWORD = password;
	}

	public  HttpConstants getHTTPCONST() {
		return HTTPCONST;
	}

	public  void setHTTPCONST(HttpConstants httpConst) {
		HTTPCONST = httpConst;
	}

	public  String getACCEPTHEADER() {
		return ACCEPTHEADER;
	}

	public  void setACCEPTHEADER(String acceptHeader) {
		ACCEPTHEADER = acceptHeader;
	}

	public  String getPAYLOADTYPE() {
		return PAYLOADTYPE;
	}

	public  void setPAYLOADTYPE(String payloadType) {
		PAYLOADTYPE = payloadType;
	}
	
	public String getBaseUrl() {
		return baseUrl;
	}

	public  void setBaseUrl(String baseurl) {
		baseUrl = baseurl;
	}
	//----------------------------Getter / Setter----------------------------------end
	
// TODO für https Certifikat anpassen 
//	public static Client IgnoreSSLClient() throws Exception {
//	    SSLContext sslcontext = SSLContext.getInstance("TLS");
//	    sslcontext.init(null, new TrustManager[]{new X509TrustManager() {
//	        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
//	        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
//	        public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
//
//	    }}, new java.security.SecureRandom());
//	    return (Client) ClientBuilder.newBuilder().sslContext(sslcontext).hostnameVerifier((s1, s2) -> true).build();
//	}
	

	
	//Methoden: (invokeGet() | invokePost() | invokePut() | invokeDelete()---------start
	/**
	 * Ermöglicht einen HttpGet Request mit BASC Authentifizierung ( Hash base64 von username:password )
	 * @param 	url Http://host:Port/resourceUrl
	 * @param 	USERNAME Username String
	 * @param 	PASSWORD Pssword String
	 * @return 	ClientResponse com.sun.jersey.api.client.ClientResponse;
	 * @throws 	AuthenticationException Wenn Http Status code != 200 ist (Http ok)
	 * @throws 	ClientHandlerException	
	 */
	public  ClientResponse invokeGet(String url) throws AuthenticationException, ClientHandlerException {
		// Basic Autentifizierungs String Hashen
		String auth = new String(Base64.encode(USERNAME+":"+PASSWORD));
		// Jersy Http Clienten anlegen 
	    Client client = Client.create();
	    //Webrecource mit URL anlegen 
	    WebResource webResource = client.resource(url);
	    // Http Post mit dem Payload absenden
	    return   webResource.queryParams(QUERRYPARMS).header("Authorization", "Basic " +auth).accept(ACCEPTHEADER).get(ClientResponse.class);
	}
	
	
	/**
	 * Ermöglicht einen HttpPOST Request mit BASC Authentifizierung ( Hash base64 von username:password )
	 * @param 	url Http://host:Port/resourceUrl
	 * @param 	payload Zu sendende Daten
	 * @param 	USERNAME Username String
	 * @param 	PASSWORD Pssword String
	 * @return 	ClientResponse com.sun.jersey.api.client.ClientResponse;
	 * @throws 	AuthenticationException Wenn Http Status code != 200 ist (Http ok)
	 * @throws 	ClientHandlerException	
	 */
	public  ClientResponse invokePost(String url, String payload) throws AuthenticationException, ClientHandlerException {
		// Basic Autentifizierungs String Hashen
		String auth = new String(Base64.encode(USERNAME+":"+PASSWORD));
		// Jersy Http Clienten anlegen 
	    Client client = Client.create();
	    //Webrecource mit URL anlegen 
	    WebResource webResource = client.resource(url);
	    // Http Post mit dem Payload absenden
	    return   webResource.queryParams(QUERRYPARMS).header("Authorization", "Basic " +auth).type(PAYLOADTYPE).accept(ACCEPTHEADER).post(ClientResponse.class,payload);
	}
	
	
	/**
	 * Ermöglicht einen HttpPUT Request mit BASC Authentifizierung ( Hash base64 von username:password )
	 * @param 	url Http://host:Port/resourceUrl
	 * @param 	payload Zu sendende Daten
	 * @param 	USERNAME Username String
	 * @param 	PASSWORD Pssword String
	 * @return 	ClientResponse com.sun.jersey.api.client.ClientResponse;
	 * @throws 	AuthenticationException Wenn Http Status code != 200 ist (Http ok)
	 * @throws 	ClientHandlerException	
	 */
	public  ClientResponse invokePut(String url, String payload) throws AuthenticationException, ClientHandlerException {
		// Basic Autentifizierungs String Hashen
		String auth = new String(Base64.encode(USERNAME+":"+PASSWORD));
		// Jersy Http Clienten anlegen 
	    Client client = Client.create();
	    //Webrecource mit URL anlegen 
	    WebResource webResource = client.resource(url);
	    // Http Post mit dem Payload absenden
	    return   webResource.queryParams(QUERRYPARMS).header("Authorization", "Basic " +auth).type(PAYLOADTYPE).accept(ACCEPTHEADER).put(ClientResponse.class,payload);
	}
	
	
	/**
	 * Ermöglicht einen HttpDEL Request mit BASC Authentifizierung ( Hash base64 von username:password )
	 * @param 	url Http://host:Port/resourceUrl
	 * @param 	payload Zu sendende Daten
	 * @param 	USERNAME Username String
	 * @param 	PASSWORD Pssword String
	 * @return 	ClientResponse com.sun.jersey.api.client.ClientResponse;
	 * @throws 	AuthenticationException Wenn Http Status code != 200 ist (Http ok)
	 * @throws 	ClientHandlerException	
	 */
	public  ClientResponse invokeDelete(String url, String payload) throws AuthenticationException, ClientHandlerException {
		// Basic Autentifizierungs String Hashen
		String auth = new String(Base64.encode(USERNAME+":"+PASSWORD));
		// Jersy Http Clienten anlegen 
	    Client client = Client.create();
	    //Webrecource mit URL anlegen 
	    WebResource webResource = client.resource(url);
	    // Http Post mit dem Payload absenden
	    return   webResource.queryParams(QUERRYPARMS).header("Authorization", "Basic " +auth).type(PAYLOADTYPE).accept(ACCEPTHEADER).delete(ClientResponse.class,payload);
	}
	//Methoden: (invokeGet() | invokePost() | invokePut() | invokeDelete()---------end
		
}// MoaHttpClient  Ende
