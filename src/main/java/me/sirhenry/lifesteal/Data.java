package me.sirhenry.lifesteal;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class Data {

    static Plugin plugin = LifeSteal.getPlugin(LifeSteal.class);

    private static File file;
    private static FileConfiguration fileConfig;

    public static void setup() {
        file = new File(plugin.getDataFolder(), "Data.yml");

        if(!file.exists()){
            try{
                file.createNewFile();
            }catch(IOException ex){
                // :(
            }
        }
        fileConfig = YamlConfiguration.loadConfiguration(file);
    }
    public static FileConfiguration get(){
        return fileConfig;
    }

    public static void save(){
        try{
            fileConfig.save(file);
        }catch(IOException ex){
            // :(
        }

    }

    public static void reload(){
        fileConfig = YamlConfiguration.loadConfiguration(file);
    }
}
