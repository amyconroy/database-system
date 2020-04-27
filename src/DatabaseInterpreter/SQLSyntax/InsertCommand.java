package DatabaseInterpreter.SQLSyntax;
import DatabaseInterpreter.DBParser;
import DatabaseInterpreter.DBQuery;
import DatabaseInterpreter.Exceptions.InvalidQueryException;
import java.util.List;

//<Insert>  ::=  INSERT INTO <TableName> VALUES ( <ValueList> )
public class InsertCommand implements Command {
    public List<String> tokens;
    public List<String> values;

    public void preformCommand(DBQuery Query) throws InvalidQueryException {
    }

    public void parseInput(DBQuery Query, DBParser parser) throws InvalidQueryException {
        tokens = Query.getTokens();
        parser.checkEndQuery(tokens.get(tokens.size()-1));
        parser.checkInput(tokens.get(1), "INTO");
        String tableName = tokens.get(2);
        parser.checkName(tableName);
        parser.checkInput(tokens.get(3), "VALUES");
        parser.checkBrackets(tokens);
        int endIndex = tokens.indexOf(")");
        int startIndex = tokens.indexOf("(") + 1;
        values = parser.createValuesList(tokens, startIndex, endIndex);
    }
}
