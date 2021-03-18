package geometrie;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;

import dessinable.Dessinable;
/**
 * 
 * @author Thomas Bourgault
 * @author Audrey Viger
 *
 */
public class MursCourbes implements Dessinable, Shape{
	private double pixelParMetre = 1;
	private QuadCurve2D murCourbe;
	private double coordX1,coordY1, ctrlX, ctrlY, coordX2, coordY2;
	//Audrey Viger
	/**
	 * Permet d'instancier un objet de type MursCourbes
	 * @param coordX1 est la première coordonnée en x de la courbe. Elle est en double.
	 * @param coordY1 est la première coordonnée en y de la courbe. Elle est en double.
	 * @param ctrlX est le controle en x de la courbe. Il est en double.
	 * @param ctrlY est le controle en y de la courbe. Il est en double.
	 * @param coordX2 est la deuxième coordonnée en x de la courbe. Elle est en double.
	 * @param coordY2 est la deuxième coordonnée en y de la courbe. Elle est en double.
	 */

	public MursCourbes (double coordX1, double coordY1, double ctrlX, double ctrlY, double coordX2, double coordY2 ) {
		this.coordX1=coordX1;
		this.coordY1=coordY1;
		this.ctrlX=ctrlX;
		this.ctrlY=ctrlY;
		this.coordX2=coordX2;
		this.coordY2=coordY2;
		creerLaGeometrie();	
	}
	//Audrey Viger
	/**
	 * Méthode privée qui permet de créer la géométrie de la courbe avec toutes ses paramètres.
	 */
	private void creerLaGeometrie() {
		murCourbe = new QuadCurve2D.Double(coordX1,coordY1,ctrlX,ctrlY,coordX2,coordY2);
		
	}
	//Audrey Viger
	/**
	 *  Dessine les formes constituant l'objet. Dans ce contexte, il dessine un objet de type MursCourbes
	 *  @param g2d Contexte graphique du composant sur lequel dessiner
	 */
	public void dessiner(Graphics2D g2d) {
		AffineTransform mat= new AffineTransform();
		mat.scale(pixelParMetre,pixelParMetre);
		g2d.draw(mat.createTransformedShape(murCourbe));	
	}
	//Thomas Bourgault
	/**
	 * Méthode qui permet de changer la variable pixelsParMetre
	 * @param pixelsParMetre est un double qui exprime le nombre de pixel par mètre de l'image qui est inclut dans zonePinball
	 */
	public void setPixelsParMetre(double pixelsParMetre) {
		this.pixelParMetre = pixelsParMetre;									
	}
	//Thomas Bourgault
	/**
	 * Méthode qui retourne la première coordonnée en x d'un MursCourbes
	 * @return la première coordonnée en x
	 */
	public double getCoordX1() {
		return coordX1;
	}
	//Thomas Bourgault
		/**
		 * Méthode qui change la première coordonnée en x d'un MursCourbes
		 * @param la première coordonnée en x
		 */
	public void setCoordX1(double coordX1) {
		this.coordX1 = coordX1;
	}
	//Thomas Bourgault
		/**
		 * Méthode qui retourne la première coordonnée en y d'un MursCourbes
		 * @return la première coordonnée en y
		 */
	public double getCoordY1() {
		return coordY1;
	}
	//Thomas Bourgault
			/**
			 * Méthode qui change la première coordonnée en y d'un MursCourbes
			 * @param la première coordonnée en y
			 */
	public void setCoordY1(double coordY1) {
		this.coordY1 = coordY1;
	}
	//Thomas Bourgault
			/**
			 * Méthode qui retourne la deuxieme coordonnée en x d'un MursCourbes
			 * @return la deuxieme coordonnée en x
			 */
	public double getCoordX2() {
		return coordX2;
	}
	//Thomas Bourgault
	/**
	 * Méthode qui change la deuxieme coordonnée en x d'un MursCourbes
	 * @param la deuxieme coordonnée en x
	 */
	public void setCoordX2(double coordX2) {
		this.coordX2 = coordX2;
	}

//Thomas Bourgault
		/**
		 * Méthode qui retourne la deuxieme coordonnée en y d'un MursCourbes
		 * @return la deuxieme coordonnée en y
		 */
	public double getCoordY2() {
		return coordY2;
	}
	//Thomas Bourgault
		/**
		 * Méthode qui change la deuxieme coordonnée en y d'un MursCourbes
		 * @param la deuxieme coordonnée en y
		 */
	public void setCoordY2(double coordY2) {
		this.coordY2 = coordY2;
	}
	
	public QuadCurve2D getCourbe() {
		return murCourbe;
	}
	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Rectangle2D getBounds2D() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean contains(double x, double y) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean contains(Point2D p) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean intersects(double x, double y, double w, double h) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean intersects(Rectangle2D r) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean contains(double x, double y, double w, double h) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean contains(Rectangle2D r) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public PathIterator getPathIterator(AffineTransform at) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public PathIterator getPathIterator(AffineTransform at, double flatness) {
		// TODO Auto-generated method stub
		return null;
	}
}


