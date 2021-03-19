
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
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;

public class FenetreDessin extends JFrame{
	private static final long serialVersionUID = 1L;
	private App24PinballScientifique2000 fenMenu;
	private Dessin dessin;
	private JPanel contentPane;
	private String couleur;
	private JColorChooser Jcc;
	private BufferedImage imageBille;
	private final JFileChooser saveFileChooser;
	protected JLabel label; 

	public FenetreDessin(App24PinballScientifique2000 fenMenu) {
		final JFrame frame = new JFrame("JColorChooser Demo");
		this.fenMenu = fenMenu;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 492, 686);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		setBounds(200, 40, 1100, 800);
		getContentPane().setLayout(null);

		saveFileChooser = new JFileChooser();
		saveFileChooser.setCurrentDirectory(new File("c:\\temp"));
		saveFileChooser.setFileFilter(new FileNameExtensionFilter("PNG IMAGES","png"));







		setTitle("Dessin");

		JButton btnRetour = new JButton("Sauvegarder et retourner au menu");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fenMenu.setVisible(true);
				setVisible(false);
				dessin.saveImage("ImageB","png");
				
				

			}
		});
		
		
	
		
		btnRetour.setBounds(757, 561, 232, 77);
		getContentPane().add(btnRetour);

		JLabel lblSelection = new JLabel("S\u00E9lectionner des couleurs");
		lblSelection.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSelection.setBounds(783, 59, 206, 29);
		getContentPane().add(lblSelection);

		JButton btnEffacerTout = new JButton("Recommencer");
		btnEffacerTout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dessin.clear();
			}
		});
		btnEffacerTout.setBounds(757, 222, 232, 46);
		contentPane.add(btnEffacerTout);

		dessin = new Dessin();
		dessin.setBounds(89, 74, 585, 621);
		contentPane.add(dessin);

		JButton btn1 = new JButton("Choisir une couleur");

		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color couleur = JColorChooser.showDialog(
						frame,
						"Choose Background Color",
						Color.white);

				if(couleur != null){

					dessin.setCouleur(couleur);

				}
			}
		});
		btn1.setBounds(757,143,232,52);
		contentPane.add(btn1);

		JSlider sliderTailleTrait = new JSlider();
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
		sliderTailleTrait.setBounds(757, 308, 232, 38);
		contentPane.add(sliderTailleTrait);

		JLabel lblTailleTrait = new JLabel("Taille du trait");
		lblTailleTrait.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTailleTrait.setBounds(757, 279, 232, 29);
		contentPane.add(lblTailleTrait);

		JButton btnEffacer = new JButton("Effacer");
		btnEffacer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dessin.setCouleur(Color.white);
			}
		});
		btnEffacer.setBounds(827, 372, 89, 23);
		contentPane.add(btnEffacer);





	}
}
