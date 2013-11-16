package com.eyeofender.gortume.handlers;

import java.util.ArrayList;
import java.util.List;

import com.eyeofender.gortume.HideAndGo;

public class RankHandler {
	
	private HideAndGo plugin;

    public RankHandler(HideAndGo plugin) {
        this.plugin = plugin;
    }
	
	List<String> developers = new ArrayList<String>();
	List<String> owners = new ArrayList<String>();
	List<String> mods = new ArrayList<String>();
	List<String> admins = new ArrayList<String>();
	
	public List<String> getAdmins() {
		return admins;
	}

	public void setAdmins(List<String> admins) {
		this.admins = admins;
	}

	public List<String> getMods() {
		return mods;
	}

	public void setMods(List<String> mods) {
		this.mods = mods;
	}

	public void init(){
		 if (plugin.getConfig().contains("developers")) {
			 developers = plugin.getConfig().getStringList("developers");
		 }
		 if (plugin.getConfig().contains("owners")) {
			 owners = plugin.getConfig().getStringList("owners");
		 }
		 if (plugin.getConfig().contains("mods")) {
			 mods = plugin.getConfig().getStringList("mods");
		 } if (plugin.getConfig().contains("admins")) {
			 admins = plugin.getConfig().getStringList("admins");
		 }
	}
	
	public List<String> getOwners() {
		return owners;
	}

	public void setOwners(List<String> owners) {
		this.owners = owners;
	}

	public List<String> getDevelopers() {
		return developers;
	}

	public void setDevelopers(List<String> developers) {
		this.developers = developers;
	}
}
