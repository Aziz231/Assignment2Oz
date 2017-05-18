/*
* THE class will connect with DB 

*  @version 3 14 May 2017 
*  @author Abdulaziz Bazuhayr 
*  @ reviewd by TURKI ALJANDAL 
*   * Copyright  2017  All Rights Reserved. *
* the proprietary information of this program is confidential . You shall not
* use it only if you have written permission from Turki al jandal & Abdulaziz Bazuhayr
* we will not be liable any damage suffered as result of using this program */
package controller;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection {
	public static Connection connect() {
		File file = new File("participants.sqlite");
		try {
			if (file.exists()) {
				Class.forName("org.sqlite.JDBC");
				Connection conn;
				conn = DriverManager.getConnection("jdbc:sqlite:participants.sqlite");
				return conn;
			} else
				return null;
		} catch (ClassNotFoundException e) {
			return null;

		} catch (SQLException e) {
			return null;
		}

	}
}
