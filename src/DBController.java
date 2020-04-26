import SQLSyntax.DBQuery;
import SQLSyntax.Interpreter;
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
        interpreter = new Interpreter();
    }

    public String preformQuery(String input) throws IncorrectSQLException, InvalidQueryException {
        this.input = input;
        queryTokens = new ArrayList<>();
        DBQuery DBQuery = new DBQuery();
        tokenize();
        DBQuery.setTokens(queryTokens);
        interpreter.interpretQuery(DBQuery);
        return DBQuery.getOutput();
    }

    private void tokenize(){
       tokens = input.split("(?=[ ,;()'])|(?<=[ ,;()'])");
       trimSpaces();
       queryTokens.removeAll(Collections.singleton(""));
    }

    private void trimSpaces(){
        for (String token : tokens) {
            queryTokens.add(token.trim());
        }
    }
}
