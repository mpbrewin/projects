package pokerclientgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import customcomponents.PaintedButton;
import customcomponents.PaintedPanel;
import gamemechanics.GameConstants;
import libraries.ImageLibrary;

public class SelectOpponentsPanel extends PaintedPanel{

	private static final long serialVersionUID = 24352435244L;
	private JLabel oppLabel;
	private String [] numOpp = {"1", "2", "3", "4", "5", "6", "7"};
	private String [] diff = {"Beginner", "Intermediate", "Advanced"};
	
	private PaintedButton confirmButton;
	private JPanel bottomPanel;
	
	private JComboBox numOppBox;
	private JComboBox diffBox;
	
	
	
	public SelectOpponentsPanel(ActionListener confirmAction){
		super(ImageLibrary.getImage(GameConstants.POKERTABLEIMAGE),null,null);
		this.setLayout(new BorderLayout());
		
		oppLabel = new JLabel("Pick Opponents and Difficulty");
		oppLabel.setFont(new Font("Impact", Font.BOLD, 38));
		oppLabel.setForeground(Color.white);

		//Create the combo boxes
		numOppBox = new JComboBox(numOpp);
		diffBox = new JComboBox(diff);
		numOppBox.setFont(new Font("Impact", Font.BOLD, 38));
		diffBox.setFont(new Font("Impact", Font.BOLD, 38));
		
		//set selected indices
		numOppBox.setSelectedIndex(0);
		diffBox.setSelectedIndex(0);
		
		JPanel dropPanel = new JPanel();
		dropPanel.setOpaque(false);
		dropPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 150, 150));
		dropPanel.setBackground(Color.GREEN);
		dropPanel.add(numOppBox);
		dropPanel.add(diffBox);
		
		confirmButton = new PaintedButton("START GAME",ImageLibrary.getImage(GameConstants.REDPOKERCHIP), ImageLibrary.getImage(GameConstants.BLACKPOKERCHIP),80,10);
		confirmButton.addActionListener(confirmAction);
		confirmButton.setForeground(Color.white);
		confirmButton.setOpaque(false);
		
		bottomPanel = new JPanel();
		bottomPanel.setOpaque(false);
		bottomPanel.add(confirmButton);
		
		this.add(oppLabel, BorderLayout.NORTH);
		this.add(dropPanel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);
	}
}
