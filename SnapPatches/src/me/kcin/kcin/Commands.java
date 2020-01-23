package me.kcin.kcin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import net.minecraft.server.v1_12_R1.CommandExecute;

public class Commands extends CommandExecute implements Listener, CommandExecutor {

	Mainkcin plugin;

	public Commands(Mainkcin pl) {
		plugin = pl;
	}

	public String cmd1 = "kcin";
	public String[] str = {"a","b","c","d","e","1","2","3","4","5","6","9"};
	public int clr;
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			Player player = ((Player) sender).getPlayer();
			if (cmd.getName().equalsIgnoreCase(cmd1)) {
				String savedPlotsWorld = plugin.getConfig().get("plotspatch.world").toString();
				String savedSpawnWorld = plugin.getConfig().get("resistance-rework.world").toString();
				boolean isplotspatch = plugin.getConfig().getBoolean("plotspatch-enabled");
				boolean isresistrework = plugin.getConfig().getBoolean("resistance-rework-enabled");

				if (sender.isOp() || sender.hasPermission("kcin.*")) {

					if (args.length != 0) {
						if (args[0].equalsIgnoreCase("setPlotsWorld")) {

							plugin.getConfig().set("plotspatch.world", player.getWorld().getName().toString());
							plugin.saveConfig();
							sender.sendMessage(ChatColor.GREEN + "Plots patch world set to " + ChatColor.GOLD
									+ player.getWorld().getName().toString());
							return true;

						} else

						if (args[0].equalsIgnoreCase("setRRWorld")) {

							plugin.getConfig().set("resistance-rework.world", player.getWorld().getName().toString());
							plugin.saveConfig();
							sender.sendMessage(ChatColor.GREEN + "Resistance rework world set to " + ChatColor.GOLD
									+ player.getWorld().getName().toString());
							return true;

						} else {
							sender.sendMessage(ChatColor.RED + "Invalid argument! Try: " + ChatColor.GOLD
									+ "setPlotsWorld" + ChatColor.RED + ", " + ChatColor.GOLD + "setRRWorld");
							return true;
						}

					} else {

						sender.sendMessage(ChatColor.GREEN + "----------------------------------------------------\n"
								+ ChatColor.WHITE + "Plots tp patch enabled: " + ChatColor.GREEN + isplotspatch + "\n"
								+ ChatColor.WHITE + "Plots tp patch world: " + ChatColor.GREEN + savedPlotsWorld + "\n"
								+ ChatColor.WHITE + "Resistance rework enabled: " + ChatColor.GREEN + isresistrework
								+ "\n" + ChatColor.WHITE + "Resistance rework world: " + ChatColor.GREEN
								+ savedSpawnWorld + "\n----------------------------------------------------");
						return true;
					}

				} else {
					if (clr > 11) {clr = 0;}
					String a = str[clr];
					sender.sendMessage("§" + a + " ok");
					clr++;
					return true;
					
				}

			}

		}

		else {
			sender.sendMessage(ChatColor.RED + "Command is for players only.");
			return true;
		}
		return false;
	}

}
