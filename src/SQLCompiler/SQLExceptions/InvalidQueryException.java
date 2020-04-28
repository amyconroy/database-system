package SQLCompiler.SQLExceptions;

public class InvalidQueryException extends Exception {
    public InvalidQueryException(String errorMessage) {
        super(errorMessage);
    }
}