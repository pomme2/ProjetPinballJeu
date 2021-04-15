package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import animation.PointageAnimation;
import animation.ZonePinball;

public class GestionScore {
	String dossierBureau = "ScorePinball";

	private String nomFichierScore = "Scores";
	
	private ZonePinball zonePinball;
	private App24PinballScientifique2001 fenMenu;
	private FenetreJouer fenJouer;
	private FenetreBacSable fenBac;
	private PointageAnimation pointage;
	private FenetreFinPartie fenFinPartie;

	private String initiales;

	

	public void ecrireFichier() {
	String nom = null;
		//pointage = new PointageAnimation();
		fenFinPartie = new FenetreFinPartie(fenMenu, fenBac, fenJouer);
		File dossier = new File(System.getProperty("user.home"),dossierBureau);
		if(dossier.mkdir()) {
			System.out.println("\nLe dossier "+dossier.toString()+ " a été créé...");
		}
		
		File fichierScore = new File(dossier + "\\"+ nomFichierScore);

		PrintWriter fluxSortie = null;
		
		try {
			
			fluxSortie = new PrintWriter(new BufferedWriter(new FileWriter(fichierScore)));
			fluxSortie.println(initiales);
			System.out.println("écrire dans fichier intiales = " +initiales);
			fluxSortie.println(pointage);
			//fluxSortie.println(pointage.getScore());
			
			//fluxSortie.println(fenFinPartie.getInitiales());
			//System.out.println(fenFinPartie.getInitiales());
		}catch(IOException e) {
			JOptionPane.showMessageDialog(null,"Erreur d'écriture");
			e.printStackTrace();
		}finally {
			if (fluxSortie!=null)
				fluxSortie.close();
		}
	}
	
	public void lireFichierTexte() {
		BufferedReader fluxEntree = null;
		String nom = null;
		
		File fichierDeTravail = new File (System.getProperty("user.home"), dossierBureau +"\\" + nomFichierScore);
		if(!fichierDeTravail.exists()) {
			JOptionPane.showMessageDialog(null,"Problème! Le fichier " + fichierDeTravail.toString()+ "n'existe pas...");
		}
		try {
			fluxEntree = new BufferedReader(new FileReader(fichierDeTravail));
		
		}
		catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Incapable de trouver le fichier  " + fichierDeTravail.toString());
			e.printStackTrace();
		}
		catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erreur rencontree lors de la lecture");
			e.printStackTrace();
		}
		finally {
			// on exécutera toujours ceci, erreur ou pas
			try {
				fluxEntree.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Erreur rencontrée lors de la fermeture!");
				e.printStackTrace();
			}
		}
	}

	public void setInitiales(String init) {
		this.initiales  = init;
		System.out.println("set dans fichier intiales = " +initiales);
		
	}
	public void setScore(PointageAnimation pointage) {
		this.pointage = pointage;
	}
}