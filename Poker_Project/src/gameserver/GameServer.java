package gameserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import gamemechanics.Card;
import gamemechanics.CardDeck;
import gamemechanics.HandrankObject;
import gamemechanics.Player;
import networkingobjects.CallObject;
import networkingobjects.ChatObject;
import networkingobjects.CheckObject;
import networkingobjects.FlipObject;
import networkingobjects.FoldObject;
import networkingobjects.JoinObj;
import networkingobjects.MoneyObj;
import networkingobjects.NewDeal;
import networkingobjects.RaiseObject;
import networkingobjects.ShowObject;
import networkingobjects.WinObj;

/*
 * NOTE: HAVENT IMPLEMENTED ENDING OF THE BETTING ROUND
 * 
 */
public class GameServer  implements Runnable{
	private Vector<PlayerCommunicator> connections;
	private int numPlayers;
	private int playersJoined = 0;
	
	private CardDeck deck = new CardDeck();
	private Card[] flop = new Card[5];
	private int betToMatch = 0;
	private Vector<Integer> playerBets;
	private Vector<String[]> playerCards; //Really just the imagepath
	private int dealer = -1;
	private int littleBlind = 0;
	private int bigBlind = 1;
	private int playerTurn = 0;
	private int lastPlayerToRaise;
	private Vector<Boolean> stillIn;	//true if the player hasn't folded
	private int numNotFolded = 0;
	private Vector<Integer> ids;
	private Vector<Player> players;
	private Vector<Integer> amounts;
	private Vector<HandrankObject> hands;
	private boolean littleBlindHasMadeMove;
	private int roundPlayer;
	private int bettingRound = 0;
	private int maxPShare = 0;
	//private Vector<Player> actual_pobjs;
	private ServerSocket ss;

	
	public GameServer(int portNumber, int numPlayers) {
		try{
			this.numPlayers = numPlayers;
			//Setup the network
			ss = new ServerSocket(portNumber);
			connections = new Vector<PlayerCommunicator>();
			stillIn = new Vector<Boolean>();
			ids = new Vector<Integer>();
			players = new Vector<Player>();
			amounts = new Vector<Integer>();
			hands = new Vector<HandrankObject>();
			playerBets = new Vector<Integer>();
			playerCards = new Vector<String[]>();
			System.out.println("GameServer: GameServer created");
			//Continuously accept connections. This will allow players to join midgame		
		}
		catch(IOException ioe){
			System.out.println("Exception in GameServer");
		}
	}
	
	@Override
	public void run() {
		try{
			while(playersJoined != numPlayers){
				boolean filledSpot = false;
				Socket s = ss.accept();
				playersJoined++;
				//Give the user an ID so we can handle the disconnects properly
				int playerID = connections.size(); 
				//Check if there are any empty spots to be filled
				for(int i=0; i < connections.size(); i++){
					if(connections.elementAt(i) == null){
						playerID = i;
						filledSpot = true;
						break;
					}
				}
				PlayerCommunicator pc = new PlayerCommunicator(this, s, playerID);
				new Thread(pc).start();
				if(!filledSpot)
					connections.addElement(pc);
				else
					connections.set(playerID, pc);
			
				//Tell the host someone joined
				PlayerCommunicator pchost = connections.elementAt(0);
				pchost.playerHasJoined(new JoinObj(playersJoined));
			//	System.out.println("GameServer: accepted client " + playerID);
				stillIn.addElement(new Boolean(false));
				playerBets.addElement(0);
				playerCards.addElement(null);
			}
			//Give time for the clients to update gui
			Thread.sleep(500);
			//Players have all joined. Send each client the list of players
			for(int i=0; i<connections.size(); i++){
				if(connections.elementAt(i)!=null){
					PlayerCommunicator pc = connections.elementAt(i);
					pc.startGame(players);
				}
			}
			newDeal();
		}
		 catch(IOException | InterruptedException ioe) {
			ioe.getMessage();
		}
	}
	
