package sqlCompiler.sqlCommands;
import sqlCompiler.DBParser;
import sqlCompiler.DBQuery;
import sqlCompiler.sqlCondition.SQLCondition;
import sqlCompiler.sqlEngine.DBEngine;
import sqlCompiler.sqlExceptions.InvalidQueryException;

import java.io.IOException;
import java.util.List;

//<Update> ::=  UPDATE <TableName> SET <NameValueList> WHERE <Condition>
public class UpdateCommand implements CommandExpression {
    private String tableName;
    private String columnName;
    private String newValue;
    private SQLCondition condition;

    public void executeCommand(DBQuery Query) throws InvalidQueryException, IOException {
        DBEngine engine = new DBEngine();
        engine.updateRow(tableName, columnName, newValue, condition, Query);
    }

    public void parseInput(DBQuery Query, DBParser parser) throws InvalidQueryException {
        List<String> tokens = Query.getTokens();
        parser.checkEndQuery(tokens.get(tokens.size()-1));
        tableName = tokens.get(1);
        parser.checkName(tableName);
        parser.checkMatchingInput(tokens.get(2), "SET");
        columnName = tokens.get(3); // 3d value is the column name
        parser.checkName(columnName);
        newValue = tokens.get(5); // 5th value is new value
        int currIndex = tokens.indexOf("WHERE");
        if(currIndex == -1) throw new InvalidQueryException("ERROR: Missing WHERE");
        currIndex++;
        condition = parser.createCondition(tokens, currIndex);
    }
}
