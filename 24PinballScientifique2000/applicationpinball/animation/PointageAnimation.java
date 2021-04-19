package animation;
/**
 * La classe permet de mettre a jour le pointage lors des collisions avec
 * les obstacles.
 * 
 * @author Carlos Eduardo
 *
 */
public class PointageAnimation {

	protected int score = 0;
	
	static int scoreFinal = 0;
/**
 * 
 * @param points le  nombre de points que cet obstacles donne au joueur
 * @return le score total
 */
	public int updateScore(int points) {
		
		score=score+points;
		
		return score;
	}
	
	/**
	 * Remet le score total a 0
	 * @return le score a 0
	 */
	public int resetScore() {
		setScore(0);
		
		return score;
		
	}
	/**
	 * Methode qui donne acces au score
	 * @return le score total
	 */
	public int getScore() {
		return score;
	}
	/**
	 * Methode qui permet le score total;
	 * @param score
	 */

	public void setScore(int score) {
		this.score = score;
	}
	
	/**
	 * Genere une chaine de caractere avec les informations  du vecteur
	 */
	@Override
	public String toString(){
		return ""+score+"";		
	}	
	
	/**
	 * methode ajoute 1point pour chaque seconde que l'animation roule
	 * @return score total
	 */
	public int timerScore() {
		
		score= score+1;
		
		return score;
	}
	
	
	

}
