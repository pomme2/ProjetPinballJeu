package application;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import java.awt.Font;
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
import javax.swing.ButtonGroup;
import application.SceneImage;
import geometrie.Bille;
import animation.Scene;
import animation.ZonePinball;

public class FenetreJouer extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private App24PinballScientifique2001 fenMenu;
	private FenetreOption fenOption;
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
	private double kRessort=125;
	private JSlider sliderEtirement;

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
		//Audrey Viger
		/**
		 * Constructeur qui permet de creer les composants la FenetreJouer
		 * @param fenMenu est la fenetre du menu
		 * @param fenOption est la fenetre des options
		 */
		public FenetreJouer(App24PinballScientifique2001 fenMenu, FenetreOption fenOption) {

			this.fenMenu = fenMenu;
			this.fenOption = fenOption;
			setTitle("Jouer");
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

			lblAcceleration.setBounds(681, 89, 151, 14);

			contentPane.add(lblAcceleration);

			lblVitesseX = new JLabel("VitesseX:");
			lblVitesseX.setFont(new Font("Tahoma", Font.PLAIN, 15));


			lblVitesseX.setBounds(817, 114, 117, 19);

			contentPane.add(lblVitesseX);

			lblVitesseY = new JLabel("VitesseY:");
			lblVitesseY.setFont(new Font("Tahoma", Font.PLAIN, 15));


			lblVitesseY.setBounds(681, 114, 109, 19);

			contentPane.add(lblVitesseY);

			lblCharge = new JLabel("Charge:");
			lblCharge.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblCharge.setBounds(734, 153, 98, 19);
			contentPane.add(lblCharge);

			JLabel lblMasse = new JLabel("Masse:" + zonePinball.getBille().getMasseEnKg());
			lblMasse.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblMasse.setBounds(734, 196, 85, 14);
			contentPane.add(lblMasse);

			JLabel lblAutresDonnees = new JLabel("Autres donn\u00E9es");
			lblAutresDonnees.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblAutresDonnees.setBounds(835, 227, 155, 21);
			contentPane.add(lblAutresDonnees);

			JLabel lblInclinaison = new JLabel("Inclinaison de la table:");
			lblInclinaison.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblInclinaison.setBounds(734, 281, 200, 21);
			contentPane.add(lblInclinaison);

			JLabel lblIntensiteAimant = new JLabel("Intensit\u00E9 de l'aimant:");
			lblIntensiteAimant.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblIntensiteAimant.setBounds(734, 342, 155, 21);
			contentPane.add(lblIntensiteAimant);

			JLabel lblConstanteRessort = new JLabel("Constante du ressort: " + zonePinball.getRessort().getkRessort());
			lblConstanteRessort.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblConstanteRessort.setBounds(734, 417, 299, 22);
			contentPane.add(lblConstanteRessort);

			lblScore = new JLabel("Score:");
			lblScore.setForeground(Color.RED);
			lblScore.setFont(new Font("Tahoma", Font.PLAIN, 30));

			

			lblScore.setBounds(773, 501, 261, 37);
			contentPane.add(lblScore);

			Inclinaison imageInclinaison = new Inclinaison();
			imageInclinaison.setBounds(1000,283,78,60);
			contentPane.add(imageInclinaison);
			imageInclinaison.setInclinaison(5);

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

			JLabel lblKg = new JLabel("kg");
			lblKg.setBounds(817, 198, 48, 14);
			contentPane.add(lblKg);








			JLabel lblValeurAccel = new JLabel("       m/s\u00B2");
			lblValeurAccel.setBounds(804, 91, 85, 14);
			contentPane.add(lblValeurAccel);

			JLabel lblValeurVitesse = new JLabel("      m/s");
			lblValeurVitesse.setBounds(921, 118, 35, 14);
			contentPane.add(lblValeurVitesse);

			JLabel lblValeurCharge = new JLabel("      C");
			lblValeurCharge.setBounds(798, 157, 48, 14);
			contentPane.add(lblValeurCharge);

			JSpinner spinnerEtirement = new JSpinner();
			spinnerEtirement.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					zonePinball.setEtirement((int)spinnerEtirement.getValue()/100.0);

				}
			});



			 sliderEtirement = new JSlider();
			sliderEtirement.setEnabled(false);
			sliderEtirement.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {

					System.out.println("Slider active");
				}
				@Override
				public void mouseReleased(MouseEvent e) {
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
			contentPane.add(sliderEtirement);
			
			JButton btnDemarrer = new JButton("D\u00E9marrer");
			btnDemarrer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
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
			contentPane.add(btnDemarrer);
			JButton btnRecommencer = new JButton("Recommencer la partie");
			btnRecommencer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					zonePinball.retablirPosition();
					spinnerEtirement.setValue(0);				
					sliderEtirement.setEnabled(false);

				}
			});
			btnRecommencer.setBounds(734, 614, 170, 69);
			contentPane.add(btnRecommencer);
			spinnerEtirement.setBounds(817, 460, 63, 38);
			contentPane.add(spinnerEtirement);

			JProgressBar progressBar = new JProgressBar();
			progressBar.setBounds(734, 372, 256, 19);
			contentPane.add(progressBar);

			SceneImage sceneImage = new SceneImage();
			sceneImage.setBounds(974, 46, 100, 100);
			contentPane.add(sceneImage);

			JLabel lblEtirement = new JLabel("Etirement:");
			lblEtirement.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblEtirement.setBounds(734, 465, 138, 25);
			contentPane.add(lblEtirement);

			JLabel lblValeurVitesse_1 = new JLabel("      m/s");
			lblValeurVitesse_1.setBounds(773, 118, 48, 14);
			contentPane.add(lblValeurVitesse_1);
			
			JLabel lblUnitekRessort = new JLabel("N/m");
			lblUnitekRessort.setBounds(921, 423, 46, 14);
			contentPane.add(lblUnitekRessort);
			
			JLabel lblNewLabel = new JLabel("5 degr\u00E9");
			lblNewLabel.setBounds(888, 286, 46, 14);
			contentPane.add(lblNewLabel);
			miseAjourInterface();
		}
}
