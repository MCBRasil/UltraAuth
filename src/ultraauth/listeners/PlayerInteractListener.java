package ultraauth.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import ultraauth.managers.LoginManager;

public class PlayerInteractListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onBreak(PlayerInteractEvent e) {
		Player p = (Player) e.getPlayer();
				if (!LoginManager.getInstance().isAuthenticated(p)) {
					e.setCancelled(true);
				}
	}

}
