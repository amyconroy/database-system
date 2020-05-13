package SQLCompiler.SQLEngine;
import SQLCompiler.DBQuery;
import SQLCompiler.SQLCondition.SQLCondition;
import SQLCompiler.SQLExceptions.InvalidQueryException;
import java.io.*;
import java.util.*;

// class to do the main engine work of the database
//todo dont need to researlize when select / not changing anything
public class DBEngine {

    public void createDatabase(String DBName, DBQuery query) throws InvalidQueryException {
        File newDB = new File(DBName);
        if(newDB.exists()) throw new InvalidQueryException("ERROR: Database already exists.");
        if(!newDB.mkdir()) throw new InvalidQueryException("ERROR: Unable to create database.");
        query.setOutput("OK");
    }

    public void useDatabase(String DBName, DBQuery query) throws InvalidQueryException {
        File database = new File(DBName);
        checkStructureExists(database);
        query.setDatabase(DBName);
        query.setOutput("OK");
    }

    private void checkStructureExists(File checkStructure) throws InvalidQueryException {
        if(!checkStructure.exists()) throw new InvalidQueryException("ERROR: Structure does not exist.");
    }

    public void createTable(String TBLName, DBQuery query, ArrayList<String> columnValues) throws IOException, InvalidQueryException {
        Table newTable = new Table();
        newTable.addSingleColumn("id");
        if(columnValues != null) newTable.addColumns(columnValues);
        serializeTableToFile(TBLName, newTable, query);
        query.setOutput("OK");
    }

    private Boolean checkTableExists(File[] listOfTables, String TBLName){
        for(File file : listOfTables){
            String currTableName = file.getName();
            if(currTableName.equals(TBLName)){
                return true;
            }
        }
        return false;
    }

    // to serialize a table to the appropriate file
    private void serializeTableToFile(String TBLFileName, Table newTable, DBQuery query) throws IOException, InvalidQueryException {
        FileOutputStream fileOut = null;
        ObjectOutputStream objOut = null;
        String DBName = query.getDatabase();
        String TableFile = DBName + File.separator + TBLFileName;
        try {
            fileOut = new FileOutputStream(TableFile);
            objOut = new ObjectOutputStream(fileOut);
            objOut.writeObject(newTable);
            objOut.close();
        } catch (FileNotFoundException e) {
            throw new InvalidQueryException("ERROR : Unable to Create Table.");
        }
    }

    public void insertTableData(String TBLName, ArrayList<String> tableValues, DBQuery query) throws InvalidQueryException, IOException {
        String DBName = query.getDatabase();
        File database = new File(DBName);
        File[] tableList = database.listFiles();
        if(!checkTableExists(tableList, TBLName)){
            throw new InvalidQueryException("ERROR : Table does not exist.");
        }
        String TBLFileName = DBName + File.separator + TBLName;
        Table table;
        table = deserializeTableFromFile(TBLFileName);
        table.addRow(tableValues);
        serializeTableToFile(TBLName, table, query);
        query.setOutput("OK");
    }

    // to de-serialize a table from the appropriate file
    private Table deserializeTableFromFile(String TBLFileName) throws IOException, InvalidQueryException {
        FileInputStream fileIn;
        ObjectInputStream objIn;
        Table table;
        try{
            fileIn = new FileInputStream(TBLFileName);
            objIn = new ObjectInputStream(fileIn);
            table = (Table) objIn.readObject();
            objIn.close();
        } catch (FileNotFoundException | ClassNotFoundException e) {
            throw new InvalidQueryException("ERROR : Table not found.");
        }
        return table;
    }

    public void dropStructure(String dropType, String dropName, DBQuery query) throws InvalidQueryException {
        File structureFile;
        if(dropType.equals("TABLE")) structureFile = getTableFile(dropName, query);
        else structureFile = dropDatabase(dropName);
        if(structureFile.delete()) query.setOutput("OK");
        else throw new InvalidQueryException("ERROR: Unable to DROP database.");
    }

    private File dropDatabase(String dropName){
        File database = new File(dropName);
        String[] tables = database.list();
        if(tables != null){
            for(String table : tables){
                String tableName = dropName + File.separator + table;
                File toDrop = new File(tableName);
                toDrop.delete();
            }
        }
        return database;
    }

