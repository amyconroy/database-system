package SQLCompiler.SQLCommands;
import SQLCompiler.DBParser;
import SQLCompiler.DBQuery;
import SQLCompiler.SQLEngine.DBEngine;
import SQLCompiler.SQLExceptions.InvalidQueryException;

public interface CommandExpression {
    // todo change the way we handle performCommand - all have some bodies
    void parseInput(DBQuery query, DBParser parser) throws InvalidQueryException;
    void preformCommand(DBQuery query) throws Exception;
}
