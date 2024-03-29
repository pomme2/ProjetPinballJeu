package geometrie;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D.Double;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.print.DocFlavor.URL;

import application.Dessin;
import dessinable.Dessinable;
import moteur.MoteurPhysique;

/**
 * 
 * @author Carlos Ed
 * @author Caro Houle
 * @author Audrey Viger
 * 
 * Classe Bille : repr�sentation de la bille en mouvement ou est m�moriser
 * sa vitesse , sa masse, sa position et son diametre.
 *
 */


public class Bille extends Rectangle implements Dessinable {

	private double diametre = 1;
	private double masseEnKg = 0.5;
	private double charge = 2;
	
	private Dessin dessin;
	
	
	private Ellipse2D.Double bille;
	private Vecteur2D forceExterieureAppliquee = new Vecteur2D(0,0);
	private Vecteur2D position;  //sera specifiee dans le constructeur
	private Vecteur2D vitesse = new Vecteur2D(0,0); //par defaut
	private Vecteur2D accel = new Vecteur2D(0,0); //par defaut
	private double pixelsParMetre =1;

	private BufferedImage img;


//Carlos Ed
/**
 * Constructeur de la bille
 * @param position de la bille en Vecteur2D
 * @param diametre de la bille 
 */
	public Bille(Vecteur2D position, double diametre) {
		this.position = new Vecteur2D(position);
		this.diametre = diametre;
		creerLaGeometrie();
	}

	//Carlos Ed
/**
 * Permet de recreer la bille
 */
	private void creerLaGeometrie() {
		bille = new Ellipse2D.Double(position.x,position.y,diametre,diametre);
	}
	//Carlos Ed
/**
 * Methode qui permet de dessiner la bille
 * @param g2d contexte graphique
 */
	@Override
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dPrive = (Graphics2D) g2d.create();
		g2dPrive.scale(pixelsParMetre, pixelsParMetre);
		//g2dPrive.fill(bille);

		lireImage(g2dPrive);

	}
	//Carlos Ed
