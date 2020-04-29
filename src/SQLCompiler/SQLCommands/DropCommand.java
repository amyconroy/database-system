package SQLCompiler.SQLCommands;

import SQLCompiler.DBParser;
import SQLCompiler.DBQuery;
import SQLCompiler.SQLEngine.DBEngine;
import SQLCompiler.SQLExceptions.InvalidQueryException;
import java.util.List;

// <Drop> ::=  DROP <Structure> <StructureName>
public class DropCommand implements CommandExpression {
    private String dropName;

    public void preformCommand(DBQuery Query) throws InvalidQueryException {
        DBEngine engine = new DBEngine();
        engine.dropStructure(dropName, Query);
    }

    public void parseInput(DBQuery Query, DBParser parser) throws InvalidQueryException {
        List<String> tokens = Query.getTokens();
        parser.checkEndQuery(tokens.get(tokens.size()-1));
        String dropType = tokens.get(1);
        parser.checkStructureName(dropType);
        dropName = tokens.get(2);
        parser.checkName(dropName);
    }
}
