package me.sirhenry.lifesteal.commands;

import me.sirhenry.lifesteal.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReviveCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("LifeSteal.admin")) {
            Util.setRevivePage(1, (Player)sender);}
        else{sender.sendMessage(ChatColor.RED + "You Do Not Have Permission to Use This Command\nPermission Required: \"lifesteal.admin\"");}
        return true;
    }
}
