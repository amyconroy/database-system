package SQLCompiler.SQLCondition;

public class GreatOrEqualCondition implements SQLCondition {
    public boolean compareCondition(String valueOne, String valueTwo) {
        int one = Integer.parseInt(valueOne);
        int two = Integer.parseInt(valueTwo);
        return one > two || one == two;
    }
}
