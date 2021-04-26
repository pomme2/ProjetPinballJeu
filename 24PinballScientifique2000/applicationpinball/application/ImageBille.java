package application;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.TexturePaint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFileFormat.Type;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dessinable.Dessinable;
/**
 * Classe qui permet de gerer l'image de la bille
 * @author Audrey Viger
 *
 */
public class ImageBille implements Dessinable{

	private static final long serialVersionUID = 1L;

	private String nomImage;
	private Image image;
	private Dessin dessin;
	private boolean dessinerImage;
	private int largHaut = 100;
	java.net.URL urlBilleBlanc = getClass().getClassLoader().getResource("Blanc.png");

	
/**
 * Constructeur pour le nom de l'image
 * @param nomImage est le nom de l'image
 */
	public ImageBille(String nomImage) {
		this.nomImage = nomImage;
		dessin = new Dessin();

	}
/**
 * Methode permettant d'appliquer une texture sur l'image de la bille
 * @param g2d le contexte graphique
 */
	@Override
	public void dessiner(Graphics2D g2d) {
		dessinerImage = dessin.dessinImage();
		File pngOriginal = new File(System.getProperty("user.home")+"\\ImageB2.png");
		File pngResized = new File(System.getProperty("user.home")+"\\ImageB.png");
		BufferedImage img = null;

		try {
				img = ImageIO.read(new File(System.getProperty("user.home")+nomImage));
		
		} catch (IOException e) {

			e.printStackTrace();
		}
		Rectangle2D rect = new Rectangle2D.Double(0,0,largHaut,largHaut);
		TexturePaint texturePaintBille = new TexturePaint(img,rect);
		g2d.setPaint(texturePaintBille);
		Rectangle2D rect2 = new Rectangle2D.Double(0,0,largHaut,largHaut);
		g2d.fill(rect2);
		
		g2d.drawImage(image,0,0,null);

	}
	/**
	 * methode qui modifie le nom de l'image
	 * @param nomImageBille est le nom de l'image 
	 */
	public void changerImage(String nomImageBille) {
		this.nomImage = nomImageBille;
		
		
	}
/**
 * Methode qui retourne le nom de l'image
 * @return le nom de l'image
 */
	public String getNomImage() {
		
		return nomImage;
	}
}
