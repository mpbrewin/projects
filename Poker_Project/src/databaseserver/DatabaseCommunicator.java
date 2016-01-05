package databaseserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import gamemechanics.Player;
import networkingobjects.CheckLoginObject;
import networkingobjects.CheckPassword;
import networkingobjects.CheckUsername;
import networkingobjects.DatabaseID;
import networkingobjects.NewUser;
import networkingobjects.PlayerRequest;

//Handles requests to and from clients wishing to talk to the database
public class DatabaseCommunicator implements Runnable{
	private DatabaseServer server;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private volatile boolean running;
	private int id;	//So we can can identify this communicator
	private String username; //So we can tell the database to logout the user
	
	public DatabaseCommunicator (DatabaseServer ds, Socket s, int i){
		try{
			//A fix so we dont get caught up in construction
			out = new ObjectOutputStream(s.getOutputStream());
			out.flush();
			in = new ObjectInputStream(s.getInputStream());
			//Send the client their id number to facilitate communications on both sides
			out.writeObject(new DatabaseID(i));
			out.flush();
			
			this.server = ds;
			this.id = i;
		}
		catch(IOException ioe){
			System.out.println("Exception in DatabaseCommunuicator");
		}
	}
	
	//Inputs
	@Override
	public void run(){
		this.running = true;
	//	System.out.println("DBComm: started thread");
		try{
			while(running){
				Object o = in.readObject();
				if(o instanceof CheckUsername){
					CheckUsername cu = (CheckUsername)o;
				//	System.out.println("DBComm: Got a request to check username");
					server.checkIfNameExists(cu);
				}
				else if(o instanceof CheckPassword){
					CheckPassword cp = (CheckPassword)o;
					server.checkIfPasswordMatches(cp);
				}
				else if (o instanceof NewUser){
					NewUser nu = (NewUser)o;
					server.addUserToDatabase(nu);
				}
				else if (o instanceof PlayerRequest){
					PlayerRequest pr = (PlayerRequest)o;
					server.getPlayer(pr);
				}
				else if(o instanceof CheckLoginObject) {
					CheckLoginObject clo = (CheckLoginObject)o;
					server.checkIfLoggedIn(clo);
				}
			}
		}
		catch(IOException disconnect){
			System.out.println("DBComm: Client " + id +" disconnected");
			running = false;
			server.handleDisconnect(id,username);
			
			//Need to have the server handle the disconnect
		}
		catch(ClassNotFoundException cnfe){
			System.out.println(cnfe.getMessage());
		}
	}
	
	//Outputs
	public void sendUsernameStatus(CheckUsername cu){
		try{
			out.writeObject(cu);
			out.flush();
		}
		catch(IOException ioe){
			System.out.println(ioe.getMessage());
		}
	}
	
	public void sendPasswordStatus(CheckPassword cp){
		try{
			out.writeObject(cp);
			out.flush();
			if(cp.matches){
				username = cp.username;
			}
		}
		catch(IOException ioe){
			System.out.println(ioe.getMessage());
		}
	}
	
	public void sendLoggedInStatus(CheckLoginObject clo) {
		try {
			out.writeObject(clo);
			out.flush();
		}
		catch(IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}
	
	public void sendPlayer(Player p){
		try{
			out.writeObject(p);
			out.flush();
		}
		catch(IOException ioe){
			System.out.println(ioe.getMessage());
		}
	}
}
