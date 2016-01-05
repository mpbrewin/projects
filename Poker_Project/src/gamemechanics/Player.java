package gamemechanics;

import java.awt.Image;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.ImageIcon;

import networkingobjects.NewDeal;

public class Player implements Serializable {
	private static final long serialVersionUID = -1158873880730684415L;
	//Generic, nonvolatile data
	private String name;
	private int money; //cummulative money
	private ImageIcon profile;
	private final boolean guest;
	//Game specific, volatile data
	private Card[] cards;	//The players own two cards
	//private Card[] hand;	//The hand, consisting of both the player's own cards and the flop
	private ArrayList<Card> hand;
	public Card[] flop;	//Makes it easier to deduce hand
	//private Card[] hand;	//The hand, consisting of both the player's own cards and the flop>>>>>>> 06acfb88b997ab79a20da8f01a1d29d05c80a81a
	private int dealer;
	private int littleBlind;
	private int bigBlind;
	private boolean folded;
	private int bet;	//The amount the user has bet THIS BETTING ROUND
	private boolean out;	//If they are out of money
	
	
	public Player(String name, int money, String imgFilePath, boolean guest){
		this.name = name;
		this.money = money;
		//this.image = img;
		this.guest = guest;
	}
	
	//Generic player info
	public String getName(){ return name;}
	public int getMoney(){ return money;}
//	public Image getProfilePic() { return image;}
	public boolean isGuest() { return guest;};
	
	//Game specific player info
	public void addMoney(int val){
		money += val;
	}
	
	
	public void deductMoney(int val){
		money -= val;
		
	}
	
	public Card[] getCards() {
		return cards;
	}
	
	public void setCards(Card[] c){
		cards = c;
	}
	
	public void fold(){
		bet = 0;
		folded = true;
	}
	
	public boolean hasFolded(){
		return folded;
	}
	
	public void unFold(){
		folded = false;
	}
	
	
	public int call(int toCall){
			if (money < toCall){
				int temp = money;
				money = 0;
				out = true;
				return temp;
			}
			money-=toCall;
			return toCall;
	}
	
	
	public void raise(int toRaise){
		money-=toRaise;
	}
	
	public void newDeal(NewDeal nd){
		folded = false;
		bet = 0;
		cards = nd.cards;
		flop = nd.flop;
		dealer = nd.dealer;
		littleBlind = nd.littleBlind;
		bigBlind = nd.bigBlind;
	}
	
	public ImageIcon getProfile(){
		return profile;
	}
	
