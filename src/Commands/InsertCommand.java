package Commands;
import Exceptions.InvalidQueryException;
import java.util.List;

//<Insert>  ::=  INSERT INTO <TableName> VALUES ( <ValueList> )
public class InsertCommand implements Command {
    public List<String> tokens;
    public List<String> values;
    DBQuery Query;
    DBParser parser;

    public void preformCommand(DBQuery Query, DBParser parser) throws InvalidQueryException {
        this.Query = Query;
        this.parser = parser;
        tokens = Query.getTokens();
        parseInput();
    }

    public void parseInput() throws InvalidQueryException {
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
