package com.eyeofender.gortume;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.eyeofender.gortume.game.Arena;
import com.eyeofender.gortume.game.ArenaCreator;
import com.eyeofender.gortume.game.GameManager;
import com.eyeofender.gortume.handlers.ConfigurationHandler;
import com.eyeofender.gortume.handlers.FileHandler;
import com.eyeofender.gortume.kits.Bonus;
import com.eyeofender.gortume.kits.Kit;
import com.eyeofender.gortume.kits.KitMenu;
import com.eyeofender.gortume.listeners.BlockListener;
import com.eyeofender.gortume.listeners.GameListener;
import com.eyeofender.gortume.listeners.MenuListener;
import com.eyeofender.gortume.listeners.MovementListener;
import com.eyeofender.gortume.listeners.PositionListener;
import com.eyeofender.gortume.listeners.SignListener;
import com.eyeofender.gortume.util.DatabaseManager;
import com.eyeofender.gortume.util.Metadata;
import com.eyeofender.gortume.util.Permissions;

public class HideAndGo extends JavaPlugin {

    private static final String MAIN_PREFIX = ChatColor.GOLD + "<" + ChatColor.BLUE + "Gortume" + ChatColor.GOLD + "> ";
    private static final String CHAT_PREFIX = ChatColor.GOLD + "<" + ChatColor.GOLD + "Chat" + ChatColor.GOLD + "> ";
    private static final String DEATH_PREFIX = ChatColor.GOLD + "<" + ChatColor.RED + "Death" + ChatColor.GOLD + "> ";
    private static final String JOIN_PREFIX = ChatColor.GOLD + "<" + ChatColor.GREEN + "Join" + ChatColor.GOLD + "> ";
    private static final String OBJECT_PREFIX = ChatColor.GOLD + "<" + ChatColor.YELLOW + "Join" + ChatColor.GOLD + "> ";

    private Logger log;
    private List<GameManager> activeArenas = new ArrayList<GameManager>();
    private DatabaseManager database;

    public List<GameManager> getActiveArenas() {
        return activeArenas;
    }

    // SPAWN ALL PLAYERS BACK ON WIN
    // ALL PLAYERS ARE KILLED GAME WON
    // BROADCAST WHEN PLAYERS LEAVE
    // FLyiNG GLITCH

    public void setActiveArenas(List<GameManager> activeArenas) {
        this.activeArenas = activeArenas;
    }

    private List<Player> inArena = new ArrayList<Player>();
    private List<Player> noMove = new ArrayList<Player>();
    private List<Location> emeralds = new ArrayList<Location>();
    private List<Player> cantTalk = new ArrayList<Player>();

    private FileHandler fc = new FileHandler(this);
    private ArenaCreator ac = new ArenaCreator(this);
    private Permissions perm = new Permissions(this);
    private ConfigurationHandler configHelper = new ConfigurationHandler(this);

    /** Locations **/
    private Location Location1 = null;
    private Location Location2 = null;

    @Override
    public void onEnable() {
        PluginManager pm = this.getServer().getPluginManager();
        this.log = this.getLogger();

        Metadata.init(this);
        Bonus.init(this);
        Kit.init();
        KitMenu.init();

        fc.configSave();
        configHelper.loadConfig();
        perm.enablePermissions();

        pm.registerEvents(new MenuListener(), this);
        pm.registerEvents(new PositionListener(this), this);
        pm.registerEvents(new MovementListener(this), this);
        pm.registerEvents(new GameListener(this), this);
        pm.registerEvents(new BlockListener(this), this);
        pm.registerEvents(new SignListener(this), this);

        // database = new DatabaseManager(this);
    }

    @Override
    public void onDisable() {

        for (Location l : this.getEmeralds()) {
            l.getBlock().setType(Material.AIR);
        }

        for (GameManager gm : this.getActiveArenas()) {
            gm.stopArena();
        }

        log.info("Successfully disabled.");
        perm.disablePermissions();
    }

    public void enableArenas() {
        List<String> arenas = new ArrayList<String>();
        arenas = this.getConfig().getStringList("enabled");
        for (String arena : arenas) {
            Arena arenaa = new Arena(this, arena);
            GameManager gmn = new GameManager(this, arenaa);

            this.getActiveArenas().add(gmn);
        }
    }

    /***************************************************************************
     * \
     * 
     * Messages
     * 
     * /
     ****************************************************************************/

