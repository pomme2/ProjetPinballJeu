package application;
 import java.awt.Menu;
import java.awt.MenuBar;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;
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
	/**
	 * Constructeur qui permet de creer les composants de la FenetreTutoriel
	 * @param fenMenu est la fenetre du Menu
	 */
	public FenetreTutoriel(App24PinballScientifique2001 fenMenu) {
		musiqueTuto=App24PinballScientifique2001.musiqueTuto();
		musiqueMenu=App24PinballScientifique2001.musiqueMenu();
		this.fenMenu = fenMenu;
		getContentPane().setLayout(null);
		
		JButton btnRetour = new JButton("Retour au menu");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				musiqueTuto.stop();
				musiqueMenu.reset();
				musiqueMenu.play();
				musiqueMenu.loop();
				fenMenu.setVisible(true);
				setVisible(false);
			}
		});
		btnRetour.setBounds(91, 226, 187, 84);
		getContentPane().add(btnRetour);
		
		JLabel lblNewLabel = new JLabel("alllloo");
		lblNewLabel.setForeground(Color.GREEN);
		lblNewLabel.setBounds(91, 120, 170, 42);
		getContentPane().add(lblNewLabel);
		lblNewLabel.setVisible(true);
		setBounds(200, 40, 1100, 800);
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menuAccueil = new JMenu("Accueil");
		menuAccueil.setEnabled(false);
		menuBar.add(menuAccueil);

		JMenu  menuAimant = new JMenu("Aimant");
	
		menuBar.add(menuAimant);
		
		JMenu  menuRessort = new JMenu("Ressort");
		menuBar.add(menuRessort);
		
		JMenu  menuObstacle = new JMenu("Obstacles");
		menuBar.add(menuObstacle);
		
		setJMenuBar(menuBar);
		
	}

}

