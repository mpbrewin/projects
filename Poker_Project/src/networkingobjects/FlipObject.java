package networkingobjects;

import java.io.Serializable;

public class FlipObject implements Serializable{
	private static final long serialVersionUID = 8299063594641905159L;
	public final int bettingRound;
	public FlipObject(int bettingRound){
		this.bettingRound = bettingRound;
	}
}
