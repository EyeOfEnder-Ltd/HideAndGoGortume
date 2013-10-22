package com.eyeofender.gortume.Listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.eyeofender.gortume.HideAndGo;
import com.eyeofender.gortume.Game.GameManager;

public class MovementListener implements Listener{

	private HideAndGo plugin;
	
	public MovementListener (HideAndGo plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event){
		Player player = event.getPlayer();
		
		if(plugin.getInArena().contains(player)){
			GameManager gm = plugin.getPlayersGame(player);
			if(!gm.getCube().isInside((player.getLocation()))){
				if (! gm.getCube().isInside(event.getFrom()))
				{
					if (! gm.getCube().isInside(event.getTo()))
					{
						player.teleport(gm.getArena().getLobbySpawn());
					}
					else
					{
						event.setCancelled(true);
					}
					plugin.sendMessage(player, "You can not leave the arena.");
				}
			}
		}
	}
	
	@EventHandler
    public void PlayerMove(PlayerMoveEvent event){
        
		Player p = event.getPlayer();
        Location q = event.getFrom();
        Location w = event.getTo();
       
        if(plugin.getNoMove().contains(p)){
	        if(p instanceof Player){
	        	if(q.getBlockX() != w.getBlockX() || q.getBlockY() != w.getBlockY() || q.getBlockZ() != w.getBlockZ()){
	                event.setTo(q);
	        	}
	        }
        }
	}
}
