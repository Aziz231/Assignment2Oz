package controller;

public class GameFullException extends Exception {
	String message;
    public GameFullException(String message) {
        this.message = message;
    }
    @Override
    public String getMessage(){
    	return message;
    }
}

