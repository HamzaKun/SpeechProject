package ma.ensa;

public interface CommanderDAO {
	public String Respond(String a, String b);
	public String getCommand(String arg);
	public boolean isCommand(String arfg);
	public void run();
	public void getHypothesis(String arg);

}
