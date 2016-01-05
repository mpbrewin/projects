package pokerclientgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import gamemechanics.Player;
import pokerclient.PokerClient;



public class ClientPanel extends JPanel {
	
	private static final long serialVersionUID = 1;
	/**** GUI ****/
	//instantiate menu bar
	private JMenuBar menubar;
	//instantiate scroll pane for menu bar
	private JScrollPane jsp;
	//Elements of the client panel
	//The panel will switch between these
	private LoginMenu loginMenu;
	private CreateAccountPanel createAccountPanel;
	private LocalOnlinePanel localOnlinePanel;
	private SelectOpponentsPanel selectOpponentsPanel;
	private HostJoinPanel hostJoinPanel;
	private PortPanel portPanel; //For host
	private IPPortPanel ipPortPanel; //For joining client
	private WaitingForHostPanel waitingForHostPanel;
	private WaitingForOthersPanel waitingForOthersPanel;
	private GameTablePanel gameTablePanel;
	//Data gathered through GUI
	//Reference to the player the user logged in as. If logged in as a previous user, this will be gotten from the playerbase
	//else, a new player will be created (and added to the database, unless they are a guest)
	private Player player;	
	private int numPlayers;

	//these enclosed brackets are to show creation/adding of login menu
	{
		/****************** MENUBAR STUFF *******************/
		menubar = new JMenuBar();
		jsp = new JScrollPane();
		
		//JDialog for help menu
		JDialog helpFrame = new JDialog();
		helpFrame.setSize(250, 300);
		helpFrame.setLocation(100,100);
		helpFrame.setModal(true);
	
		//sample string for whatever we want to write into help menu
		String helpString = "Texas Hold'Em!\n\nStarting The Game:\n\n\tCreate Account/Login'\n\n\t"
				+ "Local/Online\n";
		
		JMenuItem helpMenu = new JMenuItem("Help");
		
		//create text area to put string into
		JTextArea helpTime = new JTextArea(helpString);
		helpTime.setEnabled(false);
		helpTime.setOpaque(false);
		helpTime.setDisabledTextColor(Color.BLACK);
		helpMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.SHIFT_MASK));
		
		//instantiate helpPanel
		JPanel helpPanel= new JPanel();
		helpPanel.setLayout(new BorderLayout());
		
		helpFrame.setMinimumSize(new Dimension(200,100));
		helpFrame.setMaximumSize(new Dimension(200,100));
		
		//creating scroll pane for help menu
		jsp = new JScrollPane(helpTime, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jsp.setOpaque(false);
		jsp.getViewport().setOpaque(false);
		helpPanel.add(jsp);
		
		//action listener when help is clicked
		helpMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				helpFrame.setVisible(true);
				
			}
		});
		
		menubar.add(helpMenu);
		helpFrame.add(helpPanel);
		
		/*********************************************************/
		
		//Paramaters: loginAction, createAccountAction, guestAction
		loginMenu = new LoginMenu(
			//loginActiond
			new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae1) {
				//We first need to check if the username
				//and password are valid before we proceed
				if(loginMenu.getUsername().isEmpty()) {
					JOptionPane.showMessageDialog(null, "No username entered. Please enter a valid username.", "Login", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				if(loginMenu.getPassword().isEmpty()) {
					JOptionPane.showMessageDialog(null, "No password entered. Please enter a valid password.", "Login", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if(!loginMenu.validUsername()){
					//popup
					JOptionPane.showMessageDialog(null, "Account does not exist. Please enter a valid username.", "Login", JOptionPane.WARNING_MESSAGE);
				//	System.out.println("Invalid username");
					return;
				}
				
				if(loginMenu.checkIfLoggedIn()) {
					JOptionPane.showMessageDialog(null, "User logged in already", "Error", JOptionPane.WARNING_MESSAGE);
				//	System.out.println("Already logged in");
					return;
				}
				if(!loginMenu.validPassword()){
					//popup
					JOptionPane.showMessageDialog(null, "Entered an invalid password. Please enter a valid password.", "Login", JOptionPane.WARNING_MESSAGE);
				//	System.out.println("Invalid password");
					return;
				}
				
				//If both are valid swap to the local/online menu
				ClientPanel.this.removeAll();
				ClientPanel.this.add(localOnlinePanel);
				ClientPanel.this.add(menubar, BorderLayout.NORTH);
				ClientPanel.this.revalidate();
				ClientPanel.this.repaint();
				PokerClient.validCredentials(loginMenu.getUsername());
			}},
			//createAccountAction
			new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae2){
				ClientPanel.this.removeAll();
				ClientPanel.this.add(menubar, BorderLayout.NORTH);
				ClientPanel.this.add(createAccountPanel);
				ClientPanel.this.revalidate();
				ClientPanel.this.repaint();
			}},
			//guestAction
			//Note: this needs its own action listener, as we don't 
			//have to check for a valid username/password as we do in
			//the login ActionListener
			new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae3){
				Random r = new Random();
				int rn = r.nextInt(9999);
				ClientPanel.this.removeAll();
				ClientPanel.this.add(menubar, BorderLayout.NORTH);
				ClientPanel.this.add(localOnlinePanel);
				PokerClient.setGuestPlayer(new Player("Guest"+rn,5000,null,true));
				ClientPanel.this.revalidate();
				ClientPanel.this.repaint();
			}});

		//Set up the panel to display
		setLayout(new BorderLayout());
		add(loginMenu);
		add(menubar, BorderLayout.NORTH);
		refreshComponents();	
	}
	
	
	private void refreshComponents() {
		//Paramaters: confirmAction
		localOnlinePanel = new LocalOnlinePanel(new ActionListener(){
			//confirmAction
			@Override
			public void actionPerformed(ActionEvent ae){
				ClientPanel.this.removeAll();
				//The next panel to display depends on the option they
				//selected. NOTE: the localOnlinePanel methods aren't implemented 
				if (localOnlinePanel.selectedLocal())
					{
						ClientPanel.this.add(menubar, BorderLayout.NORTH);
						ClientPanel.this.add(selectOpponentsPanel);
					}
				else {
					ClientPanel.this.add(menubar, BorderLayout.NORTH);
					ClientPanel.this.add(hostJoinPanel);
				}
				ClientPanel.this.revalidate();
				ClientPanel.this.repaint();
				
				
			}}
		);
		//Paramaters: cancelAction, confirmAction
		createAccountPanel = new CreateAccountPanel(
			//cancelAction
			new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae1){
				ClientPanel.this.removeAll();
				ClientPanel.this.add(menubar, BorderLayout.NORTH);
				ClientPanel.this.add(loginMenu);
				ClientPanel.this.revalidate();
				ClientPanel.this.repaint();
			}},
			//confirmAction
			new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae2){
				if(!createAccountPanel.validUsername()){
					//create popup window
					if(createAccountPanel.getUsername().length() < 3) {
						JOptionPane.showMessageDialog(null, "Invalid username. Username must be at least 3 characters long.", "Invalid Username", JOptionPane.PLAIN_MESSAGE);
						return;
					}
					JOptionPane.showMessageDialog(null, "Invalid username. Username is already taken. Please enter new username.", "Invalid Username", JOptionPane.PLAIN_MESSAGE);
					return;
				}
				else if(!createAccountPanel.validPassword()){
					//create popup window
					JOptionPane.showMessageDialog(null, "Invalid password. Password must be at least 5 characters long.", "Invalid Password", JOptionPane.PLAIN_MESSAGE);
				//	System.out.println("Client Panel: invalid password");
					return;
				}
				ClientPanel.this.removeAll();
				ClientPanel.this.add(localOnlinePanel);
				ClientPanel.this.revalidate();
				ClientPanel.this.repaint();
				PokerClient.addUserToDatabase(createAccountPanel.getUsername(), createAccountPanel.getPassword(), createAccountPanel.getImagePath());
				PokerClient.validCredentials(createAccountPanel.getUsername());
			}}
		);
		//parameters: confirmAction
		selectOpponentsPanel = new SelectOpponentsPanel(new ActionListener(){
			//confirmAction
			@Override
			public void actionPerformed(ActionEvent ae){
				ClientPanel.this.removeAll();
				ClientPanel.this.add(menubar, BorderLayout.NORTH);
				ClientPanel.this.add(gameTablePanel);
				ClientPanel.this.revalidate();
				ClientPanel.this.repaint();
			}}
		);
		//parameters: hostAction, joinAction
		hostJoinPanel = new HostJoinPanel(
			new ActionListener(){
			//hostAction
			@Override
			public void actionPerformed(ActionEvent ae){
				ClientPanel.this.removeAll();
				ClientPanel.this.add(menubar, BorderLayout.NORTH);
				ClientPanel.this.add(portPanel);
				ClientPanel.this.revalidate();
				ClientPanel.this.repaint();
			}},
			new ActionListener(){
			//joinAction
			@Override
			public void actionPerformed(ActionEvent ae2){
				ClientPanel.this.removeAll();
				ClientPanel.this.add(menubar, BorderLayout.NORTH);
				ClientPanel.this.add(ipPortPanel);
				ClientPanel.this.revalidate();
				ClientPanel.this.repaint();
			}}
		);
		
		//Panel for the host and entering the port number to host form
		portPanel = new PortPanel(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){
				//Need to do error checking and switch GUI
				if(portPanel.getPortNumber() < 1 || portPanel.getPortNumber() > 65535) {
					JOptionPane.showMessageDialog(null, "Invalid port entered. Please enter port number between 1 and 65535", "Invalid Port", JOptionPane.PLAIN_MESSAGE);
					return;
				}
				numPlayers = portPanel.getNumPlayersJoin();
				waitingForOthersPanel.setNumPlayers(numPlayers);
				
				PokerClient.selectedOnline(null, portPanel.getPortNumber(), numPlayers);
				ClientPanel.this.removeAll();
				ClientPanel.this.add(menubar, BorderLayout.NORTH);
				ClientPanel.this.add(waitingForOthersPanel);
				ClientPanel.this.revalidate();
				ClientPanel.this.repaint();
				
				
			}
		});
		
		//Panel for person joining and entering ip address and port number
		ipPortPanel = new IPPortPanel(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae2){
				//Need to do error checking and switch GUI
				if(ipPortPanel.getPortNumber() < 1 || ipPortPanel.getPortNumber() > 65535) {
					JOptionPane.showMessageDialog(null, "Invalid port entered. Please enter port number between 1 and 65535", "Invalid Port", JOptionPane.PLAIN_MESSAGE);
					return;
				}
				PokerClient.selectedOnline(ipPortPanel.getIPAddress(), ipPortPanel.getPortNumber(), 0);
				ClientPanel.this.removeAll();
				ClientPanel.this.add(menubar, BorderLayout.NORTH);
				ClientPanel.this.add(waitingForHostPanel);
				ClientPanel.this.revalidate();
				ClientPanel.this.repaint();
			}
		});
		//These waiting panels will need to busy wait until they get an OK from the
		//server
		waitingForHostPanel = new WaitingForHostPanel();
		
		waitingForOthersPanel = new WaitingForOthersPanel();
		
		gameTablePanel = new GameTablePanel();
		
	}


	public void playersUpdate(int np) {
		// TODO Auto-generated method stub
		waitingForOthersPanel.playersUpdate(np);
		
	}
	
	public void changeToGameTable(Vector<Player> p) {
		ClientPanel.this.removeAll();
		ClientPanel.this.add(menubar, BorderLayout.NORTH);
		gameTablePanel.setPlayers(p);
	//	System.out.println("Starting individual clientpanel with " +p.size()+ "players");
		ClientPanel.this.add(gameTablePanel);
		ClientPanel.this.revalidate();
		ClientPanel.this.repaint();
	}
	
	public GameTablePanel getGameTable(){
		return gameTablePanel;
	}
	
}
