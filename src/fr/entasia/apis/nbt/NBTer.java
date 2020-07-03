package fr.entasia.apis.nbt;

import fr.entasia.apis.utils.ServerUtils;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

public class NBTer {

	/*
		NBTComponent.mapProperty = TagCompoundClass.getDeclaredField("map");
		NBTComponent.mapProperty.setAccessible(true);

		Class<?> mapClass = HashMap.class;

		NBTComponent.mapGetter = mapClass.getDeclaredMethod("get", Object.class);
	 */

	public static Class<?> TagCompoundClass;

	public static Method parseNBT;

	public static void init() throws Throwable {
		TagCompoundClass = Class.forName("net.minecraft.server." + ServerUtils.version + ".NBTTagCompound");

		Class<?> MojangsonParserClass = Class.forName("net.minecraft.server." + ServerUtils.version + ".MojangsonParser");
		Class<?> NBTBase = Class.forName("net.minecraft.server." + ServerUtils.version + ".NBTBase");

		parseNBT = MojangsonParserClass.getDeclaredMethod("parse", String.class);

		NBTComponent.fusion = TagCompoundClass.getDeclaredMethod("a", TagCompoundClass);
		NBTComponent.setPreciseTag = TagCompoundClass.getDeclaredMethod("set", String.class, NBTBase);

		NBTComponent.delKey = TagCompoundClass.getDeclaredMethod("remove", String.class);

		for (NBTTypes type : NBTTypes.values()) {
			type.getter = TagCompoundClass.getDeclaredMethod("get" + type.name(), String.class);
			type.setter = TagCompoundClass.getDeclaredMethod("set" + type.name(), String.class, type.type);
		}

		NBTComponent.getList = TagCompoundClass.getDeclaredMethod("remove", String.class);
		NBTComponent.getCompound = TagCompoundClass.getDeclaredMethod("getCompound", String.class);


		// ITEM
		Class<?> NMSItemStackClass = Class.forName("net.minecraft.server." + ServerUtils.version + ".ItemStack");
		Class<?> CraftItemStackClass = Class.forName("org.bukkit.craftbukkit." + ServerUtils.version + ".inventory.CraftItemStack");

		ItemNBT.getNMSItem = CraftItemStackClass.getMethod("asNMSCopy", ItemStack.class);
		ItemNBT.getNMSItemTag = NMSItemStackClass.getMethod("getTag");
		ItemNBT.setNMSItemTag = NMSItemStackClass.getMethod("setTag", TagCompoundClass);
		ItemNBT.getBukkitMeta = CraftItemStackClass.getMethod("getItemMeta", NMSItemStackClass);
//			ItemNBT.getBukkitItem = CraftItemStackClass.getMethod("asBukkitCopy", NMSItemStackClass);


		// ENTITY
		Class<?> NMSEntityClass = Class.forName("net.minecraft.server." + ServerUtils.version + ".Entity");
		Class<?> CraftEntityClass = Class.forName("org.bukkit.craftbukkit." + ServerUtils.version + ".entity.CraftEntity");

		EntityNBT.getNMSEntity = CraftEntityClass.getDeclaredMethod("getHandle");

		if (ServerUtils.version.startsWith("v1_9_R2") || ServerUtils.version.startsWith("v1_10") || ServerUtils.version.startsWith("v1_11")) {
			EntityNBT.getNMSEntityNBT = NMSEntityClass.getDeclaredMethod("e", TagCompoundClass);
		} else {
			EntityNBT.getNMSEntityNBT = NMSEntityClass.getDeclaredMethod("save", TagCompoundClass);

		}
		EntityNBT.setNMSEntityNBT = NMSEntityClass.getDeclaredMethod("f", TagCompoundClass);
	}

	protected static Object rawParseNBT(String rawNBT) {
		try {
			return parseNBT.invoke(null, rawNBT);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
