package DatabaseInterpreter.SQLSyntax;
import DatabaseInterpreter.DBParser;
import DatabaseInterpreter.DBQuery;
import DatabaseInterpreter.Exceptions.InvalidQueryException;

public interface Command {
    // todo change the way we handle performCommand - all have some bodies
    void parseInput(DBQuery query, DBParser parser) throws InvalidQueryException;
    void preformCommand(DBQuery query) throws InvalidQueryException;
}
