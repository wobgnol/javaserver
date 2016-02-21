package de.moaiosbeer.tests;


import de.moaiosbeer.dao.Game_V1_01_DaO;
import de.moaiosbeer.dao.User_V1_01_DaO;
import de.moaiosbeer.db.models.Game_V1_01;
import de.moaiosbeer.db.models.TomcatUserRoles_V1_01;
import de.moaiosbeer.db.models.User_V1_01;

public class DaO_Test {
	/*
	 * Die Klasse DaO_Test ist ausschlieﬂlich zum Testen. 
	 */
	
	public static void main(String[] args) {
		
		User_V1_01_DaO u = new User_V1_01_DaO();   
		Game_V1_01_DaO g = new Game_V1_01_DaO();
		Game_V1_01 ga = new Game_V1_01();
		
		User_V1_01 user = new User_V1_01();
		
		
		//====	listRoleUser	Test	====
//		System.out.println(g.listRoleUser(1));
		
		//====	GetGameById	Test	====
//		long id=1;
//		Game_V1_01 ga = (g.GetGameById(id));
//		System.out.println(ga.getGamename());
		
		//====	openGames	Test	====
//		for(Game_V1_01 game : g.openGames())
//		{
//			System.out.println(game.getId());
//		}
//		for(Game_V1_01 game : g.activeGames())
//		{
//			System.out.println(game.getId());
//		}
		
		//====	isUser	Test	====
//		System.out.println(u.isUser("Ich", "1234"));
//		System.out.println(u.isUser("Heinrich", "1234"));
//		System.out.println(u.isUser("Ich2", "1234"));
//		System.out.println(u.isUser("Heinrich2", "1234"));
		
		//====	activeGames / openGames 	Test	====
//		System.out.println(g.activeGames());
//		System.out.println("------------------------------------------------------------------------");
//		System.out.println(g.openGames());
		
		//====	partofGame	Test	====
//		long g_id = 1;
//		if(g.partofGame(g_id))
//			System.out.println("true");
//		else
//			System.out.println("false");
//		g_id = 5;
//		if(g.partofGame(g_id))
//			System.out.println("true");
//		else
//			System.out.println("false");
//		g_id = 7;
//		if(g.partofGame(g_id))
//			System.out.println("true");
//		else
//			System.out.println("false");
			
		//====	User erstellen	====
//		u.newUser(new User_V1_01("Hanz", "1234"));
//		System.out.println(u.isUser("Hanz", "1234"));
//		
//		u.newUser(new User_V1_01("Heinz", "1234"));
//		System.out.println(u.isUser("Heinz", "1234"));
//		
//		u.newUser(new User_V1_01("Stepfan", "1234"));
//		System.out.println(u.isUser("Stepfan", "1234"));
//		
		//====	User erstellen / mit Rollenausgabe	====
//		User_V1_01 usu = new User_V1_01("abcd", "1234");
//		u.newUser(usu);
//		System.out.println(u.isUser("abcd", "1234"));
//		for(TomcatUserRoles_V1_01 str : usu.getRoles())
//			System.out.println(str.getRolename());
		
		//====	User einen Game zuordnen	====
//		try {
//			ga.setGamename("Timmi's Game");
//			ga.getId();
//			
//			ga.addPlayer(u.getUser(7));
//			ga.addPlayer(u.getUser(8));
//			ga.addPlayer(u.getUser(9));
//			ga.addPlayer(u.getUser(10));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		for(User_V1_01 us : ga.getUserlist())
//			System.out.println(us.getUsername());

		
//		u.deleteUser(user);
		
	}
}
