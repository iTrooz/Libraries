package fr.entasia.libraries.paper.listeners;

import fr.entasia.apis.socket.SocketClient;
import fr.entasia.libraries.Common;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.UUID;

public class BaseListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void PlayerPreLogin(AsyncPlayerPreLoginEvent e) {
		if (!Common.enableDev){

			UUID b = UUID.nameUUIDFromBytes(("OfflinePlayer:"+e.getName()).getBytes());
			if(!e.getUniqueId().equals(b)) {
				e.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
				e.setKickMessage("§CUUID-Spoofing : UUID falsifiée détectée");
			}else{
				if(Common.enableSocket&&!SocketClient.isConnected) {
					e.setKickMessage("§cProblème de connexion avec un service §bEnta§7sia§c. Patience un peu avant de te reconnecter." +
							"\n\nEncore des problèmes après 5 minutes ? Contacte un membre du staff.");
					e.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
				}
			}
		}
	}
}