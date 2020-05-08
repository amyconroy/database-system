package SQLCompiler.SQLCondition;

public class LikeCondition implements SQLCondition {
    private String attributeName;
    private String valueTwo;

    public boolean compareCondition(String currValue) {
        return true;
    }

    public void setAttributeName(String attributeName){
        this.attributeName = attributeName;
    }

    public void setCompareValue(String valueTwo){
        this.valueTwo = valueTwo;
    }

    public String getAttributeName() { return attributeName; }
}
