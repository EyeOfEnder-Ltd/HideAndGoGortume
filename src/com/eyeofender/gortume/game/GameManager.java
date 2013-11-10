package com.eyeofender.gortume.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.eyeofender.gortume.HideAndGo;
import com.eyeofender.gortume.kits.KitMenu;
import com.eyeofender.gortume.timers.BatTimer;
import com.eyeofender.gortume.timers.EndTimer;
import com.eyeofender.gortume.timers.GameTimer;
import com.eyeofender.gortume.timers.GortumeTimer;
import com.eyeofender.gortume.timers.LobbyTimer;
import com.eyeofender.gortume.timers.SoundTimer;
import com.eyeofender.gortume.util.Cuboid;

public class GameManager {

    private HideAndGo plugin;

    private String arenaName;

    /** Timer Stuff **/
    private boolean isLobby = false;
    private int lobbyTimer;
    private int lobby;

    private boolean isGortume = false;
    private int gortumeTimer;
    private int gortume;

    private boolean isGame = false;
    private int gameTimer;
    private int game;

    private boolean isEnd = false;
    private int endTimer;
    private int end;

    private boolean isSound = false;
    private int soundTimer;
    private int sound;

    private boolean isBat = false;
    private int batTimer;
    private int bat;

    /** Arena Info **/
    private boolean inLobby = true;

    /** Players **/
    private List<Player> gortumePlayers = new ArrayList<Player>();
    private Player gortumePlayer = null;

    private Player winner = null;

    private List<Player> arenaPlayers = new ArrayList<Player>();
    private List<Player> spec = new ArrayList<Player>();
    private List<Player> alive = new ArrayList<Player>();
    private List<Player> clicked = new ArrayList<Player>();

    public List<Player> getAlive() {
        return alive;
    }

    public void setAlive(List<Player> alive) {
        this.alive = alive;
    }

    private Arena arena;
    private Cuboid cube;

    private Location blockLocation;

