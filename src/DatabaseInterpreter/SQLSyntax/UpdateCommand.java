package DatabaseInterpreter.SQLSyntax;

import DatabaseInterpreter.DBParser;
import DatabaseInterpreter.DBQuery;
import DatabaseInterpreter.Exceptions.InvalidQueryException;

import java.util.List;

//<Update> ::=  UPDATE <TableName> SET <NameValueList> WHERE <Condition>
//todo allow for nameValue lists
public class UpdateCommand implements Command {
    public List<String> tokens;

    //todo pass in tokens to each command as well
    public void preformCommand(DBQuery Query) throws InvalidQueryException {
    }

    public void parseInput(DBQuery Query, DBParser parser) throws InvalidQueryException {
        tokens = Query.getTokens();
        parser.checkEndQuery(tokens.get(tokens.size()-1));
        String tableName = tokens.get(1);
        parser.checkName(tableName);
        parser.checkInput(tokens.get(2), "SET");
        int currIndex = tokens.indexOf("WHERE");
        if(currIndex == -1) throw new InvalidQueryException("ERROR: Missing WHERE");
        int startIndex = 3;
        List<String> nameValueList = parser.createValuesList(tokens, startIndex, currIndex);
        //condition
    }
}
