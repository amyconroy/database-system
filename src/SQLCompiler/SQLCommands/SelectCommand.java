package SQLCompiler.SQLCommands;
import SQLCompiler.DBParser;
import SQLCompiler.DBQuery;
import SQLCompiler.SQLEngine.DBEngine;
import SQLCompiler.SQLExceptions.InvalidQueryException;

import java.util.ArrayList;
import java.util.List;

public class SelectCommand implements CommandExpression {
    private List<String> tokens;
    private List<String> attributeList;
    private String tableName;

    public void preformCommand(DBQuery Query) throws InvalidQueryException {
        DBEngine engine = new DBEngine();
        if(attributeList == null){
            engine.selectAllFromTable(tableName);
        }
        else{
            // select specific attribute from table
            // add the condition list = only select where
        }
    }

    public void parseInput(DBQuery Query, DBParser parser) throws InvalidQueryException {
        tokens = Query.getTokens();
        int listSize = tokens.size()-1;
        parser.checkEndQuery(tokens.get(listSize));
        attributeList = new ArrayList<>();
        int startIndex = 1;
        int currIndex = tokens.indexOf("FROM");
        if(currIndex == -1) throw new InvalidQueryException("ERROR: Missing FROM");
        if(!tokens.get(2).equals("*")) attributeList = parser.createAttributeList(tokens, startIndex, currIndex);
        currIndex++;
        tableName = tokens.get(currIndex);
        parser.checkName(tableName);
        currIndex++;
        if(!(tokens.get(currIndex).equals(";"))){
            // need to create a list of attributes here
            parser.checkInput(tokens.get(currIndex), "WHERE");
            currIndex++;
            parser.checkConditionBNF(tokens, currIndex, listSize);
        }
    }
}
