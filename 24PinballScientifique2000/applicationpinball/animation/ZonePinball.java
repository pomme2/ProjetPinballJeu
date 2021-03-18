package animation;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Path2D.Double;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import dessinable.OutilsImage;
import geometrie.Bille;
import geometrie.Flipper;
import geometrie.Murs;
import geometrie.MursCourbes;
import geometrie.MursDroits;
import geometrie.Ressort;
import geometrie.Vecteur2D;

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

public class ZonePinball  extends JPanel implements Runnable  {
	private static final long serialVersionUID = 1L;

	//variable bille Carlos
	private double deltaT = 0.005;


	private double diametreBallePourCetteScene = 0.03;  //em mètres
	private double massePourCetteScene = 0.1; //en kg

	private Vecteur2D posInitBalle = new Vecteur2D(1.025,1.216);  //position intiale pour la balle
	private Vecteur2D vitInitBalle = new Vecteur2D(0, 0);  //vitesse intiale pour la balle
	private Vecteur2D accelInitBalle = new Vecteur2D(0, 0);  //acceleration intiale pour la balle


	//position intiales pour la bille
	private Bille uneBille;
	private boolean enCoursDAnimation= false;
	private double tempsTotalEcoule = 0;
	private int tempsDuSleep = 10;



	//tableau pour obstacles
	ArrayList<Murs> obstaclesCercle = new ArrayList<Murs>();

	//tab pour mursHorizontales (sol)
	ArrayList<MursDroits> solHorizontal = new ArrayList<MursDroits>();



	/*private void deplacerPointSelonTouche(KeyEvent e) {
		int code = e.getKeyCode();
		switch (code) {
		case KeyEvent.VK_LEFT:
			uneBille.setVitesse(new Vecteur2D(uneBille.getVitesse().getX(),uneBille.getVitesse().getY()*-1));;
			System.out.println("LEFT");
			break;

		case KeyEvent.VK_RIGHT:
			uneBille.setVitesse(new Vecteur2D(uneBille.getVitesse().getX(),uneBille.getVitesse().getY()*-1));;
			System.out.println("RIGHT");

			break;

		}// fin switch
	}
	 */
	//Carlos Eduardo


	//tab pour les pentes
	ArrayList<MursDroits> pentes = new ArrayList<MursDroits>();

	ArrayList<MursDroits> droitSous = new ArrayList<MursDroits>();


	//tab pour les murs
	ArrayList<MursDroits> murs = new ArrayList<MursDroits>();



	private boolean premiereFois=true;
	private boolean premiereFoisImage=true;

	//Image et pixelParMetre
	private int dimensionImageX=600,dimensionImageY=768;	
	private double largeurDuComposantMetre=1.2;
	private double hauteurDuComposantMetre=1.536;
	private double pixelParMetre=500;
	private Image imageTerrainPinball1;
	//4 cercles
	private Murs cercleMauveBas,cercleMauveHautGauche,cercleMauveHaut,cercleMauveHautDroit;
	private double coordXBas=0.526, coordYBas=0.7865, coordXHautGauche=0.340,coordYHautGauche=0.458,coordXHaut=0.546,coordYHaut=0.3145,coordXHautDroit=0.719,coordYHautDroit=0.462;
	private double diametre=0.105;
	//2 triangles
	//Triangle de gauche:
	private MursDroits ligTriGaucheGau,ligTriGaucheDroit,ligTriGaucheBas;
	private double coordX1TriGauche=0.192,coordY1TriGauche=0.778,coordX2TriGauche=0.316,coordY2TriGauche=0.898,coordX3TriGauche=0.194,coordY3TriGauche=0.896;
	//Triangle de droite:
	private MursDroits ligTriDroitGau,ligTriDroitDroit,ligTriDroitBas;
	private double coordX1TriDroite=0.926,coordY1TriDroite=0.83,coordX2TriDroite=0.922,coordY2TriDroite= 1.044,coordX3TriDroite=0.814,coordY3TriDroite=1.048;

	//Coin gauche terrain pinball	
	private MursDroits ligneDroitHautGau,ligneDroitTrapezeGau,lignePencheTrapezeGau,ligneDroitBasGau,lignePetiteHautGau;
	private double coordX0CoinGauche=0.09,coordY0CoinGauche=0.71,coordX1CoinGauche=0.06,coordY1CoinGauche=0.71,coordX2CoinGauche=0.058,coordY2CoinGauche=1.202,coordX3CoinGauche=0.202,coordY3CoinGauche=1.202,coordX4CoinGauche=0.494,coordY4CoinGauche=1.386,coordX5CoinGauche=0.494,coordY5CoinGauche=1.532;

