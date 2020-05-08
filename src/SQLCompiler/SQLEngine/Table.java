package SQLCompiler.SQLEngine;
import SQLCompiler.SQLCondition.SQLCondition;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.lang.StringBuilder;

public class Table implements Serializable {
    private LinkedList<Row> tableRows;
    private LinkedList<String> columns;
    private int rowId;

    public Table(){
        tableRows = new LinkedList<>();
        columns = new LinkedList<>();
        rowId = 0;
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
        StringBuilder columnReturn = new StringBuilder();
        for(String column : columns){
            columnReturn.append(column);
            columnReturn.append("  ");
        }
        columnReturn.append("\n");
        return columnReturn.toString();
    }

    public void addRow(ArrayList<String> rowValues){
        Row newRow = new Row();
        int iterator = 0;
        rowId++;
        for(String columnName : columns){
            System.out.println("adding row id " + rowId);
            System.out.println("adding row  " + columnName);
            if(columnName.equals("id")) newRow.setRow(columnName, Integer.toString(rowId));
            else {
                newRow.setRow(columnName, rowValues.get(iterator));
                iterator++;
            }

        }
        tableRows.add(newRow);
    }

    public String checkCondition(SQLCondition condition){
        StringBuilder specificRows = new StringBuilder();
        String columnName = condition.getAttributeName();

        for(Row row : tableRows){
            // get the value for that specified column, check the condition
            String rowValue = row.selectValue(columnName);
            if(condition.compareCondition(rowValue)){
                specificRows.append(row.getRow());
                specificRows.append("\n");
            }
        }
        return specificRows.toString();
    }

    public void updateRowCondition(SQLCondition condition, String columnName, String newValue){
        for(Row row : tableRows){
            System.out.println("what the fuck 2");
            String rowData = row.selectValue(columnName);
            System.out.println("test rowData : " + rowData);
            System.out.println("test columnName : " + columnName);
            if(condition.compareCondition(rowData)){
                row.changeRowValue(columnName, newValue);
                System.out.println("test newValue : " + newValue);
            }
        }
    }

    public void removeEntireRow(SQLCondition condition){
        String columnName = condition.getAttributeName();
        for(Row row : tableRows){
            String rowData = row.selectValue(columnName);
            // if it satisfies the condition with value under column - remove
            if(condition.compareCondition(rowData)){
                tableRows.remove(row);
            }
        }
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
