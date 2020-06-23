package fr.entasia.apis;

import fr.entasia.apis.nbt.ItemNBT;
import fr.entasia.apis.nbt.NBTComponent;
import fr.entasia.apis.nbt.NBTer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Base64;

@Deprecated
public class Serialization {

	/*
	Séparateurs :
	général ;
	inv item :
	item ,
	 */

	public static String serialiseItem(@Nonnull ItemStack item) {
		StringBuilder sb = new StringBuilder();
		sb.append(item.getType().toString()).append(",").append(item.getAmount()).append(",").append(item.getDurability());
		NBTComponent s = ItemNBT.getNBT(item);
		if(s!=null)sb.append(",").append(Base64.getEncoder().encodeToString(s.getNBT().getBytes()));
		return sb.toString();
	}

	public static ItemStack deserialiseItem(@Nonnull String s) {
		String[] ss = s.split(",");
		ItemStack ssss = new ItemStack(Material.getMaterial(ss[0]), Integer.parseInt(ss[1]), Short.parseShort(ss[2]));
		if(ss.length>3)ItemNBT.setNBT(ssss, new NBTComponent(new String(Base64.getDecoder().decode(ss[3]))));
		return ssss;
	}

	public static String serialiseInv(ItemStack[] inv) {
		StringBuilder sb = new StringBuilder();
		sb.append(inv.length);
		int inc=0;
		for(ItemStack item : inv){
			if(item!=null&&item.getType()!=Material.AIR){
				sb.append(";").append(inc).append(":").append(serialiseItem(item));
			}
			inc++;
		}
		return sb.toString();
	}

	public static ItemStack[] deserialiseInv(String inv) {
		String[] s = inv.split(";");
		ItemStack[] ii = null;
		for(String i : s){
			if(ii==null){
				ii = new ItemStack[Integer.parseInt(i)];
			}else{
				String[] ss = i.split(":");
				int sss = Integer.parseInt(ss[0]);
				ii[sss] = deserialiseItem(ss[1]);
			}
		}
		return ii;
	}
}
