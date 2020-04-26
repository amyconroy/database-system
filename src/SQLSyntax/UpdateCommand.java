package SQLSyntax;

import Exceptions.InvalidQueryException;

import java.util.List;

//<Update> ::=  UPDATE <TableName> SET <NameValueList> WHERE <Condition>
//todo allow for nameValue lists
public class UpdateCommand implements Command {
    DBQuery Query;
    public List<String> tokens;
    DBParser parser;

    //todo pass in tokens to each command as well
    public void preformCommand(DBQuery Query, DBParser parser) throws InvalidQueryException {
        this.Query = Query;
        this.parser = parser;
        tokens = Query.getTokens();
        parseInput();
    }

    public void parseInput() throws InvalidQueryException {
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
