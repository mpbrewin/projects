package pokerclientgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import customcomponents.PaintedButton;
import customcomponents.PaintedPanel;
import gamemechanics.GameConstants;
import libraries.ImageLibrary;

public class IPPortPanel extends PaintedPanel{
	private JTextField portNumberTextField, ipAddressTextField;
	private PaintedButton connect;
	
	public IPPortPanel(ActionListener connectAction){
		super(ImageLibrary.getImage(GameConstants.POKERTABLEIMAGE),null,null);
		setLayout(new BorderLayout());
		
		JLabel portNumberLabel = new JLabel("PORT:");
		portNumberLabel.setFont(new Font("Impact", Font.PLAIN, 35));
		portNumberTextField = new JTextField("1738");
		portNumberTextField.setFont(new Font("Impact", Font.PLAIN, 35));
		JLabel ipLabel = new JLabel("IP:");
		ipLabel.setFont(new Font("Impact", Font.PLAIN, 35));
		ipLabel.setOpaque(false);
		ipAddressTextField = new JTextField("localhost");
		ipAddressTextField.setFont(new Font("Impact", Font.PLAIN, 35));
		
		connect = new PaintedButton("JOIN",ImageLibrary.getImage(GameConstants.REDPOKERCHIP), ImageLibrary.getImage(GameConstants.BLACKPOKERCHIP),100,12);
		connect.setOpaque(false);
		connect.setForeground(Color.white);
		connect.addActionListener(connectAction);
		
		JPanel contents = new JPanel();
		contents.setLayout(new BoxLayout(contents, BoxLayout.Y_AXIS));
		contents.setOpaque(false);
	
		JPanel portPanel = new JPanel();
		portPanel.setBorder(BorderFactory.createEmptyBorder(50,0,0,0));
		portPanel.add(portNumberLabel);
		portPanel.add(portNumberTextField);
		portPanel.setOpaque(false);
		contents.add(portPanel);
		
		JPanel ipPanel = new JPanel();
		ipPanel.add(ipLabel);
		ipPanel.add(ipAddressTextField);
		ipPanel.setOpaque(false);
		contents.add(ipPanel);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(connect);
		buttonPanel.setOpaque(false);
		contents.add(buttonPanel);
		
		add(contents);
	}
	
	public int getPortNumber(){
		return Integer.valueOf(portNumberTextField.getText());
	}
	
	public String getIPAddress(){
		return ipAddressTextField.getText();
	}
	
}