	public int isDealer(){ return dealer;}
	public int isLittleBlind(){ return littleBlind; }
	public int isBigBlind() {return bigBlind;}
	
	
	//returns rank*15 + highcard of rankhand
	public HandrankObject finalhandrank(){
		int finalrank = 0;
		int rank = 0;
		Vector<ArrayList<Card>> hands =  new Vector< ArrayList<Card>>(); //list 
		
		ArrayList<Card> al= new ArrayList<Card>();
		
		// for 012
		al.add(cards[0]);
		al.add(cards[1]);
		al.add(flop[0]);
		al.add(flop[1]);
		al.add(flop[2]);
		hands.add(al);
		

		// for 013
		ArrayList<Card> aa= new ArrayList<Card>();

		aa.add(cards[0]);
		aa.add(cards[1]);
		aa.add(flop[0]);
		aa.add(flop[1]);
		aa.add(flop[3]);
		hands.add(aa);
				
				// for 014
		ArrayList<Card> ab= new ArrayList<Card>();

		ab.add(cards[0]);
		ab.add(cards[1]);
		ab.add(flop[0]);
		ab.add(flop[1]);
		ab.add(flop[4]);
		hands.add(ab);

				// for 023
		ArrayList<Card> ac= new ArrayList<Card>();

		ac.add(cards[0]);
		ac.add(cards[1]);
		ac.add(flop[0]);
		ac.add(flop[2]);
		ac.add(flop[3]);
		hands.add(ac);
				
		//al.clear();

				// for 024
		ArrayList<Card> ad= new ArrayList<Card>();

		ad.add(cards[0]);	
		ad.add(cards[1]);
		ad.add(flop[0]);
		ad.add(flop[2]);
		ad.add(flop[4]);
		hands.add(ad);
				
				
		//al.clear();

				// for 034
		ArrayList<Card> ae= new ArrayList<Card>();

				ae.add(cards[0]);
				ae.add(cards[1]);
				ae.add(flop[0]);
				ae.add(flop[3]);
				ae.add(flop[2]);
				hands.add(ae);
				
				//al.clear();
				ArrayList<Card> af= new ArrayList<Card>();

				// for 123
				af.add(cards[0]);
				af.add(cards[1]);
				af.add(flop[1]);
				af.add(flop[2]);
				af.add(flop[3]);
				hands.add(af);
				
				//al.clear();
				ArrayList<Card> ag= new ArrayList<Card>();

				// for 124
				ag.add(cards[0]);
				ag.add(cards[1]);
				ag.add(flop[1]);
				ag.add(flop[2]);
				ag.add(flop[4]);
				hands.add(ag);
				
				//al.clear();
				ArrayList<Card> ah= new ArrayList<Card>();

				// for 134
				ah.add(cards[0]);
				ah.add(cards[1]);
				ah.add(flop[1]);
				ah.add(flop[3]);
				ah.add(flop[4]);
				hands.add(ah);
				
				
				//al.clear();
				ArrayList<Card> at= new ArrayList<Card>();

				// for 234
				at.add(cards[0]);
				at.add(cards[1]);
				at.add(flop[2]);
				at.add(flop[3]);
				at.add(flop[4]);
				hands.add(at);
				
///////////////////////////////
				// vector hands has all possible combinations of hands
				
		boolean flush = true;
		boolean straight = false;
		int straightHigh = 0;
		
		
		for(int i=0; i< hands.size(); i++){
			int moverank;
			int mrank;
			Collections.sort(hands.elementAt(i), new CustomComparator());
			ArrayList<Card> temp = hands.elementAt(i);
			
			
			
			//Flush
			for(int j=1; j<5; j++){
				if(temp.get(0).suit!=temp.get(j).suit){
					flush = false;
				}
			}

			//Straights
			if(temp.get(1).rank==temp.get(0).rank +1 &&
			   temp.get(2).rank==temp.get(0).rank +2 &&
			   temp.get(3).rank==temp.get(0).rank +3 &&
			   temp.get(4).rank==temp.get(0).rank +4){
			   straight = true;
			   straightHigh = temp.get(0).rank +4;
			}
			else if (temp.get(0).rank==2 &&
					temp.get(1).rank==3 &&
					temp.get(2).rank==4 &&
					temp.get(3).rank==5 &&
					temp.get(4).rank==14){
				   straight = true;
				   straightHigh = 5;
			}
			//}
			//Look for matches
			Vector<Integer> pair = new Vector<Integer>();
			boolean triple = false;
			boolean fourkind = false;
			
			for(int j=0; j<5; j++){
				for(int k=j+1; k<5; k++){
					if(temp.get(j).rank==temp.get(k).rank){
						if(pair.size()==0){
							pair.add(temp.get(j).rank);
						}
						else if(pair.size()==1){
							if(pair.get(0)<temp.get(j).rank){
								pair.add(temp.get(j).rank);
							}
							else if(pair.get(0)>temp.get(j).rank){
								pair.add(temp.get(j).rank);
								pair.set(1, pair.get(0));
								pair.set(0, temp.get(j).rank);
							}
							else{
								if(triple == true){
									fourkind = true;
									triple = false;
								}
								else {
									triple = true;
								}
							}
						}
					}
				}
			}
			boolean fullhouse = false;
			int fh_high = 0;
			if(triple ==true && pair.size()==2){
				fullhouse = true;
				fh_high = temp.get(2).rank;
			}
					
			
				if(flush==true && straight ==true){
					moverank = 7;
					moverank = (moverank*15)+straightHigh;
					mrank = 7;
				}
				else if(fourkind==true){
					moverank = (6*15)+temp.get(2).rank;
					mrank=6;
				}
				else if(fullhouse==true){
					moverank = (5*15)+temp.get(2).rank;
					mrank=5;
				}
				else if(flush==true){
					moverank = (4*15)+temp.get(4).rank;
					mrank=4;
				}
				else if(straight==true){
					moverank = (3*15)+temp.get(4).rank;
					mrank=3;
				}
				else if(triple==true){
					moverank = (2*15)+temp.get(2).rank;
					mrank=2;
				}
				else if(pair.size()==1){
					moverank = (1*15)+pair.elementAt(0);
					mrank=1;
				}
				else{
					moverank = temp.get(4).rank;
					mrank=0;
				}
				
			
			if(finalrank==0 || moverank>finalrank){
				finalrank=moverank;
				rank = mrank;
				hand = temp;
			}
		}
		
		
		return new HandrankObject(rank, finalrank, hand);
			
	}		
			
	
	
	
	public class CustomComparator implements Comparator<Card>, Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -3396528627174108064L;

		@Override
	    public int compare(Card o1, Card o2) {
	        if(o1.rank>o2.rank) return o1.rank;
	        else if(o1.rank==o2.rank) return 0;
	        else return -1;
	    }
	}
		
	
			
		
		
	
	
}


