package com.eyeofender.gortume.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

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
                            event.setLine(0, ""  + ChatColor.GREEN + ChatColor.BOLD + "[Join]");
                        } else {
                            event.setLine(0, ""  + ChatColor.RED + ChatColor.BOLD + "[Full]");
                        }
                    } else {
                        event.setLine(0, ""  + ChatColor.RED + ChatColor.BOLD + "[In-Game]");
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
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
     
    if(e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.SIGN_POST || e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.SIGN || e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.WALL_SIGN){
     
    	Block block = e.getClickedBlock();
    	Sign sign = (Sign) block.getState();
     
    		if(sign.getLine(0).equalsIgnoreCase(""  + ChatColor.GREEN + ChatColor.BOLD + "[Join]")){
    			String arena = sign.getLine(1);
    			
    			GameManager gm = plugin.getGameManager(arena);
    			
    			if(gm != null){
    				Player player = e.getPlayer();
    				
    		        player.getInventory().clear();
    				player.getInventory().clear();
    				player.getInventory().setHelmet(null);
    				player.getInventory().setChestplate(null);
    				player.getInventory().setLeggings(null);
    				player.getInventory().setBoots(null);
    				
    				player.getInventory().remove(Material.COMPASS);
    				player.getInventory().remove(Material.WATCH);
    				player.getInventory().remove(Material.DIAMOND_HELMET);

    				player.getInventory().setContents(player.getInventory().getContents());

    				gm.joinArena(player);
    			}else{
    				plugin.sendMessage(e.getPlayer(), "Arena could not be found.");
    			}
    		}
    	
    	}
    }

}
