package fr.entasia.apis.other;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashSet;

public enum ItemCategory {


	SWORDS(new CatContent().add(Material.WOODEN_SWORD).add(Material.STONE_SWORD).add(Material.IRON_SWORD).add(Material.GOLDEN_SWORD).add(Material.DIAMOND_SWORD)),
	PICKAXES(new CatContent().add(Material.WOODEN_PICKAXE).add(Material.STONE_PICKAXE).add(Material.IRON_PICKAXE).add(Material.GOLDEN_PICKAXE).add(Material.DIAMOND_PICKAXE)),
	SHOVELS(new CatContent().add(Material.WOODEN_SHOVEL).add(Material.STONE_SHOVEL).add(Material.IRON_SHOVEL).add(Material.GOLDEN_SHOVEL).add(Material.DIAMOND_SHOVEL)),
	AXES(new CatContent().add(Material.WOODEN_AXE).add(Material.STONE_AXE).add(Material.IRON_AXE).add(Material.GOLDEN_AXE).add(Material.DIAMOND_AXE)),
	HOES(new CatContent().add(Material.WOODEN_HOE).add(Material.STONE_HOE).add(Material.IRON_HOE).add(Material.GOLDEN_HOE).add(Material.DIAMOND_HOE)),

	HELMETS(new CatContent().add(Material.LEATHER_HELMET).add(Material.CHAINMAIL_HELMET).add(Material.IRON_HELMET).add(Material.GOLDEN_HELMET).add(Material.DIAMOND_HELMET)),
	CHESTPLATES(new CatContent().add(Material.LEATHER_CHESTPLATE).add(Material.CHAINMAIL_CHESTPLATE).add(Material.IRON_CHESTPLATE).add(Material.GOLDEN_CHESTPLATE).add(Material.DIAMOND_CHESTPLATE)),
	LEGGINGS(new CatContent().add(Material.LEATHER_LEGGINGS).add(Material.CHAINMAIL_LEGGINGS).add(Material.IRON_LEGGINGS).add(Material.GOLDEN_LEGGINGS).add(Material.DIAMOND_LEGGINGS)),
	BOOTS(new CatContent().add(Material.LEATHER_BOOTS).add(Material.CHAINMAIL_BOOTS).add(Material.IRON_BOOTS).add(Material.GOLDEN_BOOTS).add(Material.DIAMOND_BOOTS)),

	ARMORS(new CatContent().add(HELMETS).add(CHESTPLATES).add(LEGGINGS).add(BOOTS))
	;

	ItemCategory(CatContent cc){
		this.content.addAll(cc.content);
		for(ItemCategory ic : cc.subcats){
			this.content.addAll(ic.content);
		}
	}

	public HashSet<Material> content = new HashSet<>();

	public boolean contains(Material m){
		return this.content.contains(m);
	}



	private static class CatContent{

		public ArrayList<ItemCategory> subcats = new ArrayList<>();
		public ArrayList<Material> content = new ArrayList<>();

		public CatContent add(ItemCategory ic){
			subcats.add(ic);
			return this;
		}
		public CatContent add(Material m){
			content.add(m);
			return this;
		}
	}

}
