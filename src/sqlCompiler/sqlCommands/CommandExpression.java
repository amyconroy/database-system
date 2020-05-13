package sqlCompiler.sqlCommands;
import sqlCompiler.DBParser;
import sqlCompiler.DBQuery;
import sqlCompiler.sqlExceptions.InvalidQueryException;

// Implementation of strategy pattern
public interface CommandExpression {
    void parseInput(DBQuery query, DBParser parser) throws InvalidQueryException;
    void preformCommand(DBQuery query) throws Exception;
}
