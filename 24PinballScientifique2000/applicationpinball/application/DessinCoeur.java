package application;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import animation.CoeurVie;
/**
 * 
 * @author Thomas Bourgault
 * Classe qui permet de dessiner les différents coeur qui sont des objets de type CoeurVie
 */
public class DessinCoeur extends JPanel {
	private static final long serialVersionUID = 1L;
	private CoeurVie vie;
	java.net.URL urlCoeur = getClass().getClassLoader().getResource("Coeur.png");
	
	/**
	 * Constructeur qui permet lié l'objet de type CoeurVie à l'URL de l'image des coeurs
	 */
	public DessinCoeur() {
		vie=new CoeurVie(urlCoeur);
	}
	/**
	 * Méthode qui permet de dessiner les objets de type CoeurVie
	 * @param g le contexte graphique
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		vie.dessiner(g2d);
		repaint();
	}

}
