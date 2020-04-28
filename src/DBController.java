import SQLDatabase.DBParser;
import SQLDatabase.DBQuery;
import SQLDatabase.SQLCommands.*;
import SQLDatabase.SQLExceptions.IncorrectSQLException;
import SQLDatabase.SQLExceptions.InvalidQueryException;
import java.util.*;
import java.lang.*;

public class DBController {
    private List<String> queryTokens;
    private String[] tokens;
    private String input;
    private Map<String, CommandExpression> commandTypes;

    public String preformQuery(String input) throws IncorrectSQLException, InvalidQueryException {
        this.input = input;
        makeCommandMap();
        queryTokens = new ArrayList<>();
        DBQuery DBQuery = new DBQuery();
        tokenize();
        DBQuery.setTokens(queryTokens);
        identifyQuery(DBQuery);
        return DBQuery.getOutput();
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

    // todo pass in parser and tokens, saves the preform command function? then only one in interface
    private void identifyQuery(DBQuery query) throws InvalidQueryException, IncorrectSQLException {
        DBParser DBParser = new DBParser();
        System.out.println("TEST + "  + queryTokens);
        String stringCommand = queryTokens.get(0);
        CommandExpression command = commandTypes.get(stringCommand);
        if(command == null){
            throw new IncorrectSQLException("ERROR: Invalid query");
        }
        query.setCommand(command);
        command.parseInput(query, DBParser);
        command.preformCommand(query);
    }
}
