package com.eyeofender.gortume.timers;

import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.eyeofender.gortume.HideAndGo;
import com.eyeofender.gortume.game.GameManager;

public class BatTimer implements Runnable{
	
	private HideAndGo plugin;
	
	private GameManager gm;
	
	public BatTimer (HideAndGo plugin, GameManager gm){
		this.plugin = plugin;
		this.gm = gm;
	}
	
	@Override
	public void run() {
		if(gm.isBat()){
			int timer = gm.getBatTimer();
			
			if(gm.getBatTimer() != 0){
				timer--;
				gm.setBatTimer(timer);
			}else{

				gm.setBatTimer(plugin.getConfigHelper().getBatTimer());

				for(Player player : gm.getArenaPlayers()){
					if(!gm.getSpec().contains(player) || gm.getGortumePlayer() != player){
						World world = player.getWorld();
						world.spawnEntity(player.getLocation(), EntityType.BAT);
					}
				}				
			}
		}
	}
}
