package me.sirhenry.lifesteal.commands;

import me.sirhenry.lifesteal.LifeSteal;
import me.sirhenry.lifesteal.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class AddHeartsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        int HeartNum;
        Player Receiver;
        Plugin plugin = LifeSteal.getPlugin(LifeSteal.class);

        //Check Permissions
        if(!sender.hasPermission("lifesteal.mod") && !sender.hasPermission("lifesteal.admin")){
            sender.sendMessage(ChatColor.RED + "You Do Not Have Permission to Use This Command\nPermission Required: \"lifesteal.mod\"");
            return true;
        }
        //check for Correct Number of Args
        if(args.length != 2){
            sender.sendMessage(ChatColor.RED + "Usage: /AddHearts 10 Player");
            return true;
        }
        //Set HeartNum var
        try{
            HeartNum = Integer.parseInt(args[0])*2;
        }catch(NumberFormatException ex){
            sender.sendMessage(ChatColor.RED + "Usage: /AddHearts 10 Player");
            return true;
        }
        //set receiver var
        try{
            Receiver = Bukkit.getPlayer(args[1]);
        }catch(Exception ex){
            sender.sendMessage(ChatColor.RED + "Usage: /AddHearts 10 Player\nThat player may not be online.");
            return true;
        }
        //check if violates max health
        if(Util.getHearts(Receiver) + HeartNum > plugin.getConfig().getDouble("MaxHealth")){
            sender.sendMessage(ChatColor.RED + "This Action Violates the \"Max Hearts\" Parameter.");
            return true;
        }

        Util.setHearts(Receiver, Util.getHearts(Receiver) + HeartNum);

        return true;
    }
}
