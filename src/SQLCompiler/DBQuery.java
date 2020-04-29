package SQLCompiler;
import SQLCompiler.SQLCommands.CommandExpression;
import java.util.*;
import java.lang.StringBuilder;

public class DBQuery {
    private CommandExpression command;
    private List<String> queryTokens;
    private StringBuilder output;
    private String currentDB;

    public DBQuery(){ queryTokens = new ArrayList<>(); }

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
        return output.toString();
    }

    public void setNewOutput(){ output = new StringBuilder(); }

    public void setOutput(String newOutput){ output.append(newOutput); }

    public void setCommand(CommandExpression command){
        this.command = command;
    }

    public CommandExpression getCommand(){
        return command;
    }

    public void setDatabase(String currentDB){ this.currentDB = currentDB; }

    public String getDatabase(){ return currentDB; }
}
