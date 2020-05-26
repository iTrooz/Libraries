package fr.entasia.libraries;

import fr.entasia.apis.ServerUtils;
import fr.entasia.apis.socket.SocketClient;
import fr.entasia.apis.sql.SQLConnection;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Logger;


public abstract class Common {

	public static ArrayList<Class<?>> classesData = new ArrayList<>();
	public static SQLConnection sql;
	public static boolean enableDev, enableSocket, enableSQL;
	public static Logger logger;
	public static Thread thr = Thread.currentThread();

	public static boolean load() throws Exception {
		logger.info("Librairies globales en cours de chargement...");

		loadAPIs();

		if(enableSQL){
			if(enableDev) sql = new SQLConnection("root");
			else sql = new SQLConnection("libraries");
			logger.info("Connection à la base SQL réussie !");
		}

		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream("server.properties"));
			ServerUtils.serverName = prop.getProperty("server-name");
			if(ServerUtils.serverName == null){
				logger.severe("server name is not defined");
				if(!enableDev)return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.severe("Erreur lors de la lecture de server.properties !");
			if(!enableDev)return false;
		}
		logger.info("Nom du serveur : " + ServerUtils.serverName);

		if(enableSocket){
			if(!SocketClient.init()&&!enableDev)return false;
		}

		logger.info("Librairies globales chargées !");
		return true;
	}

	public static void loadAPIs() throws Exception {
		ArrayList<String> classes = new ArrayList<>();
		classes.addAll(Arrays.asList("sql.SQLConnection", "sql.SQLSecurity"));
		classes.addAll(Arrays.asList("ChatComponent"));
		classes.addAll(Arrays.asList("TextUtils", "ServerUtils"));
		if (ServerUtils.bukkit) {
			classes.addAll(Arrays.asList("menus.MenuAPI", "menus.MenuClickEvent", "menus.MenuCloseEvent", "menus.MenuCreator", "menus.MenuFlag"));
			classes.addAll(Arrays.asList("nbt.EntityNBT", "nbt.ItemNBT", "nbt.NBTComponent", "nbt.NBTer", "nbt.TileNBT"));
			classes.addAll(Arrays.asList("regionManager.api.Region", "regionManager.api.RegionAction", "regionManager.api.RegionManager",
					"regionManager.events.RegionEnterEvent", "regionManager.events.RegionLeaveEvent"));
			classes.addAll(Arrays.asList("socket.SocketClient", "socket.SocketEvent"));

			classes.addAll(Arrays.asList("BasicLocation", "events.bukkit.ServerGoingDown"));
			classes.addAll(Arrays.asList("PlayerUtils", "ItemUtils", "MathUtils"));
			classes.addAll(Arrays.asList("Serialization"));
		}

		for(String c : classes){
			classesData.add(Class.forName("fr.entasia.apis."+c));
		}
	}

	public static void main(String[] af) throws Exception {

	}

}