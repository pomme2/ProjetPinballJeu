package animation;

/**
 * @author Carlos Eduardo
 * Composant illustrant la simujlation : c'est la scence physique ou on  dessine et anime les objets.
 * C,est ce composant qui doit connaitre/determiner les parametres physiques : largeur du composant en metres, 
 * valeur du pas de simulation deltaT, positions et vitesses initiales des objets etc. etc.
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Path2D.Double;

import javax.swing.JPanel;

import geometrie.Bille;
import geometrie.Flipper;
import geometrie.Murs;
import geometrie.Vecteur2D;

public class ZoneSimulationPhysique extends JPanel implements Runnable{
	private static final long serialVersionUID = 1L;

	private double deltaT = 0.009;
	private double largeurDuComposantEnMetres = 1.2;

	private double diametreBallePourCetteScene = 0.2;  //em mètres
	private double massePourCetteScene = 0.3; //en kg

	private Vecteur2D posInitBalle = new Vecteur2D(3, 2);  //position intiale pour la balle
	private Vecteur2D vitInitBalle = new Vecteur2D(0, 0);  //vitesse intiale pour la balle
	private Vecteur2D accelInitBalle = new Vecteur2D(0, 0);  //acceleration intiale pour la balle
	private Path2D.Double sol,mur ;
	
	//position intiales pour la bille
	private Bille uneBille;
	private Flipper unFlipper;
	private boolean enCoursDAnimation= false;
	private double tempsTotalEcoule = 0;
	private int tempsDuSleep = 10;
	
	private Murs cercleThomas;
	private Area billeJeu ,obstacle1;
	
	private double billeCentreX = diametreBallePourCetteScene/2;
	private double billeCentreY= diametreBallePourCetteScene/2;

	
	/**
	 * Cree une scene qui contient (pour l'instant) une seule balle au repos
	 */
	public ZoneSimulationPhysique() {	
		setBackground(Color.lightGray);
		
		//on cree une balle au repos
		uneBille = new Bille(posInitBalle,diametreBallePourCetteScene);
		uneBille.setMasseEnKg(massePourCetteScene);
		
		//unFlipper = new Flipper();
	
	}//fin du constructeur
	

	/**
	 * Permet de dessiner une scene qui inclut ici une simple balle en mouvement
	 * @param g Contexte graphique
	 */
	@Override
	public void paintComponent(Graphics g) {		
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;	
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		
		double pixelsParMetre = getWidth()/largeurDuComposantEnMetres;
		
		g2d.setColor(Color.red);
		uneBille.setPixelsParMetre(pixelsParMetre);
		uneBille.dessiner(g2d);
		
		
		
		cercleThomas = new Murs(1,3,0.5);
		cercleThomas.setPixelsParMetre(pixelsParMetre);
		
		cercleThomas.dessiner(g2d);
		
		sol = new Path2D.Double();
		sol.moveTo(0, getHeight()-10);
		sol.lineTo(getWidth(), getHeight()-10);
		g2d.draw(sol);
		
		mur = new Path2D.Double();
		mur.moveTo(getWidth()/8, 0);
		mur.lineTo(getWidth()/8, getHeight());
		g2d.draw(mur);
		
		g2d.setColor(Color.yellow);
		g2d.drawString("Testing pour collisions ",10,  getHeight()-10);
		
		

		unFlipper.dessiner(g2d);

		//unFlipper.dessiner(g2d);

		
	}//fin paintComponent


	/**
	 * Animation de la balle
	 */
	@Override
	public void run() {
		while (enCoursDAnimation) {	
			System.out.println("Un tour de run...on avance de " + deltaT + " secondes");
			calculerUneIterationPhysique(deltaT);
			testerCollisionsEtAjusterPositions();//pas utile pour le moment
			repaint();
			try {
				Thread.sleep(tempsDuSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}//fin while
		System.out.println("Le thread est mort...");
	}
	
	/**
	 * Cette methode pourrait servir à tester si des objets de la scene
	 * sont en collision. Si oui : on calculerait par exemple les rebonds et
	 * en déduirait des nouvelles accelerations, vitesses, positions
	 * Pour cet exemple, on laissera cette methode vide.
	 */
	private void testerCollisionsEtAjusterPositions() {	
		//ici vous pourriez au besoin faire des tests de collisions
		//et des ajustements d'acceleration, vitesse et position
		
		//System.out.println(uneBille.getPosition().getY());
		
	if((uneBille.getPosition().getY()+diametreBallePourCetteScene)*100 > sol.getBounds().y){	
		
		 Vecteur2D vitActu = uneBille.getVitesse();
		 double vitX =uneBille.getVitesse().getX(); 
		 Vecteur2D VitYnegatif = new Vecteur2D(vitX,uneBille.getVitesse().getY()*-1);
		 
		 
		uneBille.setVitesse(VitYnegatif);
		
	}
	if(uneBille.getPosition().getX()*100 < mur.getBounds().x ) {
		
		Vecteur2D vitesseNegatif = new Vecteur2D (uneBille.getVitesse().getX()*-1,uneBille.getVitesse().getY());
		uneBille.setVitesse(vitesseNegatif);
		System.out.println("Collision mur");
		
		
	
		
	}
	if(Math.hypot((uneBille.getPosition().getX()+uneBille.getDiametre()/2)-(cercleThomas.getPositionMursX()), (uneBille.getPosition().getY()+uneBille.getDiametre()/2)-(cercleThomas.getPositionMursY())) < (uneBille.getDiametre()/2 + cercleThomas.getDiametre()/2)) {
		
		double vitX =uneBille.getVitesse().getX(); 
		
		Vecteur2D VitYnegatif = new Vecteur2D(vitX*-1,uneBille.getVitesse().getY()*-1);
		uneBille.setVitesse(VitYnegatif);
		
	}
	
		
	System.out.println("position bille: "+uneBille.getPosition().getX());
	System.out.println("mur pos en x:"+mur.getBounds().x );
	
	System.out.println("BILLE "+uneBille.getDiametre()/2);
	System.out.println("cercle "+cercleThomas.getDiametre()/2);
	
	System.out.println("Min Dist "+(uneBille.getDiametre()/2 + cercleThomas.getDiametre()/2));
	System.out.println((Math.hypot((uneBille.getPosition().getX()-uneBille.getDiametre())-(cercleThomas.getPositionMursX()), (uneBille.getPosition().getY()-uneBille.getDiametre())-(cercleThomas.getPositionMursY()))));
	
	
	
	}

	/**
	 * Calcul des nouvelles positions pour
	 * tous les objets de la scène
	 */
	private void calculerUneIterationPhysique(double deltaT) {
		tempsTotalEcoule += deltaT;
		uneBille.avancerUnPas( deltaT );

		System.out.println("\nNouvelle accel: " + uneBille.getAccel().toString(2));
		System.out.println("Nouvelle vitesse: " + uneBille.getVitesse().toString(2));
		System.out.println("Nouvelle position: " + uneBille.getPosition().toString(2));
		
		System.out.println("\nTemps total simulé écoulé: "  + String.format("%.3f",tempsTotalEcoule) + "sec (en temps simulé!)");
	
		
	}

	/**
	 * Demarre le thread s'il n'est pas deja demarre
	 */
	public void demarrer() {
		uneBille.setForceExterieureAppliquee( new Vecteur2D(-0.7,4.9));
		if (!enCoursDAnimation) { 
			Thread proc = new Thread(this);
			proc.start();
			enCoursDAnimation = true;
		}
	}//fin methode
	
	/**
	 * Demande l'arret du thread (prochain tour de boucle)
	 */
	public void arreter() {
		enCoursDAnimation = false;
	}//fin methode

	/**
	 * Reinitialise la position et la vitesse de la balle
	 */
	public void retablirPosition() {
		arreter();
		uneBille.setPosition(posInitBalle);
		uneBille.setVitesse(vitInitBalle);
		uneBille.setAccel(accelInitBalle);
		tempsTotalEcoule = 0;
		repaint();
	}


	/**
	 * Avance la simulation d'une unique image 
	 */
	public void prochaineImage() {
		calculerUneIterationPhysique(deltaT);
		testerCollisionsEtAjusterPositions();
		repaint();
		
	}
	

	/**
	 * Modifie la somme des forces agissant sur les objets de la scene
	 * (ce qui aura pour effet de modifier leur acceleration)
	 * @param forceX La force totale exercee en X
	 * @param forceY La force totale exercee en Y
	 */
	public void setForces(double forceX, double forceY) { 
		//dans cette application, les forces ne sont pas calculées : elles
		//sont plutôt directement données par l'utilisateur! (peu probable dans une vraie application!)
		uneBille.setForceExterieureAppliquee( new Vecteur2D(0,0));
	}

	/**
	 * S'informe de la masse de la balle, pour permettre a l'application de l'afficher
	 * @return La masse de la balle (qui ne change pas dans cette version de l'applicaion)
	 */
	public double getMasseBalle() {
		
		return uneBille.getMasseEnKg();
	}

	/**
	 * Change le temps pour le sleep du thread
	 * @param tempsDuSleep Nouveua temps a appliquer au sleep
	 */
	public void setTempsDuSleep(int tempsDuSleep) {
		this.tempsDuSleep = tempsDuSleep;
	}
	
	/**
	 * Retourne le temps de sleep actuel
	 * @return temps du sleep actuel
	 */
	public int getTempsDuSleep() {
		return tempsDuSleep;
	}

	/**
	 * Modifie le pas (intervalle) de la simulation
	 * @param deltaT le pas (intervalle) de la simulation, exprime en secondes
	 */
	public void setDeltaT(double deltaT) {
		this.deltaT = deltaT;
	}
	
	/**
	 * Retourne le pas intervalle) de la simulation
	 * @return le pas intervalle) de la simulation, exprime en secondes
	 */
	public double getDeltaT() {
		return (deltaT);
	}

	
}//fin classe