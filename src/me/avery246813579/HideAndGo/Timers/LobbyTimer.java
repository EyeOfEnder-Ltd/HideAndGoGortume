package me.avery246813579.HideAndGo.Timers;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import me.avery246813579.HideAndGo.HideAndGo;
import me.avery246813579.HideAndGo.Game.GameManager;

public class LobbyTimer implements Runnable{

	private HideAndGo plugin;
	
	private GameManager gm;
	
	public LobbyTimer (HideAndGo plugin, GameManager gm){
		this.plugin = plugin;
		this.gm = gm;
	}
	
	@Override
	public void run() {
		if(this.gm.isLobby()){
			int lobbyTimer = gm.getLobbyTimer();
			if(this.gm.getLobbyTimer() != -1){
				if (lobbyTimer != 0) {
					lobbyTimer--;
					for (Player p : gm.getArenaPlayers()) {
						p.setLevel(lobbyTimer);
					}
					gm.setLobbyTimer(lobbyTimer);
				}
				if (lobbyTimer == 10) {
					for (Player p : gm.getArenaPlayers()) {
						p.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- " + ChatColor.BLUE + "Gortume starts in: 10" + ChatColor.GOLD + " -=-=-=- -=-=- -=-");
						p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
						p.setLevel(lobbyTimer);
						gm.setLobbyTimer(lobbyTimer);
					}
				}
				if (lobbyTimer == 9) {
					for (Player p : gm.getArenaPlayers()) {
						p.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- " + ChatColor.BLUE + "Gortume starts in: 9" + ChatColor.GOLD + " -=-=-=- -=-=- -=-");
						p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
						p.setLevel(lobbyTimer);
						gm.setLobbyTimer(lobbyTimer);
					}
				}
				if (lobbyTimer == 8) {
					for (Player p : gm.getArenaPlayers()) {
						p.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- " + ChatColor.BLUE + "Gortume starts in: 8" + ChatColor.GOLD + " -=-=-=- -=-=- -=-");
						p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
						p.setLevel(lobbyTimer);
						gm.setLobbyTimer(lobbyTimer);
					}
				}
				if (lobbyTimer == 7) {
					for (Player p : gm.getArenaPlayers()) {
						p.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- " + ChatColor.BLUE + "Gortume starts in: 7" + ChatColor.GOLD + " -=-=-=- -=-=- -=-");
						p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
						p.setLevel(lobbyTimer);
						gm.setLobbyTimer(lobbyTimer);
					}
				}
				if (lobbyTimer == 6) {
					for (Player p : gm.getArenaPlayers()) {
						p.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- " + ChatColor.BLUE + "Gortume starts in: 6" + ChatColor.GOLD + " -=-=-=- -=-=- -=-");
						p.playSound(p.getLocation(), Sound.CLICK,1, 1);
						p.setLevel(lobbyTimer);
						gm.setLobbyTimer(lobbyTimer);
					}
				}
				if (lobbyTimer == 5) {
					for (Player p : gm.getArenaPlayers()) {
						p.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- " + ChatColor.BLUE + "Gortume starts in: 5" + ChatColor.GOLD + " -=-=-=- -=-=- -=-");
						p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
						p.setLevel(lobbyTimer);
						gm.setLobbyTimer(lobbyTimer);
					}
				}
				if (lobbyTimer == 4) {
					for (Player p : gm.getArenaPlayers()) {
						p.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- " + ChatColor.BLUE + "Gortume starts in: 4" + ChatColor.GOLD + " -=-=-=- -=-=- -=-");
						p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
						p.setLevel(lobbyTimer);
						gm.setLobbyTimer(lobbyTimer);
					}
				}
				if (lobbyTimer == 3) {
					for (Player p : gm.getArenaPlayers()) {
						p.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- " + ChatColor.BLUE + "Gortume starts in: 3" + ChatColor.GOLD + " -=-=-=- -=-=- -=-");
						p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
						p.setLevel(lobbyTimer);
						gm.setLobbyTimer(lobbyTimer);
					}
				}
				if (lobbyTimer == 2) {
					for (Player p : gm.getArenaPlayers()) {
						p.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- " + ChatColor.BLUE + "Gortume starts in: 2" + ChatColor.GOLD + " -=-=-=- -=-=- -=-");
						p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
						p.setLevel(lobbyTimer);
						gm.setLobbyTimer(lobbyTimer);
					}
				}
				if (lobbyTimer == 1) {
					for (Player p : gm.getArenaPlayers()) {					
					p.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- " + ChatColor.BLUE + "Gortume starts in: 1" + ChatColor.GOLD + " -=-=-=- -=-=- -=-");
					p.playSound(p.getLocation(), Sound.CLICK, 1, 10);
						p.setLevel(lobbyTimer);
						gm.setLobbyTimer(lobbyTimer);
					}
				}else if (lobbyTimer == 0){

					for(Player p : gm.getArenaPlayers()){
						p.setLevel(0);
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 10);
					}				
					plugin.getServer().getScheduler().cancelTask(gm.getLobby());
					gm.setLobbyTimer(-1);
					gm.setLobby(false);
				
					gm.startGame();
				}
			}
		}
	}
}
