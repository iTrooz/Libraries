package fr.entasia.apis.utils;

import org.bukkit.util.Vector;

public class OtherUtils {

	public static void limitVector(Vector v, double max){
		if(v.getX()>max)v.setX(max);
		else if(v.getX()<-max)v.setX(-max);

		if(v.getY()>max)v.setY(max);
		else if(v.getY()<-max)v.setY(-max);

		if(v.getZ()>max)v.setZ(max);
		else if(v.getZ()<-max)v.setZ(-max);
	}
	
}
