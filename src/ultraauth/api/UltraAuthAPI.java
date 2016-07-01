package ultraauth.api;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import ultraauth.managers.LoginManager;
import ultraauth.managers.PlayerManager;

public class UltraAuthAPI {
	
	private UltraAuthAPI() {
		
	}
	public static boolean isAuthenticated(Player p) {
		return LoginManager.getInstance().isAuthenticated(p);
	}
	public static boolean isRegisterd(Player p) {
		return PlayerManager.getInstance().isPlayerRegistered(p);
	}
	public static void authenticatedPlayer(Player p) {
		LoginManager.getInstance().authenticatedPlayer(p);
	}
	public static void unauthenticatedPlayer(Player p) {
		LoginManager.getInstance().unAuthenticatePlayer(p);
	}
	public static void setPlayerPasswordOnline(Player p, String password) {
		PlayerManager.getInstance().setPlayerPassword(p, password);
	}
	public static void setPlayerPasswordOffline(OfflinePlayer p, String password) {
		PlayerManager.getInstance().setPlayerPasswordOffline(p, password);
	}
	public static void resetPlayerPasswordOnline(Player p) {
		PlayerManager.getInstance().resetPlayerPassword(p);
	}
	public static void resetPlayerPasswordOffline(OfflinePlayer p) {
		PlayerManager.getInstance().resetPlayerPasswordOffline(p);
	}
	public static void setupPlayer(Player p) {
		PlayerManager.getInstance().setupPlayer(p);
	}
}
