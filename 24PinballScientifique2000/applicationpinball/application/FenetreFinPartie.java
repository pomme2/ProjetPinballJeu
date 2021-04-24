package application;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import animation.PointageAnimation;
import animation.Scene;
import animation.ZonePinball;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;
import application.FenetreJouer;

public class FenetreFinPartie extends JFrame{

	private static final long serialVersionUID = 1L;
	private App24PinballScientifique2001 fenMenu;
	private FenetreBacSable fenBac;
	private JPanel panelAvecImage;
	private Image backGround,backGroundRedim;
	private java.net.URL urlArcade = getClass().getClassLoader().getResource("gameOver.png");
	private GestionScore gestionScore;
	private JTextField txtEntreeInitiales;
	private FenetreJouer fenJouer;
	private PointageAnimation pointage;

	private static FenetreClassement fenClassement;

	private Scene scene;
	private int scoreFinal;
	



	public String getInitiales() {
		String initiales = txtEntreeInitiales.getText().toString();
		return initiales;
	}
	
	public FenetreFinPartie(  	App24PinballScientifique2001 fenMenu, FenetreBacSable fenBac1, FenetreJouer fenJouer, FenetreClassement fenClassement1) {
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

		backGroundRedim= backGround.getScaledInstance(1210, 990, Image.SCALE_SMOOTH );	
		panelAvecImage = new JPanel() {

			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.drawImage( backGroundRedim,0,0,null);
			}
		};
		scoreFinal = ZonePinball.getScorefinal();
		
		this.fenBac = fenBac1;
		this.fenMenu = fenMenu;
		this.fenJouer = fenJouer;
		gestionScore = new GestionScore();
		pointage = new PointageAnimation();
	//	FenetreClassement fenClassement1 = new FenetreClassement(this);
	
		HighScore hs = new HighScore(new String[]{"Nom","Score"},new int[][]
                {{1,0},{0,1}},10,"Score.txt","  ");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 40, 1100, 928);		
		panelAvecImage.setBackground(Color.black);
		panelAvecImage.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setContentPane(panelAvecImage);
		panelAvecImage.setLayout(null);
		
		JLabel lblTitre = new JLabel("FIN DE LA PARTIE");
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setFont(new Font("Arcade Normal", Font.BOLD, 61));
		lblTitre.setForeground(Color.CYAN);
		lblTitre.setBounds(10, 51, 1068, 93);
		panelAvecImage.add(lblTitre);
		
		/*JFileChooser fileChooser = new JFileChooser();
		fileChooser.setBounds(246, 134, 582, 399);
		contentPane.add(fileChooser);
		fileChooser.setVisible(false);*/
		
		JButton btnRecommencer = new JButton("Revenir au menu");
		btnRecommencer.setForeground(Color.CYAN);
		btnRecommencer.setFont(new Font("Arcade Normal", Font.PLAIN, 11));
		btnRecommencer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fenClassement = new FenetreClassement(fenBac);
				fenMenu.setVisible(true);
				setVisible(false);						
			}
		});
		btnRecommencer.setBounds(91, 594, 403, 120);
		panelAvecImage.add(btnRecommencer);
		
		JLabel lblInitiales = new JLabel("Entrez vos initiales");
		lblInitiales.setFont(new Font("Arcade Normal", Font.PLAIN, 20));
		lblInitiales.setForeground(Color.BLUE);
		lblInitiales.setBounds(317, 361, 438, 35);
		panelAvecImage.add(lblInitiales);
		lblInitiales.setVisible(false);
		
		txtEntreeInitiales = new JTextField();
		txtEntreeInitiales.setBounds(317, 394, 438, 82);
		panelAvecImage.add(txtEntreeInitiales);
		txtEntreeInitiales.setColumns(10);
		txtEntreeInitiales.setVisible(false);
		
		JButton btnEnregistrerInit = new JButton("Enregistrer");
		btnEnregistrerInit.setForeground(Color.CYAN);
		btnEnregistrerInit.setFont(new Font("Arcade Normal", Font.PLAIN, 11));
		btnEnregistrerInit.addActionListener(new ActionListener() {
		

		

			public void actionPerformed(ActionEvent e) {
				String initiales = txtEntreeInitiales.getText().toString();
			
				hs.addLigne(new Comparable[]{new String(initiales),
                    Integer.valueOf(scoreFinal)});
				 /*for(int i=0;i<hs.getNbLines();i++)
		               System.out.println(hs.getLigne(i)[0] + "\t"+hs.getLigne(i)[1]);*/
				
				 //hs.Enregistre();
				
				
			
				
			
				
			//	System.out.println(txtEntreeInitiales.getText().toString());
			}
		});
		btnEnregistrerInit.setBounds(317, 487, 438, 35);
		panelAvecImage.add(btnEnregistrerInit);
		btnEnregistrerInit.setVisible(false);
		
		
		JButton btnSauvegarderScore = new JButton("Sauvegarder le score");
		btnSauvegarderScore.setForeground(Color.CYAN);
		btnSauvegarderScore.setFont(new Font("Arcade Normal", Font.PLAIN, 11));
		btnSauvegarderScore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblInitiales.setVisible(true);
				txtEntreeInitiales.setVisible(true);
				btnEnregistrerInit.setVisible(true);
				
		
			}
		});
		
	

		btnSauvegarderScore.setBounds(600, 594, 403, 120);
		panelAvecImage.add(btnSauvegarderScore);
		
		JLabel lblScore = new JLabel("Score: ");
		lblScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblScore.setText("Score: "+scoreFinal);
		lblScore.setFont(new Font("Arcade Normal", Font.PLAIN, 52));
		lblScore.setForeground(Color.CYAN);
		lblScore.setBounds(252, 155, 583, 82);
		panelAvecImage.add(lblScore);
	
	}


}
