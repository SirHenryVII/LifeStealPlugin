package me.sirhenry.lifesteal;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WithdrawCommand implements CommandExecutor {

    int HeartNum;
    Player Receiver;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        //check if console is running command
        if(!(sender instanceof Player)) {
            System.out.println("The Console Cannot Run This Command");
            return true;
        }
        //check if player has permissions
        else if(sender.hasPermission("lifesteal.withdraw") || sender.hasPermission("lifesteal.admin")){
            sender.sendMessage(ChatColor.RED + "You Do Not Have Permission to Use This Command\nPermission Required: \"lifesteal.withdraw\"");
            return true;
        }
        //check if correct number of args are inputted
        else if (args.length != 2) {

            sender.sendMessage(ChatColor.RED + "Usage : /withdraw 10 Player");
            return true;
        }
        //set heart var
        try {
            HeartNum = Integer.parseInt(args[0])*2;
        }catch(NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Usage: /withdraw 10 Player");
            return true;
        }
        //check if player used negative numbers
        if (Integer.parseInt(args[0]) <= 0) {
            sender.sendMessage(ChatColor.RED + "You Cannot use Negative Numbers");
            return true;
        }
        //make sure player can't kill themselves
        if(((Player) sender).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() <= HeartNum + 2) {
            sender.sendMessage(ChatColor.RED + "You do not have Enough Hearts to Perform this Action");
            return true;
        }
        //set player var
        try{
            Receiver = Bukkit.getServer().getPlayer(args[1]);
        }
        catch(Exception ex) {
            sender.sendMessage(ChatColor.RED + "Usage: /withdraw 10 Player");
            sender.sendMessage(ChatColor.RED + "This Player is Not Online");
            return true;
        }

        //add and subtract hearts
        ((Player) sender).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(((Player) sender).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() - HeartNum);
        Receiver.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(Receiver.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + HeartNum);

        //sent chat messages
        sender.sendMessage(ChatColor.GOLD + "You Gave " + Receiver.getDisplayName() + " " + args[0] + " Hearts!");
        Receiver.sendMessage(ChatColor.GOLD + ((Player) sender).getDisplayName() + " Gave you " + args[0] + " Hearts!");

        return true;
    }
}
