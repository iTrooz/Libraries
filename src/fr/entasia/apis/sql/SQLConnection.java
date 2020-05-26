package fr.entasia.apis.sql;

import fr.entasia.apis.ServerUtils;

import javax.annotation.Nullable;
import java.sql.*;

public class SQLConnection {

	public Connection connection;

	protected static String host;
	protected static int port=0;
	public String user,password;
	public String db;

	public SQLConnection(){
	}

	public SQLConnection(String user) throws SQLException {
		this(user, null);
	}

	public SQLConnection(String user, String db) throws SQLException {
		if(db==null||db.equals(""))this.db = "";
		else this.db = db;
		this.user = user;
		this.password = SQLSecurity.getPassword(user);
		unsafeConnect();
	}


	public boolean connect() {
		try {
			unsafeConnect();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void unsafeConnect() throws SQLException {
		connection = DriverManager.getConnection("jdbc:mysql://" + host+":"+port+"/"+db+"?useSSL=false", user, password);
		fastSelectUnsafe("SELECT 1=1");
	}

	public boolean disconnect() {
		try {
			connection.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean checkConnect() {
		try{
			if(!connection.isValid(60)){
				return connect();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Nullable
	@Deprecated
	public ResultSet fastSelect(String requ, Object... args){
		try{
			return fastSelectUnsafe(requ, args);
		}catch(SQLException e){
			e.printStackTrace();
			broadcastError();
			return null;
		}
	}

	public ResultSet fastSelectUnsafe(String requ, Object... args) throws SQLException {
		checkConnect();
		PreparedStatement ps = connection.prepareStatement(requ);
		for(int i=0;i<args.length;i++){
			ps.setObject(i+1, args[i].toString());
		}
		return ps.executeQuery();
	}

	@Nullable
	public int fastUpdate(String requ, Object... args){
		try{
			return fastUpdateUnsafe(requ, args);
		}catch(SQLException e){
			e.printStackTrace();
			broadcastError();
			return -1;
		}

	}

	public int fastUpdateUnsafe(String requ, Object... args) throws SQLException {
		checkConnect();
		PreparedStatement ps = connection.prepareStatement(requ);
		for(int i=0;i<args.length;i++){
			ps.setObject(i+1, args[i].toString());
		}
		return ps.executeUpdate();
	}

	public void broadcastError(){
		ServerUtils.permMsg("errorlog", "§cUne erreur s'est produite lors d'une requête SQL ! PREVENIR ITROOZ D'URGENCE");
	}

}
