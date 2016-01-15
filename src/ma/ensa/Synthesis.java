package ma.ensa;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class Synthesis {
	VoiceManager freettsVM;
	Voice freettsVoice;
	String words;
	/**
	 * Create a Synthesizable object with
	 * with the the default voice mbrola_us1
	 * @param speech
	 */
	public Synthesis(String speech){
		// Set properties
		System.setProperty("mbrola.base", "./mbrola");

		freettsVM = VoiceManager.getInstance();

		// Simply change to MBROLA voice
		freettsVoice = freettsVM.getVoice("mbrola_us1");

		// Allocate your chosen voice
		freettsVoice.allocate();
		
		//Set the word to be spoken
		words = new String(speech);
	}

	/**
	 * To change the words to be spoken
	 * @param speech
	 */
	public void setWords(String speech){
		words = new String(speech);
	}

	/**
	 * Change the voice, and reallocate it 
	 * @param name
	 */
	public void setVoice(String name){
		freettsVoice = freettsVM.getVoice(name);
		freettsVoice.allocate();
	}
	
	/**
	 * To start speaking
	 */
	public void SayIt(){
		freettsVoice.speak(words);
	}

}
