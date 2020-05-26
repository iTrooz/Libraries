package fr.entasia.apis.menus;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MenuClickEvent {

	public Inventory inv;
	public Player player;
	public ItemStack item;
	public int slot;
	public Object data = null;
	public ClickType click;

	public enum ClickType {
		RIGHT,LEFT, UNKNOW
	}
}