    private File getTableFile(String tableName, DBQuery query) throws InvalidQueryException {
        String database = query.getDatabase();
        String tableFileName = database + File.separator + tableName;
        File table = new File(tableFileName);
        checkStructureExists(table);
        return table;
    }

    public void selectAllFromTable(String tableName, DBQuery query, SQLCondition condition) throws IOException, InvalidQueryException {
        Table table = getTable(tableName, query);
        String columns = table.getAllColumns();
        String rows;
        if(condition == null) rows = table.getAllRows();
        else rows = table.checkCondition(condition);
        String result = columns + rows;
        query.setOutput(result);
        serializeTableToFile(tableName, table, query);
    }

    private Table getTable(String tableName, DBQuery query) throws IOException, InvalidQueryException {
        String dbName = query.getDatabase();
        String tableFileName = dbName + File.separator + tableName;
        return deserializeTableFromFile(tableFileName);
    }

    public void selectRowsCondition(String tableName, DBQuery query, SQLCondition condition, List<String> attributeList) throws IOException, InvalidQueryException {
        if(attributeList == null){
            selectAllFromTable(tableName, query, condition);
        }
        else{
            Table table = getTable(tableName, query);
            StringBuilder output = new StringBuilder();
            for(String column : attributeList){
                output.append(column);
                output.append(" ");
            }
            output.append("\n");
            output.append(table.getSpecificRows(condition, attributeList));
            query.setOutput(output.toString());
            serializeTableToFile(tableName, table, query);
        }
    }

    public void updateRow(String tableName, String columnName, String newValue, SQLCondition condition, DBQuery query) throws IOException, InvalidQueryException {
        Table table = getTable(tableName, query);
        table.updateRowCondition(condition, columnName, newValue);
        query.setOutput("OK");
        serializeTableToFile(tableName, table, query);
    }

    public void deleteRow(String tableName, SQLCondition condition, DBQuery query) throws IOException, InvalidQueryException {
        Table table = getTable(tableName, query);
        table.removeEntireRow(condition);
        query.setOutput("OK");
        serializeTableToFile(tableName, table, query);
    }

    public void alterTable(DBQuery query, String column, String tableName, String alterationType) throws IOException, InvalidQueryException {
        Table table = getTable(tableName, query);
        if(alterationType.equals("ADD")){
            table.addSingleColumn(column);
        }
        else{
            table.removeSingleColumn(column);
        }
        query.setOutput("OK");
        serializeTableToFile(tableName, table, query);
    }

    public void joinTables(DBQuery query, String table1, String table2, String attribute1, String attribute2) throws IOException, InvalidQueryException {
        Table firstTable = getTable(table1, query);
        Table secondTable = getTable(table2, query);
        Table joinTable = new Table();
        createJoinColumns(joinTable, firstTable, secondTable, table1, table2);
        LinkedList<Row> tableOneRows = firstTable.getRowsList();
        LinkedList<Row> tableTwoRows = secondTable.getRowsList();

        for(Row row1 : tableOneRows){
            String value = row1.selectValue(attribute1);
            for(Row row2 : tableTwoRows){
                String value2 = row2.selectValue(attribute2);
                if(value.equals(value2)){
                    createNewRow(row1, row2, joinTable);
                }
            }
        }
        String columns = joinTable.getAllColumns();
        String rows = joinTable.getAllRows();
        String result = columns + rows;
        query.setOutput(result);
    }

    private void createJoinColumns(Table joinTable, Table tableOne, Table tableTwo, String table1, String table2){
        joinTable.addSingleColumn("id");
        LinkedList<String> tableOneCol = tableOne.getColumnsList();
        LinkedList<String> tableTwoCol = tableTwo.getColumnsList();
        ArrayList<String> newColumns = new ArrayList<>();
        getColList(table1, tableOneCol, newColumns);
        getColList(table2, tableTwoCol, newColumns);
        joinTable.addColumns(newColumns);
    }

