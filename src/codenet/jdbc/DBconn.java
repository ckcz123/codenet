package codenet.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

import codenet.utils.EnvironmentProperty;

public class DBconn {
	
	private Connection conn = null;
	private static String DBAddr = EnvironmentProperty.readConf("DBAddr");
	
	private static DBconn instance = null;
	
	public static Connection getConn() {
		if (instance == null)
			instance = new DBconn();
		return instance.getConnection();
	}

	private DBconn() {}

	private Connection getConnection() {
		try {
			if (conn == null || !conn.isValid(0)) {
				try { conn.close(); } catch (Exception ignore) {}
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(DBAddr,
						"root", "CodeNet2017");
//						"root", "w2qiao");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
