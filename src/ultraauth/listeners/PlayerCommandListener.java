package ultraauth.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import ultraauth.managers.ChatManager;
import ultraauth.managers.ConfigManager;
import ultraauth.managers.LoginManager;
import ultraauth.managers.PlayerManager;

public class PlayerCommandListener implements Listener {
	
	@EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
    public void onCommandProcess(PlayerCommandPreprocessEvent e)
    {
    	Player p = e.getPlayer();
		  if (ConfigManager.getInstance().getLocation() == null) {
			  p.sendMessage(ChatColor.RED + "You must have a spawn set for ultraauth! Stand at your spawn and use /ultraauth setspawn!");
			  return;
		  }
      String cmd = e.getMessage().toLowerCase();
      if (!LoginManager.getInstance().isAuthenticated(e.getPlayer())) {
       if ((cmd.startsWith("/login ") ||  (cmd.equals("/login") ||
    		   (cmd.startsWith("/l ") ||  (cmd.equals("/l") ||
    		   (cmd.equals("/register") ||
        (cmd.startsWith("/register ")))))))) {
    	   return;
       }
        e.setCancelled(true);
			if (PlayerManager.getInstance().isPlayerRegistered(p)) {
				ChatManager.getInstance().login(p);
			} else 
				ChatManager.getInstance().register(p);
      } else {
        e.setCancelled(false);
        return;
      }
      }
    
}				