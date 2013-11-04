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

import com.eyeofender.gortume.HideAndGo;

public class SpeedCarrot implements Listener{

	private HideAndGo plugin;
	
	public SpeedCarrot ( HideAndGo plugin ){
		this.plugin = plugin;
	}
	
	public ItemStack speedCarrot() {
		ItemStack is = new ItemStack(Material.CARROT_ITEM);
		ItemMeta im = is.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "Left click to gain speed.");
		im.setLore(lore);
		im.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + "Speed Carrot");
		is.setItemMeta(im);
		return is;
	}	
	
	@EventHandler
	public void onItemConsume(PlayerInteractEvent event){
		if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK){
			if(event.getItem().equals(speedCarrot())){
				if(plugin.getInArena().contains(event.getPlayer())){
					event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 2));
					event.getPlayer().getInventory().removeItem(speedCarrot());
					event.getPlayer().getInventory().remove(Material.CARROT_ITEM);
					event.getPlayer().getInventory().remove(speedCarrot());

					plugin.sendMessage(event.getPlayer(), "You have consumed a speed carrot!");
				}
			}
		}
	}
}
