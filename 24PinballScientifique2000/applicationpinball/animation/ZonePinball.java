package animation;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import java.awt.TexturePaint;

import java.awt.Shape;

import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.geom.PathIterator;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import application.App24PinballScientifique2001;
import application.FenetreBacSable;
import application.FenetreJouer;
import application.FenetreOption;
import application.Musique;
import dessinable.OutilsImage;
import geometrie.Aimant;
import geometrie.Bille;
import geometrie.FlecheVectorielle;
import geometrie.Flipper;
import geometrie.Murs;
import geometrie.MursCourbes;
import geometrie.MursDroits;
import geometrie.ObstacleClique;
import geometrie.Ressort;
import geometrie.Vecteur2D;
import moteur.MoteurPhysique;

import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**Classe qui effectue l'animation du jeu de pinball scientifique. 
 * 
 * @author Thomas Bourgault
 * @author Audrey Viger
 * @author Carlos Eduardo
 *
 */

public class ZonePinball extends JPanel implements Runnable {
	private static final long serialVersionUID = 1 ;
	
	
	
	//objet de type Scene
	Scene scene;
	public boolean coeurVie;
	public FenetreBacSable fenetreBacSable;

	//Ressort Audrey
	private Ressort ressort;
	private final Vecteur2D positionInitialRessort = new Vecteur2D(1.009, 1.272);
	private final Vecteur2D VITESSE_INIT_RESSORT = new Vecteur2D(0, -0.0000001);
	private final Vecteur2D ACCEL_INIT_RESSORT = new Vecteur2D(0, 0);

	private final int TEMPS_DU_SLEEP = 10;
	private  double K_RESSORT = 50;
	private final double ETIREMENT_NAT = 0.1;

	private final double COEFF_FROT = 0.64;
	private final double MASSE_POUR_CETTE_SCENE = 0.7; // en kg
	private final double RAYON_COURBE = 0.505; //en m

	private double largeurRessort = 0.088;
	private double longueurRessort = 0.192;
	private Vecteur2D posCentre = new Vecteur2D(0.598, 0.712);

	//variable bille Carlos
	private double deltaT = 0.007;
	private double diametreBallePourCetteScene = 0.03; //em m�tres
	private double massePourCetteScene = 0.1; //en kg

	private Vecteur2D posInitBalle; //position intiale pour la balle
	private Vecteur2D vitInitBalle = new Vecteur2D(0, 0); //vitesse intiale pour la balle
	private Vecteur2D accelInitBalle = new Vecteur2D(0, 0); //acceleration intiale pour la balle

	//position intiales pour la bille
	private Bille uneBille;
	private boolean enCoursDAnimation = false;
	private double tempsTotalEcoule = 0;
	private int tempsDuSleep = 10;


	//variable aimant
	private Aimant unAimant;
	boolean aimantActif = false;

	double aimantX = 0.32;
	double aimantY = 1.076;
	double aimantDiametre = 0.05;

	//variable pour la courbe
	double courbeX = 0.898;
	double courbeY=0.732;

	//variable pour pointage
	private PointageAnimation score = new PointageAnimation();	
	private int nbCollision =0;


	//tableau pour obstacles
	private ArrayList < Murs > obstaclesCercle = new ArrayList < Murs > ();
	private ArrayList < Murs > obstaclesCercleAjou = new ArrayList < Murs > ();


	//tab pour mursHorizontales (sol)
	private ArrayList < MursDroits > solHorizontal = new ArrayList < MursDroits > ();

	//tab pour les pentes
	private ArrayList < MursDroits > pentes = new ArrayList < MursDroits > ();

	//tab pour les mursSous les triangles
	private ArrayList < MursDroits > droitSous = new ArrayList < MursDroits > ();

	//tab pour les cotes des triangles
	private ArrayList < MursDroits > coteTriangle = new ArrayList < MursDroits > ();

	//tab pour les murs line2D sur la courbe
	private ArrayList < MursDroits > courbe = new ArrayList < MursDroits > ();


	private ArrayList<Rectangle2D> obstaclesCarre = new ArrayList<Rectangle2D>();
	//tab pour les murs

	ArrayList < MursDroits > murs = new ArrayList < MursDroits > ();

	//liste des coordonnes en X et Y des courbes
	ArrayList < Double > arcCercleGauCoordX = new ArrayList < Double > ();
	ArrayList < Double > arcCercleGauCoordY = new ArrayList < Double > ();
	ArrayList < Double > arcCercleDroitCoordX = new ArrayList < Double > ();
	ArrayList < Double > arcCercleDroitCoordY = new ArrayList < Double > ();
	ArrayList < Double > arcCerclePetitCoordX = new ArrayList < Double > ();
	ArrayList < Double > arcCerclePetitCoordY = new ArrayList < Double > ();

	//tab avec flippers

	private ArrayList < MursDroits > flipperGauche = new ArrayList < MursDroits > ();

	private ArrayList < MursDroits > flipperDroit = new ArrayList < MursDroits > ();

	private boolean premiereFois = true;

	//Image et pixelParMetre
	private int dimensionImageX = 600, dimensionImageY = 768;
	private double largeurDuComposantMetre = 1.2;
	private double hauteurDuComposantMetre = 1.536;
	private double pixelParMetre = 500;
	private Image imageTerrainPinball1;
	//4 cercles
	private Murs cercleMauveBas, cercleMauveHautGauche, cercleMauveHaut, cercleMauveHautDroit;
	private double coordXBas = 0.526, coordYBas = 0.7865, coordXHautGauche = 0.340, coordYHautGauche = 0.458, coordXHaut = 0.546, coordYHaut = 0.3145, coordXHautDroit = 0.719, coordYHautDroit = 0.462;
	private double diametre = 0.105;
	//2 triangles
	//Triangle de gauche:
	private MursDroits ligTriGaucheGau, ligTriGaucheDroit, ligTriGaucheBas;
	private double coordX1TriGauche = 0.192, coordY1TriGauche = 0.778, coordX2TriGauche = 0.316, coordY2TriGauche = 0.898, coordX3TriGauche = 0.194, coordY3TriGauche = 0.896;
	//Triangle de droite:
	private MursDroits ligTriDroitGau, ligTriDroitDroit, ligTriDroitBas;
	private double coordX1TriDroite = 0.926, coordY1TriDroite = 0.83, coordX2TriDroite = 0.922, coordY2TriDroite = 1.044, coordX3TriDroite = 0.814, coordY3TriDroite = 1.048;

	//Coin gauche terrain pinball	
	private MursDroits ligneDroitHautGau, ligneDroitTrapezeGau, lignePencheTrapezeGau, ligneDroitBasGau, lignePetiteHautGau;
	private double coordX0CoinGauche = 0.09, coordY0CoinGauche = 0.71, coordX1CoinGauche = 0.06, coordY1CoinGauche = 0.71, coordX2CoinGauche = 0.058, coordY2CoinGauche = 1.11, coordX3CoinGauche = 0.06, coordY3CoinGauche = 1.11, coordX4CoinGauche = 0.494, coordY4CoinGauche = 1.386, coordX5CoinGauche = 0.494, coordY5CoinGauche = 1.532;

	//Coin droit terrain pinball
	private MursDroits ligneDroitTrapezeDroite, lignePencheTrapezeDroite, ligneDroitBasDroite;
	private double coordX1CoinDroit = 0.662, coordY1CoinDroit = 1.532, coordX2CoinDroit = 0.662, coordY2CoinDroit = 1.388, coordX3CoinDroit = 0.93, coordY3CoinDroit = 1.21, coordX4CoinDroit = 1, coordY4CoinDroit = 1.21, coordX5CoinDroit = 1, coordY5CoinDroit = 0.942;

	//Grande courbe haut:
	private MursCourbes arcCerclegau, arcCercleDroit, arcCerclePetit;
	private double coordX1CourbeGau = 0.09, coordY1CourbeGau = 0.71, controleXGau = 0.16, controleYGau = 0.16, coordX3CourbeGau = 0.584, coordY3CourbeGau = 0.1;
	private double coordX1CourbeDroit = 0.584, coordY1CourbeDroit = 0.1, controleX = 1.01, controleY = 0.12, coordX3CourbeDroit = 1.094, coordY3CourbeDroit = 0.708;
	private double controleXPetit = 1, controleYPetit = 0.6, coordX1CourbePetit = 0.9, coordY1CourbePetit = 0.398;
	private Path2D.Double segmentCourbeGauche, segmentCourbeDroit, segmentCourbePetit;

	//Mur gauche et droit tunnel ressort
	private MursDroits tunnelRessortDroite, tunnelRessortGauche;
	private double coordX1TunnelGauche = 1.006, coordY1TunnelGauche = 1.534, coordX2TunnelGauche = 1.01, coordY2TunnelGauche = 0.784, coordX1TunnelDroit = 1.096, coordY1TunnelDroit = 0.716, coordX2TunnelDroit = 1.096, coordY2TunnelDroit = 1.532;

	//Flippers

	private Flipper flipGauche,flipDroit;
	private double coordX1FlipperGauche=0.465,coordY1FlipperGauche=1.386,longueurMancheGauche=0.09,diametreMancheGauche=0.015;
	private Vecteur2D positionFlipperGauche=new Vecteur2D(coordX1FlipperGauche,coordY1FlipperGauche);
	private Vecteur2D positionFlipperGaucheInitial=new Vecteur2D(coordX1FlipperGauche,coordY1FlipperGauche);
	private double coordX1FlipperDroit=0.690,coordY1FlipperDroit=1.386;
	private Vecteur2D positionFlipperDroit=new Vecteur2D(coordX1FlipperDroit,coordY1FlipperDroit);
	private Vecteur2D positionFlipperDroitInitial=new Vecteur2D(coordX1FlipperDroit,coordY1FlipperDroit);
	private Vecteur2D vitesseInitialeFlipper=new Vecteur2D(0,0);
	private MursDroits murFlipperGauche,murFlipperDroit;
	private double coordX2MurFlipperGauche=0.465,coordY2MurFlipperGauche=1.3785,coordX1MurFlipperGauche=0.555,coordY1MurFlipperGauche=1.3785;
	private double coordX1MurFlipperDroit=0.6,coordY1MurFlipperDroit=1.3785,coordX2MurFlipperDroit=0.690,coordY2MurFlipperDroit=1.3785;
	private boolean gauche=true;
	private double angleMax=30,angleEquilibre=0,frequenceAngulaire=40;
	private double angleGauche,angleDroit;
	private double tempsEcouleGaucheMonter,tempsEcouleGaucheDescendre,tempsEcouleDroitDescendre,tempsEcouleDroitMonter;
	private boolean premierQuartPeriode=true;

