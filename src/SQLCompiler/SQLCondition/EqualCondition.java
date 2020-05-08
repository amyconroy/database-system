package SQLCompiler.SQLCondition;

public class EqualCondition implements SQLCondition {
    public boolean compareCondition(String valueOne, String valueTwo) {
        return valueOne.equals(valueTwo);
    }
}
