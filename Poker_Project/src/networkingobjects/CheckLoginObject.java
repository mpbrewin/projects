package networkingobjects;

import java.io.Serializable;

public class CheckLoginObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 112596486192171728L;
	
	public final String username;
	public final boolean check;
	public final int id;
	
	public CheckLoginObject(String username, boolean check, int id) {
		this.username = username;
		this.check = check;		
		this.id = id;
	}

}
