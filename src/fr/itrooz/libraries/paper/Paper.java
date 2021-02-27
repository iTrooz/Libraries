package fr.itrooz.libraries.paper;

import fr.itrooz.apis.events.bukkit.ServerStartEvent;
import fr.itrooz.apis.menus.MenuAPI;
import fr.itrooz.apis.nbt.NBTManager;
import fr.itrooz.apis.other.InstantFirework;
import fr.itrooz.apis.other.Signer;
import fr.itrooz.apis.regionManager.api.RegionManager;
import fr.itrooz.apis.socket.SocketSecurity;
import fr.itrooz.apis.sql.SQLSecurity;
import fr.itrooz.apis.utils.Internal;
import fr.itrooz.apis.utils.LPUtils;
import fr.itrooz.apis.utils.ReflectionUtils;
import fr.itrooz.apis.utils.ServerUtils;
import fr.itrooz.libraries.Common;
import fr.itrooz.libraries.paper.listeners.BaseListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

public class Paper extends JavaPlugin {

    public static Paper main;

    public static boolean enableRegions, enableSigner, enableMenus;

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
			enableMenus = sec.getBoolean("menus", true);
			enableRegions = sec.getBoolean("regions", true);
			enableSigner = sec.getBoolean("signer", true);

		    // Fichiers passwords
		    File f = new File(getDataFolder(), "passes.yml");
		    if(!f.exists())Files.copy(getResource("passes.yml"), f.toPath());

		    Configuration base = YamlConfiguration.loadConfiguration(f);

		    ConfigurationSection conf = base.getConfigurationSection("sql");
		    SQLSecurity.setHost(conf.getString("host"));
		    SQLSecurity.setPort(conf.getInt("port"));
		    for(Map.Entry<String, Object> e : conf.getConfigurationSection("passes").getValues(false).entrySet()){
			    SQLSecurity.addPass(e.getKey(), (String)e.getValue());
		    }

		    if(getServer().getPluginManager().getPlugin("LuckPerms")!=null){
				LPUtils.enable();
			}

		    // Commons
		    Common.load();

		    // Chargement des listeners
		    getServer().getPluginManager().registerEvents(new BaseListener(), this);


			NBTManager.init();
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


		} catch (Throwable e) {
		    e.printStackTrace();
	    }
    }

    @Override
    public void onDisable() {
	    getLogger().info("Librairies globales déchargées");
    }
}
