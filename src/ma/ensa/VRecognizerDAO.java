package ma.ensa;

public interface VRecognizerDAO {
	public String StartRec();
	public String Respond(String res, String voice);
	public String filter(String cmd);
	public int cmdType(String arg);
	public String[] getCommand(String cmd);

}
