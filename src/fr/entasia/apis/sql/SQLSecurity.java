package fr.entasia.apis.sql;

import fr.entasia.libraries.Common;

import java.util.HashMap;

public class SQLSecurity {


	public static void setHost(String host){
		if(SQLConnection.host==null)SQLConnection.host = host;
		else if(Common.enableDev)Common.logger.severe("Host already set");
		else throw new SecurityException("Host already set");
	}
	public static void setPort(int port){
		if(SQLConnection.port==0)SQLConnection.port = port;
		else if(Common.enableDev)Common.logger.severe("Port already set");
		else throw new SecurityException("Port already set");
	}

	private static HashMap<String, String> passwords = new HashMap<>();

	public static void addPass(String user, String pass){
		if(passwords.containsKey(user))throw new SecurityException("Password already in DB");
		passwords.put(user, pass);
	}

	protected static String getPassword(String user){
		if(passwords.size()==0)throw new SecurityException("DB not loaded");
		else{
			String pass = passwords.get(user);
			if(pass==null){
				if(Common.enableDev)Common.logger.severe("User "+user+" not found");
				else throw new SecurityException("User "+user+" not found");
			}
			return pass;
		}
	}
}