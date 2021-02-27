package fr.itrooz.apis.regionManager.events;

import fr.itrooz.apis.events.EntasiaEvent;
import fr.itrooz.apis.regionManager.api.Region;
import fr.itrooz.apis.regionManager.api.RegionAction;
import org.bukkit.entity.Player;

public class RegionEnterEvent extends EntasiaEvent {

	private Region r;
	private Player p;
	private RegionAction tt;

	public RegionEnterEvent(Region r, Player p, RegionAction tt) {
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
