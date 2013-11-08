package com.eyeofender.gortume.game;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.eyeofender.gortume.HideAndGo;

public class ArenaCreator {

    private HideAndGo plugin;

    public ArenaCreator(HideAndGo plugin) {
        this.plugin = plugin;
    }

    public void createArena(Player player, String upperCaseName) {
        String name = upperCaseName.toLowerCase();
        if (plugin.getFc().getArena().contains(name)) {
            plugin.sendMessage(player, "Arena is already created.");
            return;
        }

        plugin.getFc().getArena().createSection(name);
        plugin.getFc().getArena().getConfigurationSection(name).set("Disabled", false);
        plugin.getFc().saveArena();
        plugin.sendMessage(player, "Created arena " + name + ". Do /G Set Lobby to set the lobby spawn.");
    }

    public void setLobby(Player player, String upperCaseName) {
        String name = upperCaseName.toLowerCase();
        if (plugin.getFc().getArena().contains(name)) {

            Location l = player.getLocation();

            plugin.getFc().getArena().getConfigurationSection(name).set("lobbyWorld", l.getWorld().getName());
            plugin.getFc().getArena().getConfigurationSection(name).set("lobbySpawn.x", l.getBlockX());
            plugin.getFc().getArena().getConfigurationSection(name).set("lobbySpawn.y", l.getBlockY());
            plugin.getFc().getArena().getConfigurationSection(name).set("lobbySpawn.z", l.getBlockZ());
            plugin.getFc().getArena().getConfigurationSection(name).set("lobbySpawn.yaw", l.getYaw());
            plugin.getFc().getArena().getConfigurationSection(name).set("lobbySpawn.pitch", l.getPitch());

            plugin.sendMessage(player, "You have updated " + name + "'s Lobby Spawn.");
            plugin.getFc().saveArena();

        } else {
            plugin.sendMessage(player, "Arena is not created.");
            return;
        }
    }

    public void setArenaWorld(Player player, String upperCaseName) {
        String name = upperCaseName.toLowerCase();
        if (plugin.getFc().getArena().contains(name)) {

            Location l = player.getLocation();

            plugin.getFc().getArena().getConfigurationSection(name).set("arenaWorld", l.getWorld().getName());

            plugin.sendMessage(player, "You have updated " + name + "'s Arenas world location.");
            plugin.getFc().saveArena();

        } else {
            plugin.sendMessage(player, "Arena is not created.");
            return;
        }
    }

    public void setRegularSpawn(Player player, String upperCaseName) {
        String name = upperCaseName.toLowerCase();
        if (plugin.getFc().getArena().contains(name)) {

            Location l = player.getLocation();

            if (l.getWorld().getName() == plugin.getFc().getArena().getConfigurationSection(name).getString("arenaWorld")) {
                plugin.getFc().getArena().getConfigurationSection(name).set("regularSpawn.x", l.getBlockX());
                plugin.getFc().getArena().getConfigurationSection(name).set("regularSpawn.y", l.getBlockY());
                plugin.getFc().getArena().getConfigurationSection(name).set("regularSpawn.z", l.getBlockZ());
                plugin.getFc().getArena().getConfigurationSection(name).set("regularSpawn.yaw", l.getYaw());
                plugin.getFc().getArena().getConfigurationSection(name).set("regularSpawn.pitch", l.getPitch());
                plugin.sendMessage(player, "You have updated " + name + "'s Regular Spawn Point.");
                plugin.getFc().saveArena();

            } else {
                if (plugin.getFc().getArena().getConfigurationSection(name).contains("arenaWorld")) {
                    plugin.sendMessage(player, "You have to be in the same world as you set the arena world.");
                    return;
                } else {
                    plugin.sendMessage(player, "You need to set the arena world by doing /G Set ArenaWorld");
                    return;
                }
            }
        } else {
            plugin.sendMessage(player, "Arena is not created.");
            return;
        }
    }