/**
 * Methode qui permet de calculer le deplacement de la bille a chaque pas d'animation
 * @param deltaT est le pas d'animation
 */
	public void avancerUnPas(double deltaT) {
		try {
			//ici la somme des forces est tout simplement la force carr�ment applique�e dans l'interface
			//normalement il faudrait la calculer!
			accel = MoteurPhysique.calculAcceleration(forceExterieureAppliquee, masseEnKg);
		} catch (Exception e) {
			System.out.println("Erreur calcul acc�l�ration (masse nulle)");
		}
		vitesse = MoteurPhysique.calculVitesse(deltaT, vitesse, accel);
		position = MoteurPhysique.calculPosition(deltaT, position, vitesse);
		creerLaGeometrie(); //la position a chang�! on recree notre cercle
	}

	//Carlos Ed
	/**
	 * Modifie le facteur permettant de passer des metres aux pixels lors du dessin
	 * Ainsi on peut exprimer tout en m,  m/s  et m/s2
	 * @param pixelsParMetre Facteur de mise � l'�chelle lors du dessin
	 */
	public void setPixelsParMetre(double pixelsParMetre) {
		this.pixelsParMetre = pixelsParMetre;
	}

	//Carlos Ed

	/**
	 * Retourne  la somme des forces appliquees
	 * @return La somme des forces appliquees
	 */
	public Vecteur2D getForceExterieureAppliquee() {
		return forceExterieureAppliquee;
	}


	//Carlos Ed
	/**
	 * Modifie d'un coup la somme des forces appliquees en x et en y
	 * Le prochain appel � avanceUnPas donnera donc des r�sultats differents.
	 * Note: Dans une situtaion physique simulee, on devrait habituellement calculer cette somme
	 * des forces!
	 * @param forceExterieureAppliquee La nouvelle somme des forces appliquees
	 */
	public void setForceExterieureAppliquee(Vecteur2D forceExterieureAppliquee) {
		this.forceExterieureAppliquee = forceExterieureAppliquee;
	}
	//Carlos Ed
	/**
	 * Modifie la position de la balle
	 * Note: ici on decide de simplement refaire la forme sous-jacente!
	 * @param pos Vecteur incluant les positions en x et y 
	 */
	public void setPosition(Vecteur2D pos) {
		//on fait une copie du vecteur  passe en param�tre 
		this.position = new Vecteur2D(pos);
		creerLaGeometrie();
	}	

	//Carlos Ed

	/**
	 * Retourne la position courante
	 * @return la position courante
	 */
	public Vecteur2D getPosition() {
		return (position);
	}

	//Carlos Ed
	/**
	 * Modifie la vitesse courante de la balle
	 * @param vitesse Vecteur incluant les vitesses en x et y 
	 */
	public void setVitesse(Vecteur2D vitesse) {
		//on fait une copie du vecteur pass� en param�tre 
		this.vitesse = new Vecteur2D(vitesse);
	}
	//Carlos Ed
	/**
	 * Retourne la vitesse courante
	 * @return la vitesse courante
	 */
	public Vecteur2D getVitesse() {
		return (vitesse);
	}
	//Carlos Ed
	/**
	 * Associe une acceleration, ou modifie l'acceleration courante de la balle
	 * @param accel Vecteur incluant les accelerations en x et y 
	 */

	public void setAccel(Vecteur2D accel) {
		//on fait une copie du vecteur  pass� en param�tre 
		this.accel = new Vecteur2D(accel);
	}
	//Carlos Ed
	/**
	 * Retourne l'acceleration courante
	 * @return acceleration courante
	 */
	public Vecteur2D getAccel() {
		return (accel);
	}
	//Carlos Ed
	/**
	 * Retourne le diametre de la balle
	 * @return Le diam�tre
	 */
	public double getDiametre() {
		return diametre;
	}
	//Carlos Ed
	/**
	 * MOdifie le diametre de la balle
	 * @param diametre Le nouveau diam�tre
	 */
	public void setDiametre(double diametre) {
		this.diametre = diametre;
		creerLaGeometrie();
	}
	//Carlos Ed

	/**
	 * Retourne la masse en Kg
	 * @return La masse en kg
	 */
	public double getMasseEnKg() {
		return masseEnKg;
	}
	//Carlos Ed
	/**
	 * Modifie la masse 
	 * @param masseEnKg La msse exprimee en kg
	 */
	public void setMasseEnKg(double masseEnKg) {
		this.masseEnKg = masseEnKg;
	}
	
	//Carlos Ed
	/**
	 * Retourne la charge
	 * @return la charge de la bille
	 */
	public double getCharge() {
		return charge;
	}

	//Carlos Ed
	/**
	 * 
	 * Modifie la charge de la bille
	 * @param charge de la bille
	 */
	public void setCharge(double charge) {
		this.charge = charge;
	}
	//Carlos Ed
/**
 *  methode qui permet de retourner une Ellipse2D
 * @return une Ellipse2D qui est la bille
 */
	public Ellipse2D.Double getBille() {
		return bille;
	}
//Audrey Viger
/**
 * Methode qui permet de lire l'image qui est reli�e � la bille
 * @param g2d le contexte graphique
 */
	public void lireImage(Graphics2D g2d) {

		File pngOriginal = new File(System.getProperty("user.home")+"\\ImageB.png");
		File pngResized = new File(System.getProperty("user.home")+"\\ImageB.png");
		//redimImage(pngOriginal,pngResized,100,100,"png");
		try {
			img = ImageIO.read(new File(System.getProperty("user.home")+"\\ImageB.png"));
		} catch (IOException e) {

			e.printStackTrace();
		}
		Rectangle2D rect = new Rectangle2D.Double(13.098,4.391,diametre,diametre);

		TexturePaint texturePaintBille = new TexturePaint(img,rect);
		g2d.setPaint(texturePaintBille);
		Ellipse2D ellipseBille = new Ellipse2D.Double(position.getX(),position.getY(),diametre,diametre);
		g2d.fill(ellipseBille);

	}
	



}
