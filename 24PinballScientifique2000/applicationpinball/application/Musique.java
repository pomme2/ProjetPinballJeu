package application;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Musique extends JPanel {
	private static final long serialVersionUID = 1L;
	private static Clip monClipMenu;
	private final String NOM_FICHIER_SON = "8 Bit Menu.wav"; 
	
	public Musique() {
		lireLeSon(); 
		
	}	
	private void lireLeSon() {

		String pathDeFichier;
		File objetFichier;
		AudioInputStream audioInputStream;

		try {
			pathDeFichier = getClass().getClassLoader().getResource(NOM_FICHIER_SON).getFile();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Incapable d'ouvrir le fichier de son ");
			e.printStackTrace();
			return;
		}
		try {
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

	}
	public static Clip getClipMenu() {
		return monClipMenu;
	}
}
