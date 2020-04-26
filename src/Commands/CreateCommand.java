package Commands;

import Exceptions.InvalidQueryException;
import java.util.List;

public class CreateCommand implements Command {
    public List<String> tokens;
    DBQuery Query;
    String name;

    public void preformCommand(DBQuery Query) throws InvalidQueryException {
        this.Query = Query;
        tokens = Query.getTokens();
        parseInput();
    }

    public void parseInput() throws InvalidQueryException {
        checkSyntax();
        name = tokens.get(2);
        String createType = tokens.get(1);
        StructureType structure = new StructureType();
        String type = structure.getStructureType(createType);
        if(!name.matches("^[a-zA-Z]*$")){
            throw new InvalidQueryException("ERROR: Invalid query.");
        }
    }

    public void checkSyntax() throws InvalidQueryException {
        String finalToken = tokens.get(tokens.size()-1);
        if(!finalToken.equals(";")){
            throw new InvalidQueryException("ERROR: Missing semicolon.");
        }
    }
}
