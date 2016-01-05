package networkingobjects;

import java.io.Serializable;

public class ChatObject implements Serializable {

	private static final long serialVersionUID = -1261522988354358185L;

	private String message;
	private String name;
	public ChatObject(String Name, String Message){
		this.name = Name;
		this.message = Message;
	}
	public String getMessage(){
		return message;
	}
	public String getName(){
		return name;
	}
}