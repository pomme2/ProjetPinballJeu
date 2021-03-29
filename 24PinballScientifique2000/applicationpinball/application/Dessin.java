package application;


import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
 *
 * @author Audrey Viger
 *
 */
public class Dessin extends JPanel  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Image in which we're going to draw
	private Image imageBille;
	// Graphics2D object ==> used to draw on
	private Graphics2D g2;
	// Mouse coordinates
	private int currentX, currentY, oldX, oldY;
	private int diametre,largeurCercle = 10;

	private Color couleur;

	private Ellipse2D.Double cercle;
	private Rectangle2D.Double rectangle;
	
	private Area aireCercle,aireRect;
	
	String nomDossier = "imageBille";
	private String nomImage = "imageBille";

	public Dessin() {
		
		cercle = new Ellipse2D.Double(585/2-285,621/2-285,570,570);
		//cercle = new Ellipse2D.Double(250,300,100,100);
		rectangle = new Rectangle2D.Double(0, 0, 585, 621);
		

		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
			//	if(ImageSelectionne) {
					System.out.println("X: "+e.getX()+" cliqué "+" Y: "+e.getY()+" cliqué");
				//}
			}
		});
		setDoubleBuffered(false);
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				// save coord x,y when mouse is pressed
				oldX = e.getX();
				oldY = e.getY();
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				// coord x,y when drag mouse
				currentX = e.getX();
				currentY = e.getY();
				


				if (g2 != null) {
					// draw line if g2 context not null
					g2.setStroke(new BasicStroke(diametre));
					g2.drawLine(oldX, oldY, currentX, currentY);
					// refresh draw area to repaint
					repaint();
					// store current coords x,y as olds x,y
					oldX = currentX;
					oldY = currentY;
					AffineTransform mat= g2.getTransform();
					g2.setStroke(new BasicStroke(largeurCercle));
					
					g2.draw(cercle);
					g2.setTransform(mat);

				}
			}
		});
		
		}
		


protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	Graphics2D g2d = (Graphics2D)g;
	
	
	

	if (imageBille == null) {
		
		imageBille = createImage(getSize().width, getSize().height);
		g2 = (Graphics2D) imageBille.getGraphics();
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
		clear();
	}

	g.drawImage(imageBille, 0, 0, null);
	
	
}


public void clear() {
	g2.setPaint(Color.white);
	g2.fillRect(0, 0, getSize().width, getSize().height);
	g2.setPaint(Color.black);
	repaint();
}

public void setCouleur(Color couleur) {
	g2.setPaint(couleur);
	repaint();
}
public void setDiametre(int diametre) {
	this.diametre = diametre;
	repaint();
}
public void sauvegarderImage(String name,String type)  {
	BufferedImage imageBille = new BufferedImage(getWidth(),getHeight(), BufferedImage.TYPE_INT_RGB);

	Graphics2D g2 = imageBille.createGraphics();
	paint(g2);

	try{
		ImageIO.write(imageBille, type, new File(System.getProperty("user.home"),name+"."+type));
	} catch (Exception e) {
		e.printStackTrace();
	}
	
}



}
