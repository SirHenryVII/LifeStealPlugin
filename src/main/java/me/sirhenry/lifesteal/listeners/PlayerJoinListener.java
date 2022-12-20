package me.sirhenry.lifesteal.listeners;

import me.sirhenry.lifesteal.Data;
import me.sirhenry.lifesteal.LifeSteal;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class PlayerJoinListener implements Listener {

    Plugin plugin = LifeSteal.getPlugin(LifeSteal.class);
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){

        Player player = e.getPlayer();
        //Set player to default health if they have never played before
        if(!player.hasPlayedBefore()) {player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(plugin.getConfig().getDouble("DefaultHealth"));}

        //Revive if Necessary
        try{
            for(String uuid : Data.get().getConfigurationSection("revive").getKeys(false)){
                if(UUID.fromString(uuid).equals(e.getPlayer().getUniqueId())){
                    e.getPlayer().setGameMode(GameMode.SURVIVAL);
                    e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(plugin.getConfig().getDouble("DefaultHealth"));
                    if(e.getPlayer().getBedSpawnLocation() == null){
                        e.getPlayer().teleport(e.getPlayer().getWorld().getSpawnLocation());
                    }
                    else{
                        e.getPlayer().teleport(e.getPlayer().getBedSpawnLocation());
                    }
                    Data.get().set("revive." + e.getPlayer().getUniqueId(), null);
                }
            }
        }catch(Exception ignored){}

    }

}
