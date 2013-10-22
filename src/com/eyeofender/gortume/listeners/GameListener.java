package com.eyeofender.gortume.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.kitteh.tag.PlayerReceiveNameTagEvent;

import com.eyeofender.gortume.HideAndGo;
import com.eyeofender.gortume.game.GameManager;

public class GameListener implements Listener {

	private HideAndGo plugin;
	
	public GameListener(HideAndGo plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerInteract (PlayerInteractEvent event){
		if(plugin.getInArena().contains(event.getPlayer())){
			GameManager gm = plugin.getPlayersGame(event.getPlayer());
			 if(gm.getAlive().contains(event.getPlayer())){
				if(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK){
					if(event.getClickedBlock().getType() == Material.EMERALD_BLOCK){
						gm.addClicked(event.getPlayer());
						gm.checkPlayersAlive();
						gm.tellArena(event.getPlayer() + " has clicked the emerald.");
					}
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
				plugin.sendMessage(event.getPlayer(), "You can not teleport in game.");
			}
		}
	}
	
	@EventHandler
	public void onNameTag(PlayerReceiveNameTagEvent event) {
		for(Player player : plugin.getInArena()){
			if (event.getNamedPlayer().getName().equals(player.getName())) {
				event.setTag("_");
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
			GameManager gm = plugin.getPlayersGame(player);
			gm.tellArena("The gortume has killed " + player.getName() + ".");
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
										e.setCancelled(false);
										gm.checkPlayersAlive();
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
}
