package me.sirhenry.lifesteal.listeners;

import me.sirhenry.lifesteal.Data;
import me.sirhenry.lifesteal.Util;
import me.sirhenry.lifesteal.items.HeartItem;
import me.sirhenry.lifesteal.LifeSteal;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

public class PlayerKilledListener implements Listener {

    Plugin plugin = LifeSteal.getPlugin(LifeSteal.class);

    @EventHandler
    public void onPlayerKilled(PlayerDeathEvent e) {

        Player victim = e.getEntity();
        Player killer = victim.getKiller();
        double vHealth = Util.getHearts(victim);

        if(victim.getKiller() != null && killer != victim) {

            //set vars
            double kHealth = Util.getHearts(killer);

            //Make victim lose health
            Util.setHearts(victim, vHealth - plugin.getConfig().getDouble("HealthGainedOnKill"));

            //give killer health and drop heart item if required
            for(int i = 0; i < plugin.getConfig().getDouble("HealthGainedOnKill"); i += 2 ){

                if(kHealth + i < plugin.getConfig().getDouble("MaxHealth")){
                    Util.setHearts(killer, Util.getHearts(killer) + 2.0);
                    continue;
                }

                if(plugin.getConfig().getBoolean("DropHeartsOnDeath")){
                    victim.getWorld().dropItemNaturally(victim.getLocation(), HeartItem.getItem());
                }
            }

            //custom death message
            e.setDeathMessage(ChatColor.LIGHT_PURPLE + victim.getDisplayName() + ChatColor.GOLD + " ("  + Util.getHearts(victim)
                    + ")" + ChatColor.GRAY + " Was Killed By " + ChatColor.LIGHT_PURPLE + killer.getDisplayName() + ChatColor.GOLD + " ("  +
                    Util.getHearts(killer) + ")");

        }

        //if LiseLifeIfNotKilledByPlayer is true, then make them lose health
        else if(plugin.getConfig().getBoolean("LoseLifeIfNotKilledByPlayer")){Util.setHearts(victim, vHealth - plugin.getConfig().getDouble("HealthGainedOnKill"));}

        //If Victim looses all lives set gamemode to spectator or ban in applicable
        if(victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() <= 0){
            victim.setGameMode(GameMode.SPECTATOR);
            Util.setHearts(victim, plugin.getConfig().getDouble("DefaultHealth"));
            Data.get().set("dead." + victim.getUniqueId(), "");
            Data.save();
            if(plugin.getConfig().getBoolean("BanIfLoseAllLives")){
                Util.setHearts(victim, plugin.getConfig().getDouble("DefaultHealth"));
                Bukkit.getBanList(BanList.Type.NAME).addBan(victim.getName(), ChatColor.RED + "You Lost all your lives!", null, null);
                victim.kickPlayer(ChatColor.RED + "You Lost all your lives!");
                Data.get().set("dead." + victim.getUniqueId(), "");
                Data.save();
            }
            
        }

    }

}
