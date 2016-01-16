package ma.ensa;

import java.net.URL;
import java.util.List;

import javax.swing.SwingWorker;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import edu.cmu.sphinx.util.props.PropertyException;



public class VRecognizer extends SwingWorker<Void, String>{

	static ConfigurationManager cm;
	private String resultText, voiceN;
	private Recognizer recognizer;
	private Microphone microphone;


	/**
	 * The Constructor is only for allocating the necessary ressources
	 * the recognizer, microphone and the grammar
	 * @param args
	 */

	VRecognizer(String args) {
		try {
			URL url;
			/*if (args.length > 0) {
				url = new File(args[0]).toURI().toURL();
			} else {
				url = VRecognizer.class.getResource("grammar.config.xml");
			}*/
			voiceN = new String(args);
			url = VRecognizer.class.getResource("grammar.config.xml");
			System.out.println("Loading...");

			cm = new ConfigurationManager(url);
			recognizer = (Recognizer) cm.lookup("recognizer");
			microphone = (Microphone) cm.lookup("microphone");
		}
		/*catch (IOException e) {
			System.err.println("Problem when loading Reco: " + e);
			e.printStackTrace();
		}*/ 
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

				if (result != null) {

					resultText = result.getBestFinalResultNoFiller();
					System.out.println("You said: " + resultText + "\n");
					Commander.Respond(resultText, voiceN);
					//Here we can add the microphone.stopRecognition if necessary
					microphone.clear();
				}
			}
		}else{
			System.out.println("Cannot start microphone.");
			recognizer.deallocate();
			System.exit(1);
		}
	}

	@Override
	protected Void doInBackground() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	
	protected void process(List<String> result) {
        
    }

}
