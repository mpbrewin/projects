package pokerclientgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import customcomponents.PaintedButton;
import customcomponents.PaintedPanel;
import gamemechanics.GameConstants;
//import gamemechanics.GameConstants;
import gamemechanics.Player;
import libraries.ImageLibrary;
import pokerclient.PokerClient;

//Login Menu that asks for username/password
public class LoginMenu extends PaintedPanel {

	private static final long serialVersionUID = 1;
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JTextField usernameEntry;
	private JTextField passwordEntry;
	private JButton loginButton;
	private JButton createAccountButton;
	private JButton guestButton;
	
	private static final String HTMLBLACK = "<font color=black>";
	private static final String HTMLRED = "<font color=red>";
	private static final String ENDF = "</font>";

	
	public LoginMenu(ActionListener loginAction, ActionListener createAccountAction, ActionListener guestAction) {
		super(ImageLibrary.getImage(GameConstants.POKERTABLEIMAGE),null,null);
		setLayout(new BorderLayout());
		//this.setBackground(GameConstants.POKERTABLEGREEN);	//54,146,47
		//this.setForeground(new Color(54,146,47));

		//overall JPanel to add smaller JPanels to
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		container.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));	
		container.setOpaque(false);
		usernameLabel = new JLabel("Username:");
		usernameLabel.setForeground(Color.white);
		usernameLabel.setFont(new Font("Impact", Font.BOLD, 20));
		passwordLabel = new JLabel("Password:");
		passwordLabel.setForeground(Color.white);
		passwordLabel.setFont(new Font("Impact", Font.BOLD, 20));
		usernameEntry = new JTextField(28);
		usernameEntry.setFont(new Font("Impact", Font.BOLD, 20));
		usernameEntry.requestFocus(false);
		passwordEntry = new JTextField(28);
		passwordEntry.setFont(new Font("Impact", Font.BOLD, 20));
		passwordEntry.requestFocus(false);
		
		//loginButton = new JButton("LOGIN");
		loginButton = new PaintedButton("LOGIN",ImageLibrary.getImage(GameConstants.REDPOKERCHIP), ImageLibrary.getImage(GameConstants.BLACKPOKERCHIP),100,12);
		loginButton.addActionListener(loginAction);
		loginButton.setForeground(Color.white);
		loginButton.setOpaque(false);
		//loginButton.setIcon(new ImageIcon(ImageLibrary.getImage("resources/images/spade.png")));
		
		//createAccountButton = new JButton("CREATE ACCOUNT");
		createAccountButton = new PaintedButton("NEW USER",ImageLibrary.getImage(GameConstants.REDPOKERCHIP), ImageLibrary.getImage(GameConstants.BLACKPOKERCHIP),100,12);
		createAccountButton.addActionListener(createAccountAction);
		createAccountButton.setForeground(Color.white);
		createAccountButton.setOpaque(false);
		
		//guestButton = new JButton("GUEST");
		guestButton = new PaintedButton("GUEST",ImageLibrary.getImage(GameConstants.REDPOKERCHIP), ImageLibrary.getImage(GameConstants.BLACKPOKERCHIP),100,12);
		guestButton.addActionListener(guestAction);
		guestButton.setForeground(Color.white);
		guestButton.setOpaque(false);
		
		//Panel for the title
		Image title = ImageLibrary.getImage(GameConstants.TITLEIMAGE);
		PaintedPanel titlePanel = new PaintedPanel(title,160,0);
		titlePanel.setOpaque(false);
		titlePanel.setPreferredSize(new Dimension(500,300));
		
		/*JLabel titleLabel = new JLabel("<html>"+HTMLRED+"T"+ENDF+HTMLBLACK+"e"+ENDF+HTMLRED+"x"+ENDF+HTMLBLACK+"a"+ENDF+HTMLRED
				+"s "+ENDF+HTMLBLACK+"H"+ENDF+HTMLRED+"o"+ENDF+HTMLBLACK+"l"+ENDF+HTMLRED+"d"+ENDF+HTMLBLACK+"'"+ENDF+HTMLRED+"E"+ENDF+HTMLBLACK
				+"m"+ENDF+"</html>");*/
		//titleLabel.setFont(new Font("Impact",Font.BOLD,40)); //Century, Impact
		
		JPanel firstPanel = new JPanel();
		firstPanel.add(usernameLabel);
		firstPanel.add(usernameEntry);
		firstPanel.setOpaque(false);
		firstPanel.setBorder(BorderFactory.createEmptyBorder(30,0,0,0));
		JPanel secondPanel = new JPanel();
		secondPanel.add(passwordLabel);
		secondPanel.add(passwordEntry);
		secondPanel.setOpaque(false);
		JPanel thirdPanel = new JPanel();
		thirdPanel.add(loginButton);
		thirdPanel.setOpaque(false);
		container.add(titlePanel);
		container.add(firstPanel);
		container.add(secondPanel);
		container.add(thirdPanel);
		add(container);
		
		//empty JPanel for button spacing
		JPanel emptyPanel = new JPanel();
		emptyPanel.setLayout(new BorderLayout());
		emptyPanel.add(createAccountButton, BorderLayout.WEST);
		emptyPanel.add(guestButton, BorderLayout.EAST);
		emptyPanel.setBorder(BorderFactory.createEmptyBorder(0,30,30,30));
		emptyPanel.setOpaque(false);
		add(emptyPanel, BorderLayout.SOUTH);
		
		//make the buttons bigger
		createAccountButton.setMargin(new Insets(20,0,20,0));	//20
		guestButton.setMargin(new Insets(20,0,20,0));	//20
		//guestButton.setPreferredSize(new Dimension(createAccountButton.getWidth(), createAccountButton.getHeight()));
		loginButton.setMargin(new Insets(10,0,10,0));	//25
		
		//creating borders for create account and guest button
		//createAccountButton.setBorder(BorderFactory.createEmptyBorder(100,20,50,20));
		//createAccountButton.setOpaque(false);
		//guestButton.setBorder(BorderFactory.createEmptyBorder(100, 10, 50, 60));				
	}
	
	
	//Here we check their username against the database.
	public boolean validUsername(){
		return PokerClient.checkIfNameExists(usernameEntry.getText());
	}
	//Check if the password matches the one on recrd given the username
	//Must return true for the user to proceed
	public boolean validPassword(){
		return PokerClient.checkIfPasswordMatches(usernameEntry.getText(), passwordEntry.getText());
	}
	
	public String getUsername(){
		return usernameEntry.getText();
	}
	
	public String getPassword(){
		return passwordEntry.getText();
	}
	
	public boolean checkIfLoggedIn(){
		return PokerClient.checkIfLoggedIn(usernameEntry.getText());
	}

}


