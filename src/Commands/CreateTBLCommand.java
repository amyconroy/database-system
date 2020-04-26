package Commands;

public class CreateTBLCommand {
    DBQuery query;
    String TBLName;

    public CreateTBLCommand(DBQuery query, String name) {
        this.query = query;
        TBLName = name;
    }

    // check if semi-colon
    // check for bracket
    // check AttributeName then
    // check for comma
    // check for list name
    // check for end bracket

}
