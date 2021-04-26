package application;

import java.io.File;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;

/**
 * Classe qui permet de montrer le dessin de la bille sur l'interface
 * @author Thomas Bourgault
 *
 */
public class Musique extends JPanel {
	private static final long serialVersionUID = 1L;
	private   Clip monClipMenu;
	private String nomFichierSon;
	private float volume;
	
	/**
	 *Constructeur pour la musique
	 *@param nomFichierSon nom du fichier qui contient l'audio
	 *
	 */

	public Musique(String nomFichierSon) {
		this.nomFichierSon=nomFichierSon;
		setFile(nomFichierSon );

	}	
	/**
	 *Méthode qui prend le nom du fichier et l'intègre au clip
	 *@param SoundFileName nom du fichier qui contient l'audio
	 *
	 */
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
	/**
	 *méthode qui fait jouer l'audio
	 *
	 */
	public void play() {
		monClipMenu.start();	

	}
	/**
	 *Méthode qui fait jouer l'audio en boucle
	 *
	 */
	public void loop() {
		monClipMenu.loop(Clip.LOOP_CONTINUOUSLY);
	}
	/**
	 *
	 *Méthode qui arrête la musique 
	 *@param musique l'objet musique
	 */
	public void stopMusicSpecifique(Musique musique) {
		musique.stop();
	}
	/**
	 *retourne le clip (l'objet qui permet d'ouvrir l'audio)
	 *
	 */
	public  Clip getClipMenu() {
		return monClipMenu;
	}
	/**
	 *Méthode qui arrete la musique
	 *
	 */
	public void stop() {
		monClipMenu.stop();
	}
	/**
	 *Modifie le volume de la musique 
	 *@param volumChange volume de la musique
	 */
	public void setVolume(float volumeChange) {
		
			FloatControl volume = (FloatControl) monClipMenu.getControl(FloatControl.Type.MASTER_GAIN);
			volume.setValue(20f* (float) Math.log10(volumeChange));			


	}
	/**
	 *recommence la musique du début
	 *
	 */
	public void reset() {
		monClipMenu.setMicrosecondPosition(0);
	}
	/**
	 *méthode qui retourne le volume de la musique
	 *
	 */
	public float getVolume() {
		FloatControl volume = (FloatControl) monClipMenu.getControl(FloatControl.Type.MASTER_GAIN);        
		return (float) Math.pow(10f, volume.getValue() / 20f);
	}
}
