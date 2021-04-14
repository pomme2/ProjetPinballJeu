package application;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class FenetreClassement extends JFrame {
	

		private static final long serialVersionUID = 1L;
		private App24PinballScientifique2001 fenMenu;
		private FenetreBacSable fenBac;
		private JPanel contentPane;
		private GestionScore gestionScore;
		private JTextField txtEntreeInitiales;

	
		
		public FenetreClassement( FenetreBacSable fenBac) {
			this.fenBac = fenBac;
			getContentPane().setLayout(null);
			
			JLabel lblClassement = new JLabel("Classement");
			lblClassement.setBounds(140, 11, 153, 26);
			getContentPane().add(lblClassement);
		}
}
