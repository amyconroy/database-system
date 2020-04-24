import Commands.DBQuery;
import Exceptions.IncorrectSQLException;
import java.util.regex.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.*;

public class DBController {
    private List<String> queryTokens;
    private String[] tokens;
    private String output;
    private Interpreter interpreter;
    private String input;
    private DBQuery DBQuery;

    public DBController(){
        queryTokens = new ArrayList<>();
        interpreter = new Interpreter();
        DBQuery = new DBQuery();
    }

    public String preformQuery(String input) throws IncorrectSQLException {
        this.input = input;
        parseInput();
        // DBQuery.setTokens((ArrayList<String>) queryTokens);
        //output = interpreter.interpretQuery(DBQuery);
        return output;
    }

    public void parseInput() throws IncorrectSQLException {
        tokenize();
        trimSpaces();
        checkSQL();
    }

    private void tokenize(){
       tokens = input.split("(?=[ ,;()])|(?<=[ ,;()])");
       trimSpaces();
       System.out.println("testbitch : " + queryTokens);
    }

    private void trimSpaces(){
        for(String token : tokens){
            queryTokens.add(token.trim());
        }
    }

    private void checkSQL() throws IncorrectSQLException {
        String finalWord = queryTokens.get(queryTokens.size()-1);
        if(!finalWord.substring(finalWord.length() - 1).equals(";")){
            throw new IncorrectSQLException("ERROR Missing ; \n");
        }
    }
}
