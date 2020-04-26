package SQLSyntax;

import Exceptions.InvalidQueryException;
import java.util.List;

//<Create>  ::=  <CreateDatabase> | <CreateTable>

public class CreateCommand implements Command {
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
