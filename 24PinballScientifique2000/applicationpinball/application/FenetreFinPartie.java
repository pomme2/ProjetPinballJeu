package application;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import animation.PointageAnimation;
import animation.ZonePinball;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;

public class FenetreFinPartie extends JFrame{

	private static final long serialVersionUID = 1L;
	private App24PinballScientifique2001 fenMenu;
	private FenetreBacSable fenBac;
	private JPanel contentPane;
	private GestionScore gestionScore;
	private JTextField txtEntreeInitiales;
	private FenetreJouer fenJouer;
	private ZonePinball zonePinball;
	private PointageAnimation pointage;

	public String getInitiales() {
		String initiales = txtEntreeInitiales.getText().toString();
		return initiales;
	}
	
	public FenetreFinPartie(  	App24PinballScientifique2001 fenMenu, FenetreBacSable fenBac, FenetreJouer fenJouer) {

		this.fenBac = fenBac;
		this.fenMenu = fenMenu;
		this.fenJouer = fenJouer;
		gestionScore = new GestionScore();
		pointage = new PointageAnimation();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 40, 1100, 928);
		contentPane = new JPanel();
		contentPane.setBackground(Color.black);
		contentPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitre = new JLabel("FIN DE LA PARTIE");
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setFont(new Font("Showcard Gothic", Font.BOLD, 61));
		lblTitre.setForeground(Color.MAGENTA);
		lblTitre.setBounds(10, 51, 1068, 93);
		contentPane.add(lblTitre);
		
		/*JFileChooser fileChooser = new JFileChooser();
		fileChooser.setBounds(246, 134, 582, 399);
		contentPane.add(fileChooser);
		fileChooser.setVisible(false);*/
		
		JButton btnRecommencer = new JButton("Recommencer une partie");
		btnRecommencer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnRecommencer.setBounds(91, 448, 403, 120);
		contentPane.add(btnRecommencer);
		
		JLabel lblInitiales = new JLabel("Entrez vos initiales");
		lblInitiales.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblInitiales.setForeground(Color.WHITE);
		lblInitiales.setBounds(317, 210, 291, 35);
		contentPane.add(lblInitiales);
		lblInitiales.setVisible(false);
		
		txtEntreeInitiales = new JTextField();
		txtEntreeInitiales.setBounds(317, 256, 438, 82);
		contentPane.add(txtEntreeInitiales);
		txtEntreeInitiales.setColumns(10);
		txtEntreeInitiales.setVisible(false);
		
		JButton btnEnregistrerInit = new JButton("Enregistrer");
		btnEnregistrerInit.addActionListener(new ActionListener() {
		

		

			public void actionPerformed(ActionEvent e) {
				String initiales = txtEntreeInitiales.getText().toString();
				gestionScore.setInitiales(initiales);
				//gestionScore.setScore(zonePinball.getScoreFinal());
				//pointage = zonePinball.getScoreFinal();
				//gestionScore.setScore(pointage);
			//	System.out.println("scorefinal "+zonePinball.getScoreFinal());
				gestionScore.ecrireFichier();
				
			//	System.out.println(txtEntreeInitiales.getText().toString());
			}
		});
		btnEnregistrerInit.setBounds(317, 349, 438, 35);
		contentPane.add(btnEnregistrerInit);
		btnEnregistrerInit.setVisible(false);
		
		
		JButton btnSauvegarderScore = new JButton("Sauvegarder le score");
		btnSauvegarderScore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblInitiales.setVisible(true);
				txtEntreeInitiales.setVisible(true);
				btnEnregistrerInit.setVisible(true);
				
		
			}
		});
		btnSauvegarderScore.setBounds(600, 448, 403, 120);
		contentPane.add(btnSauvegarderScore);
	
	}
	
}
