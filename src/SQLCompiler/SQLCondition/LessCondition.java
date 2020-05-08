package SQLCompiler.SQLCondition;

public class LessCondition implements SQLCondition{
    public boolean compareCondition(String valueOne, String valueTwo) {
        int one = Integer.parseInt(valueOne);
        int two = Integer.parseInt(valueTwo);
        return one < two;
    }
}
