package gamemechanics;

import pokerclient.PokerClient;

public class GameManager {
	//global game parameters visible to all players
	private CardDeck deck;
	private Card[] flop;
	private int pot;
	private int currentBet; //Bet the players have to call
	private PokerClient currentTurn;	//May have to change to client
	//Player specifics
	private PokerClient me;			//May have to change to client
	
	public GameManager(PokerClient me){
		this.me = me;
	}
	
	
	
}
