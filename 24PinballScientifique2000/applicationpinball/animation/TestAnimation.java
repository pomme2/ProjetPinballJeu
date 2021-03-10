
package animation;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import geometrie.Murs;

import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.JButton;

import javax.swing.JLabel;

public class TestAnimation extends JFrame {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TestAnimation() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(40, 40, 800, 1000);
	
		
		
		getContentPane().setLayout(null);
		
		ZonePinball zonePinball = new ZonePinball();
		zonePinball.setBounds(30,30, 600, 768);
		getContentPane().add(zonePinball);
		
		JCheckBox chckbxContour = new JCheckBox("Contour");
		chckbxContour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zonePinball.setContour(chckbxContour.isSelected());
			}
		});
		chckbxContour.setBounds(41, 825, 97, 23);
		getContentPane().add(chckbxContour);
		
		JCheckBox chckbxCoord = new JCheckBox("Coordonn\u00E9es");
		chckbxCoord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zonePinball.setCoord(chckbxCoord.isSelected());
			}
		});
		chckbxCoord.setBounds(41, 851, 97, 23);
		getContentPane().add(chckbxCoord);
		
		
		
		JButton btnGO = new JButton("GO");
		btnGO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zonePinball.demarrer();
				zonePinball.requestFocusInWindow();
				
			}
		});
		btnGO.setBounds(230, 837, 118, 43);
		getContentPane().add(btnGO);
		
		JButton btnPause = new JButton("Pause");
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zonePinball.arreter();
			}
		});
		btnPause.setBounds(377, 851, 89, 23);
		getContentPane().add(btnPause);
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zonePinball.retablirPosition();
			}
		});
		btnReset.setBounds(490, 851, 89, 23);
		getContentPane().add(btnReset);
		
		JButton btnNext = new JButton("Next Frame");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zonePinball.prochaineImage();
			}
		});
		btnNext.setBounds(609, 851, 89, 23);
		getContentPane().add(btnNext);
		
		JLabel lblNewLabel = new JLabel(zonePinball.getBille().getVitesse().toString());
		lblNewLabel.setBounds(652, 100, 122, 51);
		getContentPane().add(lblNewLabel);
		
	;

		
		JSpinner spinnerEtirement = new JSpinner();	
		spinnerEtirement.setBounds(144, 848, 64, 32);
		getContentPane().add(spinnerEtirement);
		spinnerEtirement.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				zonePinball.setEtirement((int)spinnerEtirement.getValue()/100.0);
				
			}
		});

	}


	public static void main(String[] args) {			
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						TestAnimation frame = new TestAnimation();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		

		

	}
}

