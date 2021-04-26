package application;
import javax.swing.JFrame;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.SwingConstants;

import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;

import animation.ZonePinball;

import javax.swing.event.ChangeEvent;
/**
 * 
 * 
 * @author Audrey Viger
 * @author Thomas Bourgault
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
	private JSlider sliderVolume;	
	private Musique musiqueFinPartieJouer;
	private Musique musiqueFinPartieBacSable;
	

//Audrey Viger
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
		musiqueFinPartieJouer=FenetreJouer.musiqueFinPartie();
		musiqueFinPartieBacSable=FenetreBacSable.musiqueFinPartie();
		
		
		this.fenMenu = fenMenu;
		setTitle("Options");	
		getContentPane().setLayout(null);
		setBounds(300, 80, 900, 150);
		
		
		
		JCheckBox chckbxActiveLumiere = new JCheckBox("Couper le son");
		chckbxActiveLumiere.setForeground(Color.RED);
		chckbxActiveLumiere.addActionListener(new ActionListener() {
			//Thomas Bourgault
			public void actionPerformed(ActionEvent e) {
				if(chckbxActiveLumiere.isSelected()) {
					musiqueMenu.stop();
					musiqueMenu.setVolume(0f);					
					musiqueDessin.setVolume(0f);
					musiqueTuto.setVolume(0f);
					musiqueBacSable.setVolume(0f);
					musiqueJouer.setVolume(0f);
					musiqueRessort.setVolume(0f);
					musiqueFlipperGauche.setVolume(0f);
					musiqueFlipperDroit.setVolume(0f);
					musiqueFinPartieJouer.setVolume(0f);
					musiqueFinPartieBacSable.setVolume(0f);
				}else {
					musiqueMenu.play();
					musiqueMenu.setVolume( (float) sliderVolume.getValue()/10f);
					musiqueDessin.setVolume( (float) sliderVolume.getValue()/10f);
					musiqueTuto.setVolume( (float) sliderVolume.getValue()/10f);
					musiqueBacSable.setVolume( (float) sliderVolume.getValue()/10f);
					musiqueJouer.setVolume( (float) sliderVolume.getValue()/10f);
					musiqueRessort.setVolume( (float) sliderVolume.getValue()/10f);
					musiqueFlipperGauche.setVolume( (float) sliderVolume.getValue()/10f);
					musiqueFlipperDroit.setVolume( (float) sliderVolume.getValue()/10f);
					musiqueFinPartieJouer.setVolume( (float) sliderVolume.getValue()/10f);
					musiqueFinPartieBacSable.setVolume( (float) sliderVolume.getValue()/10f);
				}
				
			}
		});
		chckbxActiveLumiere.setFont(new Font("Arcade Normal", Font.PLAIN, 15));
		chckbxActiveLumiere.setBounds(46, 20, 411, 41);
		getContentPane().add(chckbxActiveLumiere);
		
		sliderVolume = new JSlider();
		sliderVolume.setValue(10);
		sliderVolume.setMaximum(10);
		sliderVolume.addChangeListener(new ChangeListener() {
			//Thomas Bourgault
			public void stateChanged(ChangeEvent e) {				
				musiqueMenu.setVolume( (float) sliderVolume.getValue()/10f);
				musiqueDessin.setVolume( (float) sliderVolume.getValue()/10f);
				musiqueTuto.setVolume( (float) sliderVolume.getValue()/10f);
				musiqueBacSable.setVolume( (float) sliderVolume.getValue()/10f);
				musiqueJouer.setVolume( (float) sliderVolume.getValue()/10f);
				musiqueRessort.setVolume( (float) sliderVolume.getValue()/10f);
				musiqueFlipperGauche.setVolume( (float) sliderVolume.getValue()/10f);
				musiqueFlipperDroit.setVolume( (float) sliderVolume.getValue()/10f);
				musiqueFinPartieJouer.setVolume( (float) sliderVolume.getValue()/10f);
				musiqueFinPartieBacSable.setVolume( (float) sliderVolume.getValue()/10f);
			}
		});
		sliderVolume.setBounds(534, 58, 289, 15);
		getContentPane().add(sliderVolume);
		
		JLabel lblVolume = new JLabel("Volume de la musique");
		lblVolume.setForeground(Color.RED);
		lblVolume.setFont(new Font("Arcade Normal", Font.PLAIN, 14));
		lblVolume.setHorizontalAlignment(SwingConstants.CENTER);
		lblVolume.setBounds(524, 33, 299, 14);
		getContentPane().add(lblVolume);
		
		JButton btnRetour = new JButton("Retour au menu");
		btnRetour.setForeground(Color.RED);
		btnRetour.setFont(new Font("Arcade Normal", Font.PLAIN, 7));
		btnRetour.addActionListener(new ActionListener() {
			//Audrey Viger
			public void actionPerformed(ActionEvent e) {
				fenMenu.setVisible(true);
				setVisible(false);
			}
		});
		btnRetour.setBounds(335, 84, 231, 23);
		getContentPane().add(btnRetour);
	}

	
}
