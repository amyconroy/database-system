package SQLCompiler.SQLEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Table {
    private LinkedList<Row> tableRows;
    private LinkedList<String> columns;

    public Table(){
        tableRows = new LinkedList<>();
        columns = new LinkedList<>();
    }

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

    public void checkRows(String rowValue, String columnName){
        for(Row row : tableRows){
            if(row.checkValueExists(rowValue, columnName)){
                row.selectValue(columnName);
            }
        }
    }

    public void getAllRows(){
        for(Row row : tableRows){
            row.getRow();
        }
    }
}
