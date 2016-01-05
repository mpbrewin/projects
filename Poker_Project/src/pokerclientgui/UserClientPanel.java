//package pokerclientgui;
//
//import gamemechanics.GameConstants;
//import gamemechanics.Player;
//
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.GridLayout;
//import java.util.Vector;
//
//import javax.swing.BorderFactory;
//import javax.swing.Box;
//import javax.swing.BoxLayout;
//import javax.swing.ImageIcon;
//import javax.swing.JButton;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTextArea;
//import javax.swing.JTextField;
//
//import pokerclient.PokerClient;
//import libraries.ImageLibrary;
//import customcomponents.PaintedButton;
//import customcomponents.PaintedPanel;
//
//public class GameTablePanel extends PaintedPanel{
//
///**
//	 * 
//	 */
//	private static final long serialVersionUID = 1314930066091939523L;
//	
//	private JPanel gamePanel;
//		private JPanel cardsPanel;
//		private Vector<PaintedPanel> cardsVector = new Vector<PaintedPanel>();
//	
//	private ImageIcon profile;
//	
//	private Vector<PlayerPanel> ppVector=new Vector<PlayerPanel>();
//
//	
//	private JPanel bottomPanel;
//		private JPanel chatPanel;
//			private JTextArea chatArea;
//			private JScrollPane chatPane;
//			private JPanel bottomChatPanel;
//			private JTextField chatField;
//			private JButton sendButton;
//		
//		private JPanel actionPanel;
//			private JPanel leftActionPanel;
//				private PaintedButton check;
//				private PaintedButton call;
//				private PaintedButton fold;
//			private JPanel rightActionPanel;	
//				private JPanel raisePanel;
//					private JLabel raiseLabel;
//					private JTextField raiseField;
//				private PaintedButton raiseButton;
//				private PaintedButton allIn;
//			
//		private JPanel historyPanel;
//			private JLabel historyLabel;
//			private JTextArea historyArea;
//			private JScrollPane historyPane;
//			
//		private static final int buttonSize = 60;
//	
//	public GameTablePanel(){
//		super(ImageLibrary.getImage(GameConstants.POKERTABLEIMAGE),null,null);
//		this.setLayout(new BorderLayout());
//		
//		gamePanel = new JPanel();
//		gamePanel.setOpaque(false);
//		gamePanel.setLayout(new GridLayout(3,3));
//		for(int i=0; i<9; i++){
//			if(i == 4){
//				JLabel cardLabel= new JLabel("CARDS");
//				cardsPanel = new JPanel();
//				cardsPanel.add(cardLabel);
//				cardsPanel.setOpaque(false);
////				for(int j=0; j<5; i++){
////					PaintedPanel card = new PaintedPanel(ImageLibrary.getImage("resources/images/back.jpg"), 0, 0);
////					cardsVector.add(card);
////					cardsVector.get(j).setPreferredSize(new Dimension(50,73));
////					cardsPanel.add(cardsVector.get(j));
////				}
//				gamePanel.add(cardsPanel);
//			}	
//			else{
//				Player player = new Player("PMW James", 1000, "lelbron.jpg", false);
//				PlayerPanel pp = new PlayerPanel(player);
//				ppVector.add(pp);
//				gamePanel.add(pp);
//			}
//		}
//		
//		
//		//find the client's location at the table in the GUI, update it to show cards
////		for(int i=0; i<8; i++){
////			if(PokerClient.getPlayer().getName() == ppVector.get(i).getPlayer().getName()){
////				ppVector.get(i).showCards();
////			}
////		}
//		
//		
//		bottomPanel = new JPanel();
//		bottomPanel.setOpaque(false);
//		
//			chatPanel = new JPanel();
//			chatPanel.setPreferredSize(new Dimension(213, 183));
////			chatPanel.setOpaque(false);
//			chatPanel.setBorder(BorderFactory.createLineBorder(Color.black));	
//			chatPanel.setLayout(new BorderLayout());
//				chatArea = new JTextArea("POKER CHAT");
//				chatArea.setEditable(false);
//				chatArea.setBackground(Color.BLACK);
//				chatArea.setForeground(Color.WHITE);
//				chatPane = new JScrollPane();
//				chatPane.setBackground(Color.BLACK);
//				chatPane.setForeground(Color.WHITE);
//				chatPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//
//				chatPane.add(chatArea);
//				
//				bottomChatPanel = new JPanel();
//				bottomChatPanel.setLayout(new BorderLayout());
//				bottomChatPanel.setPreferredSize(new Dimension (213, 25));
//				chatField = new JTextField("HELLO");
//				chatField.setPreferredSize(new Dimension(138, 25));
//				sendButton = new JButton("SEND");
//				sendButton.setPreferredSize(new Dimension(75, 25));
//				
//				bottomChatPanel.add(chatField, BorderLayout.WEST);
//				bottomChatPanel.add(sendButton, BorderLayout.EAST);
//				
//				chatPanel.add(chatPane, BorderLayout.CENTER);
//				chatPanel.add(bottomChatPanel, BorderLayout.SOUTH);
//				
//			actionPanel = new JPanel();
//			actionPanel.setPreferredSize(new Dimension(213, 183));
//			actionPanel.setOpaque(false);
//			actionPanel.setBorder(BorderFactory.createLineBorder(Color.yellow));
//			actionPanel.setLayout(new GridLayout(1,2));	
//				leftActionPanel = new JPanel();
//				leftActionPanel.setLayout(new BoxLayout(leftActionPanel, BoxLayout.Y_AXIS));
//					check = new PaintedButton("CHECK",ImageLibrary.getImage(GameConstants.REDPOKERCHIP), ImageLibrary.getImage(GameConstants.BLACKPOKERCHIP),buttonSize,10);
//					check.setForeground(Color.white);
//					check.setOpaque(false);
//					
//					call = new PaintedButton("CALL",ImageLibrary.getImage(GameConstants.REDPOKERCHIP), ImageLibrary.getImage(GameConstants.BLACKPOKERCHIP),buttonSize,10);
//					call.setForeground(Color.white);
//					call.setOpaque(false);
//					
//					fold = new PaintedButton("FOLD",ImageLibrary.getImage(GameConstants.REDPOKERCHIP), ImageLibrary.getImage(GameConstants.BLACKPOKERCHIP),buttonSize,10);
//					fold.setForeground(Color.white);
//					fold.setOpaque(false);
//					
//					leftActionPanel.add(check);
//					leftActionPanel.add(Box.createVerticalGlue());
//					leftActionPanel.add(call);
//					leftActionPanel.add(Box.createVerticalGlue());
//					leftActionPanel.add(fold);
//				
//				rightActionPanel = new JPanel();
//				rightActionPanel.setLayout(new BoxLayout(rightActionPanel, BoxLayout.Y_AXIS));
//					raisePanel = new JPanel();
//						raiseLabel = new JLabel("Raise By:");
//						raiseField = new JTextField("23");
//						raisePanel.add(raiseLabel);
//						raisePanel.add(raiseField);
//
//					raiseButton = new PaintedButton("RAISE",ImageLibrary.getImage(GameConstants.REDPOKERCHIP), ImageLibrary.getImage(GameConstants.BLACKPOKERCHIP),buttonSize,10);
//					raiseButton.setForeground(Color.white);
//					raiseButton.setOpaque(false);
//
//					allIn = new PaintedButton("ALL IN",ImageLibrary.getImage(GameConstants.REDPOKERCHIP), ImageLibrary.getImage(GameConstants.BLACKPOKERCHIP),buttonSize,10);
//					allIn.setForeground(Color.white);
//					allIn.setOpaque(false);
//					
//					rightActionPanel.add(raisePanel);
////					rightActionPanel.add(Box.createVerticalGlue());
//					rightActionPanel.add(raiseButton);
//					rightActionPanel.add(Box.createVerticalGlue());
//					rightActionPanel.add(allIn);
//					
//			actionPanel.add(leftActionPanel);
//			actionPanel.add(rightActionPanel);
//			
//			
//
//			historyPanel = new JPanel();
//			historyPanel.setPreferredSize(new Dimension(213, 183));
//			historyPanel.setOpaque(false);
//			historyPanel.setBorder(BorderFactory.createLineBorder(Color.black));
//			historyPanel.setLayout(new BorderLayout());
//				historyLabel = new JLabel("GAME HISTORY: ");
//				historyLabel.setOpaque(true);
//				historyArea = new JTextArea();
//				historyPane = new JScrollPane();
//				historyPane.add(historyArea);
//				historyPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//				
//				historyPanel.add(historyLabel, BorderLayout.NORTH);
//				historyPanel.add(historyPane, BorderLayout.CENTER);
//			
//		bottomPanel.setLayout(new GridLayout(1,3));
//		bottomPanel.add(chatPanel);
//		bottomPanel.add(actionPanel);
//		bottomPanel.add(historyPanel);
//		
//		this.add(gamePanel, BorderLayout.CENTER);
//		this.add(bottomPanel, BorderLayout.SOUTH);
//			
