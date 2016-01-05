package gamemechanics;

import java.awt.Image;
import java.io.Serializable;

import libraries.ImageLibrary;
import libraries.ImgDirectoryLibrary;

public class Card implements Serializable{

	private static final long serialVersionUID = 3880486749274434787L;
	public final int rank; //1-10 = 1-10, 11=J 12=Q 13=K 14=A
	public final int suit;	//1=Clubs 2=Diamonds 3=Hearts 4=Spades (alphabetical)
	public final String imagePath;
	
	public Card(int r, int s){
		this.rank = r;
		this.suit = s;
		String info = ""+s+r;
		imagePath = "resources/images/" + info + ".png";
	}
	
}
