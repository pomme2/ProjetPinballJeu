package application;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;

public class FenetreFinPartie extends JFrame{

	private static final long serialVersionUID = 1L;
	private App24PinballScientifique2001 fenMenu;
	private FenetreBacSable fenBac;
	private JPanel contentPane;


	
	public FenetreFinPartie(  	App24PinballScientifique2001 fenMenu, FenetreBacSable fenBac) {
		
		this.fenBac = fenBac;
		this.fenMenu = fenMenu;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 40, 1100, 928);
		contentPane = new JPanel();
		contentPane.setBackground(Color.black);
		contentPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitre = new JLabel("FIN DE LA PARTIE");
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setFont(new Font("Showcard Gothic", Font.BOLD, 61));
		lblTitre.setForeground(Color.MAGENTA);
		lblTitre.setBounds(10, 51, 1068, 93);
		contentPane.add(lblTitre);
		
		JButton btnRecommencer = new JButton("Recommencer une partie");
		btnRecommencer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnRecommencer.setBounds(91, 448, 403, 120);
		contentPane.add(btnRecommencer);
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setBounds(516, 155, 582, 399);
		contentPane.add(fileChooser);
		fileChooser.setVisible(false);
		
		JButton btnSauvegarderScore = new JButton("Sauvegarder le score");
		btnSauvegarderScore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileChooser.setVisible(true);
			}
		});
		btnSauvegarderScore.setBounds(600, 448, 403, 120);
		contentPane.add(btnSauvegarderScore);
		
		
		
		
	}
}
