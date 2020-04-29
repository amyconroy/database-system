package SQLCompiler.SQLEngine;
import SQLCompiler.DBQuery;
import SQLCompiler.SQLExceptions.InvalidQueryException;
import java.io.*;
import java.util.ArrayList;

// class to do the main engine work of the database
public class DBEngine {

    public void createDatabase(String DBName, DBQuery query) throws InvalidQueryException {
        File newDB = new File(DBName);
        if(newDB.exists()) throw new InvalidQueryException("ERROR: Database already exists.");
        if(!newDB.mkdir()) throw new InvalidQueryException("ERROR: Unable to create database.");
        query.setDatabase(DBName);
        query.setOutput("OK");
    }

    public void useDatabase(String DBName, DBQuery query) throws InvalidQueryException {
        File database = new File(DBName);
        checkStructureExists(database);
        query.setDatabase(DBName);
        query.setOutput("OK");
    }

    private void checkStructureExists(File checkStructure) throws InvalidQueryException {
        if(checkStructure.exists()) throw new InvalidQueryException("ERROR: Structure does not exist.");
    }

    public void createTable(String TBLName, DBQuery query, ArrayList<String> columnValues) throws IOException, InvalidQueryException {
        String DBName = query.getDatabase();
        Table newTable = new Table();
        if(columnValues != null) newTable.addColumns(columnValues);
        String newFileName = DBName + File.separator + TBLName;
        serializeTableToFile(newFileName, newTable);
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
    private void serializeTableToFile(String TBLFileName, Table newTable) throws IOException, InvalidQueryException {
        FileOutputStream fileOut = null;
        ObjectOutputStream objOut = null;
        try {
            fileOut = new FileOutputStream(TBLFileName);
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
        table.addColumns(tableValues);
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

    public void dropStructure(String structureName, DBQuery query) throws InvalidQueryException {
        File structure = new File(structureName);
        checkStructureExists(structure);
        if(structure.delete()){
            query.setOutput("OK");
        }
        else{
            throw new InvalidQueryException("ERROR: Unable to DROP structure.");
        }
    }
}