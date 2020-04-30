package SQLCompiler.SQLEngine;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.lang.StringBuilder;

public class Table implements Serializable {
    private LinkedList<Row> tableRows;
    private LinkedList<String> columns;

    public Table(){
        tableRows = new LinkedList<>();
        columns = new LinkedList<>();
    }

    public Boolean checkColumnExists(String columnName) { return columns.contains(columnName); }

    public void addColumns(ArrayList<String> columnNames){
        columns.addAll(columnNames);
    }

    public void addSingleColumn(String columnName){ columns.add(columnName); }

    public void removeSingleColumn(String columnName){
        removeColumnValue(columnName);
        columns.remove(columnName);
    }

    public String getAllColumns(){
        String columnsReturn = columns.toString();
        String newReturn = columnsReturn.concat("\n");
        return newReturn;
    }

    public void addRow(ArrayList<String> rowValues){
        Row newRow = new Row();
        int iterator = 0;

        for(String columnName : columns){
            System.out.println("inserting row value" + rowValues.get(iterator) + "for attribute" + columnName);
            newRow.setRow(columnName, rowValues.get(iterator));
            iterator++;
        }
        tableRows.add(newRow);
    }

    public StringBuilder getSpecificRows(String rowValue, String columnName){
        StringBuilder specificRows = new StringBuilder();

        for(Row row : tableRows){
            if(row.checkValueExists(rowValue, columnName)){
                specificRows.append(row.selectValue(columnName));
                specificRows.append("\n");
            }
        }
        return specificRows;
    }

    // remove the entire row if it contains the value - WHERE column name == this
    public void removeEntireRow(String rowValue, String columnName){
        tableRows.removeIf(row -> row.checkValueExists(rowValue, columnName));
    }

    public void removeColumnValue(String columnName){
        for(Row row : tableRows){
            row.removeColumnValue(columnName);
        }
    }

    public String getAllRows(){
        StringBuilder allRows = new StringBuilder();

        for(Row row : tableRows){
            allRows.append(row.getRow());
            allRows.append("\n");
        }
        return allRows.toString();
    }
}
