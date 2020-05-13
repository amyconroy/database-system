package sqlCompiler.sqlCommands;
import sqlCompiler.DBParser;
import sqlCompiler.DBQuery;
import sqlCompiler.sqlEngine.DBEngine;
import sqlCompiler.sqlExceptions.InvalidQueryException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//<Insert>  ::=  INSERT INTO <TableName> VALUES ( <ValueList> )
public class InsertCommand implements CommandExpression {
    private List<String> values;
    private String tableName;

    public void executeCommand(DBQuery Query) throws InvalidQueryException, IOException {
        DBEngine engine = new DBEngine();
        engine.insertTableData(tableName, (ArrayList<String>) values, Query);
    }

    public void parseInput(DBQuery Query, DBParser parser) throws InvalidQueryException {
        List<String> tokens = Query.getTokens();
        parser.checkEndQuery(tokens.get(tokens.size()-1)); // valid final token ;
        parser.checkMatchingInput(tokens.get(1), "INTO");
        tableName = tokens.get(2);
        parser.checkName(tableName);
        parser.checkMatchingInput(tokens.get(3), "VALUES");
        parser.checkBrackets(tokens);
        int endIndex = tokens.indexOf(")");
        int startIndex = tokens.indexOf("(") + 1; // start at first value after (
        // create values list from ( to )
        values = parser.createValuesList(tokens, startIndex, endIndex);
    }
}
