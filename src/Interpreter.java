import Commands.*;
import Exceptions.InvalidQueryException;

import java.util.*;

public class Interpreter {
    private Map<String, Command> commandTypes;
    private List<String> queryTokens;
    private String stringCommand;

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

    public void interpretQuery(DBQuery query) throws InvalidQueryException {
        queryTokens = query.getTokens();
        stringCommand = queryTokens.get(0);
        Command command = commandTypes.get(stringCommand); // todo add in exception for if the command is not found
        query.setCommand(command);
        command.preformCommand(query);
    }
}
