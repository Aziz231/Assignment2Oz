/*
* THis Exception class will be invoked when the Athlete does not match the sport selected sport 

*  @version 3 14 May 2017 
*  @author Abdulaziz Bazuhayr 
*  @ reviewd by TURKI ALJANDAL 
*   * Copyright  2017  All Rights Reserved. *
* the proprietary information of this program is confidential . You shall not
* use it only if you have written permission from Turki al jandal & Abdulaziz Bazuhayr
* we will not be liable any damage suffered as result of using this program */
package controller;

public class WrongTypeException extends Exception {
	String message;

	public WrongTypeException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
