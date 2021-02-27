package fr.itrooz.libraries.bungee;

import fr.itrooz.apis.socket.SocketClient;
import fr.itrooz.apis.socket.SocketEvent;
import fr.itrooz.apis.socket.SocketSecurity;
import fr.itrooz.apis.sql.SQLSecurity;
import fr.itrooz.apis.utils.LPUtils;
import fr.itrooz.apis.utils.ServerUtils;
import fr.itrooz.libraries.Common;
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


			// Fichier passwords
			File f = new File(getDataFolder(), "passes.yml");
			if(!f.exists())Files.copy(getResourceAsStream("passes.yml"), f.toPath());

			Configuration base = ConfigurationProvider.getProvider(YamlConfiguration.class).load(f);

			Configuration conf = base.getSection("sql");
			SQLSecurity.setHost(conf.getString("host"));
			SQLSecurity.setPort(conf.getInt("port"));
			conf = conf.getSection("passes");
			for(String i : conf.getKeys()){
				SQLSecurity.addPass(i, conf.getString(i));
			}

			Common.load();

//			if(getProxy().getPluginManager().getPlugin("LuckPerms")!=null){
//				LPUtils.enable();
//			}


		}catch(Throwable e){
			e.printStackTrace();
		}
	}

	@Override
	public void onDisable() {
		getLogger().info("[Librairies] Librairies globales déchargées");
	}

}
