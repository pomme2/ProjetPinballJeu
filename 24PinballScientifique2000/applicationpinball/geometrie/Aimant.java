package geometrie;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import dessinable.Dessinable;
/**
 * Classe forme un aimant avec un champ magnetique
 * @author Carlos Eduardo
 *
 */
public class Aimant  extends Rectangle implements Dessinable{
	
	private Ellipse2D.Double aimant, rondAimant;
	private double pixelParMetre=1;
	private double coordX,coordY,diametre;
	private Rectangle2D.Double carreNoir,recBlanc1,recBlanc2,carreRouge1,carreRouge2;
	
	private Vecteur2D position;  //sera specifiee dans le constructeur
	
	private double charge = -1;
	private Color couleur;
	

	


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
		rondAimant = new Ellipse2D.Double(coordX-(diametre/2),coordY-(diametre/2),diametre*2,diametre*2);
		carreNoir = new Rectangle2D.Double(coordX+(diametre/2),coordY-(diametre/2),diametre*2,diametre*2);
		recBlanc1 = new Rectangle2D.Double(coordX+(diametre/2),coordY-(diametre/2),diametre,diametre/2);
		recBlanc2 = new Rectangle2D.Double(coordX+(diametre/2),coordY+(diametre),diametre,diametre/2);
		carreRouge1 = new Rectangle2D.Double(coordX+(diametre),coordY+(diametre),diametre/2,diametre/2);
		carreRouge2 = new Rectangle2D.Double(coordX+(diametre),coordY-(diametre/2),diametre/2,diametre/2);
	}

/**
 * Methode qui permet de dessiner l'aimant
 * @param g2d le contexte graphique
 */
	public void dessiner(Graphics2D g2d) {
		
	
		AffineTransform mat= new AffineTransform();
		mat.scale(pixelParMetre,pixelParMetre);
		
		g2d.setColor(Color.gray);
		g2d.fill(mat.createTransformedShape(rondAimant));
		
		g2d.setColor(Color.black);
		g2d.fill(mat.createTransformedShape(aimant));
		g2d.fill(mat.createTransformedShape(carreNoir));
		
		g2d.setColor(Color.gray);
		g2d.fill(mat.createTransformedShape(recBlanc1));
		g2d.fill(mat.createTransformedShape(recBlanc2));
			g2d.setColor(couleur);
			g2d.setColor(Color.red);
		
		if (getCharge()>0) {
			g2d.setColor(Color.YELLOW);
		}
	
		g2d.fill(mat.createTransformedShape(carreRouge1));
		g2d.fill(mat.createTransformedShape(carreRouge2));
		
	}
	/**
	 * Méthode qui permet de changer la variable pixelsParMetre
	 * @param pixelsParMetre est un double qui exprime le nombre de pixel par mètre de l'image qui est inclut dans zonePinball
	 */
	public void setPixelsParMetre(double pixelsParMetre) {
		this.pixelParMetre = pixelsParMetre;
								
	}
	/**
	 * Méthode qui permet de changer la coordonnee en x et en y. Elle change donc la position de l'aimant
	 * @param coordX la coordonne en x du coin gauche de l'aimant
	 * @param coordX la coordonne en y du coin gauche de l'aimant
	 */
	public void setPositionAimant(double coordX,double coordY) {
		this.coordX=coordX;
		this.coordY=coordY;		
		creerLaGeometrie();
	}
	/**
	 * Méthode qui retourne la coordonne en x qui est au centre de l'aimant
	 * @return la coordonne en x du centre du cercle
	 */
	public double getPositionAimantX() {
		return (coordX+diametre/2);
	}
	/**
	 * Méthode qui retourne la coordonne en y qui est au centre de l'aimant
	 * @return la coordonne en y du centre du cercle
	 */
	public double getPositionAimantY() {
		return (coordY+diametre/2);
	}
	/**
	 * Méthode qui retourne le diametre de l'aimant
	 * @return le diametre de l'aimant
	 */
	public double getDiametre() {
		return this.diametre;
	}
	
	/**
	 * Méthode qui retourne la coordonne en x de l'aimant
	 * @return la coordonne en x de l'aimant
	 */
	public double getCoordX() {
		return coordX;
	}
	
	/**
	 * Méthode qui modifie la coordonne en x de l'aimant
	 * @param la nouvelle coordonne en x de l'aimant
	 */

	public void setCoordX(double coordX) {
		this.coordX = coordX;
	}
	/**
	 * Méthode qui retourne la coordonne en y de l'aimant
	 * @return la coordonne en y de l'aimant
	 */
	public double getCoordY() {
		return coordY;
	}

	/**
	 * Méthode qui modifie la coordonne en y de l'aimant
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
	 
	public void setCouleurAimant(Color couleur) {
		this.couleur = couleur;
	}

	
	
}



