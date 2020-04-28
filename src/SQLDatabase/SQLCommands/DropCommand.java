package SQLDatabase.SQLCommands;

import SQLDatabase.DBParser;
import SQLDatabase.DBQuery;
import SQLDatabase.SQLExceptions.InvalidQueryException;
import java.util.List;

// <Drop> ::=  DROP <Structure> <StructureName>
public class DropCommand implements CommandExpression {
    public List<String> tokens;
    DBQuery Query;

    public void preformCommand(DBQuery Query) throws InvalidQueryException {
    }

    public void parseInput(DBQuery Query, DBParser parser) throws InvalidQueryException {
        tokens = Query.getTokens();
        parser.checkEndQuery(tokens.get(tokens.size()-1));
        String dropType = tokens.get(1);
        parser.checkStructureName(dropType);
        String dropName = tokens.get(2);
        parser.checkName(dropName);
    }
}
