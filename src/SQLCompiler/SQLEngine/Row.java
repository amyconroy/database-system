package SQLCompiler.SQLEngine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.StringBuilder;
import java.util.LinkedHashMap;

public class Row implements Serializable {
    // key is column name, value is row
    private LinkedHashMap<String, String> rowData;
    private StringBuilder row; // for ease of printing, row stored here

    public Row(){
        rowData = new LinkedHashMap<>();
        row = new StringBuilder();
    }

    public void changeRowValue(String column, String newValue){
        rowData.replace(column, newValue);
        updateRowToPrint();
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

    private void updateRowToPrint(){
        row = new StringBuilder();
        for(String key : rowData.keySet()){
            String value = rowData.get(key);
            row.append(value);
            row.append("  ");
        }
    }

    public void setRow(String columnName, String newRowData){
        rowData.put(columnName, newRowData);
        updateRowToPrint();
    }

    public void removeColumnValue(String columnName){
        rowData.remove(columnName);
    }
}
