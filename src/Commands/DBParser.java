package Commands;

import Exceptions.InvalidQueryException;

import java.util.ArrayList;
import java.util.List;

public class DBParser {
    String attributeName = ("^[a-zA-Z_]*$");

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

    public void checkName(String input) throws InvalidQueryException {
        if(!input.matches(attributeName)){
            throw new InvalidQueryException("ERROR: Invalid Attribute Name.");
        }
    }

    public void checkStructureName(String input) throws InvalidQueryException {
        if(!(input.equals("DATABASE")) && !(input.equals("TABLE"))){
            throw new InvalidQueryException("ERROR: Invalid Structure Type.");
        }
    }

    public void checkAlterationType(String input) throws InvalidQueryException {
        if(!(input.equals("ADD")) && !(input.equals("DROP"))){
            throw new InvalidQueryException("ERROR: Invalid Alteration Type.");
        }
    }

    public List<String> createValuesList(List<String> tokens, int startIndex, int endIndex) {
        List<String> values = new ArrayList<>();
        String value = "";

        for(int iterator = startIndex; iterator <= endIndex; iterator++){
            String token = tokens.get(iterator);
            if(value == null && !token.equals(",")){
                value = token;
            }
            if(token.equals(",")){
                values.add(value);
                value = null;
            }
            else if(!token.equals("'") && !token.equals(";")){
                value = value.concat(" ");
                value = value.concat(token);
            }
        }
        return values;
    }

    public void checkBrackets(List<String> tokens) throws InvalidQueryException {
        boolean openFlag = false;
        boolean closeFlag = false;

        for(String token : tokens){
            if(token.equals("(")){
                openFlag = true;
            }
            if(token.equals(")")){
                closeFlag = true;
            }
        }
        if(!openFlag && closeFlag){
            throw new InvalidQueryException("ERROR: Surround values with ( ).");
        }
    }
}
