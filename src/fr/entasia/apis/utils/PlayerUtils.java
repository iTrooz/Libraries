package fr.entasia.apis.utils;

import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.UUID;

public class PlayerUtils {

	public static int getPing(Player p){
		try{
			Method getH = ReflectionUtils.CraftPlayer.getDeclaredMethod("getHandle");
			Object handle = getH.invoke(p);
			Field ping = ReflectionUtils.EntityPlayer.getDeclaredField("ping");

			return (int)ping.get(handle);
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	public static String colorPing(int ping){
		if(ping<100) return "§a"+ping;
		else if(ping<325) return "§3"+ping;
		else return "§c"+ping;
	}

	@Deprecated
	public static String getPing(Player p, boolean color){
		return colorPing(getPing(p));
	}

	public static UUID getUUID(String name){
		return UUID.nameUUIDFromBytes(("OfflinePlayer:"+name).getBytes());
	}
}
