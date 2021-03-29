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
/**
 * 
 * Classe qui permet de creer la fenetre pour le tutoriel
 * @author Audrey Viger
 *
 */
public class FenetreTutoriel extends JFrame{	
	private static final long serialVersionUID = 1L;
	private App24PinballScientifique2001 fenMenu;
	/**
	 * Constructeur qui permet de creer les composants de la FenetreTutoriel
	 * @param fenMenu est la fenetre du Menu
	 */
	public FenetreTutoriel(App24PinballScientifique2001 fenMenu) {
		this.fenMenu = fenMenu;
		getContentPane().setLayout(null);
		
		JButton btnRetour = new JButton("Retour au menu");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fenMenu.setVisible(true);
				setVisible(false);
			}
		});
		btnRetour.setBounds(91, 226, 187, 84);
		getContentPane().add(btnRetour);
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

