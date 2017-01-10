package ultraauth.cmds;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ultraauth.managers.ChatManager;
import ultraauth.managers.PlayerManager;

public class ResetPassword implements CommandExecutor {
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args)
	{
	  if (!(sender instanceof Player)) {
	    sender.sendMessage(ChatColor.RED + "Only Players Can Use This Command!");
	    return true;
	  }
	  Player p = (Player) sender;
	  if (cmd.getName().equalsIgnoreCase("resetpassword")) {
		  if (sender.hasPermission("ultraauth.resetpassword") == false) {
			  sender.sendMessage(ChatColor.RED + "No Permissions");
			  return true;
		  }
		  if (args.length == 0) {
			  p.sendMessage(ChatManager.getInstance().colorMsg(true, "&c/resetpassword <oldpassword> <newpassword>"));
		 return true;
		  }
		  if (args.length == 1) {
		  if (!PlayerManager.getInstance().checkPlayerPassword(p, args[0])) {
			  ChatManager.getInstance().invalidpassword(p);
			  return true;
		  } else p.sendMessage(ChatManager.getInstance().colorMsg(true, "&c/resetpassword <oldpassword> <newpassword>"));
		  return true;
		  
		  }
		  if (args.length == 2) {
		  PlayerManager.getInstance().setPlayerPassword(p, args[1]);
		 p.kickPlayer(ChatManager.getInstance().updatepasswords_string());
		  return true;
		  } else p.sendMessage(ChatManager.getInstance().colorMsg(true, "&c/resetpassword <oldpassword> <newpassword>"));
		  return true;
	  }
	return false;
	}

}
