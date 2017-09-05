package codenet.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

import codenet.utils.EnvironmentProperty;

public class DBconn {
	
	private Connection conn = null;
	private static String DBAddr = EnvironmentProperty.readConf("DBAddr");
	public Connection getConn() {
		return conn;
	}

	public DBconn() {
		this.conn = this.getConnection();
	}

	private Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DBAddr,
					"root", "CodeNet2017");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
