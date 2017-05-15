package controller;
public class OficialsInformation extends ParticipantsInfromation {

    public OficialsInformation(String id, String type, String name,  int age, String state) {
    	super( id, type,  name,  age,  state);
    }
    
    public OficialsInformation(){
    	
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public String getID() {
        return ID;
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
    
    
}