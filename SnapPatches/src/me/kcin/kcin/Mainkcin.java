package me.kcin.kcin;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

public class Mainkcin extends JavaPlugin implements Listener {

	private Commands commands = new Commands(this);

	private final Map<UUID, Instant> loginCooldown = new HashMap<>();

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
		loginCooldown.clear();
	}

	@EventHandler
	public void onChangeWorld(PlayerChangedWorldEvent event) {
		Player player = event.getPlayer();
		boolean isResistRework = this.getConfig().getBoolean("resistance-rework-enabled");
		String savedSpawnWorld = this.getConfig().get("resistance-rework.world").toString();
		int amp = player.getPotionEffect(PotionEffectType.DAMAGE_RESISTANCE).getAmplifier();
		if (isResistRework && player.getWorld().getName().toString() == savedSpawnWorld && amp > 0) {
			// int dur = player.getPotionEffect(PotionEffectType.DAMAGE_RESISTANCE).getDuration();
			player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
			// player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, dur, 0));
		}

	}

	@EventHandler
	public void onTeleport(PlayerTeleportEvent event) {
		Player player = event.getPlayer();
		String toWorld = event.getTo().getWorld().getName();
		String fromWorld = event.getFrom().getWorld().getName();
		String savedPlotsWorld = this.getConfig().getString("plotspatch.world").toString();

		if (toWorld.toString() == fromWorld.toString() && fromWorld.toString().equals(savedPlotsWorld)) {

			double fromY = event.getFrom().getY();
			double fromX = event.getFrom().getBlockX();
			double fromZ = event.getFrom().getBlockZ();
			double toX = event.getTo().getX();
			double toZ = event.getTo().getZ();

			if (toX == fromX && toZ == fromZ) {

				if (loginCooldown.containsKey(player.getUniqueId())) {
					Instant currentTime = Instant.now();
					Instant login = loginCooldown.get(player.getUniqueId());

					if (currentTime.isAfter(login.plusSeconds(3))) {

						event.setCancelled(true);
						float yaw = player.getLocation().getYaw();
						float pitch = player.getLocation().getPitch();
						player.teleport(new Location(player.getWorld(), (int) fromX + 0.5, fromY, (int) fromZ + 0.5, yaw, pitch));

					}

				}
				
			}

		}

	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		loginCooldown.put(player.getUniqueId(), Instant.now());
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		loginCooldown.remove(player.getUniqueId());
	}

}
