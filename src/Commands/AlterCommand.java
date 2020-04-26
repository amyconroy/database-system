package Commands;

import Exceptions.InvalidQueryException;

import java.util.List;

public class AlterCommand implements Command {
    DBQuery Query;
    public List<String> tokens;

    public void preformCommand(DBQuery Query) throws InvalidQueryException {
        this.Query = Query;
        tokens = Query.getTokens();
        parseInput();
    }

    public void parseInput() throws InvalidQueryException {

    }

    //todo figure out a better strucut
    public void checkSyntax() throws InvalidQueryException {
        String finalToken = tokens.get(tokens.size()-1);
        if(!finalToken.equals(";")){
            throw new InvalidQueryException("ERROR: Missing semicolon.");
        }
    }
}
