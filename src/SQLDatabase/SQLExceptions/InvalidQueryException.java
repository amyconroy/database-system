package SQLDatabase.SQLExceptions;

public class InvalidQueryException extends Exception {
    public InvalidQueryException(String errorMessage) {
        super(errorMessage);
    }
}