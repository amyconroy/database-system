package sqlCompiler.sqlCommands;
import sqlCompiler.DBParser;
import sqlCompiler.DBQuery;
import sqlCompiler.sqlCondition.SQLCondition;
import sqlCompiler.sqlEngine.DBEngine;
import sqlCompiler.sqlExceptions.InvalidQueryException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// <Select>  ::=  SELECT <WildAttribList> FROM <TableName> |
//                SELECT <WildAttribList> FROM <TableName> WHERE <Condition>
public class SelectCommand implements CommandExpression {
    private String tableName;
    private Boolean selectAll;
    private SQLCondition condition;
    private List<String> attributeList;
    private Boolean multipleCondition = false; // checks for nested WHERE conditions
    private Stack<String> tokenStack;
    private Stack<SQLCondition> conditionStack;
    private int listSize;

    public void executeCommand(DBQuery Query) throws InvalidQueryException, IOException {
        DBEngine engine = new DBEngine();
        // if selecting all and not a nested condition
        if(selectAll && !multipleCondition) engine.selectAllFromTable(tableName, Query, condition);
        // selecting only one WHERE condition
        else if(!multipleCondition){
            engine.selectRowsCondition(tableName, Query, condition, attributeList);
        }
        // selecting nested condition
        else engine.selectMultipleCondition(tableName, Query, tokenStack, conditionStack);
    }

    public void parseInput(DBQuery Query, DBParser parser) throws InvalidQueryException {
        List<String> tokens = Query.getTokens();
        listSize = tokens.size()-1;
        parser.checkEndQuery(tokens.get(listSize));
        attributeList = new ArrayList<>();
        int selectIndex = 1; // start index is where WILDATTRIBLIST is specified
        int currIndex = tokens.indexOf("FROM");
        if(currIndex == -1) throw new InvalidQueryException("ERROR: Missing FROM"); // -1 is default if no "from"
        if(!tokens.get(selectIndex).equals("*")){ // list of what to select
            attributeList = parser.createAttributeList(tokens, selectIndex, listSize);
            selectAll = false; // not a WILDATTRIBLIST token (*)
        }
        else selectAll = true;
        currIndex++;
        tableName = tokens.get(currIndex);
        parser.checkName(tableName);
        currIndex++;
        if(!(tokens.get(currIndex).equals(";"))){ // if it is not the end of the query (;)
            parser.checkMatchingInput(tokens.get(currIndex), "WHERE"); // not select all, not end - check WHERE
            whereCondition(parser, tokens, currIndex);
        }
    }

    private void whereCondition(DBParser parser, List<String> tokens, int currIndex) throws InvalidQueryException {
        currIndex++; // move past WHERE token
        if(!tokens.get(currIndex).equals("(")){
            // single condition if first token is not (
            condition = parser.createCondition(tokens, currIndex);
        }
        // multiple condition queries handled with a stack, created when parsing
        else{
            tokenStack = new Stack<>();
            conditionStack = new Stack<>();
            multipleCondition = true; // flag set for when preform action
            for(int i = currIndex; i < listSize; i++){
                String currToken = tokens.get(i);
                // check if AND or OR token
                if ("AND".equals(currToken) || "OR".equals(currToken)) tokenStack.push(currToken);
                // if tokens separating other tokens, add to condition stack
                else if(!("'").equals(currToken) && !(")").equals(currToken) && !("(").equals(currToken)){
                    SQLCondition condition = parser.createCondition(tokens, i);
                    i = i+3; // add three to skip tokens added to condition
                    if(tokens.get(i).equals(")")) i--; // to account for the string literals, moving count back one
                    conditionStack.push(condition);
                }
            }
        }
    }
}
