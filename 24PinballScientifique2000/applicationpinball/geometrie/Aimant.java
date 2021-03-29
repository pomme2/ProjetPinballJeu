package geometrie;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import dessinable.Dessinable;
/**
 * Classe forme un aimant avec un champ magnetique
 * @author Carlos Eduardo
 *
 */
public class Aimant  extends Rectangle implements Dessinable{
	
	private Ellipse2D.Double aimant;
	private double pixelParMetre=1;
	private double coordX,coordY,diametre;
	
	private Vecteur2D position;  //sera specifiee dans le constructeur
	
	private double charge = -1;

	


/**
 * Constructeur de l'aimant
 * @param coordX est la coordonne en X de l'aimant
 * @param coordY est la coordonne en Y de l'aimant
 * @param diametre est le diametre de l'aimant
 */
	public Aimant(double coordX,double coordY, double diametre) {
		this.coordX=coordX;
		this.coordY=coordY;
		this.diametre=diametre;
		this.setPosition(new Vecteur2D(coordX,coordY));
		creerLaGeometrie();			
	}
	
	
	/**
	 * Methode qui permet de recreer l'aimant
	 */
	private void creerLaGeometrie() {
		aimant= new Ellipse2D.Double(coordX,coordY,diametre,diametre);

		
	}

/**
 * Methode qui permet de dessiner l'aimant
 * @param g2d le contexte graphique
 */
	public void dessiner(Graphics2D g2d) {
		AffineTransform mat= new AffineTransform();
		mat.scale(pixelParMetre,pixelParMetre);
		g2d.draw(mat.createTransformedShape(aimant));
		
	}
	/**
	 * M�thode qui permet de changer la variable pixelsParMetre
	 * @param pixelsParMetre est un double qui exprime le nombre de pixel par m�tre de l'image qui est inclut dans zonePinball
	 */
	public void setPixelsParMetre(double pixelsParMetre) {
		this.pixelParMetre = pixelsParMetre;
								
	}
	/**
	 * M�thode qui permet de changer la coordonnee en x et en y. Elle change donc la position de l'aimant
	 * @param coordX la coordonne en x du coin gauche de l'aimant
	 * @param coordX la coordonne en y du coin gauche de l'aimant
	 */
	public void setPositionAimant(double coordX,double coordY) {
		this.coordX=coordX;
		this.coordY=coordY;		
		creerLaGeometrie();
	}
	/**
	 * M�thode qui retourne la coordonne en x qui est au centre de l'aimant
	 * @return la coordonne en x du centre du cercle
	 */
	public double getPositionAimantX() {
		return (coordX+diametre/2);
	}
	/**
	 * M�thode qui retourne la coordonne en y qui est au centre de l'aimant
	 * @return la coordonne en y du centre du cercle
	 */
	public double getPositionAimantY() {
		return (coordY+diametre/2);
	}
	/**
	 * M�thode qui retourne le diametre de l'aimant
	 * @return le diametre de l'aimant
	 */
	public double getDiametre() {
		return this.diametre;
	}
	
	/**
	 * M�thode qui retourne la coordonne en x de l'aimant
	 * @return la coordonne en x de l'aimant
	 */
	public double getCoordX() {
		return coordX;
	}
	
	/**
	 * M�thode qui modifie la coordonne en x de l'aimant
	 * @param la nouvelle coordonne en x de l'aimant
	 */

	public void setCoordX(double coordX) {
		this.coordX = coordX;
	}
	/**
	 * M�thode qui retourne la coordonne en y de l'aimant
	 * @return la coordonne en y de l'aimant
	 */
	public double getCoordY() {
		return coordY;
	}

	/**
	 * M�thode qui modifie la coordonne en y de l'aimant
	 * @param la nouvelle coordonne en y de l'aimant
	 */

	public void setCoordY(double coordY) {
		this.coordY = coordY;
	}


/**
 * Methode qui retourne la charge de l'aimant
 * @return la charge de l'aimant
 */
	public double getCharge() {
		return charge;
	}


	/**
	 * Methode qui modifie la charge de l'aimant
	 * @param la nouvelle charge de l'aimant
	 */
	public void setCharge(double charge) {
		this.charge = charge;
	}

	/**
	 * Methode qui retourne un vecteur2D position de l'aimant
	 * @return le vecteur2D position de l'aimant
	 */

	public Vecteur2D getPosition() {
		return position;
	}


	/**
	 * Methode qui modifie un vecteur2D position de l'aimant
	 * @param le vecteur2D position de l'aimant
	 */
	public void setPosition(Vecteur2D position) {
		this.position = position;
	}

	
	
}


