package fr.itrooz.apis.utils;

import fr.itrooz.apis.other.Pair;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Map;

public class LPUtils {

	public static boolean enabled = false;
	public static LuckPerms lpAPI;

	public static void enable(){
		enabled = true;
		lpAPI = LuckPermsProvider.get();
	}

	@Nullable
	public static User getUser(Player p){
		return getUser(p.getName());
	}

	@Nullable
	public static User getUser(String name){
		return lpAPI.getUserManager().getUser(name);
	}


	public static Pair<String, Integer> getPrefixSafe(Player p) {
		Pair<String, Integer> pair = getPrefix(p);
		return pair == null ? new Pair<>("", 0) : pair;
	}

	@Nullable
	public static Pair<String, Integer> getPrefix(Player p) {
		if(enabled){
			User u = getUser(p);
			if(u!=null){
				CachedMetaData meta = u.getCachedData().getMetaData();
				Iterator<Map.Entry<Integer, String>> ite = meta.getPrefixes().entrySet().iterator();
				if(ite.hasNext()) {
					Map.Entry<Integer, String> a = ite.next();
					return new Pair<>(a.getValue().replace("&", "§"), a.getKey());
				}
			}
			return null;
		}else return new Pair<>("", 0);
	}

	public static Pair<String, Integer> getSuffixSafe(Player p) {
		Pair<String, Integer> pair = getSuffix(p);
		return pair == null ? new Pair<>("", 0) : pair;
	}

	@Nullable
	public static Pair<String, Integer> getSuffix(Player p) {
		if(enabled){
			User u = getUser(p);
			if(u!=null){
				CachedMetaData meta = u.getCachedData().getMetaData();
				Iterator<Map.Entry<Integer, String>> ite = meta.getSuffixes().entrySet().iterator();
				if(ite.hasNext()) {
					Map.Entry<Integer, String> a = ite.next();
					return new Pair<>(a.getValue().replace("&", "§"), a.getKey());
				}
			}
			return null;
		}else return new Pair<>("", 0);
	}

}
