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
            plugin.installDDL();
        }
    }

    public Passes getPasses(Player player) {
        return plugin.getDatabase().find(Passes.class).where().ieq("name", player.getName()).findUnique();
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