	//Called each time someone collects the pot
	//Reinitialize the game to start a new round
	//Unfold every player, set the pot to 0, etc.
	public void newDeal(){
		maxPShare = 0;
		numNotFolded = 0;
		//Make a new deck
		deck.shuffleDeck();
		//Reset the bet to match
		betToMatch = 0;
		bettingRound = 0;
		//Make a new flop
		for (int i = 0; i < 5; i++){
			flop[i] = deck.drawCard();
		}
		//Specify a new dealer,littleblind, and big blind
		dealer++;
		if(dealer == connections.size()) dealer = 0;
		while(connections.elementAt(dealer) == null){
			dealer++;
			if(dealer == connections.size()) dealer = 0;
		}
		littleBlind++;
		if(littleBlind == connections.size()) littleBlind = 0;
		while(connections.elementAt(littleBlind) == null){
			littleBlind++;
			if(littleBlind == connections.size()) littleBlind = 0;
		}
		 
		if(littleBlind+1 == connections.size()){
			playerTurn = 0;
		}
		else{
			playerTurn = littleBlind+1;
		}
		
			if(playerTurn+1==connections.size()){
				playerTurn = 0;
			}
			else{
				playerTurn= playerTurn+1;
			}
		
		//Reset the littleBlind as last raise made
		roundPlayer = playerTurn;
		lastPlayerToRaise = playerTurn;
		PlayerCommunicator p = connections.elementAt(playerTurn);
		p.giveTurn(true);
		bigBlind++;
		if(bigBlind == connections.size()) bigBlind = 0;
		while(connections.elementAt(bigBlind) == null){
			bigBlind++;
			if(bigBlind == connections.size()) bigBlind = 0;
		}
		for (int j = 0; j < connections.size(); j++){
			PlayerCommunicator pc = connections.elementAt(j);
			if (pc != null){
				numNotFolded+=1;
				//Update all players to playing
				stillIn.set(j, true);
				playerBets.set(j, 0);
				//Draw 2 new cards for the player
				Card[] cards = new Card[2];
				cards[0] = deck.drawCard();
				cards[1] = deck.drawCard();
				//Keep track of their card (images) so we can send them to all the players
				//when ready to flip
				String[] playerCardImages = new String[2];
				playerCardImages[0] = cards[0].imagePath;
				playerCardImages[1] = cards[1].imagePath;
				playerCards.set(j, playerCardImages);
				
				pc.notifyOfNewDeal(new NewDeal(flop,cards,dealer,littleBlind,bigBlind));
			}
		}
	}
	
	//Called after any move
	private void nextTurn(){
		
		if(roundPlayer+1==playerBets.size()){
			if(playerTurn == 0 && littleBlindHasMadeMove == false){
				littleBlindHasMadeMove = true;
			}
		}
		else{
			if(playerTurn == roundPlayer+1 && littleBlindHasMadeMove == false){
				littleBlindHasMadeMove = true;
			}
		}
		playerTurn++;
		while(true){
			if(playerTurn == playerBets.size()) playerTurn = 0;

			if(!stillIn.get(playerTurn)) playerTurn++;
			if(playerTurn == playerBets.size()) playerTurn = 0;
			if(stillIn.get(playerTurn)) break;
		}
		///////
		if(playerTurn == playerBets.size()) playerTurn = 0;
		while(playerBets.elementAt(playerTurn) == null){
			playerTurn++;
			if(playerTurn == playerBets.size()) playerTurn = 0;
		}
		//Turn has gone back to the raising player, and the betting round has started
		if(playerTurn == lastPlayerToRaise && littleBlindHasMadeMove == true){
			//Next betting round
			//bettingRound++;
			//playerTurn = littleBlind;
			
			
			if(littleBlind+1 == connections.size()){
				playerTurn = 0;
			}
			else{
				playerTurn = littleBlind+1;
			}
			
				if(playerTurn+1==connections.size()){
					playerTurn = 0;
				}
				else{
					playerTurn= playerTurn+1;
				}
				
				while(true){
					if(playerTurn == playerBets.size()) playerTurn = 0;

					if(!stillIn.get(playerTurn)) playerTurn++;
					if(playerTurn == playerBets.size()) playerTurn = 0;
					if(stillIn.get(playerTurn)) break;
				}
				roundPlayer = playerTurn;
				lastPlayerToRaise = playerTurn;
				littleBlindHasMadeMove = false;
			//Players are ready to flip their own cards over
			//Return prematurely and prep to determine winner
			if(bettingRound == 3){
				flipAllPlayerCards();
				return;
				//decide who wins
				
			}
			else{
				//So now we tell the players to flip the flop card
				for(int i=0; i<connections.size();i++){
					PlayerCommunicator pc = connections.elementAt(i);
					if(pc != null){
						pc.notifyOfFlip(new FlipObject(bettingRound));
					}
				}
				//Now reset all of the players bets (if they are still in)
				resetPlayerBets();
			}
			bettingRound++;

		}

		PlayerCommunicator pc = connections.elementAt(playerTurn);
		if( pc != null){
			pc.giveTurn(new Boolean(true));
		}
		else{
			System.out.println("GameServer: error in nextTurn");
		}
	}
	
