package com.eyeofender.gortume.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.eyeofender.gortume.HideAndGo;
import com.eyeofender.gortume.kits.Kit;

public class Kits implements Listener {

    public static Inventory inventory;

    private HideAndGo plugin;

    private Map<Player, String> kits = new HashMap<Player, String>();
    private List<Player> voted = new ArrayList<Player>();

    public Kits(HideAndGo plugin) {
        this.plugin = plugin;
    }

    public void addPlayer(Player player) {
        kits.put(player, "traveler");
        this.voted.add(player);
    }

    public void vote(Player player, String kit) {
        if (plugin.getInArena().contains(player)) {
            if (kit.equalsIgnoreCase("traveler")) {
                kits.put(player, "traveler");
            } else if (kit.equalsIgnoreCase("ninja")) {
                kits.put(player, "ninja");
            } else if (kit.equalsIgnoreCase("tank")) {
                kits.put(player, "tank");
            } else if (kit.equalsIgnoreCase("spy")) {
                kits.put(player, "spy");
            } else if (kit.equalsIgnoreCase("god")) {
                kits.put(player, "god");
            } else {
                plugin.sendMessage(player, "Kit not found.");
                return;
            }

            if (!voted.contains(player)) {
                voted.add(player);
            }

        } else {
            plugin.sendMessage(player, "You have to be in a arena to vote.");
        }
    }

    public void giveItems() {
        for (Player player : voted) {
            String name = kits.get(player);
            Kit kit = Kit.getByName(name);

            if (kit != null) {
                kit.equip(player);
            } else {
                plugin.sendMessage(player, "Kit not found. Contact a admin!");
                return;
            }
        }
    }

    public ItemStack getKitTool() {
        ItemStack is = new ItemStack(Material.EMERALD);
        ItemMeta im = is.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GREEN + "Click to chose a kit.");
        im.setLore(lore);
        im.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + "Kit Selection");
        is.setItemMeta(im);
        return is;
    }

    private void showKitMenu(Player player) {
        inventory = Bukkit.getServer().createInventory(null, 9, "Kit Selection");

        player.openInventory(inventory);

        inventory.setItem(0, getTravelerIcon());
        inventory.setItem(2, getNinjaIcon());
        inventory.setItem(4, getSpyIcon());
        inventory.setItem(6, getTankIcon());
        inventory.setItem(8, getGodItem());
    }

    private ItemStack getTravelerIcon() {
        ItemStack is = new ItemStack(Material.CHAINMAIL_HELMET);
        ItemMeta im = is.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GREEN + "Click to use kit.");
        im.setLore(lore);
        im.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + "Traveler");
        is.setItemMeta(im);
        return is;
    }

    private ItemStack getNinjaIcon() {
        ItemStack is = new ItemStack(Material.LEATHER_HELMET);
        ItemMeta im = is.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GREEN + "Click to use kit.");
        im.setLore(lore);
        im.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + "Ninja");
        is.setItemMeta(im);
        return is;
    }

    private ItemStack getSpyIcon() {
        ItemStack is = new ItemStack(Material.IRON_HELMET);
        ItemMeta im = is.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GREEN + "Click to use kit.");
        im.setLore(lore);
        im.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + "Spy");
        is.setItemMeta(im);
        return is;
    }

    private ItemStack getTankIcon() {
        ItemStack is = new ItemStack(Material.GOLD_HELMET);
        ItemMeta im = is.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GREEN + "Click to use kit.");
        im.setLore(lore);
        im.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + "Tank");
        is.setItemMeta(im);
        return is;
    }

    private ItemStack getGodItem() {
        ItemStack is = new ItemStack(Material.DIAMOND_HELMET);
        ItemMeta im = is.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GREEN + "Click to use kit.");
        im.setLore(lore);
        im.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + "God");
        is.setItemMeta(im);
        return is;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if (event.getItem().getItemMeta().getDisplayName().equals("" + ChatColor.GOLD + ChatColor.BOLD + "Kit Selection")) {

                if (event.getAction() == null || event.getItem() == null) {
                    return;
                }

                this.showKitMenu(event.getPlayer());
                event.setCancelled(true);

            }
        }

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getAction() == null || event.getItem() == null) {
                return;
            }

            if (event.getItem() == this.getTravelerIcon()) {
                event.setCancelled(true);
                Kit.getByName("travler").equip(player);
            } else if (event.getItem() == this.getNinjaIcon()) {
                event.setCancelled(true);
                Kit.getByName("ninja").equip(player);
            } else if (event.getItem() == this.getSpyIcon()) {
                event.setCancelled(true);
                Kit.getByName("spy").equip(player);
            } else if (event.getItem() == this.getTankIcon()) {
                event.setCancelled(true);
                Kit.getByName("tank").equip(player);
            } else if (event.getItem() == this.getGodItem()) {
                event.setCancelled(true);
                Kit.getByName("god").equip(player);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();

        if (clicked == null || clicked.getType() == Material.AIR) return;

        if (clicked.getItemMeta().getDisplayName().equals("" + ChatColor.GOLD + ChatColor.BOLD + "Magic Compass")) {
            event.setCancelled(true);
            this.showKitMenu(player);
            return;
        }

        if (event.getInventory().equals(inventory)) {

            // TODO

        }
    }
}
