package moteur;

import geometrie.Vecteur2D;

/**
 * Cette classe regroupera les calculs physiques nécessaires au mouvement des objets
 * des divers objets dans la scène.Utilise la méthode n'intégration numérique d'Euler semi-implicite 
 * pour ce qui est de la modification des positions, vitesse, acceleration sur un intervalle de temps. 
 *  
 * @author Carlos Ed
 * @author Caroline Houle
 * @author Audrey Viger
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
			throw new Exception("Erreur MoteurPhysique: La masse étant nulle ou presque l'accéleration ne peut pas etre calculée.");
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
	 * @param vitesse La vitesse de l'objet (pour pouvoir opposer la force de friction à celle-ci)
	 * @return Le vecteur de force de friction
	 */
		
		
		public static Vecteur2D calculForceFriction(double mu, double masse, Vecteur2D vitesse) {
		if (vitesse.getX() > 0 ) {
			return new Vecteur2D(-mu * calculForceNormale(masse).getY(), 0);
		} else { 
			return new Vecteur2D(mu * calculForceNormale(masse).getY(), 0);
		}
	}

}