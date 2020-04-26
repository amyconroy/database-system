package Commands;

import Exceptions.InvalidQueryException;

public class StructureType {
    String type;

    public String getStructureType(String type) throws InvalidQueryException {
        this.type = type;
        if(type.equals("TABLE")){
            type = "TABLE";
        }
        else if(type.equals("DATABASE")){
            type = "DATABASE";
        }
        else{
            throw new InvalidQueryException("ERROR: Invalid Query");
        }
        return type;
    }
}
