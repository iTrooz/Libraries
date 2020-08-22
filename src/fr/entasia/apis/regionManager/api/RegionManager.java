package fr.entasia.apis.regionManager.api;

import fr.entasia.apis.utils.BasicLocation;
import fr.entasia.libraries.Common;
import fr.entasia.libraries.paper.Paper;
import fr.entasia.libraries.paper.listeners.RegionEventTrigger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RegionManager {

	public static File regionsFile = new File(Paper.main.getDataFolder()+"/regions.yml");
	public static FileConfiguration regionsConfig;

	private static final ArrayList<Region> regs = new ArrayList<>();

	public static void init() throws Throwable {
		if (regionsFile.exists()) {
			regionsConfig = YamlConfiguration.loadConfiguration(regionsFile);
			int x, y, z;
			World w;
			for (String i : regionsConfig.getKeys(false)) {
				w = Bukkit.getWorld(regionsConfig.getString(i + ".world"));
				if (w == null) Paper.main.getLogger().info("Région " + i + " non chargée : monde invalide");
				else {
					BasicLocation bound1 = new BasicLocation();
					bound1.x = regionsConfig.getInt(i + ".b1.x");
					bound1.y = regionsConfig.getInt(i + ".b1.y");
					bound1.z = regionsConfig.getInt(i + ".b1.z");
					BasicLocation bound2 = new BasicLocation();
					bound2.x = regionsConfig.getInt(i + ".b2.x");
					bound2.y = regionsConfig.getInt(i + ".b2.y");
					bound2.z = regionsConfig.getInt(i + ".b2.z");
					registerRegion(i, w, bound1, bound2);
					Paper.main.getLogger().info("Région " + i + " chargée");
				}
			}
		}else{
			if (!regionsFile.createNewFile()) throw new IOException("File not created");
		}
		Paper.main.getServer().getPluginManager().registerEvents(new RegionEventTrigger(), Paper.main);
	}

	public static void registerRegion(String name, World w, BasicLocation bound1, BasicLocation bound2){
		BasicLocation a = new BasicLocation();
		a.x = Math.min(bound1.getX(), bound2.getX());
		a.y = Math.min(bound1.getX(), bound2.getX());
		a.z = Math.min(bound1.getX(), bound2.getX());
		BasicLocation b = new BasicLocation();
		b.x = Math.min(bound1.getX(), bound2.getX());
		b.y = Math.min(bound1.getX(), bound2.getX());
		b.z = Math.min(bound1.getX(), bound2.getX());
		regs.add(new Region(name.toLowerCase(), w, b, b));
	}

	public static Region getRegionByName(String name){
		return getRegion(name);
	}
	public static Region getRegion(String name){
		if(name==null)return null;
		name = name.toLowerCase();
		for(Region i : regs){
			if(i.getName().equals(name)){
				return i;
			}
		}
		return null;
	}

	@Deprecated
	public static Set<Region> getRegionsAtLocation(Location loc) {
		return getRegionsAt(loc);
	}

	public static Set<Region> getRegionsAt(Location loc) {
		if(loc==null)return null;
		HashSet<Region> set = new HashSet<>();
		for(Region r : regs){
			if(r.containsLocation(loc)){
				set.add(r);
			}
		}
		return set;
	}
	
}
