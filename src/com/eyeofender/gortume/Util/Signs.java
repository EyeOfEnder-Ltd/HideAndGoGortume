package com.eyeofender.gortume.Util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import com.eyeofender.gortume.HideAndGo;
import com.eyeofender.gortume.Game.GameManager;

public class Signs {
	
	private  HideAndGo plugin;
	public  List<Sign> signs = new ArrayList<Sign>();
    public  List<String> signsString = new ArrayList<String>();

    public Signs(HideAndGo plugin){
		this.plugin = plugin;
	}
	
	public  void updateSigns(){		
		if (signs.size() == 0)
             return;
         
         for (Sign s : signs) {
        	 if (s.getLocation().getBlock().getTypeId() == 63 || s.getLocation().getBlock().getTypeId() == 68) {
        		         		  
        		  String arena = s.getLine(1);
        		  plugin.sendConsole(arena);
        		  /**GameManager gm = plugin.getGameManager(arena);
        		  
        		  if(gm.isInLobby()){
        			  if(gm.getArenaPlayers().size() < plugin.getConfigHelper().getMaxPlayers()){
        				  s.setLine(0, ChatColor.GREEN + "[Join]");
        			  }else{
        				  s.setLine(0, ChatColor.RED + "[Full]");
        			  }
        		  }else{
        			  s.setLine(0, ChatColor.RED + "[In-Game]");
        		  }
        		  
        		  s.setLine(1, gm.getArenaName());
        		  s.setLine(2, ChatColor.GRAY + "" + ChatColor.BOLD + gm.getArenaPlayers().size() + "/" + plugin.getConfigHelper().getMaxPlayers());
        		  
        		  if(gm.isInLobby()){
        			  s.setLine(3, ChatColor.GOLD + "-=- Lobby -=-");
        		  }else{
        			  s.setLine(3, ChatColor.GRAY + "-=- In-Game -=-");
        		  }
        		  **/
        		  
        	 }else{
        		 signs.remove(s);
        	 }
         }         
	}
	
	 public  void unregisterSign(Sign sign) {
         
         if (signs.contains(sign))
                 signs.remove(sign);
         
	 }
 
	 public  void registerSign(Sign sign) {
         
         if (signs.contains(sign)){
        	 return;
                 
         }
         
         signs.add(sign);
         
	 }
	 
	 public  void saveSigns() {
         
         signsString.clear();
         
         for (Sign sign : signs) {
                 String string = locationToString(sign.getLocation());
                 signsString.add(string);
         }
         
         plugin.getConfig().set("signs", signsString);
         plugin.saveConfig();
         
	 }

	 public  String locationToString(Location loc) {
         return loc.getWorld().getName() + "%" + loc.getX() + "%" + loc.getY() + "%" + loc.getZ();
	 }
	 
	 public  void loadSigns() {
         
         signsString = plugin.getConfig().getStringList("signs");
         
         if (signsString == null) 
                 return;
         
         for (String s : signsString) {
                 Location loc = setLocation(s);
                 Block block = loc.getBlock();
                 
                 if (block.getTypeId() == 63 || block.getTypeId() == 68) {
                         Sign sign = (Sign) block.getState();
                         signs.add(sign);
                 }
         }
         
	 }
	 
	 public  Location setLocation(String string) {
         String[] s = string.split("%");
         Location loc = new Location(Bukkit.getWorld(s[0]), Double.parseDouble(s[1]),
                         Double.parseDouble(s[2]), Double.parseDouble(s[3]));
         return loc;
	 }
}