    private void getColList(String tableName, LinkedList<String> tableColumns, ArrayList<String> newColumns){
        for(String colName : tableColumns){
            if(!colName.equals("id")){
                String name = tableName + "." + colName;
                newColumns.add(name);
            }
        }
    }

    private void createNewRow(Row row1, Row row2, Table joinTable){
        ArrayList<String> joinValues = new ArrayList<>();
        ArrayList<String> row1Values = row1.getValues();
        row1Values.remove(0);
        ArrayList<String> row2Values = row2.getValues();
        row2Values.remove(0);
        joinValues.addAll(row1Values);
        joinValues.addAll(row2Values);
        joinTable.addRow(joinValues);
    }

    public void selectMultipleCondition(String tableName, DBQuery Query, Stack<String> tokenStack,
                                        Stack<SQLCondition> conditionStack) throws IOException, InvalidQueryException {
        String type = tokenStack.pop();
        LinkedList<Row> firstResults = chooseCondition(type, conditionStack, tableName, Query);
        LinkedList<Row> finalResults = new LinkedList<>();
        Table table = getTable(tableName, Query);
        if(!tokenStack.empty()){
            type = tokenStack.pop();
            SQLCondition thirdCondition = conditionStack.pop();
            LinkedList<Row> tableRows = table.getRowsList();
            LinkedList<Row> conditionRows = getConditionRows(thirdCondition, tableRows);
            if(type.equals("AND")){
                finalResults = compareAndConditionRows(firstResults, conditionRows);
            }
            else{
                finalResults.addAll(firstResults);
                finalResults.addAll(conditionRows);
            }
        }
        else{
            finalResults = firstResults;
        }
        printConditions(finalResults, Query, table);
    }

    private void printConditions(LinkedList<Row> finalRows, DBQuery Query, Table table){
        Table conditionTable = new Table();
        LinkedList<String> columns = table.getColumnsList();
        ArrayList<String> newColumns = new ArrayList<>(columns);
        conditionTable.addColumns(newColumns);
        conditionTable.setAllRows(finalRows);
        String columnsPrint = conditionTable.getAllColumns();
        String rows = conditionTable.getAllRows();
        String result = columnsPrint + rows;
        Query.setOutput(result);
    }

    private LinkedList<Row> chooseCondition(String type, Stack<SQLCondition> conditionStack, String tableName,
                                            DBQuery Query) throws IOException, InvalidQueryException {
        LinkedList<Row> newRows = new LinkedList<>();
        SQLCondition firstCondition = conditionStack.pop();
        SQLCondition secondCondition = conditionStack.pop();
        Table table = getTable(tableName, Query);
        LinkedList<Row> tableRows = table.getRowsList();
        LinkedList<Row> firstConditionMatch = getConditionRows(firstCondition, tableRows);
        LinkedList<Row> secondConditionMatch = getConditionRows(secondCondition, tableRows);
        if(type.equals("AND")){
            newRows = andCondition(firstConditionMatch, secondConditionMatch);
        }
        else{
            newRows.addAll(firstConditionMatch);
            newRows.addAll(secondConditionMatch);
        }
        return newRows;
    }

    private LinkedList<Row> andCondition(LinkedList<Row> firstConditionMatch,  LinkedList<Row> secondConditionMatch){
        return compareAndConditionRows(firstConditionMatch, secondConditionMatch);
    }

    private LinkedList<Row> getConditionRows(SQLCondition condition, LinkedList<Row> tableRows)
            throws InvalidQueryException {
        LinkedList<Row> matchingRows = new LinkedList<>();
        String columnName = condition.getAttributeName();

        for(Row row : tableRows){
            String value = row.selectValue(columnName);
            if(condition.compareCondition(value)){
                matchingRows.add(row);
            }
        }
        return matchingRows;
    }

    private LinkedList<Row> compareAndConditionRows(LinkedList<Row> firstMatches, LinkedList<Row> secondMatches){
        LinkedList<Row> andMatches = new LinkedList<>();
        for(Row row1 : firstMatches) {
            String firstRow = row1.getRow();
            for (Row row2 : secondMatches) {
                String secondRow = row2.getRow();
                if (firstRow.equals(secondRow)) {
                    andMatches.add(row1);
                }
            }
        }
        return andMatches;
    }
}
