package fr.entasia.apis.events.bukkit;

import fr.entasia.apis.events.EntasiaEvent;

public class ServerStopEvent extends EntasiaEvent {

	public int seconds;

	public ServerStopEvent(int seconds) {
		this.seconds = seconds;
	}

}