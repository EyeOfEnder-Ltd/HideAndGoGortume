package com.eyeofender.gortume.timers;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.eyeofender.gortume.HideAndGo;
import com.eyeofender.gortume.game.GameManager;

public class GortumeTimer implements Runnable {

	private HideAndGo plugin;
	private GameManager gm;
	
	public GortumeTimer (HideAndGo plugin, GameManager gm){
		this.plugin = plugin;
		this.gm = gm;
	}
	
	@Override
	public void run() {
		if(this.gm.isGortume()){
			int gortumeTimer = gm.getGortumeTimer();
			if(this.gm.getGortumeTimer() != -1){
				if (gortumeTimer != 0 || gortumeTimer != 10 || gortumeTimer != 9 || gortumeTimer != 8 || gortumeTimer != 7 || gortumeTimer != 6|| gortumeTimer != 5 || gortumeTimer != 4|| gortumeTimer != 3 || gortumeTimer != 2 || gortumeTimer != 1) {
					gortumeTimer--;
					for (Player p : gm.getArenaPlayers()) {
						p.setLevel(gortumeTimer);
					}
					gm.setGortumeTimer(gortumeTimer);
				}
				if (gortumeTimer == 10) {
					for (Player p : gm.getArenaPlayers()) {
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage(ChatColor.GOLD + "=-=-=-=-= =-=-=-=-= =-=-=-=-= =-=-=-=-=");
						p.sendMessage("");
						p.sendMessage(ChatColor.DARK_BLUE + "                 Game of");
						p.sendMessage("");
						p.sendMessage(ChatColor.GOLD + "=-=-=-=-= =-=-=-=-= =-=-=-=-= =-=-=-=-=");
						p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
						p.setLevel(gortumeTimer);
						gm.setGortumeTimer(gortumeTimer);
					}
				}
				if (gortumeTimer == 9) {
					for (Player p : gm.getArenaPlayers()) {
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage(ChatColor.GOLD + "=-=-=-=-= =-=-=-=-= =-=-=-=-= =-=-=-=-=");
						p.sendMessage("");
						p.sendMessage(ChatColor.DARK_BLUE + "        Hide And");
						p.sendMessage("");
						p.sendMessage(ChatColor.GOLD + "=-=-=-=-= =-=-=-=-= =-=-=-=-= =-=-=-=-=");
						p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
						p.setLevel(gortumeTimer);
						gm.setGortumeTimer(gortumeTimer);
					}
				}
				if (gortumeTimer == 8) {
					for (Player p : gm.getArenaPlayers()) {
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage(ChatColor.GOLD + "=-=-=-=-= =-=-=-=-= =-=-=-=-= =-=-=-=-=");
						p.sendMessage("");
						p.sendMessage(ChatColor.DARK_BLUE + "        Hide And Go Gortume");
						p.sendMessage("");
						p.sendMessage(ChatColor.GOLD + "=-=-=-=-= =-=-=-=-= =-=-=-=-= =-=-=-=-=");
						p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
						p.setLevel(gortumeTimer);
						gm.setGortumeTimer(gortumeTimer);
					}
				}
				if (gortumeTimer == 7) {
					for (Player p : gm.getArenaPlayers()) {
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage(ChatColor.GOLD + "=-=-=-=-= =-=-=-=-= =-=-=-=-= =-=-=-=-=");
						p.sendMessage("");
						p.sendMessage(ChatColor.DARK_BLUE + "                Starts");
						p.sendMessage("");
						p.sendMessage(ChatColor.GOLD+ "=-=-=-=-= =-=-=-=-= =-=-=-=-= =-=-=-=-=");
						p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
						p.setLevel(gortumeTimer);
						gm.setGortumeTimer(gortumeTimer);
					}
				}
				if (gortumeTimer == 6) {
					for (Player p : gm.getArenaPlayers()) {
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage(ChatColor.GOLD + "=-=-=-=-= =-=-=-=-= =-=-=-=-= =-=-=-=-=");
						p.sendMessage("");
						p.sendMessage(ChatColor.DARK_BLUE + "                Starts in");
						p.sendMessage("");
						p.sendMessage(ChatColor.GOLD + "=-=-=-=-= =-=-=-=-= =-=-=-=-= =-=-=-=-=");
						p.playSound(p.getLocation(), Sound.CLICK,1, 1);
						p.setLevel(gortumeTimer);
						gm.setGortumeTimer(gortumeTimer);
					}
				}
				if (gortumeTimer == 5) {
					for (Player p : gm.getArenaPlayers()) {
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage(ChatColor.GOLD + "=-=-=-=-= =-=-=-=-= =-=-=-=-= =-=-=-=-=");
						p.sendMessage("");
						p.sendMessage(ChatColor.DARK_BLUE + "                Starts in 5");
						p.sendMessage("");
						p.sendMessage(ChatColor.GOLD + "=-=-=-=-= =-=-=-=-= =-=-=-=-= =-=-=-=-=");
						p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
						p.setLevel(gortumeTimer);
						gm.setGortumeTimer(gortumeTimer);
					}
				}
				if (gortumeTimer == 4) {
					for (Player p : gm.getArenaPlayers()) {
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage(ChatColor.GOLD + "=-=-=-=-= =-=-=-=-= =-=-=-=-= =-=-=-=-=");
						p.sendMessage("");
						p.sendMessage(ChatColor.DARK_BLUE + "                Starts in 4");
						p.sendMessage("");
						p.sendMessage(ChatColor.GOLD + "=-=-=-=-= =-=-=-=-= =-=-=-=-= =-=-=-=-=");
						p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
						p.setLevel(gortumeTimer);
						gm.setGortumeTimer(gortumeTimer);
					}
				}
				if (gortumeTimer == 3) {
					for (Player p : gm.getArenaPlayers()) {
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage(ChatColor.GOLD + "=-=-=-=-= =-=-=-=-= =-=-=-=-= =-=-=-=-=");
						p.sendMessage("");
						p.sendMessage(ChatColor.DARK_BLUE + "                Starts in 3");
						p.sendMessage("");
						p.sendMessage(ChatColor.GOLD + "=-=-=-=-= =-=-=-=-= =-=-=-=-= =-=-=-=-=");
						p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
						p.setLevel(gortumeTimer);
						gm.setGortumeTimer(gortumeTimer);
					}
				}
				if (gortumeTimer == 2) {
					for (Player p : gm.getArenaPlayers()) {
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage(ChatColor.GOLD + "=-=-=-=-= =-=-=-=-= =-=-=-=-= =-=-=-=-=");
						p.sendMessage("");
						p.sendMessage(ChatColor.DARK_BLUE + "                Starts in 2");
						p.sendMessage("");
						p.sendMessage(ChatColor.GOLD + "=-=-=-=-= =-=-=-=-= =-=-=-=-= =-=-=-=-=");
						p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
						p.setLevel(gortumeTimer);
						gm.setGortumeTimer(gortumeTimer);
					}
				}
				if (gortumeTimer == 1) {
					for (Player p : gm.getArenaPlayers()) {
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage("");
						p.sendMessage(ChatColor.GOLD + "=-=-=-=-= =-=-=-=-= =-=-=-=-= =-=-=-=-=");
						p.sendMessage("");
						p.sendMessage(ChatColor.DARK_BLUE + "                Starts in 1");
						p.sendMessage("");
						p.sendMessage(ChatColor.GOLD + "=-=-=-=-= =-=-=-=-= =-=-=-=-= =-=-=-=-=");
						p.playSound(p.getLocation(), Sound.CLICK, 1, 10);
						p.setLevel(gortumeTimer);
						gm.setGortumeTimer(gortumeTimer);
					}
				}if (gortumeTimer == 0){
					for(Player p : gm.getArenaPlayers()){
						p.setLevel(0);
						p.playSound(p.getLocation(), Sound.ANVIL_LAND, 1, 10);
					}
					plugin.getServer().getScheduler().cancelTask(gm.getGortume());
					gm.setGortumeTimer(-1);
					gm.setGortume(false);
					gm.autoPlace();

				}
			}
		}
	}
}
