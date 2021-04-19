package application;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Musique extends JPanel {
	private static final long serialVersionUID = 1L;
	private static Clip monClipMenu;
	private String nomFichierSon;
	
	public Musique(String nomFichierSon) {
		this.nomFichierSon=nomFichierSon;
		setFile(nomFichierSon );
		play();
		lireLeSon(); 
		
	}	
	private void lireLeSon() {

	/*
	 * 	String pathDeFichier;
		File objetFichier;
		AudioInputStream audioInputStream;

		try {
			System.out.println(new File(".").getAbsolutePath());
			pathDeFichier = getClass().getClassLoader().getResource(NOM_FICHIER_SON).getFile();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Incapable d'ouvrir le fichier de son ");
			e.printStackTrace();
			return;
		}
		try {
			System.out.println(new File(".").getAbsolutePath());
			objetFichier = new File(pathDeFichier);			
			audioInputStream = AudioSystem.getAudioInputStream(objetFichier);
			System.out.println("J'ai ouvert le fichier son");
			monClipMenu = AudioSystem.getClip();
			monClipMenu.open(audioInputStream);
			monClipMenu.loop(Clip.LOOP_CONTINUOUSLY);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Problème d'ouverture du clip pour le fichier de son " + NOM_FICHIER_SON);
			e.printStackTrace();
			return;
		}
*/
		
	}
	public void setFile(String 	soundFileName) {
		
		try {
			File file= new File(soundFileName);
			AudioInputStream sound=AudioSystem.getAudioInputStream(file);
			try {
				monClipMenu=AudioSystem.getClip();
			} catch (LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				monClipMenu.open(sound);
				monClipMenu.loop(Clip.LOOP_CONTINUOUSLY);
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void play() {
		monClipMenu.start();
	}
	public static Clip getClipMenu() {
		return monClipMenu;
	}
	public static void stop() {
		monClipMenu.stop();
	}
	public void setVolume(float volumeChange) {
		
		FloatControl volume = (FloatControl) monClipMenu.getControl(FloatControl.Type.MASTER_GAIN);
		volume.setValue(volumeChange);
	}
}
