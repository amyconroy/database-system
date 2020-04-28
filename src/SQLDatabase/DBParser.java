package SQLDatabase;

import SQLDatabase.SQLExceptions.InvalidQueryException;

import java.util.ArrayList;
import java.util.List;

public class DBParser {
    String attributeName = ("^[a-zA-Z_]*$");
    List<String> operators;
    int valueIterator;

    public DBParser(){
        operators = new ArrayList<>();
        operators.add("==");
        operators.add(">");
        operators.add("<");
        operators.add(">=");
        operators.add("<=");
        operators.add("!=");
        operators.add("LIKE");
    }

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

    private Boolean checkOperator(String input) throws InvalidQueryException{
        for(String operator : operators){
            if(input.equals(operator)){
                return true;
            }
        }
        return false;
    }

    public void checkConditionBNF(List<String> tokens, int startIndex, int endIndex) throws InvalidQueryException {
       String firstToken = tokens.get(startIndex);
       valueIterator = startIndex;

        if(!firstToken.equals("(")){
            checkIndividualCondition(tokens, firstToken);
        }
        else {
            checkIndividualCondition(tokens, firstToken);
            valueIterator++;
            if(!tokens.get(valueIterator).equals("AND") || !tokens.get(valueIterator).equals("OR")){
                throw new InvalidQueryException("ERROR: Missing action type");
            }
            valueIterator++;
            checkIndividualCondition(tokens, tokens.get(valueIterator));
        }
    }

    public void checkIndividualCondition(List<String> tokens, String firstToken) throws InvalidQueryException {
        checkName(firstToken);
        valueIterator++;
        String token = tokens.get(valueIterator);
        checkOperator(token);
        valueIterator++;
        String value = checkValues(tokens);
    }

    //todo refactor to create one list function
    public List<String> createValuesList(List<String> tokens, int startIndex, int endIndex) throws InvalidQueryException {
        List<String> values = new ArrayList<>();
        String value = "";

        for(valueIterator = startIndex; valueIterator < endIndex; valueIterator++){
            String token = tokens.get(valueIterator);
            System.out.println("test : " + token);
            if(!token.equals(",")){
                value = checkValues(tokens);
                values.add(value);
            }
        }
        System.out.println("test the values : " + values);
        return values;
    }

    // check for appropriate stringliteral, boolean literal, float, int
    public String checkValues(List<String> tokens) throws InvalidQueryException {
        String token = tokens.get(valueIterator);
        boolean validValue = false;

        System.out.println("Initial token test: " + token);
        // string literal
        if(token.equals("'")){
            boolean bracketFlag = false;
            boolean endFlag = false;
            valueIterator++;
            token = "";
            while(!bracketFlag || !endFlag){
                System.out.println("token : " + token);
                if(tokens.get(valueIterator).equals("'")){
                    bracketFlag = true;
                    validValue = true;
                    System.out.println("we here : " + token);
                    break;
                }
                else if(tokens.get(valueIterator).equals(";")){
                    endFlag = true;
                    break;
                }
                token = token.concat(" ");
                token = token.concat(tokens.get(valueIterator));
                valueIterator++;
            }
            if(!bracketFlag){
                throw new InvalidQueryException("ERROR: Missing end quote");
            }
        }
        // number
        else if(token.matches("\\d+")){
            validValue = true;
        }
        // boolean
        else if(token.equalsIgnoreCase("true") || token.equalsIgnoreCase("false")){
            validValue = true;
        }
        if(!validValue){
            throw new InvalidQueryException("ERROR: Incorrect Value");
        }
        return token;
    }

    public List<String> createAttributeList(List<String> tokens, int startIndex, int endIndex) throws InvalidQueryException {
        List<String> attributes = new ArrayList<>();
        boolean wordFlag = false;
        int checkIndex = startIndex + 1;

        if(checkIndex != endIndex){
            checkInput(tokens.get(checkIndex), ",");
            for(int iterator = startIndex; iterator < endIndex; iterator++){
                String currToken = tokens.get(iterator);
                if(currToken.equals(",")){
                    wordFlag = false;
                }
                if(wordFlag){
                    throw new InvalidQueryException("ERROR: Missing comma between attributes.");
                }
                if(!tokens.get(iterator).equals(",")){
                    checkName(currToken);
                    attributes.add(currToken);
                    wordFlag = true;
                }
            }
        }
        else{
            attributes.add(tokens.get(startIndex));
        }
        return attributes;
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
