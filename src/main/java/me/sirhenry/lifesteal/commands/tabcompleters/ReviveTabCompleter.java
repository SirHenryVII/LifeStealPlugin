package me.sirhenry.lifesteal.commands.tabcompleters;

import me.sirhenry.lifesteal.Data;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReviveTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {

        ArrayList<String> list = new ArrayList<>();

        //add tab completer for everyone on dead list
        if(args.length == 1){
            for(String uuid : Data.get().getConfigurationSection("dead").getKeys(false)){
                list.add(Bukkit.getPlayer(UUID.fromString(uuid)).getDisplayName());
            }
        }
        //if list in empty, return null
        if(list.isEmpty()){
            return null;
        }

        return list;
    }
}
