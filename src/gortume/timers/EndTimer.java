package com.eyeofender.gortume.timers;

import com.eyeofender.gortume.HideAndGo;
import com.eyeofender.gortume.game.GameManager;
import com.eyeofender.gortume.handlers.FireworkHandler;

public class EndTimer implements Runnable {

    private HideAndGo plugin;

    GameManager gm;

    public EndTimer(HideAndGo plugin, GameManager gm) {
        this.plugin = plugin;
        this.gm = gm;
    }

    @Override
    public void run() {
        if (this.gm.isEnd()) {
            int endTimer = gm.getEndTimer();
            if (this.gm.getEndTimer() != -1) {
                if (this.gm.getEndTimer() != 0) {
                    FireworkHandler fh = new FireworkHandler(plugin, gm);
                    fh.Shootfireworks();
                    endTimer--;
                    gm.setEndTimer(endTimer);
                } else {
                    gm.stopArena();

                    gm.setEndTimer(-1);
                    gm.setEnd(false);
                    plugin.getServer().getScheduler().cancelTask(gm.getEnd());
                }
            }
        }
    }
}
