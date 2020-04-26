package Commands;

import Exceptions.InvalidQueryException;
import java.util.List;

public class DropCommand implements Command {
    public List<String> tokens;
    DBQuery Query;

    public void preformCommand(DBQuery Query) throws InvalidQueryException {
        this.Query = Query;
        tokens = Query.getTokens();
        parseInput();
    }

    public void parseInput() throws InvalidQueryException {
        String createType = tokens.get(1);
        StructureType structure = new StructureType();
        String type = structure.getStructureType(createType);
    }

    public void checkSyntax() throws InvalidQueryException {

    }
}
