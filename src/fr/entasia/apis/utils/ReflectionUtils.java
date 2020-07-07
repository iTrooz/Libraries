package fr.entasia.apis.utils;

import net.minecraft.server.v1_12_R1.PlayerConnection;
import org.bukkit.Effect;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

// pour la réflection & les packets (oui je sais le nom est pas bon)
public class ReflectionUtils {

	public static Class<?> CraftPlayer;
	public static Class<?> EntityPlayer;
	public static Class<?> PlayerConnection;
	public static Class<?> Packet;

	static {
		try {
			CraftPlayer = getOBCClass("entity.CraftPlayer");
			EntityPlayer = getNMSClass("EntityPlayer");
			PlayerConnection = getNMSClass("PlayerConnection");
			Packet = getNMSClass("Packet");
		} catch (ReflectiveOperationException ex) {
			ex.printStackTrace();
		}
	}

	public static Class<?> getNMSClass(String c) throws ClassNotFoundException {
		return Class.forName("net.minecraft.server." + ServerUtils.version + "." + c);
	}

	public static Class<?> getOBCClass(String c) throws ClassNotFoundException {
		return Class.forName("org.bukkit.craftbukkit."+ServerUtils.version+"." + c);
	}

	public static void setField(Object obj, String field, Object value) throws ReflectiveOperationException {
		Field f = obj.getClass().getDeclaredField(field);
		f.setAccessible(true);
		f.set(obj, value);
	}

	public static Object getField(Object obj, String field) throws ReflectiveOperationException {
		Field f = obj.getClass().getDeclaredField(field);
		f.setAccessible(true);
		return f.get(obj);
	}

	public static void sendPacket(Player p, Object packet){
		try {
			Method getHandle = CraftPlayer.getDeclaredMethod("getHandle");
			Object o = getHandle.invoke(p);
			Field pc = EntityPlayer.getDeclaredField("playerConnection");
			o = pc.get(o);
			Method send = PlayerConnection.getDeclaredMethod("sendPacket", Packet);
			send.invoke(o, packet);
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}
	}

}
