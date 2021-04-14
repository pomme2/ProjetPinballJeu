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
	private CoeurVie vie;
	java.net.URL urlCoeur = getClass().getClassLoader().getResource("Coeur.png");
	
	
	public DessinCoeur() {
		vie=new CoeurVie(urlCoeur);
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		vie.dessiner(g2d);
		repaint();
	}

}
