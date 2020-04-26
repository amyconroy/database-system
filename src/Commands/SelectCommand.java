package Commands;

import Exceptions.InvalidQueryException;

import java.util.ArrayList;
import java.util.List;

public class SelectCommand implements Command {
    public List<String> tokens;
    DBQuery Query;
    DBParser parser;

    public void preformCommand(DBQuery Query, DBParser parser) throws InvalidQueryException {
        this.Query = Query;
        this.parser = parser;
        tokens = Query.getTokens();
        parseInput();
    }

    public void parseInput() throws InvalidQueryException {
        int listSize = tokens.size()-1;
        parser.checkEndQuery(tokens.get(listSize));
        List<String> attributeList = new ArrayList<>();
        int startIndex = 1;
        int currIndex = tokens.indexOf("FROM");
        if(currIndex == -1) throw new InvalidQueryException("ERROR: Missing FROM");
        if(!tokens.get(2).equals("*")) attributeList = parser.createAttributeList(tokens, startIndex, currIndex);
        parser.checkName(tokens.get(currIndex++));
        if(!(tokens.get(currIndex++).equals(";"))) parser.checkInput(tokens.get(currIndex), "WHERE");
        //todo deal with the conditions
    }
}
