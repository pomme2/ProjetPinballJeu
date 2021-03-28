import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class SceneImage extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageBille imageBille;
	private String nomImage = "\\ImageB2.png";


public SceneImage() {
	
	imageBille = new ImageBille(nomImage);
	
	setLayout(null);
}
public void paintComponent(Graphics g) {
	super.paintComponent(g);
	Graphics2D g2d = (Graphics2D)g;
	System.out.println("sssssssssssssssssssss"+nomImage);
	imageBille.dessiner(g2d);
}
public void changerImage(String nomImageB) {
	this.nomImage = nomImageB;
	imageBille = new ImageBille(nomImage);
	repaint();
}
}