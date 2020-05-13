package sqlCompiler.sqlCommands;
import sqlCompiler.DBParser;
import sqlCompiler.DBQuery;
import sqlCompiler.sqlExceptions.InvalidQueryException;

/* Implementation of strategy pattern to parse and preform with same
command object */
public interface CommandExpression {
    void parseInput(DBQuery query, DBParser parser) throws InvalidQueryException;
    void executeCommand(DBQuery query) throws Exception;
}
