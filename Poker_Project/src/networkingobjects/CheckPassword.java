package networkingobjects;

import java.io.Serializable;

public class CheckPassword implements Serializable{
	private static final long serialVersionUID = 7252470112692171597L;
	public final int id;	//Client requesting
	public final String username;
	public final String password;
	public final boolean matches; //wheteher the passowrd matches or not
	public CheckPassword(int id, String username, String password, boolean matches){
		this.id = id;
		this.username = username;
		this.password = password;
		this.matches = matches;
	}
}
