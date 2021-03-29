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

public class ImageBille implements Dessinable{

	private static final long serialVersionUID = 1L;

	private String nomImage;
	private Image image;

	

	public ImageBille(String nomImage) {
		this.nomImage = nomImage;
		/*try {
			 image = ImageIO.read(new File(System.getProperty("user.home")+nomImage));
			 System.out.println("aaaaaaaaaaaaaaaaaaaaaaa"+nomImage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		
	}

	@Override
	public void dessiner(Graphics2D g2d) {
		File pngOriginal = new File(System.getProperty("user.home")+"\\ImageB2.png");
		File pngResized = new File(System.getProperty("user.home")+"\\ImageB.png");
		BufferedImage img = null;
		//redimImage(pngOriginal,pngResized,100,100,"png");
		try {
			img = ImageIO.read(new File(System.getProperty("user.home")+"\\ImageB.png"));
		} catch (IOException e) {

			e.printStackTrace();
		}
		Rectangle2D rect = new Rectangle2D.Double(0,0,100,100);
		//Rectangle2D rect = new Rectangle2D.Double(14,5.5,diametre,diametre);
		//Rectangle2D rect = new Rectangle2D.Double(14,5.5,diametre,diametre);
		//Rectangle2D rect = new Rectangle2D.Double(img.getWidth()-(img.getWidth()/2),img.getHeight()+(img.getHeight()/2),diametre,diametre);
		//Rectangle2D rect = new Rectangle2D.Double(position.getX()-15,position.getY()-3,diametre,diametre);
		TexturePaint texturePaintBille = new TexturePaint(img,rect);
		g2d.setPaint(texturePaintBille);
		Rectangle2D rect2 = new Rectangle2D.Double(0,0,100,100);
		g2d.fill(rect2);
		
		g2d.drawImage(image,0,0,null);

	}
	public void changerImage(String nomImageBille) {
		this.nomImage = nomImageBille;
		
		
	}

	public String getNomImage() {
		
		return nomImage;
	}
}
