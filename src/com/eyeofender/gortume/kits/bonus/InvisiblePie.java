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

public class InvisiblePie extends Bonus {

    public InvisiblePie() {
        super("Invisible Pie", true, false);

        item = new ItemStack(Material.PUMPKIN_PIE, 1);
        ItemMeta im = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GREEN + "Left click to go invisible.");
        im.setLore(lore);
        im.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + "Invisible Pie");
        item.setItemMeta(im);
    }

    @Override
    protected void onConsume(Player player) {
    	GameManager gm = super.getPlugin().getPlayersGame(player);
    	
    	if(!gm.isInLobby()){
    		if(gm.getAlive().contains(player)){
    	        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 200, 2));
    		}else{
    			super.getPlugin().sendError(player, "You can not use items if you are not alive.");
    		}
    	}else{
    		super.getPlugin().sendError(player, "You can not use items if you are not in game.");
    	}
    }

}
