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
    
    
   
    public static void main(String[] args) {
        try {
            	URL url;
            	if (args.length > 0) {
            		url = new File(args[0]).toURI().toURL();
            	} else {
            		url = VRecognizer.class.getResource("grammar.config.xml");
            	}
            
            	System.out.println("Loading...");
            
            	cm = new ConfigurationManager(url);
            	Recognizer recognizer = (Recognizer) cm.lookup("recognizer");
            	Microphone microphone = (Microphone) cm.lookup("microphone");

            	/* allocate the resource necessary for the recognizer */
            	recognizer.allocate();

            	/* the microphone will keep recording until the program exits */
            	if (microphone.startRecording()) {
            		System.out.println("Speak: ");

            		while (true) {
            			System.out.println("Start speaking using hello grammar");
            			Result result = recognizer.recognize();
		    
            			if (result != null) {
            				String resultText = result.getBestFinalResultNoFiller();
            				System.out.println("You said: " + resultText + "\n");
            			} else {
            				System.out.println("I can't hear what you said.\n");
            			}

                    
            		}
		 
            	} else {
            		System.out.println("Cannot start microphone.");
            		recognizer.deallocate();
            		System.exit(1);
            	}
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

    static void swapGrammar(String newGrammarName) 
            throws PropertyException, InstantiationException, IOException {
        System.out.println("Swapping to grammar " + newGrammarName);
        Linguist linguist = (Linguist) cm.lookup("flatLinguist");
        linguist.deallocate();

        //cm.setProperty("jsgfGrammar", "grammarName", newGrammarName);
        linguist.allocate();
    }


}
