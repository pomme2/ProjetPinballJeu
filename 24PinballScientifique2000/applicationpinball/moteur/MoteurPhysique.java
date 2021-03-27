package moteur;

import geometrie.Bille;
import geometrie.Vecteur2D;

/**
 * Cette classe regroupera les calculs physiques n�cessaires au mouvement des objets
 * des divers objets dans la sc�ne.Utilise la m�thode n'int�gration num�rique d'Euler semi-implicite 
 * pour ce qui est de la modification des positions, vitesse, acceleration sur un intervalle de temps. 
 *  
 * @author Carlos Ed
 * @author Caroline Houle
 * @author Audrey Viger
 * @author Thomas Bourgault
 */

public class MoteurPhysique {

	private static final double ACCEL_GRAV = 9.8066;
	private static final double EPSILON = 1e-10; //tolerance utilisee dans les comparaisons reelles avec zero

	/**
	 * Calcule et retourne l'acceleration en utilisant F=ma
	 * @param sommeDesForces Somme des forces appliquees
	 * @param masse Masse del'objet
	 * @return l'acceletation F/m
	 * @throws Exception Erreur si la masse est pratiquement nulle
	 */
	public static Vecteur2D calculAcceleration(Vecteur2D sommeDesForces, double masse) throws Exception { 
		if(masse < EPSILON) 
			throw new Exception("Erreur MoteurPhysique: La masse �tant nulle ou presque l'acc�leration ne peut pas etre calcul�e.");
		else
			return new Vecteur2D( sommeDesForces.getX()/masse , sommeDesForces.getY()/masse );	
	}

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
	/**
	 * Calcule et retourne la force de rappel du ressort
	 * @param kRessort La constante du ressort 
	 * @param etirement La quantite d'etirement du ressort par rapport a sa position de repos
	 * @return Le vecteur de force de rappel
	 */
	public static Vecteur2D calculForceRappel(double kRessort, Vecteur2D etirement) {
		return new Vecteur2D(0 ,-kRessort * etirement.getY() );
	}
	/**
	 * Calcule et retourne la force gravitationnelle
	 * @param masse La masse de l'objet
	 * @return Le vecteur de force gravitationnelle
	 */
	public static Vecteur2D calculForceGrav(double masse) {
		return new Vecteur2D(0, -ACCEL_GRAV * masse);
	}
	/**
	 * Calcule et retourne la force normale
	 * @param masse La masse de l'objet
	 * @return Le vecteur de force normale
	 */
	public static Vecteur2D calculForceNormale(double masse) {
		return new Vecteur2D(0, ACCEL_GRAV * masse);
	}



	/**
	 * Calcule et retourne la force de friction
	 * @param mu Le coefficient friction cinetique
	 * @param masse La masse de l'objet (pour calcul de force normale)
	 * @param vitesse La vitesse de l'objet (pour pouvoir opposer la force de friction � celle-ci)
	 * @return Le vecteur de force de friction
	 */


	public static Vecteur2D calculForceFriction(double mu, double masse, Vecteur2D vitesse) {
		if (vitesse.getY() > 0 ) {
			return new Vecteur2D(0,-mu * calculForceNormale(masse).getX());
		} else { 
			return new Vecteur2D(0,mu * calculForceNormale(masse).getX() );
		}
	}
	
	/**
	 *  Calcul et retourne l'energie cinetique de la balle a partir de l'energie potentiel du ressort
	 * @param k La constante de rappel du ressort
	 * @param etirement l'etirement de la pos initial et final du ressort
	 * @param masse masse de la bille en kg
	 * @return Le vecteur de l'energie cinetique
	 */
	public static Vecteur2D caculVitesseBilleRessort(double k, double etirement,double masse){
		
		double energiePotentiel =  (k*Math.pow(etirement, 2))/2;

		
		
		//System.out.println( "ENERGIE POTENIELLLLL "+energiePotentiel);
		
		double energieCinetique;
		
		energieCinetique = Math.pow(2*energiePotentiel/masse, 0.5);
		
		
		
		return new Vecteur2D (0,energieCinetique*-1);
	}
	
	
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
	
	public static Vecteur2D calculRebondBilleCerlce (Bille billeVitesse) {
		
		
		billeVitesse.getVitesse();
		
		return null;
	}
	
	public static double calculForceCentripete(double masse, Vecteur2D vitesse, double rayon) {
		
		double temp =masse*Math.pow(vitesse.module(), 2);
		
		double fc = temp/rayon;
		
		return fc;
	}
	
	public static Vecteur2D calculAngleVectorForceCentripete(Vecteur2D centre,Vecteur2D positionCourbe ) {
		
		
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
	
	
	
	
	
	//Thomas Bourgault
	/**
	 * 
	 * @return
	 */
	public static Vecteur2D vitesseRotationFlipper(Vecteur2D position, double angle,double deltaT) {
		Vecteur2D vitesse= new Vecteur2D();

		return null;

	}	
	public static Vecteur2D testFlipper (Vecteur2D position,double angle,double longueurManche) {
		Vecteur2D nouvPosition= new Vecteur2D();
		position=position.rotation(position,angle,longueurManche);
		return position;
	}
	//Thomas Bourgault
	/**
	 * M�thode qui calcule l'angle d'un flipper avec l'�quation d'un mouvement harmonique simple
	 * @param angleMax est l'angle maximum qu'un flipper peut atteindre
	 * @param deltaT est le pas d'animation
	 * @param frequenceAngulaire est la vitesse � quel point une oscillation compl�te peut �tre effectue
	 * @return l,angle du flipper selon le deltaT (pas d'animation)
	 */
	public static double calculAngle(double angleMax, double deltaT, double frequenceAngulaire) {
		double angle;
		angle=angleMax*Math.sin(frequenceAngulaire*deltaT+Math.PI/2);		
		return angle;		
	}
	//Thomas Bourgault
	
	public static Vecteur2D vitesseFlipper(double angleMax, double deltaT, double frequenceAngulaire) {
		
		double vy=angleMax*frequenceAngulaire*Math.cos(frequenceAngulaire*deltaT+Math.PI/2);
		Vecteur2D vitesse=new Vecteur2D(0,vy);
		return vitesse;
		
	}
	
	
	
	

}