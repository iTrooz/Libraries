package fr.itrooz.apis.regionManager.events;

import fr.itrooz.apis.other.BaseEvent;
import fr.itrooz.apis.regionManager.api.Region;
import fr.itrooz.apis.regionManager.api.RegionAction;
import org.bukkit.entity.Player;

public class RegionLeaveEvent extends BaseEvent {

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
