package SQLCompiler.SQLCommands;
import SQLCompiler.DBParser;
import SQLCompiler.DBQuery;
import SQLCompiler.SQLCondition.SQLCondition;
import SQLCompiler.SQLEngine.DBEngine;
import SQLCompiler.SQLExceptions.InvalidQueryException;

import java.io.IOException;
import java.util.List;

// <Delete>   ::=  DELETE FROM <TableName> WHERE <Condition>
public class DeleteCommand implements CommandExpression {
    private SQLCondition condition;
    private String tableName;

    public void preformCommand(DBQuery Query) throws InvalidQueryException, IOException {
        DBEngine engine = new DBEngine();
        engine.deleteRow(tableName, condition, Query);
    }

    public void parseInput(DBQuery Query, DBParser parser) throws InvalidQueryException {
        List<String> tokens = Query.getTokens();
        parser.checkEndQuery(tokens.get(tokens.size()-1));
        parser.checkInput(tokens.get(1), "FROM");
        tableName = tokens.get(2);
        parser.checkName(tableName);
        parser.checkInput(tokens.get(3), "WHERE");
        condition = parser.createCondition(tokens, 4);
    }
}
