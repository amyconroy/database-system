package sqlCompiler.sqlExceptions;

public class InvalidQueryException extends Exception {
    public InvalidQueryException(String errorMessage) { super(errorMessage); }
}