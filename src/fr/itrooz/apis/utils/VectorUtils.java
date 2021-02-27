package fr.itrooz.apis.utils;

import org.bukkit.util.Vector;

public class VectorUtils {

	public static void limitVector(Vector v){
		limitVector(v, 4);
	}

	public static void limitVector(Vector v, double max){
		if(v.getX()>max)v.setX(max);
		else if(v.getX()<-max)v.setX(-max);

		if(v.getY()>max)v.setY(max);
		else if(v.getY()<-max)v.setY(-max);

		if(v.getZ()>max)v.setZ(max);
		else if(v.getZ()<-max)v.setZ(-max);
	}

//	public static void scaleVector(Vector v, double max){
//		if(v.getX()>max)v.setX(max);
//		else if(v.getX()<-max)v.setX(-max);
//
//		if(v.getY()>max)v.setY(max);
//		else if(v.getY()<-max)v.setY(-max);
//
//		if(v.getZ()>max)v.setZ(max);
//		else if(v.getZ()<-max)v.setZ(-max);
//	}
}
