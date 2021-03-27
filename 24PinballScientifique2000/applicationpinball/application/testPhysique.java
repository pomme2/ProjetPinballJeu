package application;

import geometrie.Vecteur2D;

public class testPhysique {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Vecteur2D centre = new Vecteur2D(6,9);
		
		Vecteur2D courbe = new Vecteur2D(14,22);
		
		
		 Vecteur2D rep = moteur.MoteurPhysique.calculAngleVectorForceCentripete(centre, courbe);
		
		System.out.println(rep);

	}

}
