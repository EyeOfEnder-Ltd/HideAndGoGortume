package com.eyeofender.gortume.kits.bonus;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.eyeofender.gortume.kits.Bonus;

public class ConfusionCookie extends Bonus {

    public ConfusionCookie() {
        super("Confusion Cookie", false, true);

        item = new ItemStack(Material.COOKIE, 1);
        ItemMeta im = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GREEN + "Left click a player to confuse them.");
        im.setLore(lore);
        im.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + "Confusion Cookie");
        item.setItemMeta(im);
    }

    @Override
    protected void onPlayerHit(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 400, 2));
    }

}
