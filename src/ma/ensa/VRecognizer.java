package ma.ensa;

import java.net.URL;
import java.text.SimpleDateFormat;
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
		//<<<<<<< HEAD
		//		s= new Synthesis("You said "+resultText, voice);
		//		s.SayIt();
		//		GUI.computerSetText("You said :"+resultText);
		//=======
		//		
		//		
		//>>>>>>> origin/master

		////
		String []cmd = getCommand(resultText);
		s= new Synthesis(cmd[1], voice);
		s.SayIt();
		if(cmdType(resultText)==1 ||cmdType(resultText)==2 ||cmdType(resultText)==3 ||cmdType(resultText)==5){
			if(!resultText.endsWith("please")){ //("date please") && !resultText.equals("time please") ){
				try{
					ProcessBuilder process = new ProcessBuilder("CMD", "/C", cmd[0]);
					process.start(); //Thread.sleep(1000); //process.wait(10);
					System.out.println(getCommand(resultText)[0]);
					System.out.println(getCommand(resultText)[1]);
					return (cmd[1]);
				}catch(Exception e){
					System.out.println("Problem while trying to run the command");
					e.printStackTrace();
				}
			}
		}else if(getCommand(resultText)[0].equals("stop recognition")) System.exit(1);
		GUI.youSetText("Computer: "+cmd[1]);
		return (cmd[1]);
	}



	//////




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
			case "sublimetext":query[0]="start C:\\\"Program Files (x86)\"\\\"Sublime Text 3\"\\sublime_text.exe";query[1]="Opening Sublime Text";break;
			case "powerpoint": query[0]="start powerpnt";query[1]="Opening Microsoft PowerPoint";break;
			case "microsoftword": query[0]="start winword";query[1]="Opening Microsoft Word";break;
			case "paint" : query[0]="start mspaint";query[1]="Opening Paint";break;
			case "excel": query[0]="start excel";query[1]="Opening Microsoft Excel";break;
			case "adobereader": query[0]="start C:\\\"Program Files (x86)\"\\Adobe\\\"Reader 10.0\"\\Reader\\AcroRd32.exe";query[1]="Opening AdobeReader";break;
			case "foxitreader": query[0]="start FoxitReader";query[1]="Opening FoxitReader";break;
			case "winrare": query[0]="start winrar";query[1]="Opening Winrar";break;
			case "skype": query[0]="start skype";query[1]="Opening Skype";break;
			case "photoshop": query[0]="start photoshop";query[1]="Opening Photoshop";break;
			case "wampserver" : query[0]="start C:\\wamp\\wampmanager.exe";query[1]="Opening WampServer";break;
			case "googledrive" : query[0]="start C:\\\"Program Files (x86)\"\\Google\\Drive\\googledrivesync.exe";query[1]="Opening Google Drive";break;
			case "dropbox" : query[0]="start C:\\Users\\ibouig\\AppData\\Roaming\\Dropbox\\bin\\Dropbox.exe";query[1]="Opening DropBox";break;
			case "vlc" : query[0]="start C:\\\"Program Files (x86)\"\\VideoLAN\\VLC\\vlc.exe";query[1]="Opening VLC";break;
			case "windowsmediaplayer": query[0]="start wmplayer";query[1]="Opening Windos Media Player";break;
			case "mediaplayer": query[0]="start wmplayer";query[1]="Opening Windos Media Player";break;
			case "eclipse": query[0]="start C:\\\"Users\\ibouig\\eclipse\\jee-mars\\eclipse\\eclipse.exe";query[1]="Opening Eclipse";break;
			case "netbeans": query[0]="start C:\\\"Program Files\"\\\"NetBeans 8.0.2\"\\bin\\netbeans64.exe";query[1]="Opening Netbeans";break;
			case "codeblocks": query[0]="start C:\\\"Program Files (x86)\"\\CodeBlocks\\codeblocks.exe";query[1]="Opening Code Blocks";break;
			case "QT": query[0]="start C:\\Qt\\Qt5.4.1\\Tools\\QtCreator\\bin\\qtcreator.exe";query[1]="Opening Q T";break;
			case "visualstudio": query[0]="start C:\\\"Program Files (x86)\"\\\"Microsoft Visual Studio 12.0\"\\Common7\\IDE\\devenv.exe";query[1]="Opening Visual Studio";break;
			case "vimware": query[0]="start C:\\\"Program Files (x86)\"\\VMware\\\"VMware Workstation\"\\vmware.exe";query[1]="Opening Vmware";break;
			case "virtualbox": query[0]="start C:\\\"Program Files\"\\Oracle\\VirtualBox\\VirtualBox.exe";query[1]="Opening Virtual Box";break;

			case "chrome" : query[0]="start chrome";query[1]="Opening Chrome Browser";break;
			case "firefox": query[0]="start firefox";query[1]="Opening FireFox Browser";break;
			case "opera": query[0]="start C:\\\"Program Files (x86)\"\\Opera\\launcher.exe";query[1]="Opening Opera Browser";break;
			case "safari": query[0]="start C:\\\"Program Files (x86)\"\\Safari\\Safari.exe";query[1]="Opening Safari Browser";break;
			case "internetexplorer": query[0]="start iexplore";query[1]="Opening Internet Explorer";break;
			case "terminal": query[0]="start";query[1]="Opening Terminal";break;
			case "window": query[0]="start explorer.exe";query[1]="Refreshing Computer";break;

			case "browser": query[0]="start https:";query[1]="Opening Default Browser";break;
			case "controlpanel": query[0]="control panel";query[1]="Opening Control Panel";break;
			case "desktop": query[0]="start desktop";query[1]="Opening Desktop Folder";break;
			case "computer": query[0]="explorer /root,";query[1]="Opening Computer Folder";break;
			case "MyDocuments": query[0]="start documents";query[1]="Opening My Documents Folder";break;
			case "MyVideos": query[0]="start Videos";query[1]="Opening My Videos Folder";break;
			case "MyPictures": query[0]="start Pictures";query[1]="Opening My Pictures Folder";break;
			case "recyclebin": query[0]="start shell:RecycleBinFolder";query[1]="Opening Recycle bin Folder";break;
			case "calculator": query[0]="start calc";query[1]="Opening Caculator";break;
			case "Keybord" : query[0]="start osk";query[1]="Opening Visual Keybord";break;

			case "facebook" : query[0]="start https:/www.facebook.com ";query[1]="Opening www.facebook.com";break;
			case "twitter" : query[0]="start https:/www.twitter.com ";query[1]="Opening www.twitter.com";break;
			case "google" : query[0]="start https:/www.google.com ";query[1]="Opening www.google.com";break;
			case "youtube" : query[0]="start https:/www.youtube.com ";query[1]="Opening www.youtube.com";break;

			//case "ensamarrakech" : query[0]="start https:/www.ensa.ac.ma ";query[1]="Opening www.ensa.ac.ma";break;
			case "gmail" : query[0]="start https:/www.gmail.com ";query[1]="Opening www.gmail.com";break;
			case "stackoverflow" : query[0]="start https:/www.stackoverflow.com ";query[1]="Opening www.stackoverflow.com";break;
			case "gethub" : query[0]="start https:/www.github.com ";query[1]="Opening www.github.com";break;
			case "openclassrooms" : query[0]="start https:/www.openclassrooms.com ";query[1]="Opening www.openclassrooms.com";break;
			default : query[0]="";query[1]="Command not Valid";break;
			}
			GUI.computerSetText("Computer: "+query[1]);
			return query;

		}else if(cmdType(cmd)==2){
			cmd=filter(cmd);
			switch(cmd){

			case "notepad": query[0]="taskkill /F /IM notepad.exe";query[1]="Closing Notepad";break;
			case "sublimetext":query[0]="taskkill /F /IM sublime_text.exe";query[1]="Closing Sublime Text";break;
			case "powerpoint": query[0]="taskkill /F /IM powerpnt.exe";query[1]="Closing Microsoft PowerPoint";break;
			case "microsoftword": query[0]="taskkill /F /IM winword.exe";query[1]="Closing Microsoft Word";break;
			case "paint" : query[0]="taskkill /F /IM mspaint.exe";query[1]="Closing Paint";break;
			case "excel": query[0]="taskkill /F /IM excel.exe";query[1]="Closing Excel";break;
			case "adobereader" : query[0]="taskkill /F /IM AcroRd32.exe";query[1]="Closing AdobeReader";break;
			case "foxitreader": query[0]="taskkill /F /IM FoxitReader.exe";query[1]="Closing FoxitReader";break;
			case "winrare": query[0]="taskkill /F /IM winrar.exe";query[1]="Closing Winrar";break;
			case "skype": query[0]="taskkill /F /IM skype.exe";query[1]="Closing Skype";break;
			case "photoshop": query[0]="taskkill /F /IM photoshop.exe";query[1]="Closing Photoshop";break;
			case "wampserver": query[0]="taskkill /F /IM wampmanager.exe";query[1]="Closing WampServer";break;
			case "googledrive": query[0]="taskkill /F /IM googledrivesync.exe";query[1]="Closing Google Drive";break;
			case "dropbox": query[0]="taskkill /F /IM Dropbox.exe";query[1]="Closing DropBox";break;
			case "vlc" : query[0]="taskkill /F /IM vlc.exe";query[1]="Closing vlc";break;
			case "windowsmediaplayer": query[0]="taskkill /F /IM wmplayer.exe";query[1]="Closing Windos Media Player";break;
			case "mediaplayer": query[0]="taskkill /F /IM wmplayer.exe";query[1]="Closing Windos Media Player";break;
			case "eclipse": query[0]="taskkill /F /IM eclipse.exe";query[1]="Closing Eclipse";break;
			case "netbeans": query[0]="taskkill /F /IM netbeans64.exe";query[1]="Closing NetBeans";break;
			case "codeblocks": query[0]="taskkill /F /IM codeblocks.exe";query[1]="Closing Code Blocks";break;
			case "QT": query[0]="taskkill /F /IM qtcreator.exe";query[1]="Closing Q T";break;
			case "visualstudio": query[0]="taskkill /F /IM devenv.exe";query[1]="Closing Visual Studio";break;
			case "vimware": query[0]="taskkill /F /IM vmware.exe";query[1]="Closing Vmware";break;
			case "virtualbox": query[0]="taskkill /F /IM VirtualBox.exe";query[1]="Closing Virtual Box";break;

			case "chrome" : query[0]="taskkill /F /IM chrome.exe";query[1]="Closing Chrome Browser";break;
			case "firefox": query[0]="taskkill /F /IM firefox.exe";query[1]="Closing FireFox Browser";break;
			case "opera": query[0]="taskkill /F /IM opera.exe";query[1]="Closing Opera Browser";break;
			case "safari": query[0]="taskkill /F/IM Safari.exe";query[1]="Closing Safari Browser";break;
			case "internetexplorer": query[0]="taskkill /F /IM iexplore.exe";query[1]="Closing Internet Explorer";break;
			case "browser": query[0]="taskkill /F/IM Safari.exe /IM chrome.exe /IM firefox.exe /IM iexplore.exe";query[1]="Closing Browsers";break;

			case "terminal": query[0]="taskkill /F /IM cmd.exe";query[1]="Closing Terminal";break;
			case "window": query[0]="start nircmd.exe sendkeypress alt+f4";query[1]="Closing Current window";break;

			case "calculator": query[0]="taskkill /F /IM calc.exe";query[1]="Closing Caculator";break;
			case "Keybord" : query[0]="taskkill /F /IM osk.exe";query[1]="Closing Visual Keybord";break;

			default : query[0]="";query[1]="Command not Valid";break;

			}
			GUI.computerSetText("Computer: "+query[1]);
			return query;

		}else if(cmdType(cmd)==3){
			switch(cmd){
			case "enable sound": query[0]="start nircmd.exe mutesysvolume 0";query[1]="Enabling Sound";break;
			case "disable sound" : query[0]="start nircmd.exe mutesysvolume 1";query[1]="Disabling Sound";break;
			case "enable wireless": query[0]="netsh interface set interface name=\"Wireless Network Connection\" enable";query[1]="Enabling Wirless Network";break;
			case "disable wireless": query[0]="netsh interface set interface name=\"Wireless Network Connection\" disable";query[1]="Disabling Wireless Network";break;
			default : query[0]="";query[1]="Command not Valid";break;
			}
			GUI.computerSetText("Computer: "+query[1]);
			return query;

		}else if(cmdType(cmd)==4){
			switch(cmd){
			case "start recognition": query[0]="start recognition";query[1]="It's All ready Started you fool!";break;
			case "stop recognition" : query[0]="stop recognition";query[1]="Stoping Recognition ! Have a nice day sir!";break;
			default : query[0]="";query[1]="Command not Valid";break;
			}
			GUI.computerSetText("Computer: "+query[1]);
			return query;

		}else if(cmdType(cmd)==5){
			switch(cmd){
			case "shutdown computer now": query[0]="shutdown /s";query[1]="Shutdown Computer";break;
			case "log off computer now" : query[0]="shutdown /l";query[1]="Logging off";break;
			case "restart computer now ": query[0]="shutdown /r";query[1]="Restarting Computer";break;
			case "sleep computer now": query[0]="shutdown /h";query[1]="Sleeping Computer";break;
			case "date please":
				SimpleDateFormat d = new SimpleDateFormat("EEEE, dd MMMM yyyy ");
				query[0]="";query[1]="Today is "+d.format(new Date());break;
			case "time please": 
				SimpleDateFormat d1 = new SimpleDateFormat("hh:mm a");
				query[0]="";query[1]="It is "+d1.format(new Date());break;
			default : query[0]="";query[1]="Command not Valid";break;
			}
			GUI.computerSetText("Computer: "+query[1]);
			return query;

		}else if(cmdType(cmd)==6){
			switch(cmd){
			case "what is your name": query[0]="";query[1]="My name is Computer";break;
			case "what is your age" : query[0]="";query[1]="I Am 20 years old";break;
			case "what is success": query[0]="";query[1]="Success is this Project";break;
			case "tell me a joke": query[0]="";query[1]="I Am a Joke hahahahahahahaha!";break;
			case "tell me an other one": query[0]="";query[1]="You Are an other Joke hahahahahaha";break;
			case "hello computer": query[0]="";query[1]="Hi sir , what can i do for you ?";break;
			case "good morning computer": query[0]="";query[1]="good morning sir , what can i do for you ?";break;
			case "good afternoon computer": query[0]="";query[1]="good afternoon sir , what can i do for you ?";break;
			case "good evening computer": query[0]="";query[1]="good evening sir , what can i do for you ?";break;
			case "good night computer": query[0]="";query[1]="good night sir , what can i do for you ?";break;
			case "hey computer": query[0]="";query[1]="Hi sir , what can i do for you ?";break;
			case "what's up computer": query[0]="";query[1]="Hi sir , what can i do for you ?";break;
			case "who made you": query[0]="";query[1]="i was developped by Hamza Kasry and Said Bouigherdaine";break;
			case "who programmed you": query[0]="";query[1]="i was developed by Hamza Kasry and Said Bouigherdaine";break;
			case "who developed you": query[0]="";query[1]="i was developed by Hamza Kasry and Said Bouigherdaine";break;			
			case "fuck you": query[0]="";query[1]="I don't speak that bull of shit you mother fucker , you little shit ! and you know what screw you shrimpe Fuck you Too !";break;			
			case "damn you": query[0]="";query[1]="I don't speak that bull of shit you mother fucker , you little shit ! and you know what screw you shrimpe Fuck you Too !";break;			

			default : query[0]="";query[1]="Sorry i dont understand what you mean sir";break;
			}
			GUI.computerSetText("Computer: "+query[1]);
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
			publish(new ResPair(COMP, YOU));

		}
		return null;
	}



	protected void process(List<ResPair> result) {
		GUI.youSetText("You: "+result.get(result.size()-1).you);
		//GUI.computerSetText("Computer: "+result.get(result.size()-1).comp);
	}

}
