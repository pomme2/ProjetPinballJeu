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

	
	public Aimant(double coordX,double coordY, double diametre) {
		this.coordX=coordX;
		this.coordY=coordY;
		this.diametre=diametre;
		creerLaGeometrie();			
	}
	
	
	
	private void creerLaGeometrie() {
		aimant= new Ellipse2D.Double(coordX,coordY,diametre,diametre);

		
	}



	public void dessiner(Graphics2D g2d) {
		AffineTransform mat= new AffineTransform();
		mat.scale(pixelParMetre,pixelParMetre);
		g2d.draw(mat.createTransformedShape(aimant));
		
	}
	/**
	 * Méthode qui permet de changer la variable pixelsParMetre
	 * @param pixelsParMetre est un double qui exprime le nombre de pixel par mètre de l'image qui est inclut dans zonePinball
	 */
	public void setPixelsParMetre(double pixelsParMetre) {
		this.pixelParMetre = pixelsParMetre;
								
	}
	/**
	 * Méthode qui permet de changer la coordonnee en x et en y. Elle change donc la position du cercle
	 * @param coordX la coordonne en x du coin gauche du cercle
	 * @param coordX la coordonne en y du coin gauche du cercle
	 */
	public void setPositionAimant(double coordX,double coordY) {
		this.coordX=coordX;
		this.coordY=coordY;		
		creerLaGeometrie();
	}
	/**
	 * Méthode qui retourne la coordonne en x qui est au centre du cercle
	 * @return la coordonne en x du centre du cercle
	 */
	public double getPositionAimantX() {
		return (coordX+diametre/2);
	}
	/**
	 * Méthode qui retourne la coordonne en y qui est au centre du cercle
	 * @return la coordonne en y du centre du cercle
	 */
	public double getPositionAimantY() {
		return (coordY+diametre/2);
	}
	/**
	 * Méthode qui retourne le diametre du cercle
	 * @return le diametre du cercle
	 */
	public double getDiametre() {
		return this.diametre;
	}
	
	
}



