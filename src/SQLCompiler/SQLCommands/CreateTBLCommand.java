package SQLCompiler.SQLCommands;

import SQLCompiler.DBQuery;

// <CreateTable>  ::=  CREATE TABLE <TableName> | CREATE TABLE <TableName> ( <AttributeList> )
public class CreateTBLCommand {
    private DBQuery query;
    private String TBLName;

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
