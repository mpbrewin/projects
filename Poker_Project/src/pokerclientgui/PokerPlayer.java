package pokerclientgui;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class PokerPlayer {
	private String name;
	private ImageIcon userImage;
	
	private int cash;
	
	
	public PokerPlayer(String name, ImageIcon userImage){
		this.name = name;
		this.userImage = userImage;
		cash = 1000;
	}
	
	public int getCash(){
		return cash;
	}

	public String getName(){
		return name;
	}

	public Icon getProfile() {
		return userImage;
	}
}
