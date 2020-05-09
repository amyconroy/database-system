package SQLCompiler.SQLCondition;
import SQLCompiler.SQLExceptions.InvalidQueryException;
import java.util.regex.Pattern;

public class GreaterCondition implements SQLCondition {
    private String attributeName;
    private int valueTwo;
    private Pattern numberPattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public boolean compareCondition(String currValue) throws InvalidQueryException {
        if(!checkInteger(currValue)){
            throw new InvalidQueryException("ERROR : Attribute cannot be converted to number.");
        }
        int one = Integer.parseInt(currValue);
        return one > valueTwo;
    }

    private boolean checkInteger(String currValue) {
        return numberPattern.matcher(currValue).matches();
    }

    public void setAttributeName(String attributeName){
        this.attributeName = attributeName;
    }

    public void setCompareValue(String valueTwo){
        int two = Integer.parseInt(valueTwo);
        this.valueTwo = two;
    }

    public String getAttributeName() { return attributeName; }
}
