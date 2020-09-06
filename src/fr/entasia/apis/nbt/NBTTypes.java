package fr.entasia.apis.nbt;

import net.minecraft.server.v1_15_R1.NBTTagCompound;

import java.lang.reflect.Method;

public enum NBTTypes {
	String(String.class), Byte(byte.class), Short(short.class), Int(int.class), Long(long.class), Float(float.class), Double(double.class), Boolean(boolean.class);

	public Class<?> type;
	public Method getter;
	public Method setter;

	NBTTypes(Class<?> type){
		this.type = type;
	}
}
