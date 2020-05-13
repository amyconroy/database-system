package sqlCompiler;
import java.util.ArrayList;
import java.util.List;

/* query class to retain database currently in use between queries + current
query tokens */
public class DBQuery {
    private List<String> queryTokens;
    private StringBuilder output;
    private String currentDB;

    public DBQuery(){ queryTokens = new ArrayList<>(); }

    public void setTokens(List<String> queryTokens){
        // not in constructor to reset tokens each query
        this.queryTokens = new ArrayList<>();
        this.queryTokens = queryTokens;
    }

    public List<String> getTokens(){ return queryTokens; }

    public String getOutput(){
        return output.toString();
    }

    // reset output each new query
    public void setNewOutput(){ output = new StringBuilder(); }

    public void setOutput(String newOutput){ output.append(newOutput); }

    // only changed when user inputs 'use' database
    public void setDatabase(String currentDB){ this.currentDB = currentDB; }

    public String getDatabase(){ return currentDB; }
}
