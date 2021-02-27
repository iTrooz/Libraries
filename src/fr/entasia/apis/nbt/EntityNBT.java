package fr.entasia.apis.nbt;

import fr.entasia.errors.MirrorException;
import org.bukkit.entity.Entity;

import java.lang.reflect.Method;

public class EntityNBT {

	public static Method getNMSEntity, setNMSEntityNBT, getNMSEntityNBT;

	public static NBTComponent getNBT(Entity entity){
		try{
			Object nmsEntity = getNMSEntity.invoke(entity);
			Object a = NBTManager.TagCompoundClass.newInstance();
			getNMSEntityNBT.invoke(nmsEntity, a);
			return new NBTComponent(a);
		}catch(ReflectiveOperationException e){
			throw new MirrorException(e);
		}
	}

	public static void setNBT(Entity entity, NBTComponent nbt) {
		try {
			Object nmsEntity = getNMSEntity.invoke(entity);
			setNMSEntityNBT.invoke(nmsEntity, nbt.rawnbt);
		} catch (ReflectiveOperationException e) {
			throw new MirrorException(e);
		}
	}

	public static void addNBT(Entity entity, NBTComponent add){
		NBTComponent nbt = getNBT(entity);
//		if(nbt==null)nbt = new NBTComponent(); // pas necessaire normalement ?
		nbt.fusion(add);
		setNBT(entity, nbt);
	}
}
