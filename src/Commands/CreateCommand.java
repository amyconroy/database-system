package Commands;

import Exceptions.InvalidQueryException;
import java.util.List;

public class CreateCommand implements Command {
    public List<String> tokens;
    DBQuery Query;

    public void preformCommand(DBQuery Query) throws InvalidQueryException {
        this.Query = Query;
        tokens = Query.getTokens();
        parseInput();
    }

    public void parseInput() throws InvalidQueryException {
        checkSyntax();
        String createType = tokens.get(1);
        if(createType.equals("TABLE")){
            CreateTBLCommand CreateTBLCommand = new CreateTBLCommand();
        }
        else if(createType.equals("DATABASE")){
            CreateDBCommand CreateDBCommand = new CreateDBCommand();
        }
        else{
            throw new InvalidQueryException("ERROR: Invalid Query");
        }
    }

    public void checkSyntax() throws InvalidQueryException {
        String finalToken = tokens.get(tokens.size()-1);
        if(!finalToken.equals(";")){
            throw new InvalidQueryException("ERROR: Missing semicolon.");
        }
    }
}
