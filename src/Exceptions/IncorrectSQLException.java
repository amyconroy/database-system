package Exceptions;

import static java.lang.System.exit;

public class IncorrectSQLException extends Exception {
    public IncorrectSQLException(String errorMessage){
        super(errorMessage);
    }
}
