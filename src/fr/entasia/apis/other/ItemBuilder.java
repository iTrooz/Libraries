package fr.entasia.apis.other;

import fr.entasia.apis.nbt.ItemNBT;
import fr.entasia.apis.nbt.NBTComponent;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;

public class ItemBuilder {

	public ItemStack item;
	public ItemMeta meta;

	public ItemBuilder(Material type){
		item = new ItemStack(type);
		meta = item.getItemMeta();
	}

	public ItemBuilder damage(short damage){
		item.setDurability(damage);
		return this;
	}
	public ItemBuilder damage(int damage){
		item.setDurability((short)damage);
		return this;
	}

	public ItemBuilder name(String name){
		meta.setDisplayName(name);
		return this;
	}

	public ItemBuilder lore(String lore){
		meta.setLore(Collections.singletonList(lore));
		return this;
	}

	public ItemBuilder lore(ArrayList<String> lore){
		meta.setLore(lore);
		return this;
	}

	public ItemBuilder amount(int amount){
		item.setAmount(amount);
		return this;
	}

	public ItemBuilder nbt(NBTComponent nbt){
		item.setItemMeta(meta); // obligé
		ItemNBT.setNBT(item, nbt);
		return this;
	}

	public ItemBuilder enchant(Enchantment ench, int lvl){
		item.addEnchantment(ench, lvl);
		return this;
	}


	public ItemStack build(){
		item.setItemMeta(meta);
		return item;
	}
}
