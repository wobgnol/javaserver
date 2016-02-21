<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

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
    
    <%
    String Antwort = request.getParameter("Antwort");
    %>
    
</head>
<body>
    <div class="container">
    
    <!-- Reihe1 Start  -->
		<div class="row">
			<FORM NAME="form1" METHOD="POST" action="./loginCheck">
				<ol class="breadcrumb">
					<li><a href="./">Login</a></li>
				</ol>
			</FORM>
			<h1 class="text-center ">Registrieren</h1>
			<hr />
		</div>
		<!-- Reihe1 Ende  -->
   
   			
        <!--loginpage-->
        <!--Form Bsp.:<form action="action_page.php  method="post"">-->
        <form action="./register" method="post" NAME="form1>
            <!--erste Zeile mit Labe und Textfeld-->
            <div class="row">
                <!--alle Mediendevice werden supportet repsonisve-->
                <div class="col-xs-1 col-sm-2 col-md-4 col-lg-4"></div><!-- platzhalter Leer-->
                <div class="col-xs-10 col-sm-8 col-md-4 col-lg-4 form-group">
                    <label for="beispielFeldEmail1">Benutzername</label>
                    <!-- name="username" wird für die JSP benötigt Requestparameter/Parametername-->
                    <input  name="username" type="text" class="form-control" placeholder="Benutzername" value="MaxMustermann"> 
                </div>
                <div class="col-xs-1 col-sm-2 col-md-4 col-lg-4"></div><!-- platzhalter Leer-->
                <!--alle Mediendevice werden supportet repsonisve-->
            </div>
            <!--/erste Zeile mit Labe und Textfeld-->
            <div class="row">
                <div class="col-xs-1 col-sm-2 col-md-4 col-lg-4"></div>
                <div class="col-xs-10 col-sm-8 col-md-4 col-lg-4 form-group">
                    <label for="beispielFeldPasswort1">Passwort</label>
                    <!-- name="password" wird für die JSP benötigt Requestparameter/Parametername-->
                    <input name="passsword" type="password" class="form-control" placeholder="Passwort" value="Geheim113654">
                </div>
                <div class="col-xs-1 col-sm-2 col-md-4 col-lg-4"></div>
            </div>
            <div class="row">
                <center>
                    <button type="submit" class="btn btn-default">Regristreren</button>
                </center>
            </div>
        </form>
        <%=response.getStatus() %>
        <!--/loginpage-->
        <div class="row">
            <div class="page-header">
            </div>
        </div>
    </div>
</body>
</html>
