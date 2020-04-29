package SQLCompiler.SQLEngine;

import java.util.ArrayList;
import java.util.LinkedList;
import java.lang.StringBuilder;

public class Table {
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

    public void addRow(ArrayList<String> rowValues){
        Row newRow = new Row();
        int iterator = 0;

        for(String columnName : columns){
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

    public StringBuilder getAllRows(){
        StringBuilder allRows = new StringBuilder();

        for(Row row : tableRows){
            allRows.append(row.getRow());
            allRows.append("\n");
        }
        return allRows;
    }
}
