package ma.ensa;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class Synthesis {
	VoiceManager freettsVM;
    Voice freettsVoice;
    String words;
    
    public Synthesis(String speech){
    	 // Set propreties
        //System.setProperty("mbrola.base", "C:/Users/hamza/Desktop/SpeechDocuments/Syntesis/mbrola");
        ////
    	
    	System.setProperty("mbrola.base","C:/Users/ibouig/Documents/GitHub/SpeechProject/mbrola");

        freettsVM = VoiceManager.getInstance();

        // Simply change to MBROLA voice
        freettsVoice = freettsVM.getVoice("mbrola_us1");
        

        // Allocate your chosen voice
        freettsVoice.allocate();
        //To speak
        words = new String(speech);
        //freettsVoice.speak(speech);
    }
    
    public void setWords(String speech){
    	words = new String(speech);
    }
    
    public void SayIt(){
    	freettsVoice.speak(words);
    }

}
