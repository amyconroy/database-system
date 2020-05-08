package SQLCompiler.SQLCondition;

public class GreaterCondition implements SQLCondition {
    private String attributeName;
    private int valueTwo;

    public boolean compareCondition(String currValue) {
        int one = Integer.parseInt(currValue);
        return one > valueTwo;
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
