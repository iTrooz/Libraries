package fr.entasia.apis;

import fr.entasia.apis.nbt.ItemNBT;
import fr.entasia.apis.nbt.NBTComponent;
import fr.entasia.apis.nbt.NBTer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Base64;

public class Serialization {


	public static String SerialiseInv(ItemStack[] inv) {
		StringBuilder sb = new StringBuilder();
		sb.append(inv.length);
		int inc=0;
		for(ItemStack i : inv){
			if(i!=null&&i.getType()!=Material.AIR){
				sb.append(";").append(inc).append(",").append(i.getType().toString()).append(",").append(i.getAmount()).append(",").append(i.getDurability());
				NBTComponent s = ItemNBT.getNBT(i);
				if(s!=null)sb.append(",").append(Base64.getEncoder().encodeToString(s.getNBT().getBytes()));
			}
			inc++;
		}
		return sb.toString();
	}

	public static ItemStack[] DeserialiseInv(String inv) {
		String[] s = inv.split(";");
		ItemStack[] items=null;
		for(String i : s){
			if(items==null){
				items = new ItemStack[Integer.parseInt(i)];
			}else{
				String[] ss = i.split(",");
				int sss = Integer.parseInt(ss[0]);
				items[sss] = new ItemStack(Material.getMaterial(ss[1]), Integer.parseInt(ss[2]), Short.parseShort(ss[3]));
				if(ss.length>4)items[sss] = ItemNBT.setNBT(items[sss], NBTer.parseNBT(new String(Base64.getDecoder().decode(ss[4]))));
			}
		}
		return items;
	}
}
