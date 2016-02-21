package de.moaiosbeer.rest_api;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.criterion.Restrictions;

import de.moaiosbeer.dao.Game_V1_01_DaO;
import de.moaiosbeer.dao.Playsheet_V1_01_DaO;
import de.moaiosbeer.dao.User_V1_01_DaO;
import de.moaiosbeer.db.models.Game_V1_01;
import de.moaiosbeer.db.models.Playsheet_V1_01;
import de.moaiosbeer.db.models.User_V1_01;
import de.moaiosbeer.hibernate.Hib_DB_Conn_V1_01;


/**
 * Dieser API Pfad dient dem Login_Servlet als Nutzer Validierung für den Login
 * @author Stephan
 */
@Path("v1.01/games")
public class API_Games_V1_01 {
	
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
			u = ud.getUser(ud.getUserIdByAuthToken(token));
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
		
		try {
			g.addPlayer(u,roleId);
			gd.newGame(g);
		} catch (Exception e) {
			e.printStackTrace();
			// 400 Bad Request
			return Response.status(400).entity("\"Error\":\"" + e.getMessage() + "\"").build();
		}
		
		return Response.status(201).build();
	}
	
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
	
	@GET
	@RolesAllowed({"admin","player","manager-gui"}) 
	@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML,MediaType.TEXT_PLAIN})
	@Path("/{auth}/open")
	public Response getGameIdByUserId(@PathParam("auth") String auth){
		try {
		/* Userdao anlegen um die userid eines nutzers anhand des AuthTokens zu finden*/
		User_V1_01_DaO Userdao = new User_V1_01_DaO();
		// Userid anhand des Tokens ermitteln
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