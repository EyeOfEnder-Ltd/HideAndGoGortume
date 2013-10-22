package com.eyeofender.gortume.util;

import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;

import com.eyeofender.gortume.HideAndGo;

public class Permissions {

	private HideAndGo plugin;
	
	public Permissions (HideAndGo plugin){
		this.plugin = plugin;
	}
	
	/***************************************************************************\
	 * 
	 *                         Permissions
	 *
	/****************************************************************************/
	
	
	/** Group Permissions **/
	
	/** Signs Permissions **/
	public Permission wand = new Permission("gortume.wand");
	
	
	public void enablePermissions(){
		PluginManager pm = plugin.getServer().getPluginManager();
		pm.addPermission(wand);
	}
}
