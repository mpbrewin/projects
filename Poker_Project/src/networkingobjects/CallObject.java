package networkingobjects;

import java.io.Serializable;

public class CallObject implements Serializable{

	private static final long serialVersionUID = -4868946266876414982L;
	public final int called;
	public final int id;
	public final String name;
	public  int maxShare;
	public  int myShare;
	
	public CallObject(int id, int called, String name){
		this.id = id;
		this.called = called;
		this.name = name;
	}

	
}
