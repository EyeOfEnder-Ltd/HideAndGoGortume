package com.eyeofender.gortume.kits;

import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.potion.PotionEffect;

import com.eyeofender.gortume.util.Metadata;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Kit {

    private static Map<String, Kit> byName = Maps.newHashMap();

    private String name;
    private Permission permission;
    private List<ItemStack> items;
    private List<PotionEffect> effects;
    private double maxHealth;

    static {
        Kit kit = new Kit("Travler");
        kit.addItems(Bonus.speedCarrot.getItem());
        kit.save();

        kit = new Kit("Ninja");
        kit.addItems(Bonus.speedCarrot.getItem(), Bonus.invisiblePie.getItem(), Bonus.confusionCookie.getItem(), Bonus.slownessSteak.getItem());
        kit.save();

        kit = new Kit("Tank");
        kit.addItems(Bonus.slownessSteak.getItem(), Bonus.confusionCookie.getItem(), Bonus.knockbackStick.getItem(), Bonus.goldenApple.getItem());
        kit.save();

        kit = new Kit("Spy");
        kit.addItems(Bonus.speedCarrot.getItem(), Bonus.invisiblePie.getItem(), Bonus.confusionCookie.getItem());
        kit.save();

        kit = new Kit("God");
        kit.addItems(Bonus.speedCarrot.getItem(), Bonus.invisiblePie.getItem(), Bonus.sightPork.getItem(), Bonus.slownessSteak.getItem(), Bonus.confusionCookie.getItem(), Bonus.goldenApple.getItem(),
                Bonus.knockbackStick.getItem());
        kit.save();
    }

    public Kit(String name) {
        this.name = name;

        String perm = "gortume.kit." + name.toLowerCase();
        permission = new Permission(perm, "Permission for the kit: " + name + ".", PermissionDefault.TRUE);
        try {
            Bukkit.getServer().getPluginManager().addPermission(permission);
        } catch (Exception e) {
        }

        this.items = Lists.newArrayList();
        this.effects = Lists.newArrayList();
        this.maxHealth = 20;

        byName.put(name.toLowerCase(), this);
    }

    public void save() {
        byName.put(name.toLowerCase(), this);
    }

    public static Kit getByName(String name) {
        return byName.get(name.toLowerCase());
    }

    public void equip(Player player) {
        if (player.hasPermission(this.permission)) {
            player.sendMessage(ChatColor.RED + "You do not have permission for kit " + ChatColor.GOLD + this.name + ChatColor.RED + "");
            return;
        }

        PlayerInventory inv = player.getInventory();

        Metadata.set(player, "kit", this.name);

        // Inventory
        inv.setContents(items.toArray(new ItemStack[items.size()]));

        // Effects
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.addPotionEffect(new PotionEffect(effect.getType(), 0, 0), true);
        }

        for (PotionEffect effect : effects) {
            player.addPotionEffect(effect, true);
        }

        // Health
        player.setMaxHealth(maxHealth);
        player.setHealth(maxHealth);

        player.sendMessage(ChatColor.GRAY + "You have recieved kit " + ChatColor.BLUE + name + ChatColor.GRAY + ".");
    }

    public String getName() {
        return name;
    }

    public Permission getPermission() {
        return permission;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public void addItems(ItemStack... items) {
        this.items.addAll(Lists.newArrayList(items));
    }

    public List<PotionEffect> getEffects() {
        return effects;
    }

    public void setEffects(List<PotionEffect> effects) {
        this.effects = effects;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

}
