package application;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
/**
 * 
 * @author Audrey Viger
 *Cette permet d'initialiser la fenetre qui permet de dessiner sur la bille
 */
public class FenetreDessin extends JFrame{
	private static final long serialVersionUID = 1L;
	private App24PinballScientifique2001 fenMenu;
	private Dessin dessin;
	private JPanel panelAvecImage;
	private String couleur;
	private JColorChooser Jcc;
	private BufferedImage imageBille;
	private final JFileChooser saveFileChooser;
	protected JLabel label; 	
	private String nomFichierSonMenu= ".//Ressource//8BitMenu.wav"; 
	private Image backGround,backGroundRedim;
	private java.net.URL urlDessinNuage = getClass().getClassLoader().getResource("dessinNuage.jpg");
	private Musique musiqueDessin;
	private Musique musiqueMenu;
	/**
	 * Constructeur de la fenetre de dessin qui permet de dessiner sur la bille
	 * @param fenMenu est la fenetre du menu
	 */
	public FenetreDessin(App24PinballScientifique2001 fenMenu) {
		musiqueDessin=App24PinballScientifique2001.musiqueDessin();
		musiqueMenu=App24PinballScientifique2001.musiqueMenu();
		if (urlDessinNuage == null) {
			JOptionPane.showMessageDialog(null , "Fichier pause.jpg introuvable");
			System.exit(0);
			}else {
				System.out.println("pause.jpg trouvé");
			}
			 backGround =null;
			try{
				backGround = ImageIO.read(urlDessinNuage);
			System.out.println("fichier trouver img");
			}
			catch (IOException e) {
			System.out.println("Erreur pendant la lecture du fichier d'image");
			}
			
			 backGroundRedim= backGround.getScaledInstance(1920, 1200, Image.SCALE_SMOOTH );	
			 panelAvecImage = new JPanel() {

			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.drawImage( backGroundRedim,0,0,null);
			}
		};

		final JFrame frame = new JFrame("JColorChooser Demo");
		this.fenMenu = fenMenu;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 492, 686);		
		panelAvecImage.setBackground(Color.GRAY);
		panelAvecImage.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelAvecImage);
		panelAvecImage.setLayout(null);

		setBounds(200, 40, 1100, 800);
		getContentPane().setLayout(null);

		saveFileChooser = new JFileChooser();
		saveFileChooser.setCurrentDirectory(new File("c:\\temp"));
		saveFileChooser.setFileFilter(new FileNameExtensionFilter("PNG IMAGES","png"));		


		setTitle("Dessin");

		JButton btnRetour = new JButton("Sauvegarder et retourner au menu");
		btnRetour.setForeground(Color.BLUE);
		btnRetour.setFont(new Font("Arcade Normal", Font.PLAIN, 9));
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fenMenu.setVisible(true);
				setVisible(false);
				dessin.sauvegarderImage("ImageB","png");
				musiqueDessin.stop();
				musiqueMenu.reset();
				musiqueMenu.play();
				musiqueMenu.loop();

			}
		});




		btnRetour.setBounds(730, 561, 325, 102);
		getContentPane().add(btnRetour);

		JLabel lblSelection = new JLabel("Selectionner des couleurs");
		lblSelection.setFont(new Font("Arcade Normal", Font.PLAIN, 15));
		lblSelection.setBounds(686, 60, 390, 29);
		getContentPane().add(lblSelection);

		JButton btnEffacerTout = new JButton("Recommencer");
		btnEffacerTout.setForeground(Color.BLUE);
		btnEffacerTout.setFont(new Font("Arcade Normal", Font.PLAIN, 11));
		btnEffacerTout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dessin.clear();
			}
		});
		btnEffacerTout.setBounds(757, 239, 232, 46);
		panelAvecImage.add(btnEffacerTout);

		dessin = new Dessin();		
		dessin.setBounds(89, 74, 585, 621);
		panelAvecImage.add(dessin);
		JButton btn1 = new JButton("Choisir une couleur");
		btn1.setForeground(Color.BLUE);
		btn1.setFont(new Font("Arcade Normal", Font.PLAIN, 10));

		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color couleur = JColorChooser.showDialog(
						frame,
						"Choisir une couleur",
						Color.white);

				if(couleur != null){

					dessin.setCouleur(couleur);

				}
			}
		});
		btn1.setBounds(757,159,232,52);
		panelAvecImage.add(btn1);

		JSlider sliderTailleTrait = new JSlider();
		sliderTailleTrait.setFont(new Font("Arcade Normal", Font.PLAIN, 11));
		sliderTailleTrait.setMinorTickSpacing(5);
		sliderTailleTrait.setMajorTickSpacing(15);
		sliderTailleTrait.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				dessin.setDiametre(sliderTailleTrait.getValue());
			}
		});
		sliderTailleTrait.setPaintLabels(true);
		sliderTailleTrait.setValue(5);
		sliderTailleTrait.setMinimum(5);
		sliderTailleTrait.setPaintTicks(true);
		sliderTailleTrait.setBounds(769, 351, 232, 38);
		panelAvecImage.add(sliderTailleTrait);

		JLabel lblTailleTrait = new JLabel("Taille du trait");
		lblTailleTrait.setFont(new Font("Arcade Normal", Font.PLAIN, 15));
		lblTailleTrait.setBounds(769, 311, 232, 29);
		panelAvecImage.add(lblTailleTrait);

		JButton btnEffacer = new JButton("Effacer");
		btnEffacer.setForeground(Color.BLUE);
		btnEffacer.setFont(new Font("Arcade Normal", Font.PLAIN, 9));
		btnEffacer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dessin.setCouleur(Color.white);
			}
		});
		btnEffacer.setBounds(829, 400, 104, 23);
		panelAvecImage.add(btnEffacer);





	}
}
