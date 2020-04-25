package Exceptions;
import static java.lang.System.exit;

public class InvalidQueryException extends Exception {
    public InvalidQueryException(String errorMessage) {
        super(errorMessage);
    }
}