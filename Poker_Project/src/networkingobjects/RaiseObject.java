package networkingobjects;

import java.io.Serializable;

public class RaiseObject implements Serializable {

	private static final long serialVersionUID = -8482373289700825553L;
	public final int raiseBy;
	public final int id;
	public final int call;
	public  int maxShare;
	public  int myShare;
	
	public final String name;
	public RaiseObject(int id, int r, int call, String name){
		this.raiseBy = r;
		this.id = id;
		this.name = name;
		this.call = call;
	}

}
