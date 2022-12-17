package me.sirhenry.lifesteal.listeners;

import me.sirhenry.lifesteal.Data;
import me.sirhenry.lifesteal.HeartItem;
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
        double vHealth = victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();

        if(victim.getKiller() != null && killer != victim) {

            //set vars
            double kHealth = killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();

            //Make victim lose health
            victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(vHealth - plugin.getConfig().getDouble("HealthLostOnDeath"));

            //give killer health and drop heart item if required
            for(int i = 0; i < plugin.getConfig().getDouble("HealthGainedOnKill"); i += 2 ){

                if(kHealth + i < plugin.getConfig().getDouble("MaxHealth")){
                    killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + 2.0);
                    continue;
                }
                victim.getWorld().dropItemNaturally(victim.getLocation(), HeartItem.getItem());
            }

            //custom death message
            e.setDeathMessage(ChatColor.LIGHT_PURPLE + victim.getDisplayName() + ChatColor.GOLD + " ("  + victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()
                    + ")" + ChatColor.GRAY + " Was Killed By " + ChatColor.LIGHT_PURPLE + killer.getDisplayName() + ChatColor.GOLD + " ("  +
                    killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + ")");

        }

        //if LiseLifeIfNotKilledByPlayer is true, then make them lose health
        else if(plugin.getConfig().getBoolean("LoseLifeIfNotKilledByPlayer")){victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(vHealth - plugin.getConfig().getDouble("HealthLostOnDeath"));}

        //If Victim looses all lives set gamemode to spectator or ban in applicable
        if(victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() <= 0){
            victim.setGameMode(GameMode.SPECTATOR);
            victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(plugin.getConfig().getDouble("DefaultHealth"));
            Data.get().set("dead." + victim.getUniqueId(), "");
            Data.save();
            if(plugin.getConfig().getBoolean("BanIfLoseAllLives")){
                Bukkit.getBanList(BanList.Type.NAME).addBan(victim.getName(), ChatColor.RED + "You Lost all your lives!", null, null);
                victim.kickPlayer(ChatColor.RED + "You Lost all your lives!");
            }
        }

    }

}
