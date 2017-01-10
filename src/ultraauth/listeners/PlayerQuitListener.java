package ultraauth.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import ultraauth.managers.LoginManager;
import ultraauth.managers.PlayerManager;

public class PlayerQuitListener implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		
		if (LoginManager.getInstance().isAuthenticated(p) == true) {
			LoginManager.getInstance().unAuthenticatePlayer(p);
			PlayerManager.getInstance().setLocation(p);
			
		} else {
						
		}
	}

}
