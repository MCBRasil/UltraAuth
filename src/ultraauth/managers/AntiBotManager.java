package ultraauth.managers;

import java.util.ArrayList;




public class AntiBotManager {
	

	private AntiBotManager() {

	}

	static AntiBotManager instance = new AntiBotManager();

	public static AntiBotManager getInstance() {
		return instance;
	}
	ArrayList<String> listed = new ArrayList<String>();
	
	public void add(String p) {
		listed.add(p);
	}
	public void remove(String p) {
		listed.remove(p);
	}
	public boolean check(String p) {
		return listed.contains(p);
	}
}
