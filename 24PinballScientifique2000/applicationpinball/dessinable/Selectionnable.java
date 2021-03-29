package dessinable;

/**
 * Interface qui définit la méthode (ou possiblement les méthodes) qu'un objet doit implémenter pour pouvoir
 * être sélectionné dans un composant de dessin
 *  
 * @author Caroline Houle
 *
 */

public interface Selectionnable {
	
	/**
	 * Retourne vrai si le point passé en paramètre fait partie de l'objet dessinable
	 * sur lequel cette methode sera appelée
	 * 
	 * 
	 * @param xPix Coordonnée en x du point (exprimé en pixels) 
	 * @param yPix Coordonnée en y du point (exprimé en pixels)
	 */
	public boolean contient(double xPix, double yPix);

	public boolean contientCercle(double xPix, double yPix);
	public boolean contientTriangle(double xPix, double yPix);
	public boolean contientRectangle(double xPix, double yPix);
	public boolean contientCarre(double xPix, double yPix);
	
}
