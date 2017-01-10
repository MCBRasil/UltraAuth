package ultraauth.cmds;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ultraauth.managers.ChatManager;
import ultraauth.managers.ConfigManager;
import ultraauth.managers.PlayerManager;

public class UltraAuth implements CommandExecutor {
	
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args)
	{
		CommandSender p = sender;
	  if (cmd.getName().equalsIgnoreCase("ultraauth")) {
		  if (!sender.hasPermission("ultraauth.ultraauth")) {
			  sender.sendMessage(ChatColor.RED + "No Permissions");
			  return true;
		  }
		  if (args.length == 0) {
			  ChatManager.getInstance().helpMessage(p);
			  return true;
		  }
		  if (args.length == 1) {
				  if (args[0].equalsIgnoreCase("setspawn")) {
					  if (sender instanceof Player) {
						  Player player = (Player) sender;
				  ConfigManager.getInstance().setLocation(player);
				  p.sendMessage(ChatManager.getInstance().colorMsg(true, "&cSpawn has been set!"));
				  
				  return true;
			  } else {
				  sender.sendMessage(ChatColor.RED + "You must be a player!"); return true;
			    }
			  } else
			  if (args[0].equalsIgnoreCase("reset")) {
				  ChatManager.getInstance().helpMessage(p);
				  return true;
			  } else
			   if (args[0].equalsIgnoreCase("set")) {
					  ChatManager.getInstance().helpMessage(p);
				   
					  return true;
			  } else {
				  return true;
			  }
		  }
		  if (args.length == 2) {
			  if (args[0].equalsIgnoreCase("reset")) {
				  Player target_online = Bukkit.getServer().getPlayer(args[1]);
				  OfflinePlayer target_offline;
				  
				  if (target_online == null) {
					  target_offline = Bukkit.getServer().getOfflinePlayer(args[1]);
					  
					  try {
						
						  PlayerManager.getInstance().resetPlayerPasswordOffline(target_offline);
						  ChatManager.getInstance().updatepasswords_admin(p);
						  
					} catch (Exception e) {
						ChatManager.getInstance().playerNotFound(p);
						return true;
					}		  
					  return true;
				  } else {
					
					  PlayerManager.getInstance().resetPlayerPassword(target_online);
					  ChatManager.getInstance().updatepasswords_admin(p);
					  target_online.kickPlayer(ChatColor.translateAlternateColorCodes('&', ChatManager.getInstance().updatepasswords_string()));
					  
					  
					  return true;
				  }
				  } else 
			   if (args[0].equalsIgnoreCase("set")) {
					  ChatManager.getInstance().helpMessage(p);
				   
					  return true;
					  
			  } else 
				  return true;
			   
				  }
		  if (args.length == 3) {
			  if (args[0].equalsIgnoreCase("set")) {
				  Player target_online = Bukkit.getServer().getPlayer(args[1]);
				  OfflinePlayer target_offline;
				  
				  if (target_online == null) {
					  target_offline = Bukkit.getServer().getOfflinePlayer(args[1]);
					  
					  try {
						  PlayerManager.getInstance().setPlayerPasswordOffline(target_offline, args[2]);
						  ChatManager.getInstance().updatepasswords_admin(p);
						  return true;
					} catch (Exception e) {
						ChatManager.getInstance().playerNotFound(p);
						return true;
					}
				  } else {
					
					  PlayerManager.getInstance().setPlayerPassword(target_online, args[2]);
					  ChatManager.getInstance().updatepasswords_admin(p);
					  target_online.kickPlayer(ChatColor.translateAlternateColorCodes('&', ChatManager.getInstance().updatepasswords_string()));
					  
					  
					  return true;
				  }
			  } else {
				  return true;
			  }
		  }
		  }
	  
	return false;
	}
}