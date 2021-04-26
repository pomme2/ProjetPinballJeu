package animation;
/**
 * La classe permet de mettre a jour le pointage lors des collisions avec
 * les obstacles.
 * 
 * @author Carlos Eduardo
 *@author Thomas Bourgault
 */
public class PointageAnimation {

	protected int score = 0;
	protected int scoreCalculer=0;
	
	static int scoreFinal = 0;
	//Carlos Eduardo
/**
 * 
 * @param points le  nombre de points que cet obstacles donne au joueur
 * @return le score total
 */
	public int updateScore(int points) {
		
		 score= score+points;
		
		return score;
	}
	
	
	//Carlos Eduardo
	/**
	 * Remet le score total a 0
	 * @return le score a 0
	 */
	public int resetScore() {
		setScore(0);
		
		return score;
		
	}
	//Carlos Eduardo
	/**
	 * Methode qui donne acces au score
	 * @return le score total
	 */
	public int getScore() {
		return score;
	}
	//Carlos Eduardo
	/**
	 * Methode qui permet le score total;
	 * @param score
	 */	
	public void setScore(int score) {
		this.score = score;
	}
	
	//Carlos Eduardo
	/**
	 * Genere une chaine de caractere avec les informations  du vecteur
	 */
	@Override
	public String toString(){
		return ""+score+"";		
	}	
	//Carlos Eduardo
	/**
	 * methode ajoute 1point pour chaque seconde que l'animation roule
	 * @return score total
	 */
	public int timerScore() {
		
		score= score+1;
		
		return score;
	}

	//Thomas Bourgault
    /**
     * Methode qui permet de savoir si le jouer à atteint le score minimum pour activer les formes
     * @return vrai si le jouer a atteint ce score et retoune faux si le jouer n'a pas atteint ce score
     */
    public boolean activerForme() {
        if(score>=2000) {
            return true;
        }else {
            return false;
        }
    }

	
	

}
