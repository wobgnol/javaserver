<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <title>MoaIosBeerGame</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="favicon.ico" rel="icon" type="image/x-icon" />
    <!--css-->
    <link href="bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <!--/css-->
    <!--js-->
    <script src="bower_components/jquery/dist/jquery.min.js" type="text/javascript"></script>
    <script src="bower_components/angular/angular.min.js" type="text/javascript"></script>
    <script src="bower_components/angular-route/angular-route.min.js" type="text/javascript"></script>
    <script src="bower_components/bootstrap/dist/js/bootstrap.min.js" type="text/javascript"></script>
    <!--/js-->
</head>
<%
/* Variabele f�r die Serverantwort Attrubut("username") 
   Nimmt den vom Server gesendeten username an ( Bei falsch Eingaben wird diese JSP erneut geladen)
   Damit der User seine fehleingaben korrigieren kann sollen diese wieder in die eingabefelder geladen werden*/
String username = (String)request.getAttribute("username"); 
/* Variabele f�r die Serverantwort Attrubut("password") 
Nimmt den vom Server gesendeten username an ( Bei falsch Eingaben wird diese JSP erneut geladen)
Damit der User seine fehleingaben korrigieren kann sollen diese wieder in die eingabefelder geladen werden*/
String password = (String)request.getAttribute("password"); 
/*Variabele f�r den Http Statuscode der Serverantwort ( 200 ok, 404 not found ...)*/
int statuscode = response.getStatus(); 
%>
<%!
/* Mathode zum schreiben des input Values f�r den username*/
public String usernamePlaceholder(String username){
	// Gab es noch keine Server Antwort
	if(username == null){
		/*sorgt daf�r das das html attribut placeholder des input Aktiv bleibt.
		input zeigt den placeholder Text*/
		return "";
	}
	// ist die Antwort vom server der leerstring
	else if(username.equals("")){
		// input value fordert zu Passwort Eingabe auf
		return "!! KEIN BENUTZERNAME EINGEGEBEN !!";
	}
	// ansonsten gieb die alte eingabe als Text an ( erm�glich korrektur )
	return username;
}
%>

<%!
/* Mathode zum schreiben des input Values f�r den username*/
public String passwordPlaceholder(String password){
	// Gab es noch keine Server Antwort
	if(password == null){
		/*sorgt daf�r das das html attribut placeholder des input Aktiv bleibt.
		input zeigt den placeholder Text*/
		return "";
	}
	// ist die Antwort vom server der leerstring
	else if(password.equals("")){
		// war das password leer bleibt es dies auch
		return "";
	}
	// ansonsten gib das alte passwort zur�ck und erm�glich korrektur 
	return password;
}
%>

<%!
	public String statuscode(int statuscode, HttpServletResponse res){
	 if(statuscode != 200){
		
		 if(statuscode == 1010101 ){
			 return res.getHeader("logouttext") ;
		 }else
		return "Login Error: "+statuscode +"<br>check username and password";
	 }
	 return  "";
}
%>


<body>
    <div class="container">
        <div class="row">
            <div class="page-header">
                <h1 class="text-center">Anmeldung</h1>
            </div>
        </div>
        <!--loginpage-->
        <!--Form Bsp.:<form action="action_page.php  method="post"">-->
        <form action="./loginCheck" method="post" NAME="form1">
            <br>
           	<!--erste Zeile Error Label-->
             <div class="row">
                <!--alle Mediendevice werden supportet repsonisve-->
                <div class="col-xs-1 col-sm-2 col-md-4 col-lg-4"></div><!-- platzhalter Leer-->
                <div class="col-xs-10 col-sm-8 col-md-4 col-lg-4 form-group">
                  <label><%= statuscode(statuscode,response) %></label>
                </div>
                <div class="col-xs-1 col-sm-2 col-md-4 col-lg-4"></div><!-- platzhalter Leer-->
                <!--alle Mediendevice werden supportet repsonisve-->
            </div>
             <!--/erste Zeile Error Label-->
             
            <!--zweite Zeile mit Labe und Textfeld-->
            <div class="row">
                <!--alle Mediendevice werden supportet repsonisve-->
                <div class="col-xs-1 col-sm-2 col-md-4 col-lg-4"></div><!-- platzhalter Leer-->
                <div class="col-xs-10 col-sm-8 col-md-4 col-lg-4 form-group">
                    <label>Benutzername</label>
                    <!-- name="username" wird f�r die JSP ben�tigt Requestparameter/Parametername-->
                    <input  name="username" type="text" class="form-control" placeholder="Benutzername" value="<%= usernamePlaceholder(username)%>"> 
                </div>
                <div class="col-xs-1 col-sm-2 col-md-4 col-lg-4"></div><!-- platzhalter Leer-->
                <!--alle Mediendevice werden supportet repsonisve-->
            </div>
            <!--/zweite Zeile mit Labe und Textfeld-->
            <div class="row">
                <div class="col-xs-1 col-sm-2 col-md-4 col-lg-4"></div>
                <div class="col-xs-10 col-sm-8 col-md-4 col-lg-4 form-group">
                    <label>Passwort</label>
                    <!-- name="password" wird f�r die JSP ben�tigt Requestparameter/Parametername-->
                    <input name="password" type="password" class="form-control" placeholder="Passwort" value="<%= passwordPlaceholder(password) %>">
                </div>
                <div class="col-xs-1 col-sm-2 col-md-4 col-lg-4"></div>
            </div>
            <div class="row">
                <div class="col-xs-1 col-sm-4 col-md-4 col-lg-5"></div>
                <div class="col-xs-5 col-sm-2 col-md-2 col-lg-1 form-group">
                    <center>
                        <button type="submit" class="btn btn-default" onclick="encodebase64()">Anmelden</button>
                    </center>
                </div>
                <div class="col-xs-5 col-sm-2 col-md-2 col-lg-1 form-group">
                    <center>
                        <!-- Link zur Registrierenseite einf�gen als hrref="../Reg" -->
                        <a href="./registrierung.jsp" class="btn btn-default">Regristreren</a>
                    </center>
                </div>
                <div class="col-xs-1 col-sm-4 col-md-4 col-lg-5"></div>
            </div>
        </form>
        <!--/loginpage-->
        <div class="row">
            <div class="page-header">
            </div>
        </div>
    </div>
</body>
</html>
