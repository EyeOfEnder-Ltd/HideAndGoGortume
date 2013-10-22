package com.eyeofender.gortume.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.eyeofender.gortume.HideAndGo;
import com.eyeofender.gortume.game.GameManager;

public class BlockListener implements Listener{

	private HideAndGo plugin;
	
	public BlockListener (HideAndGo plugin){
		this.plugin = plugin;
	}

	@EventHandler
	public void onBlockPlace (BlockPlaceEvent event){
		if(plugin.getInArena().contains(event.getPlayer())){
			
			GameManager gm = plugin.getPlayersGame(event.getPlayer());
			
			if(gm.getGortumePlayer() == event.getPlayer()){
			
				if(event.getBlockPlaced().getType() == Material.EMERALD_BLOCK){
					
					gm.startGortume();
					
					gm.setBlockLocation(event.getBlockPlaced().getLocation());
					
				}
			
			}else{
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak (BlockBreakEvent event){
		GameManager gm = plugin.getPlayersGame(event.getPlayer());
		
		if(gm != null){
			if(gm.getCube().isInside(event.getBlock().getLocation())){
				event.setCancelled(true);
			}
		}
	}
}
