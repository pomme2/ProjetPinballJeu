package geometrie;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D.Double;

import dessinable.Dessinable;
import moteur.MoteurPhysique;

/**
 * 
 * @author Carlos Ed
 * @author Caro Houle
 * 
 * Classe Bille : représentation de la bille en mouvement ou est mémoriser
 * sa vitesse , sa masse, sa position et son diametre.
 *
 */


public class Bille extends Rectangle implements Dessinable {

	private double diametre = 1;
	private double masseEnKg = 0.5;
	private Ellipse2D.Double bille;
	private Vecteur2D forceExterieureAppliquee = new Vecteur2D(0,0);
	private Vecteur2D position;  //sera specifiee dans le constructeur
	private Vecteur2D vitesse = new Vecteur2D(0,0); //par defaut
	private Vecteur2D accel = new Vecteur2D(0,0); //par defaut
	private double pixelsParMetre =1;
	
	private Image imageBille;
	
	
	//constructeur de la bille
	public Bille(Vecteur2D position, double diametre) {
		this.position = new Vecteur2D(position);
		this.diametre = diametre;
		creerLaGeometrie();
	}
	
	
	
	private void creerLaGeometrie() {
		bille = new Ellipse2D.Double(position.x,position.y,diametre,diametre);
	}


	@Override
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dPrive = (Graphics2D) g2d.create();
		g2dPrive.scale(pixelsParMetre, pixelsParMetre);
		g2dPrive.fill(bille);
		
	}
	
	public void avancerUnPas(double deltaT) {
		try {
			//ici la somme des forces est tout simplement la force carrément appliqueée dans l'interface
			//normalement il faudrait la calculer!
			accel = MoteurPhysique.calculAcceleration(forceExterieureAppliquee, masseEnKg);
		} catch (Exception e) {
			System.out.println("Erreur calcul accélération (masse nulle)");
		}
		vitesse = MoteurPhysique.calculVitesse(deltaT, vitesse, accel);
		position = MoteurPhysique.calculPosition(deltaT, position, vitesse);
		creerLaGeometrie(); //la position a changé! on recree notre cercle
	}
	
	
	/**
	 * Modifie le facteur permettant de passer des metres aux pixels lors du dessin
	 * Ainsi on peut exprimer tout en m,  m/s  et m/s2
	 * @param pixelsParMetre Facteur de mise à l'échelle lors du dessin
	 */
	public void setPixelsParMetre(double pixelsParMetre) {
		this.pixelsParMetre = pixelsParMetre;
	}
	
	
	
	/**
	 * Retourne  la somme des forces appliquees
	 * @return La somme des forces appliquees
	 */
	public Vecteur2D getForceExterieureAppliquee() {
		return forceExterieureAppliquee;
	}
	
	

	/**
	 * Modifie d'un coup la somme des forces appliquees en x et en y
	 * Le prochain appel à avanceUnPas donnera donc des résultats differents.
	 * Note: Dans une situtaion physique simulee, on devrait habituellement calculer cette somme
	 * des forces!
	 * @param forceExterieureAppliquee La nouvelle somme des forces appliquees
	 */
	public void setForceExterieureAppliquee(Vecteur2D forceExterieureAppliquee) {
		this.forceExterieureAppliquee = forceExterieureAppliquee;
	}
	
	
	
	/**
	 * Modifie la position de la balle
	 * Note: ici on decide de simplement refaire la forme sous-jacente!
	 * @param pos Vecteur incluant les positions en x et y 
	 */
	public void setPosition(Vecteur2D pos) {
		//on fait une copie du vecteur  passe en paramètre 
		this.position = new Vecteur2D(pos);
		creerLaGeometrie();
	}	
	
	
	
	/**
	 * Retourne la position courante
	 * @return la position courante
	 */
	public Vecteur2D getPosition() {
		return (position);
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
	 * Associe une acceleration, ou modifie l'acceleration courante de la balle
	 * @param accel Vecteur incluant les accelerations en x et y 
	 */
	
	public void setAccel(Vecteur2D accel) {
		//on fait une copie du vecteur  passé en paramètre 
		this.accel = new Vecteur2D(accel);
	}
	
	/**
	 * Retourne l'acceleration courante
	 * @return acceleration courante
	 */
	public Vecteur2D getAccel() {
		return (accel);
	}
	
	/**
	 * Retourne le diametre de la balle
	 * @return Le diamètre
	 */
	public double getDiametre() {
		return diametre;
	}

	/**
	 * MOdifie le diametre de la balle
	 * @param diametre Le nouveau diamètre
	 */
	public void setDiametre(double diametre) {
		this.diametre = diametre;
		creerLaGeometrie();
	}


	/**
	 * Retourne la masse en Kg
	 * @return La masse en kg
	 */
	public double getMasseEnKg() {
		return masseEnKg;
	}

	/**
	 * Modifie la masse 
	 * @param masseEnKg La msse exprimee en kg
	 */
	public void setMasseEnKg(double masseEnKg) {
		this.masseEnKg = masseEnKg;
	}


	
	public Ellipse2D.Double getBille() {
		return bille;
	}
	

}
