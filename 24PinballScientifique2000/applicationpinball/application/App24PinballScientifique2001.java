package application;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.Font;

/**
 * 
 * 
 * @author Audrey Viger
 *
 */
public class App24PinballScientifique2001 extends JFrame{
	private static final long serialVersionUID = 1L;
	private FenetreOption fenOption;
	private FenetreDessin fenDessin;
	private FenetreTutoriel fenTuto;
	private FenetreBacSable fenBac;
	private FenetreJouer fenJouer;
	private JPanel contentPane;
	
public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				App24PinballScientifique2001 frame = new App24PinballScientifique2001();
				frame.setVisible(true);

				
				frame.requestFocus();


			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});
}
public void initianilisationFenSecondaire() throws IOException {
	fenOption = new FenetreOption(this);
	fenDessin = new FenetreDessin(this);
	fenTuto = new FenetreTutoriel(this);
	fenBac = new FenetreBacSable(this, fenOption);
	fenJouer = new FenetreJouer(this,fenOption);
	
}


public App24PinballScientifique2001() throws IOException {

	setTitle("Menu");
	initianilisationFenSecondaire();
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(200, 40, 1100, 800);
	
	contentPane = new JPanel();
	contentPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	setContentPane(contentPane);
	contentPane.setLayout(null);
	
	JButton btnOption = new JButton("Options");
	btnOption.setFont(new Font("Tahoma", Font.PLAIN, 18));
	btnOption.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			fenOption.setVisible(true);
			setVisible(false);
		}
	});
	getContentPane().setLayout(null);
	btnOption.setBounds(719, 186, 244, 102);
	getContentPane().add(btnOption);
	
	JButton btnDessin = new JButton("Dessin");
	btnDessin.setFont(new Font("Tahoma", Font.PLAIN, 18));
	btnDessin.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			fenDessin.setVisible(true);
			setVisible(false);
		}
	});
	btnDessin.setBounds(112, 580, 244, 101);
	getContentPane().add(btnDessin);
	
	JButton btnBac = new JButton("Bac \u00E0 sable");
	btnBac.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			fenBac.setVisible(true);
			setVisible(false);
			BufferedImage imageBille = null;
			try {
				imageBille = ImageIO.read(new File(System.getProperty("user.home")+"\\ImageB.png"));
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
		}
	});
	btnBac.setFont(new Font("Tahoma", Font.PLAIN, 18));
	btnBac.setBounds(112, 388, 244, 101);
	getContentPane().add(btnBac);
	
	JButton btnJouer = new JButton("Jouer");
	btnJouer.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			fenJouer.setVisible(true);
			setVisible(false);
		}
	});
	btnJouer.setFont(new Font("Tahoma", Font.PLAIN, 18));
	btnJouer.setBounds(112, 186, 244, 101);
	getContentPane().add(btnJouer);
	
	JButton btnTuto = new JButton("Tutoriel");
	btnTuto.setFont(new Font("Tahoma", Font.PLAIN, 18));
	btnTuto.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			fenTuto.setVisible(true);
			setVisible(false);
		}
	});
	btnTuto.setBounds(719, 382, 244, 108);
	getContentPane().add(btnTuto);
	
	JButton btnQuitter = new JButton("Quitter");
	btnQuitter.setFont(new Font("Tahoma", Font.PLAIN, 18));
	btnQuitter.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	});
	btnQuitter.setBounds(720, 581, 244, 102);
	getContentPane().add(btnQuitter);
	
	

}
}

