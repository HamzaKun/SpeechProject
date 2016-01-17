package ma.ensa.test;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.linguist.Linguist;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import edu.cmu.sphinx.util.props.PropertyException;
import ma.ensa.Synthesis;

import java.io.File;
import java.io.IOException;
import java.net.URL;



public class VRecognizer_test {
	
    static ConfigurationManager cm;
    public static Commander_test c;

    public static void main(String[] args) {
        try {
            	URL url;
            	if (args.length > 0) {
            		url = new File(args[0]).toURI().toURL();
            	} else {
            		url = VRecognizer_test.class.getResource("grammar.config.xml");
            	}
            
            	System.out.println("Loading...");
            
            	cm = new ConfigurationManager(url);
            	Recognizer recognizer = (Recognizer) cm.lookup("recognizer");
            	Microphone microphone = (Microphone) cm.lookup("microphone");
            	
            	recognizer.allocate();

            	if (microphone.startRecording()) {
            		System.out.println("Speak: ");

            		while (true) {
            			System.out.println("Start speaking using grammar");
            			Result result = recognizer.recognize();
            			Synthesis s ; 
            			String voiceName = new String("mbrola_us1");
            			if (result != null) {
            				new Commander_test();
            				
            				String resultText = result.getBestFinalResultNoFiller();
            				resultText = c.getCommand(resultText)[1];
            				s= new Synthesis( resultText, voiceName);
            				s.SayIt();
            				System.out.println("You said: " + resultText + "\n");
            				
            				if(!resultText.isEmpty()){
            				
            					Runtime runTime = Runtime.getRuntime();
            					runTime.exec(resultText);
            				}
            				
            			} else {
            				
            				s= new Synthesis("I can't hear what you said", voiceName);
            				s.SayIt();
            				System.out.println("I can't hear what you said.\n");
            			}
            			result=null;
                    
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
