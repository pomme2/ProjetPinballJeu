package application;
 import java.awt.Menu;
import java.awt.MenuBar;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
/**
 * 
 * Classe qui permet de creer la fenetre pour le tutoriel
 * @author Audrey Viger
 *
 */
public class FenetreTutoriel extends JFrame{	
	private static final long serialVersionUID = 1L;
	private App24PinballScientifique2001 fenMenu;
	private String nomFichierSonMenu= ".//Ressource//8BitMenu.wav"; 
	private Musique musiqueTuto;
	private Musique musiqueMenu;
	private JPanel contentPane;
	private JButton btnPagePrecedente;
	private JButton btnPageSuivante;
	
	private String tableauImages[] = {"fenMenu.PNG","butBacEtJouer.PNG","fenBac1.PNG","fenBac2.PNG","fenBac3.PNG","fenJouer.PNG","fenFinPartie.PNG","fenClassement.PNG","fenDessin.PNG","fenOptions.PNG"};

	/**
	 * Constructeur qui permet de creer les composants de la FenetreTutoriel
	 * @param fenMenu est la fenetre du Menu
	 */
	public FenetreTutoriel(App24PinballScientifique2001 fenMenu) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 886, 808);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ImagesTuto panAide = new ImagesTuto();
		//Pour modifier la largeur et la couleur du cadre autour des pages 
		panAide.setLargeurCadre(10);
		panAide.setBackground(Color.white); 
		panAide.setFichiersImages(tableauImages); // on precise quelles images seront utilisees
		panAide.setBounds(10, 36, 1068, 658);
		contentPane.add(panAide);
		
		btnPagePrecedente = new JButton("Page precedente");
		btnPagePrecedente.setForeground(Color.RED);
		btnPagePrecedente.setFont(new Font("Arcade Normal", Font.PLAIN, 14));
		btnPagePrecedente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPagePrecedente.setEnabled( panAide.precedente() );
				btnPageSuivante.setEnabled(true);
			}
		});
		btnPagePrecedente.setBounds(51, 687, 463, 45);
		contentPane.add(btnPagePrecedente);
		
		btnPageSuivante = new JButton("Page suivante");
		btnPageSuivante.setForeground(Color.RED);
		btnPageSuivante.setFont(new Font("Arcade Normal", Font.PLAIN, 14));
		btnPageSuivante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPageSuivante.setEnabled( panAide.suivante() );
				btnPagePrecedente.setEnabled(true);
			}
		
		});
		
		if (tableauImages.length==1 ) {
			btnPagePrecedente.setEnabled(false);
			btnPageSuivante.setEnabled(false);
		}
		btnPageSuivante.setBounds(554, 687, 484, 45);
		contentPane.add(btnPageSuivante);
		
		JLabel lblAideConcepts = new JLabel("Tutoriel");
		lblAideConcepts.setHorizontalAlignment(SwingConstants.CENTER);
		lblAideConcepts.setForeground(Color.RED);
		lblAideConcepts.setFont(new Font("Arcade Normal", Font.BOLD, 30));
		lblAideConcepts.setBounds(10, 11, 1068, 34);
		contentPane.add(lblAideConcepts);
		
		musiqueTuto=App24PinballScientifique2001.musiqueTuto();
		musiqueMenu=App24PinballScientifique2001.musiqueMenu();
		this.fenMenu = fenMenu;
		getContentPane().setLayout(null);
		

		
		JLabel lblNewLabel = new JLabel("alllloo");
		lblNewLabel.setForeground(Color.GREEN);
		lblNewLabel.setBounds(91, 120, 170, 42);
		getContentPane().add(lblNewLabel);
		
		JButton btnRetourMenu = new JButton("Retour au menu");
		btnRetourMenu.setForeground(Color.RED);
		btnRetourMenu.setFont(new Font("Arcade Normal", Font.PLAIN, 8));
		btnRetourMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				musiqueTuto.stop();
				musiqueMenu.reset();
				musiqueMenu.play();
				musiqueMenu.loop();
				fenMenu.setVisible(true);
				setVisible(false);
			}
		});
		btnRetourMenu.setBounds(912, 11, 149, 25);
		contentPane.add(btnRetourMenu);
		lblNewLabel.setVisible(true);
		setBounds(200, 40, 1100, 800);
		
	}
}

