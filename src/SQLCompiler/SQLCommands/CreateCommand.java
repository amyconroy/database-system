package SQLCompiler.SQLCommands;

import SQLCompiler.DBParser;
import SQLCompiler.DBQuery;
import SQLCompiler.SQLEngine.DBEngine;
import SQLCompiler.SQLExceptions.InvalidQueryException;
import java.util.List;

//<Create>  ::=  <CreateDatabase> | <CreateTable>

public class CreateCommand implements CommandExpression {
    private String createType;
    private String createName;

    public void parseInput(DBQuery Query, DBParser parser) throws InvalidQueryException {
        List<String> tokens = Query.getTokens();
        parser.checkEndQuery(tokens.get(tokens.size()-1));
        createType = tokens.get(1);
        parser.checkStructureName(createType);
        createName = tokens.get(2);
        parser.checkName(createName);
    }

    public void preformCommand(DBQuery Query) throws InvalidQueryException {
        DBEngine engine = new DBEngine();
        if(createType.equals("DATABASE")){
            engine.createDatabase(createName, Query);
        }
        else if(createType.equals("TABLE")){
            CreateTBLCommand TBLCommand = new CreateTBLCommand(Query, createName);
        }
    }
}
