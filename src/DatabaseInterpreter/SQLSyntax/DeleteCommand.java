package DatabaseInterpreter.SQLSyntax;

import DatabaseInterpreter.DBParser;
import DatabaseInterpreter.DBQuery;
import DatabaseInterpreter.Exceptions.InvalidQueryException;
import java.util.List;

public class DeleteCommand implements Command {
    DBQuery Query;
    public List<String> tokens;

    public void preformCommand(DBQuery Query) throws InvalidQueryException {
        this.Query = Query;
    }

    public void parseInput(DBQuery Query, DBParser parser) throws InvalidQueryException {
        tokens = Query.getTokens();
        parser.checkEndQuery(tokens.get(tokens.size()-1));
        parser.checkInput(tokens.get(1), "FROM");
        String tableName = tokens.get(2);
        parser.checkName(tableName);
        parser.checkInput(tokens.get(3), "WHERE");
        //todo add in support for parsing conditions

    }
}
