package fr.itrooz.apis.other;

import fr.itrooz.apis.nbt.ItemNBT;
import fr.itrooz.apis.nbt.NBTComponent;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ItemBuilder {

	public ItemStack item;
	public ItemMeta meta;

	public ItemBuilder(Material type){
		this(type, 1);
	}

	public ItemBuilder(Material type, int count){
		this(new ItemStack(type, count));
	}

	public ItemBuilder(ItemStack item){
		this.item = item;
		this.meta = item.getItemMeta();
	}

	public ItemBuilder(Material type, int count, ItemMeta meta){
		item = new ItemStack(type, count);
		this.meta = meta;
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

	public ItemBuilder lore(List<String> lore){
		meta.setLore(lore);
		return this;
	}

	public ItemBuilder lore(String... lore){
		meta.setLore(Arrays.asList(lore));
		return this;
	}

	public ItemBuilder amount(int amount){
		item.setAmount(amount);
		return this;
	}

	public ItemBuilder nbt(NBTComponent nbt){
		item.setItemMeta(meta); // obligé
		ItemNBT.setNBT(item, nbt);
		meta = item.getItemMeta(); // obligé
		return this;
	}

	public ItemBuilder enchant(Enchantment ench){
		meta.addEnchant(ench, 1, true);
		return this;
	}

	public ItemBuilder enchant(Enchantment ench, int lvl){
		meta.addEnchant(ench, lvl, true);
		return this;
	}

	// Attention, ne pas utiliser avec enchant();
	public ItemBuilder fakeEnchant(){
		meta.addEnchant(Enchantment.LURE, 1,true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		return this;
	}

	public ItemBuilder flags(ItemFlag... flags){
		meta.addItemFlags(flags);
		return this;
	}


	public ItemStack build(){
		item.setItemMeta(meta);
		return item;
	}
}
