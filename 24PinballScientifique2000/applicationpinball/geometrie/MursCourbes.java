package geometrie;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import dessinable.Dessinable;
/**
 * 
 * @author Thomas Bourgault
 * @author Audrey Viger
 *
 */
public class MursCourbes implements Dessinable{
	private double pixelParMetre = 1;
	private QuadCurve2D murCourbe;
	private double coordX1,coordY1, ctrlX, ctrlY, coordX2, coordY2;
	private Path2D.Double  segmentTest;
	private MursDroits ligneTest; 
	ArrayList<Double> coordXligneSegment = new ArrayList<Double>();
	ArrayList<Double> coordYligneSegment = new ArrayList<Double>();
	private double flatness=0.01;
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

	public MursCourbes (double coordX1, double coordY1, double ctrlX, double ctrlY, double coordX2, double coordY2, Path2D.Double segmentTest ) {
		this.coordX1=coordX1;
		this.coordY1=coordY1;
		this.ctrlX=ctrlX;
		this.ctrlY=ctrlY;
		this.coordX2=coordX2;
		this.coordY2=coordY2;
		this.segmentTest=segmentTest;
		creerLaGeometrie();	
	}
	//Audrey Viger
	/**
	 * Méthode privée qui permet de créer la géométrie de la courbe avec toutes ses paramètres.
	 */
	private void creerLaGeometrie() {
		segmentTest=new Path2D.Double();
		murCourbe = new QuadCurve2D.Double(coordX1,coordY1,ctrlX,ctrlY,coordX2,coordY2);
		infoSegmentCourbe(murCourbe);
		

	}
	//Audrey Viger
	/**
	 *  Dessine les formes constituant l'objet. Dans ce contexte, il dessine un objet de type MursCourbes
	 *  @param g2d Contexte graphique du composant sur lequel dessiner
	 */
	public void dessiner(Graphics2D g2d) {
		AffineTransform mat= new AffineTransform();
		mat.scale(pixelParMetre,pixelParMetre);
		g2d.setColor(Color.green);
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
	//Thomas Bourgault
/**
 * Methode qui permet de retourne la courbe
 * @return un objet de type courbe (murCourbe)
 */
	public QuadCurve2D getCourbe() {
		return murCourbe;
	}

	//Thomas Bourgault
	/**
	 * Methode qui permet de deconstruire la courbe en Path2D
	 * @param courbe est la Shape prit en compte pour le cassage de la courbe
	 */
	public  void infoSegmentCourbe(Shape courbe) {

		double[] coordonnees = new double[2];
		AffineTransform identite = new AffineTransform();
		identite.scale(pixelParMetre,pixelParMetre);
		PathIterator path = courbe.getPathIterator(identite,flatness );
		segmentTest= new Path2D.Double();
		while (path.isDone() == false) {
			int type = path.currentSegment(coordonnees);
			switch (type) {
			case PathIterator.SEG_MOVETO:
				segmentTest.moveTo(coordonnees[0] , coordonnees[1]);				
				coordXligneSegment.add(coordonnees[0]);
				coordYligneSegment.add(coordonnees[1]);
				break;
			case PathIterator.SEG_LINETO:
				segmentTest.lineTo(coordonnees[0] , coordonnees[1]); 
				coordXligneSegment.add(coordonnees[0]);
				coordYligneSegment.add(coordonnees[1]);
				break;
			case PathIterator.SEG_CLOSE:
				segmentTest.closePath();
				break;
			}
			path.next(); 

		}

		
	}
	//Thomas Bourgault
	/**
	 * Methode qui permet de retourner la liste des coordonnee en x des courbes
	 * @return la liste des coordonne en x des courbes
	 */
	public ArrayList<Double> listeCoordX(){
		return coordXligneSegment;
	}
	//Thomas Bourgault
	/**
	 * Methode qui met la liste des coordonnes en x des courbes dans une nouvelle liste
	 * @param nouvelleListe une nouvelle liste
	 * @return la nouvelle liste qui contient les coordonnes en x des courbes
	 */
	public ArrayList<Double> listeCoordX(ArrayList<Double> nouvelleListe){
		int i=0;
		while( i<coordXligneSegment.size()) {
			nouvelleListe.add(coordXligneSegment.get(i));
			i++;
		}

		return  nouvelleListe;
	}

}


