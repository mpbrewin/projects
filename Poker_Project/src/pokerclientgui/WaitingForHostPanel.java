package pokerclientgui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import customcomponents.PaintedPanel;
import gamemechanics.GameConstants;
import libraries.ImageLibrary;
import pokerclient.PokerClient;

public class WaitingForHostPanel extends PaintedPanel {
	
	private JLabel label1 = new JLabel("Waiting for Host....");
	PokerClient pc;
	
	public WaitingForHostPanel() {
		super(ImageLibrary.getImage(GameConstants.POKERTABLEIMAGE),null,null);
		this.setLayout(new BorderLayout());
		JPanel emptyPanel = new JPanel();
		emptyPanel.setOpaque(false);
		JPanel container = new JPanel();
		container.setOpaque(false);
	
		container.add(label1);
		container.setBorder(BorderFactory.createEmptyBorder(250,0,0,0));
				
		add(emptyPanel, BorderLayout.EAST);
		add(emptyPanel, BorderLayout.WEST);
		add(container, BorderLayout.CENTER);
		label1.setFont(new Font("Impact", Font.PLAIN, 75));
	}
}
