package SQLCompiler.SQLCommands;
import SQLCompiler.DBParser;
import SQLCompiler.DBQuery;
import SQLCompiler.SQLEngine.DBEngine;
import SQLCompiler.SQLExceptions.InvalidQueryException;
import java.util.List;

//<Alter>  ::=  ALTER TABLE <TableName> <AlterationType> <AttributeName>
public class AlterCommand implements CommandExpression {
    private List<String> tokens;

    public void preformCommand(DBQuery Query) throws InvalidQueryException {

    }

    public void parseInput(DBQuery Query, DBParser parser) throws InvalidQueryException {
        tokens = Query.getTokens();
        parser.checkEndQuery(tokens.get(tokens.size()-1));
        parser.checkInput(tokens.get(1), "TABLE");
        String tableName = tokens.get(2);
        parser.checkName(tableName);
        String alterationType = tokens.get(3);
        parser.checkAlterationType(alterationType);
        String alterationName = tokens.get(4);
        parser.checkName(alterationName);
    }
}
