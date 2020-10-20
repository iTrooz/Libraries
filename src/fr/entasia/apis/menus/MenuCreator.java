package fr.entasia.apis.menus;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class MenuCreator {
	protected List<InvInst> instances = new ArrayList<>();
	protected int[] freeSlots = new int[0];
	protected MenuFlag[] flags = new MenuFlag[0];

	public MenuCreator(){
		MenuAPI.menus.add(this);
	}
	@Deprecated
	public MenuCreator(MenuFlag[] flags){
		this(flags, null);
	}
	@Deprecated
	public MenuCreator(int[] freeSlots){
		this(null, freeSlots);
	}

	@Deprecated
	public MenuCreator(MenuFlag[] flags, int[] freeSlots){
		this();
		setFlags(flags);
		setFreeSlots(freeSlots);
	}

	public MenuCreator setFlags(MenuFlag... flags){
		if(flags!=null) this.flags = flags;
		return this;
	}

	public MenuCreator setFreeSlots(int... freeSlots){
		if(freeSlots!=null) this.freeSlots = freeSlots;
		return this;
	}

	public boolean hasFlag(MenuFlag flag){
		 for(MenuFlag f : flags){
		 	if(f==flag)return true;
		 }
		 return false;
	}

	public boolean hasFreeSlot(int slot){
		 for(int s : freeSlots){
		 	if(s==slot)return true;
		 }
		return false;
	}


	public Inventory createInv(int size, String name){
		return createInv(size, name, null);
	}

	public Inventory createInv(int size, String name, Object data){
		if(size<0||size>6)throw new RuntimeException("Menu Size not valid : "+size);
		Inventory inv = Bukkit.createInventory(null, size*9, name);
		instances.add(new InvInst(inv, data));
		return inv;
	}

	public void onMenuClick(MenuClickEvent e){}

	public boolean onFreeSlotClick(MenuClickEvent e){return false;}

	public void onMenuClose(MenuCloseEvent e){}
}
