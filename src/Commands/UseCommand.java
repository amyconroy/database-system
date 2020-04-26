package Commands;

import Exceptions.InvalidQueryException;

import java.util.ArrayList;
import java.util.List;

public class UseCommand implements Command {
    public List<String> tokens;
    DBQuery Query;
    String dbName;
    DBParser parser;

    public void preformCommand(DBQuery Query) throws InvalidQueryException {
        this.Query = Query;
        parser = new DBParser();
        tokens = Query.getTokens();
        parseInput();
    }

    public void parseInput() throws InvalidQueryException {
        validLength();
        parser.checkEndQuery(tokens.get(tokens.size()-1));
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
}
