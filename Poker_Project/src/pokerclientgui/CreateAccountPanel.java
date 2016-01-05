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
import libraries.ImageLibrary;
import pokerclient.PokerClient;

public class CreateAccountPanel extends PaintedPanel {
		private JLabel usernameLabel;
		private JLabel passwordLabel;
		private JTextField usernameEntry;
		private JTextField passwordEntry;
		private JButton createButton;
		private JButton cancelButton;
		private JButton emptyButton;	//fills up space
		
		private static final String HTMLBLACK = "<font color=black>";
		private static final String HTMLRED = "<font color=red>";
		private static final String ENDF = "</font>";
		
	public CreateAccountPanel(ActionListener cancelAction, ActionListener confirmAction){
		super(ImageLibrary.getImage(GameConstants.POKERTABLEIMAGE),null,null);
		setLayout(new BorderLayout());
		//overall JPanel to add smaller JPanels to
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		container.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));	
		container.setOpaque(false);
		container.setOpaque(false);
		usernameLabel = new JLabel("Username:");
		usernameLabel.setForeground(Color.white);
		usernameLabel.setFont(new Font("Impact", Font.BOLD, 20));
		passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(new Font("Impact", Font.BOLD, 20));
		passwordLabel.setForeground(Color.white);
		usernameEntry = new JTextField(20);
		usernameEntry.setFont(new Font("Impact", Font.BOLD, 20));
		usernameEntry.requestFocus(false);
		passwordEntry = new JTextField(20);
		passwordEntry.setFont(new Font("Impact", Font.BOLD, 20));
		passwordEntry.requestFocus(false);
		
		createButton = new PaintedButton("CREATE",ImageLibrary.getImage(GameConstants.REDPOKERCHIP), ImageLibrary.getImage(GameConstants.BLACKPOKERCHIP),100,12);
		createButton.addActionListener(confirmAction);
		createButton.setForeground(Color.white);
		createButton.setOpaque(false);
		
		cancelButton = new PaintedButton("CANCEL",ImageLibrary.getImage(GameConstants.REDPOKERCHIP), ImageLibrary.getImage(GameConstants.BLACKPOKERCHIP),100,12);
		cancelButton.addActionListener(cancelAction);
		cancelButton.setForeground(Color.white);
		cancelButton.setOpaque(false);

		
		//Panel for the title
		Image title = ImageLibrary.getImage(GameConstants.TITLEIMAGE);
		PaintedPanel titlePanel = new PaintedPanel(title,100,100);
		titlePanel.setOpaque(false);
		titlePanel.setPreferredSize(new Dimension(100,500));
		
		JPanel firstPanel = new JPanel();
		firstPanel.add(usernameLabel);
		firstPanel.add(usernameEntry);
		firstPanel.setOpaque(false);
		JPanel secondPanel = new JPanel();
		secondPanel.add(passwordLabel);
		secondPanel.add(passwordEntry);
		secondPanel.setOpaque(false);
		JPanel thirdPanel = new JPanel();
		thirdPanel.setOpaque(false);
		container.add(titlePanel);
		container.add(firstPanel);
		container.add(secondPanel);
		container.add(thirdPanel);
		add(container);
		
		//empty JPanel for button spacing
		JPanel emptyPanel = new JPanel();
		emptyPanel.setLayout(new BorderLayout());
		emptyPanel.add(cancelButton, BorderLayout.WEST);
		emptyPanel.add(createButton, BorderLayout.EAST);
		emptyPanel.setBorder(BorderFactory.createEmptyBorder(0,30,30,30));
		emptyPanel.setOpaque(false);
		add(emptyPanel, BorderLayout.SOUTH);
		
		//make the buttons bigger
		createButton.setMargin(new Insets(20,0,20,0));
		cancelButton.setMargin(new Insets(20,0,20,0));
		//guestButton.setPreferredSize(new Dimension(createAccountButton.getWidth(), createAccountButton.getHeight()));
	}
	
	//Username must be greater than 2 letters and must not already exist
	public boolean validUsername(){
		String s = usernameEntry.getText();
		//Make sure the username is greater than 3 characters
		if (s.length() < 3) return false;
		//See if the username is already taken
		return !PokerClient.checkIfNameExists(s);
	}
	
	//Password must be 5 or more characters
	public boolean validPassword(){
		String p = passwordEntry.getText();
		return (p.length() > 4);
	}
	/*public boolean validPassword(){
		String p = passwordEntry.getText();
		if (p.length() < 5) return false;
		String u = usernameEntry.getText();
		//Check to see if the password is correct
		return PokerClient.checkIfPasswordMatches(u,p);
	}*/
	
	public String getUsername(){
		return usernameEntry.getText();
	}
	
	public String getPassword(){
		return passwordEntry.getText();
	}
	
	public String getImagePath(){
		return null;
	}
}
