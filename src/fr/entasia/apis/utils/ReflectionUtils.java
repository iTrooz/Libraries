package fr.entasia.apis.utils;

import fr.entasia.errors.MirrorException;
import net.minecraft.server.v1_12_R1.IBlockData;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_12_R1.block.CraftBlock;
import org.bukkit.craftbukkit.v1_12_R1.block.CraftBlockState;
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

	static {
		CraftMagicNumbers = getOBCClass("util.CraftMagicNumbers");
		CraftPlayer = getOBCClass("entity.CraftPlayer");
		CraftBlockState = getOBCClass("block.CraftBlockState");
		EntityPlayer = getNMSClass("EntityPlayer");
		PlayerConnection = getNMSClass("PlayerConnection");
		Packet = getNMSClass("Packet");
	}

	public static Class<?> getNMSClass(String c) {
		try{
			return Class.forName("net.minecraft.server." + ServerUtils.version + "." + c);
		}catch(ReflectiveOperationException e) {
			e.printStackTrace();
			throw new MirrorException(e);
		}
	}

	public static Class<?> getOBCClass(String c) {
		try{
			return Class.forName("org.bukkit.craftbukkit."+ServerUtils.version+"." + c);
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
