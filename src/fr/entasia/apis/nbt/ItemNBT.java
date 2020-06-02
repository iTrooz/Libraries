package fr.entasia.apis.nbt;

import com.sun.istack.internal.NotNull;
import fr.entasia.errors.EntasiaException;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;

public class ItemNBT {

	public static Method getNMSItem, getBukkitItem, setNMSItemTag, getNMSItemTag;



	public static ItemStack setNBT(ItemStack item, NBTComponent nbt) {
		try {
			Object nmsStack = getNMSItem.invoke(null, item);

			setNMSItemTag.invoke(nmsStack, nbt.rawnbt);

			return (ItemStack) getBukkitItem.invoke(null, nmsStack);

		} catch (Exception ex) {
			throw new EntasiaException(ex);
		}
	}

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
		return setNBT(item, nbt);
	}
}
