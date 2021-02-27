package fr.entasia.apis.utils;

// NE PAS TOUCHER !

public class Internal {

	public static void setVersionStr(String ver){
		ServerUtils.versionStr = ver;

	}

	public static void setVersionInt(String ver) {
		String[] a = ver.split("\\.");
		ServerUtils.majorVersion = Integer.parseInt(a[1]);
		ServerUtils.minorVersion = Integer.parseInt(a[2]);
	}

}
