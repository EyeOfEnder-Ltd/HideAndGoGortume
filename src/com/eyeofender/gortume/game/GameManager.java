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
import org.kitteh.tag.TagAPI;

import com.eyeofender.gortume.HideAndGo;
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
        plugin.getInArena().add(player);
        player.teleport(arena.getLobbySpawn());
        arenaPlayers.add(player);

        /** Updates Players Inventory **/
        player.setGameMode(GameMode.SURVIVAL);
        player.setAllowFlight(true);
        player.setFlySpeed(0.1F);
        player.setFlying(true);

        /** Resets exp levels **/
        player.setLevel(0);
        player.setExp(0);

        /** Clear Inventory **/
        player.getInventory().clear();

        /** Updates Signs **/

        /** Clears the Players Potion Effects **/
        this.clearPotionEffects(player);

        tellArena(ChatColor.AQUA + player.getName() + ChatColor.BLUE + " has joined the game. (" + arenaPlayers.size() + "/" + plugin.getConfigHelper().getMaxPlayers() + ")");

        checkPlayerAmount();
        arena.updateSigns();
    }

    public void checkPlayerAmount() {
        if (this.getArenaPlayers().size() >= plugin.getConfigHelper().getMinPlayers()) {
            if (this.isLobby == false) {
                this.setUpGame();
                this.tellArena("Lobby Timer has started.");
            }
        }
    }

    public void setUpGame() {
        this.setLobby(true);
        lobby = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new LobbyTimer(plugin, this), 20L, 20L);
    }

    public void startGame() {
        if (this.getArenaPlayers().size() != 0) {
            if (this.getGortumePlayers().size() != 0) {
                if (this.getGortumePlayers().size() != 1) {
                    int number = new Random().nextInt(this.getGortumePlayers().size()) + 0;
                    Player player = this.gortumePlayers.get(number);

                    this.setGortumePlayer(player);

                    this.tellArena(player.getName() + " is the gortume!");
                } else {
                    Player player = this.gortumePlayers.get(0);

                    this.setGortumePlayer(player);

                    this.tellArena(player.getName() + " is the gortume!");
                }
            } else {
                Random generator = new Random();
                int randoms = generator.nextInt(this.getArenaPlayers().size()) + 0;
                Player player = this.getArenaPlayers().get(randoms);

                this.setGortumePlayer(player);

                this.tellArena(player.getName() + " is the gortume!");
            }

            this.getGortumePlayer().teleport(arena.getGortumeSpawn());
            this.getGortumePlayer().playEffect(this.getGortumePlayer().getLocation(), Effect.MOBSPAWNER_FLAMES, 1000000);

            ItemStack emerald = new ItemStack(Material.EMERALD_BLOCK);
            ItemMeta im = emerald.getItemMeta();
            im.addEnchant(Enchantment.KNOCKBACK, 5, true);
            im.setDisplayName(ChatColor.GOLD + "Sticky Block");
            List<String> list = new ArrayList<String>();
            list.add(ChatColor.BLUE + "Place in random spot. Try to hid this hiders.");
            emerald.setItemMeta(im);

            this.getGortumePlayer().getInventory().addItem(emerald);

            for (Player player : this.getArenaPlayers()) {

                player.setAllowFlight(false);
                player.setFlySpeed(0.1F);
                player.setFlying(false);
                alive.add(player);
                TagAPI.refreshPlayer(player);

                if (player != this.getGortumePlayer()) {
                    player.teleport(arena.getRegularSpawn());
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1000000, 1));
                    plugin.getNoMove().add(player);

                    alive.remove(this.getGortumePlayer());
                }
            }

            this.setGortume(true);
            gortume = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new GortumeTimer(plugin, this), 20L, 20L);
            this.setInLobby(false);

            plugin.sendMessage(this.getGortumePlayer(), "Place the Emerald Block somewhere that the seekers will not find.");
            plugin.sendMessage(getGortumePlayer(), "You have 30 Seconds. If you fail to place, the block will be placed randomly.");
            tellArena("Gortume is placing Emerald. Please wait 30 Seconds.");
        } else {
            this.tellArena("Not enough players to start");
            this.stopArena();
        }
        arena.updateSigns();
    }

    public void autoPlace() {
        this.getArena().getRandomBlock().getBlock().setType(Material.EMERALD_BLOCK);
        this.setBlockLocation(this.getArena().getRandomBlock());
        this.tellArena("Random block has been set.");
        startGortume();
    }

    public void startGortume() {
        /** Cancels the Timer **/
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
                player.teleport(this.getArena().getGortumeSpawn());
            } else {
                plugin.getNoMove().remove(player);
                player.teleport(this.getArena().getRegularSpawn());
            }
        }

        this.setGame(true);
        this.setBat(true);
        this.setSound(true);
        this.game = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new GameTimer(plugin, this), 20L, 20L);
        this.bat = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new BatTimer(plugin, this), 20L, 20L);
        this.sound = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new SoundTimer(plugin, this), 20L, 20L);
    }

    public void addSpectator(Player player) {
        this.alive.remove(player);
        this.getSpec().add(player);
        this.clearPotionEffects(player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, 5));
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
        this.gortumePlayer = null;
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
        }

        plugin.getServer().getScheduler().cancelTask(this.getGame());
        plugin.getServer().getScheduler().cancelTask(this.getBat());
        plugin.getServer().getScheduler().cancelTask(this.getSound());
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new EndTimer(plugin, this), 20L, 20L);
        this.getBlockLocation().getBlock().setType(Material.AIR);
    }

    public void useGortumePass(Player player) {
        this.gortumePlayers.add(player);
        this.tellArena(player.getName() + " has used there Gortume Pass and now entered the Gortume drawing.");
    }

    public void leaveArena(Player player) {
        player.teleport(arena.getEndLocation());
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
        TagAPI.refreshPlayer(player);

        arena.updateSigns();

        if (this.getArenaPlayers().size() == 0) {
            this.stopArena();
            return;
        }

        if (this.getGortumePlayer() == player) {
            this.tellArena("The gortume has left the arena. A new gortume will be picked.");

            this.gortumePlayer = null;

            for (Player playerr : this.getArenaPlayers()) {
                this.leaveArena(playerr);
                this.addPlayer(playerr);
            }

            this.setUpGame();
            return;
        }
    }

    public void stopArena() {
        if (this.getArenaPlayers().size() > 0) {
            for (Player player : this.getArenaPlayers()) {
                this.leaveArena(player);
                player.teleport(arena.getEndLocation());
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
            }
        }

        plugin.getInArena().remove(this);
        plugin.getServer().getScheduler().cancelTask(this.getLobby());
        plugin.getServer().getScheduler().cancelTask(this.getGortume());
        plugin.getServer().getScheduler().cancelTask(this.getGame());
        plugin.getServer().getScheduler().cancelTask(this.getEnd());
        plugin.getServer().getScheduler().cancelTask(this.getBat());
        plugin.getServer().getScheduler().cancelTask(this.getSound());

        this.getSpec().clear();
        this.getArenaPlayers().clear();
        this.getGortumePlayers().clear();
        this.getAlive().clear();

        this.getArena().getRandomBlock().getBlock().setType(Material.AIR);
    }

    public void tellArena(String Message) {
        for (Player player : arenaPlayers) {
            plugin.sendMessage(player, Message);
        }
    }

    public void teleport(Player p, Location loc) {
        p.teleport(loc.clone().add(0.5D, 1.0D, 0.5D));
    }

    public void clearPotionEffects(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
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
