package moteur;

import geometrie.Bille;
import geometrie.Flipper;
import geometrie.Vecteur2D;

/**
 * Cette classe regroupera les calculs physiques nécessaires au mouvement des objets
 * des divers objets dans la scène.Utilise la méthode n'intégration numérique d'Euler semi-implicite 
 * pour ce qui est de la modification des positions, vitesse, acceleration sur un intervalle de temps. 
 *  
 * @author Carlos Ed
 * @author Caroline Houle
 * @author Audrey Viger
 * @author Thomas Bourgault
 */

public class MoteurPhysique {

	private static final double ACCEL_GRAV = 9.8066;
	private static final double EPSILON = 1e-10; //tolerance utilisee dans les comparaisons reelles avec zero+

	private static final double K = 9e+10;
	//Carlos Eduardo
	/**
	 * Calcule et retourne l'acceleration en utilisant F=ma
	 * @param sommeDesForces Somme des forces appliquees
	 * @param masse Masse del'objet
	 * @return l'acceletation F/m
	 * @throws Exception Erreur si la masse est pratiquement nulle
	 */
	public static Vecteur2D calculAcceleration(Vecteur2D sommeDesForces, double masse) throws Exception { 
		if(masse < EPSILON) 
			throw new Exception("Erreur MoteurPhysique: La masse étant nulle ou presque l'accéleration ne peut pas etre calculée.");
		else
			return new Vecteur2D( sommeDesForces.getX()/masse , sommeDesForces.getY()/masse );	
	}
	//Carlos Eduardo
	/**
	 * Calcule et retourne la nouvelle vitesse, deltaT secondes plus tard, en utilisant l'algorithme
	 * d'Euler semi-implicite.
	 * @param deltaT L'intervalle de temps (petit!) en secondes
	 * @param vitesse La vitesse initiale au debut de l'intervalle de temps, en m/s
	 * @param accel L'acceleration initiale au debut de l'intervalle de temps, en m/s2
	 * @return La nouvelle vitesse (a la fin de l'intervalle)
	 */
	public static Vecteur2D calculVitesse(double deltaT, Vecteur2D vitesse, Vecteur2D accel) {
		Vecteur2D deltaVitesse = Vecteur2D.multiplie(accel, deltaT);
		Vecteur2D resultVit = vitesse.additionne( deltaVitesse );
		return new Vecteur2D(resultVit.getX(), resultVit.getY());

	}
	//Carlos Eduardo
	/**
	 * Calcule et retourne la nouvelle position, deltaT secondes plus tard, en utilisant l'algorithme
	 * d'Euler semi-implicite.
	 * @param deltaT L'intervalle de temps (petit!) en secondes
	 * @param position La position initiale au debut de l'intervalle de temps, en m
	 * @param vitesse La vitesse initiale au debut de l'intervalle de temps, en m/s
	 * @return La nouvelle position (a la fin de l'intervalle)
	 */

	public static Vecteur2D calculPosition(double deltaT, Vecteur2D position, Vecteur2D vitesse) {
		Vecteur2D deltaPosition = Vecteur2D.multiplie(vitesse, deltaT);
		Vecteur2D resultPos = position.additionne(deltaPosition); 
		return new Vecteur2D(resultPos.getX(), resultPos.getY());

	}
	//Audrey Viger
	/**
	 * Calcule et retourne la force de rappel du ressort
	 * @param kRessort La constante du ressort 
	 * @param etirement La quantite d'etirement du ressort par rapport a sa position de repos
	 * @return Le vecteur de force de rappel
	 */
	public static Vecteur2D calculForceRappel(double kRessort, Vecteur2D etirement) {
		return new Vecteur2D(0 ,-kRessort * etirement.getY() );
	}
	//Carlos Eduardo
	/**
	 * Calcule et retourne la force gravitationnelle
	 * @param masse La masse de l'objet
	 * @return Le vecteur de force gravitationnelle
	 */
	public static Vecteur2D calculForceGrav(double masse) {
		return new Vecteur2D(0, ACCEL_GRAV * masse);
	}
	//Carlos Eduardo
	/**
	 * Calcule et retourne la force normale
	 * @param masse La masse de l'objet
	 * @return Le vecteur de force normale
	 */
	public static Vecteur2D calculForceNormale(double masse) {
		return new Vecteur2D(0, ACCEL_GRAV * masse);
	}


	//Audrey Viger
	/**
	 * Calcule et retourne la force de friction
	 * @param mu Le coefficient friction cinetique
	 * @param masse La masse de l'objet (pour calcul de force normale)
	 * @param vitesse La vitesse de l'objet (pour pouvoir opposer la force de friction à celle-ci)
	 * @return Le vecteur de force de friction
	 */


	public static Vecteur2D calculForceFriction(double mu, double masse, Vecteur2D vitesse) {
		if (vitesse.getY() > 0 ) {
			return new Vecteur2D(0,-mu * calculForceNormale(masse).getX());
		} else { 
			return new Vecteur2D(0,mu * calculForceNormale(masse).getX() );
		}
		
	}
	//Carlos Eduardo
	/**
	 *  Calcul et retourne l'energie cinetique de la balle a partir de l'energie potentiel du ressort
	 * @param k La constante de rappel du ressort
	 * @param etirement l'etirement de la pos initial et final du ressort
	 * @param masse masse de la bille en kg
	 * @return Le vecteur de l'energie cinetique
	 */
	public static Vecteur2D caculVitesseBilleRessort(double k, double etirement,double masse){

		double energiePotentiel =  (k*Math.pow(etirement, 2))/2;


		double energieCinetique;

		energieCinetique = Math.pow(2*energiePotentiel/masse, 0.5);

		return new Vecteur2D (0,energieCinetique*-1);
	}

