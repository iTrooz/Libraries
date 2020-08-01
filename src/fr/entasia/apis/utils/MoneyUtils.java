package fr.entasia.apis.utils;

import fr.entasia.libraries.Common;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MoneyUtils {

	public static int getMoney(UUID uuid) {
		if(ServerUtils.serverName.equals("Skyblock"))return 100000; // TODO TEMP : beta
		try{
			ResultSet rs = Common.sql.fastSelectUnsafe("SELECT money FROM playerdata.global WHERE uuid = ?", uuid);
			if(rs.next()){
				return rs.getInt("money");
			}

		}catch(SQLException e){
			e.printStackTrace();
			Common.sql.broadcastError();
		}
		return -1;
	}

	public static void setMoney(UUID uuid, int money) {
		if(ServerUtils.serverName.equals("Skyblock"))return; // TODO TEMP : beta
		try{
			Common.sql.fastUpdateUnsafe("UPDATE playerdata.global SET money=? WHERE uuid = ?", money, uuid);
		}catch(SQLException e){
			e.printStackTrace();
			Common.sql.broadcastError();
		}
	}

	public static void addMoney(UUID uuid, int coins){
		if(ServerUtils.serverName.equals("Skyblock"))return; // TODO TEMP : beta
		try{
			Common.sql.fastUpdateUnsafe("UPDATE playerdata.global SET money=money+? WHERE uuid = ?", coins, uuid);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	public static void removeMoney(UUID uuid, int coins){
		addMoney(uuid, -coins);
	}

}
