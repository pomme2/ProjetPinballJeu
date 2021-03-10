package dessinable;

import java.awt.Graphics2D;

/**
 * Interface qui d�finit la m�thode (ou possiblement les m�thodes) qu'un objet dessinable
 * doit impl�menter.
 *  
 * @author Thomas Bourgault
 *
 */

public interface Dessinable {
	
	/**
	 * Dessine les formes constituant l'objet.
	 * Doit s'assurer de ne pas modifier le contexte graphique
	 * 
	 * @param g2d Contexte graphique du composant sur lequel dessiner
	 */
	public void dessiner(Graphics2D g2d);
}
