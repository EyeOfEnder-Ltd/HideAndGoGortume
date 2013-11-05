package com.eyeofender.gortume.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.eyeofender.gortume.HideAndGo;
import com.eyeofender.gortume.game.GameManager;
import com.eyeofender.gortume.util.Permissions;

public class Kits implements Listener{

	private HideAndGo plugin;
	private GameManager gm;
	public static Inventory i;
	
	/** Permissions **/
	Permissions p = new Permissions(plugin);
	
	/** Items **/
    SpeedCarrot sc = new SpeedCarrot(plugin);
    InvisiblePie ip = new InvisiblePie(plugin);
    SightPork sp = new SightPork(plugin);
    SlownessSteak ss = new SlownessSteak(plugin);
    ConfusionCookie cc = new ConfusionCookie(plugin);
    GoldenApple ga = new GoldenApple(plugin);
    KnockbackStick ks = new KnockbackStick(plugin);
	
    /** List **/
    private Map<Player, String> kits = new HashMap<Player, String>();
    private List<Player> voted = new ArrayList<Player>();
    
    public Kits(HideAndGo plugin){
		this.plugin = plugin;
	}
    
	public Kits(HideAndGo plugin, GameManager gm){
		this.plugin = plugin;
		this.gm = gm;
	}

	
	/**************************************
	 * 
	 *  			Action
	 * 
	 *************************************/
	
	public void addPlayer(Player player){
		kits.put(player, "traveler");
		this.voted.add(player);
	}

	public void vote(Player player, String kit){
		if(plugin.getInArena().contains(player)){
			if(kit.equalsIgnoreCase("traveler")){
				kits.put(player, "traveler");
			}else if(kit.equalsIgnoreCase("ninja")){
				kits.put(player, "ninja");
			}else if(kit.equalsIgnoreCase("tank")){
				kits.put(player, "tank");
			}else if(kit.equalsIgnoreCase("spy")){
				kits.put(player, "spy");
			}else if(kit.equalsIgnoreCase("god")){
				kits.put(player, "god");
			}else{
				plugin.sendMessage(player, "Kit not found.");
				return;
			}
			
			if(!voted.contains(player)){
				voted.add(player);
			}
			
		}else{
			plugin.sendMessage(player, "You have to be in a arena to vote.");
		}
	}
	
	public void giveItems(){
		for(Player player : voted){
			String kit = kits.get(player);
			
			if(kit.equalsIgnoreCase("traveler")){
				traveler(player);
			}else if(kit.equalsIgnoreCase("ninja")){
				ninja(player);
			}else if(kit.equalsIgnoreCase("tank")){
				tank(player);
			}else if(kit.equalsIgnoreCase("spy")){
				spy(player);
			}else if(kit.equalsIgnoreCase("god")){
				god(player);
			}else{
				plugin.sendMessage(player, "Kit not found. Contact a admin!");
				return;
			}
		}
	}
	
	/**************************************
	 * 
	 *  			Inventory
	 * 
	 *************************************/
	