	//All possible player actions, signals the end of a turn
	public void foldPlayer(FoldObject fo){
		stillIn.set(playerTurn, false);
		playerBets.set(playerTurn, null);
		numNotFolded-=1;
		if(lastPlayerToRaise==playerTurn){
			if(playerTurn==connections.size()-1){
				lastPlayerToRaise = 0;
			}
			else{
				lastPlayerToRaise = playerTurn+1;
			}
			
		}
		if(roundPlayer==playerTurn){
			if(playerTurn==connections.size()-2){
				roundPlayer = 0;
			}
			else{
				roundPlayer++;
			}
			while(true){
				if(roundPlayer == playerBets.size()) roundPlayer = 0;

				if(!stillIn.get(roundPlayer)) roundPlayer++;
				if(roundPlayer == playerBets.size()) roundPlayer = 0;
				if(stillIn.get(roundPlayer)) break;
			}
			
		}
		
		for(int i=0; i<connections.size();i++){
			PlayerCommunicator pc = connections.elementAt(i);
			if(pc != null){
				pc.notifyOfFold(fo);
			}
		}
		
		int check =0;
		int idcheck = -1;
		for(int i=0; i<stillIn.size();i++){
			if(stillIn.get(i)==true){
				check+=1;
				idcheck = i;
			}
		}
		if(check==1){
			for(int i=0; i<connections.size(); i++){
				PlayerCommunicator pc = connections.elementAt(i);
				if(pc!=null){
					pc.sendWinner(new WinObj(idcheck));
				}
			}
			newDeal();
		}
		
		nextTurn();
	}
	
	public void checkPlayer(CheckObject cho){
		for(int i=0; i<connections.size();i++){
			PlayerCommunicator pc = connections.elementAt(i);
			if(pc != null){
				pc.notifyOfCheck(cho);
			}
		}
		nextTurn();
	}
	
	public void raiseBet(RaiseObject ro){
		if(ro.myShare>maxPShare){
			maxPShare = ro.myShare;
		}
		betToMatch += ro.raiseBy;
		playerBets.set(playerTurn, betToMatch);
		for(int i=0; i<connections.size();i++){
			PlayerCommunicator pc = connections.elementAt(i);
			if(pc != null){
				ro.maxShare = maxPShare;
				pc.notifyOfRaise(ro);
			}
		}
		lastPlayerToRaise = playerTurn;
		nextTurn();
	}
	
	public void called(CallObject co){
		if(co.myShare>maxPShare){
			maxPShare = co.myShare;
		}
		playerBets.set(playerTurn, betToMatch);
		for(int i=0; i<connections.size();i++){
			PlayerCommunicator pc = connections.elementAt(i);
			if(pc != null){
				co.maxShare = maxPShare;
				pc.notifyOfCall(co);
			}
		}
		nextTurn();
	}
	
	public void addPlayer(Player p){
		players.addElement(p);
		amounts.addElement(0);
	}
	
	public void sendChat(ChatObject cho){
		for(int i=0; i<connections.size(); i++){
			PlayerCommunicator pc = connections.elementAt(i);
			if(pc!=null){
				pc.sendMessage(cho);
			}
		}
	}
	
	private void resetPlayerBets(){
		for(int i=0; i<playerBets.size(); i++){
			if(playerBets.elementAt(i) != null){
				playerBets.setElementAt(i, 0);
			}
		}
	}
	
	private void flipAllPlayerCards(){
		for(int i=0; i<connections.size(); i++){
			PlayerCommunicator pc = connections.elementAt(i);
			if(pc!=null){
				ShowObject so = new ShowObject(playerCards);
				so.setShow(stillIn);
				pc.showPlayerCards(so);
			}
		}
	}
	public void updateMoney(int ix, int px){
		amounts.set(ix, px);
	}
	public void updateHand(HandrankObject ho){
		hands.addElement(ho);
		if(hands.size()==numNotFolded){
			decideWin();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			hands.clear();
			newDeal();
		}

		
	}
	public void decideWin(){
		int winner = 0;
		for(int i=1; i< hands.size();i++){
			if(hands.get(winner).getrank()<hands.get(i).getrank()){
				winner = i;
			}
			else if(hands.get(winner).getrank()==hands.get(i).getrank()){
				if(hands.get(winner).getcustomrank()<hands.get(i).getcustomrank()){
					winner = i;
				}
				else if(hands.get(winner).getcustomrank()==hands.get(i).getcustomrank()){
					//do something here
					//System.out.println("error two winners");
				}
			}
			
		}
		Integer winnerID = hands.get(winner).getID();
		
		for(int i=0; i<connections.size(); i++){
			PlayerCommunicator pc = connections.elementAt(i);
			if(pc!=null){
				pc.sendWinner(new WinObj(winnerID));
			}
		}
		
	}
	
	/*
	public void checkOver(){
		int check = 0;
		int idcheck = -1;
		for(int i=0; i<amounts.size(); i++){
			if(amounts.get(i) >0){
				check +=1;
				idcheck = i;
			}
		}
		if(check==1){
			OverObj oo = new OverObj();
			for(int i=0; i<connections.size(); i++){
				PlayerCommunicator pc = connections.elementAt(i);
				if(pc!=null){
					pc.gameOver(oo);
				}
			}
		}
	}
	*/
	
}
