package ma.ensa;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class GUI {
	public GUI(){
		JFrame fenetre = new JFrame("Speech Project");

		fenetre.setSize(450, 625);
		fenetre.setResizable(false);

		try {
			//UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
			//UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
			//UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String [] tables={"Male1","Male2","Female"};

		fenetre.setLocationRelativeTo(null);
		JLabel backGround = new JLabel();
		JTextArea You = new JTextArea("You: ");
		JTextArea Computer = new JTextArea("Computer: ");
		JComboBox<String> choix=new JComboBox<String>(tables);
		choix.setSize(80, 20);
	    //choix.setPreferredSize(new Dimension(10, 20));
	    choix.setSelectedIndex(0);
	    choix.setLocation(20, 260);
	    
		You.setFont(new Font("Verdana" , Font.BOLD, 14));
		You.setLineWrap(true);
		You.setWrapStyleWord(true);
		Computer.setWrapStyleWord(true);
		Computer.setLineWrap(true);
		Computer.setFont(new Font("Verdana" , Font.BOLD, 14));
		Computer.setForeground(new Color(120,120,120));
		You.setForeground(new Color(120,120,120));
		You.setSize(412,70);
		You.setLocation(20, 472);
		Computer.setLocation(20, 360);
		Computer.setSize(412, 70);


		JButton about = new JButton("About");
		JButton help = new JButton("Help");
		JButton start = new JButton("Start");


		start.setBounds(20,295,90,30);
		help.setBounds(170,295,90,30);
		about.setBounds(320,295,90,30);
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
		
		choix.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		        System.out.println("Selected index=" + choix.getSelectedIndex()
		            + " Selected item=" + choix.getSelectedItem());
		      }
		    });

		backGround.add(choix);
		backGround.add(You);
		backGround.add(about);
		backGround.add(help);
		backGround.add(Computer);
		backGround.add(start);
		fenetre.add(backGround);
		backGround.setIcon(new ImageIcon ("InterfaceDesign.png"));
		//backGround.setIconTextGap("InterfaceDesign");
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setVisible(true);

	}



}
