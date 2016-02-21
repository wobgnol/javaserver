<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	 import="de.moaiosbeer.helper.* "%>

<!-- http://localhost:8080/MoaIosBeer/rest/v1.01/games/info/1
{
	"role":Wholesaler,
	"average_order":0,
	"costs":0.0,
	"available":20,
	"next_inc":30,
	"delivered":0,
	"playsheet":[	{"owner_id":1,"gamerole":"Wholesaler","incoming":0,"totalcosts":0.0,"inventory":15,"backorder":0,"neworder":0,"ourorder":0,"delivery":0,"id":1,"round":1},	
					{"owner_id":1,"gamerole":"Wholesaler","incoming":5,"totalcosts":0.0,"inventory":15,"backorder":0,"neworder":0,"ourorder":0,"delivery":0,"id":1,"round":2},
					{"owner_id":1,"gamerole":"Wholesaler","incoming":6,"totalcosts":0.0,"inventory":15,"backorder":0,"neworder":0,"ourorder":0,"delivery":0,"id":1,"round":3},
					...
				]
}
 -->
<html data-ng-app="myApp">
<title>MoaIosBeer</title>
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
 <script type="text/javascript">
        
	// Funktion die den Logout ausl�st
	function logout() {
		document.form1.logoutflag.value = "true"
		form1.submit()
	}
</script>
</head>

<%
	// Globale Seitenvariabelen
	session = request.getSession(true);
	SessionOwner owner = (SessionOwner) session.getAttribute("sessionOwner");
	String username = owner.getName();
%>


