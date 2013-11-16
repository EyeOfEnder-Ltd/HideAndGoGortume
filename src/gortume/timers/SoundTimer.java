package com.eyeofender.gortume.timers;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.eyeofender.gortume.HideAndGo;
import com.eyeofender.gortume.game.GameManager;

public class SoundTimer implements Runnable {

    private HideAndGo plugin;

    private GameManager gm;

    public SoundTimer(HideAndGo plugin, GameManager gm) {
        this.plugin = plugin;
        this.gm = gm;
    }

    @Override
    public void run() {
        if (gm.isSound()) {
            int soundTimer = gm.getSoundTimer();
            if (gm.getSoundTimer() != 0) {
                soundTimer--;
                gm.setSoundTimer(soundTimer);
            } else {

                gm.setSoundTimer(plugin.getConfigHelper().getSoundTimer());

                for (Player player : gm.getArenaPlayers()) {
                    if (!gm.getSpec().contains(player) || gm.getGortumePlayer() != player) {
                        player.playSound(player.getLocation(), Sound.AMBIENCE_CAVE, 10, 10);
                    }
                }
            }
        }
    }
}
