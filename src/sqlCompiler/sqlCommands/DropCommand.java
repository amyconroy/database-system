package sqlCompiler.sqlCommands;

import sqlCompiler.DBParser;
import sqlCompiler.DBQuery;
import sqlCompiler.sqlEngine.DBEngine;
import sqlCompiler.sqlExceptions.InvalidQueryException;
import java.util.List;

// <Drop> ::=  DROP <Structure> <StructureName>
public class DropCommand implements CommandExpression {
    private String dropName;
    private String dropType;

    public void preformCommand(DBQuery Query) throws InvalidQueryException {
        DBEngine engine = new DBEngine();
        engine.dropStructure(dropType, dropName, Query);
    }

    public void parseInput(DBQuery Query, DBParser parser) throws InvalidQueryException {
        List<String> tokens = Query.getTokens();
        parser.checkEndQuery(tokens.get(tokens.size()-1));
        dropType = tokens.get(1);
        parser.checkStructureName(dropType);
        dropName = tokens.get(2);
        parser.checkName(dropName);
    }
}
