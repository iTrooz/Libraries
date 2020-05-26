package fr.entasia.apis.events.bukkit;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ServerGoingDown extends Event {

	private static final HandlerList handlers = new HandlerList();

	public int seconds;

	public ServerGoingDown(int seconds) {
		this.seconds = seconds;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
}