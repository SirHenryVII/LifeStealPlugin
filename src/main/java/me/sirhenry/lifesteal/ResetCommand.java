package me.sirhenry.lifesteal;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import de.tr7zw.nbtapi.*;
import java.io.File;
import java.io.IOException;

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

		//Check if NBT Api is installed
		if(!Bukkit.getPluginManager().isPluginEnabled("NBTAPI")){
			p.sendMessage(ChatColor.RED + "This Command can Only be Used if \"NBT Api\" (plugin) is Installed\n\"NBT Api\" can be Found here: https://www.curseforge.com/minecraft/bukkit-plugins/nbt-api/files");
		}

		//for every player
		for(OfflinePlayer player : Bukkit.getServer().getOfflinePlayers()){
			String path = Bukkit.getWorldContainer() + File.separator + "lifesteal" + File.separator + "playerdata" + File.separator;
			String f = player.getUniqueId() + ".dat";
			NBTFile nbt;

			//if player is online, set hearts to default
			if(player.isOnline()) {
				Player onlinePlayer = (Player) player;
				onlinePlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(plugin.getConfig().getDouble("DefaultHealth"));
				continue;
			}

			//If player is not online, set their heart value to default using item nbt api
			try {
				nbt = new NBTFile(new File(path + f));
				NBTCompoundList list = nbt.getCompoundList("Attributes");
				for(int i=0;i<list.size();i++) {
					NBTListCompound item = list.get(i);
					if(item.getString("Name").equals("minecraft:generic.max_health")) {
						sender.sendMessage(item.getString("Base"));
						item.setDouble("Base", plugin.getConfig().getDouble("DefaultHealth"));
						nbt.save();
						break;
					} else {
						continue;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//send chat message
		Bukkit.broadcastMessage(ChatColor.BOLD + "SMP Reset Complete!");
		return true;
	}
}