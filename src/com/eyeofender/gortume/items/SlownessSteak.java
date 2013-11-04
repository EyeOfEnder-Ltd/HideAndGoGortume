package com.eyeofender.gortume.items;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.eyeofender.gortume.HideAndGo;
import com.eyeofender.gortume.game.GameManager;

public class SlownessSteak implements Listener{

	private HideAndGo plugin;
	
	public SlownessSteak ( HideAndGo plugin ){
		this.plugin = plugin;
	}
	
	public ItemStack slownessSteak() {
		ItemStack is = new ItemStack(Material.COOKED_BEEF);
		ItemMeta im = is.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "Left click a player to give them slowness.");
		im.setLore(lore);
		im.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + "Slowness Steak");
		is.setItemMeta(im);
		return is;
	}	
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player attacker = (Player) e.getDamager();
		
			if (e.getEntity() instanceof Player) {
				Player attacked = (Player) e.getEntity();
					
				if(attacker.getInventory().getItemInHand().equals(slownessSteak())){
				if(plugin.getInArena().contains(attacker)){
					GameManager gm = plugin.getPlayersGame(attacker);
					if(plugin.getInArena().contains(attacked)){
						GameManager gm2 = plugin.getPlayersGame(attacked);
						if(gm == gm2){
							if(gm.isInLobby()){
								e.setCancelled(true);
							}else{
								if(gm.getGortumePlayer() == attacker){
									if(!gm.getSpec().contains(attacked)){
										attacked.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 2));
										attacker.getPlayer().getInventory().removeItem(slownessSteak());
										attacker.getPlayer().getInventory().remove(Material.COOKED_BEEF);
										attacker.getPlayer().getInventory().remove(slownessSteak());
																				
										plugin.sendMessage(attacker, "You have given " + ChatColor.BLUE + attacked.getName() + ChatColor.GRAY + " the slowness steak. ");
										plugin.sendMessage(attacked, ChatColor.RED + attacker.getName() + ChatColor.GRAY + " has given you the slowness steak.");
										e.setCancelled(true);
									}else{
										e.setCancelled(true);
									}
								}else if(gm.getSpec().contains(attacker)){
									e.setCancelled(true);
									plugin.sendMessage(attacker, "You can not use items when you are spectating.");
								}else{
									if(gm.getGortumePlayer() == attacked){
										attacked.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 2));
										attacker.getPlayer().getInventory().removeItem(slownessSteak());
										attacker.getPlayer().getInventory().remove(Material.COOKED_BEEF);
										attacker.getPlayer().getInventory().remove(slownessSteak());
																				
										plugin.sendMessage(attacker, "You have given " + ChatColor.BLUE + attacked.getName() + ChatColor.GRAY + " the slowness steak. ");
										plugin.sendMessage(attacked, ChatColor.RED + attacker.getName() + ChatColor.GRAY + " has given you the slowness steak.");
										e.setCancelled(true);
									}else{
										e.setCancelled(true);
										attacked.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 2));
										attacker.getPlayer().getInventory().removeItem(slownessSteak());
										attacker.getPlayer().getInventory().remove(Material.COOKED_BEEF);
										attacker.getPlayer().getInventory().remove(slownessSteak());
																				
										plugin.sendMessage(attacker, "You have given " + ChatColor.BLUE + attacked.getName() + ChatColor.GRAY + " the slowness steak. ");
										plugin.sendMessage(attacked, ChatColor.RED + attacker.getName() + ChatColor.GRAY + " has given you the slowness steak.");									}
								}
							}
						}else{
							e.setCancelled(true);
							plugin.sendMessage(attacker, "You can only hit people in your arena.");
						}
					}else{
						e.setCancelled(true);
						plugin.sendMessage(attacker, "You can only hit people in your arena.");
					}
				}		
				}
			}
		}
	}
}
