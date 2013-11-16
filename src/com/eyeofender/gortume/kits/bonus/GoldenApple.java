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

public class GoldenApple extends Bonus {

    public GoldenApple() {
        super("Golden Apple", true, false);

        item = new ItemStack(Material.GOLDEN_APPLE, 1);
        ItemMeta im = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GREEN + "Left click to gain health, healing, and resistance.");
        im.setLore(lore);
        im.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + "Golden Apple");
        item.setItemMeta(im);
    }

    @Override
    protected void onConsume(Player player) {
    	GameManager gm = super.getPlugin().getPlayersGame(player);
    	
    	if(!gm.isInLobby()){
    		if(gm.getAlive().contains(player)){
    			player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 200, 5));
    	        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 5));
    	        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 5));    
    		}else{
    			super.getPlugin().sendError(player, "You can not use items if you are not alive.");
    		}
    	}else{
    		super.getPlugin().sendError(player, "You can not use items if you are not in game.");
    	}
    	
    }

}