    public GameManager(HideAndGo plugin, Arena arena) {
        this.plugin = plugin;
        this.arena = arena;

        try {
            this.setCube(new Cuboid(arena.getLocation1(), arena.getLocation2()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setArenaName(arena.getArenaName());

        this.setLobbyTimer(plugin.getConfigHelper().getLobbyTime());
        this.setGameTimer(plugin.getConfigHelper().getGameTimer());
        this.setGortumeTimer(plugin.getConfigHelper().getGortumeTimer());
        this.setSoundTimer(plugin.getConfigHelper().getSoundTimer());
        this.setBatTimer(plugin.getConfigHelper().getBatTimer());
    }

    public void joinArena(Player player) {
        /** Checks if the arena is disabled **/
        if (!arena.isDisabled()) {
            if (isInLobby()) {
                if (plugin.getConfigHelper().getMaxPlayers() >= this.getArenaPlayers().size()) {
                    if (!plugin.getInArena().contains(player)) {

                        addPlayer(player);
                    } else {
                        plugin.sendMessage(player, "You are already in a arena.");
                    }
                } else {
                    plugin.sendMessage(player, "Arena is full. Try again later.");
                }
            } else {
                plugin.sendMessage(player, "Game is in progress. Try again later.");
            }
        } else {
            plugin.sendMessage(player, "Arena is currectly disabled.");
        }
    }

    public void addPlayer(Player player) {
        /** Clear Inventory **/
        this.clearInventory(player);

        /** Sets time to night **/
        player.getWorld().setTime(12500);

        /** Adds player to arrays **/
        plugin.getInArena().add(player);
        arenaPlayers.add(player);

        /** Teleports player **/
        this.teleport(player, arena.getLobbySpawn());

        /** Tells arena of player join **/
        for (Player players : this.getArenaPlayers()) {
            plugin.sendJoin(players, ChatColor.AQUA + player.getName() + ChatColor.BLUE + " has joined the arena. (" + arenaPlayers.size() + "/" + plugin.getConfigHelper().getMaxPlayers() + ")");
        }

        /** Checks players in arena **/
        checkPlayerAmount();

        /** Updates Signs **/
        arena.updateSigns();

        /** Adds Kit Tool **/
        player.getInventory().addItem(KitMenu.getMenuItem());
    }

    public void checkPlayerAmount() {
        if (this.getArenaPlayers().size() >= plugin.getConfigHelper().getMinPlayers()) {
            if (this.isLobby == false) {
                this.setUpGame();

                for (Player player : this.getArenaPlayers()) {
                    plugin.sendMessage(player, "Minimum players has been reached. Lobby timer has started.");
                }
            }
        }
    }

    public void setUpGame() {
        this.setLobby(true);
        lobby = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new LobbyTimer(plugin, this), 20L, 20L);
    }

    public void startGame() {
        if (this.getArenaPlayers().size() != 1) {
            if (this.getGortumePlayers().size() != 0) {
                if (this.getGortumePlayers().size() != 1) {
                    int number = new Random().nextInt(this.getGortumePlayers().size()) + 0;
                    Player player = this.gortumePlayers.get(number);

                    this.setGortumePlayer(player);

                } else {
                    Player player = this.gortumePlayers.get(0);

                    this.setGortumePlayer(player);

                }
            } else {
                Random generator = new Random();
                int randoms = generator.nextInt(this.getArenaPlayers().size()) + 0;
                Player player = this.getArenaPlayers().get(randoms);

                this.setGortumePlayer(player);

            }

            this.teleport(this.getGortumePlayer(), arena.getGortumeSpawn());
            this.getGortumePlayer().playEffect(this.getGortumePlayer().getLocation(), Effect.MOBSPAWNER_FLAMES, 1000000);

            ItemStack emerald = new ItemStack(Material.EMERALD_BLOCK);
            ItemMeta im = emerald.getItemMeta();
            im.addEnchant(Enchantment.KNOCKBACK, 5, true);
            im.setDisplayName(ChatColor.GOLD + "Sticky Block");
            List<String> list = new ArrayList<String>();
            list.add(ChatColor.BLUE + "Hide this block to start the action.");
            emerald.setItemMeta(im);

            for (Player player : this.getArenaPlayers()) {

                /** Makes is so every player can not talk **/
                plugin.getCantTalk().add(player);

                if (player != this.getGortumePlayer()) {
                    this.teleport(player, arena.getRegularSpawn());
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1000000, 1));
                    plugin.getNoMove().add(player);
                    alive.add(player);

                    for (Player players : this.getAlive()) {
                        player.sendMessage("");
                        player.sendMessage("");
                        player.sendMessage("");
                        player.sendMessage("");
                        player.sendMessage("");
                        player.sendMessage("");
                        player.sendMessage("");
                        player.sendMessage("");
                        player.sendMessage("");
                        player.sendMessage("");
                        players.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=-=-=- -=-=-=- -=-=- -=-");
                        players.sendMessage("");
                        plugin.sendMessage(players, ChatColor.BLUE + this.getGortumePlayer().getName() + ChatColor.GRAY + " is the gortume.");
                        players.sendMessage("");
                        plugin.sendChat(players, "Chat has been disabled.");
                        players.sendMessage("");
                        plugin.sendObject(players, "Wait for the gortume to place the emerald. Then find it.");
                        players.sendMessage("");
                        players.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=-=-=- -=-=-=- -=-=- -=-");
                    }

                } else {
                    /** Clears players inventory **/
                    this.clearInventory(player);

                    player.sendMessage("");
                    player.sendMessage("");
                    player.sendMessage("");
                    player.sendMessage("");
                    player.sendMessage("");
                    player.sendMessage("");
                    player.sendMessage("");
                    player.sendMessage("");
                    player.sendMessage("");
                    player.sendMessage("");
                    this.getGortumePlayer().getInventory().addItem(emerald);
                    this.getGortumePlayer().sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=-=-=- -=-=-=- -=-=- -=-");
                    this.getGortumePlayer().sendMessage("");
                    plugin.sendMessage(this.getGortumePlayer(), ChatColor.BLUE + this.getGortumePlayer().getName() + ChatColor.GRAY + " is the gortume.");
                    this.getGortumePlayer().sendMessage("");
                    plugin.sendChat(this.getGortumePlayer(), "Chat has been disabled.");
                    this.getGortumePlayer().sendMessage("");
                    plugin.sendObject(this.getGortumePlayer(), "Place the emerald block. Kill all the Adventurers.");
                    this.getGortumePlayer().sendMessage("");
                    this.getGortumePlayer().sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=-=-=- -=-=-=- -=-=- -=-");
                }
            }

            this.setGortume(true);
            gortume = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new GortumeTimer(plugin, this), 20L, 20L);
            this.setInLobby(false);

        } else {
            this.tellArena("Not enough players to start");
            this.stopArena();
        }