    public Logger log() {
        return log;
    }

    public void sendMessage(Player player, String Message) {
        player.sendMessage(MAIN_PREFIX + ChatColor.GRAY + Message);
    }

    public void sendChat(Player player, String Message) {
        player.sendMessage(CHAT_PREFIX + ChatColor.GRAY + Message);
    }

    public void sendDeath(Player player, String Message) {
        player.sendMessage(DEATH_PREFIX + ChatColor.GRAY + Message);
    }

    public void sendJoin(Player player, String Message) {
        player.sendMessage(JOIN_PREFIX + ChatColor.GRAY + Message);
    }

    public void sendObject(Player player, String Message) {
        player.sendMessage(OBJECT_PREFIX + ChatColor.GRAY + Message);
    }

    public void sendArgs(Player player) {
        this.sendMessage(player, "Incorrect Amount of Arguments.");
    }

    public String showBlockCoords(Location l) {
        return (ChatColor.LIGHT_PURPLE + "" + l.getBlockX() + ", " + l.getBlockY() + ", " + l.getBlockZ());
    }

    /***************************************************************************
     * \
     * 
     * Commands
     * 
     * /
     ****************************************************************************/

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        String command = cmd.getName();

        if (command.equalsIgnoreCase("set")) {
            if (args.length < 2) {
                sendMessage(player, ChatColor.RED + "Please specify both waypoint and arena.");
                return false;
            }

            if (args[0].equalsIgnoreCase("lobby")) {
                ac.setLobby(player, args[1]);
            } else if (args[0].equalsIgnoreCase("blockspawn")) {
                ac.setRandom(player, args[1]);
            } else if (args[0].equalsIgnoreCase("world")) {
                ac.setArenaWorld(player, args[1]);
            } else if (args[0].equalsIgnoreCase("gortumespawn")) {
                ac.setGortumeSpawn(player, args[1]);
            } else if (args[0].equalsIgnoreCase("regularspawn")) {
                ac.setRegularSpawn(player, args[1]);
            } else if (args[0].equalsIgnoreCase("end")) {
                ac.setEndLocation(player, args[1]);
            } else if (args[0].equalsIgnoreCase("arena")) {
                ac.setArena(player, args[1]);
            } else {
                sendMessage(player, ChatColor.RED + "Invalid waypoint!");
            }

            return true;
        } else if (command.equalsIgnoreCase("create")) {
            if (args.length < 1) {
                sendMessage(player, ChatColor.RED + "Please specify an arena name.");
                return false;
            }

            ac.createArena(player, args[0]);
            return true;
        } else if (command.equalsIgnoreCase("join")) {
            if (args.length < 1) {
                sendMessage(player, ChatColor.RED + "Please specify an arena.");
                return false;
            }

            String arena = args[0].toLowerCase();
            GameManager gm = this.getGameManager(arena);

            if (activeArenas.contains(gm)) {
                gm.joinArena(player);
            } else {
                this.sendMessage(player, ChatColor.RED + "Arena not found " + arena + ".");
            }

            return true;
        } else if (command.equalsIgnoreCase("enable")) {
            if (args.length < 1) {
                sendMessage(player, ChatColor.RED + "Please specify an arena.");
                return false;
            }

            String arenaName = args[0].toLowerCase();

            if (this.getFc().getArena().contains(arenaName)) {
                for (GameManager gm : this.getActiveArenas()) {
                    if (gm.getArenaName().equals(arenaName)) {
                        this.sendMessage(player, ChatColor.RED + "Arena already running.");
                        return true;
                    }
                }

                Arena arena = new Arena(this, arenaName);
                GameManager gm = new GameManager(this, arena);

                this.getActiveArenas().add(gm);

                List<String> arenas = new ArrayList<String>();
                arenas = this.getConfig().getStringList("enabled");

                arenas.add(arenaName);

                this.getConfig().set("enabled", arenas);

                this.sendMessage(player, "Arenas saved.");

                this.saveConfig();
            } else {
                this.sendMessage(player, ChatColor.RED + "There is no arena called " + arenaName + "!");
            }

            return true;
        } else if (command.equalsIgnoreCase("countdown")) {
            GameManager gm = this.getPlayersGame(player);

            if (gm != null) {
                gm.setUpGame();
                this.sendMessage(player, "Countdown has started.");
            } else {
                this.sendMessage(player, ChatColor.RED + "You have to be in a arena to do this.");
            }

            return true;
        } else if (command.equalsIgnoreCase("leave")) {
            GameManager gm = this.getPlayersGame(player);

            if (gm != null) {
                gm.leaveArena(player);
                this.sendMessage(player, "You have left the arena.");
            } else {
                this.sendMessage(player, ChatColor.RED + "You have to be in a arena to do this.");
            }

            return true;
        } else if (command.equalsIgnoreCase("pass")) {
            GameManager gm = this.getPlayersGame(player);

            if (gm != null) {
                gm.useGortumePass(player);
            } else {
                this.sendMessage(player, ChatColor.RED + "You have to be in a arena to do this.");
            }

            return true;
        } else if (command.equalsIgnoreCase("kit")) {
            if (args.length < 1) {
                sendMessage(player, ChatColor.RED + "Please specify a kit.");
                return false;
            }

            if (this.getInArena().contains(player)) {
                GameManager gm = this.getPlayersGame(player);

                if (gm.isInLobby()) {
                    Kit kit = Kit.getByName(args[0]);
                    if (kit != null) {
                        kit.equip(player);
                    } else {
                        this.sendMessage(player, ChatColor.RED + "Kit not found.");
                    }
                } else {
                    this.sendMessage(player, ChatColor.RED + "You have to be in the lobby to pick a kit.");
                }
            } else {
                this.sendMessage(player, ChatColor.RED + "You have to be in a arena to pick a kit.");
            }
            return true;
        } else if (command.equalsIgnoreCase("gortume")) {
            sendMessage(player, "Running " + getDescription().getFullName() + ".");
            return true;
        }

