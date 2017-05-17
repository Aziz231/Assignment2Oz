package controller;

import javafx.beans.property.BooleanProperty;

public class OficialsInformation extends ParticipantsInfromation {

    public OficialsInformation(String id, String type, String name,  int age, String state) {
    	super( id, type,  name,  age,  state);
    }
    
    public OficialsInformation(){
    	
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
    public String getType(){
    	return super.type;
    }
	@Override
	public int getScore() {
		return 0;
	}
	@Override
	public int getTime() {
		return 0;
	}
	@Override
	public BooleanProperty checkedProperty(){ 
		return super.checked;
		}
	
	@Override
	public  boolean isChecked(){
		return super.checked.get();
	}
	
	@Override
	public void setChecked(boolean value){
		super.checked.set(value);
	}
    
}
