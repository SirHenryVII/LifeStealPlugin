package me.sirhenry.lifesteal.items;

import me.sirhenry.lifesteal.LifeSteal;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class HeartItem {

    private static Plugin plugin = LifeSteal.getPlugin(LifeSteal.class);


    public static ItemStack getItem(){
        ItemStack item = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§cHeart");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_RED + "Created Using The Riches of 1000 Men");
        lore.add(ChatColor.DARK_RED + "Grants an Extra Heart... Use Wisely");
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public static ShapedRecipe getHeartRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, "Heart");
        ShapedRecipe sr = new ShapedRecipe(key, getItem());

        sr.shape("ABC", "DEF", "GHI");

        char[] Alphabet = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};

        for(int i = 0; i < 9; i++) sr.setIngredient(Alphabet[i], Material.valueOf((String) plugin.getConfig().get("HeartRecipe.Slot" + i)));

        return sr;
    }
}
