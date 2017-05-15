package controller;

public class WrongTypeException extends Exception {
	String message;
    public WrongTypeException(String message) {
        this.message = message;
    }
    @Override
    public String getMessage(){
    	return message;
    }
}

