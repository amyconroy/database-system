package Commands;

import Exceptions.InvalidQueryException;

import java.util.ArrayList;
import java.util.List;

public class UseCommand implements Command {
    public List<String> tokens;
    DBQuery Query;
    String dbName;

    public void preformCommand(DBQuery Query) throws InvalidQueryException {
        this.Query = Query;
        tokens = Query.getTokens();
        parseInput();
        System.out.println("test : " + tokens);
    }

    public void parseInput() throws InvalidQueryException {
        System.out.println("test : " + tokens.size());
        validLength();
        checkSyntax();
        dbName = tokens.get(1);
        if(!dbName.matches("^[a-zA-Z]*$")){
            throw new InvalidQueryException("ERROR: Incorrect usage of database name.");
        }
        // preform the command
    }

    public void validLength() throws InvalidQueryException {
        if(tokens.size() != 3){
            throw new InvalidQueryException("ERROR: Invalid query");
        }
    }

    public void checkSyntax() throws InvalidQueryException {
        String finalToken = tokens.get(tokens.size()-1);
        if(!finalToken.equals(";")){
            throw new InvalidQueryException("ERROR: Missing semicolon.");
        }
    }
}
