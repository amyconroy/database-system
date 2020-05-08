package SQLCompiler.SQLCondition;

public class EqualCondition implements SQLCondition {
    private String attributeName;
    private String valueTwo;

    public boolean compareCondition(String currValue) {
        System.out.println("test equal condition : " + currValue);
        System.out.println("test equal condition : " + valueTwo);
        return valueTwo.equals(currValue);
    }

    public void setAttributeName(String attributeName){
        this.attributeName = attributeName;
    }

    public void setCompareValue(String valueTwo){
        this.valueTwo = valueTwo;
    }

    public String getAttributeName() { return attributeName; }
}
