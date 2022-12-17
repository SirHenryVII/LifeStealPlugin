package me.sirhenry.lifesteal;

import me.sirhenry.lifesteal.listeners.PlayerInteractListener;
import me.sirhenry.lifesteal.listeners.PlayerJoinListener;
import me.sirhenry.lifesteal.listeners.PlayerKilledListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class LifeSteal extends JavaPlugin {

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(new PlayerKilledListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        getCommand("withdraw").setExecutor(new WithdrawCommand());
        getCommand("smpreset").setExecutor(new ResetCommand());
        getCommand("addhearts").setExecutor(new AddHeartsCommand());
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        Data.setup();
        Bukkit.addRecipe(HeartItem.getHeartRecipe());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}

