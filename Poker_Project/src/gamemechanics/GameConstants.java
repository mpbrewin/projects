package gamemechanics;

import java.awt.Color;

public class GameConstants {
	//Network related
	public final static String DATABASEIP = "localhost";
	public final static int DATABASEPORTNUMBER = 5555;
	//Game related
	public static final Color POKERTABLEGREEN;
	public static final String POKERTABLEIMAGE;
	public static final String TITLEIMAGE;
	public static final String BLACKPOKERCHIP;
	public static final String REDPOKERCHIP;
	
	static{
		POKERTABLEGREEN = new Color(57,142,51);
		POKERTABLEIMAGE = new String("resources/images/PokerTable.jpg");
		TITLEIMAGE = new String("resources/images/Title.png");
		BLACKPOKERCHIP = new String("resources/images/BlackPokerChip.png");
		REDPOKERCHIP = new String("resources/images/RedPokerChip.png");
				
	}
}
