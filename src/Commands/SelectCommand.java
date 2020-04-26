package Commands;

import Exceptions.InvalidQueryException;

import java.util.List;

public class SelectCommand implements Command {
    public List<String> tokens;
    DBQuery Query;
    DBParser parser;

    public void preformCommand(DBQuery Query) throws InvalidQueryException {
        this.Query = Query;
        parser = new DBParser();
        tokens = Query.getTokens();
        parseInput();
    }

    public void parseInput() throws InvalidQueryException {
        parser.checkEndQuery(tokens.get(tokens.size()-1));
    }

    public void checkSyntax() throws InvalidQueryException {

    }
}
