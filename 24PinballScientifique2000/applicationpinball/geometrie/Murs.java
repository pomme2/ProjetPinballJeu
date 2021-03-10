package geometrie;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.util.Arrays;
import java.util.Scanner;
import dessinable.Dessinable;

/**
 * Cette classe permet de cr�er des cercles g2d invisibles qu'on superpose sur l'image du terrain de pinball.
 * @author Thomas Bourgault
 *
 */

public class Murs extends Rectangle implements Dessinable  {
	private Ellipse2D.Double obstacleCercle;
	private double pixelParMetre=1;
	private double coordX,coordY,diametre;

	/**
	 * Permet d'instancier un objet de type Murs
	 * @param coordX  est la coordonn�e en x du coin gauche du cercle. Elle est en double.
	 * @param coordY est la coordonn�e en y du coin gauche du cercle. Elle est en double.
	 * @param diametre est la variable qui d�termine le diam�tre du cercle. Elle est en double.
	 */

	public Murs(double coordX,double coordY, double diametre) {
		this.coordX=coordX;
		this.coordY=coordY;
		this.diametre=diametre;
		creerLaGeometrie();			
	}
	/**
	 *  M�thode priv�e qui permet de cr�er la g�om�trie du cercle avec toutes ses param�tres.
	 */
	public void creerLaGeometrie() {
		obstacleCercle= new Ellipse2D.Double(coordX,coordY,diametre,diametre);
	}
	/**
	 * Dessine les formes constituant l'objet. Dans ce contexte, il dessine un objet de type Murs
	 * @param g2d Contexte graphique du composant sur lequel dessiner
	 */
	public void dessiner(Graphics2D g2d) {
		AffineTransform mat= new AffineTransform();
		mat.scale(pixelParMetre,pixelParMetre);
		g2d.draw(mat.createTransformedShape(obstacleCercle));				
	}
	/**
	 * M�thode qui permet de changer la variable pixelsParMetre
	 * @param pixelsParMetre est un double qui exprime le nombre de pixel par m�tre de l'image qui est inclut dans zonePinball
	 */
	public void setPixelsParMetre(double pixelsParMetre) {
		this.pixelParMetre = pixelsParMetre;
								
	}
	/**
	 * M�thode qui permet de changer la coordonnee en x et en y. Elle change donc la position du cercle
	 * @param coordX la coordonne en x du coin gauche du cercle
	 * @param coordX la coordonne en y du coin gauche du cercle
	 */
	public void setPositionMurs(double coordX,double coordY) {
		this.coordX=coordX;
		this.coordY=coordY;		
		creerLaGeometrie();
	}
	/**
	 * M�thode qui retourne la coordonne en x qui est au centre du cercle
	 * @return la coordonne en x du centre du cercle
	 */
	public double getPositionMursX() {
		return (coordX+diametre/2);
	}
	/**
	 * M�thode qui retourne la coordonne en y qui est au centre du cercle
	 * @return la coordonne en y du centre du cercle
	 */
	public double getPositionMursY() {
		return (coordY+diametre/2);
	}
	/**
	 * M�thode qui retourne le diametre du cercle
	 * @return le diametre du cercle
	 */
	public double getDiametre() {
		return this.diametre;
	}
	
	
}
