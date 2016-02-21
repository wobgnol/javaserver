<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="de.moaiosbeer.helper.* "%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">




<html data-ng-app="myApp">
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
	// Globale Seitenvariabelen
	session = request.getSession(true);
	SessionOwner owner = (SessionOwner) session.getAttribute("sessionOwner");
	String username = owner.getName();
%>
    
    <script type="text/javascript">
    
  
    
  // Eine waitlist (JSON)     
  //      [	{"role":"Wholesaler","name":"Hans"} , {"role":"Distributer","name":"Ingo"},{"role":"Brewer","name":"Otto"} ]
    var app = angular.module('myApp', []);
    
 	// Polling alle 3 Sekunden ------------------------------Start
    app.controller('MyController', function($scope, $http, $timeout) {    	
   
    	// $scope.playerinfos  =[{"role":"Wholesaler","name":"Ich"} , {"role":"Distributer","name":"Ingo"},{"role":"Brewer","name":"Otto"}];
    	// wichtig die Url muss nur offene spiele fetchen
		$scope.playerinfos = [];	
    	// nacher mit polling f�llen
    
			var polling = function() {
				var value = $http({
					method : 'GET',
					url : './rest/v1.01/games/playerinfo/<%=owner.getGameID()%>',
					headers: {
							   'Authorization': "Basic SWNoOjEyMzQ="
				}// //owner.getAuth()
			});

			value.success(function(data, status, headers, config) {
				$scope.playerinfos = data;
				//alert(status);
				console.log(angular.toJson($scope.games));
			});

			
			
			
			$timeout(function() {
				polling();
			}, 3000);
		};

		//Call function polling()
		polling();
	}); // Polling alle 3 Sekunden ------------------------------Ende

	// Funktion Sendet: => /SessionController => Logout bitte 
	function logout() {
		document.form1.logoutflag.value = "true";
		form1.submit();
	}

</script>



</head>

<body data-ng-controller="MyController">
    <div class="container">
    	<!-- Reihe1 Start  -->
		<div class="row">
			<FORM NAME="form1" METHOD="POST" action="./loginCheck">
				<ol class="breadcrumb">
					<li><a href="./">Home</a></li>
					<!-- Die versteckten <Input> Tages,
       				 senden dem LoginCheck.servlet die ben�tigten requestParameter.
       				 a) logoutflag wird ben�tigt damit das servlet die Nutzer Sitzung schlie�t
       				 b) username wird ben�tigt damit das servlet dieser jsp den usernamen zur�cksenden kann 
       				 c) mode wird ben�tigt um Spiel beizutreten oder zu erstellen-->
					<INPUT TYPE="HIDDEN" NAME="logoutflag">
					<INPUT TYPE="HIDDEN" NAME="username" value="<%=username%>">
					<li class="active">
					<a type="BUTTON" href="#" onclick=" logout()">Logut</a>
					</li>
				</ol>
			</FORM>
			<h1 class="text-center ">Spiel von: <%=owner.getName()%></h1>
			<hr />
		</div>
		<!-- Reihe1 Ende  -->

        <div class="row text-center">
            <div  class="table-responsive">
                <table  class="table table-hover">
                    <tr data-ng-repeat="info in playerinfos"> 
                        <td class="success">{{info.role}}</td>
                        <td class="success">{{info.name}}</td>
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