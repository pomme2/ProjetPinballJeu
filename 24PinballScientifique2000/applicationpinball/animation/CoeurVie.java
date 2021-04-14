package animation;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import dessinable.Dessinable;

/**
 * Classe qui permet de dessiner 3 coeurs qui representeront le nombre de vie du joueur, 
 * si les trois coeurs disparaissent alors la partie est finie
 * @author Thomas Bourgault
 *
 */
public class CoeurVie implements Dessinable  {
	
	private Image imageCoeur,imageCoeurRedim;
	private URL UrlCoeur;
	private static int nombreCoeur;
	private int dimensionX=100,dimensionY=100;
	java.net.URL urlCoeur = getClass().getClassLoader().getResource("Coeur.png");
	private CoeurVie vie;

	public CoeurVie(URL urlCoeur2) {
		
			this.UrlCoeur=urlCoeur2;
			try {
				imageCoeur=ImageIO.read(UrlCoeur);
				imageCoeurRedim = imageCoeur.getScaledInstance(dimensionX, dimensionY, Image.SCALE_SMOOTH);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
			initialiserCoeur();

	}
	public void initialiserCoeur() {
		nombreCoeur=3;
	}

	@Override
	public void dessiner(Graphics2D g2d) {
		g2d.translate(25, 10);
		for(int i=0;i<this.nombreCoeur;i++) {
			g2d.drawImage(imageCoeurRedim, 0, 0, null);
			g2d.translate(100,0);						
		}

	}
	public void setNombreCoeur(int nouvNbr) {
		this.nombreCoeur=nouvNbr;
		
	}
	public int getNombreCoeur() {
		return this.nombreCoeur;
	}
	public static void perdVie() {
		nombreCoeur=nombreCoeur-1;
	}	
}
