package application;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class SceneClassement extends JPanel{
	private HighScore hs;
	private static final long serialVersionUID = 1L;
	
	public SceneClassement() {
		
		hs = new HighScore(null, null);
		
		setLayout(null);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		//System.out.println("sssssssssssssssssssss"+nomImage);
		hs.toString();
	}

}
