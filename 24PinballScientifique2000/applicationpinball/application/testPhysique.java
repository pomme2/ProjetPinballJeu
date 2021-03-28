package application;

import animation.PointageAnimation;
import geometrie.Vecteur2D;

public class testPhysique {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Vecteur2D centre = new Vecteur2D(6,9);
		
		Vecteur2D courbe = new Vecteur2D(14,22);
		
		
		
		
		
		System.out.println(moteur.MoteurPhysique.forceElectrique(15, 10, 0.1));

		
		
		PointageAnimation points ;
		
		points = new PointageAnimation();
		
		points.updateScore(1);
		points.updateScore(1);

		points.updateScore(1);

		points.updateScore(1);

		points.updateScore(2);

		System.out.println(points.getScore());
		
		points.resetScore();
		

		System.out.println(points.getScore());

	

	}

}
