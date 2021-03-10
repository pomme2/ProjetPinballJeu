package moteur;

import geometrie.Vecteur2D;

/**
 * Cette classe regroupera les calculs physiques n�cessaires au mouvement des objets
 * des divers objets dans la sc�ne.Utilise la m�thode n'int�gration num�rique d'Euler semi-implicite 
 * pour ce qui est de la modification des positions, vitesse, acceleration sur un intervalle de temps. 
 *  
 * @author Carlos Ed
 * @author Caroline Houle
 *
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

}