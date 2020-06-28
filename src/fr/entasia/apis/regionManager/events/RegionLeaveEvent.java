package fr.entasia.apis.regionManager.events;

import fr.entasia.apis.events.EntasiaEvent;
import fr.entasia.apis.regionManager.api.Region;
import fr.entasia.apis.regionManager.api.RegionAction;
import org.bukkit.entity.Player;

public class RegionLeaveEvent extends EntasiaEvent {

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

}
