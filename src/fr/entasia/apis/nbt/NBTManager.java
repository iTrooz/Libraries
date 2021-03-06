package fr.entasia.apis.nbt;

import fr.entasia.apis.utils.ReflectionUtils;
import fr.entasia.apis.utils.ServerUtils;
import fr.entasia.errors.MirrorException;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

public class NBTManager {

	public static Class<?> TagCompoundClass;

	public static Method parseNBT;

	public static void init() throws Throwable {

		TagCompoundClass = ReflectionUtils.getNMSClass("nbt", "NBTTagCompound");

		Class<?> MojangsonParserClass = ReflectionUtils.getNMSClass("nbt", "MojangsonParser");
		Class<?> NBTBase = ReflectionUtils.getNMSClass("nbt", "NBTBase");

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

		if(ServerUtils.getMajorVersion()>16){
			NBTComponent.mapField = TagCompoundClass.getDeclaredField("x");
		}else{
			NBTComponent.mapField = TagCompoundClass.getDeclaredField("map");
		}
		NBTComponent.mapField.setAccessible(true);


		// ITEM
		Class<?> NMSItemStackClass = ReflectionUtils.getNMSClass("world.item", "ItemStack");
		Class<?> CraftItemStackClass = ReflectionUtils.getOBCClass("inventory.CraftItemStack");

		ItemNBT.getNMSItem = CraftItemStackClass.getMethod("asNMSCopy", ItemStack.class);
		ItemNBT.getNMSItemTag = NMSItemStackClass.getMethod("getTag");
		ItemNBT.setNMSItemTag = NMSItemStackClass.getMethod("setTag", TagCompoundClass);
		ItemNBT.getBukkitMeta = CraftItemStackClass.getMethod("getItemMeta", NMSItemStackClass);
//			ItemNBT.getBukkitItem = CraftItemStackClass.getMethod("asBukkitCopy", NMSItemStackClass);


		// ENTITY
		Class<?> NMSEntityClass = ReflectionUtils.getNMSClass("world.entity", "Entity");
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
