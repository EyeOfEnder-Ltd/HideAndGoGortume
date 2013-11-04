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

public class InvisiblePie implements Listener{

	private HideAndGo plugin;
	
	public InvisiblePie ( HideAndGo plugin ){
		this.plugin = plugin;
	}
	
	public ItemStack invisiblePie() {
		ItemStack is = new ItemStack(Material.PUMPKIN_PIE);
		ItemMeta im = is.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "Left click to go invisible.");
		im.setLore(lore);
		im.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + "Invisible Pie");
		is.setItemMeta(im);
		return is;
	}	
	
	@EventHandler
	public void onItemConsume(PlayerInteractEvent event){
		if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK){
			if(event.getItem().equals(invisiblePie())){
				if(plugin.getInArena().contains(event.getPlayer())){
					event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 200, 2));
					event.getPlayer().getInventory().removeItem(invisiblePie());
					event.getPlayer().getInventory().remove(Material.PUMPKIN_PIE);
					event.getPlayer().getInventory().remove(invisiblePie());

					plugin.sendMessage(event.getPlayer(), "You have consumed a invisible pie!");
				}
			}
		}
	}
}
