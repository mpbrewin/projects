package gameserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

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
import networkingobjects.PlayerID;
import networkingobjects.RaiseObject;
import networkingobjects.ShowObject;
import networkingobjects.StartObj;
import networkingobjects.WinObj;

public class PlayerCommunicator implements Runnable{
	private GameServer gameServer;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private volatile boolean running;
	private int gameID;	//So we can can identify this communicator
	
	public PlayerCommunicator (GameServer gs, Socket s, int i){
		try{
			//A fix so we dont get caught up in construction
			out = new ObjectOutputStream(s.getOutputStream());
			out.flush();
			in = new ObjectInputStream(s.getInputStream());
			//Send the client their id number to facilitate communications on both sides
			out.writeObject(new PlayerID(i));
			out.flush();
			
			this.gameServer = gs;
			this.gameID = i;
		}
		catch(IOException ioe){
			System.out.println("Exception in DatabaseCommunuicator");
		}
	}
	
	@Override
	public void run(){
		try{
			while(true){
				Object o = in.readObject();
				//The player wants to fold
				if (o instanceof FoldObject){
					FoldObject fo = (FoldObject)o;
					gameServer.foldPlayer(fo);
				}
				//The player wants to check
				else if(o instanceof CheckObject){
					CheckObject cho = (CheckObject)o;
					gameServer.checkPlayer(cho);
				}
				//Raise
				else if(o instanceof RaiseObject){
					//System.out.println("Playercomm: received raise");
					RaiseObject ro = (RaiseObject)o;
					gameServer.raiseBet(ro);
				}
				//Call
				else if(o instanceof CallObject){
					CallObject co = (CallObject)o;
					gameServer.called(co);
				}
				else if(o instanceof Player){
					Player po = (Player)o;
					gameServer.addPlayer(po);
				}
				else if(o instanceof ChatObject){
					ChatObject cho = (ChatObject)o;
					gameServer.sendChat(cho);
				}
				else if(o instanceof MoneyObj){
					MoneyObj mo = (MoneyObj)o;
					gameServer.updateMoney(gameID, mo.getMoney());
				}
				else if(o instanceof HandrankObject){
					HandrankObject ho = (HandrankObject)o;
					ho.setID(gameID);
					gameServer.updateHand(ho);
				}
			}
		}
		catch(IOException ioe){
			System.out.println("PlayerComm " + ioe.getMessage());
		}
		catch(ClassNotFoundException cnfe){
			System.out.println("PlayerComm " + cnfe.getMessage());
		}
	}
	
	public void notifyOfNewDeal(NewDeal nd){
		try{
			out.writeObject(nd);
			out.flush();
		}
		catch(IOException ioe){
			System.out.println("PlayerComm: new deal");
			System.out.println(ioe.getMessage());
		}
	}
	
	public void giveTurn(Boolean b){
		try{
			out.writeObject(b);
			out.flush();
		}
		catch(IOException ioe){
			System.out.println("PlayerComm: giveTurn");
		}
	}
	
	public void notifyOfFold(FoldObject fo){
		try{
			out.writeObject(fo);
			out.flush();
		}
		catch(IOException ioe){
			System.out.println("PlayerComm: notifyOfFold");
		}
	}
	
	public void notifyOfCheck(CheckObject co){
		try{
			out.writeObject(co);
			out.flush();
		}
		catch(IOException ioe){
			System.out.println("PlayerComm: notifyOfCheck");
		}
	}
	
	public void notifyOfRaise(RaiseObject ro){
		try{
			out.writeObject(ro);
			out.flush();
		}
		catch(IOException ioe){
			System.out.println("PlayerComm: notifyOfRaise");
		}
	}
	
	public void notifyOfCall(CallObject co){
		try{
			out.writeObject(co);
			out.flush();
		}
		catch(IOException ioe){
			System.out.println("PlayerComm: notifyOfCall");
		}
	}
	
	public void playerHasJoined(JoinObj jo) {
		try{
			out.writeObject(jo);
			out.flush();
		} catch(IOException ioe){
			System.out.println("PlayerComm: notifyOfJoin");
		}
	}
	
	public void startGame(Vector<Player> players) {
		try{
			out.writeObject(new StartObj(players));
			out.flush();
			
		} catch(IOException ioe){
			System.out.println("PlayerComm: Start Object/Game");
		}
	}
	public int getID(){
		return gameID;
	}
	
	public void sendMessage(ChatObject cho){
		try {
			out.writeObject(cho);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void notifyOfFlip(FlipObject fo){
		try{
			out.writeObject(fo);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showPlayerCards(ShowObject so){
		try{
			out.writeObject(so);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void askMoney(MoneyObj mo){
		try{
			out.writeObject(mo);
			out.flush();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	public void sendWinner(WinObj wo){
		try{
			out.writeObject(wo);
			out.flush();
			
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
}
