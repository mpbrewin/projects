package networkingobjects;

import java.io.Serializable;
import java.util.Vector;

import gamemechanics.Player;

public class StartObj implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3381503963601658536L;

	private Vector<Player> players;
	public StartObj(Vector<Player> players) {
		this.players = players;
	}
	
	public Vector<Player> getPlayers(){
		return players;
	}

}
