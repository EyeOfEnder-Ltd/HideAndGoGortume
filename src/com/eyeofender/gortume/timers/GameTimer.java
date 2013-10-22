package com.eyeofender.gortume.timers;

import org.bukkit.entity.Player;

import com.eyeofender.gortume.HideAndGo;
import com.eyeofender.gortume.game.GameManager;

public class GameTimer implements Runnable {

	private HideAndGo plugin;
	
	private GameManager gm;
	
	public GameTimer (HideAndGo plugin, GameManager gm){
		this.plugin = plugin;
		this.gm = gm;
	}
	
	@Override
	public void run() {
		if(gm.isGame()){
			int timer = gm.getGameTimer();
			if(gm.getGameTimer() != 0){
				for(Player player : gm.getArenaPlayers()){
					player.setLevel(timer);
				}
				
				timer--;
				
				/** Resets the Timer **/
				gm.setGameTimer(timer);
			}else{
				plugin.getServer().getScheduler().cancelTask(gm.getGame());
				gm.setGameTimer(-1);
				gm.setGame(false);
				gm.gameTimerRunout();
			}
		}
	}
}
