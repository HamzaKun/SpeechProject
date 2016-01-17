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


		static void run(){
			String query;
			switch(command){
			case "home": query="$HOME :3" ;
			}
		}

		void getHypotisis (String cmd){
			String [] parts = cmd.split(" ");

			if ( parts[0].equalsIgnoreCase("open")){
				for (int i=1 ;i< cmd.length() ; i++){
					command+=parts[i];
				}
				run();
			}
		}

	}
