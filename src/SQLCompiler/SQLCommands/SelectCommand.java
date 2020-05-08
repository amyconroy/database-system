package SQLCompiler.SQLCommands;
import SQLCompiler.DBParser;
import SQLCompiler.DBQuery;
import SQLCompiler.SQLEngine.DBEngine;
import SQLCompiler.SQLExceptions.InvalidQueryException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// <Select>  ::=  SELECT <WildAttribList> FROM <TableName> |
//                SELECT <WildAttribList> FROM <TableName> WHERE <Condition>
public class SelectCommand implements CommandExpression {
    private List<String> attributeList;
    private String tableName;
    private Boolean selectAll;
    private Boolean singleConditionFlag;
    private List<String> whereConditions;

    public void preformCommand(DBQuery Query) throws InvalidQueryException, IOException {
        DBEngine engine = new DBEngine();
        System.out.println("Test : " + selectAll);
        if(selectAll){
            engine.selectAllFromTable(tableName, Query);
        }
        else if(singleConditionFlag){
            engine.getRowsCondition(tableName, Query);
        }
    }

    public void parseInput(DBQuery Query, DBParser parser) throws InvalidQueryException {
        List<String> tokens = Query.getTokens();
        int listSize = tokens.size()-1;
        parser.checkEndQuery(tokens.get(listSize));
        attributeList = new ArrayList<>();
        int selectIndex = 1; // start index is where WILDATTRIBLIST is specified
        int currIndex = tokens.indexOf("FROM");
        if(currIndex == -1) throw new InvalidQueryException("ERROR: Missing FROM"); // -1 is default if no from
        if(!tokens.get(selectIndex).equals("*")){
            // not a select all, need the list of what to select
            attributeList = parser.createAttributeList(tokens, currIndex, listSize);
            selectAll = false;
        }
        else selectAll = true;
        currIndex++;
        tableName = tokens.get(currIndex);
        parser.checkName(tableName);
        currIndex++;
        if(!(tokens.get(currIndex).equals(";"))){ // if it is not the end of the query
            parser.checkInput(tokens.get(currIndex), "WHERE");
            whereCondition(parser, tokens, currIndex, listSize);
        }
    }

    private void whereCondition(DBParser parser, List<String> tokens, int currIndex, int listSize) throws InvalidQueryException {
        for(String token : tokens) {

        }
        parser.checkConditionBNF(tokens, currIndex, listSize);
    }
}
