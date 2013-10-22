package com.eyeofender.gortume.handlers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.eyeofender.gortume.HideAndGo;

public class FileHandler {
	
	private HideAndGo plugin;
	
	public FileHandler(HideAndGo plugin){
		this.plugin = plugin;
	}

	public void configSave(){
		plugin.getConfig().options().copyDefaults(true);
		getArena().options().copyDefaults(true);
		plugin.saveConfig();
		saveArena();
	}
	
	private FileConfiguration arena = null;
	private File arenaFile = null;
	
	public void reloadArena() {
	    if (arenaFile == null) {
	    	arenaFile = new File(plugin.getDataFolder(), "arena.yml");
	    }
	    arena = YamlConfiguration.loadConfiguration(arenaFile);
	 
	    // Look for defaults in the jar
	    InputStream defConfigStream = plugin.getResource("arena.yml");
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        arena.setDefaults(defConfig);
	    }
	}
	
	public FileConfiguration getArena() {
	    if (arena == null) {
	        this.reloadArena();
	    }
	    return arena;
	}
	
	public void saveArena() {
	    if (arena == null || arenaFile == null) {
	    return;
	    }
	    try {
	        getArena().save(arenaFile);
	    } catch (IOException ex) {
	        plugin.getLogger().log(Level.SEVERE, "Could not save config to " + arenaFile, ex);
	    }
	}
	
	public void saveDefaultArena() {
	    if (arenaFile == null) {
	        arenaFile = new File(plugin.getDataFolder(), "arena.yml");
	    }
	    if (!arenaFile.exists()) {            
	         this.plugin.saveResource("arena.yml", false);
	     }
	}
}
