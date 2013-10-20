package me.avery246813579.HideAndGo.Listeners;

import java.sql.SQLException;
import java.sql.Statement;

import me.avery246813579.HideAndGo.HideAndGo;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener{

	private HideAndGo plugin;
	
	public PlayerListener (HideAndGo plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerJoin (PlayerJoinEvent event) throws SQLException{
		
	}
}
