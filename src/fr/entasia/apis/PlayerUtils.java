package fr.entasia.apis;

import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.UUID;

public class PlayerUtils {

	private static Class<?> CraftPlayerClass;
	private static Class<?> EntityPlayerClass;

	static{
		try{
			CraftPlayerClass = Class.forName("org.bukkit.craftbukkit." + ServerUtils.version +".entity.CraftPlayer");
			EntityPlayerClass = Class.forName("net.minecraft.server." + ServerUtils.version +".EntityPlayer");
		}catch(ClassNotFoundException ex){
			ex.printStackTrace();
		}
	}

	public static String getPing(Player p, boolean color){
		try{
			Method getH = CraftPlayerClass.getDeclaredMethod("getHandle");
			Object Handle = getH.invoke(p);

			Field ping = EntityPlayerClass.getDeclaredField("ping");

			if(color){
				int i = Integer.parseInt(ping.get(Handle).toString());
				if(i<100) return "§a"+i;
				else if(i>325) return "§c"+i;
				else return "§3"+i;
			}else return ping.get(Handle).toString();

		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public static UUID getUUID(String name){
		return UUID.nameUUIDFromBytes(("OfflinePlayer:"+name).getBytes());
	}
}
