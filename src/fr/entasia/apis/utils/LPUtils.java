package fr.entasia.apis.utils;

import fr.entasia.apis.other.Pair;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Map;

public class LPUtils {

	public static LuckPerms lpAPI = LuckPermsProvider.get();

	@Nullable
	public static User getUser(Player p){
		return getUser(p.getName());
	}

	@Nullable
	public static User getUser(String name){
		return lpAPI.getUserManager().getUser(name);
	}

	public static Pair<String, Integer> getHighestPrefix(User u) {
		CachedMetaData meta = u.getCachedData().getMetaData();
		Iterator<Map.Entry<Integer, String>> ite = meta.getPrefixes().entrySet().iterator();
		if(ite.hasNext()){
			Map.Entry<Integer, String> a = ite.next();
			return new Pair<>(a.getValue(), a.getKey());
		}else return null;
	}
	public static Pair<String, Integer> getHighestSuffix(User u) {
		CachedMetaData meta = u.getCachedData().getMetaData();
		Iterator<Map.Entry<Integer, String>> ite = meta.getSuffixes().entrySet().iterator();
		if(ite.hasNext()){
			Map.Entry<Integer, String> a = ite.next();
			return new Pair<>(a.getValue(), a.getKey());
		}else return null;
	}

	public static Pair<String, Integer> getHighestSuffix(Group gr){
		CachedMetaData meta = gr.getCachedData().getMetaData();
		Iterator<Map.Entry<Integer, String>> ite = meta.getSuffixes().entrySet().iterator();
		if(ite.hasNext()){
			Map.Entry<Integer, String> a = ite.next();
			return new Pair<>(a.getValue(), a.getKey());
		}else return null;
	}
}
