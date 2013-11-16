package com.eyeofender.gortume.kits.bonus;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import com.eyeofender.gortume.game.GameManager;
import com.eyeofender.gortume.kits.Bonus;

public class KnockbackStick extends Bonus {

    public KnockbackStick() {
        super("Knockback Stick", false, true);

        item = new ItemStack(Material.STICK, 1);
        ItemMeta im = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GREEN + "Left click a player to knock them back.");
        im.setLore(lore);
        im.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + "Knockback Stick");
        item.setItemMeta(im);
    }

    @Override
    protected void onPlayerHit(Player player) {
    	GameManager gm = super.getPlugin().getPlayersGame(player);
    	
    	if(!gm.isInLobby()){
    		if(gm.getAlive().contains(player)){
    			Vector direction = player.getLocation().getDirection();
    	        player.setVelocity(direction.setY(-0.1).multiply(-1));
    	}else{
    			super.getPlugin().sendError(player, "You can not use items if you are not alive.");
    		}
    	}else{
    		super.getPlugin().sendError(player, "You can not use items if you are not in game.");
    	}
    }

}
