package SQLSyntax;

import Exceptions.InvalidQueryException;
import java.util.List;

// <Drop> ::=  DROP <Structure> <StructureName>
public class DropCommand implements Command {
    public List<String> tokens;
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
        String dropType = tokens.get(1);
        parser.checkStructureName(dropType);
        String dropName = tokens.get(2);
        parser.checkName(dropName);
    }
}
