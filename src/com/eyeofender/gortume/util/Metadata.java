package com.eyeofender.gortume.util;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import com.eyeofender.gortume.kits.Kit;

public class Metadata {

    private static Plugin plugin;

    private Metadata() {
    }

    public static void init(Plugin plugin) {
        Metadata.plugin = plugin;
    }

    public static boolean getBoolean(Player player, String key) {
        MetadataValue value = getValue(player, key);
        if (value != null) return value.asBoolean();
        return false;
    }

    public static int getInt(Player player, String key) {
        MetadataValue value = getValue(player, key);
        if (value != null) return value.asInt();
        return 0;
    }

    public static Kit getKit(Player player) {
        return Kit.getByName(getString(player, "kit"));
    }

    public static String getString(Player player, String key) {
        MetadataValue value = getValue(player, key);
        if (value != null) return value.asString();
        return null;
    }

    public static void remove(Player player, String key) {
        player.removeMetadata(key, plugin);
    }

    public static void set(Player player, String key, Object value) {
        player.setMetadata(key, new FixedMetadataValue(plugin, value));
    }

    private static MetadataValue getValue(Player player, String key) {
        List<MetadataValue> values = player.getMetadata(key);
        String bnName = plugin.getDescription().getName();

        for (MetadataValue value : values) {
            String owner = value.getOwningPlugin().getDescription().getName();
            if (owner == bnName) return value;
        }

        return null;
    }

}
