package me.sirhenry.lifesteal;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class ReviveTotemItem {
    static Plugin plugin = LifeSteal.getPlugin(LifeSteal.class);

    public static ItemStack getItem(){
        ItemStack item = new ItemStack(Material.TOTEM_OF_UNDYING, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§aRevive Totem");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_RED + "Created Using The Riches of 10000 Men");
        lore.add(ChatColor.DARK_RED + "Revives a Dead Player... Use Wisely");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
        item.setItemMeta(meta);

        return item;
    }

    public static ShapedRecipe getReviveTotemRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, "ReviveTotem");
        ShapedRecipe sr = new ShapedRecipe(key, getItem());

        sr.shape("ABC", "DEF", "GHI");

        char[] Alphabet = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};

        for(int i = 0; i < 9; i++) sr.setIngredient(Alphabet[i], Material.valueOf((String) plugin.getConfig().get("ReviveTotemRecipe.Slot" + i)));

        return sr;
    }
}
