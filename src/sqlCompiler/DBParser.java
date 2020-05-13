package sqlCompiler;
import sqlCompiler.sqlCondition.*;
import sqlCompiler.sqlExceptions.InvalidQueryException;
import java.util.ArrayList;
import java.util.List;

public class DBParser {
    // to iterate from tokens
    private int valueIterator;

    public void checkMatchingInput(String input, String input2) throws InvalidQueryException {
        if(!input.equals(input2)){
            throw new InvalidQueryException("ERROR: Invalid query.");
        }
    }

    // ensure end of the query includes ;
    public void checkEndQuery(String input) throws InvalidQueryException {
        if(!input.equals(";")){
            throw new InvalidQueryException("ERROR: Missing semicolon.");
        }
    }

    public void checkName(String input) throws InvalidQueryException {
        // regex checks for letter name
        String attributeName = ("^[a-zA-Z_]*$");
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

    // create sqlCondition object with appropriate tokens
    public SQLCondition createCondition(List<String> tokens, int index) throws InvalidQueryException {
        valueIterator = index; // current parsing index
        String valueOne = tokens.get(valueIterator);
        valueIterator++;
        String operator = tokens.get(valueIterator);
        SQLCondition sqlCondition = getOperator(operator);
        valueIterator++;
        String valueTwo = checkValues(tokens);
        if (sqlCondition != null) sqlCondition.setAttributeName(valueOne);
        if (sqlCondition != null) sqlCondition.setCompareValue(valueTwo);
        return sqlCondition;
    }


    private SQLCondition getOperator(String operator){
        switch (operator) {
            case "==":
                return new EqualCondition();
            case "LIKE":
                return new LikeCondition();
            case ">":
                return new GreaterCondition();
            case "<":
                return new LessCondition();
            case ">=":
                return new GreatOrEqualCondition();
            case "<=":
                return new LessOrEqualCondition();
            case "!=":
                return new NotEqualCondition();
            default:
                return null;
        }
    }

    // for BNF - <ValueList>  ::=  <Value>  |  <Value> , <ValueList>
    public List<String> createValuesList(List<String> tokens, int startIndex, int endIndex) throws InvalidQueryException {
        List<String> values = new ArrayList<>();
        String value;

        for(valueIterator = startIndex; valueIterator < endIndex; valueIterator++){
            String token = tokens.get(valueIterator);
            if(!token.equals(",")){
                value = checkValues(tokens);
                values.add(value);
            }
        }
        return values;
    }

    // <Value> ::=  '<StringLiteral>'  |  <BooleanLiteral>  |  <FloatLiteral>  |  <IntegerLiteral>
    public String checkValues(List<String> tokens) throws InvalidQueryException {
        String token = tokens.get(valueIterator);
        boolean validValue = false; // valid value flag for appropriate string/bool/float/int

        // string literal
        if(token.equals("'")){
            boolean bracketFlag = false;
            boolean endFlag = false;
            valueIterator++;
            token = "";

            while(!bracketFlag && !endFlag){
                switch (tokens.get(valueIterator)) {
                    case "'":
                        bracketFlag = true;
                        validValue = true;
                        break;
                    case ";": // in case they have not included the final quote
                        endFlag = true;
                        break;
                    default:
                        token = token.concat(" ");
                        token = token.concat(tokens.get(valueIterator));
                        valueIterator++;
                        break;
                }
            }
            // ensure that you have hit the final close quote for string literal
            if(!bracketFlag){
                throw new InvalidQueryException("ERROR: Missing end quote");
            }
        }
        // float or integer literal, regex checks for number input
        else if(token.matches("\\d+")){
            validValue = true;
        }
        // boolean literal
        else if(token.equalsIgnoreCase("true") || token.equalsIgnoreCase("false")){
            validValue = true;
        }
        if(!validValue) throw new InvalidQueryException("ERROR: Incorrect Value");
        return token;
    }

    //<AttributeList>  ::=  <AttributeName> | <AttributeName> , <AttributeList>
    public List<String> createAttributeList(List<String> tokens, int startIndex, int endIndex) throws InvalidQueryException {
        List<String> attributes = new ArrayList<>();
        boolean wordFlag = false;
        int checkIndex = startIndex + 1; //move forward by one to see if multiple attributes

        // if the next token is not a comma, check for multiple attribute
        if(tokens.get(checkIndex).equals(",")){
            for(int iterator = startIndex; iterator < endIndex; iterator++) {
                String currToken = tokens.get(iterator);
                // wordFlag triggered where word hit, false when comma - catches missing commas
                if (currToken.equals(",")) wordFlag = false;
                if (wordFlag) throw new InvalidQueryException("ERROR: Missing comma between attributes.");
                if (!tokens.get(iterator).equals(",")) {
                    checkName(currToken);
                    attributes.add(currToken);
                    wordFlag = true;
                }
            }
        }
        else{ // if next token is not from, missing a comma
            if(!tokens.get(checkIndex).equals("FROM")){
                throw new InvalidQueryException("ERROR: Missing comma between attributes.");
            }
            attributes.add(tokens.get(startIndex));
        }
        return attributes;
    }

    // iterate through tokens to ensure matching brackets
    public void checkBrackets(List<String> tokens) throws InvalidQueryException {
        boolean openFlag = false;
        boolean closeFlag = false;

        for(String token : tokens){
            if(token.equals("(")) openFlag = true;
            if(token.equals(")")) closeFlag = true;
        }
        if(!openFlag && closeFlag){
            throw new InvalidQueryException("ERROR: Surround values with ( ).");
        }
    }
}
