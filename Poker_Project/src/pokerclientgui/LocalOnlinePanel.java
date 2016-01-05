package pokerclientgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import customcomponents.PaintedButton;
import customcomponents.PaintedPanel;
import gamemechanics.GameConstants;
import libraries.ImageLibrary;

public class LocalOnlinePanel extends PaintedPanel{
	private static final long serialVersionUID = 1233;
	private boolean selectedLocal;
	
	JRadioButton localButton = new JRadioButton("Local");
	JRadioButton onlineButton = new JRadioButton("Online");
	JButton confirmButton = new PaintedButton("Confirm",ImageLibrary.getImage(GameConstants.REDPOKERCHIP), ImageLibrary.getImage(GameConstants.BLACKPOKERCHIP),100,14);
	
	
	public LocalOnlinePanel (ActionListener localonlineAction) {
		super(ImageLibrary.getImage(GameConstants.POKERTABLEIMAGE),null,null);
		setLayout(new BorderLayout());
		
		//Panel for the title
		Image title = ImageLibrary.getImage(GameConstants.TITLEIMAGE);
		PaintedPanel titlePanel = new PaintedPanel(title,160,0);
		titlePanel.setOpaque(false);
		titlePanel.setBorder(BorderFactory.createEmptyBorder(125,0,0,0));
		titlePanel.setPreferredSize(new Dimension(500,300));
		add(titlePanel, BorderLayout.NORTH);
		
		
		localButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				selectedLocal = true;
				confirmButton.setEnabled(true);
			}
		});
		
		localButton.setFont(new Font("Impact", Font.BOLD, 48));
		localButton.setForeground(Color.white);
		localButton.setOpaque(false);

		
		onlineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				selectedLocal = false;
				confirmButton.setEnabled(true);
			}
		});
		
		onlineButton.setFont(new Font("Impact", Font.BOLD, 48));
		onlineButton.setOpaque(false);
		onlineButton.setForeground(Color.white);
		
		//jradio buttons
		ButtonGroup bg = new ButtonGroup();
		bg.add(localButton);
		bg.add(onlineButton);
		
		//putting buttons into a flow layout to center
		JPanel flowbutton = new JPanel();
		flowbutton.setOpaque(false);
		flowbutton.setLayout(new FlowLayout(FlowLayout.CENTER, 150, 0));
		flowbutton.add(localButton);
		flowbutton.add(onlineButton);
		flowbutton.setBorder(BorderFactory.createEmptyBorder(100,0,0,0));
		add(flowbutton, BorderLayout.CENTER);
		
		//passing action listener to confirm button
		confirmButton.addActionListener(localonlineAction);
		confirmButton.setForeground(Color.white);
		confirmButton.setOpaque(false);
		confirmButton.setEnabled(false);
		
		//adding confirm button to south-east part of main jpanel
		JPanel emptyPanel = new JPanel();
		emptyPanel.setOpaque(false);
		emptyPanel.setLayout(new BorderLayout());
		emptyPanel.setBorder(BorderFactory.createEmptyBorder(0,0,50,50));
		emptyPanel.add(confirmButton, BorderLayout.EAST);
		add(emptyPanel, BorderLayout.SOUTH);
	}


	public boolean selectedLocal() {
		return selectedLocal;
	}

}
