package geometrie;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;

import dessinable.Dessinable;

/**
 * 
 * 
 * @author Carlos Ed
 *
 */

public class Flipper implements Dessinable {
	private double coordX1,coordY1,diametreCercle,longueurManche,diametreManche;



	private boolean gauche=true;
	private Ellipse2D.Double boutCercle;
	private  Ellipse2D.Double manche;
	private double pixelsParMetre =1;
	private Vecteur2D position;  //sera specifiee dans le constructeur
	private Vecteur2D vitesse = new Vecteur2D(0,0); //par defaut
	private Vecteur2D accel = new Vecteur2D(0,0); //par defaut



	public  Flipper (Vecteur2D position, double longueurManche, double diametreManche,boolean gauche) {
		this.position=new Vecteur2D(position);
		this.longueurManche=longueurManche;
		this.diametreManche=diametreManche;
		this.gauche=gauche;
		creerLaGeometrie();
	}

	private void creerLaGeometrie() {
		if(gauche==true) {
			manche=new Ellipse2D.Double(position.x,position.y-diametreManche/2,longueurManche,diametreManche);	
		}else {
			manche= new Ellipse2D.Double(position.x-longueurManche,position.y-diametreManche/2,longueurManche,diametreManche);
		}
	}

	@Override
	public void dessiner(Graphics2D g2d) {
		AffineTransform mat= new AffineTransform();
		mat.scale(pixelsParMetre,pixelsParMetre);
		g2d.fill(mat.createTransformedShape(manche));


	}

	/**
	 * Méthode qui permet de changer la variable pixelsParMetre
	 * @param pixelsParMetre est un double qui exprime le nombre de pixel par mètre de l'image qui est inclut dans zonePinball
	 */
	public void setPixelsParMetre(double pixelsParMetre) {
		this.pixelsParMetre = pixelsParMetre;

	}
	public void avancerUnPas() {

	}
	/**
	 * Modifie la vitesse courante de la balle
	 * @param vitesse Vecteur incluant les vitesses en x et y 
	 */
	public void setVitesse(Vecteur2D vitesse) {
		//on fait une copie du vecteur passé en paramètre 
		this.vitesse = new Vecteur2D(vitesse);
	}
	/**
	 * Retourne la vitesse courante
	 * @return la vitesse courante
	 */
	public Vecteur2D getVitesse() {
		return (vitesse);
	}
	/**
	 * Méthode qui permet de changer la variable diametreManche
	 * @param diametreManche est la hauter du flipper
	 */
	public void setDiametreManche(double diametreManche) {
		this.diametreManche = diametreManche;
	}	
	/**
	 * Méthode qui permet de retourner la hauteur du flipper
	 * @return diametreManche qui est la hauteur du flipper
	 */
	public double getDiametreManche() {
		return diametreManche;
	}
	/**
	 * Méthode qui permet de retourner le rayon du flipper
	 * @return diametreManche/2 qui est le rayon du flipper
	 */
	public double getRayonManche() {
		return diametreManche/2;
	}
}






