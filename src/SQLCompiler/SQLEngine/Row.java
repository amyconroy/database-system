package SQLCompiler.SQLEngine;

import java.util.ArrayList;
import java.util.HashMap;

public class Row {
    // key is column name
    private HashMap<String, String> rowData;
    private String row;

    public Row(){
        rowData = new HashMap<>();
        row = "";
    }

    public Boolean checkValueExists(String value, String column){
        return rowData.get(column).equals(value);
    }

    // select a specific column value
    public String selectValue(String column){
        return rowData.get(column);
    }

    public String getRow(){
        return row;
    }

    public void setRow(String columnName, String newRowData){
        rowData.put(columnName, newRowData);
        row = row.concat(newRowData);
        row = row.concat("  ");
    }
}
