package com.eyeofender.gortume;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
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
import com.eyeofender.gortume.listeners.BlockListener;
import com.eyeofender.gortume.listeners.DropListener;
import com.eyeofender.gortume.listeners.GameListener;
import com.eyeofender.gortume.listeners.MovementListener;
import com.eyeofender.gortume.listeners.PlayerListener;
import com.eyeofender.gortume.listeners.PositionListener;
import com.eyeofender.gortume.listeners.SignListener;
import com.eyeofender.gortume.util.Permissions;

public class HideAndGo extends JavaPlugin{
	
	private List<GameManager> activeArenas = new ArrayList<GameManager>();
	
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

	private Logger logger = Logger.getLogger("Minecraft");
	
	private FileHandler fc = new FileHandler(this);
	private ArenaCreator ac = new ArenaCreator(this);
	private Permissions perm = new Permissions(this);
	private ConfigurationHandler configHelper = new ConfigurationHandler(this);
	
	/** Locations **/
	private Location Location1 = null;
	private Location Location2 = null;
	
	
	@Override
	public void onEnable() {
		fc.configSave();		
		configHelper.loadConfig();
		perm.enablePermissions();
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new PositionListener(this), this);
		pm.registerEvents(new MovementListener(this), this);
		pm.registerEvents(new GameListener(this), this);
		pm.registerEvents(new BlockListener(this), this);
		pm.registerEvents(new DropListener(this), this);
		pm.registerEvents(new PlayerListener(this), this);
		pm.registerEvents(new SignListener(this), this);
		
	}
	
	@Override
	public void onDisable() {
		//TODO Disable Permissions.
		
		for(GameManager gm : this.getActiveArenas()){
			
			gm.stopArena();
		}
	}
	
	/***************************************************************************\
	 * 
	 *                        Messages
	 *
	/****************************************************************************/
	
	public void sendConsole(String Message){
		this.logger.info("[Gortume] " + Message);
	}
	
	public void sendMessage(Player player, String Message){
		player.sendMessage(ChatColor.GOLD + "[" + ChatColor.BLUE + "Gortume" + ChatColor.GOLD + "] " + ChatColor.GRAY + Message);
	}
	
	public void sendArgs(Player player){
		this.sendMessage(player, "Incorrect Amount of Arguments.");
	}
	
	public String showBlockCoords(Location l)
	{
		return(ChatColor.LIGHT_PURPLE + "" + l.getBlockX() + ", " + l.getBlockY() + ", " + l.getBlockZ());
	}
	
	/***************************************************************************\
	 * 
	 *                        Commands
	 *
	/****************************************************************************/
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String CommandLabel, String[] args) {
		
		Player player = (Player) sender;
				
		if(CommandLabel.equalsIgnoreCase("Set") || CommandLabel.equalsIgnoreCase("S")){
			
			if(args.length == 0){
				
				this.sendMessage(player, "### Help Menu ### - To be added.");
				
			}else if(args.length == 1){
				
				this.sendArgs(player);
				
			}else if(args.length == 2){
				
				if(args[0].equalsIgnoreCase("Lobby")){
					
					ac.setLobby(player, args[1]);
					
				}
				
				if(args[0].equalsIgnoreCase("BlockSpawn")){
					
					ac.setRandom(player, args[1]);
					
				}
				
				if(args[0].equalsIgnoreCase("World")){
					
					ac.setArenaWorld(player, args[1]);
					
				}
				
				if(args[0].equalsIgnoreCase("GortumeSpawn")){
					
					ac.setGortumeSpawn(player, args[1]);
					
				}
				
				if(args[0].equalsIgnoreCase("RegularSpawn")){
					
					ac.setRegularSpawn(player, args[1]);
					
				}
				
				if(args[0].equalsIgnoreCase("End")){
					
					ac.setEndLocation(player, args[1]);
					
				}
				
				if(args[0].equalsIgnoreCase("Arena")){
					
					ac.setArena(player, args[1]);
					
				}
				
			}
			
		}
				
				
		if(CommandLabel.equalsIgnoreCase("Create")){
			
			if(args.length == 0){
				
				this.sendMessage(player, "Help Menu");
				
			}else if(args.length == 1){
				
				ac.createArena(player, args[0]);
				
			}else{
				this.sendArgs(player);
			}
		}
		
		if(CommandLabel.equalsIgnoreCase("Join")){
			
			if(args.length == 0){
				
				this.sendMessage(player, "You have to join a arena.");
				
			}else if(args.length == 1){
				String arena = args[0].toLowerCase();
					
				GameManager gm = this.getGameManager(arena);
					
				if(activeArenas.contains(gm)){
					
					gm.joinArena(player);
					
				}
				
			}else{
				this.sendArgs(player);
			}
			
		}
		
		if(CommandLabel.equalsIgnoreCase("Enable")){
			
			if(args.length == 0){
				
				this.sendMessage(player, "Do /Enable (Arena) to enable a arena.");
				
			}else if(args.length == 1){
				
				String arena = args[0].toLowerCase();
				
				if(this.getFc().getArena().contains(arena)){
				
					Arena arenaa = new Arena(this, arena);
					GameManager gmn = new GameManager(this, arenaa);
					
					this.getActiveArenas().add(gmn);
					
					List<String> arenas = new ArrayList<String>();
					arenas = this.getConfig().getStringList("enabled");
					
					arenas.add(arena);
					
					this.getConfig().set("enabled", arenas);
					
					this.sendMessage(player, "Arenas saved.");
					
					this.saveConfig();
					
				}else{
					this.sendMessage(player, "There is no arena called " + arena + "!");
				}
				
			}
			
		}
		
		if(CommandLabel.equalsIgnoreCase("Countdown") || CommandLabel.equalsIgnoreCase("C")){
			
			if(args.length == 0){
				GameManager gm = this.getPlayersGame(player);
			
				if(gm != null){
					gm.setUpGame();
					this.sendMessage(player, "Countdown has started.");
				}else{
					this.sendMessage(player, "You have to be in a arena to do this.");
				}
			}else{
				this.sendArgs(player);
			}
		}
		
		if(CommandLabel.equalsIgnoreCase("Leave") || CommandLabel.equalsIgnoreCase("L")){
			if(args.length == 0){
				GameManager gm = this.getPlayersGame(player);
				
				if(gm != null){
					gm.leaveArena(player);
					this.sendMessage(player, "You have left the arena.");
				}else{
					this.sendMessage(player, "You have to be in a arena to do this.");
				}
			}else{
				this.sendArgs(player);
			}
			
		}
		
		if(CommandLabel.equalsIgnoreCase("Pass")){
			if(args.length == 0){
				GameManager gm = this.getPlayersGame(player);
				
				if(gm != null){
					gm.useGortumePass(player);
				}else{
					this.sendMessage(player, "You have to be in a arena to do this.");
				}

			}else{
				this.sendArgs(player);
			}
		}

		
		return false;
		
	}
	
	/***************************************************************************\
	 * 
	 *                        Random Methods
	 *
	/****************************************************************************/
	
	public GameManager getGameManager (String arena){
		for (GameManager gm : this.activeArenas)
		{
						
			if(gm.getArenaName().equalsIgnoreCase(arena)){
			
				return gm;
				
			}
		}
		return null;
	}
	
	public GameManager getPlayersGame (Player player){
		for(GameManager gm : activeArenas){
			if(gm.getArenaPlayers().contains(player)){
				return gm;
			}
		}
		return null;
	}
	
	public boolean getArenaCreated (String Name){
		if(this.getFc().getArena().contains(Name)){
			return true;
		}else{
			return false;
		}
	}
	/***************************************************************************\
	 * 
	 *                        Getters and Setters
	 *
	/****************************************************************************/

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
}
