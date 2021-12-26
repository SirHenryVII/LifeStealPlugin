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
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {
            System.out.println("The Console Cannot Run This Command");
            return true;
        }

        else if (args.length != 2) {

            sender.sendMessage(ChatColor.RED + "Usage : /withdraw 10 Player");
            return true;

        }

        else {


            try {

                Integer.parseInt(args[0]);

            }catch(NumberFormatException e) {

                sender.sendMessage(ChatColor.RED + "Usage: /withdraw 10 Player");
                return true;

            }

            if (Integer.parseInt(args[0]) <= 0) {
                sender.sendMessage(ChatColor.RED + "You Cannot use Negative Numbers");
                return true;
            }

            if(((Player) sender).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() <= Integer.parseInt(args[0]) / 2 + 2) {
                sender.sendMessage(ChatColor.RED + "You do not have Enough Hearts to Perform this Action");
                return true;
            }

            try{
                Bukkit.getServer().getPlayer(args[1]);
            }
            catch(Exception ex) {
                sender.sendMessage(ChatColor.RED + "Usage: /withdraw 10 Player");
                sender.sendMessage(ChatColor.RED + "This Player is Not Online");
                return true;
            }


            if(((Player) sender).getGameMode() == GameMode.SURVIVAL) {

                Bukkit.getServer().getPlayer(args[1]).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(Bukkit.getServer().getPlayer(args[1]).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + Integer.parseInt(args[0]) * 2);
                ((Player) sender).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(((Player) sender).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() - Integer.parseInt(args[0]) * 2);

                sender.sendMessage(ChatColor.GOLD + "You Gave " + Bukkit.getServer().getPlayer(args[1]).getDisplayName() + " " + args[0] + " Hearts!");
                Bukkit.getServer().getPlayer(args[1]).sendMessage(ChatColor.GOLD + ((Player) sender).getDisplayName() + " Gave you " + args[0] + " Hearts!");
            }
            else {

                sender.sendMessage(ChatColor.RED + "You Can Only Perform This Command While in Survival Mode");

            }
            return true;
        }
    }
}
