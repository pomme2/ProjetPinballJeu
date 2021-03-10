import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;

public class FenetreDessin extends JFrame{
	private static final long serialVersionUID = 1L;
	private ApplicationMenu fenMenu;
	
	public FenetreDessin(ApplicationMenu fenMenu) {
		this.fenMenu = fenMenu;
		setBounds(200, 40, 1100, 800);
		getContentPane().setLayout(null);
		setTitle("Dessin");
		String[] couleur = { "Rouge", "Orange","Bleu","Jaune","Vert"};
		JComboBox<Object> comboBoxCouleur = new JComboBox<Object>(couleur);
		comboBoxCouleur.setSelectedIndex(1);
		
		
		comboBoxCouleur.setBounds(783, 89, 176, 46);
		getContentPane().add(comboBoxCouleur);
		
		JButton btnRetour = new JButton("Sauvegarder et retourner au menu");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fenMenu.setVisible(true);
				setVisible(false);
			}
		});
		btnRetour.setBounds(757, 561, 232, 77);
		getContentPane().add(btnRetour);
		
		JButton btnColorie = new JButton("Colorier");
		btnColorie.setBounds(747, 324, 251, 100);
		getContentPane().add(btnColorie);
		
		JLabel lblSelection = new JLabel("S\u00E9lectionner des couleurs");
		lblSelection.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSelection.setBounds(783, 59, 206, 29);
		getContentPane().add(lblSelection);
	}
}
