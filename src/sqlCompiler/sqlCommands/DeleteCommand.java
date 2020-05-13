package sqlCompiler.sqlCommands;
import sqlCompiler.DBParser;
import sqlCompiler.DBQuery;
import sqlCompiler.sqlCondition.SQLCondition;
import sqlCompiler.sqlEngine.DBEngine;
import sqlCompiler.sqlExceptions.InvalidQueryException;

import java.io.IOException;
import java.util.List;

// <Delete>   ::=  DELETE FROM <TableName> WHERE <Condition>
public class DeleteCommand implements CommandExpression {
    private SQLCondition condition;
    private String tableName;

    public void executeCommand(DBQuery Query) throws InvalidQueryException, IOException {
        DBEngine engine = new DBEngine();
        engine.deleteRow(tableName, condition, Query);
    }

    public void parseInput(DBQuery Query, DBParser parser) throws InvalidQueryException {
        List<String> tokens = Query.getTokens();
        parser.checkEndQuery(tokens.get(tokens.size()-1));
        parser.checkMatchingInput(tokens.get(1), "FROM");
        tableName = tokens.get(2);
        parser.checkName(tableName);
        parser.checkMatchingInput(tokens.get(3), "WHERE");
        condition = parser.createCondition(tokens, 4); // create the new condition object
    }
}
