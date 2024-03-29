package application;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

import javax.swing.JPanel;

import dessinable.Dessinable;
/**
 * Classe qui permet de montrer l'inclinaison de la table
 * @author Audrey Viger
 *
 */
public class Inclinaison extends JPanel{
	private static final long serialVersionUID = 1L;

	private int angle;
	
	private int coordx1 =15, coordx2 = 55, coordy1 = 10, coordy2 = 50, coordYangle = 20;
	private Path2D.Double graph;
	private Line2D.Double inclinaison;
/**
 * Constructeur de l'inclinaison de la table
 */
	public Inclinaison(){

		setBackground(Color.white);

	}

	/**
	 * Methode qui permet de dessiner l'inclinaison
	 * @param g le contexte graphique
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(Color.red);
		graph = new Path2D.Double();
		graph.moveTo(coordx1, coordy1);
		graph.lineTo(coordx1, coordy2);
		graph.lineTo(coordx2, coordy2);


		g2d.drawString(""+angle+"\u00b0",coordy2,coordYangle);

		g2d.draw(graph);
		
		g2d.setColor(Color.black);
		inclinaison = new Line2D.Double(coordx1,coordy2,coordy2,coordy2);
		g2d.rotate(Math.toRadians(-angle),coordx1,coordy2);
		g2d.draw(inclinaison);

	}
	/**
	 * Permet de modfier l'inclinaison de la table
	 * @param angle est l'angle d'inclinaison de la table
	 */
	public void setInclinaison(int angle) {
		this.angle = angle;
		repaint();
	}

}
