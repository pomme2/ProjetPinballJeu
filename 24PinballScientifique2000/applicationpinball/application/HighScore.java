package application;

import java.util.Vector;
import java.io.*;
import java.util.StringTokenizer;

/**
 * Classe qui permet de cr�er des scores qui lient un nom � un pointage, qui trie les pointages et l'enregistre dans un fichier
 * @author Audrey Viger
 */

public class HighScore {
  private Vector HighScore_courant = null;
  private String[] LesNoms;
  private int[][] LesComparaisons;
  private String Fichier = "";
  private String Delimiteur = "   ";
  private String etat = "Normal";
  public int nb_max;

  /**
   * Constructeur : Cr�e un highScore et essaie de le charger � partir du fichier . L'highScore est born� par nb_max.
   * permet de cr�er un highScore � 2 colonnes (Nom et Score) tri�.
   * L'highScore  sera enregistr� dans un fichier 
   *
   * @param Noms Le nom des colonnes du highScore.
   * @param Comparaisons Le tableau de comparaisons.
   * @param nb_max Le nombre maximum de lignes dans l'highScore.
   * @param Fichier Le fichier de l'highScore.
   * @param Delim Le d�limiteur pour le fichier.
   */
  public HighScore(String[] Noms, int[][] Comparaisons, int nb_max, String Fichier, String Delim) {
    LesNoms = Noms;
    LesComparaisons = Comparaisons;
    HighScore_courant = new Vector();
    this.nb_max = nb_max;
    this.Fichier = Fichier;
    this.Delimiteur = Delim;
    Charge();
  }


  /**
   * Ajoute la ligne dans le highScore. 
   * @param ligne Les �l�ments � ins�rer 
   * @return Le num�ro de la ligne dans l'highScore. -1 si elle n'y figure pas.
   */
  public int addLigne(Comparable[] ligne){
    int place = -1;
    int num_comparaison = 0;
    for(int i=0;i<HighScore_courant.size();i++){
      if(num_comparaison > 0){
        for(int j=0;j<num_comparaison;j++){
          if (ligne[LesComparaisons[j][0]].compareTo( ( (Comparable[])
              HighScore_courant.get(i))[LesComparaisons[j][0]]) != 0) {
            place = i;
            j = num_comparaison;
            i = HighScore_courant.size(); 
          }
        }
      }
      if(place != -1)
        continue;
      if (ligne[LesComparaisons[num_comparaison][0]].compareTo( ( (Comparable[])
          HighScore_courant.get(i))[LesComparaisons[num_comparaison][0]]) == 0) {
        num_comparaison++;
        if(num_comparaison == LesComparaisons.length){
          place = i;
          i = HighScore_courant.size(); 
        }else
          i--;
      }
      else
      if (LesComparaisons[num_comparaison][1] == 0) { 
        if (ligne[LesComparaisons[num_comparaison][0]].compareTo( ( (Comparable[])
            HighScore_courant.get(i))[LesComparaisons[num_comparaison][0]]) > 0) {
          place = i;
          i = HighScore_courant.size(); 
        }
      }
      else { 
        if (ligne[LesComparaisons[num_comparaison][0]].compareTo( ( (Comparable[])
            HighScore_courant.get(i))[LesComparaisons[num_comparaison][0]]) < 0) {
          place = i;
          i = HighScore_courant.size(); 
        }
      }
    }
    if(place == -1){
        place = HighScore_courant.size();
    }

    HighScore_courant.add(place, ligne);
    while(HighScore_courant.size() > nb_max)
      HighScore_courant.remove(HighScore_courant.size()-1);
    if(place >= HighScore_courant.size())
      place = -1;

    if(place != -1 && etat == "Normal")
      Enregistre();
    return place;
  }
  /**
   * Permet de r�cup�rer la ligne d�sir�e du highScore. 
   * @param num_ligne Le num�ro de ligne voulue
   * @return Les �l�ments de la ligne.
   */
  public Comparable[] getLigne(int num_ligne){
    if(num_ligne <= HighScore_courant.size())
      return (Comparable[])HighScore_courant.get(num_ligne);
    return null;
  }
  /**
   * Retourne le nombre de colonnes
   * @return le nombre de colonnes.
   */
  public int getColumnNumber(){
    return LesNoms.length;
  }
  /**
   * Retourne le nom de la colonne d�sir�e
   * @param num le num�ro de la colonne d�sir�e
   * @return Le nom de la colonne
   */
  public String getColumnName(int num){
    if(num < LesNoms.length)
      return LesNoms[num];
    return "";
  }
  /**
   * Permet de conna�tre le nombre de lignes contenues dans le highScore
   * @return Le nombre de lignes
   */
  public int getNbLines(){
    return HighScore_courant.size();
  }
  /**
   * Charge un highScore � partir du fichier nomm� par Fichier
   */
  public void Charge(){
    if(Fichier.equals(""))
      HighScore_courant.removeAllElements();
    etat = "charge";
    BufferedReader in = null;
    try {
      in = new BufferedReader(new FileReader(Fichier));
      String les_classes = in.readLine();
      StringTokenizer stringTokenizer1 = new StringTokenizer(les_classes,Delimiteur,false);

      if(stringTokenizer1.countTokens() != this.LesNoms.length)
        throw new Exception("Le fichier highscore n'est pas bien form� !");
      String[] classes = new String[this.LesNoms.length];
      int i = 0;
      while(stringTokenizer1.hasMoreTokens())
      {
        classes[i] = stringTokenizer1.nextToken();
        i++;
      }

      while (in.ready()) {
        String ligne = in.readLine();
        StringTokenizer stringTokenizer = new StringTokenizer(ligne,Delimiteur,false);

        if(stringTokenizer.countTokens() != this.LesNoms.length)
          throw new Exception("Le fichier highscore n'est pas bien form� !");

        Comparable[] ligne_hs = new Comparable[this.LesNoms.length];
        i = 0;
        while(stringTokenizer.hasMoreTokens())
        {
          ligne_hs[i] = (Comparable)Class.forName(classes[i]).getConstructor(new Class[]{new String().getClass()}).newInstance(new Object[]{new String(stringTokenizer.nextToken())});
          i++;
        }
        addLigne(ligne_hs);
      }
      in.close();
    }
    catch (Exception ex1) {
      HighScore_courant.removeAllElements();
      try {
        in.close();
      }
      catch (Exception ex) {
      }
    }
    etat = "Normal";
  }

  /**
   * Enregistre le HighScore dans le fichier nomm� par Fichier
   */
  public void Enregistre(){
    if(Fichier.equals(""))
      return;
    etat = "Enregistre";
    BufferedWriter out = null;
    try {
      out = new BufferedWriter(new FileWriter(Fichier));
      if(HighScore_courant.size() > 0)
      {
        Comparable[] Valeurs = (Comparable[])HighScore_courant.get(0);
        for (int i = 0; i < Valeurs.length; i++) {
          out.write(Delimiteur+Valeurs[i].getClass().getName());
        }
        out.newLine();
      }
      for(int i=0;i<HighScore_courant.size();i++){
        Comparable[] Valeurs = (Comparable[])HighScore_courant.get(i);
        String ligne = "";

        for(int j=0;j<Valeurs.length;j++)
          ligne += Delimiteur + Valeurs[j].toString();
        out.write(ligne);
        out.newLine();
      }
      out.close();
    }
    catch (Exception ex1) {
      ex1.printStackTrace();
      HighScore_courant.removeAllElements();
      try {
        out.close();
      }
      catch (IOException ex) {
        ex.printStackTrace();
      }
    }
    etat = "Normal";
  }

}