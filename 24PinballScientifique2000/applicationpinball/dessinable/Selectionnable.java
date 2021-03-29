package dessinable;

/**
 * Interface qui d�finit la m�thode (ou possiblement les m�thodes) qu'un objet doit impl�menter pour pouvoir
 * �tre s�lectionn� dans un composant de dessin
 *  
 * @author Caroline Houle
 *
 */

public interface Selectionnable {
	
	/**
	 * Retourne vrai si le point pass� en param�tre fait partie de l'objet dessinable
	 * sur lequel cette methode sera appel�e
	 * 
	 * 
	 * @param xPix Coordonn�e en x du point (exprim� en pixels) 
	 * @param yPix Coordonn�e en y du point (exprim� en pixels)
	 */
	public boolean contient(double xPix, double yPix);

	public boolean contientCercle(double xPix, double yPix);
	public boolean contientTriangle(double xPix, double yPix);
	public boolean contientRectangle(double xPix, double yPix);
	public boolean contientCarre(double xPix, double yPix);
	
}
