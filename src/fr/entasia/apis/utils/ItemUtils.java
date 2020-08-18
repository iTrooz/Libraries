package fr.entasia.apis.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import fr.entasia.errors.EntasiaException;
import fr.entasia.errors.LibraryException;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Base64;
import java.util.UUID;

public class ItemUtils {

	private static final ArrayList<String> skulls = new ArrayList<>();

	private static Class<?> CraftWorld;
	private static Constructor<?> blockPosConstruct;

	// items
	private static Field ITProfileField;
	// blocks
	private static Field BLProfileField;
	private static Method getHandle, getTileEntity, setGameProfile;


	public static boolean hasName(ItemStack item){
		return (item!=null&&item.hasItemMeta()&&item.getItemMeta().hasDisplayName());
	}

	public static boolean hasName(ItemStack item, String name){
		if(!hasName(item))return false;
		return item.getItemMeta().getDisplayName().equals(name);
	}


	static {
		try{
			SkullMeta meta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
			ITProfileField = meta.getClass().getDeclaredField("profile");
			ITProfileField.setAccessible(true);

			CraftWorld = ReflectionUtils.getOBCClass("CraftWorld");
			Class<?> tileSkullClass = ReflectionUtils.getNMSClass("TileEntitySkull");
//			Class<?> WorldServer = ReflectionUtils.getNMSClass("WorldServer");
			Class<?> World = ReflectionUtils.getNMSClass("World");
			Class<?> BlockPosition = ReflectionUtils.getNMSClass("BlockPosition");

			blockPosConstruct = BlockPosition.getConstructor(int.class, int.class, int.class);

			getHandle = CraftWorld.getDeclaredMethod("getHandle");
//			getTileEntity = worldServerClass.getDeclaredMethod("getTileEntity", blockPositionClass);
			getTileEntity = World.getDeclaredMethod("getTileEntity", BlockPosition);

			setGameProfile = tileSkullClass.getDeclaredMethod("setGameProfile", GameProfile.class);
			if(ServerUtils.getMajorVersion()<14){
				BLProfileField = tileSkullClass.getDeclaredField("g");
			}else{
				BLProfileField = tileSkullClass.getDeclaredField("gameProfile");
			}


		}catch(ReflectiveOperationException e){
			e.printStackTrace();
		}
	}

	public static GameProfile genProfile(String uuidstr, String texture){
		return genProfile(UUID.nameUUIDFromBytes(uuidstr.getBytes()), texture);
	}

	public static GameProfile genProfile(UUID uuid, String texture){
		GameProfile profile = new GameProfile(uuid, null);
		String data = Base64.getEncoder().encodeToString(("{textures:{SKIN:{url:\"http://textures.minecraft.net/texture/" + texture + "\"}}}").getBytes());
		profile.getProperties().put("textures", new Property("textures", data));
		return profile;
	}

	public static void setTexture(SkullMeta im, GameProfile profile) {
		try {
			ITProfileField.set(im, profile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Nullable
	public static GameProfile getProfile(SkullMeta im) {
		try {
			return (GameProfile) ITProfileField.get(im);
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void setTexture(Block b, GameProfile profile) {
		try{
			Object craftW = CraftWorld.cast(b.getWorld());
			Object nmsW = getHandle.invoke(craftW);
			Object tile = getTileEntity.invoke(nmsW, blockPosConstruct.newInstance(b.getX(), b.getY(), b.getZ()));
			if(tile==null)throw new EntasiaException("Invalid tile");
			else{

				setGameProfile.invoke(tile, profile);
				b.getState().update(true);
			}
		}catch(ReflectiveOperationException e){
			e.printStackTrace();
		}
	}

	@Nullable
	public static GameProfile getProfile(Block b) {
		try{
			Object craftW = CraftWorld.cast(b.getWorld());
			Object nmsW = getHandle.invoke(craftW);
			Object tile = getTileEntity.invoke(nmsW, blockPosConstruct.newInstance(b.getX(), b.getY(), b.getZ()));
			if(tile==null)throw new EntasiaException("Invalid tile");
			else{
				return (GameProfile) BLProfileField.get(tile);
			}
		}catch(ReflectiveOperationException e){
			e.printStackTrace();
		}
		return null;
	}

	public static String retrieveTexture(GameProfile profile){
		String data=null;
		for(Property pr : profile.getProperties().values()) {
			if (pr.getName().equals("textures")) {
				data = pr.getValue();
			}
		}
		if(data==null)return null;
		data = new String(Base64.getDecoder().decode(data));
		data = data.substring(60, data.length()-4);
		return data;
	}



	public static void placeSkullAsync(Inventory inv, final int slot, final ItemStack item, final OfflinePlayer op, JavaPlugin plugin){
		assert op!=null;
		Ownering o;
		if(ServerUtils.getMajorVersion()<=9)o = new Ownering(op.getName());
		else o = new Ownering(op);
		placeSkullAsync(inv, slot, item, o, plugin);
	}

	public static void placeSkullAsync(Inventory inv, final int slot, final ItemStack item, final String owner, JavaPlugin plugin){
		placeSkullAsync(inv, slot, item, new Ownering(owner), plugin);
	}

	private static void placeSkullAsync(Inventory inv, final int slot, final ItemStack item, final Ownering owner, JavaPlugin plugin){
		if(skulls.contains(owner.name)) {
			SkullMeta meta = (SkullMeta) item.getItemMeta();
			if(owner.s==null) meta.setOwningPlayer(owner.op);
			else meta.setOwner(owner.s);
			item.setItemMeta(meta);
			inv.setItem(slot, item);
		}else{
			SkullMeta meta = (SkullMeta) item.getItemMeta();
			inv.setItem(slot, item);
			if(owner.s==null) meta.setOwningPlayer(owner.op);
			else meta.setOwner(owner.s);
			item.setItemMeta(meta);
			new BukkitRunnable() {
				@Override
				public void run() {
					inv.setItem(slot, item);
					if(!skulls.contains(owner.name))skulls.add(owner.name);
				}
			}.runTaskAsynchronously(plugin);
		}
	}

	private static class Ownering{
		public OfflinePlayer op;
		public String s;
		public String name;

		public Ownering(String s){
			this.s = s;
			this.name = s;
		}

		public Ownering(OfflinePlayer op) {
			this.op = op;
			this.name = op.getName();
		}
	}


	public static boolean damage(ItemStack item, int by){
		if(item.getType().getMaxDurability()==0)throw new LibraryException("Invalid item : no durability");
		short dura = (short) (item.getDurability()+by);
		if(dura>item.getType().getMaxDurability()){
			item.setType(Material.AIR);
			return true;
		}else{
			item.setDurability(dura);
			return false;
		}
	}

	public static boolean giveOrDrop(Player p, ItemStack item){
		if(p.getInventory().firstEmpty()==-1){
			p.getWorld().dropItem(p.getLocation(), item);
			return true;
		}else{
			p.getInventory().addItem(item);
			return false;
		}
	}


}
