package controller;

public class NoRefereeException extends Exception {
	String message;
    public NoRefereeException(String message) {
        this.message = message;
    }
    @Override
    public String getMessage(){
    	return message;
    }
}
