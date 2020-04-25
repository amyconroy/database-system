import Commands.DBQuery;
import Commands.Interpreter;
import Exceptions.IncorrectSQLException;
import Exceptions.InvalidQueryException;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.lang.*;

public class DBController {
    private List<String> queryTokens;
    private String[] tokens;
    private Interpreter interpreter;
    private String input;

    public DBController(){
        queryTokens = new ArrayList<>();
        interpreter = new Interpreter();
    }

    public String preformQuery(String input) throws IncorrectSQLException, InvalidQueryException {
        this.input = input;
        Commands.DBQuery DBQuery = new DBQuery();
        tokenize();
        DBQuery.setTokens(queryTokens);
        interpreter.interpretQuery(DBQuery);
        String output = DBQuery.getOutput();
        return output;
    }

    private void tokenize(){
       tokens = input.split("(?=[ ,;()])|(?<=[ ,;()])");
       trimSpaces();
       queryTokens.removeAll(Collections.singleton(""));
    }

    private void trimSpaces(){
        for (String token : tokens) {
            queryTokens.add(token.trim());
        }
    }
}
