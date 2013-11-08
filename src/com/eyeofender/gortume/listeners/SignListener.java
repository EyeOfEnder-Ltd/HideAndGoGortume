package com.eyeofender.gortume.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import com.eyeofender.gortume.HideAndGo;
import com.eyeofender.gortume.game.GameManager;

public class SignListener implements Listener {

    HideAndGo plugin;

    public SignListener(HideAndGo plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSignCreate(SignChangeEvent event) {
        if (event.getLine(0).equalsIgnoreCase("[Gortume]")) {
            if (event.getLine(2).isEmpty()) {
                if (event.getLine(3).isEmpty()) {
                    plugin.sendMessage(event.getPlayer(), "You have created a sign.");

                    GameManager gm = plugin.getGameManager(event.getLine(1));

                    if (gm.isInLobby()) {
                        if (gm.getArenaPlayers().size() < plugin.getConfigHelper().getMaxPlayers()) {
                            event.setLine(0, "" + ChatColor.GREEN + ChatColor.BOLD + "[Join]");
                        } else {
                            event.setLine(0, "" + ChatColor.RED + ChatColor.BOLD + "[Full]");
                        }
                    } else {
                        event.setLine(0, "" + ChatColor.RED + ChatColor.BOLD + "[In-Game]");
                    }

                    event.setLine(1, gm.getArenaName());
                    event.setLine(2, ChatColor.GRAY + "" + ChatColor.BOLD + gm.getArenaPlayers().size() + "/" + plugin.getConfigHelper().getMaxPlayers());

                    if (gm.isInLobby()) {
                        event.setLine(3, "" + ChatColor.GOLD + ChatColor.BOLD + "-=- Lobby -=-");
                    } else {
                        event.setLine(3, "" + ChatColor.GRAY + ChatColor.BOLD + "-=- In-Game -=-");
                    }

                    gm.getArena().addSignLocation(event.getBlock().getLocation());

                    gm.getArena().updateSigns();
                } else {
                    plugin.sendMessage(event.getPlayer(), "Sign creation failed. Line 2 and 3 have to be empty.");
                    event.setLine(0, ChatColor.DARK_RED + event.getLine(0));
                }
            } else {
                plugin.sendMessage(event.getPlayer(), "Sign creation failed. Line 2 and 3 have to be empty.");
                event.setLine(0, ChatColor.DARK_RED + event.getLine(0));
            }
        }
    }
}
