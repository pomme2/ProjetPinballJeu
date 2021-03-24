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

import animation.ZoneSimulationPhysique;
import geometrie.Bille;
import geometrie.Vecteur2D;
import animation.TestAnimation;
import animation.ZonePinball;
import javax.swing.SpinnerNumberModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JCheckBox;
import geometrie.Ressort;
import dessinable.OutilsImage;

/**
 * 
 * 
 * @author Audrey Viger
 *
 */

public class FenetreBacSable extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private App24PinballScientifique2001 fenMenu;
	private FenetreOption fenOption;
	private int valeurInclinaison;
	private int valeurAimant,valeurMasse=1;
	private int valeurRessort;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private boolean enCoursdAnimation=false;
	private Ressort ressort;
	

	private  Inclinaison imageInclinaison;


	/**
	 * Constructeur : cr�ation et initialisation de l'inteface
	 * @param fenMenu
	 * @param fenOption 
	 */
	public FenetreBacSable(App24PinballScientifique2001 fenMenu, FenetreOption fenOption) {

		this.fenMenu = fenMenu;
		this.fenOption = fenOption;
		setTitle("Bac � sable");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 40, 1100, 928);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		ZonePinball zonePinball = new ZonePinball();
		zonePinball.setBounds(71, 26, 600,768);
		contentPane.add(zonePinball);
		
	
		

		//Initialisation des valeurs de spinners initiales.
		int etirementInitial = (int)(zonePinball.getETIREMENT_NAT()*100.0);
		int kRessortInitial = (int)zonePinball.getK_RESSORT();
		
	
		
		JLabel lblDonneesBalle = new JLabel("Donn\u00E9es de la balle");
		lblDonneesBalle.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblDonneesBalle.setBounds(817, 26, 192, 37);
		contentPane.add(lblDonneesBalle);

		JLabel lblAcceleration = new JLabel("Acc\u00E9l\u00E9ration:");
		lblAcceleration.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAcceleration.setBounds(734, 79, 89, 14);
		contentPane.add(lblAcceleration);

		JLabel lblVitesse = new JLabel("Vitesse:");
		lblVitesse.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblVitesse.setBounds(734, 114, 98, 19);
		contentPane.add(lblVitesse);

		JLabel lblCharge = new JLabel("Charge:");
		lblCharge.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCharge.setBounds(734, 153, 98, 19);
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

		JLabel lblScore = new JLabel("Score:");
		lblScore.setForeground(Color.RED);
		lblScore.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblScore.setBounds(773, 501, 116, 37);
		contentPane.add(lblScore);

		JRadioButton rdbtnChargePos = new JRadioButton("+e");
		buttonGroup.add(rdbtnChargePos);
		rdbtnChargePos.setBounds(897, 153, 48, 23);
		contentPane.add(rdbtnChargePos);

		JRadioButton rdbtnChargeNeg = new JRadioButton("-e");
		buttonGroup.add(rdbtnChargeNeg);
		rdbtnChargeNeg.setBounds(948, 153, 42, 23);
		contentPane.add(rdbtnChargeNeg);



		Object[] choixObstacles = { "Carr�", "Cercle","Triangle","Rectangle"};

		JComboBox<Object> comboBoxObstacles = new JComboBox<Object>(choixObstacles);
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
		sliderInclinaison.setValue(0);
		sliderInclinaison.setMaximum(75);
		
		
Inclinaison imageInclinaison = new Inclinaison();
		imageInclinaison.setBounds(1000,283,78,60);
		contentPane.add(imageInclinaison);
		
		JSpinner spinnerInclinaison = new JSpinner();
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
		lblValeurAccel.setBounds(827, 81, 85, 14);
		contentPane.add(lblValeurAccel);
		//double vitesse = Math.sqrt(zonePinball.getVitesseBalle().getX()+zonePinball.getVitesseBalle().getY());

		JLabel lblValeurVitesse = new JLabel( " m/s");
		lblValeurVitesse.setBounds(798, 118, 149, 14);
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




		JSlider sliderEtirement = new JSlider();
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
				if(zonePinball.getPositionBille()!=zonePinball.getPositionIniBille()) {
					
				}
				sliderEtirement.setValue(0);
				
			}
		});
		
		sliderEtirement.setMinimum(-10);
		sliderEtirement.setMaximum(0);
		sliderEtirement.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				
				/*if(zonePinball.retablirPosition()==true) {
			sliderEtirement.setValue(0);
		}*/
				// carlos testing
				//zonePinball.getBille().setPosition(new Vecteur2D (1.056, 1- (int) sliderEtirement.getValue()/100));
				
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
				sliderEtirement.setEnabled(false);
			
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
				sliderEtirement.setEnabled(true);
				 //if(zonePinball.getPositionBille().getX()==zonePinball.getPositionIniBille().getX()) {
				//	 sliderEtirement.setValue(0);
				// }
			
				if ((int)sliderEtirement.getValue() != 0) {					
					zonePinball.demarrer();
					enCoursdAnimation=true;
					zonePinball.requestFocusInWindow();
				

				}
			}			


		});
		if(zonePinball.retablirPosition()==true) {
			sliderEtirement.setValue(0);
		}
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
			imageBille = ImageIO.read(new File(System.getProperty("user.home")+"\\ImageB.png"));
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		
		JLabel lblImage = new JLabel(new ImageIcon(imageBille));
		lblImage.setBounds(798, 750, 192, 143);
		contentPane.add(lblImage);
		
		
	}
	
}
