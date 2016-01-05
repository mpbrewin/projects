package pokerclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JOptionPane;

import gamemechanics.GameConstants;
import gamemechanics.HandrankObject;
import gamemechanics.Player;
import gameserver.GameServer;
import networkingobjects.CallObject;
import networkingobjects.ChatObject;
import networkingobjects.CheckLoginObject;
import networkingobjects.CheckObject;
import networkingobjects.CheckPassword;
import networkingobjects.CheckUsername;
import networkingobjects.DatabaseID;
import networkingobjects.FlipObject;
import networkingobjects.FoldObject;
import networkingobjects.JoinObj;
import networkingobjects.MoneyObj;
import networkingobjects.NewDeal;
import networkingobjects.NewUser;
import networkingobjects.PlayerID;
import networkingobjects.PlayerRequest;
import networkingobjects.RaiseObject;
import networkingobjects.ShowObject;
import networkingobjects.StartObj;
import networkingobjects.WinObj;
import pokerclientgui.FlopPanel;
import pokerclientgui.PlayerPanel;
import pokerclientgui.PokerClientWindow;

public class PokerClient {
	private static ObjectInputStream databaseIn, gameIn;
	private static ObjectOutputStream databaseOut, gameOut;
	private static String gameServerIPAddress;
	private static int gameServerPortNumber;
	private static int gameServerNumPlayers;
	
	private static PokerClientWindow pcw;	//Responsible for all of the client GUI
	private static Player player; 			//The player the client has logged in as
	private static DatabaseID databaseID;
	public static PlayerID playerID;		//The ID for the actual game, facilitates communication with the gameServer
	private static volatile boolean loggedIn = false;
	private static volatile boolean hasSelectedHostOrJoin = false;
	private static boolean choseToHost = false;	//If false, it means they chose to join an existing game
	private static int betToMatch = 0;
	private static int maxPotShare;
	private static int myPotShare;
	private static int currentBet = 0;
	private static volatile boolean running;	//Whether the user is not at the main menu
	private static int playersjoined;
	private static int playersleftjoin;
	private static volatile boolean canStartGame = false;
	public static Vector<PlayerPanel> ppVector = new Vector<PlayerPanel>();
	public static FlopPanel flopPanel = new FlopPanel();
	private static Vector<Player> players;
	
	
	
