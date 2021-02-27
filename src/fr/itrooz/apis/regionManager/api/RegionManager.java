package fr.itrooz.apis.regionManager.api;

import fr.itrooz.apis.utils.BasicLocation;
import fr.itrooz.libraries.paper.Paper;
import fr.itrooz.libraries.paper.listeners.RegionEventTrigger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
				}
			}
		}else{
			if (!regionsFile.createNewFile()) throw new IOException("File not created");
		}
		Paper.main.getServer().getPluginManager().registerEvents(new RegionEventTrigger(), Paper.main);
	}

	public static Region registerRegion(String name, World w, BasicLocation t1, BasicLocation t2){
		BasicLocation bound1 = new BasicLocation();
		bound1.x = Math.min(t1.getX(), t2.getX());
		bound1.y = Math.min(t1.getY(), t2.getY());
		bound1.z = Math.min(t1.getZ(), t2.getZ());
		BasicLocation bound2 = new BasicLocation();
		bound2.x = Math.max(t1.getX(), t2.getX());
		bound2.y = Math.max(t1.getY(), t2.getY());
		bound2.z = Math.max(t1.getZ(), t2.getZ());
		Region reg = new Region(name.toLowerCase(), w, bound1, bound2);
		regs.add(reg);
		Paper.main.getLogger().info("Région " + name + " chargée");
		return reg;
	}

	@Deprecated
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
	public static List<Region> getRegionsAtLocation(Location loc) {
		return getRegionsAt(loc);
	}

	public static List<Region> getRegionsAt(Location loc) {
		if(loc==null)return null;
		List<Region> list = new ArrayList<>();
		for(Region r : regs){
			if(r.containsLocation(loc)){
				list.add(r);
			}
		}
		return list;
	}
	
}
