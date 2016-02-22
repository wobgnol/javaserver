package de.moaiosbeer.rest_api;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import de.moaiosbeer.dao.Game_V1_01_DaO;
import de.moaiosbeer.dao.Playsheet_V1_01_DaO;
import de.moaiosbeer.dao.User_V1_01_DaO;
import de.moaiosbeer.db.models.Game_V1_01;
import de.moaiosbeer.db.models.Playsheet_V1_01;
import de.moaiosbeer.db.models.User_V1_01;


/**
 * Dieser Api Pfad [ ./rest/v1.01/games ] Ist der Hauptaufsatzpunkt der API_Games_V1_01
 * Welcher alle Öffentlichen Rest-Calls welche für die Klasse de.moaiosbeer.db.models.Games_V1_01.java 
 * bereitgestellt werden sollen.
 * 
 * Http Methoden mit Methoden Auffruf an dieser URL: GET oder POST
 * Weitere Methoden Erlaubt aber geben keinen Content zurück
 * @author Stephan
 */
@Path("v1.01/games")
public class API_Games_V1_01 {
	
	
	
	/**
	 * Dieser Api Pfad [ ./rest/v1.01/users/{auth}/create={gamename}/as={roleID} ],
	 * ist für die Rollen: {"admin","player","manager-gui"} zugänglich ( mittels POST ).
	 * Erstellt auf ein Post Request ein Spiel von dem User der den Request sendet,
	 * Wenn der Spielname nicht schon vergeben ist, und teilt Ihm der gewählten Rolle im Spiel zu.
	 * 
	 * @param token String: Auth Key
	 * @param gamename String: Name des Spiels welches erstellt werden soll
	 * @param roleId String: der Rolle 
	 * @return Format JSON + Statuscode  Http 500 wenn Spieler nicht im System Vorhanden ist | Http 406 wenn Spielname bereits Vergeben | Http 400 bei fehlerhaftem requst | Http 201 Wenn Spiel erstellt wird
	 */
	@POST
	@RolesAllowed({"admin","player","manager-gui"})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML,MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML,MediaType.TEXT_PLAIN})
	@Path("/{auth}/create={gamename}/as={roleID}")
	public Response create_game(@PathParam("auth") String token,@PathParam("gamename") String gamename,@PathParam("roleID") int roleId)
	{
		Game_V1_01_DaO gd = new Game_V1_01_DaO();
		Game_V1_01 g = new Game_V1_01();
		User_V1_01 u = new User_V1_01();
		g.setGamename(gamename);
		User_V1_01_DaO ud = new User_V1_01_DaO();
		try {
			// testen ob User Valide ist
			u = ud.getUser(ud.getUserIdByAuthToken(token));
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
		
		try {
			g.addPlayer(u,roleId);
			//Testen ob Spielname schon vergeben ist
			if(gd.existGame(g.getGamename()) == false){
			gd.newGame(g);
			}
			// Http 406 	Not Acceptable
			else return Response.status(406).entity("").build() ;
		} catch (Exception e) {
			e.printStackTrace();
			// 400 Bad Request
			return Response.status(400).entity("\"Error\":\"" + e.getMessage() + "\"").build();
		}
		
		return Response.status(201).build();
	}
	
	

	/**
	 * Dieser Api Pfad [ ./rest/v1.01/users/{auth}/join={gameid}/as={roleID} ],
	 * ist für die Rollen: {"admin","player","manager-gui"} zugänglich( mittels POST ).
	 * Ermöglicht Spielern auf ein Post Request einem Spiel Beizutreten, wenn dies nicht bereits Voll ist.
	 * @param token String: Auth Key
	 * @param gameid Long gameID
	 * @param roleId String: der Rolle 
	 * @return Format JSON + Statuscode  Http 400 bei fehlerhaftem requst | Http 500 bei server Fehler | Http 200 wenn game join durchgeht |
	 */
	@POST
	@RolesAllowed({"admin","player","manager-gui"})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML,MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML,MediaType.TEXT_PLAIN})
	@Path("/{auth}/join={gameid}/as={roleID}")
	public Response join_game(@PathParam("auth") String token,@PathParam("gameid") long gameid,@PathParam("roleID") int roleId)
	{
		roleId%=4;
		Game_V1_01_DaO gd = new Game_V1_01_DaO();
		Game_V1_01 g = new Game_V1_01();
		User_V1_01 u = new User_V1_01();
		User_V1_01_DaO ud = new User_V1_01_DaO();
		try {
			g = gd.GetGameById(gameid);
			u = ud.getUser(ud.getUserIdByAuthToken(token));
			System.out.println("Userid: " + u.getId());
			System.out.println("Role  : " + roleId);
		} catch (Exception e) {
			return Response.status(500).build();
		}
		
		try {
			g.addPlayer(u,roleId);
			gd.updateGame(g, u.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// 400 Bad Request
			return Response.status(400).entity("{\"Error\":\"" + e.getMessage() + "\"}").build();
		}
		return Response.status(200).build();
	}
	
	/**
	 * 
	 * Dieser Api Pfad [ ./rest/v1.01/users/{auth}/myplaysheet={gameid}],
	 * ist für die Rollen: {"admin","player","manager-gui"} zugänglich( mittels GET ).
	 * Ermöglicht Spielern auf ein Get Request Ihr Aktuelles Playsheet Abzurufen.
	 * @param token String: Auth Key
	 * @param gameid Long gameID
	 * @return Format JSON + Statuscode  Http 500 bzw 501 bei Server Fehler | Http 200 wenn Abfrage durchgeht
	 */
	@GET
	@RolesAllowed({"admin","player","manager-gui"})
	@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML,MediaType.TEXT_PLAIN})
	@Path("/{auth}/myplaysheet={gameid}")
	public Response get_playsheet_info(@PathParam("auth") String token,@PathParam("gameid") long gameid)
	{
		User_V1_01_DaO ud = new User_V1_01_DaO();
		long id = ud.getUserIdByAuthToken(token);
		Game_V1_01_DaO g = new Game_V1_01_DaO();
		Game_V1_01 game = g.GetGameById(gameid);
		long pid = 1;
		for(Playsheet_V1_01 pls : game.getPlaysheets())
		{
			if(pls.getOwner_id() == id)
			{
				pid = pls.getId();
				break;
			}
		}
		Playsheet_V1_01_DaO p = new Playsheet_V1_01_DaO();
		List<Playsheet_V1_01> ps = new ArrayList<Playsheet_V1_01>();
		try {
			ps = p.get_whole_playsheet(pid);
		} catch (Exception e) {
			return Response.status(500).build();
		}
		String json;
		if(ps.size()>1)
		{
			int average = 0;
			for(int i=0;i<ps.size()-1;i++)
			{
				average = ps.get(i).getNeworder();
			}
			average/=(ps.size()-1);
			try {
				json = "{\"role\":\"" + ps.get(0).getGamerole() + "\",\"average_order\":" + average + ",\"costs\":" + ps.get(ps.size()-1).getTotalcosts()
						+ ",\"available\":" + ps.get(ps.size()-1).getInventory()
						+ ",\"next_inc\":" + p.get_incoming(ps.get(0).getId(), ps.size()-1)
						+ ",\"delivered\":" + ps.get(ps.size()-1).getNeworder()
						+ ",\"playsheet\":";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				json = "{\"Error\": \"An unknown Error occured!\"}";
				return Response.status(500).build();
			}
			ObjectMapper mapper = new ObjectMapper();
			try {
				json += mapper.writeValueAsString(ps);
				json+="}";
			} catch (JsonGenerationException e) {
				e.printStackTrace();
				json = "{\"Error\": \"An unknown Error occured!\"}";
				return Response.status(501).build();
			} catch (JsonMappingException e) {
				e.printStackTrace();
				json = "{\"Error\": \"An unknown Error occured!\"}";
				return Response.status(501).build();
			} catch (IOException e) {
				e.printStackTrace();
				json = "{\"Error\": \"An unknown Error occured!\"}";
				return Response.status(501).build();
			}
		}
		else
		{
			List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
			for(Playsheet_V1_01 pls : game.getPlaysheets())
			{
				HashMap<String, String> hm = new HashMap<String, String>();
				try {
					hm.put("Player:",ud.getUser(pls.getOwner_id()).getUsername());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return Response.status(500).build();
				}
				hm.put("Role", pls.getGamerole());
				list.add(hm);
			}
			
			ObjectMapper mapper = new ObjectMapper();
			try {
				json = mapper.writeValueAsString(list);
			} catch (JsonGenerationException e) {
				json = "{\"Error\": \"An unknown Error occured!\"}";
				return Response.status(501).build();
			} catch (JsonMappingException e) {
				json = "{\"Error\": \"An unknown Error occured!\"}";
				return Response.status(501).build();
			} catch (IOException e) {
				json = "{\"Error\": \"An unknown Error occured!\"}";
				return Response.status(501).build();
			}
		}
		return Response.status(200).entity(json).build();
	}
	
	
	/**
	 * 
	 * Dieser Api Pfad [ ./rest/v1.01/users/{auth}/open],
	 * ist für die Rollen: {"admin","player","manager-gui"} zugänglich( mittels GET ).
	 * Ermöglicht Spielern Ihre momentan offenen Spiele abzufragen
	 * @param token String: Auth Key
	 * @return Format JSON + Statuscode  Http 500  bei Server Fehler | Http 200 wenn Abfrage durchgeht
	 */
	@GET
	@RolesAllowed({"admin","player","manager-gui"}) 
	@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML,MediaType.TEXT_PLAIN})
	@Path("/{auth}/open")
	public Response getGameIdByUserId(@PathParam("auth") String auth){
		try {
		/* Userdao anlegen um die userid eines nutzers anhand des AuthTokens zu finden*/
		User_V1_01_DaO Userdao = new User_V1_01_DaO();
		// Userid anhand des Tokens ermitteln
		@SuppressWarnings("unused")
		Long userID = Userdao.getUserIdByAuthToken(auth);
		
		// GameDao Instanzieren
		Game_V1_01_DaO gameDao = new Game_V1_01_DaO();
		List<Game_V1_01> games = gameDao.getOpenGamesByToken(auth);
		// Liste aller MeinerSpiel Details
		List<HashMap<String, Object>> myGameDetails = new ArrayList<HashMap<String,Object>>();
		for (Game_V1_01 g : games ){
			HashMap<String, Object> oneGame = new HashMap<String,Object>();
			oneGame.put("GameId", g.getId());
			oneGame.put("GameName", g.getGamename());
			oneGame.put("PlayerCount",g.getUserlist().size());
			// Die Hashmap der Liste Anfügen
			myGameDetails.add(oneGame);
		}
		// Server Antwort schreiben Http 200 ok + entity = Gamelist der Spiele
		return Response.status(200).entity(myGameDetails).build();  
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
}