package com.eyeofender.gortume.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import com.eyeofender.gortume.HideAndGo;

public class Arena {

    /** Hi **/
    private HideAndGo plugin;

    private boolean disabled = false;

    private String arenaName = null;
    private Location lobbySpawn = null;
    private Location gortumeSpawn = null;
    private Location regularSpawn = null;
    private Location endLocation = null;
    private Location location1 = null;
    private Location location2 = null;
    private Location randomBlock = null;
    private List<Location> signLocations;

    public Arena(HideAndGo plugin, String Arena) {
        this.plugin = plugin;
        this.arenaName = Arena;

        loadArena(Arena);
    }

    public void loadArena(String ArenaUppercase) {
        String Arena = ArenaUppercase.toLowerCase();
        World lobbyWorld = plugin.getServer().getWorld(plugin.getFc().getArena().getConfigurationSection(Arena).getString("lobbyWorld"));

        if (lobbyWorld != null) {
            String y = plugin.getFc().getArena().getConfigurationSection(Arena).getString("lobbySpawn.yaw");
            String p = plugin.getFc().getArena().getConfigurationSection(Arena).getString("lobbySpawn.pitch");

            float yaw = (float) Float.parseFloat(y);
            float pitch = (float) Float.parseFloat(p);

            lobbySpawn = new Location(lobbyWorld, plugin.getFc().getArena().getConfigurationSection(Arena).getInt("lobbySpawn.x"), plugin.getFc().getArena().getConfigurationSection(Arena)
                    .getInt("lobbySpawn.y"), plugin.getFc().getArena().getConfigurationSection(Arena).getInt("lobbySpawn.z"), yaw, pitch);
        } else {
            plugin.sendConsole("Error loading arena " + this.getArenaName());
        }

        World arenaWorld = plugin.getServer().getWorld(plugin.getFc().getArena().getConfigurationSection(Arena).getString("arenaWorld"));
        Random generator = new Random();
        int randoms = generator.nextInt(plugin.getFc().getArena().getConfigurationSection(Arena).getInt("randoms")) + 1;

        if (arenaWorld != null) {
            String y = plugin.getFc().getArena().getConfigurationSection(Arena).getString("gortumeSpawn.yaw");
            String p = plugin.getFc().getArena().getConfigurationSection(Arena).getString("gortumeSpawn.pitch");

            float yaw = (float) Float.parseFloat(y);
            float pitch = (float) Float.parseFloat(p);

            String y2 = plugin.getFc().getArena().getConfigurationSection(Arena).getString("regularSpawn.yaw");
            String p2 = plugin.getFc().getArena().getConfigurationSection(Arena).getString("regularSpawn.pitch");

            float yaw2 = (float) Float.parseFloat(y2);
            float pitch2 = (float) Float.parseFloat(p2);

            gortumeSpawn = new Location(arenaWorld, plugin.getFc().getArena().getConfigurationSection(Arena).getInt("gortumeSpawn.x"), plugin.getFc().getArena().getConfigurationSection(Arena)
                    .getInt("gortumeSpawn.y"), plugin.getFc().getArena().getConfigurationSection(Arena).getInt("gortumeSpawn.z"), yaw, pitch);
            regularSpawn = new Location(arenaWorld, plugin.getFc().getArena().getConfigurationSection(Arena).getInt("regularSpawn.x"), plugin.getFc().getArena().getConfigurationSection(Arena)
                    .getInt("regularSpawn.y"), plugin.getFc().getArena().getConfigurationSection(Arena).getInt("regularSpawn.z"), yaw2, pitch2);
            setLocation1(new Location(arenaWorld, plugin.getFc().getArena().getConfigurationSection(Arena).getInt("location1.x"), plugin.getFc().getArena().getConfigurationSection(Arena)
                    .getInt("location1.y"), plugin.getFc().getArena().getConfigurationSection(Arena).getInt("location1.z")));
            setLocation2(new Location(arenaWorld, plugin.getFc().getArena().getConfigurationSection(Arena).getInt("location2.x"), plugin.getFc().getArena().getConfigurationSection(Arena)
                    .getInt("location2.y"), plugin.getFc().getArena().getConfigurationSection(Arena).getInt("location2.z")));
            this.setRandomBlock(new Location(arenaWorld, plugin.getFc().getArena().getConfigurationSection(Arena).getInt("random" + randoms + ".x"), plugin.getFc().getArena()
                    .getConfigurationSection(Arena).getInt("random" + randoms + ".y"), plugin.getFc().getArena().getConfigurationSection(Arena).getInt("random" + randoms + ".z")));
        }

        World endWorld = plugin.getServer().getWorld(plugin.getFc().getArena().getConfigurationSection(Arena).getString("endLocation.world"));

        if (endWorld != null) {
            String y = plugin.getFc().getArena().getConfigurationSection(Arena).getString("endLocation.yaw");
            String p = plugin.getFc().getArena().getConfigurationSection(Arena).getString("endLocation.pitch");

            float yaw = (float) Float.parseFloat(y);
            float pitch = (float) Float.parseFloat(p);

            endLocation = new Location(endWorld, plugin.getFc().getArena().getConfigurationSection(Arena).getInt("endLocation.x"), plugin.getFc().getArena().getConfigurationSection(Arena)
                    .getInt("endLocation.y"), plugin.getFc().getArena().getConfigurationSection(Arena).getInt("endLocation.z"), yaw, pitch);
        }

        signLocations = new ArrayList<Location>();
        List<String> signs = plugin.getFc().getArena().getConfigurationSection(Arena).getStringList("signs");
        for (String sign : signs) {
            String[] vars = sign.split(", ");
            World world = Bukkit.getWorld(vars[0]);
            if (world == null) continue;

            signLocations.add(new Location(world, Double.parseDouble(vars[1]), Double.parseDouble(vars[2]), Double.parseDouble(vars[3])));
        }

        if (plugin.getFc().getArena().getConfigurationSection(Arena).getBoolean("Disabled")) {
            setDisabled(true);
        }
    }