	//image et booleans
	private boolean contour = false, ImageSelectionne = false, coord = false, gaucheActive = false, droitActive = false, gaucheDescente = false, droitDescente = false;
	java.net.URL urlPinballTerrain = getClass().getClassLoader().getResource("pinballTerrain.png");

	private ObstacleClique obstacle;
	private String forme;
	private boolean formeSelectionne = false;
	private double xPrecedent, yPrecedent;
	private double posXCarre = 0.3;
	private double posYCarre = 0.3;
	private double translatCarreX=0.01;
	private double translatCarreY=0.01;
	private double maxObstacleHaut = 1.26, maxObstacleGauche = 0.11, maxObstacleDroite = 1.05, maxObstacleBas = 0.15;

	//collision pour les obstacles ajoute
	private boolean collisionMax= true;
	private boolean col1= false;
	private boolean col2= false;
	private boolean col3= false;
	
	private boolean premiereFoisCercleTouche=true;

	private Path2D.Double echelle;
	private boolean dessinerAimant = false;

	private boolean apresRessort = false;

	//pause
	public static int scoreFinal = 0;
	private boolean pause = false;
	private boolean premiereFoisBille =true;
	private static String nomFichierSonFlipper=".//Ressource//sonFlipper1sec.wav"; 
	private static Musique musiqueFlipperGauche=new Musique (nomFichierSonFlipper);
	private static Musique musiqueFlipperDroit=new Musique (nomFichierSonFlipper);
	private Musique musiqueJouer;	 		
	private boolean jouerActive;

	//Portails
	private double compteurPortailGaucheSol=0;
	private double compteurPortailDroit=0;
	private int compteur=0;
	private int compteur2=0;
	private int variablePourCompter=1;
	private int variablePourCompter2=1;
	//Portail Gauche
	private double coordXPortailGaucheSol=0.195,coordYPortailGaucheSol=0.893,epaisseur=0.008, longueurPortailGaucheSol=0.122;
	private double coordXPortailGaucheDroit=0.191,coordYPortailGaucheDroit=0.770, longueurPortailGaucheDroit=0.114;
	//Portail Droit
	private double coordXPortailDroitSol=0.812,coordYPortailDroitSol=1.046,longueurPortailDroitSol=0.106;
	private double coordXPortailDroitDroit=0.924, coordYPortailDroitDroit=0.826,longueurPortailDroitDroit=0.218;
	//Trou courbe
	private double x1=0.161,y1=0.396,x2=0.12,y2=0.490,x3=0.10,y3=0.56;
	private double diametreTrou=0.05;
	private double lignex1=0.064,ligney1=0.71, longueur=0.024, hauteur=0.03;

	//boolean pour les obstacles
	private static boolean  premiereFoisBougerObstacle=true;
	private int nbClicObstacle=0;

	private int obstaclesMax1 =300, obstaclesMax2 = 600,obstaclesMax3 = 1000;
	
	private double triangleX1=103, triangleY1=602, triangleX2=30, triangleY2=555, triangleX3=30, triangleY3=602;
	private Path2D.Double petitTriangle;
	
	
	private double intensite=0;

	private FlecheVectorielle flecheVitesse, flecheAccel;


