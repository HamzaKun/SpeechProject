package ma.ensa;

import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//GUI Ihm = new GUI();
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI();
            }
        });

	}

}