	//Carlos Eduardo
	/**
	 * 
	 * @param vitesseFlipper vitesse du flipper
	 * @param vitesseBille	vitesse de la bille
	 * @return la nouvelle vitesse de la bille frapper par le flipper
	 */
	public static Vecteur2D calculVitesseBilleFlipper(double vitesseFlipper,Vecteur2D vitesseBille) {

		vitesseBille.multiplie(vitesseFlipper);

		return vitesseBille;
	}
	//Carlos Eduardo
	/**
	 * Methode qui donne la vitesse de la bille
	 * @param billeVitesse est un objet de type bille
	 * @return null
	 */

	public static Vecteur2D calculRebondBilleCerlce (Bille billeVitesse) {


		billeVitesse.getVitesse();

		return null;
	}
	
	//Carlos Eduardo
	/**Methode qui calcule et retourne la force Centripete avec la masse de la bille , sa vitesse et le rayon de la courbe
	 * 
	 * @param masse de la bille en kg
	 * @param vitesse de la bille en ms
	 * @param rayon de la courbe du cercle
	 * @return force centripete de la bille
	 */
	public static double calculForceCentripete(double masse, Vecteur2D vitesse, double rayon) {

		double temp =masse*Math.pow(vitesse.module(), 2);

		double fc = temp/rayon;

		return fc;
	}

	//Carlos Eduardo
	/**
	 * Methode qui calcule et retourne l'angle de la Force Centripete dirigee vers le centre du cercle
	 * 
	 * @param centre positon du cercle
	 * @param positionCourbe ; position de la bille Sur la courbe
	 * @return un vecteur contenant les x et y de l'angle qui forme le FC
	 */
	public static Vecteur2D calculDelta(Vecteur2D centre,Vecteur2D positionCourbe ) {


		double deltaY =	positionCourbe.getY()-centre.getY();


		double deltaX =	positionCourbe.getX()-centre.getX();

		deltaY = Math.abs(deltaY);

		deltaX= Math.abs(deltaX);



		double hypo = Math.pow(deltaY*deltaY + deltaX*deltaX , 0.5);



		double temp = deltaY/hypo;

		double tempo = Math.asin(temp);

		double angle = Math.toDegrees(tempo);


		return new Vecteur2D(deltaX,deltaY);

	}

	//Carlos Eduardo
	/**
	 * Methode qui calcule la force electrique 
	 * @param q1 est la charge de la premiere particule
	 * @param q2 est la charge de la deuxieme particule
	 * @param r est le rayon de la particule
	 * @return la force electrique
	 */
	public static double forceElectrique(double q1, double q2,double r) {

		double temp =K*q1*q2*Math.pow(1.602e-19, 2);

		double distance = Math.pow(r, 2);

		double Fe =temp/distance;

		return Fe;		
	}
	
	/**
	 * 
	 * @param pos1 position du premier point du vecteur 
	 * @param pos2 position du 2e point du vecteur
	 * @return un vecteur perpendiculaire au vecteur
	 * @throws Exception 
	 */
	public static Vecteur2D calculPerpendiculaire(Vecteur2D pos1,Vecteur2D pos2) throws Exception {
		// TODO Auto-generated method stub
		
		double x = pos1.getX()-pos2.getX();
		double y = pos1.getY()-pos2.getY();
		
		x = Math.abs(x);
		y =	Math.abs(y);
		
		Vecteur2D vecTemp =new Vecteur2D(-x,-y);
		
		vecTemp = vecTemp.normalise();
		
		System.out.println(vecTemp);
		
		return vecTemp;
		
		
	
	}


	//Thomas Bourgault
	/**
	 * Méthode qui calcule l'angle d'un flipper avec l'équation d'un mouvement harmonique simple
	 * @param angleMax est l'angle maximum qu'un flipper peut atteindre
	 * @param deltaT est le pas d'animation
	 * @param frequenceAngulaire est la vitesse à quel point une oscillation complète peut être effectue
	 * @return l,angle du flipper selon le deltaT (pas d'animation)
	 */
	public static double calculAngle(double angleMax, double tempsTotalEcoule, double frequenceAngulaire,boolean premierQuartPeriode) {
		if( premierQuartPeriode) {
			double angle;
			angle=angleMax*Math.sin(frequenceAngulaire*tempsTotalEcoule+Math.PI/2);		
			return angle;		
		}else {
			double angle;
			angle=angleMax*Math.sin(frequenceAngulaire*tempsTotalEcoule+Math.PI);		
			return angle;		
		}

	}
	//Thomas Bourgault
	/**
	 * Méthode qui calcule la vitesse d'un flipper avec l'équation d'un mouvement harmonique simple
	 * @param angleMax est l'angle maximum qu'un flipper peut atteindre
	 * @param deltaT est le pas d'animation
	 * @param frequenceAngulaire est la vitesse à quel point une oscillation complète peut être effectue
	 * @return la vitesse du flipper selon le temps ecoule depuis que le flipper est active
	 */
	public static Vecteur2D vitesseFlipper(double angleMax, double tempsTotalEcoule, double frequenceAngulaire,boolean premierQuartPeriode) {
		if( premierQuartPeriode) {

			double vy=angleMax*frequenceAngulaire*Math.cos(frequenceAngulaire*tempsTotalEcoule+Math.PI/2);
			Vecteur2D vitesse=new Vecteur2D(0,vy);
			return vitesse;
		}else {
			double vy=angleMax*frequenceAngulaire*Math.cos(frequenceAngulaire*tempsTotalEcoule+Math.PI);
			Vecteur2D vitesse=new Vecteur2D(0,vy);
			return vitesse;
		}

	}
	




}