package sqlCompiler.sqlEngine;
import java.io.Serializable;
import java.util.ArrayList;
import java.lang.StringBuilder;
import java.util.LinkedHashMap;
import java.util.Set;

public class Row implements Serializable {
    private final LinkedHashMap<String, String> rowData; // key is column name, value is row
    private String row; // for ease of printing, row stored here

    protected Row(){ rowData = new LinkedHashMap<>(); }

    protected void addNewColumn(String columnName){
        rowData.put(columnName, "");
    }

    protected void changeRowValue(String column, String newValue){
        rowData.replace(column, newValue);
        updateRowToPrint();
    }

    // select a specific column value
    protected String selectValue(String column){
        return rowData.get(column);
    }

    protected String getRow(){
        return row;
    }

    protected void updateRowToPrint(){
        row = "";
        StringBuilder newRow = new StringBuilder();
        for(String key : rowData.keySet()){
            String value = rowData.get(key);
            newRow.append(value);
            newRow.append("  ");
        }
        row = newRow.toString();
    }

    protected void setRow(String columnName, String newRowData){
        rowData.put(columnName, newRowData);
        updateRowToPrint();
    }

    protected void removeColumnValue(String columnName){
        rowData.remove(columnName);
        updateRowToPrint();
    }

    protected ArrayList<String> getValues(){
        Set<String> values = rowData.keySet();
        ArrayList<String> rowValue = new ArrayList<>();
        for(String key : values){
            rowValue.add(rowData.get(key));
        }
        return rowValue;
    }
}
