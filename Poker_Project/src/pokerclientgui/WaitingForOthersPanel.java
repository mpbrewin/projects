package pokerclientgui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import customcomponents.PaintedPanel;
import gamemechanics.GameConstants;
import libraries.ImageLibrary;

public class WaitingForOthersPanel extends PaintedPanel {

	private JLabel label1 = new JLabel("Waiting for:  ");
	private JLabel label2 = new JLabel("  more player(s) to join");
	private PortPanel portpanel;
	private JLabel playersjoined = new JLabel();
	private int playersleft;
	private String numplayers = "3";
	
	public WaitingForOthersPanel() {
		
		super(ImageLibrary.getImage(GameConstants.POKERTABLEIMAGE),null,null);
		
		label1.setFont(new Font("Impact", Font.PLAIN, 45));
		label2.setFont(new Font("Impact", Font.PLAIN, 45));
		
		JPanel container = new JPanel();
		container.setLayout(new FlowLayout());
		container.setBorder(BorderFactory.createEmptyBorder(230,0,0,0));
		container.setOpaque(false);
		container.add(label1);
		container.add(playersjoined);
		container.add(label2);
		add(container);
		
	}
	
	public void setNumPlayers(int numPlayers) {
		
		playersleft = numPlayers;
		numplayers = Integer.toString(playersleft);
		playersjoined.setText(numplayers);
		playersjoined.setFont(new Font("Impact", Font.PLAIN, 90));
	//	System.out.println("Players left " + playersleft);
		playersleft = playersleft - 1;
		
		
	}

	public void playersUpdate(int np) {
		// TODO Auto-generated method stub
		String playertemp = Integer.toString(playersleft+np);
	//	System.out.println("Player temp: " + playertemp);
		playersjoined.setText(playertemp);
		playersjoined.setFont(new Font("Impact", Font.PLAIN, 90));
		this.repaint();
		this.revalidate();
		
	}
}
