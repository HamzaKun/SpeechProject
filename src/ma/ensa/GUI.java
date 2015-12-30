package ma.ensa;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class GUI {
	public GUI(){
JFrame fenetre = new JFrame("Speech Project");
		
		fenetre.setSize(450, 600);
		fenetre.setResizable(false);
		
		try {
		    //UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
			//UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
		    //UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
		    e.printStackTrace();
		}
		fenetre.setLocationRelativeTo(null);
		JLabel backGround = new JLabel();
		JTextArea You = new JTextArea("You: ");
		JTextArea Computer = new JTextArea("Computer: ");
		
		You.setFont(new Font("Verdana" , Font.BOLD, 14));
		You.setLineWrap(true);
		You.setWrapStyleWord(true);
		Computer.setWrapStyleWord(true);
		Computer.setLineWrap(true);
		Computer.setFont(new Font("Verdana" , Font.BOLD, 14));
		Computer.setForeground(new Color(120,120,120));
		You.setForeground(new Color(120,120,120));
		You.setSize(440, 50);
		You.setLocation(0, 400);
		Computer.setLocation(0, 340);
		Computer.setSize(440, 50);
		
		
		JButton about = new JButton("About");
		JButton help = new JButton("Help");
		
		
		about.setBounds(70,280,90,30);
		help.setBounds(170,280,90,30);
		help.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				   JOptionPane.showMessageDialog(null, "");
				       }
		});
		about.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ag0){
				JOptionPane.showMessageDialog(null, "Said BOUIGHERDAINE \n Hamza KASRY");
				Synthesis s = new Synthesis("Said BOUIGHERDAINE , Hamza KASRY");
				s.SayIt();
			}
		});
		
		backGround.add(You);
		backGround.add(about);
		backGround.add(help);
		backGround.add(Computer);
		fenetre.add(backGround);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setVisible(true);

	}



}
