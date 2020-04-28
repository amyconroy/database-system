package SQLDatabase.SQLCommands;

import SQLDatabase.DBParser;
import SQLDatabase.DBQuery;
import SQLDatabase.SQLExceptions.InvalidQueryException;
import java.util.List;

//<Create>  ::=  <CreateDatabase> | <CreateTable>

public class CreateCommand implements CommandExpression {
    public List<String> tokens;

    public void preformCommand(DBQuery Query) throws InvalidQueryException {

    }

    public void parseInput(DBQuery Query, DBParser parser) throws InvalidQueryException {
        tokens = Query.getTokens();
        parser.checkEndQuery(tokens.get(tokens.size()-1));
        String createType = tokens.get(1);
        parser.checkStructureName(createType);
        String name = tokens.get(2);
        parser.checkName(name);
        if(createType.equals("DATABASE")){
            CreateDBCommand DBCommand = new CreateDBCommand(Query, name);
        }
        else if(createType.equals("TABLE")){
            CreateTBLCommand TBLCommand = new CreateTBLCommand(Query, name);
        }
    }
}
