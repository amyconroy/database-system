package Commands;

import Exceptions.InvalidQueryException;

public interface Command {
    void preformCommand(DBQuery query) throws InvalidQueryException;
    void parseInput() throws InvalidQueryException;
}
