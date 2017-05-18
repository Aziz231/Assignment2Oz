/*
*The abstract-class ParticipantsInfromation containes common variables and methods that will be used
* in the subclass OficialsInformation and AthletesInformation  * 

*  @version 4 16 May 2017 
*  @author  TURKI ALJANDAL
*  @ reviewed by Abdulaziz Bazuhayr
*  
*  * Copyright  2017  All Rights Reserved. *
* the proprietary information of this program is confidential . You shall not
* use it only if you have written permission from Turki al jandal & Abdulaziz Bazuhayr
* we will not be liable any damage suffered as result of using this program */

package controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public abstract class ParticipantsInfromation {
	protected String name = "";
	protected String state = "";
	protected int age = 0;
	protected String ID = "";
	protected String type = "";
	protected BooleanProperty checked;

	public ParticipantsInfromation(String id, String type, String name, int age, String state) {
		this.name = name;
		this.ID = id;
		this.age = age;
		this.state = state;
		this.type = type;
		this.checked = new SimpleBooleanProperty(false);
	}

	public ParticipantsInfromation() {

	}

	public abstract String getName();

	public abstract String getState();

	public abstract int getAge();

	public abstract String getID();

	public abstract String getType();

	public abstract int getScore();

	public abstract int getTime();

	public abstract boolean isChecked();

	public abstract void setChecked(boolean value);

	public abstract BooleanProperty checkedProperty();

}
