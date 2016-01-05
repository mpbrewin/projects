package pokerclientgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import customcomponents.PaintedButton;
import customcomponents.PaintedPanel;
import gamemechanics.GameConstants;
import libraries.ImageLibrary;
import pokerclient.PokerClient;

//TODO: have to check for valid portnumber
public class PortPanel extends PaintedPanel {
	private JLabel portNumberLabel;
	private JTextField portNumberTextField;
	private PaintedButton connect;
	private String [] numOpp = {"3", "4", "5", "6", "7"};
	private JComboBox numOppBox;
	private int numPlayersJoin = 3;

	
	@SuppressWarnings("unchecked")
	public PortPanel(ActionListener connectAction){
		super(ImageLibrary.getImage(GameConstants.POKERTABLEIMAGE),null,null);
		setLayout(new BorderLayout());
		
		portNumberLabel = new JLabel("PORT:");
		portNumberLabel.setFont(new Font("Impact", Font.PLAIN, 30));
		portNumberLabel.setOpaque(false);
		portNumberTextField = new JTextField("1738");
		portNumberTextField.setFont(new Font("Impact", Font.PLAIN, 30));
		connect = new PaintedButton("START",ImageLibrary.getImage(GameConstants.REDPOKERCHIP), ImageLibrary.getImage(GameConstants.BLACKPOKERCHIP),100,12);
		connect.addActionListener(connectAction);
		connect.setOpaque(false);
		connect.setForeground(Color.white);
		
		JPanel contents = new JPanel();
		contents.setLayout(new BoxLayout(contents, BoxLayout.Y_AXIS));
		contents.setOpaque(false);
		
		JPanel portPanel = new JPanel();
		portPanel.setBorder(BorderFactory.createEmptyBorder(100,0,0,0));
		portPanel.add(portNumberLabel);
		portPanel.add(portNumberTextField);
		portPanel.setOpaque(false);
		
		JPanel playersPanel = new JPanel();
		JLabel playersLabel = new JLabel("Select Total Number of Players Allowed:");
		playersLabel.setFont(new Font("Impact", Font.PLAIN, 35));
		playersPanel.setOpaque(false);
		numOppBox = new JComboBox(numOpp);
		numOppBox.setPreferredSize(new Dimension(45,45));
		numOppBox.setFont(new Font("Impact", Font.PLAIN,20));
		numOppBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				numPlayersJoin = numOppBox.getSelectedIndex() + 3;
			//	System.out.println("Entered clicked: " + numPlayersJoin);
			
			}
		});
		JPanel dropPanel = new JPanel();
		dropPanel.setOpaque(false);
		dropPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 100));
		dropPanel.setBackground(Color.GREEN);
		dropPanel.add(playersLabel);
		dropPanel.add(numOppBox);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(connect);
		buttonPanel.setOpaque(false);
		
		contents.add(portPanel);
		contents.add(dropPanel);
		contents.add(buttonPanel);
		
		add(contents,BorderLayout.CENTER);
	}
	
	public int getPortNumber(){
		String portnumberString = portNumberTextField.getText();
		int portnumber = Integer.parseInt(portnumberString);
	//	System.out.println(portnumber);
		return portnumber;
	}
	
	public int getNumPlayersJoin() {
		return numPlayersJoin;	
	}
}
