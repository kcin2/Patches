@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			Player player = ((Player) sender).getPlayer();
			if (cmd.getName().equalsIgnoreCase(cmd1)) {
				String savedSpawnWorld = plugin.getConfig().get("resistance-rework.world").toString();
				boolean isresistrework = plugin.getConfig().getBoolean("resistance-rework-enabled");

				if (sender.isOp() || sender.hasPermission("kcin.*")) {

					if (args.length != 0) {

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
								+ ChatColor.WHITE + "Resistance rework enabled: " + ChatColor.GREEN + isresistrework
								+ "\n" + ChatColor.WHITE + "Resistance rework world: " + ChatColor.GREEN
								+ savedSpawnWorld + "\n----------------------------------------------------");
						return true;
					}

				} else {
					if (clr > 11) {
						clr = 0;
					}
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
