package controller;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection {
	public static Connection connect(){
		File file = new File("participants.sqlite");
		try {
			if(file.exists()){
			Class.forName("org.sqlite.JDBC");	
			Connection conn;
			conn = DriverManager.getConnection("jdbc:sqlite:participants.sqlite");
			return conn;
			}else
				return null;
			}catch (ClassNotFoundException e) {
				return null;
				
				} catch (SQLException e) {
				return null;		
			}

	}
}