	public static void main(String[] args) {
		//Create a GUI for the client
		PokerClient.pcw = new PokerClientWindow();
		//Connect to the database
		connectToDatabase(GameConstants.DATABASEIP,GameConstants.DATABASEPORTNUMBER);
		//Busy wait for the database to acknowledge a login
		while(!loggedIn){}
		//At this point the user has logged in and the associated player has been set
		//Busy wait until the host has decided to host or hoin a game, 
		while(!hasSelectedHostOrJoin){}
		//Now see what their choice was, and take the necessary action
		//At this point the gameServerIPAdress and gameServerPortNumber have been set
		if(gameServerIPAddress == null){
			setUpGameServer(gameServerPortNumber);
		}
		else{
			joinGameServer(gameServerIPAddress,gameServerPortNumber);
		}
		
	
		//will read input from game here
		while(true){
			try{
				Object o = gameIn.readObject();
				//A new hand will be dealt
				if (o instanceof NewDeal){
					//System.out.println("Entered the NEW DEAL LOOP");
					NewDeal nd = (NewDeal)o;
					betToMatch = 0;
					myPotShare =0;
					currentBet = 100;
					maxPotShare = 100;
					player.newDeal(nd);	//update the players info
					ppVector.elementAt(playerID.id).setPlayer(player);		

					ppVector.elementAt(nd.dealer).updateLabelDealer();
					ppVector.elementAt(nd.littleBlind).updateLabelLittleBlind();
					ppVector.elementAt(nd.bigBlind).updateLabelBigBlind();
					
					if(nd.littleBlind==playerID.id){
						player.deductMoney(50);
						myPotShare +=50;
						
					}
					else if(nd.bigBlind==playerID.id){
						player.deductMoney(100);
						myPotShare+=100;
					}
					
					ppVector.elementAt(nd.littleBlind).deductAmount(50);
					ppVector.elementAt(nd.bigBlind).deductAmount(100);
					pcw.getClientPanel().getGameTable().flopPanel.potUpdate(150);

					
					
					/*update the gui:
					 * update the dealer, lil, big chips
					 * update the player's cards
					 * update the flop
					 * reset the pot
					 * ALL OF THIS INFO IS CONTAINED IN THE NEW DEAL CLASS
					 */
					//players = nd.players;
						
					//pcw.createGameTable(nd.players);
					
					ppVector.get(playerID.id).showCards();
					pcw.getClientPanel().getGameTable().flopPanel.resetCards();
					
					pcw.getClientPanel().getGameTable().setFlop(nd.flop);
					for(int i=0; i<players.size(); i++){
						if(i!=playerID.id){
							String[] st = {"resources/images/back.jpg" , "resources/images/back.jpg"};
							ppVector.elementAt(i).showCards(st);
							ppVector.elementAt(i).hideCards();
						}
					}
					//pcw.getClientPanel().getGameTable().flopPanel.potUpdate(150);


				}
				//It is this player's turn
				else if (o instanceof Boolean){
					/*ON GAMETABLE GUI:
					 * ENABLE THEIR BUTTONS
					 * (THEY SHOULD BE DISABLED WHEN THEY
					 * END THEIR TURN)
					 */
					//System.out.println("PokerClient: Boolean: Bet To Match " + betToMatch);
					pcw.getClientPanel().getGameTable().setButtons(true);
				}
				else if(o instanceof FoldObject){
					FoldObject fo = (FoldObject)o;
					/*update the gui:
					 * fold object contains an id that refers to which player
					 * to fold on the gui
					 * 
					 */
					ppVector.elementAt(fo.id).removeCards();
					//pcw.getClientPanel().getGameTable().fold(fo.id);
						
					
					//System.out.println("Poker Client: player " + fo.id + " folded");
					pcw.getClientPanel().getGameTable().setHistoryMessage(fo.name, " Folded", " ");
				}
				else if(o instanceof CheckObject){
					//Write to the game history
					CheckObject co = (CheckObject)o;
					//System.out.println("Poker Client: player checked");
					pcw.getClientPanel().getGameTable().setHistoryMessage(co.name, " Checked", " ");
				}
				
				else if(o instanceof RaiseObject){
					RaiseObject ro = (RaiseObject)o;
					
					//Raiseobject contains raiseBy
					//Subtract this amount from the players PlayerPanel

					//System.out.println("Poker Client: player " + ro.id +  "raised the current bet by " + ro.raiseBy);
					maxPotShare = ro.maxShare;
					
					ppVector.elementAt(ro.id).deductAmount(ro.raiseBy + ro.call);
					//This client received their own call object, so their betToMatch is 0
					maxPotShare = ro.maxShare;
					if(playerID.id == ro.id){
						//betToMatch = 0;
					}
					//This client received another player's raise, so increase their betToMatch
					else{
						betToMatch = ro.raiseBy + currentBet;
						//System.out.println("");
					}
					currentBet+=ro.raiseBy;
					
					String convertBetString = Integer.toString(currentBet);
					pcw.getClientPanel().getGameTable().setHistoryMessage(ro.name, " Raised. The current bet is ", convertBetString + " chips");
					pcw.getClientPanel().getGameTable().flopPanel.potUpdate(ro.raiseBy);
				}
				
				else if(o instanceof CallObject){
					CallObject co = (CallObject)o;
					//System.out.println("Poker Client: player " + co.id + " called " + co.called);
					maxPotShare = co.maxShare;
					ppVector.elementAt(co.id).deductAmount(co.called);
					if(playerID.id == co.id){
						betToMatch = 0;
					}
					
					String convertBetString = Integer.toString(co.called);
					pcw.getClientPanel().getGameTable().setHistoryMessage(co.name, " Called ", convertBetString + " chips");
					pcw.getClientPanel().getGameTable().flopPanel.potUpdate(co.called);
					
				}
				else if(o instanceof JoinObj) {
					JoinObj jo = (JoinObj)o;
					playersUpdate(playersleftjoin);
					--playersleftjoin;
				}
				else if(o instanceof StartObj) {
					//System.out.println("Entered the start object loop");
					StartObj so = (StartObj)o;
					players = so.getPlayers();
					
					pcw.createGameTable(players);
					
				}
				else if(o instanceof ChatObject){
					ChatObject cho = (ChatObject)o;
					pcw.getClientPanel().getGameTable().setMessage(cho.getName(),cho.getMessage());
				}
				//BettingRound has ended
				else if(o instanceof FlipObject){
					currentBet = 0;
					FlipObject fo = (FlipObject)o;
					pcw.getClientPanel().getGameTable().flopPanel.turnUpdate(fo.bettingRound);
					betToMatch = 0;
				}
				else if(o instanceof ShowObject){
					ShowObject so = (ShowObject)o;
					for (int i = 0; i < players.size(); i++){
						if(i != playerID.id){
							if(so.show.get(i)==true){
								ppVector.elementAt(i).showCards(so.playerCards.elementAt(i));
							}
						}
						
						
						//Sleep here so users have time to look at the cards and result
					}
					if(!player.hasFolded()){
						HandrankObject ho = player.finalhandrank();
						ho.setTwo(player.getCards());
						gameOut.writeObject(ho);
						gameOut.flush();
					}
					
					
				}
				else if(o instanceof MoneyObj){
					sendMoney();
				}
				else if(o instanceof WinObj){
					WinObj wo = (WinObj)o;
					int winner = wo.winner;
					if(winner == playerID.id){
						player.addMoney(pcw.getClientPanel().getGameTable().flopPanel.getPot());
					}
					pcw.getClientPanel().getGameTable().setHistoryMessage(players.get(winner).getName(), " won ", pcw.getClientPanel().getGameTable().flopPanel.getPot() + " chips");
					ppVector.elementAt(winner).setCash(pcw.getClientPanel().getGameTable().flopPanel.getPot());
					pcw.getClientPanel().getGameTable().flopPanel.clearPot();
					
				}
				
			}
			catch(IOException ioe){
				System.out.println(ioe.getMessage());
				System.out.println("Exceptio in pokerclient gameIn");
			}
			catch(ClassNotFoundException cnfe){
				System.out.println("Exceptio in pokerclient gameIn cnfe");
			}
		}
	}	
	