	public ItemStack kitTool() {
		ItemStack is = new ItemStack(Material.EMERALD);
		ItemMeta im = is.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "Click to chose a kit.");
		im.setLore(lore);
		im.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + "Kit Selection");
		is.setItemMeta(im);
		return is;
	}	
	
	public void createKit(Player player){
		i = Bukkit.getServer().createInventory(null, 9, "Kit Selection");

         player.openInventory(i);
         
         i.setItem(0, traveler());
         
         i.setItem(2, ninja());
         
         i.setItem(4, spy());
         
         i.setItem(6, tank());
         
         i.setItem(8, god());
	}
	
	public void sendPermissions(Player player, String kit){
		player.sendMessage(ChatColor.RED + "You do not have permission for kit " + ChatColor.GOLD + kit + ChatColor.RED + "");
	}
	
	/**************************************
	 * 
	 *  			Kits
	 * 
	 *************************************/
	
	public void traveler(Player player){
		if(player.hasPermission(p.traveler)){
			player.getInventory().addItem(sc.speedCarrot());
			plugin.sendMessage(player, "You have recieved kit " + ChatColor.BLUE + "Traveler" + ChatColor.GRAY + ".");
		}else{
			this.sendPermissions(player, "Traveler");
		}
	}
	
	public void ninja(Player player){
		if(player.hasPermission(p.ninja)){
			player.getInventory().addItem(sc.speedCarrot());
			player.getInventory().addItem(ip.invisiblePie());
			player.getInventory().addItem(cc.confusionCookie());
			player.getInventory().addItem(ss.slownessSteak());
	
			plugin.sendMessage(player, "You have recieved kit " + ChatColor.BLUE + "Ninja" + ChatColor.GRAY + ".");
		}else{
			this.sendPermissions(player, "Ninja");
		}
	}
	
	public void tank(Player player){
		if(player.hasPermission(p.tank)){
			player.getInventory().addItem(ss.slownessSteak());
			player.getInventory().addItem(cc.confusionCookie());
			player.getInventory().addItem(ks.knockbackStick());
			player.getInventory().addItem(ga.goldenApple());

			plugin.sendMessage(player, "You have recieved kit " + ChatColor.BLUE + "Tank" + ChatColor.GRAY + ".");
		}else{
			this.sendPermissions(player, "Tank");
		}
	}
	
	public void spy(Player player){
		if(player.hasPermission(p.spy)){
			player.getInventory().addItem(sc.speedCarrot());
			player.getInventory().addItem(ip.invisiblePie());
			player.getInventory().addItem(cc.confusionCookie());
	
			plugin.sendMessage(player, "You have recieved kit " + ChatColor.BLUE + "Spy" + ChatColor.GRAY + ".");
		}else{
			this.sendPermissions(player, "Spy");
		}
	}
	
	public void god(Player player){
		if(player.hasPermission(p.god)){
			player.getInventory().addItem(sc.speedCarrot());
            player.getInventory().addItem(ip.invisiblePie());
            player.getInventory().addItem(sp.sightPork());
            player.getInventory().addItem(ss.slownessSteak());
            player.getInventory().addItem(cc.confusionCookie());
            player.getInventory().addItem(ga.goldenApple());
            player.getInventory().addItem(ks.knockbackStick());
	
			plugin.sendMessage(player, "You have recieved kit " + ChatColor.BLUE + "God" + ChatColor.GRAY + ".");
		}else{
			this.sendPermissions(player, "God");
		}
	}
	
	/**************************************
	 * 
	 *  			Items
	 * 
	 *************************************/
	
	public ItemStack traveler() {
		ItemStack is = new ItemStack(Material.CHAINMAIL_HELMET);
		ItemMeta im = is.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "Click to use kit.");
		im.setLore(lore);
		im.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + "Traveler");
		is.setItemMeta(im);
		return is;
	}
	
	public ItemStack ninja() {
		ItemStack is = new ItemStack(Material.LEATHER_HELMET);
		ItemMeta im = is.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "Click to use kit.");
		im.setLore(lore);
		im.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + "Ninja");
		is.setItemMeta(im);
		return is;
	}
	
	public ItemStack spy() {
		ItemStack is = new ItemStack(Material.IRON_HELMET);
		ItemMeta im = is.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "Click to use kit.");
		im.setLore(lore);
		im.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + "Spy");
		is.setItemMeta(im);
		return is;
	}
	
	public ItemStack tank() {
		ItemStack is = new ItemStack(Material.GOLD_HELMET);
		ItemMeta im = is.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "Click to use kit.");
		im.setLore(lore);
		im.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + "Tank");
		is.setItemMeta(im);
		return is;
	}
	
	public ItemStack god() {
		ItemStack is = new ItemStack(Material.DIAMOND_HELMET);
		ItemMeta im = is.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "Click to use kit.");
		im.setLore(lore);
		im.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + "God");
		is.setItemMeta(im);
		return is;
	}
	
	/**************************************
	 * 
	 *  			Listeners
	 * 
	 *************************************/
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		
		Player player = event.getPlayer();
		
		if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_AIR ||event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
			
			if(event.getItem().getItemMeta().getDisplayName().equals("" + ChatColor.GOLD + ChatColor.BOLD + "Kit Selection")){

				if(event.getAction() == null || event.getItem() == null){
					return;
				}
				
				this.createKit(event.getPlayer());
				event.setCancelled(true);
			
			}
		}
		
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(event.getAction() == null || event.getItem() == null){
				return;
			}
			
			if(event.getItem() == this.traveler()){
				if(player.hasPermission(p.traveler)){
					event.setCancelled(true);
					vote(player, "Traveler");
				}else{
					this.sendPermissions(player, "Traveler");
				}
				return;
			}
			
			if(event.getItem() == this.ninja()){
				if(player.hasPermission(p.ninja)){
					event.setCancelled(true);
					vote(player, "ninja");
				}else{
					this.sendPermissions(player, "ninja");
				}
				return;
			}
			
			if(event.getItem() == this.spy()){
				if(player.hasPermission(p.spy)){
					event.setCancelled(true);
					vote(player, "spy");
				}else{
					this.sendPermissions(player, "spy");
				}
				return;
			}
			
			if(event.getItem() == this.tank()){
				if(player.hasPermission(p.tank)){
					event.setCancelled(true);
					vote(player, "tank");
				}else{
					this.sendPermissions(player, "tank");
				}
				return;
			}
			
			if(event.getItem() == this.god()){
				if(player.hasPermission(p.god)){
					event.setCancelled(true);
					vote(player, "god");
				}else{
					this.sendPermissions(player, "god");
				}
				return;
			}
		}
}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) return;
		
		Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();
        
        if (clicked == null || clicked.getType() == Material.AIR) return;

        
        if (clicked.getItemMeta().getDisplayName().equals("" + ChatColor.GOLD + ChatColor.BOLD + "Magic Compass")) {
			  	event.setCancelled(true);
			  	this.createKit(player);
				return;
		  } 
		
        if(event.getInventory().equals(i)){
        
		 //TODO
		  
		  
        }
	}
}
