package fr.entasia.apis.nbt;

import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

public class ItemNBT {

	public static Method getNMSItem, getBukkitItem, setNMSItemTag, getNMSItemTag;


	public static ItemStack setNBT(ItemStack item, NBTComponent nbt) {
		try {
			Object nmsStack = getNMSItem.invoke(null, item);

			setNMSItemTag.invoke(nmsStack, nbt.rawnbt);

			return (ItemStack) getBukkitItem.invoke(null, nmsStack);

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static NBTComponent getNBT(ItemStack item){
		try{
			Object nmsStack = getNMSItem.invoke(null, item);

			Object a = getNMSItemTag.invoke(nmsStack);
			if(a==null)return new NBTComponent("{}");
			else return new NBTComponent(a);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
}
