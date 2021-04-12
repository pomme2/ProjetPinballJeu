package application;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import animation.CoeurVie;
/**
 * 
 * @author Thomas Bourgault
 *
 */
public class DessinCoeur extends JPanel {
	private static final long serialVersionUID = 1L;
	private CoeurVie coeur;
	java.net.URL urlCoeur = getClass().getClassLoader().getResource("Coeur.png");
	
	public DessinCoeur() {
		coeur=new CoeurVie(urlCoeur);
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		coeur.dessiner(g2d);
		repaint();
	}

}
