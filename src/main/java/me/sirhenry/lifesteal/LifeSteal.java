package me.sirhenry.lifesteal;

import me.sirhenry.lifesteal.listeners.PlayerInteractListener;
import me.sirhenry.lifesteal.listeners.PlayerJoinListener;
import me.sirhenry.lifesteal.listeners.PlayerKilledListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public final class LifeSteal extends JavaPlugin {

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(new PlayerKilledListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        getCommand("withdraw").setExecutor(new WithdrawCommand());
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        Bukkit.addRecipe(heartRecipe());

        Bukkit.broadcastMessage(ChatColor.GREEN + "Life Steal Plugin has Finished Loading!");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public ShapedRecipe heartRecipe() {

        ItemStack item = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Heart");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_RED + "Created Using The Riches of 1000 Men");
        lore.add(ChatColor.DARK_RED + "Grants an Extra Heart... Use Wisely");
        meta.setLore(lore);
        item.setItemMeta(meta);

        NamespacedKey key = new NamespacedKey(this, "Heart");
        ShapedRecipe sr = new ShapedRecipe(key, item);

        sr.shape("ABC", "DEF", "GHI");

        char Alphabet[] = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};

        for(int i = 0; i < 9; i++) sr.setIngredient(Alphabet[i], Material.valueOf((String) getConfig().get("HeartRecipe.Slot" + i)));

        return sr;
    }

}

