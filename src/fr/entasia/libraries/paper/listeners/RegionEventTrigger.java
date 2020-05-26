package fr.entasia.libraries.paper.listeners;

import fr.entasia.apis.regionManager.api.Region;
import fr.entasia.apis.regionManager.api.RegionAction;
import fr.entasia.apis.regionManager.api.RegionManager;
import fr.entasia.apis.regionManager.events.RegionEnterEvent;
import fr.entasia.apis.regionManager.events.RegionLeaveEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.List;

public class RegionEventTrigger implements Listener {

	@EventHandler
	private void onMove(PlayerMoveEvent e){
		trigger(e.getPlayer(), e.getFrom(), e.getTo(), RegionAction.MOVE);
	}
	
	@EventHandler
	public void onTeleport(PlayerTeleportEvent e){
		trigger(e.getPlayer(), e.getFrom(), e.getTo(), RegionAction.TELEPORT);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		for(Region reg : RegionManager.getRegionsAtLocation(e.getPlayer().getLocation())){
			Bukkit.getPluginManager().callEvent(new RegionEnterEvent(reg, e.getPlayer(), RegionAction.JOIN_QUIT));
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		for(Region reg : RegionManager.getRegionsAtLocation(e.getPlayer().getLocation())){
			Bukkit.getPluginManager().callEvent(new RegionLeaveEvent(reg, e.getPlayer(), RegionAction.JOIN_QUIT));
		}
		
	}
	
	private void trigger(Player p, Location from, Location to, RegionAction t){
		List<Region> fromregs = RegionManager.getRegionsAtLocation(from);
		List<Region> toregs = RegionManager.getRegionsAtLocation(to);
			
		for(Region reg : fromregs){
			if(!toregs.contains(reg)){
				Bukkit.getPluginManager().callEvent(new RegionLeaveEvent(reg, p, t));
			}
				
		}
			
		for(Region reg : toregs){
			if(!fromregs.contains(reg)){
				Bukkit.getPluginManager().callEvent(new RegionEnterEvent(reg, p, t));
			}
		}
	}
}