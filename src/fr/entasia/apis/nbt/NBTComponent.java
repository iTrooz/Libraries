package fr.entasia.apis.nbt;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

public class NBTComponent {

	public static Method fusion, setPreciseTag, delKey, getList, setList, getAny;
	public static Field mapField;

	public Map<String, Object> map;
	protected Object rawnbt;

	public Object getRawNBT(){
		return rawnbt;
	}

	public String getNBT(){
		return rawnbt.toString();
	}

	protected NBTComponent(Object nbt){
		this.rawnbt = nbt;
		try{
			map = (Map<String, Object>) mapField.get(nbt);
		}catch(ReflectiveOperationException e){
			e.printStackTrace();
		}
	}

	public NBTComponent(String nbt){
		this(NBTer.rawParseNBT(nbt));
	}

	public NBTComponent(){
		this(NBTer.rawParseNBT("{}"));
	}


	public boolean isEmpty() {
		return map.size()==0;
	}

	public void setComponent(String key, NBTComponent toAdd) {
		try{
			setPreciseTag.invoke(rawnbt, key, toAdd.rawnbt);
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}
	}

	public void fusion(NBTComponent toAdd) {
		try{
			fusion.invoke(rawnbt, toAdd.rawnbt);
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}
	}



	public void delKey(String key) {
		try{
			delKey.invoke(rawnbt, key);
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}
	}

	public void setValue(NBTTypes type, String key, Object value) {
		try{
			type.setter.invoke(rawnbt, key, value);
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}
	}

	public Object getValue(NBTTypes type, String key) { // peut return un string vide
		try{
			return type.getter.invoke(rawnbt, key);
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Deprecated
	public void setKeyString(String key, Object value) {
		setKeyString(key, value.toString());
	}

	@Deprecated
	public void setKeyString(String key, String value) {
		try{
			NBTTypes.String.setter.invoke(rawnbt, key, value);
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}
	}

	@Nullable
	@Deprecated
	public String getKeyString(String key) {
		try{
			String a = (String) NBTTypes.String.getter.invoke(rawnbt, key);
			if(a.equals(""))return null;
			return a;
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}
		return null;
	}



//	public void setList(String key) {
//		try{
//			mapGetter.invoke(mapProperty.get(rawnbt), key);
//		} catch (ReflectiveOperationException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Nullable
//	public String getList(String key) {
//		try{
//			Object o = mapGetter.invoke(mapProperty.get(rawnbt), key);
//		} catch (ReflectiveOperationException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}



	@Nullable
	public NBTComponent getComponent(String key) {
		try{
			Object o = getAny.invoke(rawnbt, key);
			if(o==null)return null;
			else return new NBTComponent(o);
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
			return null;
		}
	}

	public NBTComponent getComponentSafe(String key) {
		NBTComponent a = getComponent(key);
		if(a==null)return new NBTComponent();
		else return a;
	}

	public String toString(){
		return getNBT();
	}
}
