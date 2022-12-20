package me.sirhenry.lifesteal;

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
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class ResetCommand implements CommandExecutor {
	Plugin plugin = LifeSteal.getPlugin(LifeSteal.class);

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		Player p = ((Player) sender);

		//Check if player has permission
		if(!p.hasPermission("lifesteal.admin")) {
			p.sendMessage(ChatColor.RED + "You Do Not Have Permission to Use This Command\nPermission Required: \"lifesteal.admin\"");
			return true;
		}

		//for every player
		for(String key : Data.get().getConfigurationSection("dead").getKeys(false)){
			UUID uuid = UUID.fromString(key);
			//if player is online, set hearts to default
			if(Bukkit.getPlayer(uuid) != null) {
				Player player = Bukkit.getPlayer(uuid);
				player.setGameMode(GameMode.SURVIVAL);
				player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(plugin.getConfig().getDouble("DefaultHealth"));
				if(player.getBedSpawnLocation() == null){
					player.teleport(player.getWorld().getSpawnLocation());
				}
				else{
					player.teleport(player.getBedSpawnLocation());
				}
			}
			else{
				Data.get().set("revive." + uuid, "");
			}

		}

		//Take people off Dead List
		Data.get().set("dead", null);
		Data.save();

		//send chat message
		Bukkit.broadcastMessage(ChatColor.BOLD + "SMP Reset Complete!");
		return true;
	}
}