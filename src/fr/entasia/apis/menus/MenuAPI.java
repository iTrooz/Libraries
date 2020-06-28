package fr.entasia.apis.menus;

import fr.entasia.apis.utils.ItemUtils;
import fr.entasia.apis.utils.ServerUtils;
import fr.entasia.libraries.paper.Paper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Map;

public class MenuAPI implements Listener {

	public static void init(){
		Paper.main.getServer().getPluginManager().registerEvents(new MenuAPI(), Paper.main);
	}

	public static class InvInst{
		public Inventory inv;
		public Object data;

		public InvInst(Inventory inv, Object data) {
			this.inv = inv;
			this.data = data;
		}
	}
	public static class MenuData{
		public MenuCreator menu;
		public InvInst inst;

	}

	public static ArrayList<MenuCreator> menus = new ArrayList<>();

	public static MenuData getMenu(Inventory inv) {
		for (MenuCreator mc : menus) {
			for(InvInst i : mc.instances){
				if(i.inv.hashCode()==inv.hashCode()){
					MenuData md = new MenuData();
					md.inst = i;
					md.menu = mc;
					return md;
				}
			}
		}
		return null;
	}

	// raw = plus grande portée, des deux inventaires en même temps
	// inventory = portée de l'inventaire local

	@EventHandler
	public void invDrag(InventoryDragEvent e){
		if(e.getWhoClicked() instanceof Player) {
			MenuData md = getMenu(e.getWhoClicked().getOpenInventory().getTopInventory());
			if(md!=null){
				try{

					boolean onlylocal=true;
					for(int i : e.getInventorySlots()){
						if(e.getRawSlots().contains(i)){
							onlylocal=false;
							break;
						}
					}
					if(!onlylocal){
						int max = e.getInventory().getSize();
						MenuClickEvent mce = new MenuClickEvent();
						mce.inv = e.getView().getTopInventory();
						mce.player = (Player) e.getWhoClicked();
						mce.data = md.inst.data;
						for(Map.Entry<Integer, ItemStack> entry : e.getNewItems().entrySet()){ // checker si c'est bien les raw slots !!!
							mce.slot = entry.getKey();
							if(mce.slot<max){
								mce.item = entry.getValue();
								try{
									if(!md.menu.containSlot(mce.slot)||md.menu.onFreeSlotClick(mce)) {
										e.setCancelled(true);
										((Player) e.getWhoClicked()).updateInventory();
										return;
									}
								}catch(Exception e2){
									e2.printStackTrace();
								}
							}
						}
					}
				}catch(Exception e2){
					e.setCancelled(true);
					e2.printStackTrace();
					ServerUtils.permMsg("staff.errormsgs", "\n\n",
							"------§4§lERREUR RENCONTREE AVEC L'API DE MENUS PREVENEZ ITROOZ IMMEDIATEMMENT !",
							"Joueur : "+e.getWhoClicked().getName(),
							"\n\n");
				}
			}
		}
	}

	@EventHandler
	public void invClick(InventoryClickEvent e){
		if(e.getWhoClicked() instanceof Player) {
			MenuData md = getMenu(e.getWhoClicked().getOpenInventory().getTopInventory());
			if (md != null) {
				try {
					if (e.getAction() == InventoryAction.NOTHING || e.getAction() == InventoryAction.UNKNOWN || e.getClick() == ClickType.UNKNOWN ||
							e.getClick() == ClickType.WINDOW_BORDER_LEFT || e.getClick() == ClickType.WINDOW_BORDER_RIGHT) {
						e.setCancelled(true);
						((Player) e.getWhoClicked()).updateInventory();
					} else {
						if (e.getAction() == InventoryAction.COLLECT_TO_CURSOR)e.setCancelled(true);
						else {
							MenuClickEvent mce = new MenuClickEvent();
							mce.player = (Player) e.getWhoClicked();
							mce.data = md.inst.data;
							mce.item = e.getCurrentItem();
							mce.inv = e.getClickedInventory();
							if(e.getClick()==ClickType.LEFT||e.getClick()== ClickType.SHIFT_LEFT)mce.click = MenuClickEvent.ClickType.LEFT;
							else if (e.getClick()==ClickType.RIGHT||e.getClick()== ClickType.SHIFT_RIGHT)mce.click = MenuClickEvent.ClickType.RIGHT;
							else mce.click = MenuClickEvent.ClickType.UNKNOW;
							if (e.getClickedInventory() == null )return; // il veut drop un item
							if (e.getClickedInventory().getType() == InventoryType.CHEST) { // cliqué sur l'inv chest
								mce.slot = e.getSlot();
								if (md.menu.containSlot(e.getSlot())){ // slot déplacable
									try{
										if(md.menu.onFreeSlotClick(mce)) e.setCancelled(true);
									}catch(Exception e2){
										e2.printStackTrace();
									}
								}else{ // slot déplacable
									e.setCancelled(true);
									((Player) e.getWhoClicked()).updateInventory();
									if (md.menu.containFlag(MenuFlag.AllItemsTrigger) || ItemUtils.hasName(e.getCurrentItem())) {
										try{
											md.menu.onMenuClick(mce);
										}catch(Exception e2){
											e2.printStackTrace();
										}
									}
								}
							} else { // clické sur son inv
								if (!(md.menu.containFlag(MenuFlag.NoMoveLocalInv) && md.menu.slots.length == 0)) {
									if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) { // shift click
										e.setCancelled(true);
										if (md.menu.slots.length != 0) {
											for (int i : md.menu.slots) {
												if (e.getInventory().getItem(i) == null) {
													mce.slot = i;

													e.setCurrentItem(null);
													e.getInventory().setItem(i, mce.item);

													try{
														md.menu.onFreeSlotClick(mce);
													}catch(Exception e2){
														e2.printStackTrace();
													}
													break;
												}
											}
										}
										((Player) e.getWhoClicked()).updateInventory();
									}
								} else {
									e.setCancelled(true);
									((Player) e.getWhoClicked()).updateInventory();
								}
							}
						}
					}
				} catch (Throwable e2) {
					e.setCancelled(true);
					e2.printStackTrace();
					ServerUtils.permMsg("staff.errormsgs", "\n\n",
							"------§4§lERREUR RENCONTREE AVEC L'API DE MENUS PREVENEZ ITROOZ IMMEDIATEMMENT !",
							"Joueur : " + e.getWhoClicked().getName(),
							"\n\n");
				}
			}
		}
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e){
		if(e.getPlayer() instanceof Player) {
			int inv = e.getInventory().hashCode();
			for (MenuCreator mc : menus) {
				for(InvInst inst : mc.instances){
					if(inst.inv.hashCode()==inv){
						mc.instances.remove(inst);
						if(!mc.containFlag(MenuFlag.NoReturnUnlockedItems)){
							for(int i : mc.slots){
								ItemStack item = e.getInventory().getItem(i);
								if(item!=null){
									int a = e.getPlayer().getInventory().firstEmpty();
									if(a==-1)e.getPlayer().getWorld().dropItem(e.getPlayer().getLocation(), item);
									else e.getPlayer().getInventory().setItem(a, item);
								}
							}
						}
						mc.onMenuClose((Player)e.getPlayer(), e.getInventory());

						MenuCloseEvent mce = new MenuCloseEvent();
						mce.player = (Player) e.getPlayer();
						mce.inv = e.getInventory();
						mce.data = inst.data;
						mc.onMenuClose(mce);
						return;
					}
				}
			}
		}
	}
}
