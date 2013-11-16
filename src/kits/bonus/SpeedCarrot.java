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
import com.google.common.collect.Lists;

public class SpeedCarrot extends Bonus {

    public SpeedCarrot() {
        super("Speed Carrot", true, false);

        item = new ItemStack(Material.CARROT_ITEM, 1);
        ItemMeta im = item.getItemMeta();
        ArrayList<String> lore = Lists.newArrayList();
        lore.add(ChatColor.GREEN + "Left click to gain speed.");
        im.setLore(lore);
        im.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Speed Carrot");
        item.setItemMeta(im);
    }

    @Override
    protected void onConsume(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 2));
    }

}
