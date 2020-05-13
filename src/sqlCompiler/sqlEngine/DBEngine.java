package sqlCompiler.sqlEngine;
import sqlCompiler.DBQuery;
import sqlCompiler.sqlCondition.SQLCondition;
import sqlCompiler.sqlExceptions.InvalidQueryException;
import java.io.*;
import java.util.*;

// class to do the main engine work of the database
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

    // either database or table does not exist
    private void checkStructureExists(File checkStructure) throws InvalidQueryException {
        if(!checkStructure.exists()) throw new InvalidQueryException("ERROR: Structure does not exist.");
    }


    public void createTable(String TBLName, DBQuery query, ArrayList<String> columnValues) throws IOException, InvalidQueryException {
        Table newTable = new Table();
        newTable.addSingleColumn("id"); // to add the first column as ID, only done once
        if(columnValues != null) newTable.addColumns(columnValues); // if the user has provided column names when creating
        serializeTableToFile(TBLName, newTable, query);
        query.setOutput("OK");
    }


    private Boolean checkTableExists(File[] listOfTables, String TBLName){
        // if in list of tables in database, there is a matching table - return true
        return Arrays
                .stream(listOfTables)
                .map(File::getName)
                .anyMatch(currTableName -> currTableName.equals(TBLName));
    }

    // to serialize a table to the appropriate file path (database name + separator + table file name)
    private void serializeTableToFile(String TBLFileName, Table newTable, DBQuery query)
            throws IOException, InvalidQueryException {
        String DBName = query.getDatabase(); // get the current database
        String TableFile = DBName + File.separator + TBLFileName;
        try {
            FileOutputStream fileOut = new FileOutputStream(TableFile);
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
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
        if (tableList != null && !checkTableExists(tableList, TBLName)) {
            throw new InvalidQueryException("ERROR : Table does not exist.");
        }
        Table table = getTable(TBLName, query);
        table.addRow(tableValues);
        serializeTableToFile(TBLName, table, query);
        query.setOutput("OK");
    }

    // to de-serialize a table from the appropriate file
    private Table deserializeTableFromFile(String TBLName, String DBName) throws IOException, InvalidQueryException {
        Table table;
        String tableFileName = DBName + File.separator + TBLName;
        try{
            FileInputStream fileIn = new FileInputStream(tableFileName);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);
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
        // if there are table in the database delete each table
        if(tables != null) for (String table : tables) {
            String tableName = dropName + File.separator + table;
            File toDrop = new File(tableName);
            toDrop.delete();
        }
        return database;
    }

    // get appropriate file pointer for table
    private File getTableFile(String tableName, DBQuery query) throws InvalidQueryException {
        String database = query.getDatabase();
        String tableFileName = database + File.separator + tableName;
        File table = new File(tableFileName);
        checkStructureExists(table);
        return table;
    }

    public void selectAllFromTable(String tableName, DBQuery query, SQLCondition condition)
            throws IOException, InvalidQueryException {
        Table table = getTable(tableName, query);
        String columns = table.getAllColumns();
        String rows = "";
        if(condition == null) printEntireTable(table, query); // they have not specified a condition
        else rows = table.checkCondition(condition); // get the rows from the condition
        String result = columns + rows;
        query.setOutput(result);
    }


    private Table getTable(String tableName, DBQuery query) throws IOException, InvalidQueryException {
        String dbName = query.getDatabase();
        return deserializeTableFromFile(tableName, dbName);
    }


    public void selectRowsCondition(String tableName, DBQuery query, SQLCondition condition,
                                    List<String> attributeList) throws IOException, InvalidQueryException {
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
        }
    }


    public void updateRow(String tableName, String columnName, String newValue,
                          SQLCondition condition, DBQuery query)
            throws IOException, InvalidQueryException {
        Table table = getTable(tableName, query);
        table.updateRowCondition(condition, columnName, newValue);
        query.setOutput("OK");
        serializeTableToFile(tableName, table, query);
    }


    public void deleteRow(String tableName, SQLCondition condition, DBQuery query)
            throws IOException, InvalidQueryException {
        Table table = getTable(tableName, query);
        table.removeEntireRow(condition);
        query.setOutput("OK");
        serializeTableToFile(tableName, table, query);
    }


    public void alterTable(DBQuery query, String column, String tableName, String alterationType)
            throws IOException, InvalidQueryException {
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


    public void joinTables(DBQuery query, String table1, String table2, String attribute1,
                           String attribute2) throws IOException, InvalidQueryException {
        Table firstTable = getTable(table1, query);
        Table secondTable = getTable(table2, query);
        Table joinTable = new Table();
        // get all columns from two tables
        createJoinColumns(joinTable, firstTable, secondTable, table1, table2);
        LinkedList<Row> tableOneRows = firstTable.getRowsList();
        LinkedList<Row> tableTwoRows = secondTable.getRowsList();

        // for the each row from first table, get corresponding column
        for(Row row1 : tableOneRows){
            String value = row1.selectValue(attribute1);
            // for the each row from second table, get corresponding column
            for(Row row2 : tableTwoRows){
                String value2 = row2.selectValue(attribute2);
                //if matching on JOIN value, create a new row
                if(value.equals(value2)) createNewRow(row1, row2, joinTable);
            }
        }
        printEntireTable(joinTable, query);
    }

    // print all columns and rows from a table
    private void printEntireTable(Table table, DBQuery query){
        String columns = table.getAllColumns();
        String rows = table.getAllRows();
        String result = columns + rows;
        query.setOutput(result);
    }

    //creates temporary table to store JOIN results in
    private void createJoinColumns(Table joinTable, Table tableOne, Table tableTwo,
                                   String table1, String table2){
        joinTable.addSingleColumn("id");
        LinkedList<String> tableOneCol = tableOne.getColumnsList();
        LinkedList<String> tableTwoCol = tableTwo.getColumnsList();
        ArrayList<String> newColumns = new ArrayList<>();
        // get the columns for temp table using the two tables
        getColList(table1, tableOneCol, newColumns);
        getColList(table2, tableTwoCol, newColumns);
        joinTable.addColumns(newColumns);
    }


    private void getColList(String tableName, LinkedList<String> tableColumns,
                            ArrayList<String> newColumns){
        // for each column - set it to be the tablename.column name, unless id column
        for(String colName : tableColumns)
            if (!colName.equals("id")) {
                String name = tableName + "." + colName;
                newColumns.add(name);
            }
    }


    private void createNewRow(Row row1, Row row2, Table joinTable){
        ArrayList<String> joinValues = new ArrayList<>();
        ArrayList<String> row1Values = row1.getValues();
        row1Values.remove(0); // remove the id column
        ArrayList<String> row2Values = row2.getValues();
        row2Values.remove(0); // remove the id column
        joinValues.addAll(row1Values); // add all values from first row
        joinValues.addAll(row2Values); // add all values from second row
        joinTable.addRow(joinValues); // add row to temp join table
    }

    // method to handle the stack of conditions
    public void selectMultipleCondition(String tableName, DBQuery Query, Stack<String> tokenStack,
                                        Stack<SQLCondition> conditionStack) throws IOException, InvalidQueryException {
        String type = tokenStack.pop(); // get the type of operator
        // initial comparison results from chooseCondition in linkedList, first two conditions on the stack
        LinkedList<Row> firstResults = chooseCondition(type, conditionStack, tableName, Query);
        LinkedList<Row> finalResults = new LinkedList<>();
        Table table = getTable(tableName, Query);
        if(!tokenStack.empty()){
            type = tokenStack.pop(); // get the next token type
            SQLCondition thirdCondition = conditionStack.pop(); // get the third condition
            LinkedList<Row> tableRows = table.getRowsList();
            // get the rows that satisfy that condition
            LinkedList<Row> conditionRows = getConditionRows(thirdCondition, tableRows);
            finalResults = addConditionRows(firstResults, conditionRows, type);
        }
        else{
            // only had to satisfy two conditions
            finalResults = firstResults;
        }
        printConditions(finalResults, Query, table);
    }

    // sets results of the conditions as the query output
    private void printConditions(LinkedList<Row> finalRows, DBQuery Query, Table table){
        Table conditionTable = new Table();
        LinkedList<String> columns = table.getColumnsList();
        ArrayList<String> newColumns = new ArrayList<>(columns);
        conditionTable.addColumns(newColumns);
        conditionTable.setAllRows(finalRows);
        printEntireTable(conditionTable, Query);
    }

    // choose the condition type - either AND or OR
    private LinkedList<Row> chooseCondition(String type, Stack<SQLCondition> conditionStack, String tableName,
                                            DBQuery Query) throws IOException, InvalidQueryException {
        LinkedList<Row> newRows;
        SQLCondition firstCondition = conditionStack.pop();
        SQLCondition secondCondition = conditionStack.pop();
        Table table = getTable(tableName, Query);
        LinkedList<Row> tableRows = table.getRowsList(); // get all rows
        // execute the conditions and get list of rows matching conditions
        LinkedList<Row> firstConditionMatch = getConditionRows(firstCondition, tableRows);
        LinkedList<Row> secondConditionMatch = getConditionRows(secondCondition, tableRows);
        // then add to temp table based on type
        newRows = addConditionRows(firstConditionMatch, secondConditionMatch, type);
        return newRows;
    }


    private LinkedList<Row> addConditionRows(LinkedList<Row> firstCondition, LinkedList<Row> secondCondition,
                                             String type){
        LinkedList<Row> newRows = new LinkedList<>();
        // if the type is AND, compare the and condition between results
        if(type.equals("AND")){
            newRows = compareAndCondition(firstCondition, secondCondition);
        }
        else{
            // add all, as with or - any rows that satisfy either condition are added
            newRows.addAll(firstCondition);
            newRows.addAll(secondCondition);
        }
        return newRows;
    }

    // get the rows that satisfy the condition
    private LinkedList<Row> getConditionRows(SQLCondition condition, LinkedList<Row> tableRows)
            throws InvalidQueryException {
        LinkedList<Row> matchingRows = new LinkedList<>();
        String columnName = condition.getAttributeName();

        for(Row row : tableRows){
            // select the value from the requisite column
            String value = row.selectValue(columnName);
            if(condition.compareCondition(value)){ // see if it satisfies condition, add row to list
                matchingRows.add(row);
            }
        }
        return matchingRows;
    }

    /* method to compare all rows from first condition matches, and second condition matches
    as only added if rows satisfy both conditions (AND) */
    private LinkedList<Row> compareAndCondition(LinkedList<Row> firstMatches,
                                                    LinkedList<Row> secondMatches){
        LinkedList<Row> andMatches = new LinkedList<>();

        for(Row row1 : firstMatches) {
            String firstRow = row1.getRow();
            for (Row row2 : secondMatches) {
                String secondRow = row2.getRow();
                // if there are matched rows, add them to the list of rows
                if (firstRow.equals(secondRow)) andMatches.add(row1);
            }
        }
        return andMatches;
    }
}
