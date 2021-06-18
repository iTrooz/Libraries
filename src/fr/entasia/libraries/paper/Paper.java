package fr.entasia.libraries.paper;

import fr.entasia.apis.events.ServerStartEvent;
import fr.entasia.apis.menus.MenuAPI;
import fr.entasia.apis.nbt.NBTManager;
import fr.entasia.apis.other.InstantFirework;
import fr.entasia.apis.regionManager.api.RegionManager;
import fr.entasia.apis.sql.SQLSecurity;
import fr.entasia.apis.utils.Internal;
import fr.entasia.apis.utils.ReflectionUtils;
import fr.entasia.apis.utils.ServerUtils;
import fr.entasia.libraries.Common;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
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
			Internal.setBukkitVersions();

			ReflectionUtils.initBukkit();

		    ServerUtils.bukkit = true;
		    ServerUtils.bungeeMode = Bukkit.spigot().getConfig().getBoolean("settings.bungeecord", false);


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

		    Common.load();

			NBTManager.init();
			InstantFirework.init();

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
