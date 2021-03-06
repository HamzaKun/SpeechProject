package ma.ensa;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GUI {
	JLabel backGround ;
	static JTextArea You ;
	static JTextArea Computer ;
	JComboBox<String> choix;
	static String voiceName ;
	RecoTask recoTask;
	

	public GUI(){
		JFrame fenetre = new JFrame("Speech Project");
		fenetre.setIconImage(new ImageIcon("icone.gif").getImage());
		fenetre.setSize(450, 625);
		fenetre.setResizable(false);
		fenetre.setIconImage(new ImageIcon("icone.gif").getImage());

		try {
			//UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
			//UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
			//UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String [] tables={"kevin16", "mbrola_us1","mbrola_us2","mbrola_us3"};

		voiceName = new String("kevin16");
		fenetre.setLocationRelativeTo(null);
		backGround = new JLabel();
		You = new JTextArea("You: ");
		Computer = new JTextArea("Computer: ");
		choix=new JComboBox<String>(tables);
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
		about.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				JFrame aboutF = new JFrame();
				JLabel hlab = new JLabel();
				aboutF.setSize(478, 268);
				aboutF.setResizable(false);
				aboutF.setIconImage(new ImageIcon("icone.gif").getImage());
				hlab.setIcon(new ImageIcon ("about.jpg"));
				aboutF.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				aboutF.getContentPane().add(hlab);
				
				aboutF.setLocationRelativeTo(null);
				aboutF.setVisible(true);
				
			}
		});
		start.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Synthesis s = new Synthesis("Starting recognition",voiceName);
				Computer.setText("Computer : Starting recognition");
				s.SayIt();
				recoTask = new RecoTask();
				recoTask.execute();
				

				//JOptionPane.showMessageDialog(null, "");
//				VRecognizer recognize = new VRecognizer((String)choix.getSelectedItem());
//				recognize.StartRec();

			}
		});
		help.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ag0){
				JFrame helpF = new JFrame();
				JLabel hlab = new JLabel();
				JScrollPane scroll = new JScrollPane(hlab);

		        helpF.add(scroll);
				helpF.setSize(536, 468);
				helpF.setResizable(false);
				helpF.setIconImage(new ImageIcon("icone.gif").getImage());
				hlab.setIcon(new ImageIcon ("help.jpg"));
				helpF.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				helpF.getContentPane().add(scroll);
				
				helpF.setLocationRelativeTo(null);
				helpF.setVisible(true);
			}
		});

		fenetre.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				System.exit(0);
			}
		});

		choix.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Selected item= " + choix.getSelectedItem());
				voiceName = (String)choix.getSelectedItem();
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

	public static void youSetText(String text){
		You.setText(text);
	}
	
	public static void computerSetText(String text){
		Computer.setText(text);
	}
	
	public static String getVoiceN(){
		return voiceName;
	}

}
