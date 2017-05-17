package controller;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLiteModel {
	Connection conn;
	 public SQLiteModel(){
		 conn = SQLiteConnection.connect();
	 }
	 
public boolean isDBConnected(){ 
		 try {
			return !conn.isClosed();
		} catch (SQLException e) {
			return false;
		}		 
	 }

public Connection getConn() {
	return conn;
}

}
