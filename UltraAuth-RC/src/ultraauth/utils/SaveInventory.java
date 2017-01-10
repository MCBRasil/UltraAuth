package ultraauth.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;

import ultraauth.main.Main;

public class SaveInventory {

	public SaveInventory() {

	}

	static SaveInventory instance = new SaveInventory();

	public static SaveInventory getInstance() {
		return instance;
	}

	Plugin plugin = Main.getPlugin();

	public void saveInventory(Player p) throws IOException {

		YamlConfiguration c = new YamlConfiguration();
		c.set("inventory", p.getInventory().getContents());
		c.set("armor", p.getInventory().getArmorContents());
		c.set("potions", p.getActivePotionEffects());
		c.save(new File(plugin.getDataFolder() + File.separator + "players", p.getName() + ".yml"));
		p.getInventory().clear();
		p.getInventory().setHelmet(new ItemStack(Material.AIR));
		p.getInventory().setChestplate(new ItemStack(Material.AIR));
		p.getInventory().setLeggings(new ItemStack(Material.AIR));
		p.getInventory().setBoots(new ItemStack(Material.AIR));
	}

	@SuppressWarnings("unchecked")
	public void loadInventory(Player p) {
		p.getInventory().clear();
		p.getInventory().setHelmet(new ItemStack(Material.AIR));
		p.getInventory().setChestplate(new ItemStack(Material.AIR));
		p.getInventory().setLeggings(new ItemStack(Material.AIR));
		p.getInventory().setBoots(new ItemStack(Material.AIR));
		for (PotionEffect pf : p.getActivePotionEffects()) {
			p.removePotionEffect(pf.getType());
		}
		
		if ((new File(plugin.getDataFolder() + "players", p.getName() + ".yml").exists())) {
		
		YamlConfiguration c = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "players", p.getName() + ".yml"));
        ItemStack[] content = ((List<ItemStack>) c.get("inventory")).toArray(new ItemStack[0]);
        ItemStack[] armor = ((List<ItemStack>) c.get("armor")).toArray(new ItemStack[0]);
        p.getInventory().setContents(content);
        p.getInventory().setArmorContents(armor);
        p.getPlayer().updateInventory();
        removeInventory(p);
		}
	}

	public void removeInventory(Player p) {
		File df = new File(plugin.getDataFolder() + File.separator + "players");
		File[] files = df.listFiles();
		for (File kitFile : files) {
			if (kitFile.getName().replace(".yml", "").equals(p.getName())) {
				kitFile.delete();
			}
		}
	}

	public boolean isInventorySaved(Player p) {
		YamlConfiguration c = YamlConfiguration
				.loadConfiguration(new File(plugin.getDataFolder() + File.separator + "players", p.getName() + ".yml"));
		if (c.get("inventory") == null) {
			return false;
		} else {
			return true;
		}
	}

}