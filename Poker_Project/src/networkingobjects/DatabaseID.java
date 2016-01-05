package networkingobjects;

import java.io.Serializable;

public class DatabaseID implements Serializable {
	private static final long serialVersionUID = 1107877207030394888L;
	public final int id;
	public DatabaseID(int i){
		id = i;
	}
}
