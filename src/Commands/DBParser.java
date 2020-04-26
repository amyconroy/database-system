package Commands;

import Exceptions.InvalidQueryException;

public class DBParser {
    // todo in this class, create a Commands.DBParser object - check final ;
    public void checkInput(String input, String input2) throws InvalidQueryException {
        if(!input.equals(input2)){
            throw new InvalidQueryException("ERROR: Invalid query.");
        }
    }

    public void checkEndQuery(String input) throws InvalidQueryException {
        if(!input.equals(";")){
            throw new InvalidQueryException("ERROR: Missing semicolon.");
        }
    }
}
