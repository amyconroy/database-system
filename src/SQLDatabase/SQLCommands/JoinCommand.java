package SQLDatabase.SQLCommands;

import SQLDatabase.DBParser;
import SQLDatabase.DBQuery;
import SQLDatabase.SQLExceptions.InvalidQueryException;

import java.util.List;

//<Join> ::=  JOIN <TableName> AND <TableName> ON <AttributeName> AND <AttributeName>
public class JoinCommand implements CommandExpression {
    DBQuery Query;
    public List<String> tokens;
    DBParser parser;

    public void preformCommand(DBQuery Query) throws InvalidQueryException {
        tokens = Query.getTokens();
    }

    public void parseInput(DBQuery Query, DBParser parser) throws InvalidQueryException {
        parser.checkEndQuery(tokens.get(tokens.size()-1));
        String table1 = tokens.get(1);
        parser.checkName(table1);
        parser.checkInput(tokens.get(2), "AND");
        String table2 = tokens.get(3);
        parser.checkName(table2);
        parser.checkInput(tokens.get(4), "ON");
        String attribute1 = tokens.get(5);
        parser.checkName(attribute1);
        parser.checkInput(tokens.get(6), "AND");
        String attribute2 = tokens.get(7);
        parser.checkName(attribute2);
    }
}
