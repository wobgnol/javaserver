<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

    <title>Beer Game</title>
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
<body>
    <div class="container">
        <div class="row">
            <div class="page-header">
                <h1 class="text-center">Spiel erstellen</h1>
            </div>
        </div>
        <!--loginpage-->
        <!--Form Bsp.:<form action="action_page.php  method="post"">-->
        <form>
            <br>
            <!--erste Zeile mit Labe und Textfeld-->
            <div class="row">
                <!--alle Mediendevice werden supportet repsonisve-->
                <div class="col-xs-1 col-sm-2 col-md-4 col-lg-4"></div><!-- platzhalter Leer-->
                <div class="col-xs-10 col-sm-8 col-md-4 col-lg-4 form-group">
                    <label for="beispielFeldEmail1">Spielname</label>
                    <!-- name="username" wird für die JSP benötigt Requestparameter/Parametername-->
                    <input  name="gamename" type="text" class="form-control" placeholder="Spielname" value="Spiel5"> 
                </div>
                <div class="col-xs-1 col-sm-2 col-md-4 col-lg-4"></div><!-- platzhalter Leer-->
                <!--alle Mediendevice werden supportet repsonisve-->
            </div>
            <!--/erste Zeile mit Labe und Textfeld-->
            <div class="row">
                <div class="col-xs-1 col-sm-2 col-md-4 col-lg-4"></div>
                <div class="col-xs-10 col-sm-8 col-md-4 col-lg-4 form-group">
                    <label for="beispielFeldPasswort1">Rolle</label>
                    <!-- name="password" wird für die JSP benötigt Requestparameter/Parametername-->
                    <select name="role" class="form-control">
                        <option>Rolle 1</option>
                        <option>Rolle 2</option>
                        <option>Rolle 3</option>
                        <option>Rolle 4</option>
                    </select>
                </div>
                <div class="col-xs-1 col-sm-2 col-md-4 col-lg-4"></div>
            </div>
            <div class="row">
                <center>
                    <button type="submit" class="btn btn-default">Spiel erstellen</button>
                </center>
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

<!--
ToDo: 
if(Request.get("gamename")== inDB){
 request.add(error);
 requestdispather.open("/creategame.jsp");
 responese.get(error).print;
} 
else{
  requestdispather.open("/wait4players.jsp");
}


-->