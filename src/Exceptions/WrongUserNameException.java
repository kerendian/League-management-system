package Exceptions;

public class WrongUserNameException extends Exception{
    public WrongUserNameException(String message) {
        super(message);
    }
}