    private void saveSignLocations() {
        List<String> list = new ArrayList<String>();

        for (Location loc : signLocations) {
            list.add(loc.getWorld().getName() + ", " + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ());
        }

        plugin.getFc().getArena().set("signs", list);
        plugin.getFc().saveArena();
    }

    // ///////////////////////////////////////////////////////////////////////
    // ///////////////////////// Getters and Setters ////////////////////////
    // /////////////////////////////////////////////////////////////////////

    public Location getLobbySpawn() {
        return lobbySpawn;
    }

    public void setLobbySpawn(Location lobbySpawn) {
        this.lobbySpawn = lobbySpawn;
    }

    public Location getGortumeSpawn() {
        return gortumeSpawn;
    }

    public void setGortumeSpawn(Location herobrineSpawn) {
        this.gortumeSpawn = herobrineSpawn;
    }

    public Location getRegularSpawn() {
        return regularSpawn;
    }

    public void setRegularSpawn(Location regularSpawn) {
        this.regularSpawn = regularSpawn;
    }

    /**
     * @return the endLocation
     */
    public Location getEndLocation() {
        return endLocation;
    }

    /**
     * @param endLocation
     *            the endLocation to set
     */
    public void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
    }

    /**
     * @return the arenaName
     */
    public String getArenaName() {
        return arenaName;
    }

    /**
     * @param arenaName
     *            the arenaName to set
     */
    public void setArenaName(String arenaName) {
        this.arenaName = arenaName;
    }

    /**
     * @return the disabled
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * @param disabled
     *            the disabled to set
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Location getLocation1() {
        return location1;
    }

    public void setLocation1(Location location1) {
        this.location1 = location1;
    }

    public Location getLocation2() {
        return location2;
    }

    public void setLocation2(Location location2) {
        this.location2 = location2;
    }

    public Location getRandomBlock() {
        return randomBlock;
    }

    public void setRandomBlock(Location randomBlock) {
        this.randomBlock = randomBlock;
    }

    public List<Location> getSignLocations() {
        return signLocations;
    }

    public void addSignLocation(Location location) {
        signLocations.add(location);
        saveSignLocations();
    }

    public void removeSignLocation(Location location) {
        signLocations.remove(location);
        saveSignLocations();
    }

    public void updateSigns() {
        List<Location> brokenLocs = new ArrayList<Location>();

        for (Location signLoc : signLocations) {
            Block block = signLoc.getBlock();
            if (!block.getType().equals(Material.SIGN_POST) && !block.getType().equals(Material.WALL_SIGN)) {
                brokenLocs.add(signLoc);
                continue;
            }

            GameManager gm = plugin.getGameManager(arenaName);
            boolean full = gm.getArenaPlayers().size() >= plugin.getConfigHelper().getMaxPlayers();

            Sign sign = (Sign) block.getState();
            sign.setLine(0, gm.isInLobby() ? full ? ChatColor.RED + "[Full]" : ChatColor.GREEN + "[Join]" : ChatColor.RED + "[In-Game]");
            sign.setLine(1, gm.getArenaName());
            sign.setLine(2, ChatColor.GRAY + "" + ChatColor.BOLD + gm.getArenaPlayers().size() + "/" + plugin.getConfigHelper().getMaxPlayers());
            sign.setLine(3, gm.isInLobby() ? ChatColor.GOLD + "-=- Lobby -=-" : ChatColor.GRAY + "-=- In-Game -=-");
            sign.update();
        }

        signLocations.removeAll(brokenLocs);
    }
}