	//Coin droit terrain pinball
	private MursDroits ligneDroitTrapezeDroite,lignePencheTrapezeDroite,ligneDroitBasDroite;
	private double coordX1CoinDroit=0.662,coordY1CoinDroit=1.532,coordX2CoinDroit=0.662,coordY2CoinDroit=1.388,coordX3CoinDroit=0.93,coordY3CoinDroit=1.21,coordX4CoinDroit=1,coordY4CoinDroit=1.21,coordX5CoinDroit=1,coordY5CoinDroit=0.942;

	//Grande courbe haut:
	private MursCourbes arcCerclegau, arcCercleDroit,arcCerclePetit;
	private double coordX1CourbeGau=0.09,coordY1CourbeGau=0.71,controleXGau=0.16,controleYGau=0.16,coordX3CourbeGau=0.584,coordY3CourbeGau=0.1;
	private double coordX1CourbeDroit=0.584,coordY1CourbeDroit=0.1,controleX=1.01,controleY=0.12,coordX3CourbeDroit=1.094,coordY3CourbeDroit=0.708;
	private double controleXPetit=1,controleYPetit=0.6, coordX1CourbePetit=0.9,coordY1CourbePetit=0.398;
	//Mur gauche et droit tunnel ressort
	private MursDroits tunnelRessortDroite, tunnelRessortGauche;
	private double coordX1TunnelGauche=1.006,coordY1TunnelGauche=1.534,coordX2TunnelGauche=1.01,coordY2TunnelGauche=0.784,coordX1TunnelDroit=1.096 ,coordY1TunnelDroit=0.716,coordX2TunnelDroit= 1.096,coordY2TunnelDroit=1.532;

	//Ressort Audrey
	private Ressort ressort;
	private final Vecteur2D positionInitialRessort = new Vecteur2D(1.009,1.272);
	private final Vecteur2D VITESSE_INIT_RESSORT = new Vecteur2D(0,-0.0000001 ); 
	private final Vecteur2D ACCEL_INIT_RESSORT = new Vecteur2D(0, 0); 

	private final int TEMPS_DU_SLEEP = 25;
	private final double K_RESSORT = 500;
	private final double ETIREMENT_NAT = 0.1;

	private final double COEFF_FROT = 0.64;
	private final double MASSE_POUR_CETTE_SCENE = 0.7; // en kg


	//Flippers

	private Flipper flipGauche,flipDroit;
	private double coordX1FlipperGauche=0.465,coordY1FlipperGauche=1.386,longueurMancheGauche=0.09,diametreMancheGauche=0.015;
	private Vecteur2D positionFlipperGauche=new Vecteur2D(coordX1FlipperGauche,coordY1FlipperGauche);
	private Vecteur2D positionFlipperGaucheInitial=new Vecteur2D(coordX1FlipperGauche,coordY1FlipperGauche);
	private double coordX1FlipperDroit=0.690,coordY1FlipperDroit=1.386;
	private Vecteur2D positionFlipperDroit=new Vecteur2D(coordX1FlipperDroit,coordY1FlipperDroit);
	private Vecteur2D positionFlipperDroitInitial=new Vecteur2D(coordX1FlipperDroit,coordY1FlipperDroit);
	private Vecteur2D vitesseInitialeFlipper=new Vecteur2D(0,0);
	private boolean gauche=true;

	double angle=Math.PI/100;




	private boolean contour=false,ImageSelectionne=false,coord=false,gaucheActive=false,droitActive=false,gaucheDescente=false,droitDescente=false;	
	java.net.URL urlPinballTerrain=getClass().getClassLoader().getResource("pinballTerrain.png");
	double compteurGauche,compteurDroit;


