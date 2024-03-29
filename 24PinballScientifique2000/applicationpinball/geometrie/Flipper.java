package geometrie;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;

import dessinable.Dessinable;
import moteur.MoteurPhysique;

/**
 * Classe qui permet de creer une flipper gauche et un flipper droit
 * 
 * @author Thomas Bourgault
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
	private double angle=0;
	private int rad;


/**
 * Constructeur des flippers
 * @param position est la postion du flipper en Vecteur2D
 * @param longueurManche est la longueur du flipper
 * @param diametreManche est la largeur du flipper
 * @param gauche determine si le flipper est celui de gauche ou celui de droite
 */
	public  Flipper (Vecteur2D position, double longueurManche, double diametreManche,boolean gauche) {
		this.position=new Vecteur2D(position);
		this.longueurManche=longueurManche;
		this.diametreManche=diametreManche;
		this.gauche=gauche;
		creerLaGeometrie();
	}
/**
 * methode qui permet de recreer le flipper si c'est celui de gauche ou celui de droite
 */
	private void creerLaGeometrie() {
		if(gauche==true) {
			manche=new Ellipse2D.Double(position.x,position.y-diametreManche/2,longueurManche,diametreManche);	
		}else {
			manche= new Ellipse2D.Double(position.x-longueurManche,position.y-diametreManche/2,longueurManche,diametreManche);
		}
	}
/**
 * Methode qui permet de dessiner les flippers
 * @param g2d contexte graphique
 */
	@Override
	public void dessiner(Graphics2D g2d) {
		AffineTransform mat= new AffineTransform();
		mat.scale(pixelsParMetre,pixelsParMetre);
		
		g2d.fill(mat.createTransformedShape(manche));

	}

	/**
	 * M�thode qui permet de changer la variable pixelsParMetre
	 * @param pixelsParMetre est un double qui exprime le nombre de pixel par m�tre de l'image qui est inclut dans zonePinball
	 */
	public void setPixelsParMetre(double pixelsParMetre) {
		this.pixelsParMetre = pixelsParMetre;

	}
/**
 * Methode qui permet de calculer la vitesse et l'angle des flippers selon le pas d'animation
 * @param angleMax est l'angle maximum pour la rotation
 * @param tempsTotalEcoule est le temps depuis que le flipper est active
 * @param frequenceAngulaire est la variable qui determine la vitesse de l'animation
 * @param premierQuartPeriode determine si le flipper est dans la montee ou la descente
 */
	public void avancerUnPas(double angleMax, double tempsTotalEcoule, double frequenceAngulaire,boolean premierQuartPeriode) {
		angle=MoteurPhysique.calculAngle(angleMax, tempsTotalEcoule, frequenceAngulaire, premierQuartPeriode);
		vitesse=MoteurPhysique.vitesseFlipper( angleMax,  tempsTotalEcoule,  frequenceAngulaire, premierQuartPeriode);		
		creerLaGeometrie();
	}
	/**
	 * M�thode qui retourne l'angle du flipper
	 * @return l'angle du flipper
	 */
	public double getAngle() {
		return angle;
		
	}

	/**
	 * Modifie la vitesse courante de la balle
	 * @param vitesse Vecteur incluant les vitesses en x et y 
	 */
	public void setVitesse(Vecteur2D vitesse) {
		//on fait une copie du vecteur pass� en param�tre 
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
	 * Modifie la position d'un flipper
	 * @param position du flipper
	 */
	public void setPosition(Vecteur2D position) {
		this.position=new Vecteur2D (position);
	}
	/**
	 * Permet de retourne la position d'un flipper
	 * @return la position d'un flipper
	 */
	public Vecteur2D getPosition() {
		return(position);
	}
	/**
	 * M�thode qui permet de changer la variable diametreManche
	 * @param diametreManche est la hauter du flipper
	 */
	public void setDiametreManche(double diametreManche) {
		this.diametreManche = diametreManche;
	}	
	/**
	 * M�thode qui permet de retourner la hauteur du flipper
	 * @return diametreManche qui est la hauteur du flipper
	 */
	public double getDiametreManche() {
		return diametreManche;
	}
	/**
	 * M�thode qui permet de retourner le rayon du flipper
	 * @return diametreManche/2 qui est le rayon du flipper
	 */
	public double getRayonManche() {
		return diametreManche/2;
	}
}






