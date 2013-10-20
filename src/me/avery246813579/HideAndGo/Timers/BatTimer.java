package me.avery246813579.HideAndGo.Timers;

import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import me.avery246813579.HideAndGo.HideAndGo;
import me.avery246813579.HideAndGo.Game.GameManager;

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
