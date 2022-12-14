package me.sirhenry.lifesteal;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ReviveTotemGui {

    public Inventory GUI;
    private int Pages;
    private ItemStack[] HeadList;
    public int CurrentPage;

    public ReviveTotemGui(Player player){

        GUI = Bukkit.createInventory(player, 27, ChatColor.GOLD + "Please Select Which Player you would like to Revive:");

        HeadList = new ItemStack[Bukkit.getOfflinePlayers().length];
        for(int i = 0; i < HeadList.length; i++){
            ItemStack headTemp = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullTemp = (SkullMeta) headTemp.getItemMeta();
            skullTemp.setOwningPlayer(Bukkit.getOfflinePlayers()[i]);
            ArrayList<String> tempLore = new ArrayList<>();
            tempLore.add(ChatColor.RED + Bukkit.getOfflinePlayers()[i].getName());
            skullTemp.setLore(tempLore);
            headTemp.setItemMeta(skullTemp);
            HeadList[i] = headTemp;
        }
        Pages = (int)Math.ceil((double)HeadList.length/18);
        setPage(1);
        player.openInventory(GUI);
    }

    public void setPage(int p){

        if(p>Pages || p<1){return;}

        for(int i = 0; i < 25; i++){

        }

    }
}
