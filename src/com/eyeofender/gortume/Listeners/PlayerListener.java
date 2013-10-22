package com.eyeofender.gortume.Listeners;

import java.sql.SQLException;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.eyeofender.gortume.HideAndGo;

public class PlayerListener implements Listener {

    private HideAndGo plugin;

    public PlayerListener(HideAndGo plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws SQLException {

    }
}
