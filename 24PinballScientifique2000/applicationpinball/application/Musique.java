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

public class Musique extends JPanel {
	private static final long serialVersionUID = 1L;
	private static  Clip monClipMenu;
	private String nomFichierSon;
	
	public Musique(String nomFichierSon) {
		this.nomFichierSon=nomFichierSon;
		setFile(nomFichierSon );
		play();				
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
	public void loop() {
		monClipMenu.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void stopMusicSpecifique(Musique musique) {
		musique.stop();
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
