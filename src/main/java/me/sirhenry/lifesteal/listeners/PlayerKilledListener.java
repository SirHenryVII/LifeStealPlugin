package me.sirhenry.lifesteal.listeners;

import me.sirhenry.lifesteal.LifeSteal;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

import javax.security.auth.login.Configuration;

public class PlayerKilledListener implements Listener {

    Plugin plugin = LifeSteal.getPlugin(LifeSteal.class);

    @EventHandler
    public void onPlayerKilled(PlayerDeathEvent e) {

        Player victim = e.getEntity();

        if(victim.getKiller() instanceof Player) {

            Player killer = victim.getKiller();

            double vHealth = victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
            double kHealth = killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();

            victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(vHealth - plugin.getConfig().getDouble("HealthLostOnDeath"));
            killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(kHealth + plugin.getConfig().getDouble("HealthGainedOnKill"));

            if(victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() <= 0.0) {

                victim.setGameMode(GameMode.SPECTATOR);

            }

            e.setDeathMessage(ChatColor.LIGHT_PURPLE + victim.getDisplayName() + ChatColor.GOLD + " ("  + victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + ")" + ChatColor.GRAY + " Was Killed By " + ChatColor.LIGHT_PURPLE + killer.getDisplayName() + ChatColor.GOLD + " ("  + killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + ")");

        }

        else {

            if(plugin.getConfig().getBoolean("LoseLifeIfNotKilledByPlayer")) {
                double vHealth = victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();

                victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(vHealth - plugin.getConfig().getDouble("HealthLostOnDeath"));

                if (victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() <= 0.0) {

                    victim.setGameMode(GameMode.SPECTATOR);

                }

                if(victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() <= 0.0) {

                    victim.setGameMode(GameMode.SPECTATOR);

                }

            }
        }

    }

}
