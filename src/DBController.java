import sqlCompiler.DBParser;
import sqlCompiler.DBQuery;
import sqlCompiler.sqlCommands.*;
import sqlCompiler.sqlExceptions.IncorrectSQLException;

import java.util.*;
import java.lang.*;

public class DBController {
    private List<String> queryTokens;
    private String[] tokens;
    private String input;
    private Map<String, CommandExpression> commandTypes;
    private final DBQuery DBQuery;

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

    // filled with SQL command types
    private void makeCommandMap(){
        commandTypes = new HashMap<>();
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

    private void tokenize(){
        // tokens split by spaces, quotes, semi-colon, and commas
        tokens = input.split("(?=[ ,;()'])|(?<=[ ,;()'])");
        trimSpaces();
        queryTokens.removeAll(Collections.singleton(""));
    }

    // removes remaining spaces on each token
    private void trimSpaces(){
        for(String token : tokens) {
            String newToken = token.trim();
            queryTokens.add(newToken);
        }
    }

    private void executeQuery() throws Exception {
        DBParser DBParser = new DBParser();
        String stringCommand = queryTokens.get(0);
        CommandExpression command = commandTypes.get(stringCommand);
        // null if user did not enter valid query as first token
        if(command == null) throw new IncorrectSQLException("ERROR: Invalid query");
        command.parseInput(DBQuery, DBParser);
        command.preformCommand(DBQuery);
    }
}
