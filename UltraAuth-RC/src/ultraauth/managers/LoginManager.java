package ultraauth.managers;

import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import ultraauth.utils.SaveInventory;

public class LoginManager {

	private LoginManager() {

	}

	static LoginManager instance = new LoginManager();

	public static LoginManager getInstance() {
		return instance;
	}

	ArrayList<Player> authenticated = new ArrayList<Player>();
	SaveInventory si = new SaveInventory();

	public boolean isAuthenticated(Player p) {
		return this.authenticated.contains(p);
	}

	public void authenticatedPlayer(Player p) {
		this.authenticated.add(p);
		System.out.println(p.getName() + " Has been authenticated!");
		SaveInventory.getInstance().loadInventory(p);
		p.removePotionEffect(PotionEffectType.SLOW);
		p.removePotionEffect(PotionEffectType.BLINDNESS);
		p.removePotionEffect(PotionEffectType.JUMP);
	}

	public void unAuthenticatePlayer(Player p) {
		p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000, 10000000));
		p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1000000, 10000000));
		p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1000000, 10000000));
		this.authenticated.remove(p);
		System.out.println(p.getName() + " Has been un-authenticated!");
		try {
			si.saveInventory(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
