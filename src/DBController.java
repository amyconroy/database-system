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

    public String preformQuery(String input){
        this.input = input;
        parseInput();
        output = interpreter.interpretQuery(queryTokens);
        return output;
    }

    public void parseInput(){
        tokenize();
    }

    private void tokenize(){
        StringTokenizer tokenizer = new StringTokenizer(input, " ");
        while(tokenizer.hasMoreElements()){
            queryTokens.add(tokenizer.nextToken());
        }
    }
}
