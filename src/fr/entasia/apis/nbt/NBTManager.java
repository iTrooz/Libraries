package fr.entasia.apis.nbt;

import fr.entasia.apis.utils.ServerUtils;
import fr.entasia.errors.MirrorException;
import net.minecraft.server.v1_15_R1.Entity;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

public class NBTManager {

	public static Class<?> TagCompoundClass;

	public static Method parseNBT;

	public static void init() throws Throwable {
		TagCompoundClass = Class.forName("net.minecraft.server." + ServerUtils.getVersionStr() + ".NBTTagCompound");

		Class<?> MojangsonParserClass = Class.forName("net.minecraft.server." + ServerUtils.getVersionStr() + ".MojangsonParser");
		Class<?> NBTBase = Class.forName("net.minecraft.server." + ServerUtils.getVersionStr() + ".NBTBase");

		parseNBT = MojangsonParserClass.getDeclaredMethod("parse", String.class);

		NBTComponent.fusion = TagCompoundClass.getDeclaredMethod("a", TagCompoundClass);
		NBTComponent.setPreciseTag = TagCompoundClass.getDeclaredMethod("set", String.class, NBTBase);

		NBTComponent.delKey = TagCompoundClass.getDeclaredMethod("remove", String.class);

		for (NBTTypes type : NBTTypes.values()) {
			type.getter = TagCompoundClass.getDeclaredMethod("get" + type.name(), String.class);
			type.setter = TagCompoundClass.getDeclaredMethod("set" + type.name(), String.class, type.type);
		}

		NBTComponent.getList = TagCompoundClass.getDeclaredMethod("remove", String.class);
		NBTComponent.getAny = TagCompoundClass.getDeclaredMethod("get", String.class);

		NBTComponent.mapField = TagCompoundClass.getDeclaredField("map");
		NBTComponent.mapField.setAccessible(true);


		// ITEM
		Class<?> NMSItemStackClass = Class.forName("net.minecraft.server." + ServerUtils.getVersionStr() + ".ItemStack");
		Class<?> CraftItemStackClass = Class.forName("org.bukkit.craftbukkit." + ServerUtils.getVersionStr() + ".inventory.CraftItemStack");

		ItemNBT.getNMSItem = CraftItemStackClass.getMethod("asNMSCopy", ItemStack.class);
		ItemNBT.getNMSItemTag = NMSItemStackClass.getMethod("getTag");
		ItemNBT.setNMSItemTag = NMSItemStackClass.getMethod("setTag", TagCompoundClass);
		ItemNBT.getBukkitMeta = CraftItemStackClass.getMethod("getItemMeta", NMSItemStackClass);
//			ItemNBT.getBukkitItem = CraftItemStackClass.getMethod("asBukkitCopy", NMSItemStackClass);


		// ENTITY
		Class<?> NMSEntityClass = Class.forName("net.minecraft.server." + ServerUtils.getVersionStr() + ".Entity");
		Class<?> CraftEntityClass = Class.forName("org.bukkit.craftbukkit." + ServerUtils.getVersionStr() + ".entity.CraftEntity");

		EntityNBT.getNMSEntity = CraftEntityClass.getDeclaredMethod("getHandle");

		if (ServerUtils.getMajorVersion()<12) {
			EntityNBT.getNMSEntityNBT = NMSEntityClass.getDeclaredMethod("e", TagCompoundClass);
		} else {
			EntityNBT.getNMSEntityNBT = NMSEntityClass.getDeclaredMethod("save", TagCompoundClass);
		}
		if (ServerUtils.getMajorVersion()<16) {
			EntityNBT.setNMSEntityNBT = NMSEntityClass.getDeclaredMethod("f", TagCompoundClass);
		}else{
			EntityNBT.setNMSEntityNBT = NMSEntityClass.getDeclaredMethod("save", TagCompoundClass);
		}
	}

	protected static Object rawParseNBT(String rawNBT) {
		try {
			return parseNBT.invoke(null, rawNBT);
		} catch (ReflectiveOperationException e) {
			throw new MirrorException(e);
		}
	}
}
