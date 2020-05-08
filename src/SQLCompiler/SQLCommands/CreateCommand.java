package SQLCompiler.SQLCommands;
import SQLCompiler.DBParser;
import SQLCompiler.DBQuery;
import SQLCompiler.SQLEngine.DBEngine;
import SQLCompiler.SQLExceptions.InvalidQueryException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//<Create>  ::=  <CreateDatabase> | <CreateTable>

public class CreateCommand implements CommandExpression {
    private String createType;
    private String createName;
    private ArrayList<String> tableValues;

    public void parseInput(DBQuery Query, DBParser parser) throws InvalidQueryException {
        List<String> tokens = Query.getTokens();
        int endIndex = tokens.size()-1;
        parser.checkEndQuery(tokens.get(endIndex));
        createType = tokens.get(1);
        parser.checkStructureName(createType);
        createName = tokens.get(2);
        parser.checkName(createName);
        int currIndex = 3;
        if(tokens.get(currIndex).equals("(")){
            currIndex++;
            // subtract one to account for final end bracket 
            tableValues = (ArrayList<String>) parser.createAttributeList(tokens, currIndex, endIndex-1);
        }
    }

    public void preformCommand(DBQuery Query) throws Exception {
        DBEngine engine = new DBEngine();
        if(createType.equals("DATABASE")){
            engine.createDatabase(createName, Query);
        }
        else if(createType.equals("TABLE")){
            engine.createTable(createName, Query, tableValues);
        }
    }
}
