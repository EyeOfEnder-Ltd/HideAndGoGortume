package com.eyeofender.gortume.kits.bonus;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.eyeofender.gortume.game.GameManager;
import com.eyeofender.gortume.kits.Bonus;

public class SlownessSteak extends Bonus {

    public SlownessSteak() {
        super("Slowness Steak", false, true);

        item = new ItemStack(Material.COOKED_BEEF, 1);
        ItemMeta im = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GREEN + "Left click a player to give them slowness.");
        im.setLore(lore);
        im.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + "Slowness Steak");
        item.setItemMeta(im);
    }

    @Override
    protected void onPlayerHit(Player player) {
    	GameManager gm = super.getPlugin().getPlayersGame(player);
    	
    	if(!gm.isInLobby()){
    		if(gm.getAlive().contains(player)){
    	    	player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 2));
    		}else{
    			super.getPlugin().sendError(player, "You can not use items if you are not alive.");
    		}
    	}else{
    		super.getPlugin().sendError(player, "You can not use items if you are not in game.");
    	}
    }

}
