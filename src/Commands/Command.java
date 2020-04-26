package Commands;

import Exceptions.InvalidQueryException;

public interface Command {
    // todo change the way we handle performCommand - all have some bodies
    void preformCommand(DBQuery query) throws InvalidQueryException;
    void parseInput() throws InvalidQueryException;
}
