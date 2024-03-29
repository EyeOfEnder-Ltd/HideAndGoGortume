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

    private KitMenu() {
    }

    public static void init() {
        menuItem = new ItemStack(Material.EMERALD, 1);
        ItemMeta meta = menuItem.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GREEN + "Click to chose a kit.");
        meta.setLore(lore);
        meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + TITLE);
        menuItem.setItemMeta(meta);

        Kit[] kits = Kit.getKits().toArray(new Kit[Kit.getKits().size()]);
        int slots = kits.length * 2 - 1;
        kitMenu = Bukkit.getServer().createInventory(null, (int) (Math.ceil(kits.length / 9.0) * 9.0), TITLE);

        for (int i = 0; i < slots; i++) {
            if (i % 2 == 0) kitMenu.setItem(i, kits[i / 2].getIcon());
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

}
