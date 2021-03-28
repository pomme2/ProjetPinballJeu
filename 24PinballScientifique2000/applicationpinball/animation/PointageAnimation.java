package animation;

public class PointageAnimation {

	protected int score = 0;

	

	public int updateScore(int points) {
		
		score=score+points;
		
		return score;
	}
	
	
	public int resetScore() {
		setScore(0);
		
		return score;
		
	}
	
	public int getScore() {
		return score;
	}


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
	
	public int timerScore() {
		
		score= score+1;
		
		return score;
	}
	

}
