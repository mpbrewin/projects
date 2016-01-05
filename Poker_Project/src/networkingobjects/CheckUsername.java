package networkingobjects;

import java.io.Serializable;

public class CheckUsername implements Serializable {
	private static final long serialVersionUID = -5287340048764855566L;
	public final int id;	//Client requesting
	public final String username;
	public final boolean exists;
	//Whether the username exists on file or not
	public CheckUsername(int id, String username, boolean exists){
		this.id = id;
		this.username = username;
		this.exists = exists;
	}
}
