package fr.entasia.libraries.paper;

import fr.entasia.apis.events.bukkit.ServerStartEvent;
import fr.entasia.apis.menus.MenuAPI;
import fr.entasia.apis.nbt.NBTer;
import fr.entasia.apis.other.InstantFirework;
import fr.entasia.apis.other.Signer;
import fr.entasia.apis.regionManager.api.RegionManager;
import fr.entasia.apis.sql.SQLSecurity;
import fr.entasia.apis.utils.Internal;
import fr.entasia.apis.utils.LPUtils;
import fr.entasia.apis.utils.ReflectionUtils;
import fr.entasia.apis.utils.ServerUtils;
import fr.entasia.libraries.Common;
import fr.entasia.libraries.paper.listeners.BaseListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.nio.file.Files;

public class Paper extends JavaPlugin {

    public static Paper main;

    public static boolean enableRegions, enableSigner, enableMenus;

    public void stopServer(){
    	if(Common.enableDev){
		    getLogger().severe("Une erreur est survenue, mais le serveur est en mode développement !");
	    }else{
		    getLogger().severe("LE SERVEUR VA S'ETEINDRE");
		    getServer().shutdown();
	    }
    }

    @Override
    public void onEnable() {
	    try {
	    	// Variables de base
		    main = this;
		    Common.logger = getLogger();
		    ServerUtils.bukkit = true;
		    ServerUtils.bungeeMode = Bukkit.spigot().getConfig().getBoolean("settings.bungeecord", false);
			Internal.setVersionStr(Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3]);
			ReflectionUtils.initBukkit();
			String ver = (String)ReflectionUtils.MinecraftServer.getDeclaredMethod("getVersion").invoke(ReflectionUtils.servInst);
			Internal.setVersionInt(ver);


		    // Configuration
		    saveDefaultConfig();
		    ConfigurationSection sec = getConfig().getConfigurationSection("features");
		    Common.enableDev = getConfig().getBoolean("dev", false);
			enableMenus = sec.getBoolean("menus", true);
			enableRegions = sec.getBoolean("regions", true);
			enableSigner = sec.getBoolean("signer", true);
			Common.enableSQL = sec.getBoolean("sql", true);
			Common.enableSocket = sec.getBoolean("socket", true);

		    // Fichiers passwords
		    File f = new File(getDataFolder(), "sql.yml");
		    if(!f.exists())Files.copy(getResource("sql.yml"), f.toPath());
		    Configuration conf = YamlConfiguration.loadConfiguration(f);
		    SQLSecurity.setHost(conf.getString("host"));
		    SQLSecurity.setPort(conf.getInt("port"));
		    for(String i : conf.getConfigurationSection("pass").getKeys(false)){
			    SQLSecurity.addPass(i, conf.getString("pass."+i));
		    }

		    if(getServer().getPluginManager().getPlugin("LuckPerms")!=null){
				LPUtils.enable();
			}

		    // Commons
		    if(!Common.load()&&!Common.enableDev){
			    stopServer();
			    return;
		    }

		    // Chargement des listeners
		    getServer().getPluginManager().registerEvents(new BaseListener(), this);

		    others();

	    } catch (Throwable e) {
		    e.printStackTrace();
		    stopServer();
	    }
    }

    @Override
    public void onDisable() {
	    getLogger().info("Librairies globales déchargées");
    }

    public static void others() throws Throwable {
	    // Grosses APIs
	    NBTer.init();
	    InstantFirework.init();
	    if (enableSigner&&Bukkit.getPluginManager().getPlugin("ProtocolLib") != null) {
		    Signer.initPackets();
	    }
	    if (enableRegions) RegionManager.init();
	    if (enableMenus) MenuAPI.init();


	    new BukkitRunnable() {
		    @Override
		    public void run() {
	            Bukkit.getPluginManager().callEvent(new ServerStartEvent());
		    }
	    }.runTask(main);

    }
}
