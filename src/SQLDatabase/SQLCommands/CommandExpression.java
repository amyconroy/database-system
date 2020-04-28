package SQLDatabase.SQLCommands;
import SQLDatabase.DBParser;
import SQLDatabase.DBQuery;
import SQLDatabase.SQLExceptions.InvalidQueryException;

public interface CommandExpression {
    // todo change the way we handle performCommand - all have some bodies
    void parseInput(DBQuery query, DBParser parser) throws InvalidQueryException;
    void preformCommand(DBQuery query) throws InvalidQueryException;
}
