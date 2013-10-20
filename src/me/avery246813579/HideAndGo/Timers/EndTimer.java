package me.avery246813579.HideAndGo.Timers;

import org.bukkit.entity.Player;

import me.avery246813579.HideAndGo.HideAndGo;
import me.avery246813579.HideAndGo.Game.GameManager;
import me.avery246813579.HideAndGo.Handlers.FireworkHandler;

public class EndTimer implements Runnable {

	private HideAndGo plugin;
	
	GameManager gm;
	
	public EndTimer (HideAndGo plugin, GameManager gm){
		this.plugin = plugin;
		this.gm = gm;
	}
	
	@Override
	public void run() {
		if(this.gm.isEnd()){
			int endTimer = gm.getEndTimer();
			if(this.gm.getEndTimer() != -1){
				if(this.gm.getEndTimer() != 0){
					FireworkHandler fh = new FireworkHandler(plugin, gm);
					fh.Shootfireworks();
					endTimer--;
					gm.setEndTimer(endTimer);
				}else{
					gm.stopArena();

					gm.setEndTimer(-1);
					gm.setEnd(false);
					plugin.getServer().getScheduler().cancelTask(gm.getEnd());					
				}
			}
		}
	}
}
