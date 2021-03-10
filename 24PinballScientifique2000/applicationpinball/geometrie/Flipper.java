package geometrie;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;

import dessinable.Dessinable;

/**
 * 
 * 
 * @author Carlos Ed
 *
 */

public class Flipper implements Dessinable {
	private double coordX,coordY,longueur;
	private 	QuadCurve2D.Double flipper;
	private double pixelsParMetre =1;
	private Vecteur2D position;  //sera specifiee dans le constructeur
	private Vecteur2D vitesse = new Vecteur2D(0,0); //par defaut
	private Vecteur2D accel = new Vecteur2D(0,0); //par defaut
	
	private Line2D flip;
	
	public  Flipper () {
		this.coordX = coordX;
		this.coordY = coordY;
		this.longueur = longueur;
		creerLaGeometrie();
	}

	private void creerLaGeometrie() {
		
		flipper = new QuadCurve2D.Double(400,400,100,550,400,450);
		
		 flip = new Line2D.Double (400,300 ,300 , 350);
		
	}

	@Override
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dPrive = (Graphics2D) g2d.create();
		g2dPrive.scale(pixelsParMetre, pixelsParMetre);
		g2dPrive.fill(flipper);
		
		
		g2dPrive.draw(flip);
		
		
		for(int i =0; i<5;i++) {
			
			g2dPrive.rotate(Math.PI/16, 400, 300);
			g2dPrive.draw(flip);
		}
	
		
	
		
	}

	

}



	


