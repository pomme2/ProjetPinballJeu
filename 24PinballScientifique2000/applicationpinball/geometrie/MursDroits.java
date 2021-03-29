package geometrie;					
import java.awt.Graphics;
import java.awt.Graphics2D;
/**
 * @author Thomas Bourgault
 */
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

import dessinable.Dessinable;
/**
 * 
 * @author Thomas Bourgault
 *Cette classe permet de cr�er des line2D.Double g2d invisibles qu'on superpose sur l'image du terrain de pinball
 */

public class MursDroits implements Dessinable  {
	
		private Line2D.Double MurDroit;
		private double pixelParMetre=1;
		private double coordX1,coordY1,coordX2,coordY2;
		/**
		 * Permet d'instancier un objet de type MursDroits
		 * @param coordX1 est la premi�re coordonn�e en x de la ligne. Elle est en double.
		 * @param coordY1 est la premi�re coordonn�e en y de la ligne. Elle est en double.		
		 * @param coordX2 est la deuxi�me coordonn�e en x de la ligne. Elle est en double.
		 * @param coordY2 est la deuxi�me coordonn�e en y de la ligne. Elle est en double.
		 */
		public MursDroits(double coordX1,double coordY1,double coordX2,double coordY2) {
			this.coordX1=coordX1;
			this.coordY1=coordY1;
			this.coordX2=coordX2;
			this.coordY2=coordY2;
			creerLaGeometrie();			
		}
		/**
		 * M�thode priv�e qui permet de cr�er la g�om�trie de la ligne avec toutes ses param�tres.
		 */
		private void creerLaGeometrie() {
			MurDroit=new Line2D.Double(coordX1,coordY1,coordX2,coordY2);
		}
		/**
		 * Dessine les formes constituant l'objet. Dans ce contexte, il dessine un objet de type Murs
		 * @param g2d Contexte graphique du composant sur lequel dessiner
		 */
		public void dessiner(Graphics2D g2d) {
			AffineTransform mat= new AffineTransform();
			mat.scale(pixelParMetre,pixelParMetre);
			g2d.draw(mat.createTransformedShape(MurDroit));				
		}
		/**
		 * M�thode qui permet de changer la variable pixelsParMetre
		 * @param pixelsParMetre est un double qui exprime le nombre de pixel par m�tre de l'image qui est inclut dans zonePinball
		 */
		public void setPixelsParMetre(double pixelsParMetre) {
			this.pixelParMetre = pixelsParMetre;									
			
		}
		/**
			 * M�thode qui retourne la premi�re coordonn�e en x d'un MursDroits
			 * @return la premi�re coordonn�e en x
			 */
		public double getCoordX1() {
			return coordX1;
		}
		/**
		 * M�thode qui change la premi�re coordonn�e en x d'un MursDroits
		 * @param la premi�re coordonn�e en x
		 */
		public void setCoordX1(double coordX1) {
			this.coordX1 = coordX1;
		}
		/**
		 * M�thode qui retourne la premi�re coordonn�e en y d'un MursDroits
		 * @return la premi�re coordonn�e en y
		 */
		public double getCoordY1() {
			return coordY1;
		}
		/**
		 * M�thode qui change la premi�re coordonn�e en y d'un MursDroits
		 * @param la premi�re coordonn�e en y
		 */
		public void setCoordY1(double coordY1) {
			this.coordY1 = coordY1;
		}
		/**
		 * M�thode qui retourne la deuxieme coordonn�e en x d'un MursDroits
		 * @return la deuxieme coordonn�e en x
		 */
		public double getCoordX2() {
			return coordX2;
		}
		/**
		 * M�thode qui change la deuxieme coordonn�e en x d'un MursDroits
		 * @param la deuxieme coordonn�e en x
		 */
		public void setCoordX2(double coordX2) {
			this.coordX2 = coordX2;
		}
		/**
		 * M�thode qui retourne la deuxieme coordonn�e en y d'un MursDroits
		 * @return la deuxieme coordonn�e en y
		 */
		public double getCoordY2() {
			return coordY2;
		}
		/**
		 * M�thode qui change la deuxieme coordonn�e en y d'un MursDroits
		 * @param la deuxieme coordonn�e en y
		 */
		public void setCoordY2(double coordY2) {
			this.coordY2 = coordY2;
		}
		/**
		 * Methode qui permet de retourner un objet de type MursDroits
		 * @return une line2D de type MursDroits
		 */
		public Line2D getLine() {
			// TODO Auto-generated method stub
			return MurDroit;
		}

		}	
		
		


