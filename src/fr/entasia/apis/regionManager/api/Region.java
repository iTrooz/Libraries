package fr.entasia.apis.regionManager.api;

import fr.entasia.apis.utils.BasicLocation;
import org.bukkit.Location;
import org.bukkit.World;

public class Region {

	private final World world;
	private final BasicLocation bound1, bound2;
	private final String name;
	
	public Region(String n, BasicLocation b1, BasicLocation b2, World w){
		name = n;
		bound1 = b1;
		bound2 = b2;
		world = w;
	}

	private boolean between(int a, int min, int max){
		return a>=min&&a<=max;
	}

	public boolean containsLocation(Location loc) {
		return (loc.getWorld() == getWorld()&& between(loc.getBlockX(), bound1.x, bound2.x) &&
				between(loc.getBlockY(), bound1.y, bound2.y) &&
				between(loc.getBlockZ(), bound1.z, bound2.z));
	}
	
	public String getName(){
		return name;
	}
	
	public BasicLocation getLowerBound(){
		return bound1;
	}
	
	public BasicLocation getUpperBound(){
		return bound2;
	}
	
	public World getWorld() {
		return world;
	}

	public String toString() {
		return "Region["+name+"]";
	}
	
//	public List<Player> getPlayersIn(){
//		return players;
//	}

//	public void save(){
//		try {
//			Core.getFileConfiguration().set("regions."+name+".world", w.getName());
//			Core.getFileConfiguration().set("regions."+name+".first_bound", bound1.getBlockX()+";"+bound1.getBlockY()+";"+bound1.getBlockZ());
//			Core.getFileConfiguration().set("regions."+name+".second_bound", bound2.getBlockX()+";"+bound2.getBlockY()+";"+bound2.getBlockZ());
//			Core.getFileConfiguration().save(Core.getConfigFile());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
}
