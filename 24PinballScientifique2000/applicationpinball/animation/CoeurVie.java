package animation;

import java.awt.Graphics;
import java.awt.Graphics2D;

import dessinable.Dessinable;

/**
 * Classe qui permet de dessiner 3 coeurs qui representeront le nombre de vie du joueur, 
 * si les trois coeurs disparaissent alors la partie est finie
 * @author ThomasBourgault
 *
 */
public class CoeurVie implements Dessinable {
private int x,y,longueur,largeur;
private int[] triangleY,triangleX;

public CoeurVie(int x,int y, int longueur, int largeur) {
	this.x= x;
	this.y=y;
	this.longueur=longueur;
	this.largeur=largeur;
	creerLaGeometrie();
	
	
}
private void creerLaGeometrie() {
	triangleX[0]= x - 2*longueur/18;
	triangleX[1]=  x + longueur + 2*longueur/18;
	triangleX[2]= (x - 2*longueur/18 + x + longueur + 2*longueur/18)/2;
	//triangleY[0]=
	/** triangleX = {
            x - 2*longueur/18,
            x + longueur + 2*longueur/18,
            (x - 2*longueur/18 + x + longueur + 2*longueur/18)/2};
    triangleY = { 
            y + largeur - 2*largeur/3, 
            y + largeur - 2*largeur/3, 
            y + largeur };
            */
}

@Override
public void dessiner(Graphics2D g2d) {
	g2d.fillOval(
            x - longueur/12,
            y, 
            longueur/2 + longueur/6, 
            largeur/2); 
    g2d.fillOval(
            x + longueur/2 - longueur/12,
            y,
            longueur/2 + longueur/6,
            largeur/2);
    g2d.fillPolygon(triangleX, triangleY, triangleX.length);
	
}
}
