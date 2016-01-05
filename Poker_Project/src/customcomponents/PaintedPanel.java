package customcomponents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class PaintedPanel extends JPanel {
	private Image mImage;
	private boolean mDrawBack;
	private Integer shrinkWidth;
	private Integer shrinkHeight;
	
	public PaintedPanel(Image inImage, Integer shrinkWidth, Integer shrinkHeight) {
		mImage = inImage;
		setBackground(new Color(0,0,0,0));
		this.setOpaque(true);
		this.shrinkWidth = shrinkWidth;
		this.shrinkHeight = shrinkHeight;
	}
	
	public PaintedPanel(Image inImage, boolean inDrawBack) {
		mDrawBack = inDrawBack;
		mImage = inImage;
		setBackground(new Color(0,0,0,0));
		this.setOpaque(true);
		repaint();
	}
	
	public void setImage(Image inImage) {
		mImage = inImage;
		//repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(mDrawBack)g.drawImage(mImage, 0, 0, getWidth(), getHeight(), Color.WHITE, null);
		else{
			if(shrinkWidth == null || shrinkHeight == null)
				g.drawImage(mImage, 0, 0, getWidth(), getHeight(), null);
			else{
				g.drawImage(mImage, shrinkWidth/2,shrinkHeight/2, getWidth()-shrinkWidth,getHeight()-shrinkHeight, null);
			}
		}
			
	}
}
