package fr.entasia.apis.regionManager.events;

import fr.entasia.apis.regionManager.api.Region;
import fr.entasia.apis.regionManager.api.RegionAction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RegionLeaveEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	private Region r;
	private Player p;
	private RegionAction tt;
	public RegionLeaveEvent(Region r, Player p, RegionAction tt) {
		this.r = r;
		this.p = p;
		this.tt = tt;
	}
	
	public Region getRegion() {
		return r;
	}
	
	public Player getPlayer() {
		return p;
	}
	
	public RegionAction getTriggerType(){
		return tt;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
