package pokerclientgui;

import gamemechanics.Card;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pokerclient.PokerClient;
import libraries.ImageLibrary;
import customcomponents.PaintedPanel;

public class FlopPanel extends JPanel{
	
	private JPanel cardPanel;
		private PaintedPanel card1;
		private PaintedPanel card2;
		private PaintedPanel card3;
		private PaintedPanel card4;
		private PaintedPanel card5;

	private JPanel potPanel;	
	
	private JLabel potLabel;
		
	private Dimension cardSize;
	
	private Card [] flop;
	
	private int pot = 0;
	
	
	public FlopPanel(){		
		setOpaque(false);
		setBorder(BorderFactory.createLineBorder(Color.black));
		setLayout(new BorderLayout());
		cardSize = new Dimension(75,110);
		
		card1 = new PaintedPanel(ImageLibrary.getImage("resources/images/back.jpg"), 0, 0);
		card1.setBorder(BorderFactory.createLineBorder(Color.black));
		card1.setPreferredSize(cardSize);
		
		card2 = new PaintedPanel(ImageLibrary.getImage("resources/images/back.jpg"), 0, 0);
		card2.setBorder(BorderFactory.createLineBorder(Color.black));
		card2.setPreferredSize(cardSize);
		
		card3 = new PaintedPanel(ImageLibrary.getImage("resources/images/back.jpg"), 0, 0);
		card3.setBorder(BorderFactory.createLineBorder(Color.black));
		card3.setPreferredSize(cardSize);
		
		card4 = new PaintedPanel(ImageLibrary.getImage("resources/images/back.jpg"), 0, 0);
		card4.setBorder(BorderFactory.createLineBorder(Color.black));
		card4.setPreferredSize(cardSize);
		
		card5 = new PaintedPanel(ImageLibrary.getImage("resources/images/back.jpg"), 0, 0);
		card5.setBorder(BorderFactory.createLineBorder(Color.black));
		card5.setPreferredSize(cardSize);
	
		potLabel = new JLabel("Pot: " + pot);
		potLabel.setFont(new Font("Impact", Font.BOLD, 24));
		potLabel.setForeground(Color.white);
		
		//adding the card Images to the top Panel
		cardPanel = new JPanel();
		cardPanel.setOpaque(false);
			cardPanel.add(card1);
			cardPanel.add(card2);
			cardPanel.add(card3);
			cardPanel.add(card4);
			cardPanel.add(card5);
		add(cardPanel, BorderLayout.CENTER);
		
		potPanel = new JPanel();
		potPanel.setOpaque(false);
			potPanel.add(potLabel);
		add(potPanel, BorderLayout.SOUTH);
		
	}	
	
	public void setFlop(Card [] cards){
		flop = cards;
		//flopUpdate();
//		card1 = new PaintedPanel(ImageLibrary.getImage("resources/images/back.jpg"), 0, 0);
//		card1.setBorder(BorderFactory.createLineBorder(Color.black));
//		card1.setPreferredSize(new Dimension(50,73));
//		
//		card2 = new PaintedPanel(ImageLibrary.getImage("resources/images/back.jpg"), 0, 0);
//		card2.setBorder(BorderFactory.createLineBorder(Color.black));
//		card2.setPreferredSize(new Dimension(50,73));
//		
//		card3 = new PaintedPanel(ImageLibrary.getImage("resources/images/back.jpg"), 0, 0);
//		card3.setBorder(BorderFactory.createLineBorder(Color.black));
//		card3.setPreferredSize(new Dimension(50,73));
//		
//		card4 = new PaintedPanel(ImageLibrary.getImage("resources/images/back.jpg"), 0, 0);
//		card4.setBorder(BorderFactory.createLineBorder(Color.black));
//		card4.setPreferredSize(new Dimension(50,73));
//		
//		card5 = new PaintedPanel(ImageLibrary.getImage("resources/images/back.jpg"), 0, 0);
//		card5.setBorder(BorderFactory.createLineBorder(Color.black));
//		card5.setPreferredSize(new Dimension(50,73));
//		
//		add(card1);
//		add(card2);
//		add(card3);
//		add(card4);
//		add(card5);
	}
	
	public void flopUpdate(){		
		card1.setImage(ImageLibrary.getImage(flop[0].imagePath));
		card1.setBorder(BorderFactory.createLineBorder(Color.black));
		card1.setPreferredSize(cardSize);
		
		card2.setImage(ImageLibrary.getImage(flop[1].imagePath));
		card2.setBorder(BorderFactory.createLineBorder(Color.black));
		card2.setPreferredSize(cardSize);
		
		card3.setImage(ImageLibrary.getImage(flop[2].imagePath));
		card3.setBorder(BorderFactory.createLineBorder(Color.black));
		card3.setPreferredSize(cardSize);

		
		card1.revalidate();
		card2.revalidate();
		card3.revalidate();
		card1.repaint();
		card2.repaint();
		card3.repaint();
	}
	
	public void turnUpdate(int bettingRound){
		if(bettingRound == 0){
			flopUpdate();
		}
		else if(bettingRound == 1){
			card4.setImage(ImageLibrary.getImage(flop[3].imagePath));
			card4.setBorder(BorderFactory.createLineBorder(Color.black));
			card4.setPreferredSize(cardSize);
	
			
			card4.revalidate();
			card4.repaint();
		}
		else if(bettingRound == 2){
			card5.setImage(ImageLibrary.getImage(flop[4].imagePath));
			card5.setBorder(BorderFactory.createLineBorder(Color.black));
			card5.setPreferredSize(cardSize);
			
			card5.revalidate();
			card5.repaint();
		}
	}
	
	public void potUpdate(int potValue){
		pot = potValue + pot;
		potLabel.setText("Pot: " + pot);
		
		revalidate();
		repaint(); 
	}
	
	public int getPot(){
		return pot;
	}
	
	public void clearPot(){
		pot = 0;
		potLabel.setText("Pot: " + pot);
		
		revalidate();
		repaint(); 
	}
	public void resetCards(){
		card1.setImage(ImageLibrary.getImage("resources/images/back.jpg"));
		card1.setBorder(BorderFactory.createLineBorder(Color.black));
		card1.setPreferredSize(cardSize);
		
		card2.setImage(ImageLibrary.getImage("resources/images/back.jpg"));
		card2.setBorder(BorderFactory.createLineBorder(Color.black));
		card2.setPreferredSize(cardSize);
		
		card3.setImage(ImageLibrary.getImage("resources/images/back.jpg"));
		card3.setBorder(BorderFactory.createLineBorder(Color.black));
		card3.setPreferredSize(cardSize);
		
		card4.setImage(ImageLibrary.getImage("resources/images/back.jpg"));
		card4.setBorder(BorderFactory.createLineBorder(Color.black));
		card4.setPreferredSize(cardSize);
		
		card5.setImage(ImageLibrary.getImage("resources/images/back.jpg"));
		card5.setBorder(BorderFactory.createLineBorder(Color.black));
		card5.setPreferredSize(cardSize);
		
		card1.revalidate();
		card1.repaint();
		
		card2.revalidate();
		card2.repaint();
		
		card3.revalidate();
		card3.repaint();
	
		card4.revalidate();
		card4.repaint();
		
		card5.revalidate();
		card5.repaint();
	}
	
}