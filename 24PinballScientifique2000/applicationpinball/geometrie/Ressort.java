package geometrie;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import dessinable.Dessinable;
/**
 * 
 * @author Audrey Viger
 *
 */

public class Ressort implements Dessinable {
	private double largeur = 1;
	private double hauteur = 1;
	private double hauteurBloc;
	private double distanceZigzag;
	private double positionZigzagX, positionZigzagY, positionX, positionY;
	private Rectangle2D.Double bloc,base;
	private Path2D.Double zigzag;
	
	private Vecteur2D position, positionRepos;
	private Vecteur2D vitesse = new Vecteur2D(0,0);
	private Vecteur2D accel = new Vecteur2D(0,0);
	private Vecteur2D vitesseInitiale;
	
	private double pixelsParMetre = 1;
	
	private double kRessort;
	
	
	private final double NB_ZIGZAG = 13.0;
	private final double HAUTEUR_BASE=0.2;
	private final double LARGEUR_BASE = 0.097;
	private final double POSITION_BASE = 1.006;

	public Ressort(Vecteur2D position, double largeur, double hauteur) {
		this.position = new Vecteur2D(position);
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.positionRepos = position;
		creerLaGeometrie();
	}
	private void creerLaGeometrie() {
		hauteurBloc = hauteur/8;
		bloc = new Rectangle.Double(position.getX(),position.getY(),largeur,hauteurBloc);
		base = new Rectangle.Double(POSITION_BASE,1.466,LARGEUR_BASE,HAUTEUR_BASE);
		zigzag = new Path2D.Double();
		
		positionY = position.getY();
		positionX = position.getX();
		hauteur = hauteur-(position.getY()-1.272);
		positionZigzagY = position.getY()+hauteurBloc;
		distanceZigzag = (hauteur)/NB_ZIGZAG;
		zigzag.moveTo(positionX,positionZigzagY);
		positionZigzagX = positionX;
		
		
		for(int i=2; i<NB_ZIGZAG+1;i++) {
			if(positionZigzagX==positionX) {
				positionZigzagY=i*distanceZigzag;
				zigzag.lineTo(positionX+largeur, positionY+positionZigzagY);
				positionZigzagX=positionX+largeur;
			}else {
				positionZigzagY=i*distanceZigzag;
				zigzag.lineTo(positionX, positionY+positionZigzagY);
				positionZigzagX=positionX;
			}
		}
		//zigzag.lineTo(positionX+largeur/2,positionY-distanceZigzag);
		
		
		
		
	}
	@Override
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dPrive = (Graphics2D) g2d.create();
		AffineTransform mat = new AffineTransform();
		mat.scale(pixelsParMetre,pixelsParMetre);
		Color rose = new Color(255,0,255);
		g2dPrive.setColor(rose);
		g2dPrive.fill(mat.createTransformedShape(bloc));
		g2dPrive.draw(mat.createTransformedShape(zigzag));
		g2dPrive.setColor(Color.white);
		g2dPrive.fill(mat.createTransformedShape(base));
		
		
	}
	public void setPixelsParMetre(double pixelsParMetre) {
		this.pixelsParMetre = pixelsParMetre;
								
	}
	public void setPosition(Vecteur2D pos) {
		this.position = new Vecteur2D(pos);
	}
	public Vecteur2D getPosition() {
		return (position);
	}
	/**
	 * Modifie la constante du ressort
	 * @param kRessort la constante du ressort
	 */
	//Audrey Viger
	public void setkRessort(double kRessort) {
		this.kRessort = kRessort;
		}

}
