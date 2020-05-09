package SQLCompiler.SQLCommands;

import SQLCompiler.DBParser;
import SQLCompiler.DBQuery;
import SQLCompiler.SQLEngine.DBEngine;
import SQLCompiler.SQLExceptions.InvalidQueryException;

import java.io.IOException;
import java.util.List;

//<Join> ::=  JOIN <TableName> AND <TableName> ON <AttributeName> AND <AttributeName>
public class JoinCommand implements CommandExpression {
    private String table1;
    private String table2;
    private String attribute1;
    private String attribute2;

    public void preformCommand(DBQuery Query) throws InvalidQueryException, IOException {
        DBEngine engine = new DBEngine();
        engine.joinTables(Query, table1, table2, attribute1, attribute2);
    }

    public void parseInput(DBQuery Query, DBParser parser) throws InvalidQueryException {
        List<String> tokens = Query.getTokens();
        parser.checkEndQuery(tokens.get(tokens.size()-1));
        table1 = tokens.get(1);
        parser.checkName(table1);
        parser.checkInput(tokens.get(2), "AND");
        table2 = tokens.get(3);
        parser.checkName(table2);
        parser.checkInput(tokens.get(4), "ON");
        attribute1 = tokens.get(5);
        parser.checkName(attribute1);
        parser.checkInput(tokens.get(6), "AND");
        attribute2 = tokens.get(7);
        parser.checkName(attribute2);
    }
}
