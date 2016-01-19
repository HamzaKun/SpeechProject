package ma.ensa;

import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.swing.SwingWorker;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import edu.cmu.sphinx.util.props.PropertyException;



public class VRecognizer{

	static ConfigurationManager cm;
	private String resultText;
	public static String voiceN;
	String speech;
	int nbreAppel=0;
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
			/* allocate the resource necessary for the recognizer */
			recognizer.allocate();

		}catch (PropertyException e) {
			System.err.println("Problem configuring Reco: " + e);
			e.printStackTrace();
		} 

	}

	/**
	 * StartRec is the function that starts the recognition
	 */
	public String StartRec(){
		if(nbreAppel ==0){
			GUI.computerSetText("Computer: What can I do for you?");
			Synthesis s ;
			s= new Synthesis("What can I do for you?", voiceN);
			s.SayIt();
		}
		
		/* the microphone will keep recording until the program exits */
		if(nbreAppel++ == 0){
			if (! microphone.startRecording()){
				System.out.println("Cannot start microphone.");
				recognizer.deallocate();
				System.exit(1);
			}
		}else{
			microphone.clear();
			nbreAppel++;
		}

		//microphone.clear();

		System.out.println("Start speaking using hello grammar");
		Result result = recognizer.recognize();

		if (result != null) {
			resultText = result.getBestFinalResultNoFiller();
			//GUI.youSetText("You said: " + resultText + "\n");
			System.out.println("You said: " + resultText + "\n");
			speech = resultText;
			//The static methode Commander.Respond either runs a command, or 
			//Speaks whit the user - Responding to him -
			//					Commander.Respond(resultText, voiceN);
			//Here we can add the microphone.stopRecognition if necessary
			//microphone.stopRecording();
			microphone.clear();
			GUI.youSetText("You: "+resultText);
			return resultText;
		}else{
			return null;
		}
	}

	String  Respond(String resultText, String voice){
		Synthesis s ; 
		//		s= new Synthesis("You said: " +resultText, voice);
		//System.out.println("You said: " +resultText);
		//String response = new String();
		s= new Synthesis("You said "+resultText, voice);
		s.SayIt();
		GUI.computerSetText("You said :"+resultText);
		
		////
		String []cmd = getCommand(resultText);
		try{
			if(cmdType(resultText)==1 ||cmdType(resultText)==2 ||cmdType(resultText)==3 ){ //||cmdType(resultText)==5){
				if(!resultText.equals("date please") && !resultText.equals("time please") ){
				ProcessBuilder process = new ProcessBuilder("CMD", "/C", cmd[0]);
				process.start(); //Thread.sleep(1000); //process.wait(10);
				System.out.println(getCommand(resultText)[0]);
				System.out.println(getCommand(resultText)[1]);
				}
			}else {
				return (cmd[1]);
			}
			
		}catch(Exception e){
			System.out.println("Problem while trying to run the command");
			e.printStackTrace();
		}
		//////
		
		
		
		
		//return ("You said : "+resultText);
		return (cmd[1]);
	}

	public String filter (String cmd){
		String [] parts = cmd.split(" ");
		String command= new String();
		if ( parts[0].equalsIgnoreCase("open") ||parts[0].equalsIgnoreCase("close") ||parts[0].equalsIgnoreCase("enable") || parts[0].equalsIgnoreCase("disable")){
			for (int i=1 ;i<parts.length ; i++){
				command+=parts[i];
			}
		}
		return command;
	}

	public int cmdType(String cmd){
		String[] parts = cmd.split(" ");
		if(parts[0].equalsIgnoreCase("open")) return 1;
		else if(parts[0].equalsIgnoreCase("close")) return 2;
		else if(parts[0].equalsIgnoreCase("enable") || parts[0].equalsIgnoreCase("disable")) return 3;
		else if(parts[0].equalsIgnoreCase("start") || parts[0].equalsIgnoreCase("stop")) return 4;
		else if(parts[0].equalsIgnoreCase("time") || parts[0].equalsIgnoreCase("date")||
				parts[0].equalsIgnoreCase("log") || parts[0].equalsIgnoreCase("shutdown")||
				parts[0].equalsIgnoreCase("restart") || parts[0].equalsIgnoreCase("sleep")) return 5;
		else return 6;
	}

	public String[] getCommand(String cmd){
		String query[]=new String[2];
		if(cmdType(cmd)==1){
			cmd=filter(cmd);
			switch(cmd){
			case "notepad": query[0]="start notepad";query[1]="Opening Notepad";break;
			case "chrome" : query[0]="start chrome";query[1]="Opening Chrome";break;
			case "firefox": query[0]="start firefox";query[1]="Opening FireFox";break;
			case "powerpoint": query[0]="start powerpnt";query[1]="Opening PowerPoint";break;
			case "word": query[0]="start winword";query[1]="Opening Word";break;
			case "excel": query[0]="start excel";query[1]="Opening Execel";break;

			case "foxitreader": query[0]="start FoxitReader";query[1]="Opening FoxitReader";break;
			case "winrar": query[0]="start winrar";query[1]="Opening Winrar";break;
			case "skype": query[0]="start skype";query[1]="Opening Skype";break;
			case "photoshop": query[0]="start photoshop";query[1]="Opening Photoshop";break;
			case "windowsmediaplayer": query[0]="start wmplayer";query[1]="Opening Windos Media Player";break;
			case "vmware": query[0]="start vmware";query[1]="Opening Vmware";break;
			case "internetexplorer": query[0]="start iexplore";query[1]="Opening Internet Explorer";break;
			case "terminal": query[0]="start";query[1]="Opening Terminal";break;

			case "browser": query[0]="https:";query[1]="Opening Default Browser";break;
			case "controlpanel": query[0]="control panel";query[1]="Opening Control Panel";break;
			case "desktop": query[0]="start desktop";query[1]="Opening Desktop Folder";break;
			case "computer": query[0]="explorer /root,";query[1]="Opening Computer Folder";break;
			case "MyDocuments": query[0]="start documents";query[1]="Opening My Documents Folder";break;
			case "MyVideos": query[0]="start Videos";query[1]="Opening My Videos Folder";break;
			case "MyPictures": query[0]="start Pictures";query[1]="Opening My Pictures Folder";break;
			case "Recyclebin": query[0]="start shell:RecycleBinFolder";query[1]="Opening Recycle bin Folder";break;
			case "calculator": query[0]="start calc";query[1]="Opening Caculator";break;
			case "Keybord" : query[0]="start osk";query[1]="Opening Visual Keybord";break;

			case "facebook" : query[0]="start https:/www.facebook.com ";query[1]="Opening www.facebook.com";break;
			case "twitter" : query[0]="start https:/www.twitter.com ";query[1]="Opening www.twitter.com";break;
			case "google" : query[0]="start https:/www.google.com ";query[1]="Opening www.google.com";break;
			case "ensamarrakech" : query[0]="start https:/www.ensa.ac.ma ";query[1]="Opening www.ensa.ac.ma";break;
			case "gmail" : query[0]="start https:/www.gmail.com ";query[1]="Opening www.gmail.com";break;
			case "stackoverflow" : query[0]="start https:/www.stackoverflow.com ";query[1]="Opening www.stackoverflow.com";break;
			case "github" : query[0]="start https:/www.github.com ";query[1]="Opening www.github.com";break;
			case "openclassrooms" : query[0]="start https:/www.openclassrooms.com ";query[1]="Opening www.openclassrooms.com";break;
			default : query[0]="";query[1]="Command not Valid";break;
			}
			return query;

		}else if(cmdType(cmd)==2){
			cmd=filter(cmd);
			switch(cmd){
			case "notepad": query[0]="taskkill /IM notepad.exe";query[1]="Closing Notepad";break;
			case "chrome" : query[0]="taskkill /IM chrome.exe";query[1]="Closing Chrome";break;
			case "firefox": query[0]="taskkill /IM firefox.exe";query[1]="Closing FireFox";break;
			case "powerpoint": query[0]="taskkill /IM powerpnt.exe";query[1]="Closing PowerPoint";break;
			case "word": query[0]="taskkill /IM winword.exe";query[1]="Closing Word";break;
			case "excel": query[0]="taskkill /IM excel.exe";query[1]="Closing Execel";break;

			case "foxitreader": query[0]="taskkill /IM FoxitReader.exe";query[1]="Closing FoxitReader";break;
			case "winrar": query[0]="taskkill /IM winrar.exe";query[1]="Closing Winrar";break;
			case "skype": query[0]="taskkill /IM skype.exe";query[1]="Closing Skype";break;
			case "photoshop": query[0]="taskkill /IM photoshop.exe";query[1]="Closing Photoshop";break;
			case "windowsmediaplayer": query[0]="taskkill /IM wmplayer.exe";query[1]="Closing Windos Media Player";break;
			case "vmware": query[0]="taskkill /IM vmware.exe";query[1]="Closing Vmware";break;
			case "internetexplorer": query[0]="taskkill /IM iexplore.exe";query[1]="Closing Internet Explorer";break;
			case "terminal": query[0]="taskkill /IM cmd.exe";query[1]="Closing Terminal";break;

			case "window": query[0]=" ";query[1]="Closing Current Window";break;
			default : query[0]="";query[1]="Command not Valid";break;
			}
			return query;

		}else if(cmdType(cmd)==3){
			switch(cmd){
			case "enable sound": query[0]="net start Audiosrv";query[1]="Enabling Sound";break;
			case "disable sound" : query[0]="net stop Audiosrv";query[1]="Disabling Sound";break;
			case "enable wirless": query[0]="netsh interface set interface name=\"Local Area Connection\" enable";query[1]="Enabling Wirless Network";break;
			case "disable wirless": query[0]="netsh interface set interface name=\"Local Area Connection\" disable";query[1]="Disabling Wirless Network";break;
			default : query[0]="";query[1]="Command not Valid";break;
			}
			return query;

		}else if(cmdType(cmd)==4){
			switch(cmd){
			case "start recognition": query[0]="start recognition";query[1]="Starting Recognition";break;
			case "stop recognition" : query[0]="stop recognition";query[1]="Stoping Recognition";break;
			default : query[0]="";query[1]="Command not Valid";break;
			}
			return query;

		}else if(cmdType(cmd)==5){
			switch(cmd){
			case "shutdown": query[0]="shutdown -s";query[1]="Shutdown";break;
			case "log off" : query[0]="stop recognition";query[1]="Log off";break;
			case "restart": query[0]="";query[1]="shutdown -r";break;
			case "sleep": query[0]="";query[1]="shutdown -h";break;
			case "date please":
				Date d = new Date();
				query[0]=d.toString();query[1]="Date is ";break;
			case "time please": 
				Date d1 = new Date();
				query[0]=d1.toString();query[1]="Time is ";break;
			default : query[0]="";query[1]="Command not Valid";break;
			}
			return query;

		}else if(cmdType(cmd)==7){
			switch(cmd){
			case "what is your name": query[0]="";query[1]="My name is Computer";break;
			case "what is your age" : query[0]="";query[1]="I Am 20 years old";break;
			case "what is success": query[0]="";query[1]="Success is this Project";break;
			case "tell me a joke": query[0]="";query[1]="I Am a Joke hahahahahahahaha!";break;
			case "tell me an other one": query[0]="";query[1]="You Are an other Joke hahahahahaha";break;
			default : query[0]="";query[1]="Sorry i dont understand";break;
			}
			return query;

		}
		return null;

	}

}

/**
 * Class to use for updating the GUI components
 * @author hamza
 *
 */

class ResPair {
	final String comp, you;
	ResPair(String comp, String you) {
		this.comp = new String(comp);
		this.you = new String(you);
	}
}


class RecoTask extends SwingWorker<Void, ResPair> {
	int i=0;
	@Override

	protected Void doInBackground() throws Exception {
		//System.out.println("In background");
		//voiceN = new String(GUI.getVoiceN());
		VRecognizer vrec = new VRecognizer(GUI.getVoiceN());
		while (!isCancelled()) {
			System.out.println("here: " + (i++));

			String YOU = new String(vrec.StartRec());
			//System.out.println(YOU);
			String COMP = new String(vrec.Respond(YOU, GUI.getVoiceN()));
			//String h = new String(vrec.StartRec());
			//System.out.println((i++)+" Before publishing");
			publish(new ResPair(YOU, COMP));

		}
		return null;
	}



	protected void process(List<ResPair> result) {
		GUI.youSetText("You: "+result.get(result.size()-1).you);
		GUI.computerSetText("Computer: You said "+result.get(result.size()-1).comp);
	}

}
