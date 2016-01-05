package customcomponents;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class PaintedButton extends JButton{
	private static final long serialVersionUID = 7074393176317490987L;

	private Image toDraw;
	private final Image mUp;
	private final Image mDown;
	
	private final int mFontSize;
	
	public PaintedButton(String name, Image inUp, Image inDown, int size, int inFontSize) {
		super(name);
		super.setPreferredSize(new Dimension(size,size));
		this.setPreferredSize(new Dimension(size,size));
		toDraw = mUp = inUp;
		mDown = inDown;
		mFontSize = inFontSize;
		
		//Chane color of the chipButton whenever they hover over it
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				toDraw = mDown;
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				toDraw = mUp;
			}
		});
		
		super.setOpaque(false);
		setOpaque(true);
		setBackground(new Color(0,0,0,0));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(toDraw, 0, 0, getWidth(), getHeight(), null);
		g.setFont(new Font("Times Roman",Font.BOLD,mFontSize));
		super.paintComponent(g);
	}
	
	@Override
	protected void paintBorder(Graphics g) {
		//paint no border
	}
	
}

