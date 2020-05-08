package SQLCompiler.SQLCondition;

public class NotEqualCondition implements SQLCondition {
    public boolean compareCondition(String valueOne, String valueTwo) {
        return !valueOne.equals(valueTwo);
    }
}
