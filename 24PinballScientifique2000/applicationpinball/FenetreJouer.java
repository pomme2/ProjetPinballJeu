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
import javax.swing.JSpinner;
import javax.swing.JSlider;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.ButtonGroup;
import animation.ZoneSimulationPhysique;
import geometrie.Bille;
import animation.TestAnimation;
import animation.ZonePinball;

public class FenetreJouer extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ApplicationMenu fenMenu;
	private FenetreOption fenOption;
	private int valeurInclinaison;
	private int valeurAimant;
	private int valeurRessort;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	

	/**
	 * Constructeur : création et initialisation de l'inteface
	 * @param fenMenu
	 * @param fenOption 
	 */
	public FenetreJouer(ApplicationMenu fenMenu, FenetreOption fenOption) {
		
		this.fenMenu = fenMenu;
		this.fenOption = fenOption;
		setTitle("Bac à sable");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 40, 1100, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		ZonePinball zonePinball = new ZonePinball();
		zonePinball.setBounds(87, 26, 497, 636);
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
		lblInclinaison.setBounds(734, 281, 200, 21);
		contentPane.add(lblInclinaison);
		
		JLabel lblIntensiteAimant = new JLabel("Intensit\u00E9 de l'aimant:");
		lblIntensiteAimant.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblIntensiteAimant.setBounds(734, 342, 155, 21);
		contentPane.add(lblIntensiteAimant);
		
		JLabel lblConstanteRessort = new JLabel("Constante du ressort:");
		lblConstanteRessort.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblConstanteRessort.setBounds(734, 417, 178, 22);
		contentPane.add(lblConstanteRessort);
		
		JLabel lblScore = new JLabel("Score:");
		lblScore.setForeground(Color.RED);
		lblScore.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblScore.setBounds(773, 501, 116, 37);
		contentPane.add(lblScore);
		
		

		Object[] choixObstacles = { "Carré", "Cercle","Triangle","Rectangle"};
		
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
		
		JLabel lblKg = new JLabel("kg");
		lblKg.setBounds(841, 198, 48, 14);
		contentPane.add(lblKg);
		
		
		
		
		
		
		
		
		JLabel lblValeurAccel = new JLabel("       m/s\u00B2");
		lblValeurAccel.setBounds(827, 81, 85, 14);
		contentPane.add(lblValeurAccel);
		
		JLabel lblValeurVitesse = new JLabel("      m/s");
		lblValeurVitesse.setBounds(798, 118, 48, 14);
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
		
		JButton btnDemarrer = new JButton("D\u00E9marrer");
		btnDemarrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
			//	if ((int)spinnerEtirement.getValue() != 0) {					
				zonePinball.demarrer();
				
			//}
			}			
			

		});
		btnDemarrer.setBounds(226, 694, 218, 60);
		contentPane.add(btnDemarrer);
		
		
		
		JButton btnRecommencer = new JButton("Recommencer la partie");
		btnRecommencer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zonePinball.retablirPosition();
				spinnerEtirement.setValue(0);
				
			}
		});
		btnRecommencer.setBounds(734, 614, 170, 69);
		contentPane.add(btnRecommencer);
		spinnerEtirement.setBounds(817, 460, 63, 38);
		contentPane.add(spinnerEtirement);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(734, 372, 256, 19);
		contentPane.add(progressBar);
		
		JLabel lblEtirement = new JLabel("Etirement:");
		lblEtirement.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEtirement.setBounds(734, 465, 138, 25);
		contentPane.add(lblEtirement);
	}
}
