package fr.entasia.apis.socket;

public abstract class SocketEvent {

	public String key;

	public abstract void onEvent(String[] data);

	public SocketEvent(String key){
		this.key = key;
	}
}
