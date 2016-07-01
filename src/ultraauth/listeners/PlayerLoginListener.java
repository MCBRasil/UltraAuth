package ultraauth.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import ultraauth.managers.AntiBotManager;
import ultraauth.managers.ConfigManager;

public class PlayerLoginListener implements Listener {
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent e) {
		Player p = e.getPlayer();
		
		String ip = e.getAddress().getHostAddress();
		
		for (Player online : Bukkit.getServer().getOnlinePlayers()) {
			if (p.getName().equals(online) == true) {
				e.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "This player is online!");
			}
		}
		if (ConfigManager.getInstance().getSettings().getBoolean("settings.antibot") == true) {
		if (AntiBotManager.getInstance().check(ip) == false) {
			e.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "Please add the server in your server list! Please make sure the Server MOTD shows up before trying to login again!");
			return;
		}
		}
		
	}
}

