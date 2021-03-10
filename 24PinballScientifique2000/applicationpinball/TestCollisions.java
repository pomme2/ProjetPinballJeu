import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import animation.ZoneSimulationPhysique;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TestCollisions extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ZoneSimulationPhysique animPhysique;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestCollisions frame = new TestCollisions();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TestCollisions() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 557, 671);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ZoneSimulationPhysique animPhysique = new ZoneSimulationPhysique();
		animPhysique.setBounds(29, 22, 500, 500);
		contentPane.add(animPhysique);
		
		JButton btnGo = new JButton("go");
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				animPhysique.demarrer();
				
			}
		});
		btnGo.setBounds(47, 564, 89, 23);
		contentPane.add(btnGo);
		
		JButton btnPause = new JButton("Pause");
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				animPhysique.arreter();
			}
		});
		btnPause.setBounds(158, 564, 89, 23);
		contentPane.add(btnPause);
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				animPhysique.retablirPosition();
			}
		});
		btnReset.setBounds(268, 564, 89, 23);
		contentPane.add(btnReset);
		
		JButton btnNext = new JButton("Next Frame");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				animPhysique.prochaineImage();
			}
		});
		btnNext.setBounds(378, 564, 89, 23);
		contentPane.add(btnNext);
		
		
	}
}
