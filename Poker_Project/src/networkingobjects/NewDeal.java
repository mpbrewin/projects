package networkingobjects;

import java.io.Serializable;
import java.util.Vector;

import gamemechanics.Card;
import gamemechanics.Player;

//This object will be sent to each client every time the pot is collected
//Gives them new cards and notifies them if they are dealer,etc 
public class NewDeal implements Serializable{
	private static final long serialVersionUID = -2280793379759580337L;
	public final Card[] flop;
	public final Card[] cards;
	public final int dealer, littleBlind, bigBlind;
	public NewDeal(Card[] flop, Card[] cards, int dealer, int littleBlind, int bigBlind){
		this.flop = flop;
		this.cards = cards;
		this.dealer = dealer;
		this.littleBlind = littleBlind;
		this.bigBlind = bigBlind;
	}
}