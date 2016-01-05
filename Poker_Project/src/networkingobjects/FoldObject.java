package networkingobjects;

import java.io.Serializable;

public class FoldObject implements Serializable {
	private static final long serialVersionUID = -3866984697318800879L;
	public final int id;
	public final String name;
	public FoldObject(int id, String name){
		this.id = id;
		this.name = name;
	}

}
