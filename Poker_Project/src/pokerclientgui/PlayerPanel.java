package pokerclientgui;

import gamemechanics.Player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import libraries.ImageLibrary;
import customcomponents.PaintedPanel;

public class PlayerPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8250671967865227258L;

	private Player player;
	
	private JPanel leftPanel;
		private JLabel playerImage;
		private JLabel cashLabel;
		private JLabel dealerBlindLabel;
		private PaintedPanel dealerBlindImage;
		
	private JPanel rightPanel;
		private JLabel nameLabel;
		private JPanel cardsPanel;
			private PaintedPanel card1;
			private PaintedPanel card2;
//			private ImageIcon cardBack = new ImageIcon("resources/images/back.jpeg");
//			private JLabel card1;
//			private JLabel card2;
		private JLabel lastAction;
	public PlayerPanel(){
		setBorder(BorderFactory.createLineBorder(Color.black));
		setLayout(new GridLayout(1,2));
		setOpaque(false);
	}
	public PlayerPanel(Player player){
		this.player = player;
		setBorder(BorderFactory.createLineBorder(Color.black));
		setLayout(new GridLayout(1,2));
		setOpaque(false);
		
		leftPanel = new JPanel();
		leftPanel.setOpaque(false);
		leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.Y_AXIS));
			playerImage = new JLabel();
			playerImage.setIcon(player.getProfile());
			
			cashLabel = new JLabel("Cash: 	" +Integer.toString(player.getMoney()));
			cashLabel.setFont(new Font("Impact", Font.BOLD, 24));
			cashLabel.setForeground(Color.white);
			
			dealerBlindImage = new PaintedPanel(null, 0, 0);
			dealerBlindLabel = new JLabel();
			dealerBlindLabel.setFont(new Font("Impact", Font.BOLD, 24));
			dealerBlindLabel.setForeground(Color.white);
			
			leftPanel.add(playerImage);
			leftPanel.add(cashLabel);
			leftPanel.add(dealerBlindLabel);
		
		rightPanel = new JPanel();
		rightPanel.setOpaque(false);
		rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));
			nameLabel = new JLabel(player.getName());
			nameLabel.setFont(new Font("Impact", Font.BOLD, 24));
			nameLabel.setForeground(Color.white);
			
			cardsPanel = new JPanel();
			cardsPanel.setPreferredSize(new Dimension(200, 200));
			cardsPanel.setOpaque(false);
				card1 = new PaintedPanel(ImageLibrary.getImage("resources/images/back.jpg"), 0, 0);
				card1.setBorder(BorderFactory.createLineBorder(Color.black));
				card1.setPreferredSize(new Dimension(50,73));
				card2 = new PaintedPanel(ImageLibrary.getImage("resources/images/back.jpg"), 0, 0);
				card2.setBorder(BorderFactory.createLineBorder(Color.black));
				card2.setPreferredSize(new Dimension(50,73));
				cardsPanel.add(card1);
				cardsPanel.add(card2);
			lastAction = new JLabel();
			
			rightPanel.add(nameLabel);
			rightPanel.add(cardsPanel);
			rightPanel.add(lastAction);
