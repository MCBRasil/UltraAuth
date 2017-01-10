package ultraauth.managers;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import ultraauth.encrypt.BCrypt;

public class PlayerManager 
{
	private PlayerManager() {

	}

	static PlayerManager instance = new PlayerManager();

	public static PlayerManager getInstance() {
		return instance;
	}
	
	Plugin plugin;
	
	String dir_passwords = "plugins/UltraAuth/data/passwords/"; 
	
	public void setup(Plugin p) {
		this.plugin = p;
	}
	public void setupPlayer(Player p) {
		File playerfile = new File(dir_passwords + p.getName() + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(playerfile);
		
		try {
			config.save(playerfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!new File(dir_passwords).exists())
			new File(dir_passwords).mkdir();
	}
	public void savePlayerConfigOffline(OfflinePlayer p) {
		File playerfile = new File(dir_passwords + p.getName() + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(playerfile); 
		try {
			config.save(playerfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public FileConfiguration getPlayerConfig(Player p) {
		File playerfile = new File(dir_passwords + p.getName() + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(playerfile);
		return config;
		
	}
	public String getPlayerPasswordRaw(Player p) {
		File playerfile = new File(dir_passwords + p.getName() + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(playerfile);
		return config.getString("password");
	}
	public void setPlayerPassword(Player p, String password) {
		File playerfile = new File(dir_passwords + p.getName() + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(playerfile); 
		
		String ps = BCrypt.hashpw(password, BCrypt.gensalt(12));
		
		config.set("password", ps);
		try {
			config.save(playerfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void resetPlayerPasswordOffline(OfflinePlayer p) {
		File playerfile = new File(dir_passwords + p.getName() + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(playerfile); 
		
		
		config.set("password",  " ");
		savePlayerConfigOffline(p);
		
	}
	public void setPlayerPasswordOffline(OfflinePlayer p, String password) {
		File playerfile = new File(dir_passwords + p.getName() + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(playerfile); 
		
		String ps = BCrypt.hashpw(password, BCrypt.gensalt(12));
		config.set("password", ps);
		
	}
	public void resetPlayerPassword(Player p) {
		File playerfile = new File(dir_passwords + p.getName() + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(playerfile); 
		
		config.set("password", null);
		try {
			config.save(playerfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public boolean checkPlayerPassword(Player p, String password)  {
		if (BCrypt.checkpw(password, getPlayerPasswordRaw(p))) {
			
			return true;
		} else { 
		 return false;
		}
	}
	public boolean isPlayerRegistered(Player p) {
		if (getPlayerPasswordRaw(p) == null) {
			return false;
		} else {
		return true;
		}
	}
	public void setLocation(Player p) {
		
		File cfile = new File("data/ultraauth/players/password/"
				+ p.getName() + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(cfile);
		
		config.set("location.world", p.getWorld().getName());
		config.set("location.x", Double.valueOf(p.getLocation().getX()));
		config.set("location.y", Double.valueOf(p.getLocation().getY()));
		config.set("location.z", Double.valueOf(p.getLocation().getZ()));
		config.set("location.pitch", Float.valueOf(p.getLocation().getPitch()));
		config.set("location.yaw", Float.valueOf(p.getLocation().getYaw()));
		try {
			config.save(cfile);
		} catch (IOException e) {
			System.out.println("Error Could Not Save Config...");
			e.printStackTrace();
		}
		try {
			config.save(cfile);
		} catch (IOException e) {
			System.out.println("Error Could Not Save Config...");
			e.printStackTrace();
		}
	}

	public Location getLocation(Player p) {
		
		
		File cfile = new File("data/ultraauth/players/password/"
				+ p.getName() + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(cfile);
		
		if (config.contains("location")) {
		World w = Bukkit.getWorld(config
				.getString("location.world"));
		Double x = Double.valueOf(config
				.getDouble("location.x"));
		Double y = Double.valueOf(config
				.getDouble("location.y"));
		Double z = Double.valueOf(config
				.getDouble("location.z"));
		Float pitch = Float.valueOf((float) config
				.getDouble("location.pitch"));
		Float yaw = Float.valueOf((float) config
				.getDouble("location.yaw"));
		Location location = new Location(w, x.doubleValue(), y.doubleValue(),
				z.doubleValue());
		location.setPitch(pitch.floatValue());
		location.setYaw(yaw.floatValue());
		return location;
		} else
			return ConfigManager.getInstance().getLocation();
	}
}
