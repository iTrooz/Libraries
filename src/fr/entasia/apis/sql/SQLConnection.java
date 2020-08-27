package fr.entasia.apis.sql;

import fr.entasia.apis.utils.ServerUtils;

import java.io.File;
import java.sql.*;

public class SQLConnection {

	public Connection connection;

	protected static String host;
	protected static int port;
	protected String url;
	public String user,password;
	public String db;
	public byte dev = 0;

	public SQLConnection(){
	}

	// <ANCIENS>
	@Deprecated
	public SQLConnection(String user) throws SQLException {
		sql(user);
	}

	@Deprecated
	public SQLConnection(String user, String db) throws SQLException {
		sql(user, db);
	}
	// </ANCIENS>

	public void sql(String user) throws SQLException {
		sql(user, null);
	}

	@Deprecated
	public void sql(String user, String db) throws SQLException {
		if(db==null)this.db = "";
		else this.db = db;
		this.user = user;
		this.password = SQLSecurity.getPassword(user);
		this.url = "jdbc:mysql://" + host+":"+port+"/"+this.db+"?useSSL=false";
		try{
			unsafeConnect();
		}catch(SQLException e) {
			if(dev==1){
				dev = 2;
				connection = new FakeConnection();
			}else throw e;
		}
	}

	public void sqlite(File folder, String file) throws ClassNotFoundException, SQLException {
		sqlite(folder.getPath()+"/"+file);
	}

	public void sqlite(String file) throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		url = "jdbc:sqlite:"+file;
		unsafeConnect();
	}

	public void setDev(){
		dev = 1;
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
		connection = DriverManager.getConnection(url, user, password);
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
		if(dev==2)return true;
		try{
			if(!connection.isValid(60)) return connect();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public ResultSet fastSelectUnsafe(String requ, Object... args) throws SQLException {
		checkConnect();
		PreparedStatement ps = connection.prepareStatement(requ);
		for(int i=0;i<args.length;i++){
			ps.setObject(i+1, args[i].toString());
		}
		return ps.executeQuery();
	}

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
		ServerUtils.permMsg("log.sqlerror", "§cUne erreur s'est produite lors d'une requête SQL ! PREVENIR ITROOZ D'URGENCE");
	}

//	public static void main(String[] fa) throws Exception {
//		SQLConnection sql = new SQLConnection();
//		sql.setDev();
//		sql.sql("root", "playerdata");
//
//		SQLConnection sqlite = new SQLConnection();
//		sqlite.setDev();
//		sqlite.sqlite("database.db");
//	}

}
