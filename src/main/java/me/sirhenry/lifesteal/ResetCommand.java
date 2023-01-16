package me.sirhenry.lifesteal;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.awt.*;
import java.util.UUID;

public class ResetCommand implements CommandExecutor {
	Plugin plugin = LifeSteal.getPlugin(LifeSteal.class);

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		Player p = ((Player) sender);

		//Check if player has permission
		if(!p.hasPermission("lifesteal.admin")) {
			p.sendMessage(ChatColor.RED + "You Do Not Have Permission to Use This Command. Permission Required: \"lifesteal.admin\"");
			return true;
		}
		//Check if 1 arg
		if(args.length != 1){
			p.sendMessage(ChatColor.RED + "Usage: /smpreset");
			return true;
		}
		//if Confirm
		if(args[0].equalsIgnoreCase("confirm")){
			SmpReset();
			return true;
		}

		//Send Confirmation message if needed
		TextComponent ConfirmationMessage = new TextComponent("Click Here to Confirm the SMP Reset");
		ConfirmationMessage.setBold(true);
		ConfirmationMessage.setColor(net.md_5.bungee.api.ChatColor.GREEN);
		ConfirmationMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/smpreset confirm"));
		return true;
	}

	private void SmpReset() {

		//Revive Dead Players
		for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
			UUID uuid = p.getUniqueId();
			//If player online reset them
			if (Bukkit.getPlayer(uuid) != null) {
				Player player = Bukkit.getPlayer(uuid);
				player.setGameMode(GameMode.SURVIVAL);
				player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(plugin.getConfig().getDouble("DefaultHealth"));
				//If player is fully dead tp them
				if(Data.get().contains("dead." + p.getUniqueId())){
					if (player.getBedSpawnLocation() == null) {
						player.teleport(player.getWorld().getSpawnLocation());
					} else {
						player.teleport(player.getBedSpawnLocation());
					}
				}
			//If player is not online, put them on revive list if they are not already
			} else if(Data.get().getConfigurationSection("revive").contains(p.getUniqueId().toString())){
				Data.get().set("revive." + uuid, "");
			}

		}

		//Clear Dead List
		Data.get().set("dead", null);
		Data.save();

		//send chat message
		Bukkit.broadcastMessage(ChatColor.BOLD + "SMP Reset Complete!");
	}

}