package fr.entasia.apis.nbt;

import fr.entasia.apis.ServerUtils;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

public class NBTer {

	public static Class<?> TagCompoundClass;

	public static Method parseNBT;

	public static void init(){
		try{
//			net.minecraft.server.v1_9_R2.NBTTagCompound
			TagCompoundClass = Class.forName("net.minecraft.server."+ ServerUtils.version + ".NBTTagCompound");

			Class<?> MojangsonParserClass = Class.forName("net.minecraft.server."+ServerUtils.version + ".MojangsonParser");
			Class<?> NBTBase = Class.forName("net.minecraft.server."+ServerUtils.version + ".NBTBase");

			parseNBT = MojangsonParserClass.getDeclaredMethod("parse", String.class);

			NBTComponent.fusion = TagCompoundClass.getDeclaredMethod("a", TagCompoundClass);
			NBTComponent.setPreciseTag = TagCompoundClass.getDeclaredMethod("set", String.class, NBTBase);

			NBTComponent.delString = TagCompoundClass.getDeclaredMethod("remove", String.class);
			NBTComponent.setString = TagCompoundClass.getDeclaredMethod("setString", String.class, String.class);
			NBTComponent.getString = TagCompoundClass.getDeclaredMethod("getString", String.class);

			NBTComponent.getList = TagCompoundClass.getDeclaredMethod("remove", String.class);
			NBTComponent.setString = TagCompoundClass.getDeclaredMethod("setString", String.class, String.class);
			NBTComponent.getString = TagCompoundClass.getDeclaredMethod("getString", String.class);
			NBTComponent.getCompound = TagCompoundClass.getDeclaredMethod("getCompound", String.class);

//			NBTComponent.mapProperty = TagCompoundClass.getDeclaredField("map");
//			NBTComponent.mapProperty.setAccessible(true);

//			Class<?> mapClass = HashMap.class;
//
//			NBTComponent.mapGetter = mapClass.getDeclaredMethod("get", Object.class);

			Class<?> NMSItemStackClass = Class.forName("net.minecraft.server."+ServerUtils.version + ".ItemStack");
			Class<?> CraftItemStackClass = Class.forName("org.bukkit.craftbukkit."+ServerUtils.version + ".inventory.CraftItemStack");

			ItemNBT.getNMSItem = CraftItemStackClass.getMethod("asNMSCopy", ItemStack.class);
			ItemNBT.setNMSItemTag = NMSItemStackClass.getMethod("setTag", TagCompoundClass);
			ItemNBT.getBukkitItem = CraftItemStackClass.getMethod("asBukkitCopy", NMSItemStackClass);
			ItemNBT.getNMSItemTag = NMSItemStackClass.getMethod("getTag");


			Class<?> NMSEntityClass = Class.forName("net.minecraft.server."+ServerUtils.version + ".Entity");
			Class<?> CraftEntityClass = Class.forName("org.bukkit.craftbukkit."+ServerUtils.version + ".entity.CraftEntity");

			EntityNBT.getNMSEntity = CraftEntityClass.getDeclaredMethod("getHandle");

			if(ServerUtils.version.startsWith("v1_9_R2")||ServerUtils.version.startsWith("v1_10")||ServerUtils.version.startsWith("v1_11")){
				EntityNBT.getNMSEntityNBT = NMSEntityClass.getDeclaredMethod("e", TagCompoundClass);
			}else{
				EntityNBT.getNMSEntityNBT = NMSEntityClass.getDeclaredMethod("save", TagCompoundClass);

			}

			EntityNBT.setNMSEntityNBT = NMSEntityClass.getDeclaredMethod("f", TagCompoundClass);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	@Deprecated
	public static NBTComponent parseNBT(String rawNBT) {
		return new NBTComponent(rawParseNBT(rawNBT));
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
