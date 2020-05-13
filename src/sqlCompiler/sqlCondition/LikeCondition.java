package sqlCompiler.sqlCondition;

import sqlCompiler.sqlExceptions.InvalidQueryException;

import java.util.regex.Pattern;

public class LikeCondition implements SQLCondition {
    private String attributeName;
    private String valueTwo;
    private final Pattern numberPattern = Pattern.compile("-?\\d+(\\.\\d+)?");


    public boolean compareCondition(String currValue) {
        return currValue.contains(valueTwo);
    }

    private boolean checkInteger(String currValue) {
        return numberPattern.matcher(currValue).matches();
    }

    public void setAttributeName(String attributeName){
        this.attributeName = attributeName;
    }

    public void setCompareValue(String valueTwo) throws InvalidQueryException {
        if(checkInteger(valueTwo)){
            throw new InvalidQueryException("ERROR : String Expected.");
        }
        valueTwo = valueTwo.trim();
        this.valueTwo = valueTwo;
    }

    public String getAttributeName() { return attributeName; }
}