//			private JLabel cardImage;
//			private JLabel lasttAction;

		add(leftPanel);
		add(rightPanel);
	}	
	
	public void updateCash(){
		cashLabel.setText("Cash: " + player.getMoney());
	}
	
	public void setCash(int ca){
		player.addMoney(ca);
		updateCash();
	}
	
	public void updateAction(){
		lastAction.setText("Bet: ");
	}
	
	public void updateDealer(){
		dealerBlindImage.setImage(ImageLibrary.getImage("resources/images/dealer.jpg"));
		dealerBlindImage.setPreferredSize(new Dimension(50,50));
		dealerBlindImage.revalidate();
		dealerBlindImage.repaint();
	}
	public void updateLittleBlind(){
		//deductAmount(50);
		dealerBlindImage.setImage(ImageLibrary.getImage("resources/images/smallblind.png"));
		dealerBlindImage.setPreferredSize(new Dimension(50,50));
		dealerBlindImage.revalidate();
		dealerBlindImage.repaint();
		revalidate();
		repaint();
	}
	public void updateBigBlind(){
		//deductAmount(100);
		dealerBlindImage.setImage(ImageLibrary.getImage("resources/images/bigblind.png"));
		dealerBlindImage.setPreferredSize(new Dimension(50,50));
		dealerBlindImage.revalidate();
		dealerBlindImage.repaint();
		revalidate();
		repaint();
	}
	
	public void removeCards(){
		card1.setVisible(false);
		card1.setBorder(BorderFactory.createLineBorder(Color.black));
		card1.setPreferredSize(new Dimension(50,73));

		card2.setVisible(false);
		card2.setBorder(BorderFactory.createLineBorder(Color.black));
		card2.setPreferredSize(new Dimension(50,73));

		card1.revalidate();
		card2.revalidate();
		card1.repaint();
		card2.repaint();
	}
	
	public void showCards(){
		card1.setVisible(true);
		card1.setImage(ImageLibrary.getImage(player.getCards()[0].imagePath));
		card1.setBorder(BorderFactory.createLineBorder(Color.black));
		card1.setPreferredSize(new Dimension(50,73));
	//	System.out.println("Card 1: " +player.getCards()[0].suit + player.getCards()[0].rank);
		
		card2.setVisible(true);
		card2.setImage(ImageLibrary.getImage(player.getCards()[1].imagePath));
		card2.setBorder(BorderFactory.createLineBorder(Color.black));
		card2.setPreferredSize(new Dimension(50,73));
	//	System.out.println("Card 2: " +player.getCards()[1].suit + player.getCards()[1].rank);
		
		card1.revalidate();
		card2.revalidate();
		card1.repaint();
		card2.repaint();
	}
	
	public void showCards(String[] cardImages){
		card1.setVisible(true);
		card1.setImage(ImageLibrary.getImage(cardImages[0]));
		card1.setBorder(BorderFactory.createLineBorder(Color.black));
		card1.setPreferredSize(new Dimension(50,73));
	//	System.out.println("Card 1: " +player.getCards()[0].suit + player.getCards()[0].rank);
		
		card2.setVisible(true);
		card2.setImage(ImageLibrary.getImage(cardImages[1]));
		card2.setBorder(BorderFactory.createLineBorder(Color.black));
		card2.setPreferredSize(new Dimension(50,73));
	//	System.out.println("Card 2: " +player.getCards()[1].suit + player.getCards()[1].rank);
		
		card1.revalidate();
		card2.revalidate();
		card1.repaint();
		card2.repaint();
	}
	
	public void hideCards(){
		card1.setImage(ImageLibrary.getImage("resources/images/back.jpg"));
		card1.setBorder(BorderFactory.createLineBorder(Color.black));
		card1.setPreferredSize(new Dimension(50,73));
		card2.setImage(ImageLibrary.getImage("resources/images/back.jpg"));
		card2.setBorder(BorderFactory.createLineBorder(Color.black));
		card2.setPreferredSize(new Dimension(50,73));
		card1.revalidate();
		card1.repaint();
		card2.revalidate();
		card2.repaint();
	}
	
	
	public Player getPlayer(){
		return player;
	}
	
	public void setPlayer(Player p){
		player = p;
	}
	

	public void deductAmount(int amount){
		String[] s = cashLabel.getText().split("\\s+");
		int value = Integer.valueOf(s[1]);
		value = value - amount;
		cashLabel.setText(s[0]+ " " + value);
	}

	public void updateLabelDealer(){
		dealerBlindLabel.setText("Dealer");
	}
	
	public void updateLabelBigBlind(){
		dealerBlindLabel.setText("Big Blind");
	}
	
	public void updateLabelLittleBlind(){
		dealerBlindLabel.setText("Little Blind");

	}

}

//public class PokerPlayer {
//	private String name;
//	private ImageIcon userImage;
//	
//	private int cash;
//	
//	
//	public PokerPlayer(String name, ImageIcon userImage){
//		this.name = name;
//		this.userImage = userImage;
//		cash = 1000;
//	}
//	
//	public int getCash(){
//		return cash;
//	}
//
//	public String getName(){
//		return name;
//	}
//
//	public Icon getProfile() {
//		return userImage;
//	}
//}

	//public class PokerPlayer {
//	private String name;
//	private ImageIcon userImage;
//	
//	private int cash;
//	
//	
//	public PokerPlayer(String name, ImageIcon userImage){
//		this.name = name;
//		this.userImage = userImage;
//		cash = 1000;
//	}
//	
//	public int getCash(){
//		return cash;
//	}
//
//	public String getName(){
//		return name;
//	}
//
//	public Icon getProfile() {
//		return userImage;
//	}
//}	
