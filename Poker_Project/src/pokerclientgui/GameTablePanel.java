//package pokerclientgui;
//
//import javax.swing.JPanel;
//
//public class GameTablePanel extends JPanel{
//	
//}



package pokerclientgui;


import gamemechanics.Card;
import gamemechanics.GameConstants;
import gamemechanics.Player;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

import customcomponents.PaintedButton;
import customcomponents.PaintedPanel;
import gamemechanics.GameConstants;
import gamemechanics.Player;
import libraries.ImageLibrary;
import pokerclient.PokerClient;

public class GameTablePanel extends PaintedPanel{

/**
	 * 
	 */
	private static final long serialVersionUID = 1314930066091939523L;
	
	private JPanel gamePanel;
		private Card [] cardsArray;
		private JPanel cardsPanel;
		public FlopPanel flopPanel;
		private Vector<PaintedPanel> cardsVector = new Vector<PaintedPanel>();
	
	private ImageIcon profile;
	
	//private Vector<PlayerPanel> ppVector=new Vector<PlayerPanel>();
	private Vector<Player> players;

	
	private JPanel bottomPanel;
		private JPanel chatPanel;
			private JTextArea chatArea;
			private JScrollPane chatPane;
			private JPanel bottomChatPanel;
			private JTextField chatField;
			private JButton sendButton;
		
		private JPanel actionPanel;
			private JPanel leftActionPanel;
				private PaintedButton check;
				private PaintedButton call;
				private PaintedButton fold;
			private JPanel rightActionPanel;	
				private JPanel raisePanel;
					private JLabel raiseLabel;
					private JTextField raiseField;
				private PaintedButton raiseButton;
				private PaintedButton allIn;
			
		private JPanel historyPanel;
			private JLabel historyLabel;
			private JTextArea historyArea;
			private JScrollPane historyPane;
			
		private static final int buttonSize = 60;
	
	
	public GameTablePanel(){
		super(ImageLibrary.getImage(GameConstants.POKERTABLEIMAGE),null,null);
		this.setLayout(new BorderLayout());
		
		gamePanel = new JPanel();
		gamePanel.setOpaque(false);
		gamePanel.setLayout(new GridLayout(3,3));
		
		bottomPanel = new JPanel();
		bottomPanel.setOpaque(false);
		
			chatPanel = new JPanel();
			chatPanel.setPreferredSize(new Dimension(213, 183));
//			chatPanel.setOpaque(false);
			chatPanel.setBorder(BorderFactory.createLineBorder(Color.black));	
			chatPanel.setLayout(new BorderLayout());
				chatArea = new JTextArea(180,140);
				chatArea.setEditable(false);
				chatArea.setBackground(Color.BLACK);
				chatArea.setForeground(Color.WHITE);
				chatArea.setFont(new Font("Impact", Font.PLAIN, 15));
				DefaultCaret caret1 = (DefaultCaret)chatArea.getCaret();
				caret1.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
				chatPane = new JScrollPane(chatArea);
			//	chatPane.setBackground(Color.BLACK);
				//chatPane.setForeground(Color.WHITE);
				chatPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

				//chatPane.add(chatArea);
				
				bottomChatPanel = new JPanel();
				bottomChatPanel.setLayout(new BorderLayout());
				bottomChatPanel.setPreferredSize(new Dimension (213, 25));
				chatField = new JTextField("");
				chatField.setPreferredSize(new Dimension(345, 25));
				chatField.setBackground(Color.black);
				chatField.setForeground(Color.white);
				chatField.setFont(new Font("Impact", Font.PLAIN, 15));
				sendButton = new JButton("SEND");
				sendButton.setPreferredSize(new Dimension(75, 25));
				sendButton.setFont(new Font("Impact", Font.PLAIN, 15));
				sendButton.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent ae){
					//	System.out.println("Clicked button");
						String m = chatField.getText();
						chatField.setText("");
					//	System.out.println(m);
						//String n = PokerClient.ppVector.get(PokerClient.playerID.id).getName();
						//System.out.println(n);
						PokerClient.sendChat(m);
					}
				});
				
				bottomChatPanel.add(chatField, BorderLayout.WEST);
				bottomChatPanel.add(sendButton, BorderLayout.EAST);
				
				chatPanel.add(chatPane, BorderLayout.CENTER);
				chatPanel.add(bottomChatPanel, BorderLayout.SOUTH);
				
			actionPanel = new JPanel();
			actionPanel.setPreferredSize(new Dimension(213, 183));
			actionPanel.setOpaque(false);
			actionPanel.setBorder(BorderFactory.createLineBorder(Color.yellow));
			actionPanel.setLayout(new GridLayout(1,2));	
				leftActionPanel = new JPanel();
				leftActionPanel.setLayout(new BoxLayout(leftActionPanel, BoxLayout.Y_AXIS));
					check = new PaintedButton("CHECK",ImageLibrary.getImage(GameConstants.REDPOKERCHIP), ImageLibrary.getImage(GameConstants.BLACKPOKERCHIP),buttonSize,10);
					check.setForeground(Color.white);
					check.setOpaque(false);
					check.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent ae){
							PokerClient.check();
						}
					});
					
					call = new PaintedButton("CALL",ImageLibrary.getImage(GameConstants.REDPOKERCHIP), ImageLibrary.getImage(GameConstants.BLACKPOKERCHIP),buttonSize,10);
					call.setForeground(Color.white);
					call.setOpaque(false);
					call.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent ae){
							PokerClient.call();
						}
					});
					
					fold = new PaintedButton("FOLD",ImageLibrary.getImage(GameConstants.REDPOKERCHIP), ImageLibrary.getImage(GameConstants.BLACKPOKERCHIP),buttonSize,10);
					fold.setForeground(Color.white);
					fold.setOpaque(false);
					fold.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent ae){
							PokerClient.fold();
						}
					});
					
					leftActionPanel.add(check);
					leftActionPanel.add(Box.createVerticalGlue());
					leftActionPanel.add(call);
					leftActionPanel.add(Box.createVerticalGlue());
					leftActionPanel.add(fold);
				
				rightActionPanel = new JPanel();
				rightActionPanel.setLayout(new BoxLayout(rightActionPanel, BoxLayout.Y_AXIS));
					raisePanel = new JPanel();
						raiseLabel = new JLabel("Raise By:");
						raiseField = new JTextField("100",5);
						raisePanel.add(raiseLabel);
						raisePanel.add(raiseField);

					raiseButton = new PaintedButton("RAISE",ImageLibrary.getImage(GameConstants.REDPOKERCHIP), ImageLibrary.getImage(GameConstants.BLACKPOKERCHIP),buttonSize,10);
					raiseButton.setForeground(Color.white);
					raiseButton.setOpaque(false);
					raiseButton.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent ae){
							String raiseAmount = raiseField.getText();
							if(raiseAmount == null){
								return;
							}
							int amount = Integer.valueOf(raiseAmount);
							PokerClient.raise(amount);
						}
					});

					allIn = new PaintedButton("ALL IN",ImageLibrary.getImage(GameConstants.REDPOKERCHIP), ImageLibrary.getImage(GameConstants.BLACKPOKERCHIP),buttonSize,10);
					allIn.setForeground(Color.white);
					allIn.setOpaque(false);
					
					rightActionPanel.add(raisePanel);
