package fr.entasia.apis.menus;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public abstract class MenuCreator {
	public ArrayList<MenuAPI.InvInst> instances = new ArrayList<>();
	public int[] slots = new int[0];
	public MenuFlag[] flags = new MenuFlag[0];

	public MenuCreator(){
		this(null, null);
	}
	public MenuCreator(MenuFlag[] flags){
		this(flags, null);
	}
	public MenuCreator(int[] slots){
		this(null, slots);
	}

	public MenuCreator(MenuFlag[] flags, int[] slots){
		if(flags!=null) this.flags = flags;
		if(slots!=null) this.slots = slots;
		MenuAPI.menus.add(this);
	}

	public boolean containFlag(MenuFlag flag){
		 for(MenuFlag f : flags){
		 	if(f==flag)return true;
		 }
		 return false;
	}

	public boolean containSlot(int slot){
		 for(int s : slots){
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
		instances.add(new MenuAPI.InvInst(inv, data));
		return inv;
	}

	public void onMenuClick(MenuClickEvent e){};

	public boolean onFreeSlotClick(MenuClickEvent e){return false;};

	@Deprecated
	public void onMenuClose(Player p, Inventory inv){}

	public void onMenuClose(MenuCloseEvent e){}
}
