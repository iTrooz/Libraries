package fr.entasia.apis.other;

import fr.entasia.apis.utils.ReflectionUtils;
import fr.entasia.apis.utils.ServerUtils;
import fr.entasia.errors.LibraryException;
import net.minecraft.world.entity.projectile.EntityFireworks;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class InstantFirework {

	private static boolean init=false;

	public static Class<?> entityFireworkClass, craftFireworkClass;
	public static Method getHandle, setInvisible;
	public static Field expectedLifespan, ticksFlown;

	public static void init() throws Throwable {
		if(init)throw new LibraryException("already initied !");
		init = true;
		entityFireworkClass = ReflectionUtils.getNMSClass("world.entity.projectile", "EntityFireworks"); // handle
		craftFireworkClass = ReflectionUtils.getOBCClass("entity.CraftFirework");

		getHandle = craftFireworkClass.getMethod("getHandle");
		setInvisible = entityFireworkClass.getMethod("setInvisible", boolean.class);

		if(ServerUtils.getMajorVersion()>16) {
			expectedLifespan = entityFireworkClass.getDeclaredField("f");
			ticksFlown = entityFireworkClass.getDeclaredField("e");
		}else{
			expectedLifespan = entityFireworkClass.getDeclaredField("expectedLifespan");
			ticksFlown = entityFireworkClass.getDeclaredField("ticksFlown");
		}
		ticksFlown.setAccessible(true);
	}

	public static void explode(Location loc, FireworkEffect... effects) {
		Firework fw = loc.getWorld().spawn(loc, Firework.class);
		FireworkMeta meta = fw.getFireworkMeta();
		for (FireworkEffect effect : effects) {
			meta.addEffect(effect);
		}
		fw.setFireworkMeta(meta);
		explode(fw);
	}

	public static void explode(Location loc, FireworkMeta fmeta) {
		Firework fw = loc.getWorld().spawn(loc, Firework.class);
		fw.setFireworkMeta(fmeta);
		explode(fw);
	};

	public static void explode(Firework fw) {
		try {
			Object firework = craftFireworkClass.cast(fw);
			Object handle = getHandle.invoke(firework);
			ticksFlown.setInt(handle, expectedLifespan.getInt(handle)-1);
			setInvisible.invoke(handle, true);
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}
	}
}