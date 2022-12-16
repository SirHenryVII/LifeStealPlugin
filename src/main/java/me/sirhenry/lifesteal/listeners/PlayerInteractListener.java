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

        //check if player right-clicked on heart
        if(e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.NETHER_STAR) && e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§cHeart")) {
            if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

                //Check if Max Health is being violated
                if(e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + 2.0 >= plugin.getConfig().getDouble("MaxHearts")){
                    e.getPlayer().sendMessage(ChatColor.RED + "You Already have Maximum Health!");
                    return;
                }

                //set health
                e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + 2);
                e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);

            }

        }

    }

}
