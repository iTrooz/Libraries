package fr.entasia.libraries.bungee;

import fr.entasia.apis.socket.SocketClient;
import fr.entasia.apis.socket.SocketEvent;
import fr.entasia.apis.socket.SocketSecurity;
import fr.entasia.apis.sql.SQLSecurity;
import fr.entasia.apis.utils.LPUtils;
import fr.entasia.apis.utils.ServerUtils;
import fr.entasia.libraries.Common;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Bungee extends Plugin {

	public static Bungee main;

	public static File configFile;
	public static Configuration config;

	public void stopServer(){
		if(Common.enableDev){
			getLogger().severe("Une erreur est survenue, mais le serveur est en mode développement !");
		}else {
			getLogger().info("LE PROXY VA S'ETEINDRE");
			ProxyServer.getInstance().stop();
		}
	}

	@Override
	public void onEnable() {
		try{
			// Définition des variables utiles
			main=this;
			Common.logger = getLogger();
			ServerUtils.bukkit = false;



			// Configuration
			if (!getDataFolder().exists()) {
				getDataFolder().mkdir();
			}
			
			configFile = new File(getDataFolder(), "config.yml");
			if(!configFile.exists())Files.copy(getResourceAsStream("config.yml"), configFile.toPath());
			config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
			Common.enableDev = config.getBoolean("dev", false);
			Common.enableSQL = config.getBoolean("features.sql", true);
			Common.enableSocket = config.getBoolean("features.socket", true);
			SocketSecurity.secret = config.getString("socketSecret").getBytes(StandardCharsets.UTF_8);


			// Fichier passwords
			File f = new File(getDataFolder(), "sql.yml");
			if(!f.exists())Files.copy(getResourceAsStream("sql.yml"), f.toPath());

			Configuration conf = ConfigurationProvider.getProvider(YamlConfiguration.class).load(f);
			SQLSecurity.setHost(conf.getString("host"));
			SQLSecurity.setPort(conf.getInt("port"));
			conf = conf.getSection("pass");
			for(String i : conf.getKeys()){
				SQLSecurity.addPass(i, conf.getString(i));
			}

			if(getProxy().getPluginManager().getPlugin("LuckPerms")!=null){
				LPUtils.enable();
			}


			// Commons
			if(!Common.load()){
				stopServer();
				return;
			}

			// Events de base pour le socket

			// Chargement des listeners
			getProxy().getPluginManager().registerListener(this, new BaseListeners());

			others();

		}catch(Throwable e){
			e.printStackTrace();
			stopServer();
		}
	}

	@Override
	public void onDisable() {
		getLogger().info("[Librairies] Librairies globales déchargées");
	}


	private static void others() throws Throwable {

		SocketClient.addListener(new SocketEvent("send") {
			@Override
			public void onEvent(String[] data) {
				ProxiedPlayer p = ProxyServer.getInstance().getPlayer(data[0]);
				ServerInfo t = ProxyServer.getInstance().getServerInfo(data[1]);
				if (t != null && p != null) p.connect(t);
			}
		});
	}
}
