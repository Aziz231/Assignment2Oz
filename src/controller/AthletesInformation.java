/*
* The subclass AthletesInformation  will inherit variables from its superclass and will concrete the abstract methods.
* Methods such as setTime will be implemented in this subclass because it is related with Athletes 
*  @version 4 15 May 2017 
*  @author Abdulaziz Bazuhayr
*  @reviewd by Turki Aljandal 
*  
*   * Copyright  2017  All Rights Reserved. *
* the proprietary information of this program is confidential . You shall not
* use it only if you have written permission from Turki al jandal & Abdulaziz Bazuhayr
* we will not be liable any damage suffered as result of using this program */

package controller;

import javafx.beans.property.BooleanProperty;

public class AthletesInformation extends ParticipantsInfromation {

	private int score;
	private int time;

	public AthletesInformation(String id, String type, String name, int age, String state) {
		super(id, type, name, age, state);
	}

	@Override
	public String getName() {
		return super.name;
	}

	@Override
	public String getState() {
		return super.state;
	}

	@Override
	public int getAge() {
		return super.age;
	}

	@Override
	public String getID() {
		return super.ID;
	}

	@Override
	public String getType() {
		return super.type;
	}

	public void setScore(int score) {
		this.score = this.score + score;
	}

	public void setTime(int time) {
		this.time = time;
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public int getTime() {
		return time;
	}

	@Override
	public BooleanProperty checkedProperty() {
		return super.checked;
	}

	@Override
	public boolean isChecked() {
		return super.checked.get();
	}

	@Override
	public void setChecked(boolean value) {
		super.checked.set(value);
	}

}
