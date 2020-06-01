package fr.entasia.apis.nbt;

import org.bukkit.entity.Entity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;

public class EntityNBT {

	public static Method getNMSEntity, setNMSEntityNBT, getNMSEntityNBT;


	public static void setNBT(Entity entity, NBTComponent nbt) {
		try {
			Object nmsEntity = getNMSEntity.invoke(entity);
			setNMSEntityNBT.invoke(nmsEntity, nbt.rawnbt);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Nullable
	public static NBTComponent getNBT(Entity entity){
		try{
			Object nmsEntity = getNMSEntity.invoke(entity);
			Object a = NBTer.TagCompoundClass.newInstance();
			getNMSEntityNBT.invoke(nmsEntity, a);
			if(a.equals(""))return null;
			return new NBTComponent(a);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	public static void addNBT(Entity entity, NBTComponent add){
		NBTComponent nbt = getNBT(entity);
		if(nbt==null)return;
		nbt.fusion(add);
		setNBT(entity, nbt);
	}
}
