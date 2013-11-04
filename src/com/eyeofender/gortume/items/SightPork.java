package com.eyeofender.gortume.items;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import com.eyeofender.gortume.HideAndGo;

public class SightPork implements Listener{

	private HideAndGo plugin;
	
	public SightPork ( HideAndGo plugin ){
		this.plugin = plugin;
	}
	
	public ItemStack sightPork() {
		ItemStack is = new ItemStack(Material.PORK);
		ItemMeta im = is.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "Left click to gain visiblity.");
		im.setLore(lore);
		im.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + "Sight Pork");
		is.setItemMeta(im);
		return is;
	}	
	
	@EventHandler
	public void onItemConsume(PlayerInteractEvent event){
		if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK){
			if(event.getItem().equals(sightPork())){
				if(plugin.getInArena().contains(event.getPlayer())){
					event.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
					event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 200, 2));
					
					while(!event.getPlayer().getActivePotionEffects().contains(PotionEffectType.NIGHT_VISION)){
						event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS , 100000000, 5));
						return;
					}
					
					event.getPlayer().getInventory().removeItem(sightPork());
					event.getPlayer().getInventory().remove(Material.PORK);
					event.getPlayer().getInventory().remove(sightPork());
										
					plugin.sendMessage(event.getPlayer(), "You have consumed a sight pork!");
				}
			}
		}
	}
}
