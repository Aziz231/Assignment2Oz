/*
* THE class will check if DB is working

*  @version 3 14 May 2017 
*  @author TURKI ALJANDAL
*  @ reviewd by Abdulaziz Bazuhayr 
*   * Copyright  2017  All Rights Reserved. *
* the proprietary information of this program is confidential . You shall not
* use it only if you have written permission from Turki al jandal & Abdulaziz Bazuhayr
* we will not be liable any damage suffered as result of using this program */

package controller;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLiteModel {
	Connection conn;

	public SQLiteModel() {
		conn = SQLiteConnection.connect();
	}
	// this method will check if DB is connected

	public boolean isDBConnected() {
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
