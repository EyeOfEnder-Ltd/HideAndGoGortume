package com.eyeofender.gortume.kits.bonus;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.eyeofender.gortume.game.GameManager;
import com.eyeofender.gortume.kits.Bonus;

public class SightPork extends Bonus {

    public SightPork() {
        super("Sight Pork", true, false);

        item = new ItemStack(Material.PORK, 1);
        ItemMeta im = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GREEN + "Left click to gain visiblity.");
        im.setLore(lore);
        im.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + "Sight Pork");
        item.setItemMeta(im);
    }

    @Override
    protected void onConsume(final Player player) {
    	GameManager gm = super.getPlugin().getPlayersGame(player);
    	
    	if(!gm.isInLobby()){
    		if(gm.getAlive().contains(player)){
    	    	player.removePotionEffect(PotionEffectType.BLINDNESS);
    	        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 200, 2));

    			Bukkit.getScheduler().runTaskLater(super.getPlugin(), new Runnable() {
    			    @Override
    			    public void run() {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1000000, 1));
    			    }
    			}, 6 * 20);    		
    		}else{
    			super.getPlugin().sendError(player, "You can not use items if you are not alive.");
    		}
    	}else{
    		super.getPlugin().sendError(player, "You can not use items if you are not in game.");
    	}
    }

}
