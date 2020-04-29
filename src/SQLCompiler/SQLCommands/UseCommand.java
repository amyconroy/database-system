package SQLCompiler.SQLCommands;
import SQLCompiler.DBParser;
import SQLCompiler.DBQuery;
import SQLCompiler.SQLEngine.DBEngine;
import SQLCompiler.SQLExceptions.InvalidQueryException;

import java.util.List;

//<Use> ::=  USE <DatabaseName>
public class UseCommand implements CommandExpression {
    private List<String> tokens;
    private String dbName;

    public void parseInput(DBQuery Query, DBParser parser) throws InvalidQueryException {
        tokens = Query.getTokens();
        checkValidLength();
        parser.checkEndQuery(tokens.get(tokens.size()-1));
        dbName = tokens.get(1);
        parser.checkName(dbName);
    }

    private void checkValidLength() throws InvalidQueryException {
        if(tokens.size() != 3){
            throw new InvalidQueryException("ERROR: Invalid query");
        }
    }

    public void preformCommand(DBQuery Query) throws InvalidQueryException {
        DBEngine engine = new DBEngine();
        engine.useDatabase(dbName, Query);
    }
}
