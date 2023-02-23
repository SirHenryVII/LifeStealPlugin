package me.sirhenry.lifesteal.commands.tabcompleters;

import me.sirhenry.lifesteal.LifeSteal;
import me.sirhenry.lifesteal.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class WithdrawTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {

        Plugin plugin = LifeSteal.getPlugin(LifeSteal.class);
        ArrayList<String> list = new ArrayList<>();
        Player player = (Player) sender;

        //for first arg, put all valid number inputs (not accounting for MaxHealth)
        if(args.length == 1){
            for(int i = (int) Util.getHearts(player)-2; i!=0; i-=2){
                list.add(Integer.toString(i/2));
            }
        }

        //for second arg, just list all online players
        else if(args.length == 2){
            return null;
        }
        return list;
    }
}
