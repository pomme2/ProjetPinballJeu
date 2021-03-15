

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
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
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

	// Image in which we're going to draw
	private Image image;
	// Graphics2D object ==> used to draw on
	private Graphics2D g2;
	// Mouse coordinates
	private int currentX, currentY, oldX, oldY;
	private int diametre;

	private Color couleur;

	private Ellipse2D.Double cercle;
	private Rectangle2D.Double rectangle;
	
	private Area aireCercle,aireRect;

	public Dessin() {
		cercle = new Ellipse2D.Double(585/2-200,621/2-200,400,400);
		rectangle = new Rectangle2D.Double(0, 0, 585, 621);
		
		

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
				}
			}
		});
		
		}
		


protected void paintComponent(Graphics g) {
	if (image == null) {
		// image to draw null ==> we create
		image = createImage(getSize().width, getSize().height);
		g2 = (Graphics2D) image.getGraphics();
		// enable antialiasing
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// clear draw area
		clear();
	}
	/*g2.fill(rectangle);
	g2.fill(cercle);
	
	Area aireCercle = new Area(cercle);
	Area aireRect = new Area(rectangle);  
	
	aireRect.subtract(aireCercle);*/
	g.drawImage(image, 0, 0, null);
	
	
}

// now we create exposed methods
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
public void saveImage(String name,String type) {
	BufferedImage image = new BufferedImage(getWidth(),getHeight(), BufferedImage.TYPE_INT_RGB);
	Graphics2D g2 = image.createGraphics();
	paint(g2);
	try{
		ImageIO.write(image, type, new File(name+"."+type));
	} catch (Exception e) {
		e.printStackTrace();
	}
}

}
