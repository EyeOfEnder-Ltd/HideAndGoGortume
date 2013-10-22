package com.eyeofender.gortume.Handlers;

import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import com.eyeofender.gortume.HideAndGo;
import com.eyeofender.gortume.Game.GameManager;

public class FireworkHandler {

    HideAndGo plugin;
    GameManager gm;

    public FireworkHandler(HideAndGo instance, GameManager gm) {
        plugin = instance;
        this.gm = gm;
    }

    public void Shootfireworks() {
        for (Player player : gm.getArenaPlayers()) {
            Firework fw = (Firework) player.getWorld().spawn(player.getLocation(), Firework.class);
            FireworkMeta fm = fw.getFireworkMeta();
            Random r = new Random();
            int fType = r.nextInt(5) + 1;
            Type type = null;
            switch (fType) {
                default:
                case 1:
                    type = Type.BALL;
                    break;
                case 2:
                    type = Type.BALL_LARGE;
                    break;
                case 3:
                    type = Type.BURST;
                    break;
                case 4:
                    type = Type.CREEPER;
                    break;
                case 5:
                    type = Type.STAR;
                    break;
            }
            int c1i = r.nextInt(16) + 1;
            int c2i = r.nextInt(16) + 1;
            Color c1 = getColour(c1i);
            Color c2 = getColour(c2i);
            FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();
            fm.addEffect(effect);
            int Power = r.nextInt(2) + 1;
            fm.setPower(Power);
            fw.setFireworkMeta(fm);
        }

    }

    public Color getColour(int c) {
        switch (c) {
            default:
            case 1:
                return Color.AQUA;
            case 2:
                return Color.BLACK;
            case 3:
                return Color.BLUE;
            case 4:
                return Color.FUCHSIA;
            case 5:
                return Color.GRAY;
            case 6:
                return Color.GREEN;
            case 7:
                return Color.LIME;
            case 8:
                return Color.MAROON;
            case 9:
                return Color.NAVY;
            case 10:
                return Color.OLIVE;
            case 11:
                return Color.PURPLE;
            case 12:
                return Color.RED;
            case 13:
                return Color.SILVER;
            case 14:
                return Color.TEAL;
            case 15:
                return Color.WHITE;
            case 16:
                return Color.YELLOW;
        }
    }
}