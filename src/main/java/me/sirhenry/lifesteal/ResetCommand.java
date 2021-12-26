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
		Player me = ((Player) sender);
		if(!me.isOp()) {
			return false;
		}
		
		for(OfflinePlayer player : Bukkit.getServer().getOfflinePlayers()){
			String path = Bukkit.getWorldContainer() + File.separator + "lifesteal" + File.separator + "playerdata" + File.separator;
			String f = player.getUniqueId() + ".dat";
			NBTFile nbt;
			
			if(player.isOnline()) {
				Player onlinePlayer = (Player) player;
				onlinePlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(plugin.getConfig().getDouble("DefaultHealth"));
				continue;
			}

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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Bukkit.broadcastMessage("Season " + args[0] + ": '" + ChatColor.RED + ChatColor.BOLD + args[1] + ChatColor.RESET + "' has begun!");
		return true;
	}
}
