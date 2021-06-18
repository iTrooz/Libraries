package fr.entasia.apis.utils;

import fr.entasia.errors.MirrorException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

// pour la réflection & les packets (oui je sais le nom est pas bon)
public class ReflectionUtils {

	public static Class<?> CraftMagicNumbers;
	public static Class<?> CraftPlayer;
	public static Class<?> CraftWorld;
	public static Class<?> CraftBlockState;
	public static Class<?> EntityPlayer;
	public static Class<?> PlayerConnection;
	public static Class<?> Packet;
	public static Class<?> MinecraftServer;
	public static Object servInst;

	public static void initBukkit(){
		CraftMagicNumbers = getOBCClass("util.CraftMagicNumbers");
		CraftPlayer = getOBCClass("entity.CraftPlayer");
		CraftWorld = getOBCClass("CraftWorld");
		CraftBlockState = getOBCClass("block.CraftBlockState");
		EntityPlayer = getNMSClass("server.level", "EntityPlayer");
		PlayerConnection = getNMSClass("server.network", "PlayerConnection");
		Packet = getNMSClass("network.protocol", "Packet");
		MinecraftServer = getNMSClass("server", "MinecraftServer");
		servInst = getField(Bukkit.getServer(), "console");
	}

	private static String getPackage(String def){
		if(ServerUtils.getMajorVersion()>16) return def;
		else return "server." + ServerUtils.getVersionStr();
	}

	public static Class<?> getNMSClass(String packag, String c) {
		try{
			return Class.forName("net.minecraft." + getPackage(packag)+ "." + c);
		}catch(ReflectiveOperationException e) {
			throw new MirrorException(e);
		}
	}

	public static Class<?> getOBCClass(String c) {
		try{
			return Class.forName("org.bukkit.craftbukkit."+ServerUtils.getVersionStr() +"." + c);
		}catch(ReflectiveOperationException e){
			throw new MirrorException(e);
		}
	}

	public static void setField(Object obj, String field, Object value) {
		setField(obj.getClass(), obj, field, value);
	}

	public static void setField(Class<?> clazz, Object obj, String field, Object value) {
		try{
			Field f = clazz.getDeclaredField(field);
			f.setAccessible(true);
			f.set(obj, value);
		}catch(ReflectiveOperationException e){
			throw new MirrorException(e);
		}
	}

	public static Object getField(@Nonnull Object obj, String field) {
		return getField(obj.getClass(), obj, field);
	}

	public static Object getField(Class<?> clazz, Object obj, String field) {
		try {
			Field f = clazz.getDeclaredField(field);
			f.setAccessible(true);
			return f.get(obj);
		}catch(ReflectiveOperationException e) {
			throw new MirrorException(e);
		}
	}

	public static Object execMethod(@Nonnull Object obj, String method, Object... args) {
		return execMethod(obj.getClass(), obj, method, args);
	}

	public static Object execMethod(Class<?> clazz, Object obj, String method, Object... args) {
		try {
			Class<?>[] cls = new Class<?>[args.length];
			for(int i=0;i<args.length;i++){
				cls[i] = args[i].getClass();
			}

			Method m = clazz.getDeclaredMethod(method, cls);
			return m.invoke(obj, args);
		}catch(ReflectiveOperationException e) {
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
//			throw new MirrorException(e);
//		}
//	}

	public static Object getNMS(Object o) {
		try{
			Method getHandle = o.getClass().getDeclaredMethod("getHandle");
			return getHandle.invoke(o);
		}catch(ReflectiveOperationException e){
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
			throw new MirrorException(e);
		}
	}

}
