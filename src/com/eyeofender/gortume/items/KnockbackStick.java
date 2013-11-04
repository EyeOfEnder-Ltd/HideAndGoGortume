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
import org.bukkit.util.Vector;

import com.eyeofender.gortume.HideAndGo;
import com.eyeofender.gortume.game.GameManager;

public class KnockbackStick implements Listener{

	private HideAndGo plugin;
	
	public KnockbackStick ( HideAndGo plugin ){
		this.plugin = plugin;
	}
	
	public ItemStack knockbackStick() {
		ItemStack is = new ItemStack(Material.STICK);
		ItemMeta im = is.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "Left click a player to knock them back.");
		im.setLore(lore);
		im.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + "Knockback Stick");
		is.setItemMeta(im);
		return is;
	}	
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player attacker = (Player) e.getDamager();
		
			if (e.getEntity() instanceof Player) {
				Player attacked = (Player) e.getEntity();
					
				if(attacker.getInventory().getItemInHand().equals(knockbackStick())){
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
										Vector direction = attacked.getLocation().getDirection();
										attacked.setVelocity(direction.setY(-0.5).multiply(-2));
										attacker.getPlayer().getInventory().removeItem(knockbackStick());
										attacker.getPlayer().getInventory().remove(Material.STICK);
										attacker.getPlayer().getInventory().remove(knockbackStick());
																				
										plugin.sendMessage(attacker, "You have knocked " + ChatColor.BLUE + attacked.getName() + ChatColor.GRAY + " back. ");
										plugin.sendMessage(attacked, ChatColor.RED + attacker.getName() + ChatColor.GRAY + " has knocked you back.");
										e.setCancelled(true);
									}else{
										e.setCancelled(true);
									}
								}else if(gm.getSpec().contains(attacker)){
									e.setCancelled(true);
									plugin.sendMessage(attacker, "You can not use items when you are spectating.");
								}else{
									if(gm.getGortumePlayer() == attacked){
										Vector direction = attacked.getLocation().getDirection();
										attacked.setVelocity(direction.setY(-0.5).multiply(-2));
										attacker.getPlayer().getInventory().removeItem(knockbackStick());
										attacker.getPlayer().getInventory().remove(Material.STICK);
										attacker.getPlayer().getInventory().remove(knockbackStick());
																				
										plugin.sendMessage(attacker, "You have knocked " + ChatColor.BLUE + attacked.getName() + ChatColor.GRAY + " back. ");
										plugin.sendMessage(attacked, ChatColor.RED + attacker.getName() + ChatColor.GRAY + " has knocked you back.");
										e.setCancelled(true);
									}else{
										Vector direction = attacked.getLocation().getDirection();
										attacked.setVelocity(direction.setY(-0.5).multiply(-2));
										attacker.getPlayer().getInventory().removeItem(knockbackStick());
										attacker.getPlayer().getInventory().remove(Material.STICK);
										attacker.getPlayer().getInventory().remove(knockbackStick());
																				
										plugin.sendMessage(attacker, "You have knocked " + ChatColor.BLUE + attacked.getName() + ChatColor.GRAY + " back. ");
										plugin.sendMessage(attacked, ChatColor.RED + attacker.getName() + ChatColor.GRAY + " has knocked you back.");
										e.setCancelled(true);
									}
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
