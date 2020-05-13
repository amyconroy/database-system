package sqlCompiler.sqlCondition;

// checks that the two inputs do not equal the other
public class NotEqualCondition implements SQLCondition {
    private String attributeName;
    private String valueTwo;

    public boolean compareCondition(String currValue) {
        return !currValue.equals(valueTwo);
    }

    public void setAttributeName(String attributeName){
        this.attributeName = attributeName;
    }

    public void setCompareValue(String valueTwo){
        this.valueTwo = valueTwo;
    }

    public String getAttributeName() { return attributeName; }
}
