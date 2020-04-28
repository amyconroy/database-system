package SQLCompiler;
import SQLCompiler.SQLCommands.CommandExpression;

import java.util.*;

public class DBQuery {
    CommandExpression command;
    List<String> queryTokens;
    String output;

    public DBQuery(){
        List<String> queryTokens = new ArrayList<>();
    }

    public void setTokens(List<String> queryTokens){
        this.queryTokens = new ArrayList<>();
        this.queryTokens = queryTokens;
        System.out.println("test set : " + this.queryTokens);
    }

    public List<String> getTokens(){
        System.out.println("test get : " + queryTokens);
        return queryTokens;
    }

    public String getOutput(){
        return output;
    }

    public void setOutput(String output){
        this.output = output;
    }

    public void setCommand(CommandExpression command){
        this.command = command;
    }

    public CommandExpression getCommand(){
        return command;
    }
}
