package Commands;

import Exceptions.InvalidQueryException;

import java.util.ArrayList;
import java.util.List;

public class UseCommand implements Command {
    public List<String> tokens;
    DBQuery Query;
    String dbName;
    TokenType tokenType;

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
        tokenType = TokenType.valueOf(dbName);
        if(!tokenType.equals(TokenType.NAME)){
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
        tokenType = TokenType.valueOf(finalToken);
        if(!tokenType.equals(TokenType.END)){
            throw new InvalidQueryException("ERROR: Missing semicolon.");
        }
    }
}
