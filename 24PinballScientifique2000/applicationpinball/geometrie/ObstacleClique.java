package geometrie;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import dessinable.Dessinable;
import dessinable.Selectionnable;
/**
 * Classe qui permet de creer une des 4 formes selectionnes dans FenetreBacSable ou FenetreJouer
 * @author Audrey Viger
 *
 */
public class ObstacleClique  implements Dessinable,Selectionnable, Shape{

	private double posX;
	private double posY;
	private double larg;
	private double haut;
	private String forme;
	private Rectangle2D.Double carre, rectangle,ligneJ;
	private Ellipse2D.Double cercle;
	private Path2D triangle,obstacle;
	private double pixelsParMetre = 1;
	private boolean cercleSelectionne = false, carreSelectionne = false, triangleSelectionne=false,rectangleSelectionne = false;
	private double translatCarreX = 0, translatCarreY=0;
	private double xPrecedent, yPrecedent;
	/**
	 * Constructeur des formes
	 * @param poX est la position en x des formes
	 * @param posY est la position en y des formes
	 * @param larg est la largeur des formes
	 * @param haut est la hauteur des formes
	 * @param forme est le nomd e la forme
	 */
	public ObstacleClique(double poX, double posY, double larg,double haut,  String forme) {
		this.posX = poX;
		this.posY = posY;
		this.forme = forme;
		this.larg = larg;
		this.haut = haut;
		creerLaGeometrie();

	}


	/**
	 * Methode qui permet de creer la bonne forme selon la forme selectionneee dans FenetreBacSable ou FenetreJouer
	 */
	private void creerLaGeometrie() {

		if(forme == "Triangle") {
			ligneJ = new Rectangle2D.Double(0,590,700,5);
			triangle = new Path2D.Double();
			triangle.moveTo(posX-larg/2,posY+haut/2);
			triangle.lineTo(posX,posY-haut/2);
			triangle.lineTo(posX+larg/2,posY+haut/2);
			triangle.closePath();

		}else if(forme == "Carré") {
			carre = new Rectangle2D.Double(posX-haut/2,posY-haut/2,haut,haut);
			ligneJ = new Rectangle2D.Double(30,590,471,2);
		}else if (forme == "Cercle") {
			cercle = new Ellipse2D.Double(posX-larg/2,posY-larg/2,larg,larg);
			ligneJ = new Rectangle2D.Double(0,590,700,5);

		}else if (forme == "Rectangle") {
			rectangle = new Rectangle2D.Double(posX-larg/2,posY-haut/2,larg+larg/2.5,haut-haut/2.5);
			ligneJ = new Rectangle2D.Double(0,590,700,5);
		}


	}


	/**
	 * Methode qui permet de dessiner les formes
	 * @g2d contexte graphique
	 */
	public void dessiner(Graphics2D g2d) {
		AffineTransform mat = new AffineTransform();
		mat.scale(pixelsParMetre ,pixelsParMetre);

		if(forme=="Cercle") {
			g2d.fill(mat.createTransformedShape(cercle));
		}else if(forme=="Carré"){
			g2d.fill(mat.createTransformedShape(carre));
		}else if(forme=="Rectangle") {
			g2d.fill(mat.createTransformedShape(rectangle));
		}else if(forme=="Triangle"){
			g2d.fill(mat.createTransformedShape(triangle));
		}


	}

	/**
	 * Methode qui permet de determiner si le cercle est à une certaine position
	 * @param xPix est la postion en x
	 * @param  yPix est la position en y
	 */
	@Override
	public boolean contientCercle(double xPix, double yPix) {
		if(cercle.contains(xPix,yPix)) {
			return true;
		}else {
			return false;
		}

	}
	/**
	 * Methode qui permet de determiner si le carre est à une certaine position
	 * @param xPix est la postion en x
	 * @param  yPix est la position en y
	 */
	public boolean contientCarre(double xPix, double yPix) {
		if(carre.contains(xPix,yPix)) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * Methode qui permet de determiner si le rectangle est à une certaine position
	 * @param xPix est la postion en x
	 * @param  yPix est la position en y
	 */
	public boolean contientRectangle(double xPix, double yPix) {
		if(rectangle.contains(xPix,yPix)) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * Methode qui permet de determiner si le triangle est à une certaine position
	 * @param xPix est la postion en x
	 * @param  yPix est la position en y
	 */
	public boolean contientTriangle(double xPix, double yPix) {
		if(triangle.contains(xPix,yPix)) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Methodes qui sont de type abstraits car nous devons implementer le classe Shape
	 */
	////////////////////////////////////////////////////////////////////////////////////
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

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean contient(double xPix, double yPix) {
		// TODO Auto-generated method stub
		return false;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Methode qui retourne la position en y
	 * @return posY la position en y
	 */
	public double getPosY() {
		return posY;
	}

	/**
	 * Methode qui modofie la position en y
	 * @param posY la position en y
	 */
	public void setPosY(double posY) {
		this.posY = posY;
	}

	/**
	 * Methode qui retourne la largeur des différentes formes
	 * @return la largeur des différentes formes
	 */
	public double getLarg() {
		if(forme=="Cercle") {
			return larg;
		}else if(forme=="Carré"){
			return haut;
		}else if(forme=="Rectangle") {
			return (larg+larg/2.5);
		}else if(forme=="Triangle"){
			return larg;

		}else {
			return larg;
		}

	}

	/**
	 * Methode qui modofie la largeur des formes
	 * @param larg la lageur des formes
	 */
	public void setLarg(int larg) {
		this.larg = larg;
	}




	/**
	 * Methode qui retourne la hauteur des différentes formes
	 * @return la hauteur des différentes formes
	 */
	public double getHaut() {
		if(forme=="Cercle") {
			return haut;
		}else if(forme=="Carré"){
			return haut;
		}else if(forme=="Rectangle") {
			return (haut-haut/2.5);
		}else if(forme=="Triangle"){
			return haut;
		}else {
			return haut;
		}
	}


	/**
	 * Methode qui modofie la hauteur des formes
	 * @param haut la hauteur des formes
	 */


	public void setHaut(int haut) {
		this.haut = haut;
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
	 * Methode qui retourne la position en x des formes
	 * @return la position en x des formes
	 */
	public double getPosX() {
		// TODO Auto-generated method stub
		return posX;
	}





}
