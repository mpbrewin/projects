package networkingobjects;

import java.io.Serializable;

public class NewUser implements Serializable{
	private static final long serialVersionUID = -7588220594619846435L;
	public final String username;
	public final String password;
	public final String imagePath;
	public final int money;
	public final boolean guest;
	public NewUser(String username, String password, String imagePath, int money, boolean guest){
		this.username = username;
		this.password = password;
		this.imagePath = imagePath;
		this.money = money;
		this.guest = guest;
	}

}
