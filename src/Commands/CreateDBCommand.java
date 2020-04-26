package Commands;

// <CreateDatabase> ::=  CREATE DATABASE <DatabaseName>
public class CreateDBCommand {
    DBQuery query;
    String DBName;

    public CreateDBCommand(DBQuery query, String name) {
       this.query = query;
       DBName = name;
    }

}
