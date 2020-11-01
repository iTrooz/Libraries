package fr.entasia.apis.sql;

import fr.entasia.apis.utils.ServerUtils;
import fr.entasia.errors.LibraryException;
import fr.entasia.libraries.Common;

import java.io.File;
import java.sql.*;
import java.util.Properties;

public class SQLConnection {

	public Connection connection;

	protected static String host;
	protected static int port;
	protected String url;
	public Properties props = new Properties();;
	public String db;
	public byte hint = 0;

	public SQLConnection(){
	}

	// <ANCIENS>
	@Deprecated
	public SQLConnection(String user) throws SQLException {
		mariadb(user);
	}

	@Deprecated
	public SQLConnection(String user, String db) throws SQLException {
		mariadb(user, db);
	}
	// </ANCIENS>

	public SQLConnection(boolean dev) {
		if(dev)hint = 1;
	}

	public SQLConnection mariadb(String user) throws SQLException {
		return mariadb(user, null);
	}

	public SQLConnection mariadb(String user, String db) throws SQLException {
		if(db==null)this.db = "";
		else this.db = db;
		this.props.put("user", user);
		this.url = "jdbc:mysql://" + host+":"+port+"/"+this.db+"?useSSL=false";
		try{
			this.props.put("password", SQLSecurity.getPassword(user));
			unsafeConnect();
		}catch(SQLException|LibraryException e) {
			if(hint==1){
				Common.logger.warning("Connection SQL de l'user "+user+" faussée");
				setFake(true);
			}else throw e;
		}
		return this;
	}

	public SQLConnection sqlite(File folder, String file) throws ClassNotFoundException, SQLException {
		return sqlite(folder.getPath()+"/"+file);
	}

	public SQLConnection sqlite(String file) throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		url = "jdbc:sqlite:"+file;
		unsafeConnect();
		return this;
	}

	public SQLConnection setFake(boolean fake){
		if(fake) {
			hint = 2;
			connection = new FakeConnection();
		}
		return this;
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
		DriverManager.setLoginTimeout(3);
		connection = DriverManager.getConnection(url, props);
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
		if(hint ==2)return true;
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
