package ultraauth.listeners;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import ultraauth.main.Main;
import ultraauth.main.update.UpdateChecker;
import ultraauth.managers.ChatManager;
import ultraauth.managers.ConfigManager;
import ultraauth.managers.PlayerManager;
import ultraauth.utils.SaveInventory;

public class PlayerJoinListener implements Listener {

	protected UpdateChecker updateChecker = new UpdateChecker(Main.getInstance(),
			"http://dev.bukkit.org/bukkit-plugins/ultraauth-aa/files.rss");

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerJoin(final PlayerJoinEvent e) {
		final Player p = e.getPlayer();

		if (p.isOp()) {

			if (updateChecker.updateNeeded()) {
				p.sendMessage("[UltraAuth UPDATER] Version " + updateChecker.getVersion()
						+ " has been released! Download it here: " + updateChecker.getLink());

			}
		}

		final SaveInventory si = new SaveInventory();

		p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000, 10000000));
		p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1000000, 10000000));
		p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1000000, 10000000));

		new BukkitRunnable() {

			@Override
			public void run() {

				if (!si.isInventorySaved(p)) {
					try {
						si.saveInventory(p);
					} catch (IOException err) {
						err.printStackTrace();
					}
				}

			}

		}.runTaskLater(Main.getPlugin(), 20 * 1);

		PlayerManager.getInstance().setupPlayer(p);
		if (ConfigManager.getInstance().getLocation() == null) {
			p.sendMessage(ChatColor.RED
					+ "You must have a spawn set for ultraauth! Stand at your spawn and use /ultraauth setspawn!");
			return;
		}
		if (PlayerManager.getInstance().isPlayerRegistered(p)) {
			ChatManager.getInstance().login(p);
		}
		if (!PlayerManager.getInstance().getPlayerConfig(p).contains("location")) {
			PlayerManager.getInstance().setLocation(p);
		}
		p.teleport(ConfigManager.getInstance().getLocation());
	}
}