//					rightActionPanel.add(Box.createVerticalGlue());
					rightActionPanel.add(raiseButton);
					rightActionPanel.add(Box.createVerticalGlue());
					rightActionPanel.add(allIn);
					
			actionPanel.add(leftActionPanel);
			actionPanel.add(rightActionPanel);
			
			

			historyPanel = new JPanel();
			historyPanel.setPreferredSize(new Dimension(213, 183));
			historyPanel.setOpaque(false);
			historyPanel.setBorder(BorderFactory.createLineBorder(Color.black));
			historyPanel.setLayout(new BorderLayout());
				historyLabel = new JLabel("GAME HISTORY: ");
				historyLabel.setFont(new Font("Impact", Font.PLAIN, 15));
				historyLabel.setOpaque(true);
				historyArea = new JTextArea();
				historyArea.setBackground(Color.BLACK);
				historyArea.setForeground(Color.WHITE);
				historyArea.setFont(new Font("Impact", Font.PLAIN, 15));
				DefaultCaret caret = (DefaultCaret)historyArea.getCaret();
				caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
				historyPane = new JScrollPane(historyArea);
				historyPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				
				historyPanel.add(historyLabel, BorderLayout.NORTH);
				historyPanel.add(historyPane, BorderLayout.CENTER);
			
		bottomPanel.setLayout(new GridLayout(1,3));
		bottomPanel.add(chatPanel);
		bottomPanel.add(actionPanel);
		bottomPanel.add(historyPanel);
		
		this.add(gamePanel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);
			
	}
	
	
	
	public void setPlayers(Vector<Player> p){
		players = p;
		
		for(int i=0; i<9; i++){
			if(i == 4){
				flopPanel = new FlopPanel();
				gamePanel.add(flopPanel);
			}
			else{
				PlayerPanel pp;
				if(players!=null && i<players.size()){
					pp = new PlayerPanel(players.get(i));
					
				}
				else{
					pp = new PlayerPanel();
				}
				PokerClient.ppVector.add(pp);
				gamePanel.add(pp);
			}
		}
		setButtons(false);
		revalidate();
		repaint();
	}
	public void setButtons(Boolean b){
		check.setEnabled(b);
		call.setEnabled(b);
		fold.setEnabled(b);
		raiseField.setEnabled(b);
		raiseButton.setEnabled(b);
		allIn.setEnabled(b);
		revalidate();
	}
	public void fold(int id){
		PokerClient.ppVector.get(id).removeCards();
		revalidate();
	}
	public void updateMoney(int id){
		PokerClient.ppVector.get(id).updateCash();
		revalidate();
	}

	public void setFlop(Card [] cards) {
		flopPanel.setFlop(cards);
	}
	
	public void setMessage(String Name, String Message){
		chatArea.append(Name+": "+Message+"\n");
		
		revalidate();
	}
	
	public void setHistoryMessage(String Name, String Message, String Amount) {
		historyArea.append(Name + ": " + Message + Amount + "\n");
		
		revalidate();
	}
		
//	@Override
//	  protected void paintComponent(Graphics g) {
//
//	    super.paintComponent(g);
//	        g.drawImage(, 0, 0, null);
//	}
}

