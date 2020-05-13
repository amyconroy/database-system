package sqlCompiler.sqlCommands;
import sqlCompiler.DBParser;
import sqlCompiler.DBQuery;
import sqlCompiler.sqlEngine.DBEngine;
import sqlCompiler.sqlExceptions.InvalidQueryException;

import java.util.ArrayList;
import java.util.List;

//<Create>  ::=  <CreateDatabase> | <CreateTable>
public class CreateCommand implements CommandExpression {
    private String createType;
    private String createName;
    private ArrayList<String> tableValues;

    public void parseInput(DBQuery Query, DBParser parser) throws InvalidQueryException {
        List<String> tokens = Query.getTokens();
        int endIndex = tokens.size()-1; // get the final token
        parser.checkEndQuery(tokens.get(endIndex));
        createType = tokens.get(1); // set create type
        parser.checkStructureName(createType);
        createName = tokens.get(2);
        parser.checkName(createName);
        int currIndex = 3; // next token
        if(tokens.get(currIndex).equals("(")){
            currIndex++;
            // subtract one from endIndex to account for final end bracket
            tableValues = (ArrayList<String>) parser.createAttributeList(tokens, currIndex, endIndex-1);
            // attributes list create if the next token is a (, else just create the attribute
        }
    }

    public void executeCommand(DBQuery Query) throws Exception {
        DBEngine engine = new DBEngine();
        if(createType.equals("DATABASE")) engine.createDatabase(createName, Query);
        else if(createType.equals("TABLE")){
            engine.createTable(createName, Query, tableValues);
        }
    }
}
