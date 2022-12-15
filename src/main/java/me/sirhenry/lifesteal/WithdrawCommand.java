package me.sirhenry.lifesteal;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class WithdrawCommand implements CommandExecutor {

    int HeartNum;
    Player Receiver;

	Plugin plugin = LifeSteal.getPlugin(LifeSteal.class);

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
        else {
            boolean check = false;
            for(Player players : Bukkit.getOnlinePlayers()) {

                if(players == sender) {
                    check = true;
                    break;
                }

            }
            if(!check) {
                sender.sendMessage("Usage : /withdraw 10 Player");
                sender.sendMessage("Player Name is Not Valid");
                return false;
            }

            try {

                Integer.parseInt(args[0]);

            }catch(NumberFormatException e) {

                sender.sendMessage(ChatColor.RED + "Usage : /withdraw 10 Player");
                return false;

            }

            if (Integer.parseInt(args[0]) <= 0) {
                sender.sendMessage(ChatColor.RED + "You Cannot put Negative Numbers.");
                return false;
            }
            if(((Player) sender).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() <= Integer.parseInt(args[0]) * 2) return false;

            Player player = ((Player) sender);
            Player other = Bukkit.getServer().getPlayer(args[1]);
            
            double healthCheckPlayer = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
            double healthCheckOther =  other.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
            double maxHealth = plugin.getConfig().getDouble("MaxHearts");
            double otherRem = healthCheckOther + (Integer.parseInt(args[0]) * 2);
            if(maxHealth != 0.0) {
            	if(otherRem > maxHealth) {
            		sender.sendMessage(ChatColor.RED + "The hearts you give will breach the max hearts allocated.");
            		return false;
            	}
            }
            
            if(((Player) sender).getGameMode() == GameMode.SURVIVAL) {

                Bukkit.getServer().getPlayer(args[1]).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(Bukkit.getServer().getPlayer(args[1]).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + Integer.parseInt(args[0]) * 2);
                ((Player) sender).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(((Player) sender).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() - Integer.parseInt(args[0]) * 2);

                sender.sendMessage(ChatColor.GOLD + "You Gave " + Bukkit.getServer().getPlayer(args[1]).getDisplayName() + " " + args[0] + " Hearts!");
                Bukkit.getServer().getPlayer(args[1]).sendMessage(ChatColor.GOLD + ((Player) sender).getDisplayName() + " Gave you " + args[0] + " Hearts!");
            }
            else {


        //add and subtract hearts
        ((Player) sender).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(((Player) sender).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() - HeartNum);
        Receiver.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(Receiver.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + HeartNum);

        //sent chat messages
        sender.sendMessage(ChatColor.GOLD + "You Gave " + Receiver.getDisplayName() + " " + args[0] + " Hearts!");
        Receiver.sendMessage(ChatColor.GOLD + ((Player) sender).getDisplayName() + " Gave you " + args[0] + " Hearts!");

        return true;
    }
}
