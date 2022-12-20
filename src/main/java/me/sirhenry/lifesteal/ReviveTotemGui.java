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
import java.util.UUID;

public class ReviveTotemGui {

    public static void setPage(int page, Player player){

        Inventory GUI = Bukkit.createInventory(player, 27, "Select a Player:");
        int Pages;
        ArrayList<ItemStack> HeadList = new ArrayList<>();
        ItemStack[] HeadListArr;

        for(String uuid : Data.get().getConfigurationSection("dead").getKeys(false)){
            ItemStack headTemp = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullTemp = (SkullMeta) headTemp.getItemMeta();
            skullTemp.setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString(uuid)));
            skullTemp.setDisplayName(Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName());
            ArrayList<String> tempLore = new ArrayList<>();
            tempLore.add(ChatColor.RED + Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName());
            skullTemp.setLore(tempLore);
            headTemp.setItemMeta(skullTemp);
            HeadList.add(headTemp);
        }

        HeadListArr = HeadList.toArray(new ItemStack[0]);
        Pages = (int)Math.ceil((double)HeadListArr.length/18);

        if(page>Pages || page<1){return;}
        ItemStack[] tempPage = new ItemStack[27];
        ItemStack greenGlass = new ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1);
        ItemMeta tempMeta = greenGlass.getItemMeta();
        tempMeta.setDisplayName(" ");
        greenGlass.setItemMeta(tempMeta);
        ItemStack signBack = new ItemStack(Material.OAK_SIGN, 1);
        tempMeta = signBack.getItemMeta();
        tempMeta.setDisplayName("<-- Back");
        signBack.setItemMeta(tempMeta);
        ItemStack signForward = new ItemStack(Material.OAK_SIGN, 1);
        tempMeta = signForward.getItemMeta();
        tempMeta.setDisplayName("Forward -->");
        signForward.setItemMeta(tempMeta);
        ItemStack signPage = new ItemStack(Material.OAK_SIGN, 1);
        tempMeta = signPage.getItemMeta();
        tempMeta.setDisplayName(Integer.toString(page));
        signPage.setItemMeta(tempMeta);
        tempPage[0] = signBack;
        tempPage[1] = greenGlass;
        tempPage[2] = greenGlass;
        tempPage[3] = greenGlass;
        tempPage[4] = signPage;
        tempPage[5] = greenGlass;
        tempPage[6] = greenGlass;
        tempPage[7] = greenGlass;
        tempPage[8] = signForward;

        int j = 18*(page-1);
        for(int i = 9; i <= 26; i++){
            if(j > HeadListArr.length-1){
                break;
            }
            tempPage[i] = HeadListArr[j];
            j++;
        }

        GUI.setContents(tempPage);
        player.openInventory(GUI);
    }
}
