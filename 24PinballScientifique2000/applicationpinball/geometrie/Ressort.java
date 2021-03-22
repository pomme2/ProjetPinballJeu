package geometrie;
import moteur.MoteurPhysique;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import dessinable.Dessinable;
import moteur.MoteurPhysique;
import geometrie.Vecteur2D;
/**
 * Classe qui permet de dessiner le ressort et sa base
 * Un bloc ressort mémorise sa masse, sa largeur, sa hauteur, sa position, sa vitesse, son accélération,
 * la somme des forces qui s'applique sur elle sous forme vectorielle
 */
public class Ressort implements Dessinable {
	private double massePourCetteScene = 1;
	private double largeur = 1;
	private double hauteur = 1;
	private double hauteurBloc, hauteurIni;
	private double distanceZigzag;
	private double positionZigzagX, positionZigzagY, positionX, positionY;
	private Rectangle2D.Double bloc,base;
	private Path2D.Double zigzag;
	
	private Vecteur2D position, positionRepos;
	private Vecteur2D vitesse = new Vecteur2D(0,0);
	private Vecteur2D accel = new Vecteur2D(0,0);
	private Vecteur2D ff=new Vecteur2D(0,0),fr=new Vecteur2D(0,0), fg= new Vecteur2D(0,0), fn = new Vecteur2D(0,0);
	private Vecteur2D vitesseInitiale;
	
	
	private double pixelsParMetre = 1;
	private boolean arrete = false;
	
	private double kRessort,mu;
	
	
	private final double NB_ZIGZAG = 13.0;
	private final double HAUTEUR_BASE=0.2;
	private final double LARGEUR_BASE = 0.097;
	private final double POSITION_BASE = 1.006;
	
	private MursDroits ligneRessort ;
	private double coordX1Ligne = 1.008, coordYLigne = 1.27, coordX2Ligne = 1.094;
	/**
	 * Créer un bloc et un ressort et choisir sa position, choisir sa largeur
	 * et choisir sa hauteur
	 * @param position Vecteur incluant les positions en x et y du coin superieur-gauche
	 * @param largeur La largeur du bloc
	 * @param hauteur La hauteur du bloc
	 */
	
	public Ressort(Vecteur2D position, double largeur, double hauteur) {
		this.position = new Vecteur2D(position);
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.positionRepos = position;
		creerLaGeometrie();
	}
	/**
	  * Méthode privée pour créer les formes qui composent le bloc ressort
	 */
	private void creerLaGeometrie() {
		
		ligneRessort = new MursDroits(coordX1Ligne,coordYLigne,coordX2Ligne,coordYLigne);
		hauteurBloc = 0.024;
		
		positionY = position.getY();
		positionX = position.getX();
		
		hauteurIni = 0.19;
		hauteur = hauteurIni -( position.getY()-positionRepos.getY());
					
		
		bloc = new Rectangle.Double(position.getX(),position.getY(),largeur,hauteurBloc);
		base = new Rectangle.Double(POSITION_BASE,1.466,LARGEUR_BASE,HAUTEUR_BASE);
		zigzag = new Path2D.Double();
		ligneRessort = new MursDroits(position.getX(),position.getY(),position.getX()+largeur,position.getY());
		
		
		
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
		
		System.out.println("posY"+position.getY()+" "+position.getX());
		
		/**
		 * Permet de dessiner le bloc ressort, sur le contexte graphique passe en parametre.
		 * @param g2d contexte graphique
		 */	
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

		g2dPrive.setColor(Color.green);
	
		//System.out.println("rrrrrrrrrrrrr"+kRessort);



		
		
	}
	public void avancerUnPas(double deltaT) {
		Vecteur2D etirement, sommeForces, positionAvantIteration;
		positionAvantIteration = new Vecteur2D(position);
		etirement = Vecteur2D.soustrait(position, positionRepos);
		ff = MoteurPhysique.calculForceFriction(mu, massePourCetteScene, vitesse);
		fr = MoteurPhysique.calculForceRappel(kRessort, etirement);
		fn = MoteurPhysique.calculForceNormale(massePourCetteScene);
		fg = MoteurPhysique.calculForceGrav(massePourCetteScene);
		sommeForces = fr.additionne(fg).additionne(fn);
		try {
			accel = MoteurPhysique.calculAcceleration(sommeForces, massePourCetteScene);
		} catch (Exception e) {
			System.out.println("Erreur calcul accélération (masse nulle)");
		}
		vitesse = MoteurPhysique.calculVitesse(deltaT, vitesse, accel);
		position = MoteurPhysique.calculPosition(deltaT, position, vitesse);
		creerLaGeometrie();

		verifierArret(positionAvantIteration);

	}
	
	
	/**
	 * Classe permeant de changer les valeurs de mouvements et de positions à 0
	 * si le bloc-ressort est presque arrêté.
	 * 
	 * @param positionInitiale
	 */
	private void verifierArret(Vecteur2D positionInitiale) {
		if (position.equals(positionRepos) && position.equals(positionInitiale)) {
			accel = new Vecteur2D(0,0);
			vitesse = new Vecteur2D(0,0);
			position = new Vecteur2D(positionRepos);
			arrete = true;
			calculerForceRessort();
			calculerForceFriction();
			
		} else {
			arrete = false;
		}
	}
	/**
	 * Méthode privée qui recalcule les forces suite à un changement de masse
	 */
	
