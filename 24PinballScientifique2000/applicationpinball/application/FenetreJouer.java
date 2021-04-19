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
import animation.Scene;
import animation.ZonePinball;
import javax.swing.DefaultComboBoxModel;

public class FenetreJouer extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel panelAvecImage;
	private App24PinballScientifique2001 fenMenu;
	private FenetreOption fenOption;
	private FenetreFinPartie fenFinPartie;
	private FenetreBacSable fenBac;
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
	private String nomFichierRessort= ".//Ressource//bruitRessort.wav"; 
	private String nomFichierSonJouer=".//Ressource//musiqueJouer.wav";
	private Image backGround,backGroundRedim;
	private boolean musiquePremiereFois=true;
	private boolean premiereFoisJSlider=true;	
	private Musique musiqueMenu;
	private Musique musiqueJouer=new Musique (nomFichierSonJouer);
	private Musique musiqueRessort=new Musique(nomFichierRessort);

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
			//System.out.println("Interface mise à jour.....");
			lblAcceleration.setText("Acceleration: 0,0");
			lblVitesseX.setText("Vitesse X : "  +String.format("%."+ 1 +"f", zonePinball.getBille().getVitesse().getX()));
			lblVitesseY.setText("Vitesse Y : "  +String.format("%."+ 1 +"f", zonePinball.getBille().getVitesse().getY()));

			lblCharge.setText("Charge: " + zonePinball.getBille().getCharge());
			lblScore.setText("Score : "+ zonePinball.getScore().toString());
			if(vie.getNombreCoeur()==0 && premiereFoisGameOver) {
				FenetreFinPartie fenFinPartie1 = new FenetreFinPartie(fenMenu, fenBac, this);
				fenFinPartie1.setVisible(true);
				premiereFoisGameOver=false;
				musiqueJouer.stop();
				//System.out.println("LEs coeurs sont a 0");
			}
			remonterJSlider();

			// si l'animation vient de s'arreter, il faut arrêter le minuteur (devient inutile) et remettre le bouton d'animation disponible
			// on teste si le minuteur est null, dans ce cas il s'agirait de l'initialisation initiale de l'interface (voir appel à la fin du constructeur)
			if ( minuteurResultats != null && !zonePinball.isAnimationEnCours() ) {
				minuteurResultats.stop();
				musiqueJouer.stop();

			}
			if(sliderEtirement.getValue()==0 && !premiereFoisJSlider) {
				arretMusique();
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
			FenetreFinPartie fenFinPartie1 = new FenetreFinPartie(fenMenu, fenBac, this);
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



			lblScore.setBounds(773, 501, 261, 37);
			panelAvecImage.add(lblScore);

			Inclinaison imageInclinaison = new Inclinaison();
			imageInclinaison.setBounds(996,270,78,60);
			panelAvecImage.add(imageInclinaison);
			imageInclinaison.setInclinaison(5);

			Object[] choixObstacles = { "Carré", "Cercle","Triangle","Rectangle"};

			JComboBox<Object> comboBoxObstacles = new JComboBox<Object>(choixObstacles);
			comboBoxObstacles.setForeground(Color.CYAN);
			comboBoxObstacles.setModel(new DefaultComboBoxModel(new String[] {"Carre", "Cercle", "Triangle", "Rectangle"}));
			comboBoxObstacles.setFont(new Font("Arcade Normal", Font.PLAIN, 10));
			comboBoxObstacles.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String forme = (String) comboBoxObstacles.getSelectedItem();
					zonePinball.setForme(forme);

				}
			});


			comboBoxObstacles.setBounds(734, 563, 344, 37);
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

			JButton btnSauvegarde = new JButton("Sauvegarder et revenir au menu");
			btnSauvegarde.setForeground(Color.ORANGE);
			btnSauvegarde.setFont(new Font("Arcade Normal", Font.PLAIN, 10));
			btnSauvegarde.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fenMenu.setVisible(true);
					setVisible(false);
					vie.setNombreCoeur(3);
					FenetreBacSable.setCoeurActive(false);
					
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

			JSpinner spinnerEtirement = new JSpinner();
			spinnerEtirement.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					zonePinball.setEtirement((int)spinnerEtirement.getValue()/100.0);

				}
			});



			sliderEtirement = new JSlider();
			sliderEtirement.setEnabled(false);
			sliderEtirement.setVisible(false);
			sliderEtirement.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {

					System.out.println("Slider active");
				}
				@Override
				public void mouseReleased(MouseEvent e) {
					musiqueRessort.reset();
					musiqueRessort.play();
					if(musiquePremiereFois) {	
						musiqueJouer.reset();
						musiqueJouer.play();
						musiqueJouer.loop();
						musiquePremiereFois=false;
					}
					premiereFoisJSlider=false;
					System.out.println("Slider desactive");
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
					zonePinball.setEtirement((0.1-(int)sliderEtirement.getValue())/100.0);
					System.out.println(sliderEtirement.getValue()/100.0);
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
					musiqueJouer.stop();
					premiereFoisJSlider=true;
					musiquePremiereFois=true;					
					
					zonePinball.retablirPosition();
					spinnerEtirement.setValue(0);				
					sliderEtirement.setEnabled(false);
					vie.setNombreCoeur(3);
					premiereFoisGameOver=true;

				}
			});
			btnRecommencer.setBounds(734, 614, 170, 69);
			panelAvecImage.add(btnRecommencer);
			spinnerEtirement.setBounds(835, 457, 63, 38);
			panelAvecImage.add(spinnerEtirement);

			JProgressBar progressBar = new JProgressBar();
			progressBar.setBounds(734, 372, 256, 19);
			panelAvecImage.add(progressBar);

			SceneImage sceneImage = new SceneImage();
			sceneImage.setBounds(974, 46, 100, 100);
			panelAvecImage.add(sceneImage);

			JLabel lblEtirement = new JLabel("Etirement:");
			lblEtirement.setForeground(Color.CYAN);
			lblEtirement.setFont(new Font("Arcade Normal", Font.PLAIN, 10));
			lblEtirement.setBounds(734, 465, 138, 25);
			panelAvecImage.add(lblEtirement);

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

			JLabel lblNewLabel = new JLabel("5 degre");
			lblNewLabel.setForeground(Color.CYAN);
			lblNewLabel.setFont(new Font("Arcade Normal", Font.PLAIN, 9));
			lblNewLabel.setBounds(908, 285, 78, 14);
			panelAvecImage.add(lblNewLabel);

			DessinCoeur dessinCoeur = new DessinCoeur();
			dessinCoeur.setBounds(742, 754, 336, 124);
			panelAvecImage.add(dessinCoeur);
			dessinCoeur.setLayout(null);
			miseAjourInterface();
		}
		public void arretMusique() {
			//Musique.stop();
		}
		public Musique musiqueJouer() {
			return musiqueJouer;
		}
}
