package application;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import java.awt.Font;
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
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import geometrie.Bille;
import geometrie.Vecteur2D;
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

/**
 * 
 * 
 * @author Audrey Viger, Carlos Eduardo
 *Classe permettant de simuler un jeu de pinball scientifique avec des donnes qu'on modifier
 */

public class FenetreBacSable extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private App24PinballScientifique2001 fenMenu;
	private FenetreOption fenOption;
	private FenetreFinPartie fenFinPartie;
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
			lblAcceleration.setText("Acc\u00E9l\u00E9ration: "  +String.format("%."+ 1 +"f", zonePinball.getBille().getAccel().getX()));
			lblVitesseX.setText("Vitesse X : "  +String.format("%."+ 1 +"f", zonePinball.getBille().getVitesse().getX()));
			lblVitesseY.setText("Vitesse Y : "  +String.format("%."+ 1 +"f", zonePinball.getBille().getVitesse().getY()));

			lblCharge.setText("Charge: " + zonePinball.getBille().getCharge());
			lblScore.setText("Score : "+ zonePinball.getScore().toString());

			
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
		}*\

		//Audrey Viger
		/**
		 * Constructeur : création et initialisation de l'inteface
		 * @param fenMenu est la fenetre du menu
		 * @param fenOption est la fenetre des options
		 */
		public FenetreBacSable(App24PinballScientifique2001 fenMenu, FenetreOption fenOption, FenetreFinPartie fenFinPartie) {

			this.fenMenu = fenMenu;
			this.fenOption = fenOption;
			this.fenFinPartie = fenFinPartie;
			
		//dessinerImage=dessin.dessinImage();
			
			
			FenetreFinPartie fenFinPartie1 = new FenetreFinPartie(fenMenu, this);
			//initianilisationFenSecondaire();
			setTitle("Bac à sable");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(200, 40, 1100, 928);
			contentPane = new JPanel();
			contentPane.setBackground(Color.WHITE);
			contentPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			setContentPane(contentPane);
			contentPane.setLayout(null);

			zonePinball = new ZonePinball(scene);
			zonePinball.setBounds(71, 26, 600,768);
			contentPane.add(zonePinball);


			//Initialisation des valeurs de spinners initiales.
			int etirementInitial = (int)(zonePinball.getETIREMENT_NAT()*100.0);
			int kRessortInitial = (int)zonePinball.getK_RESSORT();



			JLabel lblDonneesBalle = new JLabel("Donn\u00E9es de la balle");
			lblDonneesBalle.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblDonneesBalle.setBounds(817, 26, 192, 37);
			contentPane.add(lblDonneesBalle);

			lblAcceleration = new JLabel("Acc\u00E9l\u00E9ration:");
			lblAcceleration.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblAcceleration.setBounds(724, 79, 122, 14);
			contentPane.add(lblAcceleration);

			lblVitesseX = new JLabel("Vitesse:");
			lblVitesseX.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblVitesseX.setBounds(724, 104, 122, 19);
			contentPane.add(lblVitesseX);

			lblCharge = new JLabel("Charge:");
			lblCharge.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblCharge.setBounds(724, 153, 98, 19);
			contentPane.add(lblCharge);

			JLabel lblMasse = new JLabel("Masse:");
			lblMasse.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblMasse.setBounds(734, 196, 48, 14);
			contentPane.add(lblMasse);

			JLabel lblAutresDonnees = new JLabel("Autres donn\u00E9es");
			lblAutresDonnees.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblAutresDonnees.setBounds(835, 227, 155, 21);
			contentPane.add(lblAutresDonnees);

			JLabel lblInclinaison = new JLabel("Inclinaison de la table:");
			lblInclinaison.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblInclinaison.setBounds(734, 273, 200, 21);
			contentPane.add(lblInclinaison);

			JLabel lblIntensiteAimant = new JLabel("Intensit\u00E9 de l'aimant:");
			lblIntensiteAimant.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblIntensiteAimant.setBounds(734, 354, 155, 21);
			contentPane.add(lblIntensiteAimant);

			JLabel lblConstanteRessort = new JLabel("Constante du ressort:");
			lblConstanteRessort.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblConstanteRessort.setBounds(734, 430, 178, 22);
			contentPane.add(lblConstanteRessort);

			lblScore = new JLabel("Score:");
			lblScore.setForeground(Color.RED);
			lblScore.setFont(new Font("Tahoma", Font.PLAIN, 30));
			lblScore.setBounds(732, 511, 285, 37);
			contentPane.add(lblScore);

			JRadioButton rdbtnChargePos = new JRadioButton("+e");
			buttonGroup.add(rdbtnChargePos);
			rdbtnChargePos.setBounds(841, 153, 48, 23);
			contentPane.add(rdbtnChargePos);

			JRadioButton rdbtnChargeNeg = new JRadioButton("-e");
			buttonGroup.add(rdbtnChargeNeg);
			rdbtnChargeNeg.setBounds(908, 153, 42, 23);
			contentPane.add(rdbtnChargeNeg);



			Object[] choixObstacles = { "Carré", "Cercle","Triangle","Rectangle"};

			JComboBox<Object> comboBoxObstacles = new JComboBox<Object>(choixObstacles);
			comboBoxObstacles.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String forme = (String) comboBoxObstacles.getSelectedItem();
					zonePinball.setForme(forme);

				}
			});


			comboBoxObstacles.setBounds(734, 563, 344, 37);
			contentPane.add(comboBoxObstacles);

			JButton btnOption = new JButton("Option");
			btnOption.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fenOption.setVisible(true);

				}
			});
			btnOption.setBounds(908, 614, 170, 69);
			contentPane.add(btnOption);

			JButton btnSauvegarde = new JButton("Sauvegarder et revenir au menu");
			btnSauvegarde.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fenMenu.setVisible(true);
					setVisible(false);

				}
			});
			btnSauvegarde.setBounds(734, 694, 344, 60);
			contentPane.add(btnSauvegarde);

			JSpinner spinnerMasse = new JSpinner();
			spinnerMasse.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
			spinnerMasse.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					valeurMasse = (int) spinnerMasse.getValue();
					zonePinball.setMasseBalle(valeurMasse);
				}
			});
			spinnerMasse.setBounds(788, 195, 44, 20);
			contentPane.add(spinnerMasse);

			JLabel lblKg = new JLabel("kg");
			lblKg.setBounds(841, 198, 48, 14);
			contentPane.add(lblKg);

			JSlider sliderAimant = new JSlider();
			sliderAimant.setValue(0);

			JSpinner spinnerAimant = new JSpinner();
			spinnerAimant.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
			spinnerAimant.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					valeurAimant = (int) spinnerAimant.getValue();
					System.out.println(valeurAimant);
					sliderAimant.setValue(valeurAimant);
				}
			});
			spinnerAimant.setBounds(948, 379, 42, 22);
			contentPane.add(spinnerAimant);


			sliderAimant.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					spinnerAimant.setValue( sliderAimant.getValue());
				}
			});

			sliderAimant.setMajorTickSpacing(25);
			sliderAimant.setPaintTicks(true);
			sliderAimant.setBounds(734, 386, 200, 33);
			contentPane.add(sliderAimant);

			JSpinner spinnerRessort = new JSpinner();
			spinnerRessort.setModel(new SpinnerNumberModel(50, 50, 800, 1));

			SceneImage sceneImage = new SceneImage();
			sceneImage.setBounds(968, 58, 99, 101);
			contentPane.add(sceneImage);

			
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
			contentPane.add(sliderRessort);

			JSlider sliderInclinaison = new JSlider();
			sliderInclinaison.setMinimum(5);
			sliderInclinaison.setValue(5);
			sliderInclinaison.setMaximum(75);


			Inclinaison imageInclinaison = new Inclinaison();
			imageInclinaison.setBounds(1000,283,78,60);
			contentPane.add(imageInclinaison);
			imageInclinaison.setInclinaison(5);

			JSpinner spinnerInclinaison = new JSpinner();
			spinnerInclinaison.setModel(new SpinnerNumberModel(5, 5, 75, 1));
			spinnerInclinaison.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					valeurInclinaison = (int) spinnerInclinaison.getValue();
					//System.out.println(valeurInclinaison);
					sliderInclinaison.setValue(valeurInclinaison);
					imageInclinaison.setInclinaison(valeurInclinaison);
				}
			});
			spinnerInclinaison.setBounds(948, 299, 42, 22);
			contentPane.add(spinnerInclinaison);
			valeurInclinaison = (int) spinnerInclinaison.getValue();




			spinnerRessort.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					valeurRessort = (int) spinnerRessort.getValue();
					sliderRessort.setValue(valeurRessort);
				}
			});
			spinnerRessort.setBounds(948, 463, 42, 22);
			contentPane.add(spinnerRessort);

			sliderInclinaison.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					spinnerInclinaison.setValue( sliderInclinaison.getValue());
				}
			});
			sliderInclinaison.setPaintTicks(true);
			sliderInclinaison.setMajorTickSpacing(25);
			sliderInclinaison.setBounds(734, 306, 200, 37);
			contentPane.add(sliderInclinaison);

			JLabel lblValeurAccel = new JLabel("       m/s\u00B2");
			lblValeurAccel.setBounds(837, 81, 85, 14);
			contentPane.add(lblValeurAccel);
			//double vitesse = Math.sqrt(zonePinball.getVitesseBalle().getX()+zonePinball.getVitesseBalle().getY());

			JLabel lblValeurVitesse = new JLabel( " m/s");
			lblValeurVitesse.setBounds(848, 108, 30, 14);
			contentPane.add(lblValeurVitesse);

			JLabel lblValeurCharge = new JLabel("      C");
			lblValeurCharge.setBounds(798, 157, 48, 14);
			contentPane.add(lblValeurCharge);

			JSpinner spinnerEtirement = new JSpinner();
			spinnerEtirement.setModel(new SpinnerNumberModel(new Integer(1), null, null, new Integer(1)));
			spinnerEtirement.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					zonePinball.setEtirement((int)spinnerEtirement.getValue()/100.0);

				}
			});


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
			contentPane.add(sliderEtirement);


			JButton btnRecommencer = new JButton("Recommencer la partie");
			btnRecommencer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					spinnerMasse.setValue(1);
					spinnerAimant.setValue(0);
					spinnerRessort.setValue(0);
					spinnerInclinaison.setValue(0);
					sliderRessort.setValue(0);
					sliderInclinaison.setValue(0);
					sliderAimant.setValue(0);
					zonePinball.retablirPosition();
					spinnerEtirement.setValue(1);
					sliderEtirement.setValue(0);

					sliderEtirement.setVisible(false);

				}
			});
			btnRecommencer.setBounds(734, 614, 170, 69);
			contentPane.add(btnRecommencer);
			spinnerEtirement.setBounds(601, 848, 30, 20);
			contentPane.add(spinnerEtirement);

			JLabel lblEtirement = new JLabel("Etirement:");
			lblEtirement.setBounds(515, 851, 98, 14);
			contentPane.add(lblEtirement);


			JButton btnDemarrer = new JButton("D\u00E9marrer");
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
			contentPane.add(btnDemarrer);

			JCheckBox chckbxContour = new JCheckBox("Contour");
			chckbxContour.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					zonePinball.setContour(chckbxContour.isSelected());
					
				}
			});

			chckbxContour.setBounds(107, 827, 99, 23);
			contentPane.add(chckbxContour);


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
			chckbxAimant.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					zonePinball.setAimant(chckbxAimant.isSelected());
					//zonePinball.aimantActif(chckbxAimant.isSelected());
				}
			});
			chckbxAimant.setBounds(109, 859, 97, 23);
			contentPane.add(chckbxAimant);

			lblVitesseY = new JLabel("Vitesse Y : 0.00");
			lblVitesseY.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblVitesseY.setBounds(724, 129, 120, 19);
			contentPane.add(lblVitesseY);

			JLabel lblValeurVitesse_1 = new JLabel(" m/s");
			lblValeurVitesse_1.setBounds(841, 133, 30, 14);
			contentPane.add(lblValeurVitesse_1);
			
			JButton btnNewButton = new JButton("New button");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fenFinPartie1.setVisible(true);
					setVisible(false);
				}
			});
			btnNewButton.setBounds(989, 526, 89, 23);
			contentPane.add(btnNewButton);


			if(zonePinball.getPostionYBille()>=hauteurDuComposantMetre) {
				sliderEtirement.setValue(0);
				//System.out.println("je suis passe dans le ifffffff");
			}
			miseAjourInterface();

		}		
}
