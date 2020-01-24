package me.kcin.kcin;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

public class Mainkcin extends JavaPlugin implements Listener {

	private Commands commands = new Commands(this);

	public void onEnable() {
		final FileConfiguration config = this.getConfig();

		config.options().copyDefaults(true);
		saveConfig();

		getCommand(commands.cmd1).setExecutor(commands);
		getServer().getConsoleSender()
				.sendMessage(ChatColor.DARK_GREEN + "Snap Patches: " + ChatColor.GREEN + "Enabled");
		getServer().getPluginManager().registerEvents(this, this);

	}

	public void onDisable() {
		getServer().getConsoleSender().sendMessage(ChatColor.DARK_RED + "Snap Patches: " + ChatColor.RED + "Disabled");
	}

	@EventHandler
	public void onChangeWorld(PlayerChangedWorldEvent event) {
		Player player = event.getPlayer();
		boolean isResistRework = this.getConfig().getBoolean("resistance-rework-enabled");
		String savedSpawnWorld = this.getConfig().get("resistance-rework.world").toString();
		int amp = player.getPotionEffect(PotionEffectType.DAMAGE_RESISTANCE).getAmplifier();
		if (isResistRework && player.getWorld().getName().toString().equalsIgnoreCase(savedSpawnWorld) && amp > 0) {
			// int dur =
			// player.getPotionEffect(PotionEffectType.DAMAGE_RESISTANCE).getDuration();
			player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
			// player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,
			// dur, 0));
		}

	}

}
