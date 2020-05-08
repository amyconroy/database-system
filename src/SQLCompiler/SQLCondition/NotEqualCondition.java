package SQLCompiler.SQLCondition;

public class NotEqualCondition implements SQLCondition {
    private String attributeName;
    private String valueTwo;

    public boolean compareCondition(String currValue) {
        System.out.println("testing : " + currValue);
        System.out.println("testing value two : " + valueTwo);
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
