package fr.entasia.apis.utils;

import fr.entasia.apis.ServerUtils;

public class ReflectionUtils {
	public static Class<?> getNMSClass(String c) throws ClassNotFoundException {
		return Class.forName("net.minecraft.server." + ServerUtils.version + "." + c);
	}

	public static Class<?> getOBCClass(String c) throws ClassNotFoundException {
		return Class.forName("org.bukkit.craftbukkit."+ServerUtils.version+"." + c);
	}
}
