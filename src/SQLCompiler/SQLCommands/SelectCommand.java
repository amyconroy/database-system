package SQLCompiler.SQLCommands;
import SQLCompiler.DBParser;
import SQLCompiler.DBQuery;
import SQLCompiler.SQLCondition.SQLCondition;
import SQLCompiler.SQLEngine.DBEngine;
import SQLCompiler.SQLExceptions.InvalidQueryException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;

// <Select>  ::=  SELECT <WildAttribList> FROM <TableName> |
//                SELECT <WildAttribList> FROM <TableName> WHERE <Condition>
public class SelectCommand implements CommandExpression {
    private String tableName;
    private Boolean selectAll;
    private SQLCondition condition;

    public void preformCommand(DBQuery Query) throws InvalidQueryException, IOException {
        DBEngine engine = new DBEngine();
        if(selectAll){
            engine.selectAllFromTable(tableName, Query, condition);
        }
        else{
            engine.preformRowsCondition(tableName, Query, condition);
        }
    }

    public void parseInput(DBQuery Query, DBParser parser) throws InvalidQueryException {
        List<String> tokens = Query.getTokens();
        int listSize = tokens.size()-1;
        parser.checkEndQuery(tokens.get(listSize));
        List<String> attributeList = new ArrayList<>();
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
            whereCondition(parser, tokens);
        }
    }

    private void whereCondition(DBParser parser, List<String> tokens) throws InvalidQueryException {
        condition = parser.createCondition(tokens);
    }
}
