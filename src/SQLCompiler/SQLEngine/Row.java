package SQLCompiler.SQLEngine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.StringBuilder;
import java.util.LinkedHashMap;
import java.util.Set;

public class Row implements Serializable {
    // key is column name, value is row
    private LinkedHashMap<String, String> rowData;
    private String row; // for ease of printing, row stored here

    public Row(){ rowData = new LinkedHashMap<>(); }

    public void addNewColumn(String columnName){
        rowData.put(columnName, "");
    }

    public void changeRowValue(String column, String newValue){
        rowData.replace(column, newValue);
        System.out.println("new valllll " + newValue);
        System.out.println("testing : " + rowData.get(column));
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
        return row;
    }

    private void updateRowToPrint(){
        row = "";
        StringBuilder newRow = new StringBuilder();
        for(String key : rowData.keySet()){
            String value = rowData.get(key);
            newRow.append(value);
            newRow.append("  ");
        }
        row = newRow.toString();
        System.out.println(" newwww row : " + row);
    }

    public void setRow(String columnName, String newRowData){
        rowData.put(columnName, newRowData);
        updateRowToPrint();
    }

    public void removeColumnValue(String columnName){
        rowData.remove(columnName);
        updateRowToPrint();
    }

    public ArrayList<String> getValues(){
        Set<String> values = rowData.keySet();
        ArrayList<String> rowValue = new ArrayList<>();
        for(String key : values){
            rowValue.add(rowData.get(key));
        }
        return rowValue;
    }
}
