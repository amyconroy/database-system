package sqlCompiler.sqlCondition;

import sqlCompiler.sqlExceptions.InvalidQueryException;

import java.util.regex.Pattern;

public class LessCondition implements SQLCondition{
    private String attributeName;
    private int valueTwo;
    private final Pattern numberPattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public boolean compareCondition(String currValue) throws InvalidQueryException {
        if(!checkInteger(currValue)){
            throw new InvalidQueryException("ERROR : Attribute cannot be converted to number.");
        }
        int one = Integer.parseInt(currValue);
        return one < valueTwo;
    }

    private boolean checkInteger(String currValue) {
        return numberPattern.matcher(currValue).matches();
    }

    public void setAttributeName(String attributeName){
        this.attributeName = attributeName;
    }

    public void setCompareValue(String valueTwo){
        this.valueTwo = Integer.parseInt(valueTwo);
    }

    public String getAttributeName() { return attributeName; }
}
