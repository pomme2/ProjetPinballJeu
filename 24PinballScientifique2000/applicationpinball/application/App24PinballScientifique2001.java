package application;
import javax.imageio.ImageIO;
import javax.print.DocFlavor.URL;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import dessinable.OutilsImage;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JOptionPane;


/**
 * 
 * 
 * @author Audrey Viger
 *  Cette classe est le menu et permet d'acceder aux autres fenetres
 */
public class App24PinballScientifique2001 extends JFrame{
	private static final long serialVersionUID = 1L;
	private FenetreOption fenOption;
	private FenetreFinPartie fenFinPartie;
	private FenetreDessin fenDessin;
	private FenetreTutoriel fenTuto;
	private FenetreBacSable fenBac;
	private FenetreJouer fenJouer;
	private Dessin dessin;
	private JPanel panelAvecImage;
	private boolean premiereFois=true;
	java.net.URL urlBilleBlanc = getClass().getClassLoader().getResource("Blanc.png");
	private boolean dessinImage;
	private java.net.URL urlArcade = getClass().getClassLoader().getResource("arcade-11.jpg");
	private Image backGround, backGroundRedim;
	//private java.net.URL urlPlay = getClass().getClassLoader().getResource("play button.jpg");
public static void main(String[] args) {
	try {
		UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
	} catch (Throwable e) {
		e.printStackTrace();
	}

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
/**
 * 
 * Methode qui intialise les fenetres secondaires
 */
public void initianilisationFenSecondaire()  {
	fenOption = new FenetreOption(this);
	fenDessin = new FenetreDessin(this);
	fenTuto = new FenetreTutoriel(this);
	fenBac = new FenetreBacSable(this, fenOption,fenFinPartie);
	fenJouer = new FenetreJouer(this,fenOption,fenFinPartie);
	//fenFinPartie = new FenetreFinPartie(this,fenBac);
}

/**
 * 
 * Constructeur du menu, on y trouve tous les boutons permettants d'acceder aux fenetres secondaires
 */
public App24PinballScientifique2001() {
	if (urlArcade == null) {
	JOptionPane.showMessageDialog(null , "Fichier pause.jpg introuvable");
	System.exit(0);
	}else {
		System.out.println("pause.jpg trouv�");
	}
	 backGround =null;
	try{
		backGround = ImageIO.read(urlArcade);
	System.out.println("fichier trouver img");
	}
	catch (IOException e) {
	System.out.println("Erreur pendant la lecture du fichier d'image");
	}
	
	 backGroundRedim= backGround.getScaledInstance(1100, 800, Image.SCALE_SMOOTH );	
	 panelAvecImage = new JPanel() {

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage( backGroundRedim,0,0,null);
	}
};
	setTitle("Menu");
	initianilisationFenSecondaire();
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(200, 40, 1100, 800);
	
	panelAvecImage.setBackground(Color.WHITE);
	panelAvecImage.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	setContentPane(panelAvecImage);
	panelAvecImage.setLayout(null);
	
	
	dessin = new Dessin();
	dessinImage = dessin.dessinImage();
	
	JButton btnOption = new JButton("Options");
	btnOption.setForeground(Color.RED);
	
	btnOption.setFont(new Font("Arcade Normal", Font.BOLD, 18));
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
	btnDessin.setForeground(Color.RED);
	btnDessin.setFont(new Font("Arcade Normal", Font.BOLD, 18));
	btnDessin.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			fenDessin.setVisible(true);
			setVisible(false);
			premiereFois=false;
		}
	});
	btnDessin.setBounds(112, 580, 244, 101);
	getContentPane().add(btnDessin);
	
	
	
	JButton btnBac = new JButton("Bac a sable");
	btnBac.setForeground(Color.RED);
	btnBac.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			fenBac.setVisible(true);
			FenetreBacSable.setCoeurActive(false);
			//fenFinPartie.setVisible(false);
			setVisible(false);
			BufferedImage imageBille = null;
			try {
				if(dessinImage || premiereFois==false ) {
					imageBille = ImageIO.read(new File(System.getProperty("user.home")+"\\ImageB.png"));
					ImageIO.write(imageBille, "png", new File(System.getProperty("user.home"),"\\ImageB2.png"));
					System.out.println("image dessine");
				}else {
					imageBille = ImageIO.read(urlBilleBlanc);
					//ImageIO.write(imageBille, "\\ImageB.png", null);
					ImageIO.write(imageBille, "png", new File(System.getProperty("user.home"),"\\ImageB.png"));
					premiereFois=false;
				}
				
				
			} catch (IOException e1) {
				
			
				e1.printStackTrace();
			}
			
		}
	});
	btnBac.setFont(new Font("Arcade Normal", Font.ITALIC, 18));
	btnBac.setBounds(112, 388, 244, 101);
	getContentPane().add(btnBac);
	
	JButton btnJouer = new JButton("Jouer");
	btnJouer.setForeground(Color.RED);
	
	btnJouer.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			fenJouer.setVisible(true);
			setVisible(false);
			
		}
	});
	btnJouer.setFont(new Font("Arcade Normal", Font.ITALIC, 18));
	btnJouer.setBounds(112, 186, 244, 101);
	getContentPane().add(btnJouer);
	
	JButton btnTuto = new JButton("Tutoriel");
	btnTuto.setForeground(Color.RED);
	btnTuto.setFont(new Font("Arcade Normal", Font.BOLD | Font.ITALIC, 18));
	btnTuto.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			fenTuto.setVisible(true);
			setVisible(false);
		}
	});
	btnTuto.setBounds(719, 382, 244, 108);
	getContentPane().add(btnTuto);
	
	JButton btnQuitter = new JButton("Quitter");
	btnQuitter.setForeground(Color.RED);
	btnQuitter.setFont(new Font("Arcade Normal", Font.BOLD | Font.ITALIC, 18));
	btnQuitter.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	});
	btnQuitter.setBounds(720, 581, 244, 102);
	getContentPane().add(btnQuitter);
	
	JLabel lblTitreApplication = new JLabel("Pinball Scientifique 2000");
	lblTitreApplication.setBackground(Color.WHITE);
	lblTitreApplication.setFont(new Font("Arcade Normal", Font.PLAIN, 38));
	lblTitreApplication.setForeground(Color.RED);
	lblTitreApplication.setBounds(73, 0, 1022, 171);
	panelAvecImage.add(lblTitreApplication);
	//OutilsImage.lireImageEtPlacerSurBouton("play button.png", btnJouer);
	
	

}
}

