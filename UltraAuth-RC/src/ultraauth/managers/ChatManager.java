package ultraauth.managers;


import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class ChatManager {
	

	private ChatManager() {

	}

	static ChatManager instance = new ChatManager();

	public static ChatManager getInstance() {
		return instance;
	}
	String prefix = ConfigManager.getInstance().getConfig().getString("Prefix");
	ConfigManager cfg = ConfigManager.getInstance();
	
	public String getPrefix() {
		return prefix;
	}
	public String colorMsg(boolean prefix, String msg) {
		if (prefix == true) {
		return ChatColor.translateAlternateColorCodes('&', this.getPrefix() + msg);
		} else if (prefix == false) {
		return ChatColor.translateAlternateColorCodes('&', msg);
		}
		return msg;
	}
	public void login(Player p) {
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + cfg.getConfig().getString("msg.login")));
	}
	public void register(Player p) {
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + cfg.getConfig().getString("msg.register")));
	}
	public void loggedin(Player p) {
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + cfg.getConfig().getString("msg.loggedin")));
	}
	public void registered(Player p) {
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + cfg.getConfig().getString("msg.registered")));
	}
	public void playerNotFound(CommandSender p) {
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + cfg.getConfig().getString("msg.playernotfound")));
	}
	public void invalidpassword(Player p) {
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + cfg.getConfig().getString("msg.invalidpassword")));
	}
	public void updatepasswords_admin(CommandSender p) {
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + cfg.getConfig().getString("msg.updatepasswords_admin")));
	}
	public void updatepasswords_user(Player p) {
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + cfg.getConfig().getString("msg.updatepasswords_user")));
	}
	public void allreadyregistered(Player p) {
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + cfg.getConfig().getString("msg.allreadyregistered")));
	}
	public void allreadyloggedin(Player p) {
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + cfg.getConfig().getString("msg.allreadyloginin")));
	}
	public String updatepasswords_string() {
		return ChatColor.translateAlternateColorCodes('&', prefix + cfg.getConfig().getString("msg.updatepasswords_user"));
	}
	public void helpMessage(CommandSender p) {
		p.sendMessage(ChatColor.GREEN
				+ "/ultraauth setspawn - Set Spawn For When Player Joins");
		p.sendMessage(ChatColor.GREEN
				+ "/ultraauth reset <player> - Reset A Players Password");
		p.sendMessage(ChatColor.GREEN
				+ "/ultraauth set <player> <password> - Set Passworld for Player.");
	}
}