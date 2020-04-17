package Commands;
import java.util.*;

public class DBQuery {
    Command command;
    ArrayList<String> queryTokens;
    String output;

    public DBQuery(){
        ArrayList<String> queryTokens = new ArrayList<>();
    }

    public void setTokens(ArrayList<String> queryTokens){
        this.queryTokens = queryTokens;
    }

    public ArrayList<String> getTokens(){
        return queryTokens;
    }

    public String getOutput(){
        return output;
    }

    public void setOutput(String output){
        this.output = output;
    }

    public void setCommand(Command command){
        this.command = command;
    }

    public Command getCommand(){
        return command;
    }
}
