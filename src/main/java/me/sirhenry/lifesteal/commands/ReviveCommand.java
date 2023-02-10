package me.sirhenry.lifesteal.commands;

import me.sirhenry.lifesteal.Data;
import me.sirhenry.lifesteal.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ReviveCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("LifeSteal.admin")) {

            if(args.length == 0){
                Util.setRevivePage(1, (Player)sender);
            }
            else if(args.length == 1 && Data.get().contains("dead." + Bukkit.getOfflinePlayer(args[0]).getUniqueId())){

            }
        }
        else{
            sender.sendMessage(ChatColor.RED + "You Do Not Have Permission to Use This Command\nPermission Required: \"lifesteal.admin\"");
        }
        return true;
    }
}
