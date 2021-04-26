package application;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
/**
 * Classe qui permet de montrer le dessin de la bille sur l'interface
 * @author Audrey Viger
 *
 */
public class SceneImage extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageBille imageBille;
	private String nomImage = "\\ImageB.png";

/**
 * Constructeur de l'image sur l'interface
 */
public SceneImage() {
	
	imageBille = new ImageBille(nomImage);
	
	setLayout(null);
}
/**
 * Methode qui permet de dessiner l'image de la bille sur l'interface
 * @param g le contexte graphique
 */
public void paintComponent(Graphics g) {
	super.paintComponent(g);
	Graphics2D g2d = (Graphics2D)g;
	imageBille.dessiner(g2d);
}

/**
 * Methode qui permet de modifier le nom de l'image de la bille
 * @param nomImageB est le nom de l'image de la bille
 */
public void changerImage(String nomImageB) {
	this.nomImage = nomImageB;
	imageBille = new ImageBille(nomImage);
	repaint();
}
}