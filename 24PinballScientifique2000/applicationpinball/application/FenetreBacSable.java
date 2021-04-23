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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import geometrie.Bille;
import geometrie.Vecteur2D;
import animation.CoeurVie;
import animation.PointageAnimation;
import animation.Scene;
import animation.ZonePinball;
import javax.swing.SpinnerNumberModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JCheckBox;
import geometrie.Ressort;

import dessinable.OutilsImage;


import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.DefaultComboBoxModel;

/**
 * 
 * 
 * @author Audrey Viger, Carlos Eduardo, Thomas Bourgault
 *Classe permettant de simuler un jeu de pinball scientifique avec des donnes qu'on modifier
 */

public class FenetreBacSable extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel panelAvecImage;
	private App24PinballScientifique2001 fenMenu;
	private FenetreOption fenOption;
	private FenetreFinPartie fenFinPartie;
	private FenetreJouer fenJouer;
	private FenetreClassement fenClassement;
	private Dessin dessin;
	private int valeurInclinaison;
	private int valeurAimant,valeurMasse=1;
	private int valeurRessort;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private boolean enCoursdAnimation=false;
	private Ressort ressort;
	private  Inclinaison imageInclinaison;
	private double hauteurDuComposantMetre=1.536;
	private Scene scene;

	private double inclinaisonAjustement = 0.128;

	//carlos affichage des resultats
	private JLabel lblVitesseX;
	private JLabel lblVitesseY;
	private JLabel lblAcceleration;
	private JLabel lblCharge;
	private JLabel lblScore; 

	private Timer minuteurResultats=null;

	private ZonePinball zonePinball;

	private JSlider sliderEtirement;
	java.net.URL urlBilleBlanc = getClass().getClassLoader().getResource("Blanc.png");

	private boolean dessinerImage;
	private CoeurVie vie;
	private DessinCoeur coeur;
	java.net.URL urlCoeur = getClass().getClassLoader().getResource("Coeur.png");
	private boolean premiereFoisGameOver=true;
	private boolean CoeurVieActiveEtScore=false;
	public static boolean coeurActive=false;
	private PointageAnimation pointage;
	private int score;
	private GestionScore gestionScore;
	private String nomFichierSonMenu= ".//Ressource//8BitMenu.wav"; 
	private Musique musiqueMenu;
	private java.net.URL urlBacSable = getClass().getClassLoader().getResource("ImageBacSable2e.jpg");
	private Image backGround, backGroundRedim;
	private Musique musiqueBacSable;
	private Musique musiqueRessort;
	private FenetreClassement fenClassement1;	




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

			//System.out.println("Interface mise à jour.....");
			lblAcceleration.setText("Acceleration: 0,0");
			lblVitesseX.setText("Vitesse X : "  +String.format("%."+ 1 +"f", zonePinball.getBille().getVitesse().getX()));
			lblVitesseY.setText("Vitesse Y : "  +String.format("%."+ 1 +"f", zonePinball.getBille().getVitesse().getY()));

			lblCharge.setText("Charge: " + zonePinball.getBille().getCharge());
			if(CoeurVieActiveEtScore) {
				lblScore.setText("Score : "+ zonePinball.getScore().toString());
			}


			if(vie.getNombreCoeur()==0 && premiereFoisGameOver && coeurActive ) {
				FenetreFinPartie fenFinPartie1 = new FenetreFinPartie(fenMenu, this,fenJouer, fenClassement);
				fenFinPartie1.setVisible(true);
				setVisible(false);
				premiereFoisGameOver=false;
				
				
			}
			remonterJSlider();

			// si l'animation vient de s'arreter, il faut arrêter le minuteur (devient inutile) et remettre le bouton d'animation disponible
			// on teste si le minuteur est null, dans ce cas il s'agirait de l'initialisation initiale de l'interface (voir appel à la fin du constructeur)
			if ( minuteurResultats != null && !zonePinball.isAnimationEnCours() ) {
				minuteurResultats.stop();

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


		/*public void initianilisationFenSecondaire() {
			fenFinPartie = new FenetreFinPartie(fenMenu,this);
			fenMenu = new App24PinballScientifique2001();
			fenOption = new FenetreOption(fenMenu);
		}*/

		//Audrey Viger
		/**
		 * Constructeur : création et initialisation de l'inteface
		 * @param fenMenu est la fenetre du menu
		 * @param fenOption est la fenetre des options
		 * 
		 */
		public FenetreBacSable(App24PinballScientifique2001 fenMenu, FenetreOption fenOption, FenetreFinPartie fenFinPartie) {
			musiqueMenu=App24PinballScientifique2001.musiqueMenu();
			musiqueBacSable=App24PinballScientifique2001.musiqueBacSable();
			musiqueRessort=FenetreJouer.musiqueRessort();		
			if (urlBacSable == null) {
				JOptionPane.showMessageDialog(null , "Fichier pause.jpg introuvable");
				System.exit(0);
			}else {
				System.out.println("pause.jpg trouvé");
			}
			backGround =null;
			try{
				backGround = ImageIO.read(urlBacSable);
				System.out.println("fichier trouver img");
			}
			catch (IOException e) {
				System.out.println("Erreur pendant la lecture du fichier d'image");
			}

			backGroundRedim= backGround.getScaledInstance(1664, 936, Image.SCALE_SMOOTH );	
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
			FenetreFinPartie fenFinPartie1 = new FenetreFinPartie(fenMenu, this,fenJouer, fenClassement);
			HighScore hs = new HighScore(new String[]{"Nom","Score"},new int[][]
	                {{1,0},{0,1}},10,"Score.txt","  ");
			//dessinerImage=dessin.dessinImage();
		
			vie=new CoeurVie(urlCoeur);

			//initianilisationFenSecondaire();
			setTitle("Bac à sable");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(200, 40, 1100, 928);			
			panelAvecImage.setBackground(Color.WHITE);
			panelAvecImage.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			setContentPane(panelAvecImage);
			panelAvecImage.setLayout(null);

			zonePinball = new ZonePinball(scene);
			zonePinball.setBounds(71, 26, 600,768);
			panelAvecImage.add(zonePinball);


			//Initialisation des valeurs de spinners initiales.
			int etirementInitial = (int)(zonePinball.getETIREMENT_NAT()*100.0);
			int kRessortInitial = (int)zonePinball.getK_RESSORT();



			JLabel lblDonneesBalle = new JLabel("Donnees de la balle");
			lblDonneesBalle.setForeground(Color.ORANGE);
			lblDonneesBalle.setFont(new Font("Arcade Normal", Font.PLAIN, 10));
			lblDonneesBalle.setBounds(776, 26, 233, 37);
			panelAvecImage.add(lblDonneesBalle);

			lblAcceleration = new JLabel("Acc\u00E9l\u00E9ration:");
			lblAcceleration.setForeground(Color.ORANGE);
			lblAcceleration.setFont(new Font("Arcade Normal", Font.PLAIN, 10));
			lblAcceleration.setBounds(681, 79, 197, 14);
			panelAvecImage.add(lblAcceleration);

			lblVitesseX = new JLabel("Vitesse:");
			lblVitesseX.setForeground(Color.ORANGE);
			lblVitesseX.setFont(new Font("Arcade Normal", Font.PLAIN, 10));
			lblVitesseX.setBounds(681, 104, 165, 19);
			panelAvecImage.add(lblVitesseX);

			lblCharge = new JLabel("Charge:");
			lblCharge.setForeground(Color.ORANGE);
			lblCharge.setFont(new Font("Arcade Normal", Font.PLAIN, 10));
			lblCharge.setBounds(681, 153, 141, 19);
			panelAvecImage.add(lblCharge);

			JLabel lblMasse = new JLabel("Masse:");
			lblMasse.setForeground(Color.ORANGE);
			lblMasse.setFont(new Font("Arcade Normal", Font.PLAIN, 10));
			lblMasse.setBounds(681, 196, 101, 14);
			panelAvecImage.add(lblMasse);

			JLabel lblAutresDonnees = new JLabel("Autres donnees");
			lblAutresDonnees.setForeground(Color.ORANGE);
			lblAutresDonnees.setFont(new Font("Arcade Normal", Font.PLAIN, 10));
			lblAutresDonnees.setBounds(776, 227, 214, 21);
			panelAvecImage.add(lblAutresDonnees);

			JLabel lblInclinaison = new JLabel("Inclinaison de la table:");
			lblInclinaison.setForeground(Color.ORANGE);
			lblInclinaison.setFont(new Font("Arcade Normal", Font.PLAIN, 8));
			lblInclinaison.setBounds(734, 273, 200, 21);
			panelAvecImage.add(lblInclinaison);

			JLabel lblIntensiteAimant = new JLabel("Intensite de l'aimant:");
			lblIntensiteAimant.setForeground(Color.ORANGE);
			lblIntensiteAimant.setFont(new Font("Arcade Normal", Font.PLAIN, 8));
			lblIntensiteAimant.setBounds(734, 354, 200, 21);
			panelAvecImage.add(lblIntensiteAimant);

			JLabel lblConstanteRessort = new JLabel("Constante du ressort:");
			lblConstanteRessort.setForeground(Color.ORANGE);
			lblConstanteRessort.setFont(new Font("Arcade Normal", Font.PLAIN, 8));
			lblConstanteRessort.setBounds(734, 430, 178, 22);
			panelAvecImage.add(lblConstanteRessort);

			lblScore = new JLabel("");
			lblScore.setForeground(Color.ORANGE);
			lblScore.setFont(new Font("Arcade Normal", Font.PLAIN, 20));
			lblScore.setBounds(732, 511, 285, 37);
			panelAvecImage.add(lblScore);

			JRadioButton rdbtnChargePos = new JRadioButton("+e");
			rdbtnChargePos.setForeground(Color.ORANGE);
			rdbtnChargePos.setFont(new Font("Arcade Normal", Font.PLAIN, 10));
			buttonGroup.add(rdbtnChargePos);
			rdbtnChargePos.setBounds(841, 153, 48, 23);
			panelAvecImage.add(rdbtnChargePos);

			JRadioButton rdbtnChargeNeg = new JRadioButton("-e");
			rdbtnChargeNeg.setForeground(Color.ORANGE);
			rdbtnChargeNeg.setFont(new Font("Arcade Normal", Font.PLAIN, 10));
			buttonGroup.add(rdbtnChargeNeg);
			rdbtnChargeNeg.setBounds(908, 153, 54, 23);
			panelAvecImage.add(rdbtnChargeNeg);



			Object[] choixObstacles = { "Carre", "Cercle","Triangle","Rectangle"};

			JComboBox<Object> comboBoxObstacles = new JComboBox<Object>(choixObstacles);
			comboBoxObstacles.setForeground(Color.CYAN);
			comboBoxObstacles.setModel(new DefaultComboBoxModel(new String[] {"Carre", "Cercle", "Triangle", "Rectangle"}));
			comboBoxObstacles.setFont(new Font("Arcade Normal", Font.PLAIN, 11));
			comboBoxObstacles.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String forme = (String) comboBoxObstacles.getSelectedItem();
					zonePinball.setForme(forme);					

				}
			});


			comboBoxObstacles.setBounds(734, 563, 344, 37);
			panelAvecImage.add(comboBoxObstacles);

			JButton btnOption = new JButton("Option");
			btnOption.setForeground(Color.CYAN);
			btnOption.setFont(new Font("Arcade Normal", Font.PLAIN, 11));
			btnOption.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fenOption.setVisible(true);

				}
			});
			btnOption.setBounds(908, 632, 170, 54);
			panelAvecImage.add(btnOption);

			JButton btnSauvegarde = new JButton("Sauvegarder et revenir au menu");
			btnSauvegarde.setFont(new Font("Arcade Normal", Font.PLAIN, 10));
			btnSauvegarde.setForeground(Color.CYAN);
			btnSauvegarde.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fenMenu.setVisible(true);
					setVisible(false);
					vie.setNombreCoeur(3);						
					musiqueBacSable.stop();
					musiqueMenu.reset();
					musiqueMenu.play();
					musiqueMenu.loop();

				}
			});
			btnSauvegarde.setBounds(734, 694, 344, 60);
			panelAvecImage.add(btnSauvegarde);

			JSpinner spinnerMasse = new JSpinner();
			spinnerMasse.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
			spinnerMasse.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					valeurMasse = (int) spinnerMasse.getValue();
					zonePinball.setMasseBalle(valeurMasse);
				}
			});
			spinnerMasse.setBounds(746, 192, 44, 20);
			panelAvecImage.add(spinnerMasse);

			JLabel lblKg = new JLabel("kg");
			lblKg.setForeground(Color.ORANGE);
			lblKg.setFont(new Font("Arcade Normal", Font.PLAIN, 9));
			lblKg.setBounds(798, 195, 48, 14);
			panelAvecImage.add(lblKg);

			JSlider sliderAimant = new JSlider();
			sliderAimant.setValue(0);

			JSpinner spinnerAimant = new JSpinner();
			spinnerAimant.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
			spinnerAimant.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					valeurAimant = (int) spinnerAimant.getValue();
					//System.out.println(valeurAimant);
					sliderAimant.setValue(valeurAimant);
					zonePinball.getAimant().setCharge(sliderAimant.getValue());

				}
			});
			spinnerAimant.setBounds(948, 379, 42, 22);
			panelAvecImage.add(spinnerAimant);


			sliderAimant.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					spinnerAimant.setValue( sliderAimant.getValue());
					zonePinball.getAimant().setCharge(sliderAimant.getValue());
				}
			});

			sliderAimant.setMajorTickSpacing(25);
			sliderAimant.setPaintTicks(true);
			sliderAimant.setBounds(734, 386, 200, 33);
			panelAvecImage.add(sliderAimant);

			JSpinner spinnerRessort = new JSpinner();
			spinnerRessort.setModel(new SpinnerNumberModel(50, 50, 800, 1));

			SceneImage sceneImage = new SceneImage();
			sceneImage.setBounds(968, 58, 99, 100);
			panelAvecImage.add(sceneImage);


			JSlider sliderRessort = new JSlider();
			sliderRessort.setMaximum(800);
			sliderRessort.setMinimum(50);
			sliderRessort.setValue(0);
			sliderRessort.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					spinnerRessort.setValue(sliderRessort.getValue());
					zonePinball.setkRessort((int)sliderRessort.getValue());
				}
			});
			sliderRessort.setPaintTicks(true);
			sliderRessort.setMajorTickSpacing(25);
			sliderRessort.setBounds(734, 463, 200, 37);
			panelAvecImage.add(sliderRessort);

			JSlider sliderInclinaison = new JSlider();
			sliderInclinaison.setMinimum(5);
			sliderInclinaison.setValue(5);
			sliderInclinaison.setMaximum(75);


			Inclinaison imageInclinaison = new Inclinaison();
			imageInclinaison.setBounds(1000,283,78,60);
			panelAvecImage.add(imageInclinaison);
			imageInclinaison.setInclinaison(5);

			JSpinner spinnerInclinaison = new JSpinner();
			spinnerInclinaison.setModel(new SpinnerNumberModel(5, 5, 75, 1));
			spinnerInclinaison.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					valeurInclinaison = (int) spinnerInclinaison.getValue();
					//System.out.println(valeurInclinaison);
					sliderInclinaison.setValue(valeurInclinaison);
					imageInclinaison.setInclinaison(valeurInclinaison);

					double grav = valeurInclinaison;

					moteur.MoteurPhysique.setACCEL_GRAV(grav*inclinaisonAjustement);
				}
			});
			spinnerInclinaison.setBounds(948, 299, 42, 22);
			panelAvecImage.add(spinnerInclinaison);
			valeurInclinaison = (int) spinnerInclinaison.getValue();




			spinnerRessort.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					valeurRessort = (int) spinnerRessort.getValue();
					sliderRessort.setValue(valeurRessort);
				}
			});
			spinnerRessort.setBounds(948, 463, 42, 22);
			panelAvecImage.add(spinnerRessort);

			sliderInclinaison.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					spinnerInclinaison.setValue( sliderInclinaison.getValue());
				}
			});
			sliderInclinaison.setPaintTicks(true);
			sliderInclinaison.setMajorTickSpacing(25);
			sliderInclinaison.setBounds(734, 306, 200, 37);
			panelAvecImage.add(sliderInclinaison);

			JLabel lblValeurAccel = new JLabel("       m/s\u00B2");
			lblValeurAccel.setForeground(Color.ORANGE);
			lblValeurAccel.setBounds(840, 78, 85, 14);
			panelAvecImage.add(lblValeurAccel);
			//double vitesse = Math.sqrt(zonePinball.getVitesseBalle().getX()+zonePinball.getVitesseBalle().getY());

			JLabel lblValeurVitesse = new JLabel( " m/s");
			lblValeurVitesse.setForeground(Color.ORANGE);
			lblValeurVitesse.setFont(new Font("Arcade Normal", Font.PLAIN, 9));
			lblValeurVitesse.setBounds(849, 106, 64, 14);
			panelAvecImage.add(lblValeurVitesse);

			JLabel lblValeurCharge = new JLabel("      C");
			lblValeurCharge.setForeground(Color.ORANGE);
			lblValeurCharge.setFont(new Font("Arcade Normal", Font.PLAIN, 9));
			lblValeurCharge.setBounds(760, 157, 91, 14);
			panelAvecImage.add(lblValeurCharge);


			sliderEtirement = new JSlider();
			sliderEtirement.setVisible(false);
			//sliderEtirement.setEnabled(false);

			sliderEtirement.addComponentListener(new ComponentAdapter() {
				@Override
				public void componentShown(ComponentEvent e) {
					if(zonePinball.isVisible()==true && zonePinball.getPostionYBille()>hauteurDuComposantMetre) {
						zonePinball.setEtirement((0.1-(int)sliderEtirement.getValue())/100.0);
						//System.out.println("Je suis entrer dans le component shown");
						sliderEtirement.setValue(0);
					}
				}
			});

			sliderEtirement.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {


				}
				@Override
				public void mouseReleased(MouseEvent e) {
					musiqueRessort.reset();
					musiqueRessort.play();
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
				}
			});
			sliderEtirement.setValue(0);
			sliderEtirement.setOrientation(SwingConstants.VERTICAL);
			sliderEtirement.setMajorTickSpacing(1);
			sliderEtirement.setMinorTickSpacing(5);
			sliderEtirement.setPaintTicks(true);
			sliderEtirement.setBounds(670, 652, 48, 113);
			panelAvecImage.add(sliderEtirement);


			JButton btnRecommencer = new JButton("Recommencer la partie");
			btnRecommencer.setForeground(Color.CYAN);
			btnRecommencer.setFont(new Font("Arcade Normal", Font.PLAIN, 6));
			btnRecommencer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					spinnerMasse.setValue(1);
					spinnerAimant.setValue(0);
					spinnerRessort.setValue(0);
					spinnerInclinaison.setValue(5);
					sliderRessort.setValue(0);
					sliderInclinaison.setValue(0);
					sliderAimant.setValue(0);
					zonePinball.retablirPosition();					
					sliderEtirement.setValue(0);
					vie.setNombreCoeur(3);
					premiereFoisGameOver=true;



					sliderEtirement.setVisible(false);

				}
			});
			btnRecommencer.setBounds(734, 632, 170, 54);
			panelAvecImage.add(btnRecommencer);


			JButton btnDemarrer = new JButton("Demarrer");
			btnDemarrer.setForeground(Color.CYAN);
			btnDemarrer.setFont(new Font("Arcade Normal", Font.PLAIN, 11));
			btnDemarrer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//sliderEtirement.setEnabled(true);
					sliderEtirement.setVisible(true);
					if ((int)sliderEtirement.getValue() != 0) {					
						zonePinball.demarrer();
						enCoursdAnimation=true;
						zonePinball.requestFocusInWindow();									
					}
				}			

			});

			btnDemarrer.setBounds(248, 808, 218, 60);
			panelAvecImage.add(btnDemarrer);

			JCheckBox chckbxContour = new JCheckBox("Contour");
			chckbxContour.setForeground(Color.CYAN);
			chckbxContour.setFont(new Font("Arcade Normal", Font.PLAIN, 11));
			chckbxContour.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					zonePinball.setContour(chckbxContour.isSelected());

				}
			});

			chckbxContour.setBounds(107, 827, 135, 23);
			panelAvecImage.add(chckbxContour);


			BufferedImage imageBille = null;
			try {

				if(dessinerImage) {
					imageBille = ImageIO.read(new File(System.getProperty("user.home")+"\\ImageB.png"));
				}else {
					imageBille = ImageIO.read(urlBilleBlanc);
				}

			} catch (IOException e1) {



				e1.printStackTrace();
			}

			JCheckBox chckbxAimant = new JCheckBox("Aimant");
			chckbxAimant.setForeground(Color.CYAN);
			chckbxAimant.setFont(new Font("Arcade Normal", Font.PLAIN, 11));
			chckbxAimant.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					zonePinball.setAimant(chckbxAimant.isSelected());
					//zonePinball.aimantActif(chckbxAimant.isSelected());


					zonePinball.aimantActif(chckbxAimant.isSelected());

				}
			});
			chckbxAimant.setBounds(109, 859, 133, 23);
			panelAvecImage.add(chckbxAimant);

			lblVitesseY = new JLabel("Vitesse Y : 0.00");
			lblVitesseY.setForeground(Color.ORANGE);
			lblVitesseY.setFont(new Font("Arcade Normal", Font.PLAIN, 10));
			lblVitesseY.setBounds(681, 129, 163, 19);
			panelAvecImage.add(lblVitesseY);

			JLabel lblValeurVitesse_1 = new JLabel(" m/s");
			lblValeurVitesse_1.setForeground(Color.ORANGE);
			lblValeurVitesse_1.setFont(new Font("Arcade Normal", Font.PLAIN, 9));
			lblValeurVitesse_1.setBounds(843, 131, 93, 14);
			panelAvecImage.add(lblValeurVitesse_1);

			JButton btnPause = new JButton("Pause");
			btnPause.setForeground(Color.CYAN);
			btnPause.setFont(new Font("Arcade Normal", Font.PLAIN, 11));
			btnPause.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					zonePinball.arreter();
				}
			});
			btnPause.setBounds(484, 805, 89, 23);
			panelAvecImage.add(btnPause);

			JButton btnNextImg = new JButton("Next frame");
			btnNextImg.setForeground(Color.CYAN);
			btnNextImg.setFont(new Font("Arcade Normal", Font.PLAIN, 6));
			btnNextImg.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					zonePinball.prochaineImage();				
				}
			});
			btnNextImg.setBounds(582, 805, 101, 23);
			panelAvecImage.add(btnNextImg);


			DessinCoeur dessinCoeur = new DessinCoeur();
			JCheckBox chckbxActiverVie = new JCheckBox("Activer Score et Vie");
			chckbxActiverVie.setForeground(Color.CYAN);
			chckbxActiverVie.setFont(new Font("Arcade Normal", Font.PLAIN, 8));
			//Thomas Bourgault
			chckbxActiverVie.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if( chckbxActiverVie.isSelected()) {
						CoeurVieActiveEtScore=true;
						lblScore.setText("Score : ");
						dessinCoeur.setVisible(true);
						dessinCoeur.setBounds(734, 753, 344, 129);
						panelAvecImage.add(dessinCoeur);
						setCoeurActive(true);
						repaint();
					}else {
						CoeurVieActiveEtScore=false;
						setCoeurActive(false);
						lblScore.setText("");
						dessinCoeur.setVisible(false);
						repaint();
					}
				}
			});
			chckbxActiverVie.setBounds(484, 845, 199, 23);
			panelAvecImage.add(chckbxActiverVie);
			
			JButton btnClassement = new JButton("Classement");
			btnClassement.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					fenClassement1.setVisible(true);
				}
			});
			btnClassement.setBounds(734, 607, 344, 24);
			panelAvecImage.add(btnClassement);


			if(zonePinball.getPostionYBille()>=hauteurDuComposantMetre) {
				sliderEtirement.setValue(0);
				//System.out.println("je suis passe dans le ifffffff");
			}
			miseAjourInterface();

		}
		//Thomas Bourgault
		/**
		 * Methode qui retourne un boolean static pour l'activation de la perte des vies
		 * @return un boolean static qui est vrai si on a appuye sur le checkbox
		 */
		public static boolean getCoeurActive() {
			return coeurActive;			
		}
		//Thomas Bourgault
		/**
		 * Methode qui permet de changer la variable statique boolean pour l'activation de la perte des vies
		 * @param nouv est un nouveau boolean 
		 */
		public static void setCoeurActive(boolean nouv) {
			coeurActive=nouv;
		}
		
}
