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
	
	public ObstacleClique(double poX, double posY, double larg,double haut,  String forme) {
		this.posX = poX;
		this.posY = posY;
		this.forme = forme;
		this.larg = larg;
		this.haut = haut;
		creerLaGeometrie();
		
	}


	






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
		
			//obstacle.moveTo(posX-larg/2, posY-larg/2);
			//obstacle.lineTo(posX+larg/2, posY-larg/2);
			//obstacle.lineTo(posX+larg/2, posY+larg/2);
			//obstacle.closePath();
		}else if (forme == "Cercle") {
			cercle = new Ellipse2D.Double(posX-larg/2,posY-larg/2,larg,larg);
			ligneJ = new Rectangle2D.Double(0,590,700,5);
			
		}else if (forme == "Rectangle") {
			rectangle = new Rectangle2D.Double(posX-larg/2,posY-haut/2,larg+larg/2.5,haut-haut/2.5);
			ligneJ = new Rectangle2D.Double(0,590,700,5);
			//obstacle.moveTo(posX-larg/2,posY-haut/2);
			//obstacle.moveTo(posX+larg/2,posY-haut/2);
			//obstacle.moveTo(posX+larg/2,posY+haut/2);
			//obstacle.closePath();
		}
		
		
	}
	


	public void dessiner(Graphics2D g2d) {
		AffineTransform mat = new AffineTransform();
		mat.scale(pixelsParMetre ,pixelsParMetre);
		
		if(forme=="Cercle") {
			g2d.fill(mat.createTransformedShape(cercle));
		//	g2d.fill(mat.createTransformedShape(ligneJ));
		}else if(forme=="Carré"){
			g2d.fill(mat.createTransformedShape(carre));
			//g2d.fill(mat.createTransformedShape(ligneJ));
		}else if(forme=="Rectangle") {
			g2d.fill(mat.createTransformedShape(rectangle));
		//	g2d.fill(mat.createTransformedShape(ligneJ));
			
		//}else {
			//g2d.fill(mat.createTransformedShape(triangle));
		}else if(forme=="Triangle"){
			g2d.fill(mat.createTransformedShape(triangle));
		//	g2d.fill(mat.createTransformedShape(ligneJ));
		}
		
		
	}
/*
 * @Override
	public boolean contientCercle(double xPix, double yPix) {
	if(cercle.contains(xPix,yPix)) {
		return true;
	}else if(carre.contains(xPix,yPix)) {
		return true;
	}else if(rectangle.contains(xPix,yPix)) {
		return true;
	}else {
		return false;
	}
		
	}
 * 
 */
	@Override
	public boolean contientCercle(double xPix, double yPix) {
	if(cercle.contains(xPix,yPix)) {
		return true;
	}else {
		return false;
	}
		
	}
	public boolean contientCarre(double xPix, double yPix) {
		if(carre.contains(xPix,yPix)) {
			return true;
	}else {
		return false;
	}
	}
	public boolean contientRectangle(double xPix, double yPix) {
	if(rectangle.contains(xPix,yPix)) {
		return true;
	}else {
		return false;
	}
	}
	public boolean contientTriangle(double xPix, double yPix) {
		if(triangle.contains(xPix,yPix)) {
			return true;
		}else {
			return false;
		}
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


	@Override
	public boolean contient(double xPix, double yPix) {
		// TODO Auto-generated method stub
		return false;
	}

	public double getPosY() {
		return posY;
	}


	public void setPosY(double posY) {
		this.posY = posY;
	}


	public double getLarg() {
		if(forme=="Cercle") {
			return larg;
		//	g2d.fill(mat.createTransformedShape(ligneJ));
		}else if(forme=="Carré"){
			return haut;
			//g2d.fill(mat.createTransformedShape(ligneJ));
		}else if(forme=="Rectangle") {
			return (larg+larg/2.5);
		//	g2d.fill(mat.createTransformedShape(ligneJ));
			
		//}else {
			//g2d.fill(mat.createTransformedShape(triangle));
		}else if(forme=="Triangle"){
			return larg;
		//	g2d.fill(mat.createTransformedShape(ligneJ));
		}else {
			return larg;
		}
		
	}





	public void setLarg(int larg) {
		this.larg = larg;
	}





	public double getHaut() {
		if(forme=="Cercle") {
			return haut;
		//	g2d.fill(mat.createTransformedShape(ligneJ));
		}else if(forme=="Carré"){
			return haut;
			//g2d.fill(mat.createTransformedShape(ligneJ));
		}else if(forme=="Rectangle") {
			return (haut-haut/2.5);
		//	g2d.fill(mat.createTransformedShape(ligneJ));
			
		//}else {
			//g2d.fill(mat.createTransformedShape(triangle));
		}else if(forme=="Triangle"){
			return haut;
		//	g2d.fill(mat.createTransformedShape(ligneJ));
		}else {
			return haut;
		}
	}





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






	public double getPosX() {
		// TODO Auto-generated method stub
		return posX;
	}


}
