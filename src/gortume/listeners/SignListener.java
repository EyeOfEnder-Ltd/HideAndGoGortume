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
        }else if(event.getLine(0).equalsIgnoreCase("[Pass]")){
        	 if(event.getLine(1).isEmpty()){
        		if (event.getLine(2).isEmpty()) {
                   if (event.getLine(3).isEmpty()) { 
                	   event.setLine(0, ""+ ChatColor.GREEN + ChatColor.BOLD + "[Pass]");
                	   event.setLine(1, "" + ChatColor.GOLD + ChatColor.BOLD + "Right Click to");
                	   event.setLine(2, "" + ChatColor.GOLD + ChatColor.BOLD + "use a");
                	   event.setLine(3, "" + ChatColor.GOLD + ChatColor.BOLD + "gortume pass");
                   }
        	   }
        	}
        }else if(event.getLine(0).equalsIgnoreCase("[Kit]")){
        	if(event.getLine(1).equalsIgnoreCase("God")){
         	   event.setLine(0, ""+ ChatColor.GREEN + ChatColor.BOLD + "[Kit]");
         	   event.setLine(1, "" + ChatColor.GOLD + ChatColor.BOLD + "God");
        	}else if(event.getLine(1).equalsIgnoreCase("Spy")){
          	   event.setLine(0, ""+ ChatColor.GREEN + ChatColor.BOLD + "[Kit]");
          	   event.setLine(1, "" + ChatColor.GOLD + ChatColor.BOLD + "Spy");
        	}else if(event.getLine(1).equalsIgnoreCase("Tank")){
           	   event.setLine(0, ""+ ChatColor.GREEN + ChatColor.BOLD + "[Kit]");
           	   event.setLine(1, "" + ChatColor.GOLD + ChatColor.BOLD + "Tank");
         	}else if(event.getLine(1).equalsIgnoreCase("Ninja")){
            	   event.setLine(0, ""+ ChatColor.GREEN + ChatColor.BOLD + "[Kit]");
               	   event.setLine(1, "" + ChatColor.GOLD + ChatColor.BOLD + "Ninja");
            }else if(event.getLine(1).equalsIgnoreCase("Travler")){
            	   event.setLine(0, ""+ ChatColor.GREEN + ChatColor.BOLD + "[Kit]");
               	   event.setLine(1, "" + ChatColor.GOLD + ChatColor.BOLD + "Travler");
            }else{
            	event.setLine(0, "" + ChatColor.RED + ChatColor.BOLD + "[Kit]");
            	plugin.sendMessage(event.getPlayer(), "Kit not found.");
            }
        }
    }
}
