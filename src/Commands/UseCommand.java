package Commands;
import Exceptions.InvalidQueryException;

import java.util.ArrayList;

public class UseCommand implements Command {
    public ArrayList<String> tokens;
    DBQuery Query;
    TokenType tokenType;
    String currToken;

    public void preformCommand(DBQuery Query) throws InvalidQueryException {
        this.Query = Query;
        parseInput();
        tokens = Query.getTokens();
    }

    public void parseInput() throws InvalidQueryException {
        validLength();
        currToken = tokenType.getToken();
    }

    public void validLength() throws InvalidQueryException {
        if(tokens.size() != 2){
            throw new InvalidQueryException("ERROR: Invalid query");
        }
    }
}
