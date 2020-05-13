package sqlCompiler.sqlCondition;
import sqlCompiler.sqlExceptions.InvalidQueryException;

// condition interface to deal with comparisons
public interface SQLCondition {
    boolean compareCondition(String currValue) throws InvalidQueryException;
    void setAttributeName(String attributeName);
    void setCompareValue(String compareValue) throws InvalidQueryException;
    String getAttributeName();
}
