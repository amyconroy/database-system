package SQLCompiler.SQLCommands;
import SQLCompiler.DBParser;
import SQLCompiler.DBQuery;
import SQLCompiler.SQLEngine.DBEngine;
import SQLCompiler.SQLExceptions.InvalidQueryException;

import java.io.IOException;
import java.util.List;

//<Alter>  ::=  ALTER TABLE <TableName> <AlterationType> <AttributeName>
public class AlterCommand implements CommandExpression {
    private String tableName;
    private String alterationType;
    private String attributeName;

    public void preformCommand(DBQuery Query) throws InvalidQueryException, IOException {
        DBEngine engine = new DBEngine();
        engine.alterTable(Query, attributeName, tableName, alterationType);
    }

    public void parseInput(DBQuery Query, DBParser parser) throws InvalidQueryException {
        List<String> tokens = Query.getTokens();
        parser.checkEndQuery(tokens.get(tokens.size()-1));
        parser.checkInput(tokens.get(1), "TABLE");
        tableName = tokens.get(2);
        parser.checkName(tableName);
        alterationType = tokens.get(3);
        parser.checkAlterationType(alterationType);
        attributeName = tokens.get(4);
        parser.checkName(attributeName);
    }
}
