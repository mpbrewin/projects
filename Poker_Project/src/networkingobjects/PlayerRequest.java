package networkingobjects;

import java.io.Serializable;

//In sending the request, playa will be null.
//In receiving a response, playa will have the player
public class PlayerRequest implements Serializable{
	private static final long serialVersionUID = 8526060296393371639L;
	public final int id;
	public final String username;
	public PlayerRequest(int id, String uname){
		this.id = id;
		username = uname;
	}
}