	//Thomas Bourgault 
	/**
	 * Constructeur qui g�re les diff�rents types d'�v�nements de la souris, permet l'initialisation de l'image ainsi que de la bille
	 * 
	 */
	public ZonePinball(Scene scene) {
		musiqueJouer=FenetreJouer.musiqueJouer();

		this.scene = scene;
		this.scene = new Scene();


		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_A) {
					repaint();
					musiqueFlipperGauche.reset();
					musiqueFlipperGauche.play();
					gaucheActive=true;
					gaucheDescente=false;					

				} else {
					if (e.getKeyCode() == KeyEvent.VK_D) {
						musiqueFlipperDroit.reset();
						musiqueFlipperDroit.play();
						droitActive = true;
						droitDescente = false;

						repaint();
					} else {
						if(e.getKeyCode() == KeyEvent.VK_ESCAPE && !pause){
							if(isAnimationEnCours() && jouerActive) {
								musiqueJouer.stop();
							}
							arreter();

							pause =true;

						}
						if(e.getKeyCode() == KeyEvent.VK_SPACE && pause){
							demarrer();
							if(isAnimationEnCours() &&  jouerActive) {
								musiqueJouer.play();
							}

							pause =false;

						}
					}
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_A) {

					gaucheActive = false;
					gaucheDescente = true;
					flipGauche.setVitesse(new Vecteur2D(0, 0));
					musiqueFlipperGauche.stop();

					repaint();
				} else {
					if (e.getKeyCode() == KeyEvent.VK_D) {
						droitActive = false;
						droitDescente = true;
						flipDroit.setVitesse(new Vecteur2D(0, 0));
						repaint();
						musiqueFlipperDroit.stop();

					}
				}
			}

		});

		ressort = new Ressort(positionInitialRessort, largeurRessort, longueurRessort);
		ressort.setkRessort(K_RESSORT);
		ressort.setMu(COEFF_FROT);
		ressort.setVitesse(VITESSE_INIT_RESSORT);
		ressort.setMasseEnKg(MASSE_POUR_CETTE_SCENE);
		ressort.setVitesse(VITESSE_INIT_RESSORT);

		flippers();

		posInitBalle = new Vecteur2D(ressort.getMurs().getCoordX1() + diametreBallePourCetteScene, ressort.getMursY() - diametreBallePourCetteScene + getEtirement());
		uneBille = new Bille(posInitBalle, diametreBallePourCetteScene);
		uneBille.setMasseEnKg(massePourCetteScene);



		unAimant = new Aimant(0.52, 0.546, 0.08);


		initialiseBille();

		gestionSourisObs();


		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				ImageSelectionne = true;
			}
			@Override
			public void mouseExited(MouseEvent e) {
				ImageSelectionne = false;
			}
			@Override
			public void mousePressed(MouseEvent e) {
				if (ImageSelectionne) {
					System.out.println("X: " + e.getX() / (dimensionImageX / largeurDuComposantMetre) + " cliqu� " + " Y: " + e.getY() / (dimensionImageY / hauteurDuComposantMetre) + " cliqu�");

					System.out.println("X: " + e.getX()  + " cliqu� " + " Y: " + e.getY()  + " cliqu�");


					//System.out.println("X: " + e.getX()  + " cliqu� " + " Y: " + e.getY()  + " cliqu�");


				}
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (ImageSelectionne && coord) {
					System.out.println("X: " + e.getX() / (dimensionImageX / largeurDuComposantMetre) + " Y: " + e.getY() / (dimensionImageY / hauteurDuComposantMetre));

				}
			}
		});
		setBackground(Color.gray);
		setLayout(null);

		Image imageTerrainPinballMauvaiseDim;
		try {
			imageTerrainPinballMauvaiseDim = ImageIO.read(urlPinballTerrain);
			imageTerrainPinball1 = imageTerrainPinballMauvaiseDim.getScaledInstance(dimensionImageX, dimensionImageY, Image.SCALE_SMOOTH);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	//Thomas Bourgault
	/**
	 * Methode de dessin du composant 
	 * @param g contexte graphique
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		double pixelParMetre = getWidth() / largeurDuComposantMetre;
		//Construction image									
		AffineTransform mat = new AffineTransform();
		double factReso = largeurDuComposantMetre / imageTerrainPinball1.getWidth(null);
		mat.scale(factReso * pixelParMetre, factReso * pixelParMetre);
		g2d.drawImage(imageTerrainPinball1, mat, null);
		g2d.setColor(Color.white);
		////////////////////////////////////////////////////////////////////////GAUCHE
		AffineTransform oldGauche = g2d.getTransform();
		AffineTransform trans = new AffineTransform();
		if (gaucheActive) {
			g2d.rotate(Math.toRadians(angleGauche),coordX1FlipperGauche*pixelParMetre,coordY1FlipperGauche*pixelParMetre);
		}
		if (gaucheDescente) {
			g2d.rotate(Math.toRadians(angleGauche),coordX1FlipperGauche*pixelParMetre,coordY1FlipperGauche*pixelParMetre);
		}
		g2d.transform(trans);
		flipGauche.dessiner(g2d);
		if (contour) {
			murFlipperGauche.dessiner(g2d);
		}

		g2d.setTransform(oldGauche);
		////////////////////////////////////////////////////////////////////////////////DROIT
		AffineTransform oldDroit = g2d.getTransform();
		if (droitActive) {
			g2d.rotate(Math.toRadians(angleDroit), coordX1FlipperDroit * pixelParMetre, coordY1FlipperDroit * pixelParMetre);

		}
		if (droitDescente) {
			g2d.rotate(Math.toRadians(angleDroit), coordX1FlipperDroit * pixelParMetre, coordY1FlipperDroit * pixelParMetre);
		}
		flipDroit.dessiner(g2d);
		if (contour) {
			murFlipperDroit.dessiner(g2d);
		}
		g2d.setTransform(oldDroit);

		////////////////////////////////////////////////////////////////////////////////
		AffineTransform matPortailGauche= new AffineTransform();


		g2d.setColor(Color.cyan);
		for(compteur=0;compteur<variablePourCompter;compteur++) {
			Ellipse2D.Double portailGaucheSol=new Ellipse2D.Double(coordXPortailGaucheSol,coordYPortailGaucheSol,longueurPortailGaucheSol+compteurPortailGaucheSol,epaisseur);
			Ellipse2D.Double portailGaucheDroit=new Ellipse2D.Double(coordXPortailGaucheDroit,coordYPortailGaucheDroit,epaisseur,longueurPortailGaucheDroit+compteurPortailGaucheSol);                               
			matPortailGauche.scale(pixelParMetre,pixelParMetre);
			g2d.fill(matPortailGauche.createTransformedShape(portailGaucheSol));
			g2d.fill(matPortailGauche.createTransformedShape(portailGaucheDroit));

		}
		AffineTransform matPortailDroit= new AffineTransform();
		g2d.setColor(Color.ORANGE);
		for(compteur2=0;compteur2<variablePourCompter2;compteur2++) {
			Ellipse2D.Double portailDroitSol= new Ellipse2D.Double( coordXPortailDroitSol, coordYPortailDroitSol,longueurPortailDroitSol+compteurPortailGaucheSol,epaisseur);
			Ellipse2D.Double portailDroitDroit= new Ellipse2D.Double(coordXPortailDroitDroit,coordYPortailDroitDroit,epaisseur,longueurPortailDroitDroit+compteurPortailGaucheSol);
			matPortailDroit.scale(pixelParMetre,pixelParMetre);
			g2d.fill(matPortailDroit.createTransformedShape(portailDroitSol));
			g2d.fill(matPortailDroit.createTransformedShape(portailDroitDroit));
		}
		g2d.setColor(Color.BLACK);
		AffineTransform matTrou1= new AffineTransform();
		Ellipse2D.Double trou1= new Ellipse2D.Double( x1, y1,diametreTrou,diametreTrou);
		matTrou1.scale(pixelParMetre,pixelParMetre);
		g2d.fill(matTrou1.createTransformedShape(trou1));

		AffineTransform matTrou2= new AffineTransform();
		Ellipse2D.Double trou2= new Ellipse2D.Double( x2, y2,diametreTrou,diametreTrou);
		matTrou2.scale(pixelParMetre,pixelParMetre);
		g2d.fill(matTrou2.createTransformedShape(trou2));

		AffineTransform matTrou3= new AffineTransform();
		Ellipse2D.Double trou3= new Ellipse2D.Double( x3, y3,diametreTrou,diametreTrou);
		matTrou3.scale(pixelParMetre,pixelParMetre);
		g2d.fill(matTrou3.createTransformedShape(trou3));

		AffineTransform matTrou4= new AffineTransform();
		Rectangle2D.Double trou4=new Rectangle2D.Double(lignex1,ligney1,longueur,hauteur);
		matTrou4.scale(pixelParMetre,pixelParMetre);
		g2d.fill(matTrou4.createTransformedShape(trou4));
		if (premiereFois) {
			//Construction 4 cercles

			cercle();
			//Construction 2 triangles
			//Triangle gauche:	
			triangleGauche();
			//Triangle droit:
			triangleDroit();
			//Coin gauche:
			coinGauche();
			//Coin droit:
			coinDroit();
			//Arc cercle haut gauche et droit/celui qui est petit:
			arcCercle();

			//Construction tunnel du ressort
			tunnel();
			//Segment petite courbe
			listeCourbe();


			SegmentCourbe();						
			petitTriangle=new Path2D.Double();
			petitTriangle.moveTo(triangleX1, triangleY1);
			petitTriangle.lineTo(triangleX2, triangleY2);
			petitTriangle.lineTo(triangleX3, triangleY3);
			petitTriangle.closePath();
			premiereFois = false;
			premiereFois = false;
		}
		g2d.setColor(Color.MAGENTA);
		g2d.fill(petitTriangle);
		changPositionFlipper();

		listeObstacle();

		ressort.setPixelsParMetre(pixelParMetre);
		ressort.dessiner(g2d);
		uneBille.setPixelsParMetre(pixelParMetre);
		uneBille.dessiner(g2d);
		

		flecheVitesse = new FlecheVectorielle(uneBille.getCenterX(),uneBille.getCenterY(),uneBille.getVitesse());
		flecheAccel = new FlecheVectorielle(uneBille.getCenterX(),uneBille.getCenterY(),uneBille.getAccel());		
		
		flecheVitesse.dessiner(g2d);
		flecheAccel.dessiner(g2d);
		
		if (dessinerAimant) {
			unAimant.setPixelsParMetre(pixelParMetre);
			unAimant.dessiner(g2d);
		}

		g2d.setColor(Color.yellow);
		obstacle = new ObstacleClique(posXCarre+translatCarreX,posYCarre+translatCarreY,0.1,0.1,forme);

		obstacle.setPixelsParMetre(pixelParMetre);

		if(collisionMax) {

			if(col1) g2d.setColor(new Color(255,0,0));

			if(col2) g2d.setColor(new Color(205,0,0));

			if(col3) g2d.setColor(new Color(153,0,0));

			obstacle.dessiner(g2d);

		}



		if (contour) {
			g2d.setColor(Color.green);
			//Les 4 cercles
			cercleMauveBas.dessiner(g2d);
			cercleMauveHautGauche.dessiner(g2d);
			cercleMauveHaut.dessiner(g2d);
			cercleMauveHautDroit.dessiner(g2d);
			//Triangle de gauche:
			ligTriGaucheGau.dessiner(g2d);
			ligTriGaucheBas.dessiner(g2d);
			ligTriGaucheDroit.dessiner(g2d);
			//Triangle de droit:
			ligTriDroitGau.dessiner(g2d);
			ligTriDroitBas.dessiner(g2d);
			ligTriDroitDroit.dessiner(g2d);
			//Coin gauche:
			ligneDroitHautGau.dessiner(g2d);
			ligneDroitTrapezeGau.dessiner(g2d);
			lignePencheTrapezeGau.dessiner(g2d);
			ligneDroitBasGau.dessiner(g2d);
			lignePetiteHautGau.dessiner(g2d);
			//Coin Droit:				
			ligneDroitTrapezeDroite.dessiner(g2d);
			lignePencheTrapezeDroite.dessiner(g2d);
			ligneDroitBasDroite.dessiner(g2d);
			//Arc cercle:
			arcCerclegau.dessiner(g2d);
			arcCercleDroit.dessiner(g2d);
			arcCerclePetit.dessiner(g2d);
			//tunnelle
			tunnelRessortDroite.dessiner(g2d);
			tunnelRessortGauche.dessiner(g2d);
		}

		if(pause) {
			g2d.setColor(Color.white);
			g2d.drawString("Pause",  getWidth()/2, getHeight()/2);
		}
		if (aimantActif) {
			g2d.setColor(Color.red);
			unAimant.dessiner(g2d);
		}
		g2d.setColor(Color.yellow);
		dessinerEchelle(g);


	}
	//Thomas Bourgault
	/**
	 * M�thode qui initialise les diff�rents objets de type Murs (des cercles)
	 */
	public void cercle() {
		cercleMauveBas = new Murs(coordXBas, coordYBas, diametre);
		cercleMauveBas.setPixelsParMetre(pixelParMetre);


		cercleMauveHautGauche = new Murs(coordXHautGauche, coordYHautGauche, diametre);
		cercleMauveHautGauche.setPixelsParMetre(pixelParMetre);

		Vecteur2D vitesseNegatif = new Vecteur2D(uneBille.getVitesse().getX() * -1, uneBille.getVitesse().getY());
		uneBille.setVitesse(vitesseNegatif);


		cercleMauveHaut = new Murs(coordXHaut, coordYHaut, diametre);
		cercleMauveHaut.setPixelsParMetre(pixelParMetre);

		cercleMauveHautDroit = new Murs(coordXHautDroit, coordYHautDroit, diametre);
		cercleMauveHautDroit.setPixelsParMetre(pixelParMetre);
	}
	//Thomas Bourgault
	/**
	 * M�thode qui initialise les diff�rents objets de type MursDroits pour le triangle gauche de l'image
	 */
	public void triangleGauche() {
		ligTriGaucheGau = new MursDroits(coordX1TriGauche, coordY1TriGauche, coordX3TriGauche, coordY3TriGauche);
		ligTriGaucheGau.setPixelsParMetre(pixelParMetre);
		ligTriGaucheBas = new MursDroits(coordX3TriGauche, coordY3TriGauche, coordX2TriGauche, coordY2TriGauche);
		ligTriGaucheBas.setPixelsParMetre(pixelParMetre);
		ligTriGaucheDroit = new MursDroits(coordX1TriGauche, coordY1TriGauche, coordX2TriGauche, coordY2TriGauche);
		ligTriGaucheDroit.setPixelsParMetre(pixelParMetre);
	}
	//Thomas Bourgault
	/**
	 * M�thode qui initialise les diff�rents objets de type MursDroits pour le triangle droit de l'image
	 */
	public void triangleDroit() {
		ligTriDroitGau = new MursDroits(coordX3TriDroite, coordY3TriDroite, coordX1TriDroite, coordY1TriDroite);
		ligTriDroitGau.setPixelsParMetre(pixelParMetre);
		ligTriDroitBas = new MursDroits(coordX2TriDroite, coordY2TriDroite, coordX3TriDroite, coordY3TriDroite);
		ligTriDroitBas.setPixelsParMetre(pixelParMetre);
		ligTriDroitDroit = new MursDroits(coordX1TriDroite, coordY1TriDroite, coordX2TriDroite, coordY2TriDroite);
		ligTriDroitDroit.setPixelsParMetre(pixelParMetre);
	}
	//Thomas Bourgault
	/**
	 * M�thode qui initialise les diff�rents objets de type MursDroits pour le coin gauche de l'image
	 */
	public void coinGauche() {
		//ligneDroitHautGau,ligneDroitTrapezeGau,lignePencheTrapezeGau,ligneDroitBasGau;
		lignePetiteHautGau = new MursDroits(coordX0CoinGauche, coordY0CoinGauche, coordX1CoinGauche, coordY1CoinGauche);
		lignePetiteHautGau.setPixelsParMetre(pixelParMetre);
		ligneDroitHautGau = new MursDroits(coordX1CoinGauche, coordY1CoinGauche, coordX2CoinGauche, coordY2CoinGauche);
		ligneDroitHautGau.setPixelsParMetre(pixelParMetre);
		ligneDroitTrapezeGau = new MursDroits(coordX2CoinGauche, coordY2CoinGauche, coordX3CoinGauche, coordY3CoinGauche);
		ligneDroitTrapezeGau.setPixelsParMetre(pixelParMetre);
		lignePencheTrapezeGau = new MursDroits(coordX3CoinGauche, coordY3CoinGauche, coordX4CoinGauche, coordY4CoinGauche);
		lignePencheTrapezeGau.setPixelsParMetre(pixelParMetre);
		ligneDroitBasGau = new MursDroits(coordX4CoinGauche, coordY4CoinGauche, coordX5CoinGauche, coordY5CoinGauche);
		ligneDroitBasGau.setPixelsParMetre(pixelParMetre);
	}
	//Thomas Bourgault
	/**
	 * M�thode qui initialise les diff�rents objets de type MursDroits pour le coin droit de l'image
	 */
	public void coinDroit() {
		ligneDroitBasDroite = new MursDroits(coordX1CoinDroit, coordY1CoinDroit, coordX2CoinDroit, coordY2CoinDroit);
		ligneDroitBasDroite.setPixelsParMetre(pixelParMetre);
		lignePencheTrapezeDroite = new MursDroits(coordX2CoinDroit, coordY2CoinDroit, coordX3CoinDroit, coordY3CoinDroit);
		lignePencheTrapezeDroite.setPixelsParMetre(pixelParMetre);
		ligneDroitTrapezeDroite = new MursDroits(coordX3CoinDroit, coordY3CoinDroit, coordX4CoinDroit, coordY4CoinDroit);
		ligneDroitTrapezeDroite.setPixelsParMetre(pixelParMetre);
	}
	//Thomas Bourgault
	/**
	 * M�thode qui initialise les diff�rents objets de type MursCourbes qui repr�sente les arcs de cercle
	 */
	public void arcCercle() {
		arcCerclegau = new MursCourbes(coordX1CourbeGau, coordY1CourbeGau, controleXGau, controleYGau, coordX3CourbeGau, coordY3CourbeGau, segmentCourbeGauche);
		arcCerclegau.setPixelsParMetre(pixelParMetre);
		arcCercleDroit = new MursCourbes(coordX1CourbeDroit, coordY1CourbeDroit, controleX, controleY, coordX3CourbeDroit, coordY3CourbeDroit, segmentCourbeDroit);
		arcCercleDroit.setPixelsParMetre(pixelParMetre);
		arcCerclePetit = new MursCourbes(coordX1CourbePetit, coordY1CourbePetit, controleXPetit, controleYPetit, coordX2TunnelGauche, coordY2TunnelGauche, segmentCourbePetit);
		arcCerclePetit.setPixelsParMetre(pixelParMetre);
	}
	//Thomas Bourgault
	/**
	 * M�thode qui initialise les deux objets de type MursDroits qui rerp�sente le tunnel du ressort
	 */
	public void tunnel() {
		tunnelRessortDroite = new MursDroits(coordX1TunnelDroit, coordY1TunnelDroit, coordX2TunnelDroit, coordY2TunnelDroit);
		tunnelRessortDroite.setPixelsParMetre(pixelParMetre);
		tunnelRessortGauche = new MursDroits(coordX1TunnelGauche, coordY2TunnelGauche, coordX2TunnelGauche, coordY1TunnelGauche);
		tunnelRessortGauche.setPixelsParMetre(pixelParMetre);
	}
	//Thomas Bourgault
	/**
	 * M�thode qui initialise les deux flippers de type MursDroits
	 */
	public void flippers() {
		flipGauche = new Flipper(positionFlipperGauche, longueurMancheGauche, diametreMancheGauche, gauche);
		flipGauche.setPixelsParMetre(pixelParMetre);
		flipDroit = new Flipper(positionFlipperDroit, longueurMancheGauche, diametreMancheGauche, !gauche);
		flipDroit.setPixelsParMetre(pixelParMetre);
		murFlipperGauche = new MursDroits(coordX1MurFlipperGauche, coordY1MurFlipperGauche, coordX2MurFlipperGauche, coordY2MurFlipperGauche);
		murFlipperGauche.setPixelsParMetre(pixelParMetre);
		murFlipperDroit = new MursDroits(coordX1MurFlipperDroit, coordY1MurFlipperDroit, coordX2MurFlipperDroit, coordY2MurFlipperDroit);
		murFlipperDroit.setPixelsParMetre(pixelParMetre);
	}


	//Carlos Eduardo
	/**
	 * Cette methode pourrait servir � tester si des objets de la scene
	 * sont en collision. Si oui : on calculerait par exemple les rebonds et
	 * en d�duirait des nouvelles accelerations, vitesses, positions
	 * Pour cet exemple, on laissera cette methode vide.
	 * @throws Exception 
	 */

	private void testerCollisionsEtAjusterPositions() throws Exception {
		premiereFoisCercleTouche=true;

		//colision avec les  murs 

		if (uneBille.getPosition().getX() < ligneDroitHautGau.getCoordX1()) {

			Vecteur2D vitesseNegatif = new Vecteur2D(uneBille.getVitesse().getX() * -1, uneBille.getVitesse().getY());
			uneBille.setVitesse(vitesseNegatif);



		}
		
	
		if(uneBille.getPosition().getX()> 1 && apresRessort ){

			Vecteur2D vitesseNegatif = new Vecteur2D(uneBille.getVitesse().getX() * -1, uneBille.getVitesse().getY());
			uneBille.setVitesse(vitesseNegatif);

		}
		
		if(uneBille.getPosition().getY() < 0.1) {
			Vecteur2D vitesseNegatif = new Vecteur2D(uneBille.getVitesse().getX() , uneBille.getVitesse().getY()*-1);
			uneBille.setVitesse(vitesseNegatif);
		}
	

		//colision avec les obstacles en cerlce


		for (int i = 0; i < obstaclesCercleAjou.size(); i++) {
			Murs cercle = obstaclesCercleAjou.get(i);

			//pythagore de la distance entre les centres de la bille et l"obstacle si inferieure a la somme des deux rayons donc collision 
			if (Math.hypot((uneBille.getPosition().getX() + uneBille.getDiametre() / 2) - (cercle.getPositionMursX()), (uneBille.getPosition().getY() + uneBille.getDiametre() / 2) - (cercle.getPositionMursY())) < (uneBille.getDiametre() / 2 + cercle.getDiametre() / 2)) {



				Vecteur2D cerclePos = new Vecteur2D(cercle.getPositionMursX(),cercle.getPositionMursY());

				Vecteur2D normal = moteur.MoteurPhysique.calculRebondBilleCerlce(uneBille.getPosition(),cerclePos);


				double vX =normal.getX();


				double vY =normal.getY();


				Vecteur2D vitesseRebound = new Vecteur2D(vX,vY);

				uneBille.setVitesse(vitesseRebound);

				nbCollision++;

			}

			if (nbCollision > obstaclesMax1) {


				col1 =true;

				if(nbCollision > obstaclesMax2) {

					col2 = true;
				}if(nbCollision > obstaclesMax3) {

					col3 =true;
					enleverObs();

				}

			}
		}


		for (int i = 0; i < obstaclesCercle.size(); i++) {
			Murs cercle = obstaclesCercle.get(i);

			//pythagore de la distance entre les centres de la bille et l"obstacle si inferieure a la somme des deux rayons donc collision 
			if (Math.hypot((uneBille.getPosition().getX() + uneBille.getDiametre() / 2) - (cercle.getPositionMursX()), (uneBille.getPosition().getY() + uneBille.getDiametre() / 2) - (cercle.getPositionMursY())) < (uneBille.getDiametre() / 2 + cercle.getDiametre() / 2)) {

				Vecteur2D cerclePos = new Vecteur2D(cercle.getPositionMursX(),cercle.getPositionMursY());

				Vecteur2D normal = moteur.MoteurPhysique.calculRebondBilleCerlce(uneBille.getPosition(),cerclePos);

				double vX =normal.getX()* 1.5;

				double vY =normal.getY()*1.5;

				Vecteur2D vitesseRebound = new Vecteur2D(vX,vY);

				uneBille.setVitesse(vitesseRebound);


				if(premiereFoisCercleTouche) {
					score.updateScore(50);
					premiereFoisCercleTouche=false;
				}
			}

		}

		//collision entre la bille et les surfaces en pentes.
		for (int i = 0; i < pentes.size(); i++) {

			MursDroits pente = pentes.get(i);

			Line2D.Double line = new Line2D.Double(pente.getCoordX1(), pente.getCoordY1(), pente.getCoordX2(), pente.getCoordY2());

			if (line.ptSegDist(uneBille.getPosition().getX() + uneBille.getDiametre() / 2, uneBille.getPosition().getY() + uneBille.getDiametre() / 2) < uneBille.getDiametre() / 2) {

				Vecteur2D x = new Vecteur2D(pente.getCoordX1(), pente.getCoordY1());
				Vecteur2D y = new Vecteur2D(pente.getCoordX2(), pente.getCoordY2());
				Vecteur2D temp = x.soustrait(y);
				double dx = temp.getX();
				double dy = temp.getY();
				
				Vecteur2D finiRebound = new Vecteur2D(dy * -3.5, dx);
				uneBille.setVitesse(finiRebound);

			}

		}
		//collision flipper gauche
		for (int i = 0; i < flipperGauche.size(); i++) {

			MursDroits flipper = flipperGauche.get(i);

			Line2D.Double line = new Line2D.Double(flipper.getCoordX1(), flipper.getCoordY1(), flipper.getCoordX2(), flipper.getCoordY2());

			if (line.ptSegDist(uneBille.getPosition().getX() + uneBille.getDiametre()/2, uneBille.getPosition().getY() + uneBille.getDiametre()/ 2) < uneBille.getDiametre()/ 2 ) {

				Vecteur2D perpendiculaire;	

				try {
					perpendiculaire =moteur.MoteurPhysique.calculPerpendiculaire(new Vecteur2D(murFlipperGauche.getCoordX1(),murFlipperGauche.getCoordY1()),new Vecteur2D(murFlipperGauche.getCoordX2(),murFlipperGauche.getCoordY2()));

					double vX = perpendiculaire.getX()*flipGauche.getVitesse().getY()*0.005;

					double vY = perpendiculaire.getY()*flipGauche.getVitesse().getY()*0.005;

					Vecteur2D calcul = new Vecteur2D(vX,-vY);

					uneBille.setVitesse(calcul);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}

		}
		//collision flipper droit
		for (int i = 0; i < flipperDroit.size(); i++) {

			MursDroits flipper = flipperDroit.get(i);


			Line2D.Double line = new Line2D.Double(flipper.getCoordX1(), flipper.getCoordY1(), flipper.getCoordX2(), flipper.getCoordY2());

			if (line.ptSegDist(uneBille.getPosition().getX() + uneBille.getDiametre() / 2, uneBille.getPosition().getY() + uneBille.getDiametre() / 2) < uneBille.getDiametre() / 2 && droitActive) {


				Vecteur2D perpendiculaire;	

				try {
					perpendiculaire =moteur.MoteurPhysique.calculPerpendiculaire(new Vecteur2D(murFlipperDroit.getCoordX1(),murFlipperDroit.getCoordY1()),new Vecteur2D(murFlipperDroit.getCoordX2(),murFlipperDroit.getCoordY2()));

					double vX = perpendiculaire.getX()*flipDroit.getVitesse().getY()*0.005;

					double vY = perpendiculaire.getY()*flipDroit.getVitesse().getY()*0.005;

					Vecteur2D calcul = new Vecteur2D(vX,vY);

					uneBille.setVitesse(calcul);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	

			}

		}

		//collision avec les surfaces planes (sol)
		for (int i = 0; i < solHorizontal.size(); i++) {

			MursDroits sol = solHorizontal.get(i);

			if (uneBille.getPosition().getY() + uneBille.getDiametre() > sol.getCoordY1() && uneBille.getPosition().getX() > sol.getCoordX1() && uneBille.getPosition().getX() < sol.getCoordX2()) {

				double vitX = uneBille.getVitesse().getX();

				Vecteur2D VitYnegatif = new Vecteur2D(vitX, uneBille.getVitesse().getY() * -1);

				uneBille.setVitesse(VitYnegatif);
			}
		}

		//surface plates
		for (int i = 0; i < murs.size(); i++) {

			MursDroits mur = murs.get(i);

			if (uneBille.getPosition().getX() + uneBille.getDiametre() > mur.getCoordX1() && uneBille.getPosition().getY() > mur.getCoordY1() && uneBille.getPosition().getY() < mur.getCoordY2()) {

				Vecteur2D VitYnegatif = new Vecteur2D(uneBille.getVitesse().getX() * -1, uneBille.getVitesse().getY());

				uneBille.setVitesse(VitYnegatif);
			}
		}

		//bille tombe dans trou reset
		if (uneBille.getPosition().getY() > hauteurDuComposantMetre) {

			setScoreFinal(score.getScore());

			arreter();
			
			apresRessort = false;

			retablirPosition();	
			score.setScore(scoreFinal);

			if(coeurVie) {
				System.out.println("J'ai perdu une vie");
				CoeurVie.perdVie();
			}
		}

		for (int i = 0; i < droitSous.size(); i++) {

			MursDroits sous = droitSous.get(i);

			boolean under = false;

			if (uneBille.getPosition().getY() + uneBille.getDiametre() > sous.getCoordY1()) {				
				under = true;


			}
			if (uneBille.getPosition().getX() > sous.getCoordX1() && uneBille.getPosition().getX() < sous.getCoordX2() && uneBille.getPosition().getY() < sous.getCoordY1() && under) {

				//uneBille.setVitesse(new Vecteur2D(uneBille.getVitesse().getX(), uneBille.getVitesse().getY() * -1));



			}
		}

		//cote des triangles
		for (int i = 0; i < coteTriangle.size(); i++) {

			MursDroits cote = coteTriangle.get(i);

			Line2D.Double line = new Line2D.Double(cote.getCoordX1(), cote.getCoordY1(), cote.getCoordX2(), cote.getCoordY2());

			if (line.ptSegDist(uneBille.getPosition().getX() + uneBille.getDiametre() / 2, uneBille.getPosition().getY() + uneBille.getDiametre() / 2) < uneBille.getDiametre() / 2) {

				//uneBille.setVitesse(new Vecteur2D(uneBille.getVitesse().getX() * -1, uneBille.getVitesse().getY()));
			}
		}

		//collision avec courbe initiale
		if (uneBille.getPosition().getY() < courbeY && uneBille.getPosition().getX() > courbeX) {

			double fc = moteur.MoteurPhysique.calculForceCentripete(massePourCetteScene, uneBille.getVitesse(), RAYON_COURBE);

			Vecteur2D fcFinal;

			fcFinal = moteur.MoteurPhysique.calculDelta(posCentre, uneBille.getPosition());

			Vecteur2D fcTemp;

			fcTemp = (fcFinal.multiplie(fc * -1));

			double fcGraviter = fcTemp.getY() * -4.8;

			Vecteur2D FCFINAL = new Vecteur2D(fcTemp.getX(), fcGraviter);

			uneBille.setForceExterieureAppliquee(FCFINAL);


		}else{
			if(uneBille.getPosition().getX()<courbeX) {
				uneBille.setForceExterieureAppliquee(new Vecteur2D (0,0.48));
				
				apresRessort = true;
			}


			//collision avec la courbe 
			for (int i = 0; i < courbe.size(); i++) {

				MursDroits courbes = courbe.get(i);

				Line2D.Double line = new Line2D.Double(courbes.getCoordX1(), courbes.getCoordY1(), courbes.getCoordX2(), courbes.getCoordY2());

				if (line.ptSegDist(uneBille.getPosition().getX() + uneBille.getDiametre() / 2, uneBille.getPosition().getY() + uneBille.getDiametre() / 2) < uneBille.getDiametre() / 2) {


					Vecteur2D x = new Vecteur2D(courbes.getCoordX1(), courbes.getCoordY1());

					Vecteur2D y = new Vecteur2D(courbes.getCoordX2(), courbes.getCoordY2());


					try {
						Vecteur2D normal = moteur.MoteurPhysique.calculPerpendiculaire(x, y);

						double vitX = Math.abs(uneBille.getVitesse().getX())*normal.getX();
						double vitY = Math.abs(uneBille.getVitesse().getY())*normal.getY();


						Vecteur2D normalVitesse = new Vecteur2D(vitX,vitY);
						uneBille.setVitesse(normalVitesse);


					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}



		}

		ajoutObsList();

		
		aimantActif(dessinerAimant);
		

	} ///fin collision


	//Carlos Eduardo
	/**
	 * M�thode qui g�re si la balle entre en contact avec la forme qui est un carr�
	 * @param carre est la forme qui est un carre
	 * @return un boolean faux si la bille ne touche pas au carre
	 */
	public boolean colCarre(Rectangle2D carre){

		if(uneBille.getPosition().getX() < carre.getX() || uneBille.getPosition().getX() > carre.getX() + carre.getWidth()) {
			return false;
		}
		if(uneBille.getPosition().getY() > carre.getY() || uneBille.getPosition().getY() < carre.getY() + carre.getHeight()) {
			return false;

		}

		if(uneBille.intersects(carre)) {
			return true;
		}
		return false;

	}
	//Carlos Eduardo
	/**
	 * M�thode qui permet de changer le score final
	 * @param score est un entier qui repr�sente le score finale
	 */
	public void setScoreFinal(int score) {
		scoreFinal = score;

	}
	//Carlos Eduardo
	/**
	 * M�thode qui retourne le score final
	 * @return le score final
	 */
	public int getScoreFinal() {
		return scoreFinal;
	}


	//Carlos Eduardo

	/**
	 * Animation de la balle
	 */
	public void run() {
		jouerActive=App24PinballScientifique2001.getJouerActive();
		while (enCoursDAnimation) {		
			calculerUneIterationPhysique(deltaT);
			score.timerScore();
			if (ressort.isArrete()) {
				arreter();
			}
			try {
				testerCollisionsEtAjusterPositions();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			repaint();
			try {
				Thread.sleep(tempsDuSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} //fin while
		System.out.println("Le thread est mort...");
	}


	//Thomas Bourgault
	/**
	 * Calcul des nouvelles positions pour
	 * tous les objets de la sc�ne
	 */
	private void calculerUneIterationPhysique(double deltaT) {
		if(compteurPortailGaucheSol<0.015) {
			compteurPortailGaucheSol=compteurPortailGaucheSol+0.001;
		}
		if(compteurPortailGaucheSol==0.015) {
			compteurPortailGaucheSol=0;
		}
		variablePourCompter++;
		if(variablePourCompter==22) {
			variablePourCompter=0;
			compteur=0;
		}
		if(compteurPortailDroit<0.015) {
			compteurPortailDroit=compteurPortailDroit+0.001;
		}
		if(compteurPortailDroit==0.015) {
			compteurPortailDroit=0;
		}
		variablePourCompter2++;
		if(variablePourCompter2==22) {
			variablePourCompter2=0;
			compteur2=0;
		}
		coeurVie=FenetreBacSable.getCoeurActive();
		tempsTotalEcoule += deltaT;
		uneBille.avancerUnPas(deltaT);
		getBille();
		ressort.avancerUnPas(deltaT);
		if(gaucheActive) {
			double deltaTFlipperGaucheMonter;
			//Si quart de p�riode atteint
			if(tempsEcouleGaucheMonter==0.085) {
				deltaTFlipperGaucheMonter=0;				
			}else {
				deltaTFlipperGaucheMonter=deltaT;
			}

			//frequenceAngulaire

			flipGauche.avancerUnPas(-angleMax,tempsEcouleGaucheMonter, frequenceAngulaire,premierQuartPeriode);
			angleGauche=flipGauche.getAngle();
			angleGauche=(-angleMax-flipGauche.getAngle())/2;
			tempsEcouleGaucheMonter += deltaTFlipperGaucheMonter;

		}
		if(gaucheDescente) {
			double deltaTFlipperGaucheDescendre;
			deltaTFlipperGaucheDescendre=deltaT;
			if( angleGauche==angleEquilibre) {
				tempsEcouleGaucheMonter=0;
				tempsEcouleGaucheDescendre=0;
			}
			flipGauche.avancerUnPas(angleEquilibre, tempsEcouleGaucheDescendre+tempsEcouleGaucheMonter, frequenceAngulaire,!premierQuartPeriode);
			angleGauche=flipGauche.getAngle();
			angleGauche=(-flipGauche.getAngle())/2;
			tempsEcouleGaucheDescendre+=deltaTFlipperGaucheDescendre;
		}
		if(droitActive) {
			double deltaTFlipperDroitMonter;
			if(tempsEcouleDroitMonter==0.085) {
				deltaTFlipperDroitMonter=0;				
			}else {
				deltaTFlipperDroitMonter=deltaT;
			}
			flipDroit.avancerUnPas(angleMax,tempsEcouleDroitMonter, frequenceAngulaire,premierQuartPeriode);
			angleDroit=flipDroit.getAngle();
			angleDroit=(angleMax-flipDroit.getAngle())/2;
			tempsEcouleDroitMonter += deltaTFlipperDroitMonter;
		}
		if(droitDescente) {
			double deltaTFlipperDroitDescendre;
			deltaTFlipperDroitDescendre=deltaT;
			if( angleDroit==angleEquilibre) {
				tempsEcouleDroitMonter=0;
				tempsEcouleDroitDescendre=0;
			}
			flipDroit.avancerUnPas(angleEquilibre, tempsEcouleDroitDescendre+tempsEcouleDroitMonter, frequenceAngulaire,!premierQuartPeriode);


			angleDroit=flipDroit.getAngle();
			angleDroit=(-flipDroit.getAngle())/2;
			tempsEcouleGaucheDescendre+=deltaTFlipperDroitDescendre;

		}

	}


	//Carlos Eduardo
	/**
	 * Demarre le thread s'il n'est pas deja demarre
	 */
	public void demarrer() {

		uneBille.setForceExterieureAppliquee(moteur.MoteurPhysique.calculForceGravBille(massePourCetteScene));
		ajoutObsList();


		if(premiereFoisBille) {

			uneBille.setVitesse(MoteurPhysique.caculVitesseBilleRessort(getK_RESSORT(), getEtirement(), uneBille.getMasseEnKg()));

			premiereFoisBille = false;
		}

		if (!enCoursDAnimation) {
			Thread proc = new Thread(this);
			proc.start();
			enCoursDAnimation = true;
		}

	}
	//Carlos Eduardo	
	/**
	 * Demande l'arret du thread (prochain tour de boucle)
	 */
	public void arreter() {
		enCoursDAnimation = false;
	}

	//Carlos Eduardo
	/**
	 * Reinitialise la position et la vitesse de la balle
	 */
	public void retablirPosition() {
		arreter();
		uneBille.setPosition(posInitBalle);
		uneBille.setVitesse(vitInitBalle);
		uneBille.setAccel(accelInitBalle);

		tempsTotalEcoule = 0;
		ressort.setPosition(positionInitialRessort);
		ressort.setAccel(ACCEL_INIT_RESSORT);
		ressort.setVitesse(VITESSE_INIT_RESSORT);

		tempsTotalEcoule = 0;
		flipDroit.setPosition(positionFlipperDroitInitial);
		flipDroit.setVitesse(vitesseInitialeFlipper);
		flipGauche.setPosition(positionFlipperGaucheInitial);
		flipGauche.setVitesse(vitesseInitialeFlipper);
		tempsTotalEcoule = 0;
		score.resetScore();
		repaint();
		premiereFoisBille = true;
		
		apresRessort = false;


	}

	//Carlos Eduardo
	/**
	 * Avance la simulation d'une unique image 
	 */
	public void prochaineImage() {
		calculerUneIterationPhysique(deltaT);
		try {
			testerCollisionsEtAjusterPositions();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		repaint();
	}

	//Carlos Eduardo
	/**Active un aimant et calcul la force electrique avec la loi de Coulomb
	 *  
	 * @param si checkbox aimant est true ou false
	 */
	public void aimantActif(boolean aimant) {

		intensite=intensite/100;
		if(aimant) {
			
			Vecteur2D distance = moteur.MoteurPhysique.calculDelta(uneBille.getPosition(), unAimant.getPosition());

			double forceElectrique = moteur.MoteurPhysique.forceElectrique(uneBille.getCharge(), unAimant.getCharge(), distance.module());


			distance = distance.multiplie(forceElectrique*0.0001);
			
			distance = distance.multiplie(intensite);

			uneBille.setForceExterieureAppliquee(new Vecteur2D(distance));
			
		
		}
	
	}

	//Carlos Eduardo
	public void setAimant(boolean dessinerAimant, double intensite) {
		this.dessinerAimant = dessinerAimant;
		this.intensite = intensite;
		repaint();
	}


	//Audrey Viger
	/**
	 * Modifie la constante du ressort
	 * @param kRessort la constante du ressort, exprime en N/m
	 */
	public void setkRessort(double kRessort) {
		K_RESSORT = kRessort;				
		ressort.setkRessort(kRessort);
		repaint();
	} // fin methode




	//Audrey Viger
	/**
	 * Modifie le coefficient de friction cin�tique du bloc
	 * 
	 * @param coeffFrot le coefficient de friction cin�tique du bloc
	 */									
	public void setCoeffFrot(double coeffFrot) {
		ressort.setMu(coeffFrot);
		repaint();
	} // fin methode



	//Audrey Viger
	/**
	 * Modifie la position du bloc en ajoutant l'etirement choisi � la poisition naturelle du ressort
	 * @param etirement distance entre le bloc et la position naturelle du ressort
	 */
	public void setEtirement(double etirement) {
		ressort.setPosition(new Vecteur2D(positionInitialRessort.getX(), positionInitialRessort.getY() + etirement));

		uneBille.setPosition(new Vecteur2D(positionInitialRessort.getX() + uneBille.getDiametre(), positionInitialRessort.getY() + etirement - uneBille.getDiametre() - 0.001));
		repaint();
	} // fin methode

	//Audrey Viger
	/**
	 * m�thode qui permet de retourner l'�tirement du ressort
	 * @return l'�tirement du ressort
	 */
	public double getEtirement() {
		double etirement = ressort.getPosition().getY() - positionInitialRessort.getY();

		return etirement;
	}


	//Audrey Viger
	/**
	 * Transmettre la constante initiale du ressort � l'application
	 * @return la constante initiale du ressort qui est 500 N/m
	 */
	public double getK_RESSORT() {
		return K_RESSORT;
	} // fin methode

	//Carlos Eduardo
	/**
	 * Modifie la somme des forces agissant sur les objets de la scene
	 * (ce qui aura pour effet de modifier leur acceleration)
	 * @param forceX La force totale exercee en X
	 * @param forceY La force totale exercee en Y
	 */
	public void setForces(double forceX, double forceY) {
		//dans cette application, les forces ne sont pas calcul�es : elles
		//sont plut�t directement donn�es par l'utilisateur! (peu probable dans une vraie application!)
		uneBille.setForceExterieureAppliquee(new Vecteur2D(0, 0));
	}

	//Audrey Viger
	/**
	 * Transmettre l'�tirement initial du ressort � l'application
	 * @return l'�tirement initial du ressort qui est 0 cm
	 */

	public double getETIREMENT_NAT() {
		return ETIREMENT_NAT;
	} // fin methode

	//Carlos Eduardo
	/**
	 * S'informe de la  de la balle, pour permettre a l'application de l'afficher
	 * @return La masse de la balle (qui ne change pas dans cette version de l'applicaion)
	 */
	public double getMasseBalle() {

		return uneBille.getMasseEnKg();
	}





	//Carlos Eduardo
	/**
	 * Transmettre la masse initiale du bloc � l'application
	 * @return la masse initiale du bloc qui est 700 en grammes
	 */

	public double getMASSE_POUR_CETTE_SCENE() {
		return MASSE_POUR_CETTE_SCENE;
	} // fin methode

	//Carlos Eduardo
	/**
	 * Change le temps pour le sleep du thread
	 * @param tempsDuSleep Nouveua temps a appliquer au sleep
	 */
	public void setTempsDuSleep(int tempsDuSleep) {
		this.tempsDuSleep = tempsDuSleep;
	}

	//Carlos Eduardo
	/**
	 * Retourne le temps de sleep actuel
	 * @return temps du sleep actuel
	 */
	public int getTempsDuSleep() {
		return tempsDuSleep;
	}

	//Carlos Eduardo
	/**
	 * Modifie le pas (intervalle) de la simulation
	 * @param deltaT le pas (intervalle) de la simulation, exprime en secondes
	 */
	public void setDeltaT(double deltaT) {
		this.deltaT = deltaT;
	}

	//Carlos Eduardo
	/**
	 * Retourne le pas intervalle) de la simulation
	 * @return le pas intervalle) de la simulation, exprime en secondes
	 */
	public double getDeltaT() {
		return (deltaT);
	}



	//Carlos Eduardo

	/**
	 * M�thode qui retourne le boolean pour savoir si l'animation est en cours
	 * @return la variable boolean qui d�termine si l'animation est en cours
	 */
	public boolean isAnimationEnCours() {
		return enCoursDAnimation;
	}

	//Carlos Eduardo
	/**
	 * Methode qui modifie le boolean qui d�termine si l'animation est en cours
	 * @param animationEnCours determine si l'animation est en cours
	 */
	public void setAnimationEnCours(boolean animationEnCours) {
		this.enCoursDAnimation = animationEnCours;

	}

	//Carlos Eduardo
	/**
	 * M�thode qui initialise la bille en par rapport � sa position, son diametre et sa masse pour cette scene
	 */
	private void initialiseBille() {
		uneBille = new Bille(posInitBalle, diametreBallePourCetteScene);
		uneBille.setMasseEnKg(massePourCetteScene);
	}
	

	//Carlos Eduardo
	/**
	 * methode qui retourne la bille en tant qu'objet
	 * @return la uneBille obj Bille
	 */	
	public Bille getBille() {

		return uneBille;
	}

	//Carlos Eduardo
	/**metode qui retourne le ressort comme objet
	 * 
	 * @return le ressort d'objet Ressort
	 */
	public Ressort getRessort() {

		return ressort;
	}
	//Carlos Eduardo
	/**
	 * Methode retourne le aimant comme objet
	 * @return l'aimant d'objet Aimant
	 */
	public Aimant getAimant() {

		return unAimant;
	}
	public ZonePinball() {
		// TODO Auto-generated constructor stub
	}
	//Carlos Eduardo
	/**
	 * Methode qui retourne le vecteur de la position de la bille
	 * @return un vecteur contenant la position de la bille
	 */
	public Vecteur2D getPositionBille() {
		return (uneBille.getPosition());
	}
	//Carlos Eduardo
	/**
	 * Methode qui retourne le vecteur en Y de la position de la bille
	 * @return un vecteur contenant la position y de la bille
	 */
	public double getPostionYBille() {
		return (uneBille.getPosition().getY());
	}
	//Carlos Eduardo
	/**
	 *  Methode qui retourne le vecteur de la position initiale de la bille
	 * @return un vecteur contenant la position initiale de la bille
	 */
	public Vecteur2D getPositionIniBille() {
		return (posInitBalle);
	}
	//Carlos Eduardo
	/**
	 * Methode qui modifie la masse de la bille
	 * @param masseEnKg de la bille
	 */
	public void setMasseBalle(double masseEnKg) {
		this.massePourCetteScene = masseEnKg;
	}

	//Carlos Eduardo
	/**
	 * Methode qui retourne le score de la partie 
	 * @return le score de la partie
	 */
	public PointageAnimation getScore() {
		return score;
	}
	//Thomas Bourgault
	/**
	 * Methode qui retourne le score de la partie  en entier
	 * @return le score de la partie en entier
	 */
	public int getScoreInt() {
		return (int) score.getScore();
	}

	//Thomas Bourgault
	/**
	 * M�thode qui permet d'activer ou de desactiver la visibilite des diff�rents murs
	 * @param contour est un boolean qui active la visibilit� des murs
	 */
	public void setContour(boolean contour) {
		this.contour = contour;
		repaint();
	}

	//Thomas Bourgault
	/**
	 * M�thode qui permet d'activer ou de desactiver les coordonn�es quand on bouge la souris
	 * @param coord est un boolean qui active le mode coordonne avec la souris
	 */
	public void setCoord(boolean coord) {
		this.coord = coord;
	}


	//Carlos Eduardo
	/**
	 * Modifie la masse du bloc
	 * 
	 * @param massePourCetteScene la masse du bloc, exprime en kg
	 */

	public void setMassePourCetteScene(double massePourCetteScene) {
		ressort.setMasseEnKg(massePourCetteScene);
		repaint();
	}// fin methode

	//Carlos Eduardo
	/**
	 * M�thode qui rajoute aux diff�rentes listes d'obstacles les murs qui leur sont associes
	 */
	private void listeObstacle() {
		obstaclesCercle.add(cercleMauveBas);
		obstaclesCercle.add(cercleMauveHautGauche);
		obstaclesCercle.add(cercleMauveHaut);
		obstaclesCercle.add(cercleMauveHautDroit);

		solHorizontal.add(ligneDroitTrapezeGau);
		solHorizontal.add(ligneDroitTrapezeDroite);




		droitSous.add(ligTriGaucheBas);
		droitSous.add(ligTriDroitBas);
		droitSous.add(lignePetiteHautGau);



		pentes.add(lignePencheTrapezeDroite);
		pentes.add(lignePencheTrapezeGau);
		pentes.add(ligTriDroitGau);
		pentes.add(ligTriGaucheDroit);



		flipperGauche.add(murFlipperGauche);

		flipperDroit.add(murFlipperDroit);

		murs.add(tunnelRessortDroite);
		murs.add(tunnelRessortGauche);




		coteTriangle.add(ligTriGaucheGau);

	}
	//Thomas Bourgault
	/**
	 * Methode qui change les coordonnes des murs invisibles qui sont les murs des flippers
	 */
	public void changPositionFlipper() {
		double x1=0.536;
		double x2=0.614;
		double y1=1.332;
		double y2=1.334;
		if(gaucheActive) {
			murFlipperGauche.setCoordX1(x1);
			murFlipperGauche.setCoordY1(y1);			
			repaint();
		}
		if(gaucheDescente) {
			murFlipperGauche.setCoordX1(coordX1MurFlipperGauche);
			murFlipperGauche.setCoordY1(coordY1MurFlipperGauche);
			repaint();
		}
		if(droitActive) {
			murFlipperDroit.setCoordX1(x2);
			murFlipperDroit.setCoordY1(y2);			
			repaint();
		}
		if(droitDescente) {
			murFlipperDroit.setCoordX1(coordX1MurFlipperDroit);
			murFlipperDroit.setCoordY1(coordY1MurFlipperDroit);
			repaint();
		}

	}


	//Thomas Bourgault
	/**
	 * Methode qui met les coordonnes li�s aux PathIterator de MursCourbes dans des listes en x et y  pour chaque courbes
	 */
	public void listeCourbe() {
		//coordonne en x et en y des segments de la courbe gauche
		double gaucheX1 = 0.09, gaucheX2 = 0.11303125, gaucheX3 = 0.147125, gaucheX4 = 0.19228125000000001, gaucheX5 = 0.2485, gaucheX6 = 0.31578125, gaucheX7 = 0.394125, gaucheX8 = 0.48353124999999997, gaucheX9 = 0.584;
		double gaucheY1 = 0.71, gaucheY2 = 0.5801562499999999, gaucheY3 = 0.465625, gaucheY4 = 0.36640625000000004, gaucheY5 = 0.2825, gaucheY6 = 0.21390624999999996, gaucheY7 = 0.160625, gaucheY8 = 0.12265625000000001, gaucheY9 = 0.1;
		//on ajoute � la liste les x et y

		//gauche x
		arcCercleGauCoordX.add(gaucheX1);
		arcCercleGauCoordX.add(gaucheX2);
		arcCercleGauCoordX.add(gaucheX3);
		arcCercleGauCoordX.add(gaucheX4);
		arcCercleGauCoordX.add(gaucheX5);
		arcCercleGauCoordX.add(gaucheX6);
		arcCercleGauCoordX.add(gaucheX7);
		arcCercleGauCoordX.add(gaucheX8);
		arcCercleGauCoordX.add(gaucheX9);

		//gauche y
		arcCercleGauCoordY.add(gaucheY1);
		arcCercleGauCoordY.add(gaucheY2);
		arcCercleGauCoordY.add(gaucheY3);
		arcCercleGauCoordY.add(gaucheY4);
		arcCercleGauCoordY.add(gaucheY5);
		arcCercleGauCoordY.add(gaucheY6);
		arcCercleGauCoordY.add(gaucheY7);
		arcCercleGauCoordY.add(gaucheY8);
		arcCercleGauCoordY.add(gaucheY9);
		//coordonne en x et en y des segments de la courbe droit
		double droitX1 = 0.584, droitX2 = 0.6851562499999999, droitX3 = 0.7756249999999999, droitX4 = 0.8554062499999999, droitX5 = 0.9245, droitX6 = 0.9829062500000001, droitX7 = 1.0306250000000001, droitX8 = 1.06765625, droitX9 = 1.094;
		double droitY1 = 0.1, droitY2 = 0.11387500000000002, droitY3 = 0.14550000000000002, droitY4 = 0.19487500000000002, droitY5 = 0.262, droitY6 = 0.34687499999999993, droitY7 = 0.44949999999999996, droitY8 = 0.5698749999999999, droitY9 = 0.708;
		//droit x
		arcCercleDroitCoordX.add(droitX1);
		arcCercleDroitCoordX.add(droitX2);
		arcCercleDroitCoordX.add(droitX3);
		arcCercleDroitCoordX.add(droitX4);
		arcCercleDroitCoordX.add(droitX5);
		arcCercleDroitCoordX.add(droitX6);
		arcCercleDroitCoordX.add(droitX7);
		arcCercleDroitCoordX.add(droitX8);
		arcCercleDroitCoordX.add(droitX9);

		//droit y
		arcCercleDroitCoordY.add(droitY1);
		arcCercleDroitCoordY.add(droitY2);
		arcCercleDroitCoordY.add(droitY3);
		arcCercleDroitCoordY.add(droitY4);
		arcCercleDroitCoordY.add(droitY5);
		arcCercleDroitCoordY.add(droitY6);
		arcCercleDroitCoordY.add(droitY7);
		arcCercleDroitCoordY.add(droitY8);
		arcCercleDroitCoordY.add(droitY9);

		//coordonne en x et en y des segments de la courbe petit
		double petitX1 = 0.9, petitX2 = 0.9774999999999999, petitX3 = 0.9993749999999999, petitX4 = 1.01;
		double petitY1 = 0.398, petitY2 = 0.5954999999999999, petitY3 = 0.6908749999999999, petitY4 = 0.784;
		//petit x
		arcCerclePetitCoordX.add(petitX1);
		arcCerclePetitCoordX.add(petitX2);
		arcCerclePetitCoordX.add(petitX3);
		arcCerclePetitCoordX.add(petitX4);
		//petit y
		arcCerclePetitCoordY.add(petitY1);
		arcCerclePetitCoordY.add(petitY2);
		arcCerclePetitCoordY.add(petitY3);
		arcCerclePetitCoordY.add(petitY4);
	}

	//Thomas Bourgault
	/**
	 * Methode qui creer des Line2D � l'aide des boucles grace aux listes reliees aux diff�rentes courbes
	 */
	public void SegmentCourbe() {
		MursDroits mur;
		for (int i = 0; i < 3; i++) {
			mur = new MursDroits(arcCerclePetitCoordX.get(i+1), arcCerclePetitCoordY.get(i+1), arcCerclePetitCoordX.get(i ), arcCerclePetitCoordY.get(i ));
			mur.setPixelsParMetre(pixelParMetre);
			courbe.add(mur);

		}
		for (int j = 0; j < 7; j++) {
			mur = new MursDroits(arcCercleDroitCoordX.get(j), arcCercleDroitCoordY.get(j), arcCercleDroitCoordX.get(j + 1), arcCercleDroitCoordY.get(j + 1));
			mur.setPixelsParMetre(pixelParMetre);
			courbe.add(mur);
		}
		for (int j = 0; j < 7; j++) {
			mur = new MursDroits(arcCercleGauCoordX.get(j), arcCercleGauCoordY.get(j), arcCercleGauCoordX.get(j+1), arcCercleGauCoordY.get(j+1));
			mur.setPixelsParMetre(pixelParMetre);
			courbe.add(mur);
		}
	}
	//Audrey viger
	/**
	 * Methode qui g�re le d�placement des formes avec la souris
	 */
	private void gestionSourisObs() {
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if(premiereFoisBougerObstacle) {


					if(obstacle.getPosY()+obstacle.getHaut()<=maxObstacleHaut && obstacle.getPosX()>=maxObstacleGauche && (obstacle.getPosX()+obstacle.getLarg())<=maxObstacleDroite && obstacle.getPosY()>=maxObstacleBas)	{				

						if (formeSelectionne) {
							translatCarreX += e.getX()/(dimensionImageX/largeurDuComposantMetre) - xPrecedent;
							translatCarreY += e.getY()/(dimensionImageX/largeurDuComposantMetre) - yPrecedent;
							xPrecedent = e.getX()/(dimensionImageX/largeurDuComposantMetre) ;
							yPrecedent = e.getY()/(dimensionImageX/largeurDuComposantMetre) ;
							repaint();
						}
					}else {
						if (formeSelectionne) {
							if(obstacle.getPosY()+obstacle.getHaut()>maxObstacleHaut) {
								translatCarreY += -0.01;

								repaint();
							}else if(obstacle.getPosX()+obstacle.getLarg()>maxObstacleDroite && obstacle.getPosX()!=maxObstacleGauche) {
								translatCarreX += -0.01;
								repaint();
							} else  if(obstacle.getPosX()<maxObstacleGauche && obstacle.getPosX()+obstacle.getLarg()!=maxObstacleBas ){
								translatCarreX += 0.01;
								repaint();
							}else {
								translatCarreY += 0.01;
								repaint();
							}

						}
					}
				} //fin drag

			}
		});	
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				nbClicObstacle=nbClicObstacle+1;
				if(forme=="Cercle") {
					if (obstacle.contientCercle(e.getX()/(dimensionImageX/largeurDuComposantMetre), e.getY()/(dimensionImageX/largeurDuComposantMetre))){
						formeSelectionne = true;
						xPrecedent = e.getX()/(dimensionImageX/largeurDuComposantMetre);
						yPrecedent = e.getY()/(dimensionImageX/largeurDuComposantMetre);
						repaint();
					}

				}else if(forme=="Rectangle") {
					if (obstacle.contientRectangle(e.getX()/(dimensionImageX/largeurDuComposantMetre), e.getY()/(dimensionImageX/largeurDuComposantMetre))){
						formeSelectionne = true;
						xPrecedent = e.getX()/(dimensionImageX/largeurDuComposantMetre);
						yPrecedent = e.getY()/(dimensionImageX/largeurDuComposantMetre);
						repaint();
					}

				}else if (forme=="Carre") {
					if (obstacle.contientCarre(e.getX()/(dimensionImageX/largeurDuComposantMetre), e.getY()/(dimensionImageX/largeurDuComposantMetre))){
						formeSelectionne = true;
						xPrecedent = e.getX()/(dimensionImageX/largeurDuComposantMetre);
						yPrecedent = e.getY()/(dimensionImageX/largeurDuComposantMetre);
						repaint();
					}
				}else if (forme == "Triangle") {
					if (obstacle.contientTriangle(e.getX()/(dimensionImageX/largeurDuComposantMetre), e.getY()/(dimensionImageX/largeurDuComposantMetre))){
						formeSelectionne = true;
						xPrecedent = e.getX()/(dimensionImageX/largeurDuComposantMetre);
						yPrecedent = e.getY()/(dimensionImageX/largeurDuComposantMetre);
						repaint();
					}

				}
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				formeSelectionne = false;
				premiereFoisBougerObstacle = false;


			} //fin released

		});	


	}

	//Audrey Viger
	/**
	 * Methode qui modifie la forme selectionne dans la FenetreBacSable ou FenetreJouer
	 * @param forme est la forme selectionne dans la FenetreBacSable ou FenetreJouer
	 */



	public void setForme(String forme) {
		this.forme=forme;
		repaint();

	}


	//Audrey Viger
	public void dessinerEchelle(Graphics g2d) {
		echelle = new Path2D.Double ();

		echelle.moveTo(17, 16);
		echelle.lineTo(17,747);
		echelle.lineTo(583,747);
		echelle.moveTo(67, 744);
		for(int i=1; i<12;i++) {
			int x =17;
			int x2 = i*50;
			g2d.setFont(new Font("TimesRoman",Font.PLAIN,10));
			g2d.drawString(i+"0", x+x2-6, 763);
			echelle.lineTo(x+x2, 752);
			echelle.moveTo((x+x2)+50,744);
		}

		echelle.moveTo(20, 697);
		for(int j=1; j<15;j++) {
			int y = 747;
			int y2 = j*50;
			g2d.setFont(new Font("TimesRoman",Font.PLAIN,10));
			g2d.drawString(j+"0",1, y-y2+10);
			echelle.lineTo(12, y-y2);
			echelle.moveTo(20,(y-y2)-50);
		}
		g2d.drawString("cm", 584, 751);
		g2d.drawString("cm", 1,15 );

		((Graphics2D) g2d).draw(echelle);

	}

//Carlos Eduardo
	/**
	 * M�thode statique qui retourne le score final
	 * @return le score final
	 */
	public static int getScorefinal() {
		return scoreFinal;		

	}
	//Thomas Bourgault
	/**
	 * M�thode statique qui retourne un objet Musique pour le flipper de gauche
	 * @return un objet Musique pour le flipper de gauche
	 */
	public static Musique musiqueFlipperGauche() {
		return musiqueFlipperGauche;
	}
	//Thomas Bourgault
	/**
	 * M�thode statique qui retourne un objet Musique pour le flipper de droit
	 * @return  un objet Musique pour le flipper de droit
	 */
	public static Musique musiqueFlipperDroit() {
		return musiqueFlipperDroit;
	}
	//Carlos Eduardo
	/**
	 * M�thode statique qui permet de changer le boolean qui g�re la premi�re qu'on touche � un obstacle
	 * @param x est un nouveau boolean
	 */
	public static void setPremiereFoisObstacle(boolean x) {

		premiereFoisBougerObstacle = x;
	}

//Carlos Eduardo
	/**
	 * M�thode statique qui retourne le boolean qui g�re la premi�re qu'on touche � un obstacle
	 * @return le boolean qui g�re la premi�re qu'on touche � un obstacle
	 */
	public static boolean premiereFoisObstacle() {

		return premiereFoisBougerObstacle;
	}


//Carlos Eduardo
	/**
	 * M�thode qui ajoute des Murs aux obstacles selon les diff�rentes formes
	 */
	public void ajoutObsList() {

		if((forme == "Cercle" || forme == "Triangle" || forme == "Carre"|| forme == "Rectangle" )  && collisionMax) {
			Murs obs = new Murs(posXCarre+translatCarreX-0.05,posYCarre+translatCarreY-0.05,0.1);

			obstaclesCercleAjou.add(obs);

		}
		if(forme == "Carre") {

			Rectangle2D carre =	new Rectangle2D.Double(posXCarre+translatCarreX-0.05,posYCarre+translatCarreY-0.05,0.1,0.1);

			obstaclesCarre.add(carre);


		}


	}
//Carlos Eduardo
	/**
	 * M�thode qui permet de remettre le nombre de collision � 0
	 */
	public void resetCollisionObs() {

		nbCollision =0;
		collisionMax = true;
	}

//Carlos Eduardo
/**
 * M�thode qui permet de r�nitialiser la liste qui contient les obstacles selon les formes
 */
	public void enleverObs() {

		obstaclesCercleAjou.clear();
		collisionMax = false;

	}


}