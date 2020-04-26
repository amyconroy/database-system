package SQLSyntax;

import Exceptions.InvalidQueryException;
import java.util.List;

public class DeleteCommand implements Command {
    DBQuery Query;
    public List<String> tokens;
    DBParser parser;

    public void preformCommand(DBQuery Query, DBParser parser) throws InvalidQueryException {
        this.Query = Query;
        this.parser = parser;
        tokens = Query.getTokens();
        parseInput();
    }

    public void parseInput() throws InvalidQueryException {
        parser.checkEndQuery(tokens.get(tokens.size()-1));
        parser.checkInput(tokens.get(1), "FROM");
        String tableName = tokens.get(2);
        parser.checkName(tableName);
        parser.checkInput(tokens.get(3), "WHERE");
        //todo add in support for parsing conditions

    }
}
