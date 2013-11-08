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
import com.eyeofender.gortume.game.GameManager;
import com.eyeofender.gortume.kits.Kit;

public class Kits implements Listener {

    public static Inventory inventory;

    private HideAndGo plugin;

    private Map<Player, String> kits = new HashMap<Player, String>();
    private List<Player> voted = new ArrayList<Player>();
    @SuppressWarnings("unused")
	private GameManager gm;
    
    public Kits(HideAndGo plugin, GameManager gm) {
        this.plugin = plugin;
        this.gm = gm;
    }

    public Kits(HideAndGo plugin2) {
		// TODO Auto-generated constructor stub
	}

	public void addPlayer(Player player) {
        kits.put(player, "traveler");
        this.voted.add(player);
    }
	
	public Kits(){
		
	}

    public static ItemStack getKitTool() {
        ItemStack is = new ItemStack(Material.EMERALD);
        ItemMeta im = is.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GREEN + "Left click to open tool.");
        im.setLore(lore);
        im.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + "Kit Selection");
        is.setItemMeta(im);
        return is;
    }

    public static void showKitMenu(Player player) {
        inventory = Bukkit.getServer().createInventory(null, 9, "Kit Selection");

        player.openInventory(inventory);

        inventory.setItem(0, getTravelerIcon());
        inventory.setItem(2, getNinjaIcon());
        inventory.setItem(4, getSpyIcon());
        inventory.setItem(6, getTankIcon());
        inventory.setItem(8, getGodItem());
    }

    private static ItemStack getTravelerIcon() {
        ItemStack is = new ItemStack(Material.CHAINMAIL_HELMET);
        ItemMeta im = is.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GREEN + "Click to use kit.");
        im.setLore(lore);
        im.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + "Traveler");
        is.setItemMeta(im);
        return is;
    }

    private static ItemStack getNinjaIcon() {
        ItemStack is = new ItemStack(Material.LEATHER_HELMET);
        ItemMeta im = is.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GREEN + "Click to use kit.");
        im.setLore(lore);
        im.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + "Ninja");
        is.setItemMeta(im);
        return is;
    }

    private static ItemStack getSpyIcon() {
        ItemStack is = new ItemStack(Material.IRON_HELMET);
        ItemMeta im = is.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GREEN + "Click to use kit.");
        im.setLore(lore);
        im.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + "Spy");
        is.setItemMeta(im);
        return is;
    }

    private static ItemStack getTankIcon() {
        ItemStack is = new ItemStack(Material.GOLD_HELMET);
        ItemMeta im = is.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GREEN + "Click to use kit.");
        im.setLore(lore);
        im.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + "Tank");
        is.setItemMeta(im);
        return is;
    }

    private static ItemStack getGodItem() {
        ItemStack is = new ItemStack(Material.DIAMOND_HELMET);
        ItemMeta im = is.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GREEN + "Click to use kit.");
        im.setLore(lore);
        im.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + "God");
        is.setItemMeta(im);
        return is;
    }

    @SuppressWarnings("static-access")
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
                Kit.getByName("traveler").equip(player);
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

    @SuppressWarnings("static-access")
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
