package ma.ensa;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.linguist.Linguist;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import edu.cmu.sphinx.util.props.PropertyException;

import java.io.File;
import java.io.IOException;
import java.net.URL;



public class VRecognizer {

	static ConfigurationManager cm;
	private String resultText;
	private Recognizer recognizer;
	private Microphone microphone;


	/**
	 * The Constructor is only for allocating the necessary ressources
	 * the recognizer, microphone and the grammar
	 * @param args
	 */

	VRecognizer(String[] args){
		try {
			URL url;
			if (args.length > 0) {
				url = new File(args[0]).toURI().toURL();
			} else {
				url = VRecognizer.class.getResource("grammar.config.xml");
			}

			System.out.println("Loading...");

			cm = new ConfigurationManager(url);
			recognizer = (Recognizer) cm.lookup("recognizer");
			microphone = (Microphone) cm.lookup("microphone");
		}
		catch (IOException e) {
			System.err.println("Problem when loading Reco: " + e);
			e.printStackTrace();
		} 
		catch (PropertyException e) {
			System.err.println("Problem configuring Reco: " + e);
			e.printStackTrace();
		} 

	}

	/**
	 * StartRec is the function that starts the recognition
	 */
	void StartRec(){
		/* allocate the resource necessary for the recognizer */
		recognizer.allocate();

		/* the microphone will keep recording until the program exits */
		if (microphone.startRecording()){
			System.out.println("Speak: ");

			while (true) {
				System.out.println("Start speaking using hello grammar");
				Result result = recognizer.recognize();
				Synthesis s ; 

				if (result != null) {

					resultText = result.getBestFinalResultNoFiller();
					System.out.println("You said: " + resultText + "\n");
					s= new Synthesis( resultText);
					s.SayIt();
					if(isCommand(resultText)){
						try{
							Process exec = new ProcessBuilder("CMD", "/C", getCommand(resultText)).start();
						}catch(Exception e){
							System.out.println("Problem while trying to run a command");
							e.printStackTrace();
						}
						//Here we can add the microphone.stopRecognition if necessary
						microphone.clear();
					}
				} else {
					System.out.println("I can't hear what you said.\n");
					s= new Synthesis("I can't hear what you said");
					s.SayIt();
				}
			}
		}else{
			System.out.println("Cannot start microphone.");
			recognizer.deallocate();
			System.exit(1);
		}
	}


	/**
	 * A function that checks if the result of the recognition is a command
	 * as open, close or some others
	 * @param arg
	 * @return
	 */
	public boolean isCommand(String arg){
		boolean b = true;
		return b;
	}
	
	/**
	 * Used to get the command
	 * the String that will be added to the ProcessBuilder
	 * @param arg
	 * @return
	 */
	public String getCommand(String arg){
		String cmd = new String();
		return cmd;
	}
	public static void main(String[] args) {

	}

}
