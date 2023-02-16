package me.sirhenry.lifesteal.commands;

import me.sirhenry.lifesteal.Data;
import me.sirhenry.lifesteal.LifeSteal;
import me.sirhenry.lifesteal.Util;
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

		//Check if player has permission
		if(!sender.hasPermission("lifesteal.admin")) {
			sender.sendMessage(ChatColor.RED + "You Do Not Have Permission to Use This Command. Permission Required: \"lifesteal.admin\"");
			return true;
		}
		//if Confirm
		if(args.length == 1){
			if(args[0].equalsIgnoreCase("confirm")){
				SmpReset();
			}
			else{
				sender.sendMessage(ChatColor.RED + "Usage: /smpreset");
			}
			return true;
		}
		else if(args.length > 1){
			sender.sendMessage(ChatColor.RED + "Usage: /smpreset");
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
				Util.setHearts(player, plugin.getConfig().getDouble("DefaultHealth"));
				//If player is fully dead tp them and set gamemode to survival
				if(Data.get().contains("dead." + p.getUniqueId())){
					if (player.getBedSpawnLocation() == null) {
						player.teleport(player.getWorld().getSpawnLocation());
					} else {
						player.teleport(player.getBedSpawnLocation());
					}
					player.setGameMode(GameMode.SURVIVAL);
				}
			//If player is not online
			}else{
				//if player is dead, then put on revive list (if not on it already)
				if(Data.get().contains("dead." + p.getUniqueId()) && !Data.get().contains("revive." + p.getUniqueId())){
					Data.get().set("revive." + uuid, " ");
					Data.save();
				}
				//if player is not dead, just offline, add to softrevive list
				if(!Data.get().contains("dead." + p.getUniqueId())){
					Data.get().set("softrevive." + uuid, " ");
					Data.save();
				}
			}


		}

		//Clear Dead List
		Data.get().set("dead", " ");
		Data.save();

		//send chat message
		Bukkit.broadcastMessage(ChatColor.BOLD + "SMP Reset Complete!");
	}

}