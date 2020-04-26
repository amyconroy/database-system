package SQLSyntax;

import Exceptions.InvalidQueryException;

import java.util.List;

//<Use> ::=  USE <DatabaseName>
public class UseCommand implements Command {
    public List<String> tokens;
    DBQuery Query;
    DBParser parser;
    String dbName;

    public void preformCommand(DBQuery Query, DBParser parser) throws InvalidQueryException {
        this.Query = Query;
        this.parser = parser;
        tokens = Query.getTokens();
        parseInput();
    }

    public void parseInput() throws InvalidQueryException {
        validLength();
        parser.checkEndQuery(tokens.get(tokens.size()-1));
        dbName = tokens.get(1);
        parser.checkName(dbName);
    }

    public void validLength() throws InvalidQueryException {
        if(tokens.size() != 3){
            throw new InvalidQueryException("ERROR: Invalid query");
        }
    }
}
