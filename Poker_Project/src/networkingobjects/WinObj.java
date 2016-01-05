package networkingobjects;

import java.io.Serializable;

public class WinObj implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6624808953600425663L;
	public int winner;
	public WinObj(int ID){
		winner = ID;
	}
}
