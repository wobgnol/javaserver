package de.moaiosbeer.tests;

import de.moaiosbeer.dao.Playsheet_V1_01_DaO;

public class TimsDaoTest {
		public static void main(String[] args)
		{
			long pid = 1;
			int round = 1;
			Playsheet_V1_01_DaO p = new Playsheet_V1_01_DaO();
			
			System.out.println("Verf+gbare Playsheets: " + (p.get_next_playsheetid()-1));
			for(;pid<p.get_next_playsheetid();pid++)
			{
				for(;round<15;round++)
				{
					try
					{
						System.out.println("Playsheet: " + pid + " - Round: " + round + "- Incoming: " + p.get_incoming(pid,round));
					}
					catch(Exception e)
					{
						System.out.println("Playsheet: " + pid + " - Round: " + round + "- Incoming: Exception!");
						e.printStackTrace();
					}
				}
			}
			
			try
			{
				System.out.print("Set Incoming for Round: 14...");
				p.incommingIn2(1, 14,45);
				System.out.println("done");
			}
			catch(Exception e)
			{
				System.out.println("Failed");
				e.printStackTrace();
			}
			
		}
}
