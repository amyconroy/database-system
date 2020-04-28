package SQLDatabase.SQLCommands;

import SQLDatabase.DBParser;
import SQLDatabase.DBQuery;
import SQLDatabase.SQLExceptions.InvalidQueryException;

import java.util.List;

//<Use> ::=  USE <DatabaseName>
public class UseCommand implements CommandExpression {
    public List<String> tokens;
    String dbName;

    public void preformCommand(DBQuery Query) throws InvalidQueryException {
    }

    public void parseInput(DBQuery Query, DBParser parser) throws InvalidQueryException {
        tokens = Query.getTokens();
        validLength();
        parser.checkEndQuery(tokens.get(tokens.size()-1));
        dbName = tokens.get(1);
        parser.checkName(dbName);
    }

    private void validLength() throws InvalidQueryException {
        if(tokens.size() != 3){
            throw new InvalidQueryException("ERROR: Invalid query");
        }
    }
}
