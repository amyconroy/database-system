package Commands;
import Exceptions.InvalidQueryException;
import java.util.List;

//<Alter>  ::=  ALTER TABLE <TableName> <AlterationType> <AttributeName>
public class AlterCommand implements Command {
    DBQuery Query;
    public List<String> tokens;
    DBParser parser;

    public void preformCommand(DBQuery Query) throws InvalidQueryException {
        this.Query = Query;
        parser = new DBParser();
        tokens = Query.getTokens();
        parseInput();
    }

    public void parseInput() throws InvalidQueryException {
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
