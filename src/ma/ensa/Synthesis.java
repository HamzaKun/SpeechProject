package ma.ensa;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class Synthesis implements SynthesisDAO{
	VoiceManager freettsVM;
	Voice freettsVoice;
	String words, voice;
	/**
	 * Create a Synthesizable object with
	 * with the the default voice mbrola_us1
	 * @param speech
	 */
	public Synthesis(String speech, String v){
		// Set properties
		System.setProperty("mbrola.base", "./mbrola");

		freettsVM = VoiceManager.getInstance();

		// Simply change to MBROLA voice
		
//		freettsVoice = freettsVM.getVoice("mbrola_us1");
		//System.out.println("In Synthesis :"+v);
		freettsVoice = freettsVM.getVoice(v);
		// Allocate your chosen voice
		freettsVoice.allocate();
		
		//Set the word to be spoken
		words = new String(speech);
		//System.out.println("Getting out of synthesis");
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
		System.out.println("I've said: "+words);
	}

}
