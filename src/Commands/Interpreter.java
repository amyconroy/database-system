package Commands;
import Exceptions.IncorrectSQLException;
import Exceptions.InvalidQueryException;

import java.util.*;

public class Interpreter {
    private Map<String, Command> commandTypes;

    public Interpreter(){
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

    public void interpretQuery(DBQuery query) throws InvalidQueryException, IncorrectSQLException {
        List<String> queryTokens = query.getTokens();
        System.out.println("TEST + "  + queryTokens);
        String stringCommand = queryTokens.get(0);
        Command command = commandTypes.get(stringCommand);
        if(command == null){
            throw new IncorrectSQLException("ERROR: Invalid query");
        }
        query.setCommand(command);
        command.preformCommand(query);
    }
}
