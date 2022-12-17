package me.sirhenry.lifesteal.listeners;

import me.sirhenry.lifesteal.LifeSteal;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

public class PlayerInteractListener implements Listener {

	Plugin plugin = LifeSteal.getPlugin(LifeSteal.class);

    @EventHandler
    public void PlayerInteract(PlayerInteractEvent e) {

        if(e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.NETHER_STAR) && e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§cHeart")) {
            if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            	
            	double pHealth = e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
            	double maxHearts = plugin.getConfig().getDouble("MaxHealth");
            	
                if(pHealth + 2 > maxHearts) {e.getPlayer().sendMessage(ChatColor.RED + "This Action Violates the \"Max Hearts\" Parameter."); return;}

                e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + 2);

            }

        }

    }

}
