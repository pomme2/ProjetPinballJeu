package application;
import javax.swing.JFrame;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.SwingConstants;

import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;

import animation.ZonePinball;

import javax.swing.event.ChangeEvent;
/**
 * 
 * 
 * @author Audrey Viger
 * Classe qui permet de creer la fenetre pour les options
 */

public class FenetreOption extends JFrame{
	private static final long serialVersionUID = 1L;
	private App24PinballScientifique2001 fenMenu;
	private Musique clip;
	private Musique musiqueMenu;
	private Musique musiqueDessin;
	private Musique musiqueTuto;
	private Musique musiqueBacSable;
	private Musique musiqueJouer;
	private Musique musiqueRessort;
	private Musique musiqueFlipperGauche;
	private Musique musiqueFlipperDroit;


	/**
	 * Constructeur qui permet de creer les composants de la FenetreOption
	 * @param fenMenu est la fenetre du menu
	 */
	public FenetreOption(App24PinballScientifique2001 fenMenu) {
		musiqueMenu=App24PinballScientifique2001.musiqueMenu();
		musiqueDessin=App24PinballScientifique2001.musiqueDessin();
		musiqueTuto=App24PinballScientifique2001.musiqueTuto();
		musiqueBacSable=App24PinballScientifique2001.musiqueBacSable();
		musiqueJouer=FenetreJouer.musiqueJouer();
		musiqueRessort=FenetreJouer.musiqueRessort();
		musiqueFlipperGauche=ZonePinball.musiqueFlipperGauche();
		musiqueFlipperDroit=ZonePinball.musiqueFlipperDroit();
		
		this.fenMenu = fenMenu;
		setTitle("Options");	
		getContentPane().setLayout(null);
		setBounds(300, 80, 900, 150);
		
		
		
		JCheckBox chckbxActiveLumiere = new JCheckBox("Activer la lumi\u00E8re lors d'un pointage \u00E9lev\u00E9");
		chckbxActiveLumiere.setFont(new Font("Tahoma", Font.PLAIN, 15));
		chckbxActiveLumiere.setBounds(46, 20, 411, 41);
		getContentPane().add(chckbxActiveLumiere);
		
		JSlider sliderVolume = new JSlider();
		sliderVolume.setValue(10);
		sliderVolume.setMaximum(10);
		sliderVolume.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {				
				musiqueMenu.setVolume( (float) sliderVolume.getValue()/10f);
				musiqueDessin.setVolume( (float) sliderVolume.getValue()/10f);
				musiqueTuto.setVolume( (float) sliderVolume.getValue()/10f);
				musiqueBacSable.setVolume( (float) sliderVolume.getValue()/10f);
				musiqueJouer.setVolume( (float) sliderVolume.getValue()/10f);
				musiqueRessort.setVolume( (float) sliderVolume.getValue()/10f);
				musiqueFlipperGauche.setVolume( (float) sliderVolume.getValue()/10f);
				musiqueFlipperDroit.setVolume( (float) sliderVolume.getValue()/10f);
			}
		});
		sliderVolume.setBounds(534, 58, 289, 15);
		getContentPane().add(sliderVolume);
		
		JLabel lblVolume = new JLabel("Volume de la musique");
		lblVolume.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblVolume.setHorizontalAlignment(SwingConstants.CENTER);
		lblVolume.setBounds(524, 33, 299, 14);
		getContentPane().add(lblVolume);
		
		JButton btnRetour = new JButton("Retour au menu");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fenMenu.setVisible(true);
				setVisible(false);
			}
		});
		btnRetour.setBounds(335, 84, 231, 23);
		getContentPane().add(btnRetour);
	}

	
}
