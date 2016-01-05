package pokerclientgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import customcomponents.PaintedButton;
import customcomponents.PaintedPanel;
import gamemechanics.GameConstants;
import libraries.ImageLibrary;

public class HostJoinPanel extends PaintedPanel{
	private PaintedButton hostButton, joinButton;
	private boolean selectedHost;
	public HostJoinPanel(ActionListener hostAction, ActionListener joinAction){
		super(ImageLibrary.getImage(GameConstants.POKERTABLEIMAGE),null,null);
		setLayout(new BorderLayout());
		
		Image title = ImageLibrary.getImage(GameConstants.TITLEIMAGE);
		PaintedPanel titlePanel = new PaintedPanel(title,160,0);
		titlePanel.setOpaque(false);
		titlePanel.setPreferredSize(new Dimension(500,300));
		add(titlePanel,BorderLayout.NORTH);
		
		JPanel buttonPanel = new JPanel();
		hostButton = new PaintedButton("HOST",ImageLibrary.getImage(GameConstants.REDPOKERCHIP), ImageLibrary.getImage(GameConstants.BLACKPOKERCHIP),100,14);
		hostButton.setForeground(Color.white);
		hostButton.setOpaque(false);
		hostButton.addActionListener(hostAction);
		
		joinButton = new PaintedButton("JOIN",ImageLibrary.getImage(GameConstants.REDPOKERCHIP), ImageLibrary.getImage(GameConstants.BLACKPOKERCHIP),100,14);
		joinButton.setForeground(Color.white);
		joinButton.setOpaque(false);
		joinButton.addActionListener(joinAction);
		
		//putting buttons into a flow layout to center
		JPanel flowbutton = new JPanel();
		flowbutton.setOpaque(false);
		flowbutton.setLayout(new FlowLayout(FlowLayout.CENTER, 150, 0));
		flowbutton.add(hostButton);
		flowbutton.add(joinButton);
		flowbutton.setBorder(BorderFactory.createEmptyBorder(125,0,0,0));
		add(flowbutton, BorderLayout.CENTER);
	}
	
	//Return depending on which radio button is selected
	public boolean selectedHost(){
		//return true for now
		return true;
	}
	//Will also need methods to check for valid IP address and port
}
