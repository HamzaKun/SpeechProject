package ma.ensa;

public class Commander {
	private static Runtime runTime; 
	private static Process process ;
	private static String command;

	static String  Respond(String resultText, String voice){
		Synthesis s ; 
//		s= new Synthesis("You said: " +resultText, voice);
		//System.out.println("You said: " +resultText);
		String response = new String();
		s= new Synthesis("From Commander.Respond", voice);
		s.SayIt();
		/*if(isCommand(resultText)){
			try{
				Process exec = new ProcessBuilder("CMD", "/C", getCommand(resultText)).start();
			}catch(Exception e){
				System.out.println("Problem while trying to run the command");
				e.printStackTrace();
			}
		}else {
			System.out.println("I can't hear what you said.\n");
			s= new Synthesis("I can't hear what you said", voice);
			s.SayIt();
			
		}*/
		return ("From Commander.Respond");
	}
		/**
		 * A function that checks if the result of the recognition is a command
		 * as open, close or some others
		 * @param arg
		 * @return
		 */
		public static boolean isCommand(String arg){
			String[] parts = arg.split(" ");
			if(parts[0].equals("Open") || parts[0].equals("open")
					|| parts[0].equals("close")){
				return true;
			}
			else
				return false;
			
		}

		/**
		 * Used to get the command
		 * the String that will be added to the ProcessBuilder
		 * @param arg
		 * @return
		 */
		public static String getCommand(String arg){
			String cmd = new String();
			return cmd;
		}


		static void run(String cmdtype){
			String query;
			if(cmdtype.equals("open")){
				switch(command){
				case "notepad": query="start notepad";break;
				case "chrome" : query="start chrome";break;
				case "firefox": query="start firefox";break;
				case "powerpoint": query="start powerpnt";break;
				case "word": query="start winword";break;
				case "excel": query="start excel";break;

				case "foxitreader": query="start FoxitReader";break;
				case "winrar": query="start winrar";break;
				case "skype": query="start skype";break;
				case "photshop": query="start photoshop";break;
				case "windowsmediaplayer": query="start wmplayer";break;
				case "vmware": query="start vmware";break;
				case "internetexplorer": query="start iexplore";break;
				case "terminal": query="start";break;
				
				case "browser": query="https:";break;
				case "controlpanel": query="control panel";break;
				case "desktop": query="start desktop";break;
				case "computer": query="explorer /root,";break;
				case "MyDocuments": query="start documents";break;
				case "MyVideos": query="start Videos";break;
				case "MyPictures": query="start Pictures";break;
				case "Recyclebin": query="start shell:RecycleBinFolder";break;
				case "calculator": query="start calc";break;
				case "Keybord" : query="start osk";break;
				
				case "facebook" : query="start https:/www.facebook.com ";break;
				case "twitter" : query="start https:/www.twitter.com ";break;
				case "google" : query="start https:/www.google.com ";break;
				case "ensamarrakech" : query="start https:/www.ensa.ac.ma ";break;
				case "gmail" : query="start https:/www.gmail.com ";break;
				case "stackoverflow" : query="start https:/www.stackoverflow.com ";break;
				case "github" : query="start https:/www.github.com ";break;
				case "openclassrooms" : query="start https:/www.openclassrooms.com ";break;
				default :query="commande introuvable";
				}
			}
		}

		void getHypotisis (String cmd){
			String [] parts = cmd.split(" ");

			if ( parts[0].equalsIgnoreCase("open")){
				for (int i=1 ;i< cmd.length() ; i++){
					command+=parts[i];
				}
				run("open");
			}else {
				for (int i=0 ;i< cmd.length() ; i++){
					command+=parts[i];
				}
				run("open");
			}
			
		}

	}
