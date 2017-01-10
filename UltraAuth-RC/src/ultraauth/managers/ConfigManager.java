package ultraauth.managers;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ConfigManager {
	
	private ConfigManager() {
		
	}
	
	static ConfigManager instance = new ConfigManager();
	
	public static ConfigManager getInstance() {
		return instance;
	}
	
	Plugin p;
	
    File config = new File("plugins/UltraAuth/","config.yml");
	FileConfiguration configfile = YamlConfiguration.loadConfiguration(config);
	
    File settings = new File("plugins/UltraAuth/","settings.yml");
	FileConfiguration settingsfile = YamlConfiguration.loadConfiguration(settings);
	

	public void setupConfig(Plugin p) {
		this.p = p;
		
		try {
		
			this.saveConfig();
			
			getConfig().addDefault("Prefix", "&b&lUltraAuth&8>&r ");
			getConfig().addDefault("msg.login", "&aPlease use /login <password>");
			getConfig().addDefault("msg.register", "&aPlease use /register <register>");
			getConfig().addDefault("msg.registered", "&aYou have been registered!");
			getConfig().addDefault("msg.loggedin", "&aYou have been logged in!");
			getConfig().addDefault("msg.playernotfound", "&cPlease has not been found!");
			getConfig().addDefault("msg.invalidpassword", "&cYour password is invalid!");
			getConfig().addDefault("msg.updatepasswords_admin", "&aPlayers Password Updated!");
			getConfig().addDefault("msg.updatepasswords_user", "&aYour password has been updated! Please rejoin!");
			getConfig().addDefault("msg.allreadyregistered", "&aYou are all ready registered!");
			getConfig().addDefault("msg.allreadyloginin", "&aYou are all ready loggedin!");
			getConfig().addDefault("msg.min_len_pass", "&cYour password should be atleast over %len% characters!");
			getConfig().addDefault("msg.max_len_pass", "&cYour password shouldn't be over %len% characters!");
			getConfig().options().copyDefaults(true);
			
			this.saveConfig();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		try {
			
			this.saveSettings();
			
			getSettings().options().header("ANTIBOT IS IN BETA. USE AT YOUR OWN RISK. PLEASE REPORT ANY #BUGS. MAX ACCOUNTS IS NOT DONE. IT WILL NOT WORK. THANK YOU");
			
			getSettings().addDefault("settings.antibot", true);
			getSettings().addDefault("settings.maxaccounts", false);
			getSettings().addDefault("settings.maxaccounts.amount", 2);
			getSettings().addDefault("settings.kickonwrongpassword", true);
			getSettings().addDefault("settings.cmd", "none");
			getSettings().addDefault("settings.min_len", 4);
			getSettings().addDefault("settings.max_len", 10);
			
			getSettings().options().copyDefaults(true);
			
			saveSettings();
			
			
			
			
		} catch (Exception e) {
		e.printStackTrace();	
		}
		
		
	}
	
	
	public FileConfiguration getSettings() {
		return settingsfile;
	}
	public FileConfiguration getConfig() {
		return configfile;
	}
	public void saveConfig() {
		try {
			configfile.save(config);
		} catch (Exception e) {
		}
	}
	public void saveSettings() {
		try {
			settingsfile.save(settings);
		} catch (Exception e) {
		}
	}
	public void setLocation(Player p) {
	
		
		this.getConfig().set("location.world", p.getWorld().getName());
		this.getConfig().set("location.x", Double.valueOf(p.getLocation().getX()));
		this.getConfig().set("location.y", Double.valueOf(p.getLocation().getY()));
		this.getConfig().set("location.z", Double.valueOf(p.getLocation().getZ()));
		this.getConfig().set("location.pitch", Float.valueOf(p.getLocation().getPitch()));
		this.getConfig().set("location.yaw", Float.valueOf(p.getLocation().getYaw()));
		
			this.saveConfig();
	}
	public Location getLocation() {
		
		if (!(this.getConfig().getString("location") == null)) {
		
		World w = Bukkit.getWorld(this.getConfig().getString("location.world"));
		Double x = Double.valueOf(this.getConfig()
				.getDouble("location.x"));
		Double y = Double.valueOf(this.getConfig()
				.getDouble("location.y"));
		Double z = Double.valueOf(this.getConfig()
				.getDouble("location.z"));
		Float pitch = Float.valueOf((float) this.getConfig()
				.getDouble("location.pitch"));
		Float yaw = Float.valueOf((float) this.getConfig()
				.getDouble("location.yaw"));
		Location location = new Location(w, x.doubleValue(), y.doubleValue(),
				z.doubleValue());
		location.setPitch(pitch.floatValue());
		location.setYaw(yaw.floatValue());
		return location;
		} else
			return null;
	}
}