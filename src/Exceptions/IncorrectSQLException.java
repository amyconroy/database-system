package Exceptions;

public class IncorrectSQLException extends Exception {
    public IncorrectSQLException(String errorMessage){
        super(errorMessage);
    }
}
