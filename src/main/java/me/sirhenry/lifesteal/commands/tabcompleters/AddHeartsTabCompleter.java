package me.sirhenry.lifesteal.commands.tabcompleters;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AddHeartsTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {

        ArrayList<String> list = new ArrayList<>();

        //if on second arg, list all players, else do nothing
        if(args.length == 2){
            return null;
        }

        return list;
    }
}
