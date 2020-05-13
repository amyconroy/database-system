package sqlCompiler.sqlCommands;
import sqlCompiler.DBParser;
import sqlCompiler.DBQuery;
import sqlCompiler.sqlEngine.DBEngine;
import sqlCompiler.sqlExceptions.InvalidQueryException;

import java.util.List;

//<Use> ::=  USE <DatabaseName>
public class UseCommand implements CommandExpression {
    private String dbName;

    public void parseInput(DBQuery Query, DBParser parser) throws InvalidQueryException {
        List<String> tokens = Query.getTokens();
        parser.checkEndQuery(tokens.get(tokens.size()-1)); // ensure final token is a ;
        dbName = tokens.get(1);
        parser.checkName(dbName);
    }

    public void executeCommand(DBQuery Query) throws InvalidQueryException {
        DBEngine engine = new DBEngine();
        engine.useDatabase(dbName, Query);
    }
}
