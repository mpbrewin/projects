package gamemechanics;

import java.io.Serializable;
import java.util.ArrayList;

public class HandrankObject implements Serializable{

	private static final long serialVersionUID = 9016051866062511164L;
	private int rank; //0-7 highcard, pair, triple, straight, flush, fullhouse, fourkind, straight flush
	private int customrank;
	private ArrayList<Card> cards;
	private ArrayList<Card> two;
	private String handString;
	private int id;
	
	public HandrankObject(int rank, int customrank, ArrayList<Card> cards){
		this.rank = rank;
		this.customrank = customrank;
		this.cards = cards;
		
		/*
		if((customrank-(rank*15))>11)
			if(rank == 0){
				handString = "High Card of " + (customrank-(rank*15));
			}
			if(rank == 1){
				handString = "Pair of " + (customrank-(rank*15));
			}
			*/
	}
	public int getrank(){
		return rank;
	}
	public int getcustomrank(){
		return customrank;
	}
	public ArrayList<Card> getcards(){
		return cards;
	}
	public void setID(int x){
		id = x;
	}
	public int getID(){
		return id;
	}
	public ArrayList<Card> getTwo(){
		return two;
	}
	public void setTwo(Card[] c){
		two = new ArrayList<Card>();
		for(int i=0; i<2; i++){
			two.add(c[i]);
		}
	}
}
