package fr.entasia.apis.utils;

// NE PAS TOUCHER !

import org.bukkit.Bukkit;

import java.util.Arrays;

public class Internal {

	public static void setBukkitVersions(){
		ServerUtils.versionStr = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		String ver = Bukkit.getBukkitVersion().split("-")[0];
		String[] a = ver.split("\\.");

		ServerUtils.majorVersion = Integer.parseInt(a[1]);
		if(a.length==2)return;
		ServerUtils.minorVersion = Integer.parseInt(a[2]);


	}
}
