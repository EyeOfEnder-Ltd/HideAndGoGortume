package com.eyeofender.gortume.kits;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KitMenu {

    private static final String TITLE = "Kit Selection";
    private static ItemStack menuItem;

    static {
        menuItem = new ItemStack(Material.EMERALD, 1);
        ItemMeta meta = menuItem.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GREEN + "Click to chose a kit.");
        meta.setLore(lore);
        meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + TITLE);
        menuItem.setItemMeta(meta);
    }

    public static String getTitle() {
        return TITLE;
    }

    public static ItemStack getMenuItem() {
        return menuItem;
    }

    public static void display(Player player) {
        Kit[] kits = (Kit[]) Kit.getKits().toArray();
        Inventory inv = Bukkit.getServer().createInventory(null, (int) (Math.ceil(kits.length / 9) * 9), TITLE);

        player.openInventory(inv);

        for (int i = 0; i < kits.length; i++) {
            inv.setItem(i, kits[i].getIcon());
        }
    }

    public static boolean areEqual(ItemStack item1, ItemStack item2) {
        if (!item1.isSimilar(item2)) return false;

        if (item1.hasItemMeta() && item2.hasItemMeta()) {
            ItemMeta meta1 = item1.getItemMeta();
            ItemMeta meta2 = item2.getItemMeta();

            if (meta1.hasDisplayName() && meta2.hasDisplayName()) {
                return meta1.getDisplayName().equals(meta2.getDisplayName());
            } else if (!meta1.hasDisplayName() && !meta2.hasDisplayName()) {
                return true;
            }
        } else if (!item1.hasItemMeta() && !item2.hasItemMeta()) {
            return true;
        }

        return false;
    }

}