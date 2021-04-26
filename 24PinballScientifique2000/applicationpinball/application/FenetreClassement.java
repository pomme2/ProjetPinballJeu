package application;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
/**
 * Classe qui permet d'afficher le score selon les intitiales dans une fenetre
 * @author Adurey Viger
 *
 */
public class FenetreClassement extends JFrame {
	

		private static final long serialVersionUID = 1L;
		private App24PinballScientifique2001 fenMenu;
		private FenetreBacSable fenBac;
		private JPanel contentPane;
	
		private JTextField txtEntreeInitiales;
		private HighScore hs;
		private  FenetreFinPartie fenFinPartie;
		private FenetreBacSable fenBacSable;
		private FenetreJouer fenJouer;

		
		/**
		 * Constructeur qui permet d'instancier la fenêtre du classement dans la fenetre jouer
		 * @param fenJouer est la fenetre qui permet de jouer au jeu
		 */
		public FenetreClassement( FenetreJouer fenJouer) {
			this.fenJouer = fenJouer;
		
			
			getContentPane().setLayout(null);
			
			setBounds(600, 40, 400,400);
			contentPane = new JPanel();
			contentPane.setBackground(Color.BLACK);
			contentPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel lblNewLabel_1 = new JLabel("Classement");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 28));
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_1.setBackground(Color.BLACK);
			lblNewLabel_1.setForeground(Color.RED);
			lblNewLabel_1.setBounds(29, 19, 323, 44);
			contentPane.add(lblNewLabel_1);
			
			JTextArea textArea_1 = new JTextArea();
			textArea_1.setEditable(false);
			textArea_1.setBackground(Color.BLACK);
			textArea_1.setBounds(140, 36, 238, 31);
			contentPane.add(textArea_1);
			
			JLabel lblNewLabel = new JLabel("");
			lblNewLabel.setBounds(140, 48, 238, 31);
			contentPane.add(lblNewLabel);
			
			JTextArea textArea = new JTextArea();
			textArea.setEditable(false);
			textArea.setBackground(Color.BLACK);
			textArea.setBounds(140, 48, 238, 306);
			contentPane.add(textArea);
			
			readTextFile(textArea,"Score.txt");
			
					
			 
	         //      lblNewLabel.setText(hs.getLigne(1)[0] + "\t"+hs.getLigne(1)[1]);
			
			//hs.
			//lblNewLabel.setText(text);;
		}
		/**
		 * @wbp.parser.constructor
		 */
		/**
		 * Constructeur qui permet d'instancier la fenêtre du classement dans la fenetre bac a sable
		 * @param fenJouer est la fenetre BacSable
		 */
		public FenetreClassement(FenetreBacSable fenetreBacSable) {
		this.fenBacSable = fenetreBacSable;
			
			getContentPane().setLayout(null);
			
			setBounds(600, 40, 400,400);
			contentPane = new JPanel();
			contentPane.setBackground(Color.BLACK);
			contentPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel lblNewLabel_1 = new JLabel("Classement");
			lblNewLabel_1.setFont(new Font("Arcade Normal", Font.PLAIN, 28));
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_1.setBackground(Color.BLACK);
			lblNewLabel_1.setForeground(Color.RED);
			lblNewLabel_1.setBounds(29, 19, 323, 44);
			contentPane.add(lblNewLabel_1);
			
			JTextArea textArea_1 = new JTextArea();
			textArea_1.setEditable(false);
			textArea_1.setBackground(Color.BLACK);
			textArea_1.setBounds(140, 36, 238, 31);
			contentPane.add(textArea_1);
			
			JLabel lblNewLabel = new JLabel("");
			lblNewLabel.setBounds(140, 48, 238, 31);
			contentPane.add(lblNewLabel);
			
			JTextArea textArea = new JTextArea();
			textArea.setEditable(false);
			textArea.setBackground(Color.BLACK);
			textArea.setBounds(140, 48, 238, 306);
			contentPane.add(textArea);
			
			readTextFile(textArea,"Score.txt");
			
			
			
		}
		/**
		 * Méthode qui permet de lire un fichier et d'écrire dedans
		 * @param texte est le texte qu'on veut écrire dedans
		 * @param fileName est le nom du fichier dans lequel on veut écrire
		 */
		private void readTextFile(JTextArea texte, String fileName) 
	 	{
	 		try 
	 			{
	  			BufferedReader inStream  
	      				= new BufferedReader (new FileReader(fileName));
	 			String line = inStream.readLine(); 
	 		
	 		 	while (line != null)
	 		 	 {                        
	     	       texte.append(line + "\n");                
			      line = inStream.readLine();                  
	  			}
	   			inStream.close();                              
	  			} catch (Exception e) 
	  				{
	              texte.setText("Exception cause: "+e);
	   		      e.printStackTrace();
	  				}	 
		} 
		
}
