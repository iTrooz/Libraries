package fr.entasia.apis.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.UUID;

public class PlayerUtils {

	public static int getPing(Player p){
		try{
			Method getH = ReflectionUtils.CraftPlayer.getDeclaredMethod("getHandle");
			Object handle = getH.invoke(p);
			Field ping = ReflectionUtils.EntityPlayer.getDeclaredField("ping");

			return (int)ping.get(handle);
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	public static String colorPing(int ping){
		if(ping<100) return "§a"+ping;
		else if(ping<325) return "§3"+ping;
		else return "§c"+ping;
	}

	@Deprecated
	public static String getPing(Player p, boolean color){
		return colorPing(getPing(p));
	}

	public static UUID getUUID(String name){
		return UUID.nameUUIDFromBytes(("OfflinePlayer:"+name).getBytes());
	}

	// warning : les fire ticks peuvent demander une BukkitRunnable#runTask();

	public static void fakeKill(Player p){

		for (PotionEffect pe : p.getActivePotionEffects()) {
			p.removePotionEffect(pe.getType());
		}

		p.setMaxHealth(20);
		p.setHealth(20);
		p.setFoodLevel(20);

		p.setSprinting(false);
		p.setFallDistance(0);

		p.setGliding(false);
		p.setVelocity(new Vector(0, 0, 0));

		p.setFireTicks(0);
	}
}
