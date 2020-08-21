package fr.entasia.apis.regionManager.api;

import fr.entasia.apis.utils.BasicLocation;
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
					x = Math.min(regionsConfig.getInt(i + ".b1.x"), regionsConfig.getInt(i + ".b2.x"));
					y = Math.min(regionsConfig.getInt(i + ".b1.y"), regionsConfig.getInt(i + ".b2.y"));
					z = Math.min(regionsConfig.getInt(i + ".b1.z"), regionsConfig.getInt(i + ".b2.z"));
					BasicLocation lower = new BasicLocation(x, y, z);
					x = Math.max(regionsConfig.getInt(i + ".b1.x"), regionsConfig.getInt(i + ".b2.x"));
					y = Math.max(regionsConfig.getInt(i + ".b1.y"), regionsConfig.getInt(i + ".b2.y"));
					z = Math.max(regionsConfig.getInt(i + ".b1.z"), regionsConfig.getInt(i + ".b2.z"));
					regs.add(new Region(i.toLowerCase(), lower, new BasicLocation(x, y, z), w));
					Paper.main.getLogger().info("Région " + i + " chargée");
				}
			}
		}else{
			if (!regionsFile.createNewFile()) throw new IOException("File not created");
		}
		Paper.main.getServer().getPluginManager().registerEvents(new RegionEventTrigger(), Paper.main);
	}

//	public static void addRegion(String name, Location b1, Location b2, World w){
//		name = name.toLowerCase();
//		regs.add(new Region(name, b1, b2, w));
//		Bukkit.getLogger().info("Nouvelle région chargée : "+name);
//	}

	public static Region getRegionByName(String name){
		if(name==null)return null;
		name = name.toLowerCase();
		for(Region i : regs){
			if(i.getName().equals(name)){
				return i;
			}
		}
		return null;
	}

	public static List<Region> getRegionsAtLocation(Location loc) {
		if(loc==null)return null;
		ArrayList<Region> reg = new ArrayList<>();
		for(Region r : regs){
			if(r.containsLocation(loc)){
				reg.add(r);
			}
		}
		return reg;
	}
	
}
