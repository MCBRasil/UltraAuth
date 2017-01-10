package ultraauth.cmds;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ultraauth.managers.ChatManager;
import ultraauth.managers.ConfigManager;
import ultraauth.managers.LoginManager;
import ultraauth.managers.PlayerManager;

public class Login implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only Players Can Use This Command!");
			return true;
		}

		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("login")) {
			if (ConfigManager.getInstance().getLocation() == null) {
				p.sendMessage(ChatColor.RED
						+ "You must have a spawn set for ultraauth! Stand at your spawn and use /ultraauth setspawn!");
				return true;
			}
			if (args.length == 0) {
				ChatManager.getInstance().login(p);
				return true;
			}
			if (!PlayerManager.getInstance().isPlayerRegistered(p)) {
				ChatManager.getInstance().register(p);
				return true;
			}
			if (LoginManager.getInstance().isAuthenticated(p)) {
				ChatManager.getInstance().allreadyloggedin(p);
				return true;
			}
			if (!PlayerManager.getInstance().checkPlayerPassword(p, args[0])) {
				ChatManager.getInstance().invalidpassword(p);
				if (ConfigManager.getInstance().getSettings().getBoolean("settings.kickonwrongpassword") == true) {
					p.kickPlayer(ChatColor.RED + "Invalid password!");
				}
				return true;
			}
			LoginManager.getInstance().authenticatedPlayer(p);
			if (p.hasPlayedBefore()) {
				ChatManager.getInstance().loggedin(p);
				p.teleport(PlayerManager.getInstance().getLocation(p));

				if (ConfigManager.getInstance().getSettings().getString("settings.cmd").equals("none")) {
				} else {

					p.performCommand(ConfigManager.getInstance().getSettings().getString("settings.cmd")
							.replace("{player}", p.getName()));

				}

				return true;
			} else {
				ChatManager.getInstance().loggedin(p);
				p.teleport(PlayerManager.getInstance().getLocation(p));

				if (ConfigManager.getInstance().getSettings().getString("settings.cmd").equals("none")) {

				} else {

					p.performCommand(ConfigManager.getInstance().getSettings().getString("settings.cmd")
							.replace("{player}", p.getName()));

				}

				return true;
			}
		}
		return false;
	}
}
