package com.eyeofender.gortume.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;

import com.eyeofender.gortume.HideAndGo;
import com.eyeofender.gortume.game.GameManager;
import com.eyeofender.gortume.items.Kits;
import com.eyeofender.gortume.kits.Kit;

public class GameListener implements Listener {

	private HideAndGo plugin;
	
	public GameListener(HideAndGo plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerInteract (PlayerInteractEvent event){
		if(plugin.getInArena().contains(event.getPlayer())){
			GameManager gm = plugin.getPlayersGame(event.getPlayer());
			
			   if (event.getAction() == null || event.getItem() == null) {
                   return;
               }
			
			if ((event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) && event.getItem().getItemMeta().getDisplayName().equals("" + ChatColor.GOLD + ChatColor.BOLD + "Kit Selection")) {

                Kits.showKitMenu(event.getPlayer());
                event.setCancelled(true);

            }
			
				if(gm.getAlive().contains(event.getPlayer())){
						if(event.getClickedBlock().getType() == Material.EMERALD_BLOCK){
							gm.addClicked(event.getPlayer());
							gm.checkPlayersAlive();
							gm.tellArena(event.getPlayer().getName() + " has clicked the emerald.");
					}
				}
		} if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.SIGN_POST || event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.SIGN || event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.WALL_SIGN){
		     
	    	Block block = event.getClickedBlock();
	    	Sign sign = (Sign) block.getState();
	     
	    		if(sign.getLine(0).equalsIgnoreCase(""  + ChatColor.GREEN + ChatColor.BOLD + "[Join]")){
	    			String arena = sign.getLine(1);
	    			
	    			GameManager gm = plugin.getGameManager(arena);
	    			
	    			if(gm != null){
	    				Player player = event.getPlayer();
	    				player.getInventory().addItem(Kits.getKitTool());
	    				gm.joinArena(player);
	    				player.getInventory().addItem(Kits.getKitTool());
	    			}else{
	    				plugin.sendMessage(event.getPlayer(), "Arena could not be found.");
	    			}
	    		}
	    }
	}
	
	@EventHandler
	public void onFoodLevelChange (FoodLevelChangeEvent event){
		if (! event.isCancelled())
		{
			if (event.getEntity() instanceof Player)
			{
				Player player = (Player) event.getEntity();
				if (plugin.getInArena().contains(player)){
					event.setFoodLevel(20);
				}
			}
		}
	}
	
	@EventHandler
	public void onTeleportEvent(PlayerTeleportEvent event){
		if (plugin.getInArena().contains(event.getPlayer())){
			if(event.getCause() == TeleportCause.COMMAND){
				event.setCancelled(true);
				plugin.sendMessage(event.getPlayer(), "You can not teleport in game.");
			}
		}
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event){
		Player player = event.getPlayer();
		
		if(plugin.getInArena().contains(player)){
			GameManager gm = plugin.getPlayersGame(player);
			if(gm.getGortumePlayer() != player && !gm.getSpec().contains(player)){
				event.setRespawnLocation(gm.getArena().getRegularSpawn());
			}
		}
	}
	
	@EventHandler
	public void onPlayerDeath (PlayerDeathEvent event){
		Player player = event.getEntity().getPlayer();
		if(plugin.getInArena().contains(player)){
			event.getDrops().clear();
			
			GameManager gm = plugin.getPlayersGame(player);
			
			plugin.sendDeath(player, "The Gortume has killed " + ChatColor.BLUE + player.getName() + ChatColor.GRAY + ".");
			
			gm.addSpectator(player);
			gm.checkPlayersAlive();
		}
	}
	
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event){
		Player player = event.getPlayer();
		if(plugin.getInArena().contains(player)){
			event.setCancelled(true);
		}
	}
	
	@EventHandler 
	public void onPlayerPickupItem(PlayerPickupItemEvent event){
		Player player = event.getPlayer();
		if(plugin.getInArena().contains(player)){
			
			GameManager gm = plugin.getPlayersGame(player);
			
			if(gm.getGortumePlayer() != player){
				
				if (event.getItem().getItemStack().getType() == Material.CARROT_ITEM) {
					event.setCancelled(false);
				}
				
			}else{
				event.setCancelled(true);
			}
		}
	}
	
	public void onPlayerDisconnect(Player player)
	{
		plugin.getPlayersGame(player).leaveArena(player);
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event){
		if(plugin.getInArena().contains(event.getPlayer())){
			onPlayerDisconnect(event.getPlayer());
		}
	}
	
	@EventHandler
	public void onPlayerKick(PlayerKickEvent event){
		if(plugin.getInArena().contains(event.getPlayer())){
			onPlayerDisconnect(event.getPlayer());
		}
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player attacker = (Player) e.getDamager();
		
			if (e.getEntity() instanceof Player) {
				Player attacked = (Player) e.getEntity();
					
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
										gm.checkPlayersAlive();
										
										attacked.damage(5.0);;
										
									}else{
										e.setCancelled(true);
									}
								}else if(gm.getSpec().contains(attacker)){
									e.setCancelled(true);
									plugin.sendMessage(attacker, "You can not attack when you are spectating.");
								}else{
									if(gm.getGortumePlayer() == attacked){
										attacked.setHealth(20);
									}else{
										e.setCancelled(true);
										plugin.sendMessage(attacker, "You can not attack your team members!");
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
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		if(plugin.getCantTalk().contains(e.getPlayer())){
	        e.setCancelled(true);
	        plugin.sendChat(e.getPlayer(), "Chatting is disibled while in game.");
		}
	}
	
	@EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();

        if(plugin.getInArena().contains(player)){
        
        	
	        if (clicked == null || clicked.getType() == Material.AIR) return;
	
	        if (clicked.getItemMeta().getDisplayName().equals("" + ChatColor.GOLD + ChatColor.BOLD + "Traveler")) {
	            event.setCancelled(true);
                Kit.getByName("Travler").equip(player);
	            return;
	        }
	        
	        if (clicked.getItemMeta().getDisplayName().equals("" + ChatColor.GOLD + ChatColor.BOLD + "Ninja")) {
	            event.setCancelled(true);
                Kit.getByName("Ninja").equip(player);
	            return;
	        }

	        if (clicked.getItemMeta().getDisplayName().equals("" + ChatColor.GOLD + ChatColor.BOLD + "Tank")) {
	            event.setCancelled(true);
                Kit.getByName("Tank").equip(player);
	            return;
	        }
	        
	        if (clicked.getItemMeta().getDisplayName().equals("" + ChatColor.GOLD + ChatColor.BOLD + "Spy")) {
	            event.setCancelled(true);
                Kit.getByName("Spy").equip(player);
	            return;
	        }
	        
	        if (clicked.getItemMeta().getDisplayName().equals("" + ChatColor.GOLD + ChatColor.BOLD + "God")) {
	            event.setCancelled(true);
                Kit.getByName("God").equip(player);
	            return;
	        }
        }
    }
}
