package sqlCompiler.sqlCondition;
import sqlCompiler.sqlExceptions.InvalidQueryException;

import java.util.regex.Pattern;

// check that it contains the valid letters
public class LikeCondition implements SQLCondition {
    private String attributeName;
    private String valueTwo;
    // regex pattern for valid numbers
    private final Pattern numberPattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public boolean compareCondition(String currValue) {
        return currValue.contains(valueTwo);
    }

    private boolean checkInteger(String currValue) {
        return numberPattern
                .matcher(currValue)
                .matches();
    }

    public void setAttributeName(String attributeName){
        this.attributeName = attributeName;
    }

    public void setCompareValue(String valueTwo) throws InvalidQueryException {
        // if it is a valid integer
        if(checkInteger(valueTwo)) throw new InvalidQueryException("ERROR : String Expected.");
        valueTwo = valueTwo.trim(); // remove the extra spaces
        this.valueTwo = valueTwo;
    }

    public String getAttributeName() { return attributeName; }
}
