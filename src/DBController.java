import SQLCompiler.DBParser;
import SQLCompiler.DBQuery;
import SQLCompiler.SQLCommands.*;
import SQLCompiler.SQLExceptions.IncorrectSQLException;
import SQLCompiler.SQLExceptions.InvalidQueryException;
import java.util.*;
import java.lang.*;

public class DBController {
    private List<String> queryTokens;
    private String[] tokens;
    private String input;
    private Map<String, CommandExpression> commandTypes;
    private DBQuery DBQuery;

    public DBController(){ DBQuery = new DBQuery(); }

    public String preformQuery(String input) throws Exception {
        this.input = input;
        makeCommandMap();
        queryTokens = new ArrayList<>();
        tokenize();
        DBQuery.setTokens(queryTokens);
        DBQuery.setNewOutput();
        executeQuery();
        return DBQuery.getOutput();
    }

    private void tokenize(){
        tokens = input.split("(?=[ ,;()'])|(?<=[ ,;()'])");
        trimSpaces();
        queryTokens.removeAll(Collections.singleton(""));
    }

    private void trimSpaces(){
        for(String token : tokens) {
            String test = token.trim();
            queryTokens.add(test);
        }
    }

    private void makeCommandMap(){
        commandTypes = new HashMap<>();
        // todo account for different cases
        commandTypes.put("USE", new UseCommand());
        commandTypes.put("CREATE", new CreateCommand());
        commandTypes.put("DROP", new DropCommand());
        commandTypes.put("ALTER", new AlterCommand());
        commandTypes.put("JOIN", new JoinCommand());
        commandTypes.put("INSERT", new InsertCommand());
        commandTypes.put("SELECT", new SelectCommand());
        commandTypes.put("UPDATE", new UpdateCommand());
        commandTypes.put("DELETE", new DeleteCommand());
    }


    // todo pass in parser and tokens, saves the preform command function? then only one in interface
    private void executeQuery() throws Exception {
        DBParser DBParser = new DBParser();
        System.out.println("TEST + "  + queryTokens);
        String stringCommand = queryTokens.get(0);
        CommandExpression command = commandTypes.get(stringCommand);
        if(command == null) throw new IncorrectSQLException("ERROR: Invalid query");
        DBQuery.setCommand(command);
        command.parseInput(DBQuery, DBParser);
        command.preformCommand(DBQuery);
    }
}
