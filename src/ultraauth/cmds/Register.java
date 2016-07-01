package ultraauth.cmds;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ultraauth.managers.ChatManager;
import ultraauth.managers.ConfigManager;
import ultraauth.managers.PlayerManager;

public class Register implements CommandExecutor {
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args)
	{
	  if (!(sender instanceof Player)) {
	    sender.sendMessage(ChatColor.RED + "Only Players Can Use This Command!");
	    return true;
	  }
	  Player p = (Player) sender;
	  if (cmd.getName().equalsIgnoreCase("register")) {
		  if (ConfigManager.getInstance().getLocation() == null) {
			  p.sendMessage(ChatColor.RED + "You must have a spawn set for ultraauth! Stand at your spawn and use /ultraauth setspawn!");
			  return true;
		  }
	        if (args.length == 0) {
	        	p.sendMessage(ChatManager.getInstance().colorMsg(true, "&c/register <password>"));
	        	return true;
	        }
		  if (PlayerManager.getInstance().isPlayerRegistered(p)) {
			  ChatManager.getInstance().allreadyregistered(p);
			  return true;
		  }
		  if (!(args[0].length() > ConfigManager.getInstance().getSettings().getInt("settings.min_len"))) {
			  
			  int i = ConfigManager.getInstance().getSettings().getInt("settings.min_len");
			  
			  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigManager.getInstance().getConfig().getString("msg.min_len_pass").replace("%len%", String.valueOf(i))));
			  
			  return true;
		  }
		  if (!(args[0].length() < ConfigManager.getInstance().getSettings().getInt("settings.max_len"))) {
			  
			  int i = ConfigManager.getInstance().getSettings().getInt("settings.min_len");
			  
			  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigManager.getInstance().getConfig().getString("msg.max_len_pass").replace("%len%", String.valueOf(i))));
			  
			  return true;
		  }
		  PlayerManager.getInstance().setPlayerPassword(p, args[0]);
		  p.sendMessage(ChatManager.getInstance().colorMsg(true, "&aPassword set to: " + args[0]));
		  ChatManager.getInstance().registered(p);
		  return true;
	  }
             return false;
	}
}