        return false;
    }

    /***************************************************************************
     * \
     * 
     * Random Methods
     * 
     * /
     ****************************************************************************/

    public GameManager getGameManager(String arena) {
        for (GameManager gm : this.activeArenas) {

            if (gm.getArenaName().equalsIgnoreCase(arena)) {

                return gm;

            }
        }
        return null;
    }

    public GameManager getPlayersGame(Player player) {
        for (GameManager gm : activeArenas) {
            if (gm.getArenaPlayers().contains(player)) {
                return gm;
            }
        }
        return null;
    }

    public boolean getArenaCreated(String Name) {
        if (this.getFc().getArena().contains(Name)) {
            return true;
        } else {
            return false;
        }
    }

    public void teleport(Player player, Location l) {
        double x = l.getBlockX() + .5;
        double y = l.getBlockY() + .5;
        double z = l.getBlockZ() + .5;
        float yaw = l.getYaw();
        float pitch = l.getPitch();

        Location tl = new Location(l.getWorld(), x, y, z, yaw, pitch);

        player.teleport(tl);
    }

    /***************************************************************************
     * \
     * 
     * Getters and Setters
     * 
     * /
     ****************************************************************************/

    public FileHandler getFc() {
        return fc;
    }

    public void setFc(FileHandler fc) {
        this.fc = fc;
    }

    public Location getLocation1() {
        return Location1;
    }

    public void setLocation1(Location location1) {
        Location1 = location1;
    }

    public Location getLocation2() {
        return Location2;
    }

    public void setLocation2(Location location2) {
        Location2 = location2;
    }

    public Permissions getPerm() {
        return perm;
    }

    public void setPerm(Permissions perm) {
        this.perm = perm;
    }

    public List<Player> getInArena() {
        return inArena;
    }

    public void setInArena(List<Player> inArena) {
        this.inArena = inArena;
    }

    public ConfigurationHandler getConfigHelper() {
        return configHelper;
    }

    public void setConfigHelper(ConfigurationHandler configHelper) {
        this.configHelper = configHelper;
    }

    public List<Player> getNoMove() {
        return noMove;
    }

    public void setNoMove(List<Player> noMove) {
        this.noMove = noMove;
    }

    public DatabaseManager getDatabaseManager() {
        return database;
    }

    @Override
    public void installDDL() {
        super.installDDL();
    }

    public List<Class<?>> getDatabaseClasses() {
        return DatabaseManager.getDatabaseClasses();
    }

    public List<Location> getEmeralds() {
        return emeralds;
    }

    public void setEmeralds(List<Location> emeralds) {
        this.emeralds = emeralds;
    }

    public List<Player> getCantTalk() {
        return cantTalk;
    }

    public void setCantTalk(List<Player> cantTalk) {
        this.cantTalk = cantTalk;
    }
}
