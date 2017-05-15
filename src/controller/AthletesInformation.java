package controller;


public class AthletesInformation extends ParticipantsInfromation {
	
    private int score;
    private int time;  
    
    public AthletesInformation(String id,String type, String name, int age, String state) {
    	super( id, type,  name,  age,  state);
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


}
