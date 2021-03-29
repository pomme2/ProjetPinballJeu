package animation;

import java.util.ArrayList;

import geometrie.Bille;
import geometrie.Flipper;
import geometrie.Murs;
import geometrie.MursDroits;
import geometrie.Ressort;
import geometrie.Vecteur2D;
/**
 * Classe qui permet d'instancier tous les paramètres reliés liés au jeu. 
 * @author thomas, Carlos, Audrey
 *
 */

public class Scene {
	private double pixelParMetre=500;
	//Ressort Audrey
	private Ressort ressort;
	private final Vecteur2D positionInitialRessort = new Vecteur2D(1.009,1.272);
	private final Vecteur2D VITESSE_INIT_RESSORT = new Vecteur2D(0,-0.0000001 ); 
	private final Vecteur2D ACCEL_INIT_RESSORT = new Vecteur2D(0, 0); 

	private final int TEMPS_DU_SLEEP = 25;
	private  double K_RESSORT = 50;
	private final double ETIREMENT_NAT = 0.1;

	private final double COEFF_FROT = 0.64;
	private final double MASSE_POUR_CETTE_SCENE = 0.7; // en kg

	private double diametreBallePourCetteScene = 0.03;  //em mètres
	private double massePourCetteScene = 0.1; //en kg

	private Vecteur2D posInitBalle;  //position intiale pour la balle
	private Vecteur2D vitInitBalle = new Vecteur2D(0, 0);  //vitesse intiale pour la balle
	private Vecteur2D accelInitBalle = new Vecteur2D(0, 0);  //acceleration intiale pour la balle


	//position intiales pour la bille
	private Bille uneBille;

	//tableau pour obstacles
	private ArrayList<Murs> obstaclesCercle = new ArrayList<Murs>();

	//tab pour mursHorizontales (sol)
	private ArrayList<MursDroits> solHorizontal = new ArrayList<MursDroits>();

	//tab pour les pentes
	private ArrayList<MursDroits> pentes = new ArrayList<MursDroits>();

	private ArrayList<MursDroits> droitSous = new ArrayList<MursDroits>();
	ArrayList<MursDroits> murs = new ArrayList<MursDroits>();

	//liste des coordonnes en X et Y des courbes
	ArrayList<Double> arcCercleGauCoordX=new ArrayList<Double>();
	ArrayList<Double> arcCercleGauCoordY=new ArrayList<Double>();
	ArrayList<Double> arcCercleDroitCoordX=new ArrayList<Double>();
	ArrayList<Double> arcCercleDroitCoordY=new ArrayList<Double>();
	ArrayList<Double> arcCerclePetitCoordX=new ArrayList<Double>();
	ArrayList<Double> arcCerclePetitCoordY=new ArrayList<Double>();

	//tab avec flippers

	private ArrayList<MursDroits> flipperGauche= new ArrayList<MursDroits>();

	private ArrayList<MursDroits> flipperDroit= new ArrayList<MursDroits>();
	//flippers		
	private Flipper flipGauche,flipDroit;
	
/**
 * Constructeur qui permet d'initialiser la bille et le ressort
 */
	public Scene() {		
		//ressort
		initialiserRessort();
		//bille
		initialiseBille();
		
		
	}
	//Carlos Eduardo
	/**
	 * Méthode qui initialise la bille en par rapport à sa position, son diametre et sa masse pour cette scene
	 */
	private void initialiseBille() {
		posInitBalle = new Vecteur2D(ressort.getMurs().getCoordX1()+diametreBallePourCetteScene, ressort.getMursY()-diametreBallePourCetteScene + getEtirement());
		uneBille = new Bille(posInitBalle,diametreBallePourCetteScene);
		uneBille.setMasseEnKg(massePourCetteScene);
	}	
	
	//Audrey Viger
	/**
	 * Méthode qui permet d'initialiser le ressort par rapport à sa position, son coeff de frottement, sa constante k, sa vitesse et sa masse
	 */
	private void initialiserRessort() {
		ressort = new Ressort(positionInitialRessort,0.088,0.192);
		ressort.setkRessort(K_RESSORT);
		ressort.setMu(COEFF_FROT);
		ressort.setVitesse(VITESSE_INIT_RESSORT);
		ressort.setMasseEnKg(MASSE_POUR_CETTE_SCENE);
		ressort.setVitesse(VITESSE_INIT_RESSORT);
	}
	//Audrey Viger
		/**
		 * méthode qui permet de retourner l'étirement du ressort
		 * @return l'étirement du ressort
		 */
		public double getEtirement() {
			double etirement = ressort.getPosition().getY()-positionInitialRessort.getY();

			return etirement;
		}

}
