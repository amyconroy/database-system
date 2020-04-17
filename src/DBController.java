import Exceptions.IncorrectSQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class DBController {
    private List<String> queryTokens;
    private String output;
    private Interpreter interpreter;
    private String input;

    public DBController(){
        queryTokens = new ArrayList<>();
        interpreter = new Interpreter();
    }

    public String preformQuery(String input) throws IncorrectSQLException {
        this.input = input;
        parseInput();
        output = interpreter.interpretQuery(queryTokens);
        return output;
    }

    public void parseInput() throws IncorrectSQLException {
        tokenize();
        checkSQL();
    }

    private void tokenize(){
        StringTokenizer tokenizer = new StringTokenizer(input, " ");
        while(tokenizer.hasMoreElements()){
            queryTokens.add(tokenizer.nextToken());
        }
    }

    private void checkSQL() throws IncorrectSQLException {
        String finalWord = queryTokens.get(queryTokens.size()-1);
        if(!finalWord.substring(finalWord.length() - 1).equals(";")){
            throw new IncorrectSQLException("ERROR Missing ; \n");
        }
    }
}
