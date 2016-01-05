package pokerclientgui;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import gamemechanics.Player;

public class PokerClientWindow extends JFrame {
	
private static final long serialVersionUID = 1;
	private ClientPanel clientPanel;
	
	public PokerClientWindow(){ 
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		setTitle("Texas Hold'Em Poker");
		setSize(new Dimension(1060,800));
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setVisible(true);
//		setMinimumSize(new Dimension(1060,800));
//		setMaximumSize(new Dimension(1060,800));
		clientPanel = new ClientPanel();
		add(clientPanel);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);		
		setVisible(true);
	}

	public void playersUpdate(int np) {
		// TODO Auto-generated method stub
		clientPanel.playersUpdate(np);
		
	}

	public void createGameTable(Vector<Player> p) {
		// TODO Auto-generated method stub
		clientPanel.changeToGameTable(p);
		
	}
	public ClientPanel getClientPanel(){
		return clientPanel;
	}
	
	//something
	/*public static void main(String[] args) {
		//Create a new SorryClient Window
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	new PokerClientWindow();
		    }
		});
	}*/

}
