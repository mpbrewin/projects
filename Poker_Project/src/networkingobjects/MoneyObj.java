package networkingobjects;

import java.io.Serializable;

public class MoneyObj implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8329466397512304121L;
	private Integer amount;
	public MoneyObj(Integer x){
	amount = x;
	}
	public Integer getMoney(){
		return amount;
	}
}
