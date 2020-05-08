package SQLCompiler.SQLCondition;
import java.util.List;

public interface SQLCondition {
    boolean compareCondition(String currValue);
    void setAttributeName(String attributeName);
    void setCompareValue(String compareValue);
    String getAttributeName();
}
