
/*
* THis Exception class will be invoked when the game can't have more than 8 athletes

*  @version 3 14 May 2017 
*  @author TURKI ALJANDAL
*  @ reviewd by Abdulaziz Bazuhayr 
*   * Copyright  2017  All Rights Reserved. *
* the proprietary information of this program is confidential . You shall not
* use it only if you have written permission from Turki al jandal & Abdulaziz Bazuhayr
* we will not be liable any damage suffered as result of using this program */

package controller;

public class GameFullException extends Exception {
	String message;

	public GameFullException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