        arena.updateSigns();
    }

    public void autoPlace() {
        this.getArena().getRandomBlock().getBlock().setType(Material.EMERALD_BLOCK);
        this.setBlockLocation(this.getArena().getRandomBlock());
        plugin.getEmeralds().add(this.getArena().getRandomBlock());
        this.tellArena("Random block has been set.");
        startGortume();
    }

    public void startGortume() {
        /** Cancels the Timer **/
        plugin.getServer().getScheduler().cancelTask(this.getGortume());
        setGortumeTimer(-1);
        setGortume(false);

        for (Player player : this.getArenaPlayers()) {
            player.setLevel(0);
        }

        if (this.getGortumePlayer().getInventory().contains(Material.EMERALD_BLOCK)) {

            this.getGortumePlayer().getInventory().remove(Material.EMERALD_BLOCK);

            ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD);

            this.getGortumePlayer().getInventory().addItem(diamondSword);

        } else {

            ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD);

            this.getGortumePlayer().getInventory().addItem(diamondSword);

        }

        plugin.getServer().getScheduler().cancelTask(getGortume());

        for (Player player : arenaPlayers) {
            if (player == this.getGortumePlayer()) {
                this.teleport(player, this.getArena().getGortumeSpawn());
            } else {
                plugin.getNoMove().remove(player);
                this.teleport(player, this.getArena().getRegularSpawn());
                plugin.sendMessage(player, "Press" + ChatColor.BLUE + " F1 " + ChatColor.GRAY + " for a scarier time.");
            }
        }

        this.setGame(true);
        this.setBat(true);
        this.setSound(true);
        this.game = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new GameTimer(plugin, this), 20L, 20L);
        this.bat = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new BatTimer(plugin, this), 20L, 20L);
        this.sound = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new SoundTimer(plugin, this), 20L, 20L);

        this.getBlockLocation().getBlock().setType(Material.EMERALD_BLOCK);
    }

    public void addSpectator(Player player) {
        this.alive.remove(player);
        this.getSpec().add(player);
        this.clearPotionEffects(player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, 5));
        this.teleport(player, arena.getRegularSpawn());
        plugin.sendMessage(player, "You have become a spectator.");
        player.setAllowFlight(true);
        player.setFlySpeed(0.1F);
        player.setFlying(true);
    }

    public void addClicked(Player player) {
        this.alive.remove(player);
        this.addSpectator(player);
        this.getClicked().add(player);
        this.clearPotionEffects(player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, 5));
        plugin.sendMessage(player, "You have clicked the emerald! Wait until the rest of you team does!");
        player.setAllowFlight(true);
        player.setFlySpeed(0.1F);
        player.setFlying(true);
    }

    public void checkPlayersAlive() {
        if (this.alive.isEmpty()) {
            if (this.getClicked().isEmpty()) {
                this.gameOver();
                this.tellArena("Gortume has killed all the players.");
            } else {
                this.gameOver();
                this.tellArena("All players have hit the emerald.");
            }
        }
    }

    public void gameTimerRunout() {
        gameOver();
        this.tellArena("Game timer has run out. No one has won! Hopefully you had fun!");
    }

    public void gameOver() {
        this.setEnd(true);
        this.setEndTimer(10);
        this.setGame(false);
        this.setGameTimer(-1);

        // Clears Everything
        for (Player player : this.getArenaPlayers()) {
            this.clearPotionEffects(player);
            player.getInventory().clear();
            player.setHealth(20);
            player.setFoodLevel(20);
            player.setLevel(0);

            plugin.getCantTalk().remove(player);
            plugin.sendChat(player, "Chat has been enabled.");
        }

        plugin.getServer().getScheduler().cancelTask(this.getGame());
        plugin.getServer().getScheduler().cancelTask(this.getBat());
        plugin.getServer().getScheduler().cancelTask(this.getSound());
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new EndTimer(plugin, this), 20L, 20L);
        this.getBlockLocation().getBlock().setType(Material.AIR);
    }

    public void leaveArena(Player player) {
        for (Player players : this.getArenaPlayers()) {
            plugin.sendLeave(players, ChatColor.BLUE + player.getName() + ChatColor.GRAY + " has left the arena.");
        }

        arena.updateSigns();

        this.teleport(player, arena.getEndLocation());
        /** Leaves all Arrays **/
        this.getArenaPlayers().remove(player);
        this.alive.remove(player);
        this.getSpec().remove(player);
        this.getGortumePlayers().remove(player);
        plugin.getInArena().remove(player);
        plugin.getNoMove().remove(player);
        this.clearPotionEffects(player);
        player.getInventory().clear();
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setLevel(0);
        player.setExp(0);
        if (plugin.getCantTalk().contains(player)) {
            plugin.getCantTalk().remove(player);
        }

        if (this.getArenaPlayers().size() <= 1) {
            this.getArena().getRandomBlock().getBlock().setType(Material.AIR);
            this.stopArena();
        }

        if (this.getGortumePlayer() == player) {
            this.stopArena();
        }
    }

    public void stopArena() {
        if (this.getArenaPlayers().size() >= 0) {

            for (Player player : this.getArenaPlayers()) {
                plugin.sendMessage(player, "Arena has been stopped.");
                this.teleport(player, arena.getEndLocation());
                this.alive.remove(player);
                this.getSpec().remove(player);
                this.getGortumePlayers().remove(player);
                plugin.getInArena().remove(player);
                plugin.getNoMove().remove(player);
                plugin.getCantTalk().remove(player);
                this.clearPotionEffects(player);
                player.getInventory().clear();
                player.setHealth(20);
                player.setFoodLevel(20);
                player.setLevel(0);
                player.setExp(0);
            }

            this.getArenaPlayers().clear();

        }

        plugin.getServer().getScheduler().cancelTask(this.getLobby());
        plugin.getServer().getScheduler().cancelTask(this.getGortume());
        plugin.getServer().getScheduler().cancelTask(this.getGame());
        plugin.getServer().getScheduler().cancelTask(this.getEnd());
        plugin.getServer().getScheduler().cancelTask(this.getBat());
        plugin.getServer().getScheduler().cancelTask(this.getSound());

        this.setLobbyTimer(plugin.getConfigHelper().getLobbyTime());
        this.setGameTimer(plugin.getConfigHelper().getGameTimer());
        this.setGortumeTimer(plugin.getConfigHelper().getGortumeTimer());
        this.setSoundTimer(plugin.getConfigHelper().getSoundTimer());
        this.setBatTimer(plugin.getConfigHelper().getBatTimer());
        this.setEndTimer(plugin.getConfigHelper().getEndTimer());

        this.inLobby = true;
        arena.updateSigns();
        this.getSpec().clear();
        this.getArenaPlayers().clear();
        this.getGortumePlayers().clear();
        this.getAlive().clear();

        plugin.getEmeralds().remove(this.getArena().getRandomBlock());
        this.getArena().getRandomBlock().getBlock().setType(Material.AIR);

        this.gortumePlayer = null;
    }

    public void tellArena(String Message) {
        for (Player player : arenaPlayers) {
            plugin.sendMessage(player, Message);
        }
    }

    /***************************************************************************
     * \
     * 
     * Helper Methods
     * 
     * /
     ****************************************************************************/

    public void teleport(Player p, Location loc) {
        p.teleport(loc.clone().add(0.5D, 0.5D, 0.5D));
    }

    public void clearPotionEffects(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    }

    @SuppressWarnings("deprecation")
    public void clearInventory(Player player) {
        this.clearPotionEffects(player);

        player.getInventory().clear();
        player.getInventory().clear();
        player.getInventory().setHelmet(new ItemStack(Material.AIR));
        player.getInventory().setChestplate(new ItemStack(Material.AIR));
        player.getInventory().setLeggings(new ItemStack(Material.AIR));
        player.getInventory().setBoots(new ItemStack(Material.AIR));

        player.getInventory().remove(Material.COMPASS);
        player.getInventory().remove(Material.WATCH);
        player.getInventory().remove(Material.DIAMOND_HELMET);

        player.getInventory().setContents(player.getInventory().getContents());

        player.updateInventory();

        /** Updates Players Inventory **/
        player.setGameMode(GameMode.SURVIVAL);
        player.setAllowFlight(false);
        player.setFlySpeed(0.1F);
        player.setFlying(false);

        /** Resets exp levels **/
        player.setLevel(0);
        player.setExp(0);
    }

    /***************************************************************************
     * \
     * 
     * Getters and Setters
     * 
     * /
     ****************************************************************************/

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public String getArenaName() {
        return arenaName;
    }

    public void setArenaName(String arenaName) {
        this.arenaName = arenaName;
    }

    public List<Player> getArenaPlayers() {
        return arenaPlayers;
    }

    public void setArenaPlayers(List<Player> arenaPlayers) {
        this.arenaPlayers = arenaPlayers;
    }

    public Cuboid getCube() {
        return cube;
    }

    public void setCube(Cuboid cube) {
        this.cube = cube;
    }

    public boolean isInLobby() {
        return inLobby;
    }

    public void setInLobby(boolean inLobby) {
        this.inLobby = inLobby;
    }

    public Player getGortumePlayer() {
        return gortumePlayer;
    }

    public void setGortumePlayer(Player gortumePlayer) {
        this.gortumePlayer = gortumePlayer;
    }

    public List<Player> getGortumePlayers() {
        return gortumePlayers;
    }

    public void setGortumePlayers(List<Player> gortumePlayers) {
        this.gortumePlayers = gortumePlayers;
    }

    public boolean isLobby() {
        return isLobby;
    }

    public void setLobby(boolean isLobby) {
        this.isLobby = isLobby;
        arena.updateSigns();
    }

    public int getLobbyTimer() {
        return lobbyTimer;
    }

    public void setLobbyTimer(int lobbyTimer) {
        this.lobbyTimer = lobbyTimer;
    }

    public int getLobby() {
        return lobby;
    }

    public void setLobby(int lobby) {
        this.lobby = lobby;
    }

    public boolean isGame() {
        return isGame;
    }

    public void setGame(boolean isGame) {
        this.isGame = isGame;
        arena.updateSigns();
    }

    public int getGameTimer() {
        return gameTimer;
    }

    public void setGameTimer(int gameTimer) {
        this.gameTimer = gameTimer;
    }

    public int getGame() {
        return game;
    }

    public void setGame(int game) {
        this.game = game;
    }

    public boolean isGortume() {
        return isGortume;
    }

    public void setGortume(boolean isGortume) {
        this.isGortume = isGortume;
    }

    public int getGortumeTimer() {
        return gortumeTimer;
    }

    public void setGortumeTimer(int gortumeTimer) {
        this.gortumeTimer = gortumeTimer;
    }

    public int getGortume() {
        return gortume;
    }

    public void setGortume(int gortume) {
        this.gortume = gortume;
    }

    public List<Player> getSpec() {
        return spec;
    }

    public void setSpec(List<Player> spec) {
        this.spec = spec;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    public int getEndTimer() {
        return endTimer;
    }

    public void setEndTimer(int endTimer) {
        this.endTimer = endTimer;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public Location getBlockLocation() {
        return blockLocation;
    }

    public void setBlockLocation(Location blockLocation) {
        this.blockLocation = blockLocation;
    }

    public boolean isSound() {
        return isSound;
    }

    public void setSound(boolean isSound) {
        this.isSound = isSound;
    }

    public int getSoundTimer() {
        return soundTimer;
    }

    public void setSoundTimer(int soundTimer) {
        this.soundTimer = soundTimer;
    }

    public int getSound() {
        return sound;
    }

    public void setSound(int sound) {
        this.sound = sound;
    }

    public boolean isBat() {
        return isBat;
    }

    public void setBat(boolean isBat) {
        this.isBat = isBat;
    }

    public int getBatTimer() {
        return batTimer;
    }

    public void setBatTimer(int batTimer) {
        this.batTimer = batTimer;
    }

    public int getBat() {
        return bat;
    }

    public void setBat(int bat) {
        this.bat = bat;
    }

    public List<Player> getClicked() {
        return clicked;
    }

    public void setClicked(List<Player> clicked) {
        this.clicked = clicked;
    }
}
