package ultraauth.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import ultraauth.main.Main;
import ultraauth.managers.ChatManager;
import ultraauth.managers.ConfigManager;
import ultraauth.managers.LoginManager;

public class PlayerReSpawnListener implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onReSpawn(PlayerRespawnEvent e) {
		final Player p = e.getPlayer();
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            public void run() {
          	  if (!LoginManager.getInstance().isAuthenticated(p)) {
        		  
        		 ChatManager.getInstance().login(p);
        			p.setFlying(false);
        			p.setGameMode(GameMode.SURVIVAL);
        			p.teleport(ConfigManager.getInstance().getLocation());
        			p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000, 10000000));	
        			p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1000000, 10000000));
        			p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1000000, 10000000));
              }
        }
        }, 20);
        }
	}
