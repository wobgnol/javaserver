<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="de.moaiosbeer.helper.* "%>


<html data-ng-app="myApp">
<title>MoaIosBeerGame</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
<link href="favicon.ico" rel="icon" type="image/x-icon" />
<!--css-->
<link href="bower_components/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<!--/css-->
<!--js-->
<script src="bower_components/jquery/dist/jquery.min.js"
	type="text/javascript"></script>
<script src="bower_components/angular/angular.min.js"
	type="text/javascript"></script>
<script src="bower_components/angular-route/angular-route.min.js"
	type="text/javascript"></script>
<script src="bower_components/bootstrap/dist/js/bootstrap.min.js"
	type="text/javascript"></script>
<script src="bower_components/angular/angularBasicAuth.js"
	type="text/javascript"></script>

<%
	// Globale Seitenvariabelen
	session = request.getSession(true);
	SessionOwner owner = (SessionOwner) session.getAttribute("sessionOwner");
	String username = owner.getName();
%>

<!--/js-->
<script type="text/javascript">
        
  // Eine Gamelist (JSON)     
  //      [	{"id":1,"currentround":0,"maxrounds":0,
  //    				"playsheets":[	{"id":1,"owner_id":1,"gamerole":null,"incoming":0,"round":1,"totalcosts":0.0,"inventory":5,"backorder":0,"neworder":0,"ourorder":0,"gamelist":[]},
  //      	            	 			{"id":2,"owner_id":2,"gamerole":null,"incoming":0,"round":1,"totalcosts":0.0,"inventory":5,"backorder":0,"neworder":0,"ourorder":0,"gamelist":[]}
  //      				 			 ],
  //      				"userlist":	 [	{"id":1,"username":"Ich","password":"1234","roles":[{"role_id":1,"rolename":"manager-gui"}],"gamelist":[]},
  //      			    	       	  	{"id":2,"username":"Heinrich","password":"1234","roles":[{"role_id":2,"rolename":"admin"}],"gamelist":[]}
  //      							 ],
  //       	"full":false,"activeUserid":1}
  //      ]
    var app = angular.module('myApp', []);
    
 	// Polling alle 3 Sekunden ------------------------------Start
    app.controller('MyController', function($scope, $http, $timeout) {    	
   
    	// wichtig die Url muss nur offene spiele fetchen
		$scope.games = [];	
			var polling = function() {
				var value = $http({
					method : 'GET',
					url : './rest/v1.01/games/open/',
					headers: {
							   'Authorization': "Basic <%=owner.getAuth()%>"
				}
			});

			value.success(function(data, status, headers, config) {
				$scope.games = data;
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

	<%!
	// Diese Methode erstellt den User bezogenen Willkomenstext
	public String welcomeText(String username) {
		if (username != null) {
			return "Wilkomen " + username + "<br><small>w�hlen Sie ein Spiel</small>";
		}
		return "Wilkomen <br><small>w�hlen Sie ein Spiel</small>";
	}%>



<!-- Hauptanzeige Container  Start  -->
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
			<h1 class="text-center "><%=welcomeText(username)%></h1>
			<hr />
		</div>
		<!-- Reihe1 Ende  -->
		
		<!-- Reihe2 Start Freiespiele rendern und Gameid bei join senden (join=gesendeteid -->
		<div class="row text-center">
			<input 	class="form-control" 
					placeholder="Game Filter "
					ng-model="searchText">
			<!--  Table Dynamisch mit Javascript (Angular js erzeugt aus Json vom Polling) -->
			<div class="table-responsive">
					<table class="text-center  table table-hover">
						<tr>
							<td class="active">ID</td>
							<td class="active">Name</td>
							<td class="active">Players</td>
							<td class="active">Join</td>
						</tr>
						<tr data-ng-repeat="game in games | filter:searchText">
						
							<td class="success">{{game.id}}</td>
							<td class="success">{{game.gamename}}</td>
							<td class="success">{{game.userlist.length}} / 4</td>
							<td class="success">
								<form NAME="form2" METHOD="POST" action="./game">
								<INPUT TYPE="HIDDEN" NAME="gameid" value="{{game.id}}" > 
								<INPUT TYPE="HIDDEN" NAME="mode" value="join">
								<!--Form Bsp.:<form action="action_page.php  method="post"">-->
								<button type="submit"
										class="btn btn-default" 
										href="#" 
								>beitreten
								</button>
							</form>	
							</td>
							
						</tr>
					</table>
				
			</div>
		</div>
		<!-- Reihe2 Ende  -->
		
		<!-- Reihe3 Start Speil Erstellen senden -->
		<div class="row">
			<hr />
			<center>
				<FORM NAME="form3" METHOD="POST" action="./game">
					<INPUT TYPE="HIDDEN" NAME="mode" value="create"> 
					<button type="submit"
							href="#" 
							class="btn btn-default">Spiel erstellen
					</button>
				</FORM>
			</center>
			<hr />
		</div>
		<!-- Reihe3 Ende  -->
		
		
	</div>
<!-- Hauptanzeige Container  Ende  -->
</body>
</html>