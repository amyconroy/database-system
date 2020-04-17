import Command.*;
import java.util.*;

public class Interpreter {
    private Map<String, Command> commandTypes;
    private List<String> queryTokens;
    private String stringCommand;
    private String output;

    public Interpreter(){
        commandTypes = new HashMap<String, Command>();
        List<String> queryTokens = new ArrayList<>();
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

    public String interpretQuery(List<String> queryTokens){
        this.queryTokens = queryTokens;
        stringCommand = queryTokens.get(0);
        Command command = commandTypes.get(stringCommand); // todo add in exception for if the command is not found
        command.preformCommand(queryTokens);
        return output;
    }
}
