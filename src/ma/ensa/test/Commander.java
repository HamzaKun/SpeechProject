package ma.ensa.test;

public class Commander {
	private Runtime runTime; 
	private Process process ;
	private String command;
	
	void run(){
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