	private void calculerForcesMasse() {
		if (vitesse.getX() == 0 || vitesse.getX() == -0.0000001) {
			ff = new Vecteur2D(0,0);
		} else {
			ff = MoteurPhysique.calculForceFriction(mu, massePourCetteScene, vitesse);
		}
		fn = MoteurPhysique.calculForceNormale(massePourCetteScene);
		fg = MoteurPhysique.calculForceGrav(massePourCetteScene);
	}
	/**
	 * Méthode privée qui recalcule la force du ressort suite à un changement
	 * de la constante de rappel ou de l'étirement du ressort
	 */
	private void calculerForceRessort() {
		Vecteur2D etirement = Vecteur2D.soustrait(position, positionRepos);
		fr = MoteurPhysique.calculForceRappel(kRessort, etirement);
	}
	
	/**
	 * Modifie le facteur permettant de passer des metres aux pixels lors du dessin
	 * Ainsi on peut exprimer tout en m,  m/s  et m/s2
	 * @param pixelsParMetre Facteur de mise à l'échelle lors du dessin
	 */
	public void setPixelsParMetre(double pixelsParMetre) {
		this.pixelsParMetre = pixelsParMetre;
								
	}
	/**
	 * Modifie la position du bloc
	 * @param pos Vecteur incluant les positions en x et y 
	 */
	public void setPosition(Vecteur2D pos) {
		this.position = new Vecteur2D(pos);
		creerLaGeometrie();
		calculerForceRessort();
		calculerForceFriction();
	}
	/**
	 * Retourne la position courante
	 * @return la position courante
	 */
	public Vecteur2D getPosition() {
		return (position);
	}
	/**
	 * Modifie la vitesse courante du bloc
	 * @param vitesse Vecteur incluant les vitesses en x et y 
	 */
	//Audrey Viger
	public void setVitesse(Vecteur2D vitesse) {
		//on fait une copie du vecteur passé en paramètre 
		this.vitesse = new Vecteur2D(vitesse);
		vitesseInitiale = new Vecteur2D(vitesse);
	}
	/**
	 * Retourne la vitesse courante
	 * @return la vitesse courante
	 */
	//Audrey Viger
	public Vecteur2D getVitesse() {
		return (vitesse);
	}
	/**
	 * Associe une acceleration, ou modifie l'acceleration courante du bloc
	 * @param accel Vecteur incluant les accelerations en x et y 
	 */
	//Audrey Viger
	public void setAccel(Vecteur2D accel) {
		this.accel = new Vecteur2D(accel);
	}
	/**
	 * Retourne l'acceleration courante
	 * @return acceleration courante
	 */
	//Audrey Viger
	public Vecteur2D getAccel() {
		return (accel);
	}
	
	/**
	 * Modifie la masse 
	 * @param masseEnKg La masse exprimee en kg
	 */
	//Audrey Viger
	public void setMasseEnKg(double masseEnKg) {
		this.massePourCetteScene = masseEnKg;
		calculerForcesMasse();
	}
	/**
	 * Retourne la force de rappel
	 * @return La force de rappel
	 */
	public Vecteur2D getFr() {
		return fr;
	}
	//Audrey Viger
		public Vecteur2D getFf() {
			return ff;
		}
	/**
	 * Retourne la force gravitationnelle
	 * @return La force gravitationnelle
	 */
	public Vecteur2D getFg() {
		return fg;
	}

	/**
	 * Retourne la force normale
	 * @return La force normale
	 */
	// Zarine Ardekani
	public Vecteur2D getFn() {
		return fn;
	}
	/**
	 * Modifie la constante du ressort
	 * @param kRessort la constante du ressort
	 */
	//Audrey Viger
	public void setkRessort(double kRessort) {
		this.kRessort = kRessort;
		calculerForceRessort();
		}

	/**
	 * Vérifie si arrete est à true ou à false.
	 * S'il est à true, on retourne true.
	 * S'il est à false, on retourne false.
	 * @return le boolean de arrete.
	 */
	
	public boolean isArrete() {
		if (arrete == true)
			return true;
		return false;
	}
	public void setMu(double COEFF_FROT) {
		this.mu = mu;
		calculerForceFriction();
		
	}
	private void calculerForceFriction() {
		if (vitesse.getX() == 0 || vitesse.equals(vitesseInitiale)) {
			ff = new Vecteur2D(0,0);
		} else {
			ff = MoteurPhysique.calculForceFriction(mu, massePourCetteScene, vitesse);
		}
		
	}
	 
	//Carlos
	public double getMursY() {
		
		
		return position.getY();
		
		
	}
	

	
	public MursDroits getMurs() {
		
		   MursDroits ligneRessortTest=new MursDroits(position.getX(),position.getY(),position.getX()+largeur,position.getY());
	        return ligneRessortTest;
	}
	
	

}

