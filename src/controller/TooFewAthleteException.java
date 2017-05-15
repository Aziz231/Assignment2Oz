package controller;

public class TooFewAthleteException extends Exception {
	String message;
    public TooFewAthleteException(String message) {
    	
    	
        this.message = message;
    }
    @Override
    public String getMessage(){
    	return message;
    }

}
