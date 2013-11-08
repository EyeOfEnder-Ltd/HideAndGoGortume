package com.eyeofender.gortume.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.eyeofender.gortume.kits.Kit;
import com.eyeofender.gortume.kits.KitMenu;

public class MenuListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (KitMenu.areEqual(KitMenu.getMenuItem(), event.getItem())) {
            KitMenu.display(player);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();

        if (clicked == null || clicked.getType() == Material.AIR) return;

        if (event.getInventory().getTitle() == KitMenu.getTitle()) {
            for (Kit kit : Kit.getKits()) {
                if (KitMenu.areEqual(clicked, kit.getIcon())) {
                    kit.equip(player);
                    return;
                }
            }
        }
    }

}
