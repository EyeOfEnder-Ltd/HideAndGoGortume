package com.eyeofender.gortume.Listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.eyeofender.gortume.HideAndGo;
import com.eyeofender.gortume.Game.GameManager;

public class DropListener implements Listener{

	private HideAndGo plugin;
	
	public DropListener (HideAndGo plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerItemConsume(PlayerItemConsumeEvent event){
		final Player player = event.getPlayer();
		
		if(plugin.getInArena().contains(player)){
			
			GameManager gm = plugin.getPlayersGame(player);
			
			if(event.getItem().getType() ==  Material.CARROT_ITEM){
			
				
			}
		}
	}
	
}
