package fr.entasia.libraries.bungee;


import fr.entasia.apis.other.ChatComponent;
import fr.entasia.apis.socket.SocketClient;
import fr.entasia.libraries.Common;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class BaseListeners implements Listener {


	@EventHandler(priority = EventPriority.HIGHEST)
	public void onLogin(LoginEvent e) {
		if(!Common.enableDev){
			UUID b = UUID.nameUUIDFromBytes(("OfflinePlayer:"+e.getConnection().getName()).getBytes());
			if(e.getConnection().getUniqueId().equals(b)){
				if(Common.enableSocket&&!SocketClient.isConnected) {
					e.setCancelled(true);
					e.setCancelReason(new TextComponent("§cProblème de connexion avec un service §bEnta§7sia§c. Patience un peu." +
							"\n\nEncore des problèmes après 5 minutes ? Contacte un membre du staff."));
				}else{
					try {
						Common.sql.checkConnect();
						PreparedStatement ps = Common.sql.connection.prepareStatement("UPDATE playerdata.global SET address=? WHERE uuid=?");
						ps.setString(1, e.getConnection().getAddress().getAddress().getHostAddress());
						ps.setString(2, e.getConnection().getUniqueId().toString());
						int a = ps.executeUpdate();
						if(a<1){
							ps = Common.sql.connection.prepareStatement("INSERT INTO playerdata.global (uuid, name, address) VALUES (?, ?, ?)");
							ps.setString(1, e.getConnection().getUniqueId().toString());
							ps.setString(2, e.getConnection().getName());
							ps.setString(3, e.getConnection().getAddress().getAddress().getHostAddress());
							ps.execute();
						}
					} catch (SQLException e2) {
						e2.printStackTrace();
						e.setCancelled(true);
						e.setCancelReason(new TextComponent("§cDésolé , quelque chose s'est mal passé. Réesaye de te connecter." +
								"\n\nEncore des problèmes après 5 minutes ? Contacte un membre du staff"));
					}
				}
			}else{
				e.setCancelled(true);
				e.setCancelReason(ChatComponent.create("§CUUID-Spoofing : UUID falsifiée détectée"));
			}
		}
	}
}