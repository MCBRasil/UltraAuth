package ultraauth.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import ultraauth.cmds.Login;
import ultraauth.cmds.Register;
import ultraauth.cmds.ResetPassword;
import ultraauth.cmds.UltraAuth;
import ultraauth.listeners.EntityDamageByEntityListener;
import ultraauth.listeners.InventoryClickListener;
import ultraauth.listeners.PlayerBlockBreakListener;
import ultraauth.listeners.PlayerChatListener;
import ultraauth.listeners.PlayerCommandListener;
import ultraauth.listeners.PlayerInteractListener;
import ultraauth.listeners.PlayerJoinListener;
import ultraauth.listeners.PlayerLoginListener;
import ultraauth.listeners.PlayerQuitListener;
import ultraauth.listeners.PlayerReSpawnListener;
import ultraauth.listeners.ServerListPingListener;
import ultraauth.main.update.UpdateChecker;
import ultraauth.managers.ChatManager;
import ultraauth.managers.ConfigManager;
import ultraauth.managers.LoginManager;
import ultraauth.managers.PlayerManager;

public class Main extends JavaPlugin {

	public static Plugin main;
	public static Main m;
	protected UpdateChecker updateChecker;
	PluginManager pm = this.getServer().getPluginManager();

	public void onEnable() {
		load();
	}

	public void onDisable() {
		main = null;
		m = null;
	}

	private void load() {
		main = this;
		m = this;
		PlayerManager.getInstance().setup(this);
		ConfigManager.getInstance().setupConfig(this);
		this.loadListeners();
		this.registerCommands();
		this.loginMsg();
		this.Updater();
		for (Player online : this.getServer().getOnlinePlayers()) {
			this.logoutAll(online);
			this.setupPlayers(online);
		}
	}

	public void loadListeners() {
		this.pm.registerEvents(new EntityDamageByEntityListener(), this);
		this.pm.registerEvents(new InventoryClickListener(), this);
		this.pm.registerEvents(new PlayerBlockBreakListener(), this);
		this.pm.registerEvents(new PlayerChatListener(), this);
		this.pm.registerEvents(new PlayerCommandListener(), this);
		this.pm.registerEvents(new PlayerInteractListener(), this);
		this.pm.registerEvents(new PlayerReSpawnListener(), this);
		this.pm.registerEvents(new PlayerQuitListener(), this);
		this.pm.registerEvents(new PlayerJoinListener(), this);
		this.pm.registerEvents(new PlayerLoginListener(), this);
		this.pm.registerEvents(new ServerListPingListener(), this);

	}

	public void registerCommands() {
		this.getCommand("login").setExecutor(new Login());
		this.getCommand("register").setExecutor(new Register());
		this.getCommand("resetpassword").setExecutor(new ResetPassword());
		this.getCommand("UltraAuth").setExecutor(new UltraAuth());
	}

	public static Plugin getPlugin() {
		return main;
	}

	public static Main getInstance() {
		return m;
	}

	public void setupPlayers(Player p) {
		PlayerManager.getInstance().setupPlayer(p);
	}

	public void logoutAll(Player p) {
		LoginManager.getInstance().unAuthenticatePlayer(p);

		p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000, 10000000));
		p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1000000, 10000000));
		p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1000000, 10000000));

		if (!PlayerManager.getInstance().getPlayerConfig(p).contains("location")) {
			PlayerManager.getInstance().setLocation(p);
		}

	}

	private void check() {
		if (ConfigManager.getInstance().getLocation() == null) {
			System.out.println(ChatColor.RED
					+ "You must have a spawn set for ultraauth! Stand at your spawn and use /ultraauth setspawn!");
			return;
		}
		for (Player online : this.getServer().getOnlinePlayers()) {
			if (!PlayerManager.getInstance().isPlayerRegistered(online)) {
				ChatManager.getInstance().register(online);
			} else if (!LoginManager.getInstance().isAuthenticated(online))
				ChatManager.getInstance().login(online);
		}

	}

	private void loginMsg() {
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				check();
			}
		}, 10, 200);
	}
	private void Updater() {
		this.updateChecker = new UpdateChecker(this,
				"http://dev.bukkit.org/bukkit-plugins/ultraauth-aa/files.rss");
		if (updateChecker.updateNeeded()) {
			this.getLogger().warning(
					"[[UltraAuth UPDATER]] Version "
							+ updateChecker.getVersion()
							+ " has been released! Download it here: "
							+ updateChecker.getLink());
			Bukkit.broadcastMessage("[UltraAuth UPDATER] Version "
					+ updateChecker.getVersion()
					+ " has been released! Download it here: "
					+ updateChecker.getLink());
		}
	}

}
