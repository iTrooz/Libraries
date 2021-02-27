package fr.itrooz.apis.utils;

import org.bukkit.Location;

public class BasicLocation {
	public int x;
	public int y;
	public int z;

	public BasicLocation(){

	}

	public BasicLocation(Location loc){
		this(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}

	public BasicLocation(int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public int getZ(){
		return z;
	}

	public String toString(){
		return "BasicLocation["+x+";"+y+";"+z+"]";
	}
}
