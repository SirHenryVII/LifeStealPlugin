package me.sirhenry.lifesteal.listeners;

import me.sirhenry.lifesteal.LifeSteal;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class PlayerKilledListener implements Listener {

    Plugin plugin = LifeSteal.getPlugin(LifeSteal.class);


    @EventHandler
    public void onPlayerKilled(PlayerDeathEvent e) {

        //set "global" variables
        Player victim = e.getEntity();
        Player killer = victim.getKiller();

        double vHealth = victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        double kHealth = killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();

        //if the player was killed by another player
        if (victim.getKiller() != null && victim.getKiller() != victim) {

            //add and subtract health
            victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(vHealth - plugin.getConfig().getDouble("HealthLostOnDeath"));
            killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(kHealth + plugin.getConfig().getDouble("HealthGainedOnKill"));

            //Take a player out of game if necessary
            if (victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() <= 0.0) {
                victim.setGameMode(GameMode.SPECTATOR);
                Bukkit.broadcastMessage(ChatColor.RED + " " + victim.getName() + " has Lost all their Hearts!");
                if (plugin.getConfig().getBoolean("BanIfLoseAllHearts")) {Bukkit.getBanList(BanList.Type.NAME).addBan(victim.getName(), "Lost All Their Lives", null, null);}


        if(victim.getKiller() instanceof Player) {
            if(killer != victim) {

                double vHealth = victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                double kHealth = killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();

                victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(vHealth - plugin.getConfig().getDouble("HealthLostOnDeath"));
            	
            	double maxHearts = plugin.getConfig().getDouble("MaxHearts");
            	if(maxHearts == 0) {
                    killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(kHealth + plugin.getConfig().getDouble("HealthGainedOnKill"));                	            		
            	} else if(kHealth >= maxHearts) {
            		ItemStack item = new ItemStack(Material.NETHER_STAR, 1);
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(ChatColor.RED + "Heart");
                    List<String> lore = new ArrayList<>();
                    lore.add(ChatColor.DARK_RED + "Grant yourself a taste of immortality.");
                    lore.add(ChatColor.DARK_RED + "Use Wisely");
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                    World world = victim.getWorld();
            		Location location = new Location(world, victim.getLocation().getX(), victim.getLocation().getY(), victim.getLocation().getZ());
            		world.dropItemNaturally(location, item);
            	} else {
            		killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(kHealth + plugin.getConfig().getDouble("HealthGainedOnKill"));
            	}

                if(victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() <= 0.0) {

                    victim.setGameMode(GameMode.SPECTATOR);

                }
            }

            //Custom Death Message
            if (plugin.getConfig().getBoolean("DisplayCustomDeathMessage")) {e.setDeathMessage(ChatColor.LIGHT_PURPLE + victim.getDisplayName() + ChatColor.GOLD +
                    " (" + victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + ")" + ChatColor.GRAY + " Was Killed By " + ChatColor.LIGHT_PURPLE + killer.getDisplayName()
                    + ChatColor.GOLD + " (" + killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + ")");
            }

        //if player was killed, but not by another player
        } else if (victim.getKiller() != victim && plugin.getConfig().getBoolean("LoseLifeIfNotKilledByPlayer")) {

            //make victim lose health
            victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(vHealth - plugin.getConfig().getDouble("HealthLostOnDeath"));

            //If player has no hearts left
            if (victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() <= 0.0) {
                victim.setGameMode(GameMode.SPECTATOR);

                //Ban player if option is true in config
                if (plugin.getConfig().getBoolean("BanIfLoseAllHearts")) {
                    Bukkit.getBanList(BanList.Type.NAME).addBan(victim.getName(), "Lost All Their Lives", null, null);
                    victim.kickPlayer(ChatColor.RED + "You have Lost all of your Lives");

                }

            }


        }

    }
}
