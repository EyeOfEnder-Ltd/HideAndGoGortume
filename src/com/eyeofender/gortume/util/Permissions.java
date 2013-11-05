package com.eyeofender.gortume.util;

import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;

import com.eyeofender.gortume.HideAndGo;

public class Permissions {

    private HideAndGo plugin;

    public Permissions(HideAndGo plugin) {
        this.plugin = plugin;
    }

    /** Signs Permissions **/
    public Permission wand = new Permission("gortume.tool.wand");

    public void enablePermissions() {
        PluginManager pm = plugin.getServer().getPluginManager();
        pm.addPermission(wand);
    }

    public void disablePermissions() {
        PluginManager pm = plugin.getServer().getPluginManager();
        pm.removePermission(wand);
    }
}
