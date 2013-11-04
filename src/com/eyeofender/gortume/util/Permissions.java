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
	
	/** Kit Permissions **/
	public Permission ninja = new Permission("gortume.ninja");
	public Permission traveler = new Permission("gortume.traveler");
	public Permission tank = new Permission("gortume.tank");
	public Permission spy = new Permission("gortume.tank");
	public Permission god = new Permission("gortume.tank");
	
	
	public void enablePermissions(){
		PluginManager pm = plugin.getServer().getPluginManager();
		pm.addPermission(wand);
		pm.addPermission(ninja);
		pm.addPermission(traveler);
		pm.addPermission(tank);
		pm.addPermission(spy);
		pm.addPermission(god);
	}
	
	public void disablePermissions(){
		PluginManager pm = plugin.getServer().getPluginManager();
		pm.removePermission(wand);
		pm.removePermission(ninja);
		pm.removePermission(traveler);
		pm.removePermission(tank);
		pm.removePermission(spy);
		pm.removePermission(god);

	}
}
