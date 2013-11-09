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
    private static Inventory kitMenu;

    static {
        menuItem = new ItemStack(Material.EMERALD, 1);
        ItemMeta meta = menuItem.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GREEN + "Click to chose a kit.");
        meta.setLore(lore);
        meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + TITLE);
        menuItem.setItemMeta(meta);

        Kit[] kits = Kit.getKits().toArray(new Kit[Kit.getKits().size()]);
        kitMenu = Bukkit.getServer().createInventory(null, (int) (Math.ceil(kits.length / 9.0) * 9.0), TITLE);

        for (int i = 0; i < kits.length; i++) {
            kitMenu.setItem(i, kits[i].getIcon());
        }
    }

    public static String getTitle() {
        return TITLE;
    }

    public static ItemStack getMenuItem() {
        return menuItem;
    }

    public static void display(Player player) {
        player.openInventory(kitMenu);
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