    public void setGortumeSpawn(Player player, String upperCaseName) {
        String name = upperCaseName.toLowerCase();
        if (plugin.getFc().getArena().contains(name)) {

            Location l = player.getLocation();

            if (l.getWorld().getName() == plugin.getFc().getArena().getConfigurationSection(name).getString("arenaWorld")) {
                plugin.getFc().getArena().getConfigurationSection(name).set("gortumeSpawn.x", l.getBlockX());
                plugin.getFc().getArena().getConfigurationSection(name).set("gortumeSpawn.y", l.getBlockY());
                plugin.getFc().getArena().getConfigurationSection(name).set("gortumeSpawn.z", l.getBlockZ());
                plugin.getFc().getArena().getConfigurationSection(name).set("gortumeSpawn.yaw", l.getYaw());
                plugin.getFc().getArena().getConfigurationSection(name).set("gortumeSpawn.pitch", l.getPitch());
                plugin.sendMessage(player, "You have updated " + name + "'s Gortume Spawn Point.");
                plugin.getFc().saveArena();

            } else {
                if (plugin.getFc().getArena().getConfigurationSection(name).contains("arenaWorld")) {
                    plugin.sendMessage(player, "You have to be in the same world as you set the arena world.");
                    return;
                } else {
                    plugin.sendMessage(player, "You need to set the arena world by doing /G Set ArenaWorld");
                    return;
                }
            }
        } else {
            plugin.sendMessage(player, "Arena is not created.");
            return;
        }
    }

    public void setEndLocation(Player player, String upperCaseName) {
        String name = upperCaseName.toLowerCase();
        if (plugin.getFc().getArena().contains(name)) {

            Location l = player.getLocation();

            plugin.getFc().getArena().getConfigurationSection(name).set("endLocation.world", l.getWorld().getName());
            plugin.getFc().getArena().getConfigurationSection(name).set("endLocation.x", l.getBlockX());
            plugin.getFc().getArena().getConfigurationSection(name).set("endLocation.y", l.getBlockY());
            plugin.getFc().getArena().getConfigurationSection(name).set("endLocation.z", l.getBlockZ());
            plugin.getFc().getArena().getConfigurationSection(name).set("endLocation.yaw", l.getYaw());
            plugin.getFc().getArena().getConfigurationSection(name).set("endLocation.pitch", l.getPitch());

            plugin.sendMessage(player, "You have updated " + name + "'s End location.");
            plugin.getFc().saveArena();

        } else {
            plugin.sendMessage(player, "Arena is not created.");
            return;
        }
    }

    public void setArena(Player player, String upperCaseName) {
        String name = upperCaseName.toLowerCase();
        if (plugin.getFc().getArena().contains(name)) {

            Location l = plugin.getLocation1();
            Location l2 = plugin.getLocation2();

            if (l != null) {
                if (l2 != null) {
                    if (l.getWorld().getName() == plugin.getFc().getArena().getConfigurationSection(name).getString("arenaWorld")) {
                        plugin.getFc().getArena().getConfigurationSection(name).set("location1.x", l.getBlockX());
                        plugin.getFc().getArena().getConfigurationSection(name).set("location1.y", 0);
                        plugin.getFc().getArena().getConfigurationSection(name).set("location1.z", l.getBlockZ());
                        plugin.getFc().getArena().getConfigurationSection(name).set("location2.x", l2.getBlockX());
                        plugin.getFc().getArena().getConfigurationSection(name).set("location2.y", 300);
                        plugin.getFc().getArena().getConfigurationSection(name).set("location2.z", l2.getBlockZ());
                        plugin.getFc().saveArena();
                        plugin.sendMessage(player, "You have set the arena.");
                    } else {
                        if (plugin.getFc().getArena().getConfigurationSection(name).contains("arenaWorld")) {
                            plugin.sendMessage(player, "You have to be in the same world as you set the arena world.");
                            return;
                        } else {
                            plugin.sendMessage(player, "You need to set the arena world by doing /G Set ArenaWorld");
                            return;
                        }
                    }
                } else {
                    plugin.sendMessage(player, "Second Location is not set.");
                }
            } else {
                plugin.sendMessage(player, "First Location is not set.");
            }
        } else {
            plugin.sendMessage(player, "Arena is not created.");
            return;
        }
    }

    public void setRandom(Player player, String upperCaseName) {
        String name = upperCaseName.toLowerCase();
        if (plugin.getFc().getArena().contains(name)) {

            int randoms = plugin.getFc().getArena().getConfigurationSection(name).getInt("randoms");
            randoms++;

            plugin.getFc().getArena().getConfigurationSection(name).set("random" + randoms + ".x", player.getLocation().getBlockX());
            plugin.getFc().getArena().getConfigurationSection(name).set("random" + randoms + ".y", player.getLocation().getBlockY());
            plugin.getFc().getArena().getConfigurationSection(name).set("random" + randoms + ".z", player.getLocation().getBlockZ());

            plugin.getFc().getArena().getConfigurationSection(name).set("randoms", randoms);

            plugin.sendMessage(player, "You have set the random emerald location. You have set a total of " + randoms + " Random spots.");

            plugin.getFc().saveArena();

        } else {
            plugin.sendMessage(player, "Arena is not created.");
            return;
        }
    }
}
