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
            skullTemp.setDisplayName(ChatColor.BOLD + Bukkit.getOfflinePlayers()[i].getName());
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
        ItemStack[] tempPage = new ItemStack[27];
        ItemStack greenGlass = new ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1);
        ItemStack signBack = new ItemStack(Material.OAK_SIGN, 1);
        signBack.getItemMeta().setDisplayName(ChatColor.BOLD + "<-- Back");
        ItemStack signForward = new ItemStack(Material.OAK_SIGN, 1);
        signForward.getItemMeta().setDisplayName(ChatColor.BOLD + "Forward -->");
        tempPage[0] = signBack;
        tempPage[8] = signForward;
        for(int i = 1; i<=8; i++) {tempPage[i] = greenGlass;}

        int j = 18*(p-1);
        for(int i = 9; i <= 26; i++){
            tempPage[i] = HeadList[j];
            j++;
        }
        GUI.setContents(tempPage);
    }
}
