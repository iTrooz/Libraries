package fr.entasia.apis.events.bukkit;

import fr.entasia.apis.events.EntasiaEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

@Deprecated
public class EntityAttackEvent extends EntasiaEvent { // en cours

	public EntityDamageEvent base;

	public boolean isCancelled(){
		return base.isCancelled();
	}

	public void setCancelled(boolean cancel){
		base.setCancelled(cancel);
	}

	public boolean hasAttacker(){
		return base instanceof EntityDamageByEntityEvent;
	}

}
