package networkingobjects;

import java.io.Serializable;

public class JoinObj implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5475997246513900793L;
	public final int playersjoin;
	
	public JoinObj(int playersjoined) {
		
		playersjoin = playersjoined;
		
	}
	
	
}
