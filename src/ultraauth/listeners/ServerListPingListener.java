package ultraauth.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import ultraauth.managers.AntiBotManager;

public class ServerListPingListener implements Listener {
	
	@EventHandler
	public void onServerListPing(ServerListPingEvent e) {
		String ip = e.getAddress().getHostAddress();
	    AntiBotManager.getInstance().add(ip);
	}

}
