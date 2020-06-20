package fr.entasia.apis.nbt;

import fr.entasia.errors.EntasiaException;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.lang.reflect.Method;

public class ItemNBT {

	public static Method getNMSItem, getBukkitMeta, setNMSItemTag, getNMSItemTag;


	public static ItemStack setNBT(ItemStack item, NBTComponent nbt) {
		try {
			Object nmsStack = getNMSItem.invoke(null, item);

			setNMSItemTag.invoke(nmsStack, nbt.rawnbt);

			ItemMeta meta = (ItemMeta) getBukkitMeta.invoke(null, nmsStack);
			item.setItemMeta(meta);
			return item;
		} catch (Exception ex) {
			throw new EntasiaException(ex);
		}
	}

	@Nullable
	public static NBTComponent getNBT(ItemStack item){
		try{
			Object nmsStack = getNMSItem.invoke(null, item);

			Object a = getNMSItemTag.invoke(nmsStack);
			if(a==null)return null;
			else return new NBTComponent(a);
		}catch(Exception ex){
			throw new EntasiaException(ex);
		}
	}


	public static ItemStack addNBT(ItemStack item, NBTComponent add){
		NBTComponent nbt = getNBT(item);
		if(nbt==null)nbt = new NBTComponent();
		nbt.fusion(add);
		setNBT(item, nbt);
		return item;
	}
}
