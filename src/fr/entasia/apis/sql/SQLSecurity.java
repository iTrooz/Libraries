package fr.entasia.apis.sql;

import fr.entasia.errors.LibraryException;
import fr.entasia.libraries.Common;

import java.util.HashMap;

public class SQLSecurity {


	public static void setHost(String host){
		if(SQLConnection.host==null)SQLConnection.host = host;
		throw new LibraryException("Host already set");
	}
	public static void setPort(int port){
		if(SQLConnection.port==0)SQLConnection.port = port;
		throw new LibraryException("Port already set");
	}

	private static final HashMap<String, String> passwords = new HashMap<>();

	public static void addPass(String user, String pass){
		if(passwords.containsKey(user))throw new LibraryException("Password already in DB");
		passwords.put(user, pass);
	}

	protected static String getPassword(String user){
		if(passwords.size()==0)throw new LibraryException("DB not loaded");
		else{
			String pass = passwords.get(user);
			if(pass==null)throw new LibraryException("User "+user+" not found");
			return pass;
		}
	}
}