<body data-ng-controller="MyController">
<!-- <br> -->
<%-- owner Ausdruck:<%= owner %> --%>
<!-- <br> -->
<%-- session.getAttribute("mode")=> <%= session.getAttribute("mode") %> --%>
<!-- <br> -->
<%-- owner.getUserAktion() =><%=owner.getUserAktion()%> --%>
<!-- <br> -->


    <div class="container">
    	<div class="row">
			<FORM NAME="form1" METHOD="POST" action="./loginCheck">
				<ol class="breadcrumb">
					<li><a href="#">Zur�ck</a></li>
					<li><a href="#">Home</a></li>

					<!-- Die beiden versteckten <Input> Tages,
       				 senden dem LoginCheck.servlet die ben�tigten requestParameter.
       				 a) logoutflag wird ben�tigt damit das servlet die Nutzer Sitzung schlie�t
       				 b) username wird ben�tigt damit das servlet dieser jsp den usernamen zur�cksenden kann -->
					<INPUT TYPE="HIDDEN" NAME="logoutflag">
					<INPUT TYPE="HIDDEN" NAME="username" value="<%=owner.getName()%>">
					<li class="active"><a type="BUTTON" href="#"
						onclick=" logout()">Logut</a></li>

				</ol>
			</FORM>
			<hr />
		</div>
    
        <div class="row">
            <div class="page-header">
                <h1 class="text-center"><%=owner.getName()%> <small>Runde 2/40</small></h1>
            </div>
        </div>
        <!--Tabmenue-->
        <div class="row">
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="active"><a href="#dashboard" role="tab" data-toggle="tab">Dashboard</a></li>
                <li role="presentation"><a href="#verlauf" role="tab" data-toggle="tab">Verlauf</a></li>

            </ul>
        </div>
        <div class="tab-content">
            <!--Verlauf-->
            <div role="tabpanel" class="tab-pane fade" id="verlauf">

                <!--Woche 2-->
                <div class="row">
                    <div class="row">
                        <div  class="page-header">
                            <a role="button" data-toggle="collapse" href="#woche2"><h5 class="text-center">Woche 2</h5></a><!--dynmisch : href und  text-->
                        </div>
                    </div>
                    <div class="collapse" id="woche2"> <!--dynmisch-->
                        <table class="table">
                            <tr>
                                <td>Woche</td>
                                <td>1</td><!--dynmisch-->
                            </tr>
                            <tr>
                                <td>Eingehend</td>
                                <td>1</td><!--dynmisch-->
                            </tr>
                            <tr>
                                <td>Verf�gbar</td>
                                <td>1</td><!--dynmisch-->
                            </tr>
                            <tr>
                                <td>Neue Bestellung</td>
                                <td>1</td><!--dynmisch-->
                            </tr>
                            <tr>
                                <td>Zu Liefern</td>
                                <td>1</td><!--dynmisch-->
                            </tr>
                            <tr>
                                <td>Deine Lieferung</td>
                                <td>1</td><!--dynmisch-->
                            </tr>
                            <tr>
                                <td>Verzug</td>
                                <td>1</td><!--dynmisch-->
                            </tr>
                            <tr>
                                <td>Lager</td>
                                <td>1</td><!--dynmisch-->
                            </tr>
                            <tr>
                                <td>Deine Bestellung</td>
                                <td>1</td><!--dynmisch-->
                            </tr>
                            <tr>
                                <td>Kosten</td>
                                <td>1</td><!--dynmisch-->
                            </tr>
                        </table>
                    </div>
                </div>
                <!--/Woche 2-->
                
                <!--dynmisch siehe woche 2!!!!!!!!!!!!!! ps woche 2 ist oben!!!!!!!!! Timmy du schafst das ! wir schaffen das!!!! -->
                <!--Woche 1-->
                <div class="row">
                    <div class="row">
                        <div  class="page-header">
                            <a role="button" data-toggle="collapse" href="#woche1"><h5 class="text-center">Woche 1</h5></a>
                        </div>
                    </div>
                    <div class="collapse" id="woche1">
                        <table class="table">
                            <tr>
                                <td>Woche</td>
                                <td>1</td>
                            </tr>
                            <tr>
                                <td>Eingehend</td>
                                <td>1</td>
                            </tr>
                            <tr>
                                <td>Verf�gbar</td>
                                <td>1</td>
                            </tr>
                            <tr>
                                <td>Neue Bestellung</td>
                                <td>1</td>
                            </tr>
                            <tr>
                                <td>Zu Liefern</td>
                                <td>1</td>
                            </tr>
                            <tr>
                                <td>Deine Lieferung</td>
                                <td>1</td>
                            </tr>
                            <tr>
                                <td>Verzug</td>
                                <td>1</td>
                            </tr>
                            <tr>
                                <td>Lager</td>
                                <td>1</td>
                            </tr>
                            <tr>
                                <td>Deine Bestellung</td>
                                <td>1</td>
                            </tr>
                            <tr>
                                <td>Kosten</td>
                                <td>1</td>
                            </tr>
                        </table>
                    </div>
                </div>
                <!--/Woche 1-->
            </div>
            <!--/Verlauf-->
            <!--DashBoard-->
            <div role="tabpanel" class="tab-pane fade in active" id="dashboard">
                <br>
                <!--Tabelle-->
                <table class="table table-bordered">
                    <tr>
                        <td>� Lieferung:</td>
                        <td>5</td> <!--dynmisch-->
                    </tr>
                    <tr>
                        <td>Kosten:</td>
                        <td>7.50</td><!--dynmisch-->
                    </tr>
                    <tr>
                        <td>Verf�gbar:</td>
                        <td>10</td><!--dynmisch-->
                    </tr>
                    <tr>
                        <td>N�chste Runde eingehend:</td>
                        <td>7</td><!--dynmisch-->
                    </tr>
                    <tr>
                        <td>zu liefern:</td>
                        <td>5</td><!--dynmisch-->
                    </tr>
                </table>
                <!--/Tabelle-->
                <!--Input Bestellung-->
                <div class="row">
                    <form><!--form action-->
                        <div class="form-group">
                            <div class="col-xs-4">
                                <button type="submit" class="btn btn-default">Bestellen</button>
                            </div>
                            <div class="col-xs-8">
                                <input type="text" class="form-control"  placeholder="bestellen">
                            </div>
                        </div>
                    </form>
                </div>
                <!--/Input Bestellung-->
            </div>
            <!--/DashBoard-->
        </div>
        <!--/Tabmenue-->
        <div class="row">
            <div class="page-header">
            </div>
        </div>
    </div>

</body>
</html>
