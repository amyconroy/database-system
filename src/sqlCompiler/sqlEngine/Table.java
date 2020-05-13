package sqlCompiler.sqlEngine;
import sqlCompiler.sqlCondition.SQLCondition;
import sqlCompiler.sqlExceptions.InvalidQueryException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.lang.StringBuilder;
import java.util.List;

public class Table implements Serializable {
    private final LinkedList<Row> tableRows;
    private final LinkedList<String> columns;
    private int rowId;

    protected Table(){
        tableRows = new LinkedList<>();
        columns = new LinkedList<>();
        rowId = 0;
    }

    protected void setAllRows(LinkedList<Row> newTableRows){
        for(Row row : newTableRows){
            tableRows.add(row);
            row.updateRowToPrint();
        }
    }

    protected Boolean checkColumnExists(String columnName) { return columns.contains(columnName); }

    protected void addColumns(ArrayList<String> columnNames){ columns.addAll(columnNames); }

    protected void addSingleColumn(String columnName){
        columns.add(columnName);
        addColumnToRows(columnName);
    }

    private void addColumnToRows(String columnName){
        for(Row row : tableRows){
            row.addNewColumn(columnName);
        }
    }

    protected void removeSingleColumn(String columnName){
        removeColumnValue(columnName);
        columns.remove(columnName);
    }

    protected String getAllColumns(){
        StringBuilder columnReturn = new StringBuilder();
        for(String column : columns){
            columnReturn.append(column);
            columnReturn.append("  ");
        }
        columnReturn.append("\n");
        return columnReturn.toString();
    }

    protected LinkedList<String> getColumnsList(){ return columns; }

    protected void addRow(ArrayList<String> rowValues){
        Row newRow = new Row();
        int iterator = 0;
        rowId++;
        for(String columnName : columns){
            if(columnName.equals("id")) newRow.setRow(columnName, Integer.toString(rowId));
            else {
                newRow.setRow(columnName, rowValues.get(iterator));
                iterator++;
            }
        }
        tableRows.add(newRow);
    }

    protected String checkCondition(SQLCondition condition) throws InvalidQueryException {
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

    protected void updateRowCondition(SQLCondition condition, String columnName, String newValue)
            throws InvalidQueryException {
        for(Row row : tableRows){
            String column = condition.getAttributeName();
            String tempRow = row.selectValue(column);
            if(condition.compareCondition(tempRow)){
                row.changeRowValue(columnName, newValue);
            }
        }
    }

    protected void removeEntireRow(SQLCondition condition) throws InvalidQueryException {
        String columnName = condition.getAttributeName();
        ArrayList<Row> rowsToRemove = new ArrayList<>();

        for(Row row : tableRows){
            String rowData = row.selectValue(columnName);
            // if it satisfies the condition with value under column - remove
            if(condition.compareCondition(rowData)){
                rowsToRemove.add(row);
            }
        }
        deleteRows(rowsToRemove);
    }

    private void deleteRows(ArrayList<Row> rowsToRemove){
        for(Row row : rowsToRemove){
            tableRows.remove(row);
        }
    }

    protected void removeColumnValue(String columnName){
        for(Row row : tableRows){
            row.removeColumnValue(columnName);
        }
    }

    protected String getAllRows(){
        StringBuilder allRows = new StringBuilder();

        for(Row row : tableRows){
            allRows.append(row.getRow());
            allRows.append("\n");
        }
        return allRows.toString();
    }

    protected String getSpecificRows(SQLCondition condition, List<String> AttributeList) throws InvalidQueryException {
        String printColumn = AttributeList.get(0);
        if(!checkColumnExists(printColumn)) {
            throw new InvalidQueryException("ERROR : Attribute not found.");
        }
        String columnName = condition.getAttributeName();
        StringBuilder specificRows = new StringBuilder();
        if(columnName == null){
            throw new InvalidQueryException("ERROR : Attribute not found.");
        }
        for(Row row : tableRows){
            // get the value for that specified column, check the condition
            String rowValue = row.selectValue(columnName);
            if(rowValue == null){
                throw new InvalidQueryException("ERROR : Attribute not found.");
            }
            if(condition.compareCondition(rowValue)){
                String printRow = row.selectValue(printColumn);
                specificRows.append(printRow);
                specificRows.append("\n");
            }
        }
        return specificRows.toString();
    }

    protected LinkedList<Row> getRowsList(){ return tableRows; }
}
