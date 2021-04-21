package application;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;

public class FenetreClassement extends JFrame {
	

		private static final long serialVersionUID = 1L;
		private App24PinballScientifique2001 fenMenu;
		private FenetreBacSable fenBac;
		private JPanel contentPane;
		private GestionScore gestionScore;
		private JTextField txtEntreeInitiales;
		private HighScore hs;
		private  FenetreFinPartie fenFinPartie;

	
		
		public FenetreClassement( FenetreFinPartie fenFinPartie) {
			this.fenFinPartie = fenFinPartie;
			
			getContentPane().setLayout(null);
			
			setBounds(600, 40, 400,400);
			contentPane = new JPanel();
			contentPane.setBackground(Color.BLACK);
			contentPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JTextArea textArea = new JTextArea();
			textArea.setBackground(Color.BLACK);
			textArea.setBounds(140, 48, 238, 306);
			contentPane.add(textArea);
			
			readTextFile(textArea,"Score.txt");
			
					
			 
	         //      lblNewLabel.setText(hs.getLigne(1)[0] + "\t"+hs.getLigne(1)[1]);
			
			//hs.
			//lblNewLabel.setText(text);;
		}
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
