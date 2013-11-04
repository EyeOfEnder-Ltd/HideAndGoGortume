package com.eyeofender.gortume.items;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import com.eyeofender.gortume.HideAndGo;
import com.eyeofender.gortume.game.GameManager;

public class GoldenApple implements Listener{

	private HideAndGo plugin;
	
	public GoldenApple ( HideAndGo plugin ){
		this.plugin = plugin;
	}
	
	public ItemStack goldenApple() {
		ItemStack is = new ItemStack(Material.GOLDEN_APPLE);
		ItemMeta im = is.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "Left click to gain health, healing, and resistance.");
		im.setLore(lore);
		im.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + "Golden Apple");
		is.setItemMeta(im);
		return is;
	}	
	
	@EventHandler
	public void onItemConsume(PlayerInteractEvent event){
		if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK){
			if(event.getItem().equals(goldenApple())){
				if(plugin.getInArena().contains(event.getPlayer())){
					event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 200, 5));
					event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 5));
					event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 5));

					event.getPlayer().getInventory().removeItem(goldenApple());
					event.getPlayer().getInventory().remove(Material.GOLDEN_APPLE);
					event.getPlayer().getInventory().remove(goldenApple());
										
					plugin.sendMessage(event.getPlayer(), "You have consumed a golden apple!");
				}
			}
		}
	}
}
