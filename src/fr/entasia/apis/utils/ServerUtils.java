package fr.entasia.apis.utils;

import fr.entasia.apis.other.ChatComponent;
import fr.entasia.libraries.Common;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ServerUtils {

	protected static String versionStr;
	protected static int majorVersion;
	protected static int minorVersion;
	public static String serverName;
	public static boolean bukkit;
	public static boolean bungeeMode;

	public static String getVersionStr(){
		return versionStr;
	}

	public static int getMajorVersion(){
		return majorVersion;
	}

	public static int getMinorVersion(){
		return minorVersion;
	}

	public static boolean isGameServer(){
		return !(serverName==null||serverName.equalsIgnoreCase("login")||serverName.equalsIgnoreCase("hub"));
	}

	public static void permMsg(String perm, String... lines){
		permMsg(perm, ChatComponent.create(String.join("\n", lines)));
	}

	public static void permMsg(String perm, BaseComponent[] message){
		if(bukkit){
			for(Player p : Bukkit.getOnlinePlayers()){
				if(p.hasPermission(perm))p.sendMessage(message);
			}
		}else{
			for(ProxiedPlayer p : ProxyServer.getInstance().getPlayers()){
				if(p.hasPermission(perm))p.sendMessage(message);
			}
		}
	}


	// not Bukkit method because of Bungee

	public static boolean isMainThread(){
		return Thread.currentThread().equals(Common.mainThread);
	}

	public static void wantMainThread(){
		if(!isMainThread())throw new RuntimeException("Fonction exécutée dans un Thread child");
	}


	public static void wantChildThread(){
		if(isMainThread())throw new RuntimeException("Fonction exécutée dans le Thread principal");
	}

	@Deprecated
	public static void checkMainThread(){
		wantMainThread();
	}



//	public static void warn(JavaPlugin plugin, String msg) {
//		warn(plugin, "logs.others", msg);
//	}
//
//	public static void warn(JavaPlugin plugin, String permission, String msg) {
//		new EntasiaException("Warning du plugin "+plugin.getName()).printStackTrace();
//		plugin.getLogger().warning(msg);
//		ServerUtils.permMsg(permission, "§6Warning <"+plugin.getName()+"> : §c"+msg);
//	}

}
