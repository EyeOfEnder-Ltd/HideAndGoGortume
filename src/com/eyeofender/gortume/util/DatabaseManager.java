package com.eyeofender.gortume.util;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.bukkit.entity.Player;

import com.eyeofender.gortume.HideAndGo;

public class DatabaseManager {

    private HideAndGo plugin;

    public DatabaseManager(HideAndGo plugin) {
        this.plugin = plugin;

        try {
            plugin.getDatabase().find(Passes.class).findRowCount();
        } catch (PersistenceException ex) {
            plugin.log().info("Installing database tables due to first time usage...");
            plugin.installDDL();
        }
    }

    public Passes getPasses(Player player) {
        Passes passes = plugin.getDatabase().find(Passes.class).where().ieq("name", player.getName()).findUnique();

        if (passes == null) {
            plugin.log().info("Creating pass entry for " + player.getName());
            passes = new Passes();
            passes.setName(player.getName());
            passes.setPasses(1);
            plugin.getDatabase().save(passes);
        }

        return passes;
    }

    public void savePasses(Passes passes) {
        plugin.getDatabase().update(passes);
    }

    public static List<Class<?>> getDatabaseClasses() {
        List<Class<?>> list = new ArrayList<Class<?>>();
        list.add(Passes.class);
        return list;
    }

}