	private static void sendMoney() {
		//tells gameserver amount of money it has
		MoneyObj mo = new MoneyObj(player.getMoney());
		try {
			gameOut.writeObject(mo);
			gameOut.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void connectToDatabase(String databaseIP, int databasePortNumber){
		try{
			Socket sdb = new Socket(databaseIP,databasePortNumber);
			//Setup in/out stream for database
			databaseOut = new ObjectOutputStream(sdb.getOutputStream());
			databaseOut.flush();
			databaseIn = new ObjectInputStream(sdb.getInputStream());
			//Wait for the database to assign an ID
			Object o = databaseIn.readObject();
			databaseID = (DatabaseID)o;
		}
		catch(IOException ioe){
			System.out.println("Error in PokerClient:ConnectToDatabase");
		}
		catch(ClassNotFoundException cnfe){
			System.out.println("Error in PokerClient:ConnectToDatabase cnfe");
		}
	}
	
	//Called by LoginMenu/CreateAccountPanel
	//Ask the database if this username is registered
	public static boolean checkIfNameExists(String username){
		try{
			databaseOut.writeObject(new CheckUsername(databaseID.id, username, false));	//id, username, name already exists (arbitrary at this point)
			databaseOut.flush();
			//Wait to hear back
			Object response = databaseIn.readObject();
			CheckUsername cu = (CheckUsername)response;
			return cu.exists;
		}
		catch (IOException ioe){
			System.out.println(ioe.getMessage());
			return true;
		}
		catch(ClassNotFoundException cnfe){
			System.out.println(cnfe.getMessage());
			return true;
		}
	}
	
	public static boolean checkIfLoggedIn(String username) {
				try{
					databaseOut.writeObject(new CheckLoginObject(username, true, databaseID.id));
					databaseOut.flush();
					
					Object response = databaseIn.readObject();
					CheckLoginObject cp = (CheckLoginObject)response;
					return cp.check;
				}
				catch (IOException ioe) {
					System.out.println(ioe.getMessage());
					return false;
				}
				catch(ClassNotFoundException cnfe){
					System.out.println(cnfe.getMessage());
					return false;
				}
			}
			
	//Called by LoginMenu
	//Ask the database if this password matches the one on file given the username
	public static boolean checkIfPasswordMatches(String username, String password){
		try{
			databaseOut.writeObject(new CheckPassword(databaseID.id, username, password, false));	//id, username, passsword, password matches (arbitrary at this point)
			databaseOut.flush();
			//Wait to hear back
			Object response = databaseIn.readObject();
			CheckPassword cp = (CheckPassword)response;
			return cp.matches;
		}
		catch (IOException ioe){
			System.out.println(ioe.getMessage());
			return false;
		}
		catch(ClassNotFoundException cnfe){
			System.out.println(cnfe.getMessage());
			return false;
		}
	}
	//Called by CreateAccountPanel
	//Tell the database to add this new user
	public static void addUserToDatabase(String username, String password, String imagePath){
		try{
			//Start a new user out with 5000 chips
			databaseOut.writeObject(new NewUser(username, password, imagePath, 5000, false)); //boolean pertatins to guest
			databaseOut.flush();
		}
		catch (IOException ioe){
			System.out.println(ioe.getMessage());
		}
	}
	
	//Called by loginMenu
	//The username and password are valid, so get the associated player from the database and login
	public static void validCredentials(String username){
		player = getPlayer(username);
		loggedIn = true;
	}
	//Called by guestAction. Manually set the player
	public static void setGuestPlayer(Player playa){
		player = playa;
		loggedIn = true;
	}
	//Get the associated player from the database
	private static Player getPlayer(String username){
		try{
			databaseOut.writeObject(new PlayerRequest(databaseID.id,username));
			databaseOut.flush();
			//Wait to hear back
			Object response = databaseIn.readObject();
			return (Player)response;
		}
		catch (IOException ioe){
			System.out.println(ioe.getMessage());
			return null;
		}
		catch(ClassNotFoundException cnfe){
			System.out.println(cnfe.getMessage());
			return null;
		}
	}
	
	private static void setUpGameServer(int portNumber){
		GameServer gs = new GameServer(portNumber, gameServerNumPlayers);
		new Thread(gs).start();
		//If this client created the server, we know their IPaddress as localhost
		joinGameServer("localhost",portNumber);
	}
	
	public static void joinGameServer(String ipAddress, int portNumber){
		try{
			//Connect to the GameServer
			Socket sgs = new Socket(ipAddress,portNumber);
			//Setup in/out stream for gameplay
			gameOut = new ObjectOutputStream(sgs.getOutputStream());
			gameOut.flush();
			gameIn = new ObjectInputStream(sgs.getInputStream());
			//Wait for the gameserver to assign an ID
			Object o = gameIn.readObject();
			playerID = (PlayerID)o;
			System.out.println("Player " + playerID.id);
			gameOut.writeObject(player);
			gameOut.flush();
		}
		catch(IOException ioe){
			System.out.println("Exception in PokerClient:JoinGameServer");
		}
		catch(ClassNotFoundException cnfe){
			System.out.println("Exception in PokerClient:JoinGameServer cnfe");
		}
	}
	//Called after the user has specified whether they want to host or join
	//and entered a valid ipadress and/or portnumber
	//Whether the user chose to host a game or join
	public static void selectedOnline(String ipAddress, int portNumber, int numPlayers){
		gameServerIPAddress = ipAddress;
		gameServerPortNumber = portNumber;
		hasSelectedHostOrJoin = true;
		gameServerNumPlayers = numPlayers;
	}
	
	//GAMEPLAY METHODS AVAILABLE TO PLAYER
	public static void fold(){
		try{
			player.fold();
			endTurn();
			gameOut.writeObject(new FoldObject(playerID.id, player.getName()));
			gameOut.flush();
			
		}
		catch(IOException ioe){
			System.out.println(ioe.getMessage());
		}
	}
	
	public static void check(){
		betToMatch = maxPotShare - myPotShare;
		if(betToMatch != 0)
			return;
		try{
			endTurn();
			gameOut.writeObject(new CheckObject(player.getName()));
			gameOut.flush();
			
		}
		catch(IOException ioe){
			System.out.println(ioe.getMessage());
		}
	}
	
	public static void raise(int raiseBy){
		betToMatch = maxPotShare-myPotShare;
		if(raiseBy + betToMatch > player.getMoney()){
			//System.out.println("PokerCLient: Player doesnt have enough money");
			JOptionPane.showMessageDialog(null, "You do not have enough cash.", "Insufficient Cash", JOptionPane.WARNING_MESSAGE);
			return;
		}
		try{
			//System.out.println("PokerClient: Calling " + betToMatch + " Raising by " + raiseBy);
			player.raise(raiseBy+betToMatch);
			endTurn();
			myPotShare +=betToMatch;
			myPotShare += raiseBy;
			RaiseObject ro = new RaiseObject(playerID.id, raiseBy,betToMatch, player.getName());
			ro.myShare = myPotShare;
			gameOut.writeObject(ro);
			gameOut.flush();
			betToMatch = 0;
			
		}
		catch(IOException ioe){
			System.out.println(ioe.getMessage());
		}
		
	}
	
	public static void call(){
		betToMatch = maxPotShare - myPotShare;
		if(betToMatch == 0)
			return;
		try{
			int called = player.call(betToMatch);
			endTurn();
			myPotShare += betToMatch;
			CallObject co = new CallObject(playerID.id,called, player.getName());
			co.myShare = myPotShare;
			gameOut.writeObject(co);
			gameOut.flush();
			
		}
		catch(IOException ioe){
			System.out.println(ioe.getMessage());
		}
	}
	
	//Disable clients button
	private static void endTurn(){
		pcw.getClientPanel().getGameTable().setButtons(false);
	//	System.out.println("Poker Client EndTurn: Bet To Match: " + betToMatch);

	}
	
	public static void playersUpdate(int playersjoined) {
		pcw.playersUpdate(playersjoined);
		
	}
	
	public static void sendChat(String m){
		try{
		gameOut.writeObject(new ChatObject(player.getName(),m));
		gameOut.flush();
		}
		catch(IOException ioe){
			
		}
	}
	
}
