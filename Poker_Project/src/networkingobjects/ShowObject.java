package networkingobjects;

import java.io.Serializable;
import java.util.Vector;

public class ShowObject implements Serializable{
	public Vector<Boolean> show;
	private static final long serialVersionUID = -20176728468305264L;
	public final Vector<String[]> playerCards;
	public ShowObject(Vector<String[]> pc){
		playerCards = pc;
		show = new Vector();
	}
	public void setShow(Vector<Boolean> m){
		for(int i=0; i<m.size(); i++){
			show.add(m.get(i));
		}
	}
}