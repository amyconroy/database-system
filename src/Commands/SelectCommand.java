package Commands;

import Exceptions.InvalidQueryException;

import java.util.ArrayList;
import java.util.List;

public class SelectCommand implements Command {
    public List<String> tokens;
    DBQuery Query;
    DBParser parser;

    public void preformCommand(DBQuery Query) throws InvalidQueryException {
        this.Query = Query;
        parser = new DBParser();
        tokens = Query.getTokens();
        parseInput();
    }

    public void parseInput() throws InvalidQueryException {
        List<String> attributeList = new ArrayList<>();
        parser.checkEndQuery(tokens.get(tokens.size()-1));
        int startIndex = 1;
        int fromIndex = tokens.indexOf("FROM");
        attributeList = parser.createAttributeList(tokens, startIndex, fromIndex);

    }
}
