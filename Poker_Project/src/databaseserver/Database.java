package databaseserver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import gamemechanics.Player;

//Contains all player info
public class Database {
	private Map<String,String> loginInfo;	//username, password
	private Map<String, Boolean> loggedInInfo; //username, if logged in
	private Map<String,Player> playerInfo;	//username, player(and all of their info)
	private static final String filepath = "src/databaseserver/userbasefile";
	private FileReader fr;
	private FileWriter fw;
	private BufferedReader br;
	private PrintWriter pw;
	
	public Database(){
		try{
			loginInfo = new HashMap<String,String>();
			playerInfo = new HashMap<String,Player>();
			loggedInInfo = new HashMap<String, Boolean>();
			fr = new FileReader(filepath);
			br = new BufferedReader(fr);
			fw = new FileWriter(filepath, true);
			pw = new PrintWriter(fw, true);
			fw = new FileWriter(filepath, true);
			String str = null;
			//Populate both databases
			while ((str = br.readLine()) != null){
				String[] info = str.split("\\s+");	//format: username, password, money, image file path
				loginInfo.put(info[0], info[1]);
				int money = Integer.valueOf(info[2]);
				playerInfo.put(info[0], new Player(info[0],money,info[3],false));
				loggedInInfo.put(info[0], false);
				
				//System.out.println("Database: read existing player " + info[0]);
			}
			//System.out.println("Database: done reading players");
		}
		catch(IOException ioe){
			System.out.println("Exception in database");
		}
		/*finally{
			try{
				if(pw != null){
					pw.close();
				}
				if(fw != null){
					fw.close();
				}
				if(br != null){
					br.close();
				}
				if(fr != null){
					fr.close();
				}
			}
			catch(IOException ioe){
				System.out.println("cannot close file");
			}
		}*/
	}
	
	public void addUser(String u, String p, String i, int m){
		loginInfo.put(u, p);
		loggedInInfo.put(u, true);
		playerInfo.put(u, new Player(u, m, i, false));
		pw.println(u + " " + p + " " + m + " " + i);
		pw.flush();
		//System.out.println("Databse: added new user " + u);
	}
	
	public boolean nameExists(String username){
		//System.out.println("Database: checking if name exists");
		String check = loginInfo.get(username);
		return (check != null);
	}
	
	public boolean loggedIn(String username){
		Boolean check = loggedInInfo.get(username);
		return (check);
		
	}
	
	public void logoutPlayer(String username){
		loggedInInfo.put(username, false);
	}
	
	public boolean passwordMatches(String username, String password){
	//	System.out.println("Database: checking if password matches");
		String pwrd = loginInfo.get(username);
		if(pwrd.equals(password)) {
			loggedInInfo.put(username, true);
			return(true);
		}
		
		return false;
	}
	
	public Player getPlayer(String username){
		return playerInfo.get(username);
	}
	
}
