package SQLCompiler.SQLEngine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.StringBuilder;

public class Row implements Serializable {
    // key is column name
    private HashMap<String, String> rowData;
    private StringBuilder row;

    public Row(){
        rowData = new HashMap<>();
        row = new StringBuilder();
    }

    public Boolean checkValueExists(String value, String column){
        return rowData.get(column).equals(value);
    }

    // select a specific column value
    public String selectValue(String column){
        return rowData.get(column);
    }

    public String getRow(){
        return row.toString();
    }

    public void setRow(String columnName, String newRowData){
        rowData.put(columnName, newRowData);
        row.append(newRowData);
        row.append("  ");
        System.out.println("adding column  " + columnName + "row " + newRowData);
    }

    public void removeColumnValue(String columnName){
        rowData.remove(columnName);
    }
}
