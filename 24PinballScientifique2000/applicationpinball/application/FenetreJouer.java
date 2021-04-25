package application;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.JSpinner;
import javax.swing.JSlider;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import application.SceneImage;
import geometrie.Bille;
import animation.CoeurVie;
import animation.PointageAnimation;
import animation.Scene;
import animation.ZonePinball;
import javax.swing.DefaultComboBoxModel;
import animation.PointageAnimation;

public class FenetreJouer extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel panelAvecImage;
	private App24PinballScientifique2001 fenMenu;
	private FenetreOption fenOption;
	private FenetreFinPartie fenFinPartie;
	private FenetreBacSable fenBac;
	private FenetreClassement fenClassement;
	private int valeurInclinaison;
	private int valeurAimant;
	private int valeurRessort;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private Scene scene;
	//carlos affichage des resultats
	private JLabel lblVitesseX;
	private JLabel lblVitesseY;
	private JLabel lblAcceleration;
	private JLabel lblCharge;
	private JLabel lblScore; 

	private Timer minuteurResultats=null;
	private ZonePinball zonePinball;
	private boolean enCoursdAnimation=false;
	private double hauteurDuComposantMetre=1.536;
	private double kRessort=250;
	private JSlider sliderEtirement;
	private CoeurVie vie;
	private DessinCoeur coeur;
	private java.net.URL urlCoeur = getClass().getClassLoader().getResource("Coeur.png");
	private java.net.URL urlArcade = getClass().getClassLoader().getResource("fondEcranJouer.jpg");
	private boolean premiereFoisGameOver=true;
	private boolean coeurVie;
	private String nomFichierSonMenu= ".//Ressource//8BitMenu.wav"; 
	private static String nomFichierRessort= ".//Ressource//bruitRessort.wav"; 
	private static String nomFichierSonJouer=".//Ressource//musiqueJouer.wav";
	private Image backGround,backGroundRedim;
	private boolean musiquePremiereFois=true;
	private boolean premiereFoisJSlider=true;	
	private Musique musiqueMenu;
	private static Musique musiqueJouer=new Musique (nomFichierSonJouer);
	private static Musique musiqueRessort=new Musique(nomFichierRessort);	
	private static String nomFichierSonFinPartie= ".//Ressource//musiqueGameOver.wav"; 
	private static Musique musiqueFinPartie=new Musique(nomFichierSonFinPartie);

	private JComboBox<Object> comboBoxObstacles;
	private JLabel lblScoreDebloquer;



	PointageAnimation score = new PointageAnimation();
	//Variable pour gérer score lors d'une partie
	private int scoreVie;
	private int scoreVie3;
	private int scoreVie2;
	private int scoreVie2Finale;
	private int scoreVie1;
	private boolean coeur2=false;	
	private boolean cliquerObstacle=false;
	private int nbClicObstacle;
	private boolean sliderLache=false;
	private boolean premiereOuverture=true;
	private int constanteVie3Degre=0;
	private int constanteVie2Degre=0;
	private int constanteVie1Degre=0;
	private int scoreBaseDegre=250;
	private int scoreBaseAimant=300;
	private int scoreIncrement=1000;
	private int scoreIncrementAimant=1250;
	private int constanteVie3Aimant=0;
	private int constanteVie2Aimant=0;
	private int constanteVi1Aimant=0;
	private JLabel lblDegre;
	private int degre;
	private int intensite;
	private int minDegre=5;
	private int maxDegre=75;
	private int minAimant=0;
	private int maxAimant=100;
	private Inclinaison imageInclinaison;
	private JLabel lblChangementDonne;
	private JProgressBar barProgressionAimant;
	private boolean incertitude=false;



	/**
	 * Classe qui permet de simuler l'interface d'un pinball scientifique mais ou on peut changer aucune donnee, on subit la partie
	 * @author Audrey Viger 
	 * @author Carlos Eduardo
	 */
	//Carlos Eduardo
	/**
	 *  Creation d'un ecouteur qui sera jumelé au timer pour l'affichage des resultats
	 */
	private ActionListener ecouteurDuMinuteur = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			miseAjourInterface();
		}};
		//Carlos Eduardo
		/**
		 * Méthode qui questionne le composant d'animation pour obtenir les résultats actuels
		 * et les affiche ensuite sur l'interface
		 * Cette méthode détecte aussi la fin de l'animation, et arrête le minuteur dans ce cas.
		 */
		public void miseAjourInterface() {
			coeurVie=FenetreBacSable.getCoeurActive();
			FenetreBacSable.setCoeurActive(true);
			lblAcceleration.setText("Acceleration: 0,0");
			lblVitesseX.setText("Vitesse X : "  +String.format("%."+ 1 +"f", zonePinball.getBille().getVitesse().getX()));
			lblVitesseY.setText("Vitesse Y : "  +String.format("%."+ 1 +"f", zonePinball.getBille().getVitesse().getY()));

			lblCharge.setText("Charge: " + zonePinball.getBille().getCharge());
			lblScore.setText("Score : "+ zonePinball.getScore().toString());
			scoreVie=zonePinball.getScoreInt();


			//System.out.println("ScoreVie2: "+scoreVie2);

			lblScoreDebloquer.setText("Points pour les obstacles: 0");

			if(vie.getNombreCoeur()==3) {				
				scoreVie3=zonePinball.getScoreInt();
				lblScoreDebloquer.setText("Points pour les obstacles: "+scoreVie);	
				if(scoreVie3>0) {
					premiereOuverture=false;
				}
				if(scoreVie3>=500) {					
					lblScoreDebloquer.setText("Obstacle debloquer pour prochaine vie");
				}
			}
			scoreVie1=scoreVie-scoreVie2Finale-scoreVie3-2;

			if(vie.getNombreCoeur()==1) {
				if(scoreVie1>=5) {
					lblScoreDebloquer.setText("Tous les obstacles ont ete utilises ");
				}

			}
			scoreVie2=scoreVie-scoreVie3-1;
			if(vie.getNombreCoeur()==2) {
				scoreVie2Finale=scoreVie2;
				coeur2=true;
				lblScoreDebloquer.setText("Points pour les obstacles: "+scoreVie2);
				if(scoreVie2>=500) {					
					lblScoreDebloquer.setText("Obstacle debloquer pour prochaine vie");
				}
			}						






			if(vie.getNombreCoeur()==0 && premiereFoisGameOver) {
				FenetreFinPartie fenFinPartie1 = new FenetreFinPartie(fenMenu, fenBac, this, fenClassement);
				fenFinPartie1.setVisible(true);
				setVisible(false);
				premiereFoisGameOver=false;
				musiqueJouer.stop();
				zonePinball.setScoreFinal(score.getScore());
				musiqueFinPartie.reset();
				musiqueFinPartie.play();
				fenClassement=new FenetreClassement(this);

			}

			activeFormeObstacle();
			if(scoreVie3>=(1051*(constanteVie3Degre+1)) && scoreVie3<=(1249*(constanteVie3Degre+1))) {
				incertitude=true;
			}
			remonterJSlider();
			while(scoreVie3==scoreBaseDegre+scoreIncrement*constanteVie3Degre ||incertitude) {
				degre=minDegre + (int)(Math.random() * ((maxDegre - minDegre) + 1));
				constanteVie3Degre=constanteVie3Degre+1;				
				lblDegre.setText(degre+ " degre");
				imageInclinaison.setInclinaison(degre);
				lblChangementDonne.setText("Attention la table a ete incline de : "+degre);
				incertitude=false;
				
				moteur.MoteurPhysique.setACCEL_GRAV(degre*1.128);
			}
			while(scoreVie2==scoreBaseDegre+scoreIncrement*constanteVie2Degre) {
				degre=minDegre + (int)(Math.random() * ((maxDegre - minDegre) + 1));
				constanteVie2Degre=constanteVie2Degre+1;				
				lblDegre.setText(degre+ " degre");
				imageInclinaison.setInclinaison(degre);
				lblChangementDonne.setText("Attention la table a ete incline de : "+degre);
				
				moteur.MoteurPhysique.setACCEL_GRAV(degre*1.128);
			}
			while(scoreVie1==scoreBaseDegre+scoreIncrement*constanteVie1Degre) {
				degre=minDegre + (int)(Math.random() * ((maxDegre - minDegre) + 1));
				constanteVie1Degre=constanteVie1Degre+1;			
				lblDegre.setText(degre+ " degre");
				imageInclinaison.setInclinaison(degre);
				lblChangementDonne.setText("Attention la table a ete incline de : "+degre);
				moteur.MoteurPhysique.setACCEL_GRAV(degre*1.128);
			}
			while(scoreVie3==scoreBaseAimant+scoreIncrementAimant*constanteVie3Aimant) {				
				intensite=minAimant + (int)(Math.random() * ((maxAimant - minAimant) + 1));
				constanteVie3Aimant=constanteVie3Aimant+1;				
				barProgressionAimant.setValue(intensite);
				zonePinball.getAimant().setCharge(intensite);
				lblChangementDonne.setText("Attention l'intensite de l'aimant est de  : "+barProgressionAimant.getValue()+ " %");
			}
			
			if(scoreVie2>=500 && !enCoursdAnimation) {
				comboBoxObstacles.setEnabled(true);
			
				
			
			}else {
				comboBoxObstacles.setEnabled(false);
			}
			if(sliderLache) {
				comboBoxObstacles.setEnabled(false);
			}	
			if(scoreVie3>=500 && !sliderLache) {
				comboBoxObstacles.setEnabled(true);
			}else {
				comboBoxObstacles.setEnabled(false);
			}
			if(comboBoxObstacles.isEnabled()) {
				lblScoreDebloquer.setText("Vous pouvez prendre un obstacle");	
				zonePinball.resetCollisionObs();
				zonePinball.setPremiereFoisObstacle(true);
			}
			// si l'animation vient de s'arreter, il faut arrêter le minuteur (devient inutile) et remettre le bouton d'animation disponible
			// on teste si le minuteur est null, dans ce cas il s'agirait de l'initialisation initiale de l'interface (voir appel à la fin du constructeur)
			if ( minuteurResultats != null && !zonePinball.isAnimationEnCours() ) {
				minuteurResultats.stop();
				musiqueJouer.stop();

			}
			if(sliderEtirement.getValue()==0 && !premiereFoisJSlider) {

				musiquePremiereFois=true;
			}
		}
		//Audrey Viger
		/**

		 * Méthode qui remet le JSlider de l'étirement du ressort à zéro quand la bille reviens à sa position initiale
		 * 
		 */
		public void remonterJSlider() {
			if (zonePinball.getPostionYBille()==zonePinball.getPositionIniBille().getY()) {
				sliderEtirement.setValue(0);
				sliderLache=false;
				



			}
		}
		//Audrey Viger
		/**
		 * Constructeur qui permet de creer les composants la FenetreJouer
		 * @param fenMenu est la fenetre du menu
		 * @param fenOption est la fenetre des options
		 */
		public FenetreJouer(App24PinballScientifique2001 fenMenu, FenetreOption fenOption,FenetreFinPartie fenFinPartie) {			
			musiqueMenu=App24PinballScientifique2001.musiqueMenu();			
			if (urlArcade == null) {
				JOptionPane.showMessageDialog(null , "Fichier pause.jpg introuvable");
				System.exit(0);
			}else {
				System.out.println("pause.jpg trouvé");
			}
			backGround =null;
			try{
				backGround = ImageIO.read(urlArcade);
				System.out.println("fichier trouver img");
			}
			catch (IOException e) {
				System.out.println("Erreur pendant la lecture du fichier d'image");
			}

			backGroundRedim= backGround.getScaledInstance(1100, 928, Image.SCALE_SMOOTH );	
			panelAvecImage = new JPanel() {

				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					Graphics2D g2d = (Graphics2D) g;
					g2d.drawImage( backGroundRedim,0,0,null);
				}
			};
			this.fenMenu = fenMenu;
			this.fenOption = fenOption;
			this.fenFinPartie = fenFinPartie;
			FenetreFinPartie fenFinPartie1 = new FenetreFinPartie(fenMenu, fenBac, this, fenClassement);
			setTitle("Jouer");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(200, 40, 1100, 928);			
			panelAvecImage.setBackground(Color.WHITE);
			panelAvecImage.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			setContentPane(panelAvecImage);
			panelAvecImage.setLayout(null);
			vie=new CoeurVie(urlCoeur);

			zonePinball = new ZonePinball(scene);
			zonePinball.setBounds(71, 26, 600,768);
			panelAvecImage.add(zonePinball);

			//Initialisation des valeurs de spinners initiales.
			int etirementInitial = (int)(zonePinball.getETIREMENT_NAT()*100.0);
			int kRessortInitial = (int)zonePinball.getK_RESSORT();

			JLabel lblDonneesBalle = new JLabel("Donnees de la balle");
			lblDonneesBalle.setForeground(Color.CYAN);
			lblDonneesBalle.setFont(new Font("Arcade Normal", Font.PLAIN, 15));
			lblDonneesBalle.setBounds(722, 11, 380, 37);
			panelAvecImage.add(lblDonneesBalle);

			lblAcceleration = new JLabel("Acc\u00E9l\u00E9ration:");
			lblAcceleration.setForeground(Color.CYAN);
			lblAcceleration.setFont(new Font("Arcade Normal", Font.PLAIN, 10));

			lblAcceleration.setBounds(681, 59, 208, 14);

			panelAvecImage.add(lblAcceleration);

			lblVitesseX = new JLabel("VitesseX:");
			lblVitesseX.setForeground(Color.CYAN);
			lblVitesseX.setFont(new Font("Arcade Normal", Font.PLAIN, 10));


			lblVitesseX.setBounds(681, 114, 184, 19);

			panelAvecImage.add(lblVitesseX);

			lblVitesseY = new JLabel("VitesseY:");
			lblVitesseY.setForeground(Color.CYAN);
			lblVitesseY.setFont(new Font("Arcade Normal", Font.PLAIN, 10));


			lblVitesseY.setBounds(681, 84, 165, 19);

			panelAvecImage.add(lblVitesseY);

			lblCharge = new JLabel("Charge:");
			lblCharge.setForeground(Color.CYAN);
			lblCharge.setFont(new Font("Arcade Normal", Font.PLAIN, 10));
			lblCharge.setBounds(681, 153, 138, 19);
			panelAvecImage.add(lblCharge);

			JLabel lblMasse = new JLabel("Masse:" + zonePinball.getBille().getMasseEnKg());
			lblMasse.setForeground(Color.CYAN);
			lblMasse.setFont(new Font("Arcade Normal", Font.PLAIN, 10));
			lblMasse.setBounds(681, 183, 107, 14);
			panelAvecImage.add(lblMasse);

			JLabel lblAutresDonnees = new JLabel("Autres donnees");
			lblAutresDonnees.setForeground(Color.CYAN);
			lblAutresDonnees.setFont(new Font("Arcade Normal", Font.PLAIN, 18));
			lblAutresDonnees.setBounds(742, 227, 299, 21);
			panelAvecImage.add(lblAutresDonnees);

			JLabel lblInclinaison = new JLabel("Inclinaison de la table:");
			lblInclinaison.setForeground(Color.CYAN);
			lblInclinaison.setFont(new Font("Arcade Normal", Font.PLAIN, 9));
			lblInclinaison.setBounds(681, 283, 227, 21);
			panelAvecImage.add(lblInclinaison);

			JLabel lblIntensiteAimant = new JLabel("Intensite de l'aimant:");
			lblIntensiteAimant.setForeground(Color.CYAN);
			lblIntensiteAimant.setFont(new Font("Arcade Normal", Font.PLAIN, 10));
			lblIntensiteAimant.setBounds(734, 342, 253, 21);
			panelAvecImage.add(lblIntensiteAimant);

			JLabel lblConstanteRessort = new JLabel("Constante du ressort: 250.0");
			lblConstanteRessort.setForeground(Color.CYAN);
			lblConstanteRessort.setFont(new Font("Arcade Normal", Font.PLAIN, 10));
			lblConstanteRessort.setBounds(734, 417, 299, 22);
			panelAvecImage.add(lblConstanteRessort);

			lblScore = new JLabel("Score:");
			lblScore.setForeground(Color.CYAN);
			lblScore.setFont(new Font("Arcade Normal", Font.PLAIN, 20));



			lblScore.setBounds(734, 445, 352, 77);
			panelAvecImage.add(lblScore);

			imageInclinaison = new Inclinaison();
			imageInclinaison.setBounds(996,270,78,60);
			panelAvecImage.add(imageInclinaison);
			imageInclinaison.setInclinaison(5);

			Object[] choixObstacles = { "Carré", "Cercle","Triangle","Rectangle"};

			comboBoxObstacles = new JComboBox<Object>(choixObstacles);
			comboBoxObstacles.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {					

				}
			});
			comboBoxObstacles.setEnabled(false);
			comboBoxObstacles.setForeground(Color.CYAN);
			comboBoxObstacles.setModel(new DefaultComboBoxModel(new String[] {"Carre", "Cercle", "Triangle", "Rectangle"}));
			comboBoxObstacles.setFont(new Font("Arcade Normal", Font.PLAIN, 10));
			comboBoxObstacles.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String forme = (String) comboBoxObstacles.getSelectedItem();
					zonePinball.setForme(forme);
				

				}
			});


			comboBoxObstacles.setBounds(734, 533, 344, 37);
			panelAvecImage.add(comboBoxObstacles);

			JButton btnOption = new JButton("Option");
			btnOption.setFont(new Font("Arcade Normal", Font.PLAIN, 10));
			btnOption.setForeground(Color.ORANGE);
			btnOption.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fenOption.setVisible(true);

				}
			});
			btnOption.setBounds(908, 614, 170, 69);
			panelAvecImage.add(btnOption);

			JButton btnSauvegarde = new JButton("Revenir au menu");
			btnSauvegarde.setForeground(Color.ORANGE);
			btnSauvegarde.setFont(new Font("Arcade Normal", Font.PLAIN, 10));
			btnSauvegarde.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					musiqueJouer.stop();
					fenMenu.setVisible(true);
					setVisible(false);
					vie.setNombreCoeur(3);
					FenetreBacSable.setCoeurActive(false);
					App24PinballScientifique2001.setJouerActive(false);

					musiqueMenu.reset();
					musiqueMenu.play();
					musiqueMenu.loop();

				}
			});
			btnSauvegarde.setBounds(734, 694, 344, 60);
			panelAvecImage.add(btnSauvegarde);

			JLabel lblKg = new JLabel("kg");
			lblKg.setForeground(Color.CYAN);
			lblKg.setFont(new Font("Arcade Normal", Font.PLAIN, 9));
			lblKg.setBounds(784, 183, 48, 14);
			panelAvecImage.add(lblKg);


			JLabel lblValeurAccel = new JLabel("       m/s\u00B2");
			lblValeurAccel.setForeground(Color.CYAN);
			lblValeurAccel.setFont(new Font("Arial", Font.PLAIN, 14));
			lblValeurAccel.setBounds(835, 59, 152, 14);
			panelAvecImage.add(lblValeurAccel);

			JLabel lblValeurVitesse = new JLabel("      m/s");
			lblValeurVitesse.setForeground(Color.CYAN);
			lblValeurVitesse.setFont(new Font("Arcade Normal", Font.PLAIN, 9));
			lblValeurVitesse.setBounds(798, 117, 99, 14);
			panelAvecImage.add(lblValeurVitesse);

			JLabel lblValeurCharge = new JLabel("      C");
			lblValeurCharge.setForeground(Color.CYAN);
			lblValeurCharge.setFont(new Font("Arcade Normal", Font.PLAIN, 11));
			lblValeurCharge.setBounds(742, 155, 91, 14);
			panelAvecImage.add(lblValeurCharge);



			sliderEtirement = new JSlider();
			sliderEtirement.setEnabled(false);
			sliderEtirement.setVisible(false);
			sliderEtirement.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {					
				}
				@Override
				public void mouseReleased(MouseEvent e) {
					moteur.MoteurPhysique.setACCEL_GRAV(9.8066);
					sliderLache=true;
					musiqueRessort.reset();
					musiqueRessort.play();
					if(musiquePremiereFois) {	
						musiqueJouer.reset();
						musiqueJouer.play();
						musiqueJouer.loop();
						musiquePremiereFois=false;
					}
					premiereFoisJSlider=false;					
					zonePinball.demarrer();
					zonePinball.requestFocusInWindow();
					minuteurResultats = new Timer(10, ecouteurDuMinuteur );
					minuteurResultats.start();					

					if(zonePinball.getPostionYBille()>hauteurDuComposantMetre) {						
						sliderEtirement.setValue(0);

					}

				}
			});
			sliderEtirement.setMinimum(-10);
			sliderEtirement.setMaximum(0);
			sliderEtirement.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					sliderLache=true;
					zonePinball.setEtirement((0.1-(int)sliderEtirement.getValue())/100.0);
				}
			});

			sliderEtirement.setValue(100);
			sliderEtirement.setOrientation(SwingConstants.VERTICAL);
			sliderEtirement.setMajorTickSpacing(1);
			sliderEtirement.setMinorTickSpacing(5);
			sliderEtirement.setPaintTicks(true);
			sliderEtirement.setBounds(670, 652, 48, 113);
			panelAvecImage.add(sliderEtirement);

			JButton btnDemarrer = new JButton("Demarrer");
			btnDemarrer.setForeground(Color.ORANGE);
			btnDemarrer.setFont(new Font("Arcade Normal", Font.PLAIN, 11));
			btnDemarrer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {


					sliderEtirement.setVisible(true);
					sliderEtirement.setEnabled(true);
					zonePinball.setkRessort(kRessort);
					if ((int)sliderEtirement.getValue() != 0) {					
						zonePinball.demarrer();						
						enCoursdAnimation=true;
						zonePinball.requestFocusInWindow();									
					}
				}			


			});
			btnDemarrer.setBounds(248, 808, 218, 60);
			panelAvecImage.add(btnDemarrer);
			JButton btnRecommencer = new JButton("Recommencer la partie");
			btnRecommencer.setForeground(Color.ORANGE);
			btnRecommencer.setFont(new Font("Arcade Normal", Font.PLAIN, 6));
			btnRecommencer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					comboBoxObstacles.setEnabled(false);
					musiqueJouer.stop();
					premiereFoisJSlider=true;
					musiquePremiereFois=true;					

					zonePinball.retablirPosition();								
					sliderEtirement.setEnabled(false);
					vie.setNombreCoeur(3);
					premiereFoisGameOver=true;

				}
			});
			btnRecommencer.setBounds(734, 614, 170, 69);
			panelAvecImage.add(btnRecommencer);

			barProgressionAimant = new JProgressBar();
			barProgressionAimant.setToolTipText("");
			barProgressionAimant.setFont(new Font("Arcade Normal", Font.PLAIN, 7));
			barProgressionAimant.setStringPainted(true);
			barProgressionAimant.setForeground(Color.ORANGE);
			barProgressionAimant.setBackground(Color.BLACK);
			barProgressionAimant.setBounds(734, 372, 256, 19);
			panelAvecImage.add(barProgressionAimant);

			SceneImage sceneImage = new SceneImage();
			sceneImage.setBounds(974, 46, 100, 100);
			panelAvecImage.add(sceneImage);

			JLabel lblValeurVitesse_1 = new JLabel("      m/s");
			lblValeurVitesse_1.setForeground(Color.CYAN);
			lblValeurVitesse_1.setFont(new Font("Arcade Normal", Font.PLAIN, 9));
			lblValeurVitesse_1.setBounds(795, 86, 161, 14);
			panelAvecImage.add(lblValeurVitesse_1);

			JLabel lblUnitekRessort = new JLabel("N/m");
			lblUnitekRessort.setForeground(Color.CYAN);
			lblUnitekRessort.setFont(new Font("Arcade Normal", Font.PLAIN, 10));
			lblUnitekRessort.setBounds(1012, 420, 91, 14);
			panelAvecImage.add(lblUnitekRessort);

			lblDegre = new JLabel("5 degre");
			lblDegre.setForeground(Color.CYAN);
			lblDegre.setFont(new Font("Arcade Normal", Font.PLAIN, 9));
			lblDegre.setBounds(908, 285, 78, 14);
			panelAvecImage.add(lblDegre);

			DessinCoeur dessinCoeur = new DessinCoeur();
			dessinCoeur.setBounds(742, 754, 336, 124);
			panelAvecImage.add(dessinCoeur);
			dessinCoeur.setLayout(null);

			JButton btnClassement = new JButton("Classement");
			btnClassement.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					creerFenetreClassement();	
					fenClassement.setVisible(true);
				}
			});
			btnClassement.setFont(new Font("Arcade Normal", Font.PLAIN, 13));
			btnClassement.setForeground(Color.ORANGE);
			btnClassement.setBounds(734, 574, 344, 37);
			panelAvecImage.add(btnClassement);

			lblScoreDebloquer = new JLabel("Points accumules : 0");
			lblScoreDebloquer.setForeground(Color.CYAN);
			lblScoreDebloquer.setFont(new Font("Arcade Normal", Font.PLAIN, 9));
			lblScoreDebloquer.setBounds(734, 508, 340, 14);
			panelAvecImage.add(lblScoreDebloquer);
			
			lblChangementDonne = new JLabel("");
			lblChangementDonne.setForeground(Color.CYAN);
			lblChangementDonne.setFont(new Font("Arcade Normal", Font.PLAIN, 9));
			lblChangementDonne.setBounds(70, 8, 601, 14);
			panelAvecImage.add(lblChangementDonne);
			miseAjourInterface();
		}

		public static Musique musiqueJouer() {
			return musiqueJouer;
		}
		public static Musique musiqueRessort() {
			return musiqueRessort;
		}

		public void activeFormeObstacle() {
			
		}		
		public static Musique musiqueFinPartie() {
			return musiqueFinPartie;
		}
		public void creerFenetreClassement() {
			fenClassement= new FenetreClassement(this);
		}
}
