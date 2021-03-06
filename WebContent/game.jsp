<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <title>JochenBecker</title>
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
                <h1 class="text-center">Ihr Spiel</h1>
            </div>
        </div>
        <div class="row">
            <ol class="breadcrumb">
                <li><a href="#">Zur�ck</a></li>
                <li><a href="#">Home</a></li>
                <li class="active">Logut</li>
            </ol>
        </div>
        <div class="row text-center">
            <div class="table-responsive">
                <table class="table table-hover">
                    <tr> 
                        <td class="success">Rolle 1</td>
                        <td class="success"></td>
                    </tr>
                    <tr>
                        <td class="success">Rolle 2</td>
                        <td class="success"></td>
                    </tr>
                    <tr>
                        <td class="danger">Rolle 3</td>
                        <td class="danger">B3nder88</td>
                    </tr>
                    <tr>
                        <td class="danger">Rolle 4</td>
                        <td class="danger"> Sie</td>
                    </tr>
                </table>
            </div>
        </div>
        <div class="row">
            <p class="lead text-center"> Das Spiel startet soblad alle Rollen vergeben wurden</p>
        </div>
        <div class="row">
            <div class="page-header">
            </div>
        </div>
    </div>
</body>
</html>
<!--
ToDo: 
Step 1: 
Seite durch Swiftcode (timer) reloaden / refreshen

Step 2:
Refresch durch polling via JS

-->