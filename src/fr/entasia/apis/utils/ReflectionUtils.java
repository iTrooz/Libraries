package fr.entasia.apis.utils;

import fr.entasia.errors.MirrorException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

// pour la réflection & les packets (oui je sais le nom est pas bon)
public class ReflectionUtils {

	public static Class<?> CraftMagicNumbers;
	public static Class<?> CraftPlayer;
	public static Class<?> CraftBlockState;
	public static Class<?> EntityPlayer;
	public static Class<?> PlayerConnection;
	public static Class<?> Packet;
	public static Class<?> MinecraftServer;
	public static Object servInst;

	public static void initBukkit(){
		CraftMagicNumbers = getOBCClass("util.CraftMagicNumbers");
		CraftPlayer = getOBCClass("entity.CraftPlayer");
		CraftBlockState = getOBCClass("block.CraftBlockState");
		EntityPlayer = getNMSClass("EntityPlayer");
		PlayerConnection = getNMSClass("PlayerConnection");
		Packet = getNMSClass("Packet");
		MinecraftServer = getNMSClass("MinecraftServer");
		servInst = getField(Bukkit.getServer(), "console");
	}

	public static Class<?> getNMSClass(String c) {
		try{
			return Class.forName("net.minecraft.server." + ServerUtils.getVersionStr() + "." + c);
		}catch(ReflectiveOperationException e) {
			e.printStackTrace();
			throw new MirrorException(e);
		}
	}

	public static Class<?> getOBCClass(String c) {
		try{
			return Class.forName("org.bukkit.craftbukkit."+ServerUtils.getVersionStr() +"." + c);
		}catch(ReflectiveOperationException e){
			e.printStackTrace();
			throw new MirrorException(e);
		}
	}

	public static void setField(Object obj, String field, Object value) {
		try{
			Field f = obj.getClass().getDeclaredField(field);
			f.setAccessible(true);
			f.set(obj, value);
		}catch(ReflectiveOperationException e){
			e.printStackTrace();
			throw new MirrorException(e);
		}
	}

	public static Object getField(Object obj, String field) {
		try {
			Field f = obj.getClass().getDeclaredField(field);
			f.setAccessible(true);
			return f.get(obj);
		}catch(ReflectiveOperationException e) {
			e.printStackTrace();
			throw new MirrorException(e);
		}
	}

//	public static Object getNMS(Block b) {
//		try{
//			CraftMagicNumbers
//			Method method = CraftBlockState.getDeclaredMethod("getHandle");
//			Object i = method.invoke(b.getState());
//			method = i.getClass().getDeclaredMethod("getBlock");
//			return method.invoke(i);
//		}catch(ReflectiveOperationException e){
//			e.printStackTrace();
//			throw new MirrorException(e);
//		}
//	}

	public static Object getNMS(Object o) {
		try{
			Method getHandle = o.getClass().getDeclaredMethod("getHandle");
			return getHandle.invoke(o);
		}catch(ReflectiveOperationException e){
			e.printStackTrace();
			throw new MirrorException(e);
		}
	}


	public static void sendPacket(Player p, Object packet) {
		try {
			Object o = getNMS(p);
			Field pc = EntityPlayer.getDeclaredField("playerConnection");
			o = pc.get(o);
			Method send = PlayerConnection.getDeclaredMethod("sendPacket", Packet);
			send.invoke(o, packet);
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}
	}

}
