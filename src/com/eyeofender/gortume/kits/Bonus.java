package com.eyeofender.gortume.kits;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.eyeofender.gortume.HideAndGo;
import com.eyeofender.gortume.game.GameManager;
import com.eyeofender.gortume.kits.bonus.ConfusionCookie;
import com.eyeofender.gortume.kits.bonus.GoldenApple;
import com.eyeofender.gortume.kits.bonus.InvisiblePie;
import com.eyeofender.gortume.kits.bonus.KnockbackStick;
import com.eyeofender.gortume.kits.bonus.SightPork;
import com.eyeofender.gortume.kits.bonus.SlownessSteak;
import com.eyeofender.gortume.kits.bonus.SpeedCarrot;

public class Bonus implements Listener {

    private static HideAndGo plugin;

    public static Bonus speedCarrot;
    public static Bonus invisiblePie;
    public static Bonus sightPork;
    public static Bonus slownessSteak;
    public static Bonus confusionCookie;
    public static Bonus goldenApple;
    public static Bonus knockbackStick;

    protected String name;
    protected ItemStack item;
    protected boolean consumeable;
    protected boolean hitable;

    protected Bonus(String name, boolean consumeable, boolean hitable) {
        this.name = name;
        this.consumeable = consumeable;
        this.hitable = hitable;
    }

    public ItemStack getItem() {
        return this.item;
    }

    protected void onConsume(Player player) {
    }

    protected void onPlayerHit(Player player) {
    }

    @EventHandler
    public void onItemConsume(PlayerInteractEvent event) {
        if (!consumeable) return;

        if (event.getAction() != Action.LEFT_CLICK_AIR && event.getAction() != Action.LEFT_CLICK_BLOCK) return;
        if (event.getItem() == null || !event.getItem().equals(item)) return;

        Player player = event.getPlayer();
        if (plugin.getInArena().contains(player)) {
            onConsume(player);
            player.getInventory().removeItem(item);
            getPlugin().sendMessage(player, "You have consumed a " + name + "!");
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!hitable) return;

        if (!(event.getDamager() instanceof Player)) return;
        if (!(event.getEntity() instanceof Player)) return;

        Player attacker = (Player) event.getDamager();
        Player attacked = (Player) event.getEntity();

        if (attacker.getInventory().getItemInHand() == null || !attacker.getInventory().getItemInHand().equals(item)) return;

        if (plugin.getInArena().contains(attacker)) {
            GameManager gm = plugin.getPlayersGame(attacker);
            event.setCancelled(true);

            if (plugin.getInArena().contains(attacked) && gm == plugin.getPlayersGame(attacked)) {
                if (gm.isInLobby() || gm.getSpec().contains(attacked)) return;

                if (gm.getSpec().contains(attacker)) {
                    plugin.sendMessage(attacker, "You can not use items when you are spectating.");
                }

                onPlayerHit(attacked);
                attacker.getInventory().removeItem(item);
                plugin.sendMessage(attacker, "You have given " + ChatColor.BLUE + attacked.getName() + ChatColor.GRAY + " the " + name + "!");
                plugin.sendMessage(attacked, ChatColor.RED + attacker.getName() + ChatColor.GRAY + " has given you the " + name + "!");
            } else {
                plugin.sendMessage(attacker, "You can only hit people in your arena.");
            }
        }
    }

    public static void init(HideAndGo plugin) {
        Bonus.plugin = plugin;
        speedCarrot = registerBonus(new SpeedCarrot());
        invisiblePie = registerBonus(new InvisiblePie());
        sightPork = registerBonus(new SightPork());
        slownessSteak = registerBonus(new SlownessSteak());
        confusionCookie = registerBonus(new ConfusionCookie());
        goldenApple = registerBonus(new GoldenApple());
        knockbackStick = registerBonus(new KnockbackStick());
    }

    private static Bonus registerBonus(Bonus bonus) {
        Bukkit.getPluginManager().registerEvents(bonus, plugin);
        return bonus;
    }

    protected static HideAndGo getPlugin() {
        return Bonus.plugin;
    }

}
