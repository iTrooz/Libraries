package fr.entasia.apis.nbt;

import fr.entasia.errors.EntasiaException;
import org.bukkit.entity.Entity;

import java.lang.reflect.Method;

public class EntityNBT {

	public static Method getNMSEntity, setNMSEntityNBT, getNMSEntityNBT;

	public static NBTComponent getNBT(Entity entity){
		try{
			Object nmsEntity = getNMSEntity.invoke(entity);
			Object a = NBTer.TagCompoundClass.newInstance();
			getNMSEntityNBT.invoke(nmsEntity, a);
			return new NBTComponent(a);
		}catch(Exception ex){
			throw new EntasiaException(ex);
		}
	}

	public static void setNBT(Entity entity, NBTComponent nbt) {
		try {
			Object nmsEntity = getNMSEntity.invoke(entity);
			setNMSEntityNBT.invoke(nmsEntity, nbt.rawnbt);
		} catch (Exception ex) {
			throw new EntasiaException(ex);
		}
	}

	public static void addNBT(Entity entity, NBTComponent add){
		NBTComponent nbt = getNBT(entity);
		if(nbt==null)nbt = new NBTComponent();
		nbt.fusion(add);
		setNBT(entity, nbt);
	}
}
