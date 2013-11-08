package com.eyeofender.gortume.handlers;

import java.util.ArrayList;
import java.util.List;

import com.eyeofender.gortume.HideAndGo;
import com.eyeofender.gortume.game.Arena;
import com.eyeofender.gortume.game.GameManager;

public class ConfigurationHandler {

    private HideAndGo plugin;

    /** Match Info **/
    private int maxPlayers = 16;
    private int minPlayers = 8;

    /** Timers **/
    private int gortumeTimer = 10;
    private int lobbyTime = 20;
    private int gameTimer = 600;
    private int endTimer = 10;

    private int batTimer = 30;
    private int soundTimer = 15;

    /** MySQL **/
    private String user;
    private String database;
    private String password;
    private String port;
    private String hostname;

    public ConfigurationHandler(HideAndGo plugin) {
        this.plugin = plugin;
    }

    public void loadConfig() {
        if (plugin.getConfig().contains("maxPlayers")) {
            this.setMaxPlayers(plugin.getConfig().getInt("maxPlayers"));
        }
        if (plugin.getConfig().contains("minPlayers")) {
            this.setMinPlayers(plugin.getConfig().getInt("minPlayers"));
        }
        if (plugin.getConfig().contains("lobbyTime")) {
            this.setLobbyTime(plugin.getConfig().getInt("lobbyTime"));
        }
        if (plugin.getConfig().contains("gortumeTimer")) {
            this.setGortumeTimer(plugin.getConfig().getInt("gortumeTimer"));
        }
        if (plugin.getConfig().contains("gameTimer")) {
            this.setGameTimer(plugin.getConfig().getInt("gameTimer"));
        }
        if (plugin.getConfig().contains("endTimer")) {
            this.setEndTimer(plugin.getConfig().getInt("endTimer"));
        }
        if (plugin.getConfig().contains("batTimer")) {
            this.setBatTimer(plugin.getConfig().getInt("batTimer"));
        }
        if (plugin.getConfig().contains("soundTimer")) {
            this.setSoundTimer(plugin.getConfig().getInt("soundTimer"));
        }
        if (plugin.getConfig().contains("user")) {
            this.setUser(plugin.getConfig().getString("user"));
        }
        if (plugin.getConfig().contains("database")) {
            this.setDatabase(plugin.getConfig().getString("database"));
        }
        if (plugin.getConfig().contains("password")) {
            this.setPassword(plugin.getConfig().getString("password"));
        }
        if (plugin.getConfig().contains("port")) {
            this.setPort(plugin.getConfig().getString("port"));
        }
        if (plugin.getConfig().contains("hostname")) {
            this.setHostname(plugin.getConfig().getString("hostname"));
        }
        if (plugin.getConfig().contains("enabled")) {
            List<String> arenas = new ArrayList<String>();
            arenas = plugin.getConfig().getStringList("enabled");

            plugin.sendConsole("Arenas enabled:");

            for (String arena : arenas) {
                Arena arenaa = new Arena(plugin, arena);
                GameManager gmn = new GameManager(plugin, arenaa);
                plugin.getActiveArenas().add(gmn);
                plugin.sendConsole(arena);
            }
        }
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public int getLobbyTime() {
        return lobbyTime;
    }

    public void setLobbyTime(int lobbyTime) {
        this.lobbyTime = lobbyTime;
    }

    public int getGortumeTimer() {
        return gortumeTimer;
    }

    public void setGortumeTimer(int gortumeTimer) {
        this.gortumeTimer = gortumeTimer;
    }

    public int getGameTimer() {
        return gameTimer;
    }

    public void setGameTimer(int gameTimer) {
        this.gameTimer = gameTimer;
    }

    public int getEndTimer() {
        return endTimer;
    }

    public void setEndTimer(int endTimer) {
        this.endTimer = endTimer;
    }

    public int getBatTimer() {
        return batTimer;
    }

    public void setBatTimer(int batTimer) {
        this.batTimer = batTimer;
    }

    public int getSoundTimer() {
        return soundTimer;
    }

    public void setSoundTimer(int soundTimer) {
        this.soundTimer = soundTimer;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
}
