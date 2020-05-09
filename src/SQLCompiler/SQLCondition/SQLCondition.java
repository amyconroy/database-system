package SQLCompiler.SQLCondition;
import SQLCompiler.SQLExceptions.InvalidQueryException;

import java.util.List;

public interface SQLCondition {
    boolean compareCondition(String currValue) throws InvalidQueryException;
    void setAttributeName(String attributeName);
    void setCompareValue(String compareValue) throws InvalidQueryException;
    String getAttributeName();
}
