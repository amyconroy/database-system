package SQLCompiler.SQLCommands;
import SQLCompiler.DBParser;
import SQLCompiler.DBQuery;
import SQLCompiler.SQLEngine.DBEngine;
import SQLCompiler.SQLExceptions.InvalidQueryException;

import java.util.ArrayList;
import java.util.List;

public class SelectCommand implements CommandExpression {
    public List<String> tokens;

    public void preformCommand(DBQuery Query) throws InvalidQueryException {

    }

    public void parseInput(DBQuery Query, DBParser parser) throws InvalidQueryException {
        tokens = Query.getTokens();
        int listSize = tokens.size()-1;
        parser.checkEndQuery(tokens.get(listSize));
        List<String> attributeList = new ArrayList<>();
        int startIndex = 1;
        int currIndex = tokens.indexOf("FROM");
        if(currIndex == -1) throw new InvalidQueryException("ERROR: Missing FROM");
        if(!tokens.get(2).equals("*")) attributeList = parser.createAttributeList(tokens, startIndex, currIndex);
        currIndex++;
        String tableName = tokens.get(currIndex);
        parser.checkName(tableName);
        currIndex++;
        if(!(tokens.get(currIndex).equals(";"))){
            parser.checkInput(tokens.get(currIndex), "WHERE");
            currIndex++;
            parser.checkConditionBNF(tokens, currIndex, listSize);
        }
    }
}
