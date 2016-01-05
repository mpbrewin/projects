package libraries;

import java.util.HashMap;
import java.util.Map;

//This class maps a card's info (string) to its relevant image directory (string)
//This class is to be used in tandem with ImageLibrary
public class ImgDirectoryLibrary {
	private static final Map<String,String> imgPaths;
	
	//Note:
	//The key is a 3 digit string, where the first number represents the suit,
	//and the rest, the rank
	//Example Jack of clubs = 111	7 of diamonds =27
	//When images of cards are added to the img directory, their names.jpg should be
	//changed to the integer value that represents them (i.e. 111.jpg)
	static{
		imgPaths = new HashMap<String,String>();
		for (int i = 1; i < 5; i++){
			for (int j = 1; j < 15; j++){
				String s = ""+i+j;	//integer appending into a string
				imgPaths.put(s, "resources/images/cards/" + s + ".png");
			}
		}
	}
	
	public static String getDir(String info){
		return imgPaths.get(info);
	}
}
