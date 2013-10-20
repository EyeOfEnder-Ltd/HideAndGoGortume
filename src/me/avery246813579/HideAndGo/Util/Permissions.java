package me.avery246813579.HideAndGo.Util;

import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;

import me.avery246813579.HideAndGo.HideAndGo;

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