	//Thomas Bourgault
	/**
	 * Constructeur qui gère les différents types d'évènements de la souris, permet l'initialisation de l'image ainsi que de la bille
	 * @throws IOException si le programme n'arrive pas à lire l'URL de l'image
	 */
	public ZonePinball() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_A) {
					System.out.println("touche a active");
					repaint();


					//uneBille.setVitesse(new Vecteur2D(uneBille.getVitesse().getX(),-3));
					gaucheActive=true;
					gaucheDescente=false;

				}else {
					if(e.getKeyCode()==KeyEvent.VK_D ) {
						System.out.println("touche d active");
						droitActive=true;
						droitDescente=false;

						//uneBille.setVitesse(new Vecteur2D(uneBille.getVitesse().getX(),-2.4));

						repaint();
					}
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_A) {
					gaucheActive=false;
					gaucheDescente=true;

					repaint();
				}else {
					if(e.getKeyCode() == KeyEvent.VK_D) {
						droitActive=false;
						droitDescente=true;
						repaint();
					}
				}
			}

		});



		initialiseBille();

		uneBille = new Bille(posInitBalle,diametreBallePourCetteScene);
		uneBille.setMasseEnKg(massePourCetteScene);

		ressort = new Ressort(positionInitialRessort,0.088,0.192);
		ressort.setkRessort(K_RESSORT);

		ressort.setMu(COEFF_FROT);
		ressort.setVitesse(VITESSE_INIT_RESSORT);
		ressort.setMasseEnKg(MASSE_POUR_CETTE_SCENE);


		ressort.setVitesse(VITESSE_INIT_RESSORT);
		flippers();

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				ImageSelectionne=true;
			}
			@Override
			public void mouseExited(MouseEvent e) {
				ImageSelectionne=false;
			}
			@Override
			public void mousePressed(MouseEvent e) {
				if(ImageSelectionne) {
					System.out.println("X: "+e.getX()/(dimensionImageX/largeurDuComposantMetre)+" cliqué "+" Y: "+e.getY()/(dimensionImageY/hauteurDuComposantMetre)+" cliqué");
				}
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if(ImageSelectionne && coord) {
					System.out.println("X: "+e.getX()/(dimensionImageX/largeurDuComposantMetre)+" Y: "+e.getY()/(dimensionImageY/hauteurDuComposantMetre));
				}
			}
		});
		setBackground(Color.gray);
		setLayout(null);

		Image imageTerrainPinballMauvaiseDim;
		try {
			imageTerrainPinballMauvaiseDim = ImageIO.read(urlPinballTerrain);
			imageTerrainPinball1=imageTerrainPinballMauvaiseDim.getScaledInstance(dimensionImageX,dimensionImageY,Image.SCALE_SMOOTH);
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
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		double pixelParMetre=getWidth()/largeurDuComposantMetre;							
		//Construction image									
		AffineTransform mat= new AffineTransform();			
		double factReso=largeurDuComposantMetre/ imageTerrainPinball1.getWidth(null);
		mat.scale (factReso*pixelParMetre,factReso*pixelParMetre);						
		g2d.drawImage(imageTerrainPinball1,mat,null);
		g2d.setColor(Color.white);
		////////////////////////////////////////////////////////////////////////GAUCHE
		AffineTransform oldGauche = g2d.getTransform();
		AffineTransform trans = new AffineTransform();
		if(gaucheActive ) {		
			g2d.rotate(Math.toRadians(-30),coordX1FlipperGauche*pixelParMetre,coordY1FlipperGauche*pixelParMetre);
		}
		if(gaucheDescente) {
			trans.rotate(Math.toRadians(0),coordX1FlipperGauche*pixelParMetre,coordY1FlipperGauche*pixelParMetre);
		}
		g2d.transform(trans);
		flipGauche.dessiner(g2d);
		
		g2d.setTransform(oldGauche);
		////////////////////////////////////////////////////////////////////////////////DROIT
		AffineTransform oldDroit = g2d.getTransform();
		if(droitActive) {
			g2d.rotate(Math.toRadians(30),coordX1FlipperDroit*pixelParMetre,coordY1FlipperDroit*pixelParMetre);
		}
		if(droitDescente) {
			g2d.rotate(Math.toRadians(0),coordX1FlipperDroit*pixelParMetre,coordY1FlipperDroit*pixelParMetre);
		}
		flipDroit.dessiner(g2d);
		g2d.setTransform(oldDroit);
		////////////////////////////////////////////////////////////////////////////////
		if(premiereFois) {
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
			//Construction flippers

			premiereFois=false;
		}



		listeObstacle();


		//ressort = new Ressort(positionInitialRessort,0.088,0.192);
		ressort.setPixelsParMetre(pixelParMetre);
		ressort.dessiner(g2d);

		g2d.setColor(Color.red);
		uneBille.setPixelsParMetre(pixelParMetre);
		uneBille.dessiner(g2d);




		if(contour) {
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
		//test

	}
	//Thomas Bourgault
	/**
	 * Méthode qui permet d'activer ou de desactiver la visibilite des différents murs
	 * @param contour est un boolean qui active la visibilité des murs
	 */
	public void setContour(boolean contour) {
		this.contour = contour;
		repaint();
	}
	//Thomas Bourgault
	/**
	 * Méthode qui permet d'activer ou de desactiver les coordonnées quand on bouge la souris
	 * @param coord est un boolean qui active le mode coordonne avec la souris
	 */
	public void setCoord(boolean coord) {
		this.coord=coord;
	}
	//Thomas Bourgault
	/**
	 * Méthode qui initialise les différents objets de type Murs (des cercles)
	 */
	public void cercle() {
		cercleMauveBas=new Murs(coordXBas,coordYBas,diametre);	
		cercleMauveBas.setPixelsParMetre(pixelParMetre);

		cercleMauveHautGauche=new Murs( coordXHautGauche,coordYHautGauche,diametre);
		cercleMauveHautGauche.setPixelsParMetre(pixelParMetre);

		cercleMauveHaut=new Murs(coordXHaut,coordYHaut,diametre);
		cercleMauveHaut.setPixelsParMetre(pixelParMetre);

		cercleMauveHautDroit=new Murs(coordXHautDroit,coordYHautDroit,diametre);
		cercleMauveHautDroit.setPixelsParMetre(pixelParMetre);
	}
	//Thomas Bourgault
	/**
	 * Méthode qui initialise les différents objets de type MursDroits pour le triangle gauche de l'image
	 */
	public void triangleGauche() {
		ligTriGaucheGau=new MursDroits(coordX1TriGauche,coordY1TriGauche,coordX3TriGauche,coordY3TriGauche);
		ligTriGaucheGau.setPixelsParMetre(pixelParMetre);
		ligTriGaucheBas=new MursDroits(coordX2TriGauche,coordY2TriGauche,coordX3TriGauche,coordY3TriGauche);
		ligTriGaucheBas.setPixelsParMetre(pixelParMetre);
		ligTriGaucheDroit=new MursDroits(coordX1TriGauche,coordY1TriGauche,coordX2TriGauche,coordY2TriGauche);
		ligTriGaucheDroit.setPixelsParMetre(pixelParMetre);
	}
	//Thomas Bourgault
	/**
	 * Méthode qui initialise les différents objets de type MursDroits pour le triangle droit de l'image
	 */
	public void triangleDroit() {
		ligTriDroitGau=new MursDroits(coordX3TriDroite,coordY3TriDroite,coordX1TriDroite,coordY1TriDroite);
		ligTriDroitGau.setPixelsParMetre(pixelParMetre);
		ligTriDroitBas=new MursDroits(coordX2TriDroite,coordY2TriDroite,coordX3TriDroite,coordY3TriDroite);
		ligTriDroitBas.setPixelsParMetre(pixelParMetre);
		ligTriDroitDroit=new MursDroits(coordX2TriDroite,coordY2TriDroite,coordX1TriDroite,coordY1TriDroite);
		ligTriDroitDroit.setPixelsParMetre(pixelParMetre);
	}
	//Thomas Bourgault
	/**
	 * Méthode qui initialise les différents objets de type MursDroits pour le coin gauche de l'image
	 */
	public void coinGauche() {
		//ligneDroitHautGau,ligneDroitTrapezeGau,lignePencheTrapezeGau,ligneDroitBasGau;
		lignePetiteHautGau=new MursDroits(coordX0CoinGauche,coordY0CoinGauche,coordX1CoinGauche,coordY1CoinGauche);
		lignePetiteHautGau.setPixelsParMetre(pixelParMetre);
		ligneDroitHautGau= new MursDroits(coordX1CoinGauche,coordY1CoinGauche,coordX2CoinGauche,coordY2CoinGauche);
		ligneDroitHautGau.setPixelsParMetre(pixelParMetre);
		ligneDroitTrapezeGau=new MursDroits(coordX2CoinGauche,coordY2CoinGauche,coordX3CoinGauche,coordY3CoinGauche);
		ligneDroitTrapezeGau.setPixelsParMetre(pixelParMetre);
		lignePencheTrapezeGau=new MursDroits(coordX3CoinGauche,coordY3CoinGauche,coordX4CoinGauche,coordY4CoinGauche);
		lignePencheTrapezeGau.setPixelsParMetre(pixelParMetre);
		ligneDroitBasGau=new MursDroits(coordX4CoinGauche,coordY4CoinGauche,coordX5CoinGauche,coordY5CoinGauche);
		ligneDroitBasGau.setPixelsParMetre(pixelParMetre);
	}
	//Thomas Bourgault
	/**
	 * Méthode qui initialise les différents objets de type MursDroits pour le coin droit de l'image
	 */
	public void coinDroit() {
		//ligneDroitHautDroite,ligneDroitTrapezeDroite,lignePencheTrapezeDroite,ligneDroitBasDroite;

		ligneDroitBasDroite=new MursDroits(coordX1CoinDroit,coordY1CoinDroit,coordX2CoinDroit,coordY2CoinDroit);
		ligneDroitBasDroite.setPixelsParMetre(pixelParMetre);
		lignePencheTrapezeDroite=new MursDroits(coordX2CoinDroit,coordY2CoinDroit,coordX3CoinDroit,coordY3CoinDroit);
		lignePencheTrapezeDroite.setPixelsParMetre(pixelParMetre);
		ligneDroitTrapezeDroite=new MursDroits(coordX3CoinDroit,coordY3CoinDroit,coordX4CoinDroit,coordY4CoinDroit);
		ligneDroitTrapezeDroite.setPixelsParMetre(pixelParMetre);		
	}
	//Thomas Bourgault
	/**
	 * Méthode qui initialise les différents objets de type MursCourbes qui représente les arcs de cercle
	 */
	public void arcCercle() {
		arcCerclegau=new MursCourbes(coordX1CourbeGau,coordY1CourbeGau,controleXGau,controleYGau,coordX3CourbeGau,coordY3CourbeGau);
		arcCerclegau.setPixelsParMetre(pixelParMetre);
		arcCercleDroit= new MursCourbes(coordX1CourbeDroit,coordY1CourbeDroit,controleX,controleY,coordX3CourbeDroit,coordY3CourbeDroit);
		arcCercleDroit.setPixelsParMetre(pixelParMetre);
		arcCerclePetit=new MursCourbes(coordX1CourbePetit,coordY1CourbePetit,controleXPetit,controleYPetit,coordX2TunnelGauche,coordY2TunnelGauche);
		arcCerclePetit.setPixelsParMetre(pixelParMetre);
	}
	//Thomas Bourgault
	/**
	 * Méthode qui initialise les deux objets de type MursDroits qui rerpésente le tunnel du ressort
	 */
	public void tunnel() {
		//tunnelRessortDroite, tunnelRessortGauche
		tunnelRessortDroite=new MursDroits(coordX1TunnelDroit,coordY1TunnelDroit,coordX2TunnelDroit,coordY2TunnelDroit);
		tunnelRessortDroite.setPixelsParMetre(pixelParMetre);
		tunnelRessortGauche=new MursDroits (coordX1TunnelGauche,coordY2TunnelGauche,coordX2TunnelGauche,coordY1TunnelGauche);
		tunnelRessortGauche.setPixelsParMetre(pixelParMetre);
	}
	//Thomas Bourgault
	/**
	 * Méthode qui initialise les deux flippers de type MursDroits
	 */
	public void flippers() {
		flipGauche=new Flipper(positionFlipperGauche,longueurMancheGauche,diametreMancheGauche,gauche);
		flipGauche.setPixelsParMetre(pixelParMetre);
		flipDroit=new Flipper(positionFlipperDroit,longueurMancheGauche,diametreMancheGauche,!gauche);
		flipDroit.setPixelsParMetre(pixelParMetre);
	}
	//Carlos Eduardo

	/**
	 * Animation de la balle
	 */
	public void run() {

		while (enCoursDAnimation) {

			if(droitActive) {
				angle=Math.PI/6;

			}
			if(gaucheActive) {
				angle=Math.PI/6;

				System.out.println(")))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))");
				System.out.println("angle: "+angle);
			}
			/**	
			}
			if(gaucheActive) {
				compteurGauche++;
			}
			if (coordY2FlipperGauche-(compteurGauche/100000000)<1.2 ) {				
				gaucheActive=false;
				gaucheDescente=true;

				if(coordY2FlipperDroit-(compteurDroit/100000000)<1.2 ) {
					droitActive=false;
				droitDescente=true;
				}
			}

			if(coordY2FlipperGauche+(compteurGauche/100000000)>1.405) {				
				gaucheDescente=false;							
			}
			if(coordY2FlipperDroit+(compteurDroit/100000000)>1.405) {
				droitDescente=false;
			}
			 */
			//System.out.println("Un tour de run...on avance de " + deltaT + " secondes");
			calculerUneIterationPhysique(deltaT);
			if(ressort.isArrete()) {
				arreter();
			}
			testerCollisionsEtAjusterPositions();//pas utile pour le moment
			repaint();
			try {
				Thread.sleep(tempsDuSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}//fin while
		//System.out.println("Le thread est mort...");
	}


	//Carlos Eduardo
	/**
	 * Calcul des nouvelles positions pour
	 * tous les objets de la scène
	 */
	private void calculerUneIterationPhysique(double deltaT) {
		tempsTotalEcoule += deltaT;
		uneBille.avancerUnPas( deltaT );
		getBille();
		if(gaucheActive) {
			flipGauche.avancerUnPas(deltaT);
			System.out.println("//////////////////////////////////////////////////////////////////////////////////////");
			System.out.println("Px gauche: "+positionFlipperGauche.getX()+" Py gauche: "+positionFlipperGauche.getY());
		}
		if(droitActive) {
			flipDroit.avancerUnPas(deltaT);
			System.out.println("//////////////////////////////////////////////////////////////////////////////////////");
			System.out.println("Px droit: "+positionFlipperDroit.getX()+" Py droit: "+positionFlipperDroit.getY());
		}
		ressort.avancerUnPas(deltaT);

		System.out.println("\nNouvelle accel: " + uneBille.getAccel().toString(2));
		System.out.println("Nouvelle vitesse: " + uneBille.getVitesse().toString(2));
		System.out.println("Nouvelle position: " + uneBille.getPosition().toString(2));

		System.out.println("\nTemps total simulé écoulé: "  + String.format("%.3f",tempsTotalEcoule) + "sec (en temps simulé!)");


	}

	//Carlos Eduardo
	/**
	 * Cette methode pourrait servir à tester si des objets de la scene
	 * sont en collision. Si oui : on calculerait par exemple les rebonds et
	 * en déduirait des nouvelles accelerations, vitesses, positions
	 * Pour cet exemple, on laissera cette methode vide.
	 */

	private void testerCollisionsEtAjusterPositions() {

		boolean col = false;


		//colission avec la courbe superieure
		/*if(arcCercleDroit.getCourbe().intersects(uneBille.getPosition().getX()+uneBille.getDiametre()*2, uneBille.getPosition().getY()+uneBille.getDiametre()*2, uneBille.getDiametre()/2, uneBille.getDiametre()/2)) {




				Vecteur2D vitesseNegatif = new Vecteur2D (uneBille.getVitesse().getX(),uneBille.getVitesse().getY()*-1);
				uneBille.setVitesse(vitesseNegatif);


			}
		 */


		//colision avec mur vertical

		if(uneBille.getPosition().getX() < ligneDroitHautGau.getCoordX1() ) {

			Vecteur2D vitesseNegatif = new Vecteur2D (uneBille.getVitesse().getX()*-1,uneBille.getVitesse().getY());
			uneBille.setVitesse(vitesseNegatif);
			System.out.println("Collision mur");
		}


		//colision avec les obstacles en cerlce


		for(int i=0; i< obstaclesCercle.size();i++) {

			Murs cercle = obstaclesCercle.get(i);

			//pythagore de la distance entre les centres de la bille et l"obstacle si inferieure a la somme des deux rayons donc collision 
			if(Math.hypot((uneBille.getPosition().getX()+uneBille.getDiametre()/2)-(cercle.getPositionMursX()), (uneBille.getPosition().getY()+uneBille.getDiametre()/2)-(cercle.getPositionMursY())) < (uneBille.getDiametre()/2 + cercle.getDiametre()/2)) {

				col = false;

				System.out.println("pos x de bile : "+uneBille.getPosition().getX());

				System.out.println("centre du cerlce : "+ cercle.getPositionMursX() );

				if(uneBille.getVitesse().getX() + uneBille.getPosition().getX() > cercle.getPositionMursX()){

					if(uneBille.getPosition().getX() > cercle.getPositionMursX() && col  ){

						double vitX =uneBille.getVitesse().getX() +0.4; 

						Vecteur2D VitYnegatif = new Vecteur2D(vitX,uneBille.getVitesse().getY()*-1);

						uneBille.setVitesse(VitYnegatif);

					}

				}

				if(uneBille.getVitesse().getX() + uneBille.getPosition().getX() < cercle.getPositionMursX()){

					if(uneBille.getPosition().getX() > cercle.getPositionMursX() && col  ){

						double vitX =uneBille.getVitesse().getX() -0.4; 

						Vecteur2D VitYnegatif = new Vecteur2D(vitX,uneBille.getVitesse().getY()*-1);

						uneBille.setVitesse(VitYnegatif);
					}
				}

				boolean colY = false;
				boolean colX = false;

				if(uneBille.getPosition().getX() < cercle.getPositionMursX()) {

					System.out.println("ON YOUR LEFT");

					double vitX =uneBille.getVitesse().getX() +0.4; 

					Vecteur2D VitYnegatif = new Vecteur2D(vitX*-1,uneBille.getVitesse().getY()*-1);

					uneBille.setVitesse(VitYnegatif);
					colX = true;


				}if(uneBille.getPosition().getX() > cercle.getPositionMursX()){
					colY = true;
				}

				if(uneBille.getVitesse().getY() + uneBille.getPosition().getY() > cercle.getPositionMursY() && colY ){

					double vitX =uneBille.getVitesse().getX() +0.4; 

					Vecteur2D VitYnegatif = new Vecteur2D(vitX,uneBille.getVitesse().getY()*-1);

					uneBille.setVitesse(VitYnegatif);



				}	

				if(uneBille.getVitesse().getX() + uneBille.getPosition().getX() > cercle.getPositionMursX() && colX ){


					double vitX =uneBille.getVitesse().getX() -0.4; 

					Vecteur2D VitYnegatif = new Vecteur2D(vitX,uneBille.getVitesse().getY()*-1);

					uneBille.setVitesse(VitYnegatif);


				}
			}


		}	




		//collision entre la bille et les surfaces en pentes.

		for (int i= 0; i < pentes.size();i++) {


			MursDroits pente = pentes.get(i);



			Line2D.Double line = new Line2D.Double(pente.getCoordX1(),pente.getCoordY1(),pente.getCoordX2(),pente.getCoordY2());

			if(line.ptSegDist(uneBille.getPosition().getX()+uneBille.getDiametre()/2,uneBille.getPosition().getY()+uneBille.getDiametre()/2) < uneBille.getDiametre()/2) {


				Vecteur2D x = new Vecteur2D(pente.getCoordX1(),pente.getCoordY1());

				Vecteur2D y = new Vecteur2D(pente.getCoordX2(),pente.getCoordY2());

				Vecteur2D temp = x.soustrait(y);

				double dx = temp.getX();
				double dy = temp.getY();

				Vecteur2D fini = new Vecteur2D(dy*-3,dx);

				uneBille.setVitesse(fini);


			}

		}


		//collision avec les surfaces planes (sol)
		for(int i =0; i < solHorizontal.size();i++) {

			MursDroits sol = solHorizontal.get(i);

			if(uneBille.getPosition().getY() +uneBille.getDiametre() > sol.getCoordY1() && uneBille.getPosition().getX() > sol.getCoordX1() && uneBille.getPosition().getX() < sol.getCoordX2())  {




				double vitX =uneBille.getVitesse().getX(); 

				Vecteur2D VitYnegatif = new Vecteur2D(vitX,uneBille.getVitesse().getY()*-1);

				uneBille.setVitesse(VitYnegatif);

			}




		}

		for(int i=0;i < murs.size();i++) {

			MursDroits mur = murs.get(i);

			if(uneBille.getPosition().getX()+ uneBille.getDiametre() > mur.getCoordX1() && uneBille.getPosition().getY() > mur.getCoordY1() && uneBille.getPosition().getY() < mur.getCoordY2()) {

				Vecteur2D VitYnegatif = new Vecteur2D(uneBille.getVitesse().getX()*-1,uneBille.getVitesse().getY());

				uneBille.setVitesse(VitYnegatif);
			}
		}

		if(uneBille.getPosition().getY() > hauteurDuComposantMetre) {

			arreter();
			retablirPosition();
		}



		System.out.println("vitesse en y "+uneBille.getVitesse().getY());
		System.out.println("position en y "+uneBille.getPosition().getY());

	}



	//Carlos Eduardo
	/**
	 * Demarre le thread s'il n'est pas deja demarre
	 */
	public void demarrer() {
		uneBille.setForceExterieureAppliquee( new Vecteur2D(0,0.8));
		uneBille.setVitesse(new Vecteur2D(0.22,-3.8));
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



		repaint();
	}


	//Carlos Eduardo
	/**
	 * Avance la simulation d'une unique image 
	 */
	public void prochaineImage() {
		calculerUneIterationPhysique(deltaT);
		testerCollisionsEtAjusterPositions();
		repaint();

	}

	//Audrey Viger
	/**
	 * Modifie la constante du ressort
	 * @param kRessort la constante du ressort, exprime en N/m
	 */
	public void setkRessort(double kRessort) {
		ressort.setkRessort(kRessort);
		repaint();
	}// fin methode

	//Audrey Viger
	/**
	 * Modifie le coefficient de friction cinétique du bloc
	 * 
	 * @param coeffFrot le coefficient de friction cinétique du bloc
	 */

	public void setCoeffFrot(double coeffFrot) {
		ressort.setMu(coeffFrot);
		repaint();
	}// fin methode


	//Audrey Viger
	/**
	 * Modifie la position du bloc en ajoutant l'etirement choisi à la poisition naturelle du ressort
	 * @param etirement distance entre le bloc et la position naturelle du ressort
	 */
	public void setEtirement(double etirement) {
		ressort.setPosition(new Vecteur2D(positionInitialRessort.getX() , positionInitialRessort.getY()+ etirement));
		repaint();
	}// fin methode

	//Audrey Viger
	/**
	 * Transmettre la constante initiale du ressort à l'application
	 * @return la constante initiale du ressort qui est 500 N/m
	 */
	public double getK_RESSORT() {
		return K_RESSORT;
	}// fin methode

	//Carlos Eduardo
	/**
	 * Modifie la somme des forces agissant sur les objets de la scene
	 * (ce qui aura pour effet de modifier leur acceleration)
	 * @param forceX La force totale exercee en X
	 * @param forceY La force totale exercee en Y
	 */

	public void setForces(double forceX, double forceY) { 
		//dans cette application, les forces ne sont pas calculées : elles
		//sont plutôt directement données par l'utilisateur! (peu probable dans une vraie application!)
		uneBille.setForceExterieureAppliquee( new Vecteur2D(0,0));
	}

	//Audrey Viger
	/**
	 * Transmettre l'étirement initial du ressort à l'application
	 * @return l'étirement initial du ressort qui est 0 cm
	 */

	public double getETIREMENT_NAT() {
		return ETIREMENT_NAT;
	}// fin methode

	//Carlos Eduardo
	/**
	 * S'informe de la masse de la balle, pour permettre a l'application de l'afficher
	 * @return La masse de la balle (qui ne change pas dans cette version de l'applicaion)
	 */
	public double getMasseBalle() {

		return uneBille.getMasseEnKg();
	}

	/**
	 * Transmettre la masse initiale du bloc à l'application
	 * @return la masse initiale du bloc qui est 700 en grammes
	 */

	public double getMASSE_POUR_CETTE_SCENE() {
		return MASSE_POUR_CETTE_SCENE;
	}// fin methode

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
	 * Méthode qui initialise la bille en par rapport à sa position, son diametre et sa masse pour cette scene
	 */
	private void initialiseBille() {
		uneBille = new Bille(posInitBalle,diametreBallePourCetteScene);
		uneBille.setMasseEnKg(massePourCetteScene);
	}
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
	 * Méthode qui rajoute aux différentes listes d'obstacles les murs qui leur sont associes
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

		pentes.add(lignePencheTrapezeDroite);
		pentes.add(lignePencheTrapezeGau);
		pentes.add(ligTriDroitGau);
		pentes.add(ligTriGaucheDroit);

		murs.add(tunnelRessortDroite);
		murs.add(tunnelRessortGauche);
	}


	public Bille getBille() {

		return uneBille;
	}





}
