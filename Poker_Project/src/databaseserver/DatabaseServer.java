package databaseserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JOptionPane;

import gamemechanics.GameConstants;
import gamemechanics.Player;
import networkingobjects.CheckLoginObject;
import networkingobjects.CheckPassword;
import networkingobjects.CheckUsername;
import networkingobjects.NewUser;
import networkingobjects.PlayerRequest;

//Handles all info pertaining to the game's database (i.e userbase)
public class DatabaseServer {
	private Vector<DatabaseCommunicator> connections;
	private Database database;
	
	public DatabaseServer(){
		try{
			//Instantiate the userbase
			database = new Database();
			//Start up the network
			//System.out.println("Reached before server socket");

			ServerSocket ss = new ServerSocket(GameConstants.DATABASEPORTNUMBER);
			//System.out.println("Reached past server socket");
			connections = new Vector<DatabaseCommunicator>();
			//Continuously accept users trying to login
			while(true){
				Socket s = ss.accept();
				//Give the user an ID so we can handle the disconnects properly
				int databaseID = connections.size(); 
				DatabaseCommunicator dc = new DatabaseCommunicator(this, s, databaseID);
				new Thread(dc).start();		
				connections.addElement(dc);
			//	System.out.println("DatabaseServer: accepted client " + databaseID);
			}
		}
		catch(IOException ioe){
			System.out.println("Exception in DatabaseServer");
		}
	}
	
	public void checkIfNameExists(CheckUsername cu){
		DatabaseCommunicator dc = connections.get(cu.id);
		if(database.nameExists(cu.username)){
			dc.sendUsernameStatus(new CheckUsername(cu.id,cu.username,true));
		}
		else{
			dc.sendUsernameStatus(new CheckUsername(cu.id,cu.username,false));
		}
	}
	
	public void checkIfLoggedIn(CheckLoginObject clo) {
		DatabaseCommunicator dc = connections.get(clo.id);
		if(database.loggedIn(clo.username)) {
			dc.sendLoggedInStatus(new CheckLoginObject(clo.username, true, clo.id));
		}
		else{
			dc.sendLoggedInStatus(new CheckLoginObject(clo.username, false, clo.id));
		}
	}
	
	public void checkIfPasswordMatches(CheckPassword cp){
		DatabaseCommunicator dc = connections.get(cp.id);
		if(database.passwordMatches(cp.username,cp.password)){
			dc.sendPasswordStatus(new CheckPassword(cp.id,cp.username,cp.password,true));
		}
		else{
			dc.sendPasswordStatus(new CheckPassword(cp.id,cp.username,cp.password,false));
		}
	}
	
	public void addUserToDatabase(NewUser nu){
		database.addUser(nu.username, nu.password, nu.imagePath, nu.money);
	}
	
	//Get player from the database according to their username
	public void getPlayer(PlayerRequest pr){
		Player p = database.getPlayer(pr.username);
		DatabaseCommunicator dc = connections.get(pr.id);
		dc.sendPlayer(p);
	}
	
	//If the user disconnects, set their communicator to null, killing the connection
	public void handleDisconnect(int id, String dcPlayer){
		connections.set(id,null);
		database.logoutPlayer(dcPlayer);
	}
	public static void main(String [] args){
		new DatabaseServer();
	}
}
